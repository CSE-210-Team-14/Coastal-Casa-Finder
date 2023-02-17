package com0.coastalcasa.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com0.coastalcasa.Models.Landlord;

@Mapper
public interface LandlordMapper {
    
    @Select("select * from landlords")
    List<Landlord> findAll();
}
