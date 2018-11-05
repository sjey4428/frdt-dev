package com.sysco.utils;

import com.sysco.entity.*;
import com.sysco.response.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by james.zhu on 2018/9/19.
 */
public class TransformUtils {
    public static ResShipOpcoDetail transformShipOpco(List<ShipPointOpcoDto> spod){
        ResShipOpcoDetail rd = new ResShipOpcoDetail();
        rd.setVendorName(spod.get(0).getVendorName());
        rd.setVendorNumber(spod.get(0).getVendorNumber());
        ResBracket bracket = new ResBracket();
        bracket.setBracket1(spod.get(0).getBracket1Header());
        bracket.setBracket2(spod.get(0).getBracket2Header());
        bracket.setBracket3(spod.get(0).getBracket3Header());
        bracket.setBracket4(spod.get(0).getBracket4Header());
        bracket.setBracket5(spod.get(0).getBracket5Header());
        bracket.setBracket6(spod.get(0).getBracket6Header());
        bracket.setBracket7(spod.get(0).getBracket7Header());
        bracket.setBracket8(spod.get(0).getBracket8Header());
        rd.setBrackets(bracket);
        rd.setBracketUOM(spod.get(0).getBracketsUom());

        List<ResShipOpco> ships = new ArrayList<>();
        List<ResShipOpco> exceptions = new ArrayList<>();
        List<ResContractException> recs = new ArrayList<>();
        spod.forEach(sd -> {
            ResShipOpco so = new ResShipOpco();
            so.setVendorNumber(rd.getVendorNumber());
            so.setVendorName(rd.getVendorName());
            so.setOpcoNo(sd.getOpcoNo());
            so.setOpcoName(sd.getOpcoNo() + "-" + sd.getOpcoName());
            so.setType(sd.getType());
            so.setCity(sd.getCity());
            so.setState(sd.getState());
            so.setZipCode(sd.getZipCode());
            so.setPickupAllowance(String.valueOf(sd.getPickupAllowance()));
            so.setPickupAllowanceUom(sd.getPickupAllowanceUom());
            so.setFreightRateUom(sd.getFreightRateUom());
            ResBracket bracket1 = new ResBracket();
            bracket1.setBracket1(String.valueOf(sd.getBracket1()));
            bracket1.setBracket2(String.valueOf(sd.getBracket2()));
            bracket1.setBracket3(String.valueOf(sd.getBracket3()));
            bracket1.setBracket4(String.valueOf(sd.getBracket4()));
            bracket1.setBracket5(String.valueOf(sd.getBracket5()));
            bracket1.setBracket6(String.valueOf(sd.getBracket6()));
            bracket1.setBracket7(String.valueOf(sd.getBracket7()));
            bracket1.setBracket8(String.valueOf(sd.getBracket8()));
            so.setFreightRate(bracket1);
            ResUpdateRat resUpdateRat = new ResUpdateRat();
            if(null == sd.getUpdatePickupAllowance()){
                resUpdateRat.setPickupAllowance("");
            }else{
                resUpdateRat.setPickupAllowance(String.valueOf(sd.getUpdatePickupAllowance()));
            }
            if(null == sd.getBracketType()){
                resUpdateRat.setBracketType("");
            }else{
                resUpdateRat.setBracketType(String.valueOf(sd.getBracketType()));
            }
            if(null == sd.getBracketValue()){
                resUpdateRat.setBracketValue("");
            }else{
                resUpdateRat.setBracketValue(String.valueOf(sd.getBracketValue()));
            }
            if(null == sd.getPuPercentage()){
                resUpdateRat.setPuPercentage("");
            }else{
                resUpdateRat.setPuPercentage(String.valueOf(sd.getPuPercentage()));
            }
            if(null == sd.getSurcharge()){
                resUpdateRat.setSurcharge("");
            }else{
                resUpdateRat.setSurcharge(String.valueOf(sd.getSurcharge()));
            }
            if(null != sd.getFreightEffectiveDate()){
                resUpdateRat.setEffectiveDate(sd.getFreightEffectiveDate());
            }
            so.setUpdateRat(resUpdateRat);
            if("ship_point".equals(sd.getType())){
                so.setShipPoint(sd.getShipPointNo());
                ships.add(so);
            }
            if("contract_exception".equals(sd.getType())){
                so.setShipPoint(sd.getShipPointNo());
                so.setContractException(sd.getContractException());
                ResContractException rce = new ResContractException();
                rce.setContractExceptionName(sd.getContractException());
                ResBracket ceb = new ResBracket();
                ceb.setBracket1(String.valueOf(sd.getContractBracket1Header()));
                ceb.setBracket2(String.valueOf(sd.getContractBracket2Header()));
                ceb.setBracket3(String.valueOf(sd.getContractBracket3Header()));
                ceb.setBracket4(String.valueOf(sd.getContractBracket4Header()));
                ceb.setBracket5(String.valueOf(sd.getContractBracket5Header()));
                ceb.setBracket6(String.valueOf(sd.getContractBracket6Header()));
                ceb.setBracket7(String.valueOf(sd.getContractBracket7Header()));
                ceb.setBracket8(String.valueOf(sd.getContractBracket8Header()));
                rce.setBrackets(ceb);
                recs.add(rce);
                exceptions.add(so);
            }
        });
        for (int i = 0; i < ships.size(); i++) {
            ships.get(i).set_id(i);
        }
        rd.setShipPoints(ships);
        rd.setContractExceptions(Collections.EMPTY_LIST);
        if(!CollectionUtils.isEmpty(exceptions)){
            Map<String, List<ResShipOpco>> map = exceptions.stream().collect(Collectors.groupingBy(ResShipOpco::getContractException));
            List<ResContractException> collect = recs.stream().filter(distinctByKey(ResContractException::getContractExceptionName)).collect(Collectors.toList());
            collect.forEach(c -> {
                List<ResShipOpco> list = map.get(c.getContractExceptionName());
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).set_id(i);
                }
                c.setContractException(list);
            });
            rd.setContractExceptions(collect);
        }
        return rd;
    }

    public static VendorInfo transformVendorInfo(List<VendorDetails> vendorInfoList) {
        VendorInfo vendorInfo = new VendorInfo();
        vendorInfo.setVendorName(vendorInfoList.get(0).getVendorName());
        vendorInfo.setAdditionalFees(vendorInfoList.get(0).getAdditionalFees());
        vendorInfo.setBidCategory(vendorInfoList.get(0).getBidCategory());
        vendorInfo.setComplianceShipperCount(vendorInfoList.get(0).getComplianceShipperCount());
        vendorInfo.setCorporateAddress(vendorInfoList.get(0).getCorporateAddress());
        vendorInfo.setCurrentPalletProgram(vendorInfoList.get(0).getCurrentPalletProgram());
        vendorInfo.setDeliveryPickupPeriod(vendorInfoList.get(0).getDeliveryPickupPeriod());
        vendorInfo.setEffectiveDate(vendorInfoList.get(0).getEffectiveDate());
        vendorInfo.setEmail(vendorInfoList.get(0).getEmail());
        vendorInfo.setLowestProductCost(vendorInfoList.get(0).getLowestProductCost());
        vendorInfo.setFreightDifferent(vendorInfoList.get(0).getFreightDifferent());
        vendorInfo.setFreightUom(vendorInfoList.get(0).getFreightUom());
        vendorInfo.setFuelSurchargePeriod(vendorInfoList.get(0).getFuelSurchargePeriod());
        vendorInfo.setGeneralSupplierComments(vendorInfoList.get(0).getGeneralSupplierComments());
        vendorInfo.setMinimumDelivery(vendorInfoList.get(0).getMinimumDelivery());
        vendorInfo.setMinimumDifferent(vendorInfoList.get(0).getMinimumDifferent());
        vendorInfo.setMinimumPickup(vendorInfoList.get(0).getMinimumPickup());
        vendorInfo.setPhone(vendorInfoList.get(0).getPhone());
        vendorInfo.setPickupAllowances(vendorInfoList.get(0).getPickupAllowances());
        vendorInfo.setProductCosting(vendorInfoList.get(0).getProductCosting());
        vendorInfo.setProductUom(vendorInfoList.get(0).getProductUom());
        vendorInfo.setPublishedFreightRates(vendorInfoList.get(0).getPublishedFreightRates());
        vendorInfo.setSupplierChainContact(vendorInfoList.get(0).getSupplierChainContact());
        vendorInfo.setSupplierChainManager(vendorInfoList.get(0).getSupplierChainManager());
        vendorInfo.setSupplierCommentsFreight(vendorInfoList.get(0).getSupplierCommentsFreight());
        vendorInfo.setSupplierCommentsProfile(vendorInfoList.get(0).getSupplierCommentsProfile());
        vendorInfo.setTemperatureCode(vendorInfoList.get(0).getTemperatureCode());
        RailMaximum railMaximum = new RailMaximum();
        railMaximum.setCases(vendorInfoList.get(0).getRail_maximum_cases());
        railMaximum.setCubes(vendorInfoList.get(0).getRail_maximum_cubes());
        railMaximum.setWeight(vendorInfoList.get(0).getRail_maximum_weight());
        railMaximum.setPallets(vendorInfoList.get(0).getRail_maximum_pallets());
        vendorInfo.setRailMaximum(railMaximum);
        TruckMaximum truckMaximum = new TruckMaximum();
        truckMaximum.setWeight(vendorInfoList.get(0).getTruckload_maximum_weight());
        truckMaximum.setPallets(vendorInfoList.get(0).getTruckload_maximum_pallets());
        truckMaximum.setCubes(vendorInfoList.get(0).getTruckload_maximum_cubes());
        truckMaximum.setCases(vendorInfoList.get(0).getTruckload_maximum_cases());
        vendorInfo.setTruckMaximum(truckMaximum);

        return vendorInfo;
    }

    public static SupplierLocation tfVendorLocationToSup(VendorLocation vendorLocation,String uuid){
        SupplierLocation supplierLocation = new SupplierLocation();
        supplierLocation.setUuid(uuid);
        supplierLocation.setShipPointNo(vendorLocation.getId());
        supplierLocation.setPhysicalAddressZone(vendorLocation.getPhysicalAddressZone());
        supplierLocation.setCity(vendorLocation.getCity());
        supplierLocation.setState(vendorLocation.getState());
        supplierLocation.setZipCode(vendorLocation.getZipCode());
        supplierLocation.setDcPlant(vendorLocation.getDcPlant());
        supplierLocation.setSourcedProduct(vendorLocation.getSourcedProduct());
        supplierLocation.setRailFacilities(vendorLocation.getRailFacilities());
        supplierLocation.setDropTrailer(vendorLocation.getDropTrailer());
        if("D".equals(vendorLocation.getStatus())){
            supplierLocation.setDeleted("Y");
        }
        if("N".equals(vendorLocation.getStatus())){
            String currentDateAsCST = FormatterUtils.getCurrentDateAsCST();
            String currentTimeAsCST = FormatterUtils.getCurrentTimeAsCST();
            supplierLocation.setDeleted("N");
            supplierLocation.setCreatedDate(FormatterUtils.getNowDate(currentDateAsCST));
            supplierLocation.setCreatedTime(currentTimeAsCST);
        }
        return supplierLocation;
    }

    public static SupplierContractException tfVendorExceptionToSup(VendorContractException vendorException,String uuid){
        SupplierContractException supplierContractException = new SupplierContractException();
        supplierContractException.setUuid(uuid);
        supplierContractException.setSupplierContractNo(vendorException.getId());
        supplierContractException.setContractException(vendorException.getContractException());
        if("D".equals(vendorException.getStatus())){
            supplierContractException.setDeleted("Y");
        }
        if("N".equals(vendorException.getStatus())){
            String currentDateAsCST = FormatterUtils.getCurrentDateAsCST();
            String currentTimeAsCST = FormatterUtils.getCurrentTimeAsCST();
            supplierContractException.setDeleted("N");
            supplierContractException.setCreatedDate(FormatterUtils.getNowDate(currentDateAsCST));
            supplierContractException.setCreatedTime(currentTimeAsCST);
        }
        return supplierContractException;
    }

    public static SupplierFileKey tfVendorFileToSup(VendorFileUrl vendorFileUrl,String uuid){
        SupplierFileKey supplierFileKey = new SupplierFileKey();
        supplierFileKey.setUuid(uuid);
        supplierFileKey.setFileUrl(vendorFileUrl.getFileUrl());
        return supplierFileKey;
    }

    public static FreightUpdateDetail tfReqShipOpcoToFreight(ResUpdateRat updateRat){
        FreightUpdateDetail freightUpdateDetail = new FreightUpdateDetail();
        if(StringUtils.isNotBlank(updateRat.getPickupAllowance())){
            freightUpdateDetail.setPickupAllowance(new BigDecimal(updateRat.getPickupAllowance()));
        }
        if(StringUtils.isNotBlank(updateRat.getSurcharge())){
            freightUpdateDetail.setSurcharge(new BigDecimal(updateRat.getSurcharge()));
        }
        if(StringUtils.isNotBlank(updateRat.getPuPercentage())){
            freightUpdateDetail.setPuPercent(new BigDecimal(updateRat.getPuPercentage()));
        }
        if(StringUtils.isNotBlank(updateRat.getBracketValue())){
            freightUpdateDetail.setBracketValue(new BigDecimal(updateRat.getBracketValue()));
        }
        if(StringUtils.isNotBlank(updateRat.getBracketType())){
            freightUpdateDetail.setBracketType(Integer.valueOf(updateRat.getBracketType()));
        }
        if(null == updateRat.getEffectiveDate()){
            String currentDateAsCST = FormatterUtils.getCurrentDateAsCST();
            freightUpdateDetail.setEffectiveDate(FormatterUtils.getNowDate(currentDateAsCST));
        }else {
            freightUpdateDetail.setEffectiveDate(updateRat.getEffectiveDate());
        }
        return freightUpdateDetail;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
