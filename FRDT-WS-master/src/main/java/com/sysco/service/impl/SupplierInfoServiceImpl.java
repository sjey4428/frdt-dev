package com.sysco.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysco.dao.*;
import com.sysco.entity.*;
import com.sysco.request.*;
import com.sysco.response.GenericResponse;
import com.sysco.utils.TransformUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.sysco.common.Constants;
import com.sysco.exception.AWS3Exception;
import com.sysco.exception.FRDTException;
import com.sysco.exception.SqlException;
import com.sysco.exception.ValidationException;
import com.sysco.service.SupplierInfoService;
import com.sysco.utils.AWS3Utils;
import com.sysco.utils.FormatterUtils;

/**
 * Created by james.zhu on 2018/8/22.
 */
@Service
public class SupplierInfoServiceImpl implements SupplierInfoService {

    private final Logger logger = Logger.getLogger(SupplierInfoServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SupplierInfoMapper supplierInfoMapper;

    @Autowired
    private SupplierLocationMapper supplierLocationMapper;

    @Autowired
    private SupplierFileMapper supplierFileMapper;

    @Autowired
    private SupplierContractExceptionMapper supplierContractExceptionMapper;

    @Autowired
    private SupplierPointDetailMapper supplierPointDetailMapper;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private SupplierLogMapper supplierLogMapper;

    @Autowired
    private SupplierSyscoSuvcMapper supplierSyscoSuvcMapper;

    @Autowired
    private FreightUpdateDetailMapper freightUpdateDetailMapper;


    @Value("${spring.mail.from}")
    private String mailFrom;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSupplier(ReqSupplier reqSupplier) {
        List<SupplierInfo> supplierInfos = supplierInfoMapper.selectBySupplierName(reqSupplier.getInfo().getSupplierName());
        if (!CollectionUtils.isEmpty(supplierInfos)) {
            throw new ValidationException(HttpStatus.OK, "The vendor name provided already exists. Please try with a different vendor name");
        }
        String[] syscos = reqSupplier.getInfo().getSyscoSuvc().split(",");
        List<SupplierSyscoSuvc> supplierSyscoSuvcs = supplierSyscoSuvcMapper.selectBySyscoSuvc(syscos);
        if (!CollectionUtils.isEmpty(supplierSyscoSuvcs)) {
            throw new ValidationException(HttpStatus.OK, "The vendor number provided already exists. Please try with a different vendor number!");
        }
        String currentDateAsCST = FormatterUtils.getCurrentDateAsCST();
        String currentTimeAsCST = FormatterUtils.getCurrentTimeAsCST();
        String uuid = UUID.randomUUID().toString();

        List<SupplierSyscoSuvc> sss = new ArrayList<>();
        for (String s : syscos) {
            SupplierSyscoSuvc supplierSyscoSuvc = new SupplierSyscoSuvc();
            supplierSyscoSuvc.setSyscoSuvc(s);
            supplierSyscoSuvc.setRefUuid(uuid);
            supplierSyscoSuvc.setDeleted("N");
            sss.add(supplierSyscoSuvc);
        }

        reqSupplier.getInfo().setUuid(uuid);
        if (null != reqSupplier.getRailMaximum()) {
            reqSupplier.getInfo().setRailMaximumWeight(reqSupplier.getRailMaximum().getWeight());
            reqSupplier.getInfo().setRailMaximumCubes(reqSupplier.getRailMaximum().getCubes());
            reqSupplier.getInfo().setRailMaximumCases(reqSupplier.getRailMaximum().getCases());
            reqSupplier.getInfo().setRailMaximumPallets(reqSupplier.getRailMaximum().getPallets());
        }
        if (null != reqSupplier.getTruckMaximum()) {
            reqSupplier.getInfo().setTruckloadMaximumWeight(reqSupplier.getTruckMaximum().getWeight());
            reqSupplier.getInfo().setTruckloadMaximumCubes(reqSupplier.getTruckMaximum().getCubes());
            reqSupplier.getInfo().setTruckloadMaximumCases(reqSupplier.getTruckMaximum().getCases());
            reqSupplier.getInfo().setTruckloadMaximumPallets(reqSupplier.getTruckMaximum().getPallets());
        }
        if(null == reqSupplier.getInfo().getEffectiveDate()){
            reqSupplier.getInfo().setEffectiveDate(FormatterUtils.getNowDate(currentDateAsCST));
        }
        reqSupplier.getInfo().setCreatedBy(reqSupplier.getEmail());
        reqSupplier.getInfo().setDeleted("N");
        reqSupplier.getInfo().setCreatedTime(currentTimeAsCST);
        reqSupplier.getInfo().setCreatedDate(FormatterUtils.getNowDate(currentDateAsCST));

        if (!CollectionUtils.isEmpty(reqSupplier.getFileUrls())) {
            reqSupplier.getFileUrls().forEach(file -> file.setUuid(uuid));
        }

        if (!CollectionUtils.isEmpty(reqSupplier.getLocations())) {
            for (int i = 0; i < reqSupplier.getLocations().size(); i++) {
                reqSupplier.getLocations().get(i).setCreatedDate(FormatterUtils.getNowDate(currentDateAsCST));
                reqSupplier.getLocations().get(i).setCreatedTime(currentTimeAsCST);
                reqSupplier.getLocations().get(i).setShipPointNo(i + 1);
                reqSupplier.getLocations().get(i).setUuid(uuid);
                reqSupplier.getLocations().get(i).setDeleted("N");
            }
        }

        if (!CollectionUtils.isEmpty(reqSupplier.getContractExceptions())) {
            for (int i = 0; i < reqSupplier.getContractExceptions().size(); i++) {
                reqSupplier.getContractExceptions().get(i).setCreatedDate(FormatterUtils.getNowDate(currentDateAsCST));
                reqSupplier.getContractExceptions().get(i).setCreatedTime(currentTimeAsCST);
                reqSupplier.getContractExceptions().get(i).setSupplierContractNo(i + 1);
                reqSupplier.getContractExceptions().get(i).setUuid(uuid);
                reqSupplier.getContractExceptions().get(i).setDeleted("N");
            }
        }

        Date operTime = FormatterUtils.getNowTime(currentDateAsCST + " " + currentTimeAsCST);
        boolean flag = true;
        SupplierLog supplierLog = new SupplierLog();
        supplierLog.setOperResult("SUCCESS");
        supplierLog.setUuid(UUID.randomUUID().toString());
        supplierLog.setOperTime(operTime);
        supplierLog.setLogHeader("New Supplier Added");
        supplierLog.setLogDesc("New Supplier \"" + reqSupplier.getInfo().getSupplierName() + "\" added at " + currentDateAsCST + " " + currentTimeAsCST);
        supplierLog.setVerdorName(reqSupplier.getInfo().getSupplierName());
        supplierLog.setVerdorUuid(StringUtils.join(syscos, ","));

        try {
            supplierSyscoSuvcMapper.insertBatch(sss);

            supplierInfoMapper.insertSelective(reqSupplier.getInfo());

            if (!CollectionUtils.isEmpty(reqSupplier.getFileUrls())) {
                List<SupplierFileKey> sfk = reqSupplier.getFileUrls().stream().filter(distinctByKey(SupplierFileKey::getFileUrl))
                        .collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(sfk)) {
                    supplierFileMapper.insertBatch(sfk);
                }
            }

            if (!CollectionUtils.isEmpty(reqSupplier.getLocations())) {
                supplierLocationMapper.insertBatch(reqSupplier.getLocations());
            }
            if (!CollectionUtils.isEmpty(reqSupplier.getContractExceptions())) {
                supplierContractExceptionMapper.insertBatch(reqSupplier.getContractExceptions());
            }
        } catch (Exception e) {
            logger.info("createSupplier error: " + e);
            flag = false;
            throw new SqlException(HttpStatus.BAD_REQUEST, "createSupplier error");
        } finally {
            if (flag) {
                supplierLog.setOperResult("SUCCESS");
            } else{
                supplierLog.setOperResult("FAILED");
            }
                supplierLogMapper.insertSelective(supplierLog);
        }
        reqSupplier.getInfo().setUuid(StringUtils.join(syscos, ","));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String modifySupplier(ReqModifyVendor reqModifyVendor) {
        String uuid;
        SupplierInfo info = reqModifyVendor.getInfo();
        List<SupplierInfo> supplierInfos = supplierInfoMapper.selectBySupplierName(info.getSupplierName());
        if(CollectionUtils.isEmpty(supplierInfos)){
            throw new ValidationException(HttpStatus.OK, "supplier not exist");
        }else{
            uuid = supplierInfos.get(0).getUuid();
            info.setUuid(uuid);
            reqModifyVendor.getInfo().setBracketsUom(supplierInfos.get(0).getBracketsUom());
            reqModifyVendor.getInfo().setBracket1Header(supplierInfos.get(0).getBracket1Header());
            reqModifyVendor.getInfo().setBracket2Header(supplierInfos.get(0).getBracket2Header());
            reqModifyVendor.getInfo().setBracket3Header(supplierInfos.get(0).getBracket3Header());
            reqModifyVendor.getInfo().setBracket4Header(supplierInfos.get(0).getBracket4Header());
            reqModifyVendor.getInfo().setBracket5Header(supplierInfos.get(0).getBracket5Header());
            reqModifyVendor.getInfo().setBracket6Header(supplierInfos.get(0).getBracket6Header());
            reqModifyVendor.getInfo().setBracket7Header(supplierInfos.get(0).getBracket7Header());
            reqModifyVendor.getInfo().setBracket8Header(supplierInfos.get(0).getBracket8Header());
            if (null != reqModifyVendor.getRailMaximum()) {
                info.setRailMaximumWeight(reqModifyVendor.getRailMaximum().getWeight());
                info.setRailMaximumCubes(reqModifyVendor.getRailMaximum().getCubes());
                info.setRailMaximumCases(reqModifyVendor.getRailMaximum().getCases());
                info.setRailMaximumPallets(reqModifyVendor.getRailMaximum().getPallets());
            }
            if (null != reqModifyVendor.getTruckMaximum()) {
                info.setTruckloadMaximumWeight(reqModifyVendor.getTruckMaximum().getWeight());
                info.setTruckloadMaximumCubes(reqModifyVendor.getTruckMaximum().getCubes());
                info.setTruckloadMaximumCases(reqModifyVendor.getTruckMaximum().getCases());
                info.setTruckloadMaximumPallets(reqModifyVendor.getTruckMaximum().getPallets());
            }
        }
        List<SupplierLocation> updateLocationList = null;
        List<SupplierLocation> deleteLocationList = null;
        List<SupplierLocation> addLocationList = null;
        if(!CollectionUtils.isEmpty(reqModifyVendor.getLocations())) {
            updateLocationList = reqModifyVendor.getLocations().stream()
                    .filter(r -> "U".equals(r.getStatus()))
                    .map(r -> TransformUtils.tfVendorLocationToSup(r, uuid)).collect(Collectors.toList());
            deleteLocationList = reqModifyVendor.getLocations().stream()
                    .filter(r -> "D".equals(r.getStatus()))
                    .map(r -> TransformUtils.tfVendorLocationToSup(r, uuid)).collect(Collectors.toList());
            addLocationList = reqModifyVendor.getLocations().stream()
                    .filter(r -> "N".equals(r.getStatus()))
                    .map(r -> TransformUtils.tfVendorLocationToSup(r, uuid)).collect(Collectors.toList());
        }
        List<SupplierContractException> updateExceptionList = null;
        List<SupplierContractException> deleteExceptionList = null;
        List<SupplierContractException> addExceptionList = null;
        if(!CollectionUtils.isEmpty(reqModifyVendor.getContractExceptions())){
            updateExceptionList = reqModifyVendor.getContractExceptions().stream()
                    .filter(r -> "U".equals(r.getStatus()))
                    .map(r -> TransformUtils.tfVendorExceptionToSup(r, uuid)).collect(Collectors.toList());
            deleteExceptionList = reqModifyVendor.getContractExceptions().stream()
                    .filter(r -> "D".equals(r.getStatus()))
                    .map(r -> TransformUtils.tfVendorExceptionToSup(r, uuid)).collect(Collectors.toList());
            addExceptionList = reqModifyVendor.getContractExceptions().stream()
                    .filter(r -> "N".equals(r.getStatus()))
                    .map(r -> TransformUtils.tfVendorExceptionToSup(r, uuid)).collect(Collectors.toList());
        }
        List<SupplierFileKey> deleteFileList = null;
        List<SupplierFileKey> addFileList = null;
        if(!CollectionUtils.isEmpty(reqModifyVendor.getFileUrls())){
            deleteFileList = reqModifyVendor.getFileUrls().stream().filter(r -> "D".equals(r.getStatus()))
                    .map(r -> TransformUtils.tfVendorFileToSup(r, uuid)).collect(Collectors.toList());
            addFileList = reqModifyVendor.getFileUrls().stream().filter(r -> "N".equals(r.getStatus()))
                    .map(r -> TransformUtils.tfVendorFileToSup(r, uuid)).collect(Collectors.toList());
        }
        String currentDateAsCST = FormatterUtils.getCurrentDateAsCST();
        String currentTimeAsCST = FormatterUtils.getCurrentTimeAsCST();
        Date operTime = FormatterUtils.getNowTime(currentDateAsCST + " " + currentTimeAsCST);
        boolean flag = true;
        String supplierName = info.getSupplierName();
        String syscoSuvc = info.getSyscoSuvc();
        try{
            supplierInfoMapper.updateByPrimaryKeySelective(info);
            if(!CollectionUtils.isEmpty(addLocationList)){
                List<SupplierLocation> supplierLocations = supplierLocationMapper.selectByUuid(uuid,"");
                for (int i = 0; i < addLocationList.size(); i++) {
                    if(!CollectionUtils.isEmpty(supplierLocations)){
                        addLocationList.get(i).setShipPointNo(i + 1 + supplierLocations.size());
                    }else{
                        addLocationList.get(i).setShipPointNo(i + 1);
                    }
                }
                supplierLocationMapper.insertBatch(addLocationList);
            }
            if(!CollectionUtils.isEmpty(deleteLocationList)){
                deleteLocationList.forEach(l -> {
                    supplierLocationMapper.updateByPrimaryKeySelective(l);
                    List<SupplierPointDetail> sps = supplierPointDetailMapper.findPointsByUTS(l.getShipPointNo(), l.getUuid(), "ship_point");
                    if(!CollectionUtils.isEmpty(sps)){
                        sps.forEach(s -> supplierPointDetailMapper.deleteByPrimaryKey(s));
                    }
                });
            }
            if(!CollectionUtils.isEmpty(updateLocationList)){
                updateLocationList.forEach(l -> supplierLocationMapper.updateByPrimaryKeySelective(l));
            }
            if(!CollectionUtils.isEmpty(addExceptionList)){
                List<SupplierContractException> contractExceptions = supplierContractExceptionMapper.selectByUuid(uuid, "");
                for (int i = 0; i < addExceptionList.size(); i++) {
                    if(!CollectionUtils.isEmpty(contractExceptions)){
                        addExceptionList.get(i).setSupplierContractNo(i + 1 + contractExceptions.size());
                    }else{
                        addExceptionList.get(i).setSupplierContractNo(i + 1);
                    }
                }
                supplierContractExceptionMapper.insertBatch(addExceptionList);
            }
            if(!CollectionUtils.isEmpty(deleteExceptionList)){
                deleteExceptionList.forEach(l -> {
                    supplierContractExceptionMapper.updateByPrimaryKeySelective(l);
                    List<SupplierPointDetail> sps = supplierPointDetailMapper.findPointsByUTS(l.getSupplierContractNo(), l.getUuid(), "contract_exception");
                    if(!CollectionUtils.isEmpty(sps)){
                        sps.forEach(s -> supplierPointDetailMapper.deleteByPrimaryKey(s));
                    }
                });
            }
            if(!CollectionUtils.isEmpty(updateExceptionList)){
                updateExceptionList.forEach(l -> supplierContractExceptionMapper.updateByPrimaryKeySelective(l));
            }
            if(!CollectionUtils.isEmpty(addFileList)){
                supplierFileMapper.insertBatch(addFileList);
            }
            if(!CollectionUtils.isEmpty(deleteFileList)){
                deleteFileList.forEach(l -> {
                    deleteDocuments(reqModifyVendor.getInfo().getSupplierName(), l.getFileUrl());
                    supplierFileMapper.deleteByPrimaryKey(l);
                });
            }
        }catch (Exception e){
            logger.info("modify supplier error: " + e.getMessage());
            flag = false;
            throw new SqlException(HttpStatus.BAD_REQUEST, "modify supplier error");
        }finally{
            SupplierLog supplierLog = new SupplierLog();
            supplierLog.setUuid(UUID.randomUUID().toString());
            supplierLog.setOperTime(operTime);
            supplierLog.setLogHeader("Supplier detail update");
            supplierLog.setLogDesc("Supplier \"" + supplierName + "\" detail has been updated at " + currentDateAsCST + " " + currentTimeAsCST);
            supplierLog.setVerdorName(supplierName);
            supplierLog.setVerdorUuid(syscoSuvc);
            if (flag) {
                supplierLog.setOperResult("SUCCESS");
            }else{
                supplierLog.setOperResult("FAILED");
            }
            supplierLogMapper.insertSelective(supplierLog);
        }
        return uuid;
    }

    @Override
    public void sendModifyEmail(String uuid, ReqModifyVendor reqModifyVendor) {
        String bucket = Constants.BUCKET_NAME + Constants.FWD_SLASH + Constants.TEMPLATE;
        String keyName = Constants.TEMPLATE_EXCEL;
        S3Object object = s3Client.getObject(bucket, keyName);
        InputStream inputStream = object.getObjectContent();
        Workbook myWorkBook;
        try {
            myWorkBook = new XSSFWorkbook(inputStream);
        } catch (IOException ioe) {
            logger.warn("Caught an IOException: " + ioe.getMessage());
            throw new FRDTException(HttpStatus.INTERNAL_SERVER_ERROR, "please check excel");
        }
        List<SupplierLocation> supplierLocations = supplierLocationMapper.selectByUuid(uuid,"N");
        List<SupplierContractException> contractExceptions = supplierContractExceptionMapper.selectByUuid(uuid, "N");
        if (CollectionUtils.isEmpty(supplierLocations) && CollectionUtils.isEmpty(contractExceptions)) {
            myWorkBook.removeSheetAt(2);
        }
        initSheet(myWorkBook,reqModifyVendor.getInfo(), supplierLocations);
        if (!CollectionUtils.isEmpty(supplierLocations)) {
            Collections.sort(supplierLocations, Comparator.comparing(SupplierLocation::getShipPointNo));
            myWorkBook.getSheetAt(2).getRow(0).getCell(2).setCellValue(supplierLocations.get(0).getPhysicalAddressZone());
            myWorkBook.getSheetAt(2).getRow(3).getCell(2).setCellValue(supplierLocations.get(0).getPickupAllowanceUom());
            myWorkBook.getSheetAt(2).getRow(3).getCell(4).setCellValue(supplierLocations.get(0).getFreightRateUom());
            myWorkBook.getSheetAt(2).getRow(3).getCell(8).setCellValue(reqModifyVendor.getInfo().getBracketsUom());
            myWorkBook.getSheetAt(2).getRow(5).getCell(10).setCellValue(reqModifyVendor.getInfo().getBracket1Header());
            myWorkBook.getSheetAt(2).getRow(5).getCell(11).setCellValue(reqModifyVendor.getInfo().getBracket2Header());
            myWorkBook.getSheetAt(2).getRow(5).getCell(12).setCellValue(reqModifyVendor.getInfo().getBracket3Header());
            myWorkBook.getSheetAt(2).getRow(5).getCell(13).setCellValue(reqModifyVendor.getInfo().getBracket4Header());
            myWorkBook.getSheetAt(2).getRow(5).getCell(14).setCellValue(reqModifyVendor.getInfo().getBracket5Header());
            myWorkBook.getSheetAt(2).getRow(5).getCell(15).setCellValue(reqModifyVendor.getInfo().getBracket6Header());
            myWorkBook.getSheetAt(2).getRow(5).getCell(16).setCellValue(reqModifyVendor.getInfo().getBracket7Header());
            myWorkBook.getSheetAt(2).getRow(5).getCell(17).setCellValue(reqModifyVendor.getInfo().getBracket8Header());
            myWorkBook.getSheetAt(2).getRow(1).getCell(2).setCellValue("MM/DD/YY");
            if(null != supplierLocations.get(0).getEffectiveDate()){
                myWorkBook.getSheetAt(2).getRow(1).getCell(2).setCellValue(supplierLocations.get(0).getEffectiveDate());
            }
            for (int i = 0; i < supplierLocations.size(); i++) {
                if (i < supplierLocations.size() - 1) {
                    myWorkBook.cloneSheet(2);
                    myWorkBook.setSheetName(i + 3, "Shipping Location " + (i + 2));
                    myWorkBook.getSheetAt(i + 3).getRow(0).getCell(2).setCellValue(supplierLocations.get(i + 1).getPhysicalAddressZone());
                    myWorkBook.getSheetAt(i + 3).getRow(3).getCell(2).setCellValue(supplierLocations.get(i + 1).getPickupAllowanceUom());
                    myWorkBook.getSheetAt(i + 3).getRow(3).getCell(4).setCellValue(supplierLocations.get(i + 1).getFreightRateUom());
                    myWorkBook.getSheetAt(i + 3).getRow(1).getCell(2).setCellValue("MM/DD/YY");
                    if(null != supplierLocations.get(i + 1).getEffectiveDate()){
                        myWorkBook.getSheetAt(i + 3).getRow(1).getCell(2).setCellValue(supplierLocations.get(i + 1).getEffectiveDate());
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(contractExceptions)) {
            if(null == supplierLocations){
                supplierLocations = new ArrayList<>();
            }
            Collections.sort(contractExceptions, Comparator.comparing(SupplierContractException::getSupplierContractNo));
            for (int i = 0; i < contractExceptions.size(); i++) {
                myWorkBook.cloneSheet(2);
                try {
                    myWorkBook.setSheetName(supplierLocations.size() + 2 + i, contractExceptions.get(i).getContractException());
                } catch (Exception e) {
                    myWorkBook.setSheetName(supplierLocations.size() + 2 + i, contractExceptions.get(i).getContractException() + i);
                }
                myWorkBook.getSheetAt(i + supplierLocations.size() + 2).getRow(0).getCell(1).setCellValue("");
                myWorkBook.getSheetAt(i + supplierLocations.size() + 2).getRow(0).getCell(2).setCellValue("");
                myWorkBook.getSheetAt(i + supplierLocations.size() + 2).getRow(3).getCell(2).setCellValue(contractExceptions.get(i).getPickupAllowanceUom());
                myWorkBook.getSheetAt(i + supplierLocations.size() + 2).getRow(3).getCell(4).setCellValue(contractExceptions.get(i).getFreightRateUom());
                myWorkBook.getSheetAt(i + supplierLocations.size() + 2).getRow(3).getCell(8).setCellValue(contractExceptions.get(i).getBracketsUom());
                myWorkBook.getSheetAt(i + supplierLocations.size() + 2).getRow(5).getCell(10).setCellValue(contractExceptions.get(i).getBracket1Header());
                myWorkBook.getSheetAt(i + supplierLocations.size() + 2).getRow(5).getCell(11).setCellValue(contractExceptions.get(i).getBracket2Header());
                myWorkBook.getSheetAt(i + supplierLocations.size() + 2).getRow(5).getCell(12).setCellValue(contractExceptions.get(i).getBracket3Header());
                myWorkBook.getSheetAt(i + supplierLocations.size() + 2).getRow(5).getCell(13).setCellValue(contractExceptions.get(i).getBracket4Header());
                myWorkBook.getSheetAt(i + supplierLocations.size() + 2).getRow(5).getCell(14).setCellValue(contractExceptions.get(i).getBracket5Header());
                myWorkBook.getSheetAt(i + supplierLocations.size() + 2).getRow(5).getCell(15).setCellValue(contractExceptions.get(i).getBracket6Header());
                myWorkBook.getSheetAt(i + supplierLocations.size() + 2).getRow(5).getCell(16).setCellValue(contractExceptions.get(i).getBracket7Header());
                myWorkBook.getSheetAt(i + supplierLocations.size() + 2).getRow(5).getCell(17).setCellValue(contractExceptions.get(i).getBracket8Header());
                myWorkBook.getSheetAt(i + supplierLocations.size() + 2).getRow(1).getCell(2).setCellValue("MM/DD/YY");
                if(null != contractExceptions.get(i).getEffectiveDate()){
                    myWorkBook.getSheetAt(i + supplierLocations.size() + 2).getRow(1).getCell(2).setCellValue(contractExceptions.get(i).getEffectiveDate());
                }
            }
        }
        ShipOpcoFilterCondition condition = new ShipOpcoFilterCondition();
        condition.setUuid(uuid);
        List<ShipPointOpcoDto> shipPointOpcoDtos = supplierPointDetailMapper.selectOpcoList(condition);
        if(!CollectionUtils.isEmpty(shipPointOpcoDtos)){
            for (int numSheet = 2; numSheet < myWorkBook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = myWorkBook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                String sn = hssfSheet.getSheetName();
                String locationName = FormatterUtils.parseExcel(hssfSheet.getRow(0).getCell(2));
                for (int rowNum = 6; rowNum < 80; rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    Optional<ShipPointOpcoDto> opcoDto;
                    if (hssfRow != null) {
                        if(sn.startsWith("Shipping Location")){
                            opcoDto = shipPointOpcoDtos.stream().filter(s -> "ship_point".equals(s.getType())
                                    && FormatterUtils.parseExcel(hssfRow.getCell(0)).equals(s.getOpcoNo())
                                    && locationName.equals(s.getPhysicalAddressZone())).findAny();
                        }else {
                            opcoDto = shipPointOpcoDtos.stream().filter(s -> "contract_exception".equals(s.getType())
                                    && FormatterUtils.parseExcel(hssfRow.getCell(0)).equals(s.getOpcoNo())
                                    && sn.equals(s.getContractException())).findAny();
                        }
                        opcoDto.ifPresent(o -> {
                            if("Y".equals(o.getSyscoCurrentlyPicksup())){
                                hssfRow.getCell(5).setCellValue("Yes");
                            }
                            if("N".equals(o.getSyscoCurrentlyPicksup())){
                                hssfRow.getCell(5).setCellValue("No");
                            }
                            hssfRow.getCell(6).setCellValue(o.getDistanceInMiles().doubleValue());
                            hssfRow.getCell(7).setCellValue(o.getTempCode());
                            hssfRow.getCell(8).setCellValue(o.getPickupAllowance().doubleValue());
                            hssfRow.getCell(9).setCellValue(o.getPickupAllowanceUom());
                            hssfRow.getCell(10).setCellValue(o.getBracket1().doubleValue());
                            hssfRow.getCell(11).setCellValue(o.getBracket2().doubleValue());
                            hssfRow.getCell(12).setCellValue(o.getBracket3().doubleValue());
                            hssfRow.getCell(13).setCellValue(o.getBracket4().doubleValue());
                            hssfRow.getCell(14).setCellValue(o.getBracket5().doubleValue());
                            hssfRow.getCell(15).setCellValue(o.getBracket6().doubleValue());
                            hssfRow.getCell(16).setCellValue(o.getBracket7().doubleValue());
                            hssfRow.getCell(17).setCellValue(o.getBracket8().doubleValue());
                            hssfRow.getCell(18).setCellValue(o.getFreightRateUom());
                        });
                    }
                }
            }
        }
        sendEmail(myWorkBook, reqModifyVendor.getEmail().split(","),
                "To complete the vendor onboarding process",
                "Hello, Attached the excel template to complete the vendor onboarding process. Please fill-in the freight rates and pick up allowance details and upload back. Thanks.",
                keyName);
    }

    @Override
    public String uploadDocuments(MultipartFile file, String supplierName) {
        String bucket = Constants.BUCKET_NAME + Constants.FWD_SLASH + supplierName;
        String keyName = file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        try {
            s3Client.putObject(new PutObjectRequest(
                    bucket, keyName, file.getInputStream(), metadata));
            return keyName;
        } catch (AmazonServiceException ase) {
            logger.warn("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            throw new AWS3Exception(HttpStatus.CONFLICT, "upload file failed");
        } catch (AmazonClientException ace) {
            logger.warn("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            logger.warn("Error Message: " + ace.getMessage());
            throw new AWS3Exception(HttpStatus.INTERNAL_SERVER_ERROR, "upload file failed");
        } catch (IOException ioe) {
            logger.warn("Caught an IOException: " + ioe.getMessage());
            throw new AWS3Exception(HttpStatus.BAD_REQUEST, "upload file failed");
        }
    }

    @Override
    public String downLoadDocuments(String syscoSuvc, String fileUrl) {
        List<SupplierInfo> supplierInfos = supplierInfoMapper.selectBySupplierName(syscoSuvc);
        checkFileExist(supplierInfos, fileUrl);
        String bucket = Constants.BUCKET_NAME + Constants.FWD_SLASH + syscoSuvc;
        return AWS3Utils.getUrlFromS3(bucket, fileUrl, s3Client);
    }

    @Override
    public void deleteDocuments(String supplierName, String fileUrl) {
        List<SupplierInfo> supplierInfos = supplierInfoMapper.selectBySupplierName(supplierName);
        checkFileExist(supplierInfos, fileUrl);
        String bucket = Constants.BUCKET_NAME + Constants.FWD_SLASH + supplierName;
        String keyName = fileUrl;
        try {
            s3Client.deleteObject(new DeleteObjectRequest(bucket, keyName));
            SupplierFileKey supplierFileKey = new SupplierFileKey();
            supplierFileKey.setFileUrl(keyName);
            supplierFileKey.setUuid(supplierInfos.get(0).getUuid());
            supplierFileMapper.deleteByPrimaryKey(supplierFileKey);
        } catch (Exception e) {
            logger.info("delete Documents error: " + e);
            throw new AWS3Exception(HttpStatus.BAD_REQUEST, "delete Documents failed");
        }
    }

    public void checkFileExist(List<SupplierInfo> supplierInfos, String fileUrl){
        if (CollectionUtils.isEmpty(supplierInfos)) {
            throw new ValidationException(HttpStatus.OK, "supplier name not exists,please re-enter");
        } else {
            List<SupplierFileKey> supplierFileKeys = supplierFileMapper.selectByUuid(supplierInfos.get(0).getUuid());
            if (!supplierFileKeys.stream().anyMatch(file -> fileUrl.equals(file.getFileUrl()))) {
                throw new ValidationException(HttpStatus.OK, "supplier file not exists,please re-enter");
            }
        }
    }

    @Override
    public void sendAttachmentsMail(ReqSupplier reqSupplier) {
        String bucket = Constants.BUCKET_NAME + Constants.FWD_SLASH + Constants.TEMPLATE;
        String keyName = Constants.TEMPLATE_EXCEL;
        S3Object object = s3Client.getObject(bucket, keyName);
        InputStream inputStream = object.getObjectContent();
        Workbook myWorkBook;
        try {
            myWorkBook = new XSSFWorkbook(inputStream);
        } catch (IOException ioe) {
            logger.warn("Caught an IOException: " + ioe.getMessage());
            throw new FRDTException(HttpStatus.INTERNAL_SERVER_ERROR, "please check excel");
        }
        initSheet(myWorkBook, reqSupplier.getInfo(), reqSupplier.getLocations());
        if (CollectionUtils.isEmpty(reqSupplier.getLocations()) && CollectionUtils.isEmpty(reqSupplier.getContractExceptions())) {
            myWorkBook.removeSheetAt(2);
        }
        if (!CollectionUtils.isEmpty(reqSupplier.getLocations())) {
            myWorkBook.getSheetAt(2).getRow(0).getCell(2).setCellValue(reqSupplier.getLocations().get(0).getPhysicalAddressZone());
            for (int i = 0; i < reqSupplier.getLocations().size(); i++) {
                if (i < reqSupplier.getLocations().size() - 1) {
                    myWorkBook.cloneSheet(2);
                    myWorkBook.setSheetName(i + 3, "Shipping Location " + (i + 2));
                    myWorkBook.getSheetAt(i + 3).getRow(0).getCell(2).setCellValue(reqSupplier.getLocations().get(i + 1).getPhysicalAddressZone());
                }
            }
        }
        if (!CollectionUtils.isEmpty(reqSupplier.getContractExceptions())) {
            for (int i = 0; i < reqSupplier.getContractExceptions().size(); i++) {
                if(null ==  reqSupplier.getLocations()){
                    reqSupplier.setLocations(new ArrayList<>());
                }
                myWorkBook.cloneSheet(2);
                try {
                    myWorkBook.setSheetName(reqSupplier.getLocations().size() + 2 + i, reqSupplier.getContractExceptions().get(i).getContractException());
                } catch (Exception e) {
                    myWorkBook.setSheetName(reqSupplier.getLocations().size() + 2 + i, reqSupplier.getContractExceptions().get(i).getContractException() + i);
                }
                myWorkBook.getSheetAt(i + reqSupplier.getLocations().size() + 2).getRow(0).getCell(1).setCellValue("");
                myWorkBook.getSheetAt(i + reqSupplier.getLocations().size() + 2).getRow(0).getCell(2).setCellValue("");
            }
        }

        sendEmail(myWorkBook, reqSupplier.getEmail().split(","),
                "To complete the vendor onboarding process",
                "Hello, Attached the excel template to complete the vendor onboarding process. Please fill-in the freight rates and pick up allowance details and upload back. Thanks.",
                keyName);
    }

    public void initSheet(Workbook myWorkBook, SupplierInfo info, List<SupplierLocation> locations){
        Sheet sheet0 = myWorkBook.getSheetAt(1);
        sheet0.getRow(3).getCell(8).setCellValue(info.getEffectiveDate());
        sheet0.getRow(4).getCell(3).setCellValue(info.getSupplierName());
        sheet0.getRow(5).getCell(3).setCellValue(info.getCorporateAddress());
        sheet0.getRow(5).getCell(5).setCellValue(info.getGeneralSupplierComments());
        sheet0.getRow(6).getCell(3).setCellValue(info.getSupplierChainContact());
        sheet0.getRow(7).getCell(3).setCellValue(info.getPhone());
        sheet0.getRow(8).getCell(3).setCellValue(info.getEmail());
        CellStyle cellStyle = myWorkBook.createCellStyle();
        DataFormat dataFormat = myWorkBook.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("@"));
        sheet0.getRow(9).getCell(3).setCellStyle(cellStyle);
        sheet0.getRow(9).getCell(3).setCellValue(info.getSyscoSuvc());
        sheet0.getRow(10).getCell(13).setCellValue(0);
        sheet0.getRow(10).getCell(14).setCellValue(0);
        sheet0.getRow(10).getCell(15).setCellValue(0);
        if(StringUtils.isNotBlank(info.getTemperatureCode())){
            if(info.getTemperatureCode().contains("C")){
                sheet0.getRow(10).getCell(13).setCellValue(1);
            }
            if(info.getTemperatureCode().contains("F")){
                sheet0.getRow(10).getCell(14).setCellValue(1);
            }
            if(info.getTemperatureCode().contains("D")){
                sheet0.getRow(10).getCell(15).setCellValue(1);
            }
        }
        sheet0.getRow(11).getCell(3).setCellValue(info.getBidCategory());
        sheet0.getRow(15).getCell(6).setCellValue(info.getProductCosting());
        if("Y".equalsIgnoreCase(info.getLowestProductCost())){
            sheet0.getRow(16).getCell(6).setCellValue("Yes");
        } else if("N".equalsIgnoreCase(info.getLowestProductCost())) {
            sheet0.getRow(16).getCell(6).setCellValue("No");
        } else {
            sheet0.getRow(16).getCell(6).setCellValue(info.getLowestProductCost());
        }

        sheet0.getRow(17).getCell(6).setCellValue(info.getProductUom());
        sheet0.getRow(19).getCell(6).setCellValue(info.getFreightUom());
        if("Case".equalsIgnoreCase(info.getFreightUom())) {
            sheet0.getRow(25).getCell(7).setCellValue("Cases");
            sheet0.getRow(26).getCell(7).setCellValue("Cases");
        } else if ("CWT".equalsIgnoreCase(info.getFreightUom()) || "NWT".equalsIgnoreCase(info.getFreightUom())){
            sheet0.getRow(25).getCell(7).setCellValue("LBS");
            sheet0.getRow(26).getCell(7).setCellValue("LBS");
        } else if ("Cube".equalsIgnoreCase(info.getFreightUom())){
            sheet0.getRow(25).getCell(7).setCellValue("Cubes");
            sheet0.getRow(26).getCell(7).setCellValue("Cubes");
        }
        sheet0.getRow(20).getCell(6).setCellValue(info.getPublishedFreightRates());
        if("Y".equalsIgnoreCase(info.getPickupAllowances())){
            sheet0.getRow(21).getCell(6).setCellValue("Yes");
        }else {
            sheet0.getRow(21).getCell(6).setCellValue("N0");
        }
        if("Y".equalsIgnoreCase(info.getFreightDifferent())){
            sheet0.getRow(22).getCell(6).setCellValue("Yes");
        }else {
            sheet0.getRow(22).getCell(6).setCellValue("No");
        }
        sheet0.getRow(25).getCell(6).setCellValue(info.getMinimumDelivery());
        sheet0.getRow(26).getCell(6).setCellValue(info.getMinimumPickup());
        if("Y".equalsIgnoreCase(info.getMinimumDifferent())){
            sheet0.getRow(27).getCell(6).setCellValue("Yes");
        }else {
            sheet0.getRow(27).getCell(6).setCellValue("No");
        }
        sheet0.getRow(30).getCell(2).setCellValue(info.getSupplierCommentsFreight());
        sheet0.getRow(39).getCell(6).setCellValue(info.getDeliveryPickupPeriod());
        sheet0.getRow(40).getCell(6).setCellValue(info.getFuelSurchargePeriod());
        if("Y".equalsIgnoreCase(info.getAdditionalFees())){
            sheet0.getRow(41).getCell(6).setCellValue("Yes");
        }else {
            sheet0.getRow(41).getCell(6).setCellValue("No");
        }
        if("Y".equalsIgnoreCase(info.getComplianceShipperCount())){
            sheet0.getRow(42).getCell(6).setCellValue("Yes");
        }else {
            sheet0.getRow(42).getCell(6).setCellValue("No");
        }
        sheet0.getRow(43).getCell(6).setCellValue(info.getCurrentPalletProgram());
        sheet0.getRow(49).getCell(2).setCellValue(info.getSupplierCommentsProfile());
        if(null != info.getTruckloadMaximumWeight()){
            sheet0.getRow(45).getCell(6).setCellValue(info.getTruckloadMaximumWeight().toString());
        }
        if(null != info.getTruckloadMaximumCubes()){
            sheet0.getRow(45).getCell(7).setCellValue(info.getTruckloadMaximumCubes().toString());
        }
        if(null != info.getTruckloadMaximumCases()){
            sheet0.getRow(45).getCell(8).setCellValue(info.getTruckloadMaximumCases().toString());
        }
        if(null != info.getTruckloadMaximumPallets()){
            sheet0.getRow(45).getCell(10).setCellValue(info.getTruckloadMaximumPallets().toString());
        }
        if(null != info.getRailMaximumWeight()){
            sheet0.getRow(46).getCell(6).setCellValue(info.getRailMaximumWeight().toString());
        }
        if(null != info.getRailMaximumCubes()){
            sheet0.getRow(46).getCell(7).setCellValue(info.getRailMaximumCubes().toString());
        }
        if(null != info.getRailMaximumCases()){
            sheet0.getRow(46).getCell(8).setCellValue(info.getRailMaximumCases().toString());
        }
        if(null != info.getRailMaximumPallets()){
            sheet0.getRow(46).getCell(10).setCellValue(info.getRailMaximumPallets().toString());
        }
        for(int i=0;i<locations.size();i++){
            sheet0.getRow(59+i).getCell(3).setCellValue(locations.get(i).getPhysicalAddressZone());
            sheet0.getRow(59+i).getCell(4).setCellValue(locations.get(i).getCity());
            sheet0.getRow(59+i).getCell(5).setCellValue(locations.get(i).getState());
            sheet0.getRow(59+i).getCell(6).setCellValue(locations.get(i).getZipCode());
            sheet0.getRow(59+i).getCell(7).setCellValue(locations.get(i).getDcPlant());
            sheet0.getRow(59+i).getCell(8).setCellValue(locations.get(i).getSourcedProduct());
            sheet0.getRow(59+i).getCell(10).setCellValue(locations.get(i).getRailFacilities());
            sheet0.getRow(59+i).getCell(11).setCellValue(locations.get(i).getDropTrailer());
        }
    }

    public void sendEmail(Workbook myWorkBook, String[] tos, String subject, String text, String keyName){
        InputStreamSource iss;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream(1000);
            myWorkBook.write(os);
            myWorkBook.close();

            iss = new ByteArrayResource(os.toByteArray());
            os.close();
        } catch (IOException e) {
            logger.info("workbook to stream error" + e.getMessage());
            throw new FRDTException(HttpStatus.INTERNAL_SERVER_ERROR, "please check excel");
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailFrom);
            helper.setTo(tos);
            helper.setSubject(subject);
            helper.setText(text, true);
            helper.addAttachment(keyName, iss);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            logger.info("send email error: " + e.getMessage());
            throw new FRDTException(HttpStatus.INTERNAL_SERVER_ERROR, "send email error");
        }
    }

    @Override
    public GenericResponse fetchSCSSupplier(SCSFilterCondition scsFilterCondition) {
        GenericResponse genericResponse = new GenericResponse();
        Map<String, Object> map = new HashMap<>();

        Integer pageNum = scsFilterCondition.getPageNum();
        Integer pageSize = scsFilterCondition.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        String lastUploadedDateBegin = scsFilterCondition.getLastUploadedDateBegin();
        String lastUploadedDateEnd = scsFilterCondition.getLastUploadedDateEnd();
        String lastDateOfFullReviewBegin = scsFilterCondition.getLastDateOfFullReviewBegin();
        String lastDateOfFullReviewEnd = scsFilterCondition.getLastDateOfFullReviewEnd();
        String nextReviewDueDateBegin = scsFilterCondition.getNextReviewDueDateBegin();
        String nextReviewDueDateEnd = scsFilterCondition.getNextReviewDueDateEnd();
        try {
            if(lastUploadedDateBegin!=null && !" ".equalsIgnoreCase(lastUploadedDateBegin)) {
                scsFilterCondition.setLastUploadedDateBegin(FormatterUtils.strToStrFormat(lastUploadedDateBegin));
            }
            if(lastUploadedDateBegin!=null && !" ".equalsIgnoreCase(lastUploadedDateBegin)) {
                scsFilterCondition.setLastUploadedDateEnd(FormatterUtils.strToStrFormat(lastUploadedDateEnd));
            }
            if(lastDateOfFullReviewBegin!=null && !" ".equalsIgnoreCase(lastDateOfFullReviewBegin)){
                scsFilterCondition.setLastDateOfFullReviewBegin(FormatterUtils.strToStrFormat(lastDateOfFullReviewBegin));
            }
            if(lastDateOfFullReviewEnd!=null && !" ".equalsIgnoreCase(lastDateOfFullReviewEnd)){
                scsFilterCondition.setLastDateOfFullReviewEnd(FormatterUtils.strToStrFormat(lastDateOfFullReviewEnd));
            }
            if(nextReviewDueDateBegin!=null && !" ".equalsIgnoreCase(nextReviewDueDateBegin)){
                scsFilterCondition.setNextReviewDueDateBegin(FormatterUtils.strToStrFormat(nextReviewDueDateBegin));
            }
            if(nextReviewDueDateEnd!=null && !" ".equalsIgnoreCase(nextReviewDueDateEnd)){
                scsFilterCondition.setNextReviewDueDateEnd(FormatterUtils.strToStrFormat(nextReviewDueDateEnd));
            }

        } catch (ParseException e){
            logger.info(e.getMessage());
        }
        List<SCSSuppliersInfo> record = supplierInfoMapper.selectSCSSupplier(scsFilterCondition);

        if (!CollectionUtils.isEmpty(record)) {
            for (SCSSuppliersInfo scsSuppliersInfo : record) {
                String vendorName = scsSuppliersInfo.getVendorName();
                List<Documents> documents = supplierInfoMapper.fetchDocumentsBySupplierName(vendorName);
                scsSuppliersInfo.setDocDetails(documents);
            }
        }
        PageInfo<SCSSuppliersInfo> pageInfo = new PageInfo<>(record);
        map.put("totalNumOfSCSSuppliers", pageInfo.getTotal());
        genericResponse.setRows(pageInfo.getList());
        genericResponse.setMapData(map);
        genericResponse.setResult(true);
        return genericResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDateOfReview(DateOfReview dateOfReview) {
        boolean result = false;
        if (dateOfReview.getSupplierName() != null) {
            List<SupplierInfo> supplierInfos = supplierInfoMapper.selectBySupplierName(dateOfReview.getSupplierName());
            if (CollectionUtils.isEmpty(supplierInfos)) {
            } else {
                String currentDateAsCST = FormatterUtils.getCurrentDateAsCST();
                String currentTimeAsCST = FormatterUtils.getCurrentTimeAsCST();
                Date operTime = FormatterUtils.getNowTime(currentDateAsCST + " " + currentTimeAsCST);
                boolean flag = true;
                SupplierLog supplierLog = new SupplierLog();
                supplierLog.setUuid(UUID.randomUUID().toString());
                supplierLog.setOperTime(operTime);
                supplierLog.setLogHeader("Supplier review updated");
                if(StringUtils.isNotBlank(dateOfReview.getLastDateOfFullReview()) && StringUtils.isBlank(dateOfReview.getNextReviewDueDate())){
                    supplierLog.setLogDesc("Supplier \"" + dateOfReview.getSupplierName() + "\" LastDateOfFullReview updated at " + currentDateAsCST + " " + currentTimeAsCST);
                }
                if(StringUtils.isBlank(dateOfReview.getLastDateOfFullReview()) && StringUtils.isNotBlank(dateOfReview.getNextReviewDueDate())){
                    supplierLog.setLogDesc("Supplier \"" + dateOfReview.getSupplierName() + "\" NextReviewDueDate updated at " + currentDateAsCST + " " + currentTimeAsCST);
                }
                if(StringUtils.isNotBlank(dateOfReview.getLastDateOfFullReview()) && StringUtils.isNotBlank(dateOfReview.getNextReviewDueDate())){
                    supplierLog.setLogDesc("Supplier \"" + dateOfReview.getSupplierName() + "\" LastDateOfFullReview and NextReviewDueDate updated at " + currentDateAsCST + " " + currentTimeAsCST);
                }
                supplierLog.setVerdorName(dateOfReview.getSupplierName());
                String collect = supplierInfos.stream().map(s -> s.getUuid()).collect(Collectors.joining(","));
                supplierLog.setVerdorUuid(collect);
                try {
                    for (SupplierInfo supplierInfo : supplierInfos) {
                        if (StringUtils.isNotBlank(dateOfReview.getLastDateOfFullReview())) {
                            supplierInfo.setLastDateOfFullReview(FormatterUtils.getReviewDate(dateOfReview.getLastDateOfFullReview()));
                        }
                        if (StringUtils.isNotBlank(dateOfReview.getNextReviewDueDate())) {
                            supplierInfo.setNextReviewDueDate(FormatterUtils.getReviewDate(dateOfReview.getNextReviewDueDate()));
                        }
                        supplierInfoMapper.updateByPrimaryKeySelective(supplierInfo);
                        result = true;
                    }
                } catch (Exception e) {
                    logger.info(dateOfReview.getSupplierName() + "updateDateOfReview error: " + e);
                    flag = false;
                } finally {
                    if(flag) {
                        supplierLog.setOperResult("SUCCESS");
                    }else{
                        supplierLog.setOperResult("FAILED");
                    }
                        supplierLogMapper.insertSelective(supplierLog);
                }
            }
        }
        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String upLoadSupplierTemp(MultipartFile file, String email) {
        Workbook hssfWorkbook;
        StringBuffer errorMsg = new StringBuffer();
        try {
            InputStream is = file.getInputStream();
            String fileName = file.getOriginalFilename();
            if (fileName.endsWith("xlsx")) {
                //Excel 2007
                hssfWorkbook = new XSSFWorkbook(is);
            } else {
                throw new ValidationException(HttpStatus.OK, "The file format is incorrect");
            }
        } catch (IOException e) {
            logger.info("File parsing failed:" + e.getMessage());
            throw new ValidationException(HttpStatus.OK, "File parsing failed");
        }
        if (null != hssfWorkbook) {
            Sheet init = hssfWorkbook.getSheetAt(1);
            String syscoSuvc = FormatterUtils.parseExcel(init.getRow(9).getCell(3));
            String supplierName = FormatterUtils.parseExcel(init.getRow(4).getCell(3));
            String bh1 = FormatterUtils.parseExcel(hssfWorkbook.getSheetAt(2).getRow(5).getCell(10));
            String bh2 = FormatterUtils.parseExcel(hssfWorkbook.getSheetAt(2).getRow(5).getCell(11));
            String bh3 = FormatterUtils.parseExcel(hssfWorkbook.getSheetAt(2).getRow(5).getCell(12));
            String bh4 = FormatterUtils.parseExcel(hssfWorkbook.getSheetAt(2).getRow(5).getCell(13));
            String bh5 = FormatterUtils.parseExcel(hssfWorkbook.getSheetAt(2).getRow(5).getCell(14));
            String bh6 = FormatterUtils.parseExcel(hssfWorkbook.getSheetAt(2).getRow(5).getCell(15));
            String bh7 = FormatterUtils.parseExcel(hssfWorkbook.getSheetAt(2).getRow(5).getCell(16));
            String bh8 = FormatterUtils.parseExcel(hssfWorkbook.getSheetAt(2).getRow(5).getCell(17));
            String bracketUom = FormatterUtils.parseExcel(hssfWorkbook.getSheetAt(2).getRow(3).getCell(8));
            List<SupplierInfo> supplierInfos = supplierInfoMapper.selectBySupplierName(supplierName);
            if (CollectionUtils.isEmpty(supplierInfos)) {
                throw new ValidationException(HttpStatus.OK, "Supplier name does not exist");
            }
            List<SupplierSyscoSuvc> suvcs = supplierSyscoSuvcMapper.selectBySyscoSuvc(syscoSuvc.split(","));
            if(CollectionUtils.isEmpty(suvcs)){
                throw new ValidationException(HttpStatus.OK, "Supplier sysyco suvc does not exist");
            }
            final String uuid = supplierInfos.get(0).getUuid();
            suvcs.forEach(s -> {
                if(!uuid.equals(s.getRefUuid())){
                    throw new ValidationException(HttpStatus.OK, "Supplier does not match");
                }
                if(!syscoSuvc.contains(s.getSyscoSuvc())){
                    throw new ValidationException(HttpStatus.OK, "Supplier does not match");
                }
            });


            List<SupplierPointDetail> spds = supplierPointDetailMapper.selectBySupplierName(supplierName);
            if (!CollectionUtils.isEmpty(spds)) {
                spds.forEach(sp -> supplierPointDetailMapper.deleteByPrimaryKey(sp));
            }

            String currentDateAsCST = FormatterUtils.getCurrentDateAsCST();
            String currentTimeAsCST = FormatterUtils.getCurrentTimeAsCST();
            Date operTime = FormatterUtils.getNowTime(currentDateAsCST + " " + currentTimeAsCST);
            boolean flag = true;
            List<SupplierPointDetail> spfs = new ArrayList<>();
            List<SupplierLocation> sls = new ArrayList<>();
            List<SupplierContractException> sces = new ArrayList<>();
            int sNum = 0;
            for (int numSheet = 2; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                String sn = hssfSheet.getSheetName();
                String paUom = FormatterUtils.parseExcel(hssfSheet.getRow(3).getCell(2));
                String frUom = FormatterUtils.parseExcel(hssfSheet.getRow(3).getCell(4));
                String bracUom = FormatterUtils.parseExcel(hssfSheet.getRow(3).getCell(8));
                String dt = FormatterUtils.parseExcel(hssfSheet.getRow(1).getCell(2));
                String effectiveDate;
                try {
                    effectiveDate = FormatterUtils.strToDateFormat(dt);
                } catch (ParseException e) {
                    errorMsg.append("<tr><td>").append(sn).append("</td><td>")
                            .append(2).append("</td><td>")
                            .append("effectiveDate Incorrect date format (MM/dd/yy)")
                            .append("</td></tr>");
                    continue;
                }
                if (StringUtils.isBlank(paUom) || StringUtils.isBlank(frUom) || StringUtils.isBlank(bracUom)) {
                    errorMsg.append("<tr><td>").append(sn).append("</td><td>")
                            .append(4).append("</td><td>")
                            .append("Pickup allowance UOM , Freight Rate UOM, Brackets UOM must have value")
                            .append("</td></tr>");
                    continue;
                }
                Integer shipNo = null;
                Integer contractNo = null;
                if(sn.startsWith("Shipping Location")){
                    SupplierLocation sl = new SupplierLocation();
                    String addressZone = FormatterUtils.parseExcel(hssfSheet.getRow(0).getCell(2));
                    SupplierLocation supplierLocations = supplierLocationMapper.selectByAddressZone(uuid, addressZone);
                    shipNo = supplierLocations.getShipPointNo();
                    sl.setPickupAllowanceUom(paUom);
                    sl.setFreightRateUom(frUom);
                    sl.setShipPointNo(shipNo);
                    if (StringUtils.isNotBlank(effectiveDate)) {
                        sl.setEffectiveDate(FormatterUtils.getNowDate(effectiveDate));
                    }
                    sls.add(sl);
                } else {
                    SupplierContractException sce = new SupplierContractException();
                    SupplierContractException supplierContractExceptions = supplierContractExceptionMapper.selectByContractException(uuid, sn);
                    contractNo = supplierContractExceptions.getSupplierContractNo();
                    String b1 = FormatterUtils.parseExcel(hssfSheet.getRow(5).getCell(10));
                    String b2 = FormatterUtils.parseExcel(hssfSheet.getRow(5).getCell(11));
                    String b3 = FormatterUtils.parseExcel(hssfSheet.getRow(5).getCell(12));
                    String b4 = FormatterUtils.parseExcel(hssfSheet.getRow(5).getCell(13));
                    String b5 = FormatterUtils.parseExcel(hssfSheet.getRow(5).getCell(14));
                    String b6 = FormatterUtils.parseExcel(hssfSheet.getRow(5).getCell(15));
                    String b7 = FormatterUtils.parseExcel(hssfSheet.getRow(5).getCell(16));
                    String b8 = FormatterUtils.parseExcel(hssfSheet.getRow(5).getCell(17));
                    sce.setBracket1Header(b1);
                    sce.setBracket2Header(b2);
                    sce.setBracket3Header(b3);
                    sce.setBracket4Header(b4);
                    sce.setBracket5Header(b5);
                    sce.setBracket6Header(b6);
                    sce.setBracket7Header(b7);
                    sce.setBracket8Header(b8);
                    sce.setPickupAllowanceUom(paUom);
                    sce.setFreightRateUom(frUom);
                    sce.setBracketsUom(bracUom);
                    sce.setSupplierContractNo(contractNo);
                    if (StringUtils.isNotBlank(effectiveDate)) {
                        sce.setEffectiveDate(FormatterUtils.getNowDate(effectiveDate));
                    }
                    sces.add(sce);
                }

                for (int rowNum = 6; rowNum < 80; rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        //empty row
                        if (StringUtils.isBlank(FormatterUtils.parseExcel(hssfRow.getCell(5))) && StringUtils.isBlank(FormatterUtils.parseExcel(hssfRow.getCell(6)))
                                && StringUtils.isBlank(FormatterUtils.parseExcel(hssfRow.getCell(7))) && StringUtils.isBlank(FormatterUtils.parseExcel(hssfRow.getCell(8)))
                                && StringUtils.isBlank(FormatterUtils.parseExcel(hssfRow.getCell(9))) && StringUtils.isBlank(FormatterUtils.parseExcel(hssfRow.getCell(10)))
                                && StringUtils.isBlank(FormatterUtils.parseExcel(hssfRow.getCell(11))) && StringUtils.isBlank(FormatterUtils.parseExcel(hssfRow.getCell(12)))
                                && StringUtils.isBlank(FormatterUtils.parseExcel(hssfRow.getCell(13))) && StringUtils.isBlank(FormatterUtils.parseExcel(hssfRow.getCell(14)))
                                && StringUtils.isBlank(FormatterUtils.parseExcel(hssfRow.getCell(15))) && StringUtils.isBlank(FormatterUtils.parseExcel(hssfRow.getCell(16)))
                                && StringUtils.isBlank(FormatterUtils.parseExcel(hssfRow.getCell(17))) && StringUtils.isBlank(FormatterUtils.parseExcel(hssfRow.getCell(18)))) {
                            continue;
                        }
                        sNum++;
                        SupplierPointDetail spd = new SupplierPointDetail();
                        spd.setOpcoNo(FormatterUtils.parseExcel(hssfRow.getCell(0)));
                        spd.setOpcoName(FormatterUtils.parseExcel(hssfRow.getCell(1)));
                        spd.setCity(FormatterUtils.parseExcel(hssfRow.getCell(2)));
                        spd.setState(FormatterUtils.parseExcel(hssfRow.getCell(3)));
                        spd.setZipCode(FormatterUtils.parseExcel(hssfRow.getCell(4)));
                        if ("Yes".equals(FormatterUtils.parseExcel(hssfRow.getCell(5)))) {
                            spd.setSyscoCurrentlyPicksup("Y");
                        } else {
                            spd.setSyscoCurrentlyPicksup("N");
                        }
                        spd.setDistanceInMiles(FormatterUtils.formatterBigDeci(FormatterUtils.parseExcel(hssfRow.getCell(6))));
                        spd.setTempCode(FormatterUtils.parseExcel(hssfRow.getCell(7)));
                        spd.setPickupAllowance(FormatterUtils.formatterBigDeci(FormatterUtils.parseExcel(hssfRow.getCell(8))));
                        if (StringUtils.isNotBlank(FormatterUtils.parseExcel(hssfRow.getCell(9)))) {
                            spd.setPickupAllowanceUom(FormatterUtils.parseExcel(hssfRow.getCell(9)));
                        } else {
                            spd.setPickupAllowanceUom(paUom);
                        }
                        spd.setBracket1(FormatterUtils.formatterBigDeci(FormatterUtils.parseExcel(hssfRow.getCell(10))));
                        spd.setBracket2(FormatterUtils.formatterBigDeci(FormatterUtils.parseExcel(hssfRow.getCell(11))));
                        spd.setBracket3(FormatterUtils.formatterBigDeci(FormatterUtils.parseExcel(hssfRow.getCell(12))));
                        spd.setBracket4(FormatterUtils.formatterBigDeci(FormatterUtils.parseExcel(hssfRow.getCell(13))));
                        spd.setBracket5(FormatterUtils.formatterBigDeci(FormatterUtils.parseExcel(hssfRow.getCell(14))));
                        spd.setBracket6(FormatterUtils.formatterBigDeci(FormatterUtils.parseExcel(hssfRow.getCell(15))));
                        spd.setBracket7(FormatterUtils.formatterBigDeci(FormatterUtils.parseExcel(hssfRow.getCell(16))));
                        spd.setBracket8(FormatterUtils.formatterBigDeci(FormatterUtils.parseExcel(hssfRow.getCell(17))));
                        if (StringUtils.isNotBlank(FormatterUtils.parseExcel(hssfRow.getCell(18)))) {
                            spd.setFreightRateUom(FormatterUtils.parseExcel(hssfRow.getCell(18)));
                        } else {
                            spd.setFreightRateUom(frUom);
                        }
                        spd.setShipPointNo(shipNo);
                        spd.setCreatedTime(currentTimeAsCST);
                        spd.setCreatedDate(FormatterUtils.getNowDate(currentDateAsCST));
                        spfs.add(spd);
                        //contract exception
                        if (!sn.startsWith("Shipping Location")) {
                            spd.setShipPointNo(contractNo);
                            spd.setType("contract_exception");
                        } else {
                            spd.setShipPointNo(shipNo);
                            spd.setType("ship_point");
                        }
                    }
                }
            }
            try {
                for (SupplierContractException sce : sces) {
                    sce.setUuid(uuid);
                }
                if (!CollectionUtils.isEmpty(spfs)) {
                    spfs.forEach(spf -> spf.setUuid(uuid));
                    supplierPointDetailMapper.insertBatch(spfs);
                }
                if (!CollectionUtils.isEmpty(sls)) {
                    sls.forEach(sl -> {
                        sl.setUuid(uuid);
                        supplierLocationMapper.updateByPrimaryKeySelective(sl);
                    });
                }
                if (!CollectionUtils.isEmpty(sces)) {
                    sces.forEach(sc -> {
                        sc.setUuid(uuid);
                        supplierContractExceptionMapper.updateByPrimaryKeySelective(sc);
                    });
                }
                SupplierInfo info = new SupplierInfo();
                info.setUuid(uuid);
                info.setLastUploadedDate(FormatterUtils.getNowDate(currentDateAsCST));
                info.setBracket1Header(bh1);
                info.setBracket2Header(bh2);
                info.setBracket3Header(bh3);
                info.setBracket4Header(bh4);
                info.setBracket5Header(bh5);
                info.setBracket6Header(bh6);
                info.setBracket7Header(bh7);
                info.setBracket8Header(bh8);
                info.setBracketsUom(bracketUom);
                supplierInfoMapper.updateByPrimaryKeySelective(info);

            } catch (Exception e) {
                logger.info("upload template error: " + e.getMessage());
                flag = false;
                throw new SqlException(HttpStatus.INTERNAL_SERVER_ERROR, "upload template error");
            } finally {
                SupplierLog supplierLog = new SupplierLog();
                supplierLog.setUuid(UUID.randomUUID().toString());
                supplierLog.setOperTime(operTime);
                supplierLog.setLogHeader("Supplier template uploaded");
                supplierLog.setLogDesc("Supplier \"" + supplierName + "\" freight rates uploaded at " + currentDateAsCST + " " + currentTimeAsCST);
                supplierLog.setVerdorName(supplierName);
                supplierLog.setVerdorUuid(syscoSuvc);
                if (flag) {
                    supplierLog.setOperResult("SUCCESS");
                }else{
                    supplierLog.setOperResult("FAILED");
                }
                supplierLogMapper.insertSelective(supplierLog);
            }
            MimeMessage message = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(mailFrom);
                helper.setTo(email);
                helper.setSubject("Import Feedback");
                String html;
                StringBuffer text = new StringBuffer();
                text.append("Hello:").append("<br>").append("File :").append(file.getOriginalFilename()).append("<br>")
                        .append("Number of Import Success Records :").append(sNum).append("<br>")
                        .append("Imported Success!");
                if (StringUtils.isNotBlank(errorMsg.toString())) {
                    errorMsg.insert(0, "<html><head><title></title></head><body><table border=\"1\"><tr><td>SheetName</td><td>RowNum</td><td>errorMessage</td></tr>");
                    errorMsg.append("</table></body></html>");
                    text.append("Please check invalid records.").append("<br>");
                    html = text.toString() + errorMsg.toString();
                } else {
                    html = text.toString();
                }
                helper.setText(html, true);
                javaMailSender.send(message);
            } catch (MessagingException e) {
                logger.info("send email error: " + e.getMessage());
                throw new FRDTException(HttpStatus.INTERNAL_SERVER_ERROR, "send email error");
            }
        }
        return errorMsg.toString();

    }

    @Override
    public List<ShipPointOpcoDto> selectOpcoList(ShipOpcoFilterCondition shipOpcoFilterCondition) {
        if(StringUtils.isNotBlank(shipOpcoFilterCondition.getOpcoNo())){
            String[] split = shipOpcoFilterCondition.getOpcoNo().split(",");
            shipOpcoFilterCondition.setOpcoList(split);
        }
        Integer pageNum = shipOpcoFilterCondition.getPageNum();
        Integer pageSize = shipOpcoFilterCondition.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        List<ShipPointOpcoDto> shipPointOpcoDtos = supplierPointDetailMapper.selectOpcoList(shipOpcoFilterCondition);
        String currentDateAsCST = FormatterUtils.getCurrentDateAsCST();
        Date today = FormatterUtils.getNowDate(currentDateAsCST);
        shipPointOpcoDtos.forEach(s -> {
            if(StringUtils.isNotBlank(s.getFreightUpdateUuid())){
                List<FreightUpdateDetail> fuds = freightUpdateDetailMapper.selectByUuid(s.getFreightUpdateUuid());
                fuds.sort(Comparator.comparing(FreightUpdateDetail::getEffectiveDate).reversed());
                Optional<FreightUpdateDetail> first = fuds.stream().filter(f -> today.compareTo(f.getEffectiveDate()) == 0 || today.compareTo(f.getEffectiveDate()) > 0).findFirst();
                first.ifPresent(fud -> {
                    s.setBracketValue(fud.getBracketValue());
                    s.setBracketType(fud.getBracketType());
                    s.setFreightEffectiveDate(fud.getEffectiveDate());
                    s.setPuPercentage(fud.getPuPercent());
                    s.setUpdatePickupAllowance(fud.getPickupAllowance());
                    s.setSurcharge(fud.getSurcharge());
                });
            }
        });
        return shipPointOpcoDtos;
    }

    @Override
    public VendorInfo getVendorInfoById(String vendorId) {
        if(StringUtils.isNotBlank(vendorId)) {

            List<VendorDetails> vendorInfoList = supplierInfoMapper.getVendorOpcoInfoById(vendorId);
            List<VendorContractException> vendorContractExceptionList = supplierInfoMapper.getVendorContractExceptionById(vendorId);
            for (VendorContractException vendorContractException :vendorContractExceptionList){
                vendorContractException.setSyscoSuvc(vendorId);
            }
            List<VendorLocation> vendorLocationList = supplierInfoMapper.getVendorLocationById(vendorId);
            for (VendorLocation vendorLocation : vendorLocationList){
                vendorLocation.setSyscoSuvc(vendorId);
            }
            List<VendorFileUrl> vendorFileUrlList = supplierInfoMapper.getVendorFileUrlById(vendorId);
            for (VendorFileUrl vendorFileUrl : vendorFileUrlList){
                vendorFileUrl.setSyscoSuvc(vendorId);
            }

            VendorInfo vendorInfo;
            if (!CollectionUtils.isEmpty(vendorInfoList)) {
                vendorInfo = TransformUtils.transformVendorInfo(vendorInfoList);
                vendorInfo.setVendorNumber(vendorId);
                vendorInfo.setContractExceptions(vendorContractExceptionList);
                vendorInfo.setLocations(vendorLocationList);
                vendorInfo.setFileUrls(vendorFileUrlList);
                return vendorInfo;
            }else{
                throw new ValidationException(HttpStatus.NOT_FOUND,"vendor does not exist.");
            }
        }else {
            throw new ValidationException(HttpStatus.NOT_FOUND,"vendorId should not be null.");
        }
    }

    @Override
    public List<SupplierLog> selectLogList() {
        List<SupplierLog> supplierLogs = supplierLogMapper.selectLogList();
        if(!CollectionUtils.isEmpty(supplierLogs)){
            supplierLogs.sort(Comparator.comparing(SupplierLog::getOperTime).reversed());
            return supplierLogs;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFreightRate(ReqUpdateFreightRate updateFreightRate) {
        List<SupplierSyscoSuvc> supplierSyscoSuvcs = supplierSyscoSuvcMapper.selectBySyscoSuvc(updateFreightRate.getVendorNumber().split(","));
        if (CollectionUtils.isEmpty(supplierSyscoSuvcs)) {
            throw new ValidationException(HttpStatus.OK, "Supplier sysco suvc does not exist");
        }
        String uuid = supplierSyscoSuvcs.get(0).getRefUuid();
        String currentDateAsCST = FormatterUtils.getCurrentDateAsCST();
        String currentTimeAsCST = FormatterUtils.getCurrentTimeAsCST();
        Date operTime = FormatterUtils.getNowTime(currentDateAsCST + " " + currentTimeAsCST);
        String supplierName = updateFreightRate.getVendorName();
        String syscoSuvc = updateFreightRate.getVendorNumber();
        boolean flag = true;
        try {
            if (!CollectionUtils.isEmpty(updateFreightRate.getShipPoints())) {
                updateFreightRate.getShipPoints().forEach(point -> updateFreight(point, "ship_point", uuid, currentDateAsCST, currentTimeAsCST));
            }
            if(!CollectionUtils.isEmpty(updateFreightRate.getContractExceptions())){
                updateFreightRate.getContractExceptions()
                        .forEach(exception -> exception.getContractException()
                                .forEach(point -> updateFreight(point,"contract_exception", uuid, currentDateAsCST, currentTimeAsCST)));
            }
        }catch(Exception e){
            logger.info("freight rate update error: " + e.getMessage());
            flag = false;
            throw new SqlException(HttpStatus.INTERNAL_SERVER_ERROR, "freight rate update error");
        }finally{
            SupplierLog supplierLog = new SupplierLog();
            supplierLog.setUuid(UUID.randomUUID().toString());
            supplierLog.setOperTime(operTime);
            supplierLog.setLogHeader("Freight rate update");
            supplierLog.setLogDesc("Supplier \"" + supplierName + "\" freight rates updated at " + currentDateAsCST + " " + currentTimeAsCST);
            supplierLog.setVerdorName(supplierName);
            supplierLog.setVerdorUuid(syscoSuvc);
            if (flag) {
                supplierLog.setOperResult("SUCCESS");
            }else{
                supplierLog.setOperResult("FAILED");
            }
            supplierLogMapper.insertSelective(supplierLog);
        }
    }

    public void updateFreight(ReqShipOpco point, String type, String uuid, String currentDateAsCST, String currentTimeAsCST ){
        SupplierPointDetailKey key = new SupplierPointDetailKey();
        key.setUuid(uuid);
        key.setOpcoNo(point.getOpco().split("-")[0]);
        key.setType(type);
        key.setShipPointNo(point.getShipPoint());
        SupplierPointDetail supplierPointDetail = supplierPointDetailMapper.selectByPrimaryKey(key);
        if(null != supplierPointDetail){
            try{

                if(StringUtils.isBlank(supplierPointDetail.getFreightUpdateUuid())){
                    SupplierPointDetail sd = new SupplierPointDetail();
                    sd.setUuid(supplierPointDetail.getUuid());
                    sd.setType(supplierPointDetail.getType());
                    sd.setOpcoNo(supplierPointDetail.getOpcoNo());
                    sd.setShipPointNo(supplierPointDetail.getShipPointNo());
                    String s = UUID.randomUUID().toString();
                    sd.setFreightUpdateUuid(s);
                    supplierPointDetailMapper.updateByPrimaryKeySelective(sd);
                    FreightUpdateDetail freightUpdateDetail = TransformUtils.tfReqShipOpcoToFreight(point.getUpdateRat());
                    freightUpdateDetail.setUuid(s);
                    freightUpdateDetail.setCreatedDate(FormatterUtils.getNowDate(currentDateAsCST));
                    freightUpdateDetail.setUpdateDate(FormatterUtils.getNowDate(currentDateAsCST));
                    freightUpdateDetail.setCreatedTime(currentTimeAsCST);
                    freightUpdateDetailMapper.insertSelective(freightUpdateDetail);
                }else {
                    FreightUpdateDetail freightUpdateDetail = TransformUtils.tfReqShipOpcoToFreight(point.getUpdateRat());
                    freightUpdateDetail.setUuid(supplierPointDetail.getFreightUpdateUuid());
                    FreightUpdateDetail fud = freightUpdateDetailMapper.selectByPrimaryKey(freightUpdateDetail);
                    //if not exist, add record
                    if(null == fud){
                        freightUpdateDetail.setCreatedDate(FormatterUtils.getNowDate(currentDateAsCST));
                        freightUpdateDetail.setUpdateDate(FormatterUtils.getNowDate(currentDateAsCST));
                        freightUpdateDetail.setCreatedTime(currentTimeAsCST);
                        freightUpdateDetailMapper.insertSelective(freightUpdateDetail);
                    }else { //if exist, edit record
                        freightUpdateDetail.setUpdateDate(FormatterUtils.getNowDate(currentDateAsCST));
                        freightUpdateDetailMapper.updateByPrimaryKeySelective(freightUpdateDetail);
                    }
                }

            }catch (Exception e){
                throw new ValidationException(HttpStatus.OK,"update Freight rate error");
            }
        }else {
            throw new ValidationException(HttpStatus.OK,"the update record is not found");
        }
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
