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

    @Insert("insert into landlord(email,password) values(#{email},#{password})")
    @SelectKey(statement = "select last_insert_id()",keyProperty = "id",
                before = false, resultType = Integer.class)
    void insert(Landlord landlord);
}
