package com0.coastalcasa.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com0.coastalcasa.Models.Landlord;

@Mapper
public interface LandlordMapper {
    
    @Select("select * from landlord")
    List<Landlord> findAll();

    @Insert("INSERT IGNORE INTO landlord(email,password) VALUES (#{email},#{password})")
    int insert(Landlord landlord);

    @Select("select * from landlord where email=#{email}")
    List<Landlord> findLandLordByEmail(Landlord landlord);

}
