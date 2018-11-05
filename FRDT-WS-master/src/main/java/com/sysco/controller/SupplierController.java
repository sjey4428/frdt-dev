package com.sysco.controller;

import com.sysco.entity.ShipPointOpcoDto;
import com.sysco.entity.VendorInfo;
import com.sysco.request.*;
import com.sysco.response.GenericResponse;
import com.sysco.response.ResShipOpcoDetail;
import com.sysco.service.SupplierInfoService;
import com.sysco.utils.TransformUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/frdt")
public class SupplierController {
    @Autowired
    private SupplierInfoService supplierInfoService;

    @ResponseBody
    @RequestMapping(value = "create-supplier", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "create supplier", notes = "", response = GenericResponse.class)
    public GenericResponse create(@RequestBody @Validated ReqSupplier reqSupplier) throws IOException {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResult(true);
        supplierInfoService.createSupplier(reqSupplier);
        supplierInfoService.sendAttachmentsMail(reqSupplier);
        return genericResponse;
    }


    @RequestMapping(value = "/upload-documents", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "supplier upload documents",
            notes = "some notes about this service, will supply later",
            response = GenericResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "supplierName",
                    value = "supplier name,  todo",
                    required = true
            )
    })
    public GenericResponse uploadDocuments(@RequestParam("file") MultipartFile file, String supplierName) {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResult(true);
        Map<String, String> map = new HashMap<>(1);
        map.put("fileUrl", supplierInfoService.uploadDocuments(file, supplierName));
        genericResponse.setMapData(map);
        return genericResponse;

    }

    @RequestMapping(value = "/download-documents", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "supplier download documents",
            notes = "some notes about this service, will supply later",
            response = GenericResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "supplierName",
                    value = "supplierName,  todo",
                    required = true
            ),
            @ApiImplicitParam(
                    name = "fileUrl",
                    value = "fileUrl,  todo",
                    required = true
            )
    })
    public GenericResponse downLoadDocuments(String supplierName, String fileUrl) {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResult(true);
        Map<String, String> map = new HashMap<>(1);
        map.put("presignedUrl", supplierInfoService.downLoadDocuments(supplierName,fileUrl));
        genericResponse.setMapData(map);
        return genericResponse;

    }

    @RequestMapping(value = "/delete-documents", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "supplier delete documents",
            notes = "some notes about this service, will supply later",
            response = GenericResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "supplierName",
                    value = "supplier name,  todo",
                    required = true
            )
    })
    public GenericResponse deleteDocuments(String supplierName, String fileUrl) {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResult(true);
        supplierInfoService.deleteDocuments(supplierName, fileUrl);
        return genericResponse;

    }

    @RequestMapping(value = "/upload-supplier-template", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "supplier upload template file",
            notes = "some notes about this service, will supply later",
            response = GenericResponse.class)
    public GenericResponse upLoadSupplierTemp(@RequestParam("file") MultipartFile file, String email) {
        String errormes = supplierInfoService.upLoadSupplierTemp(file,email);
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResult(true);
        if(StringUtils.isNotBlank(errormes)){
            genericResponse.setErrorMessage(errormes);
        }
        return genericResponse;

    }

    @RequestMapping(value = "/select-scsSuppiler-info", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "SCS Suppliers",
            notes = "some notes about this service, will supply later",
            response = GenericResponse.class)
    public GenericResponse displaySCSSuppliersList( @Validated SCSFilterCondition scsFilterCondition){
        GenericResponse genericResponse;
        genericResponse = supplierInfoService.fetchSCSSupplier(scsFilterCondition);
        return genericResponse;
    }

    @RequestMapping(value = "update-dateOfReview", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "update date of review", notes = "", response = GenericResponse.class)
    public GenericResponse updateDateOfReview(@RequestBody @Validated List<DateOfReview> dateOfReviewList){
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResult(true);

        int successUpdatedNum = 0;
        Map<String,Object> map = new HashMap<>();
        for (DateOfReview dateOfReview:dateOfReviewList) {
           if(supplierInfoService.updateDateOfReview(dateOfReview)) {
               successUpdatedNum++;
           }
        }
        map.put("totalUpdateNum",dateOfReviewList.size());
        map.put("successUpdatedNum",successUpdatedNum);
        genericResponse.setMapData(map);

        return genericResponse;
    }

    @RequestMapping(value = "/shippoint-opco-list", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "shippoint-opco-list",
            notes = "some notes about this service, will supply later",
            response = GenericResponse.class)
    public GenericResponse selectOpcoDetails(@Validated ShipOpcoFilterCondition shipOpcoFilterCondition){
        GenericResponse genericResponse = new GenericResponse();
        List<ShipPointOpcoDto> shipPointOpcoDtos = supplierInfoService.selectOpcoList(shipOpcoFilterCondition);
        if(!CollectionUtils.isEmpty(shipPointOpcoDtos)){
            ResShipOpcoDetail resShipOpcoDetails = TransformUtils.transformShipOpco(shipPointOpcoDtos);
            Map<String, Object> m = new HashMap<>();
            m.put("responseBody", resShipOpcoDetails);
            genericResponse.setMapData(m);
        }
        genericResponse.setResult(true);
        return genericResponse;
    }

    @RequestMapping(value = "/select-vendor-info", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "VendorInfo",
            notes = "some notes about this service, will supply later",
            response = GenericResponse.class)
    public GenericResponse getVendorInfoById(String vendorId){
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResult(true);

        VendorInfo vendorInfos = supplierInfoService.getVendorInfoById(vendorId);
        Map<String, Object> m = new HashMap<>();
        m.put("data", vendorInfos);
        genericResponse.setMapData(m);

        return genericResponse;
    }

    @RequestMapping(value = "/select-log-list", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "select-log-list",
            notes = "some notes about this service, will supply later",
            response = GenericResponse.class)
    public GenericResponse selectLogList(){
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResult(true);
        genericResponse.setRows(supplierInfoService.selectLogList());
        return genericResponse;
    }

    @RequestMapping(value = "/modify-supplier", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "modify-vendor",
            notes = "some notes about this service, will supply later",
            response = GenericResponse.class)
    public GenericResponse modifyVendor(@RequestBody @Validated ReqModifyVendor reqModifyVendor){
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResult(true);
        String uuid = supplierInfoService.modifySupplier(reqModifyVendor);
        supplierInfoService.sendModifyEmail(uuid, reqModifyVendor);
        return genericResponse;
    }

    @RequestMapping(value = "/update-freight-rate", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "update-freight-rate",
            notes = "some notes about this service, will supply later",
            response = GenericResponse.class)
    public GenericResponse updateFreightRate(@RequestBody ReqUpdateFreightRate updateFreightRate){
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResult(true);
        supplierInfoService.updateFreightRate(updateFreightRate);
        return genericResponse;
    }
}
