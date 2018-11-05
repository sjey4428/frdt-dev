package com.sysco.dao;

import com.sysco.entity.FreightUpdateDetail;
import com.sysco.entity.FreightUpdateDetailKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FreightUpdateDetailMapper {
    int deleteByPrimaryKey(FreightUpdateDetailKey key);

    int insert(FreightUpdateDetail record);

    int insertSelective(FreightUpdateDetail record);

    FreightUpdateDetail selectByPrimaryKey(FreightUpdateDetailKey key);

    int updateByPrimaryKeySelective(FreightUpdateDetail record);

    int updateByPrimaryKey(FreightUpdateDetail record);

    List<FreightUpdateDetail> selectByUuid(@Param("uuid")String uuid);
}