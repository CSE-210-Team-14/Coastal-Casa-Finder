package com0.coastalcasa.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com0.coastalcasa.Models.Listing;

@Mapper
public interface ListingMapper {

    @Select("SELECT * FROM listings")
    List<Listing> getAll();

    @Select("SELECT * FROM listings WHERE id = #{id}")
    Listing getById(@Param("id") Long id);

    @Select("SELECT * FROM listings WHERE email = #{email}")
    Listing getByEmail(@Param("email") String email);

    @Insert("INSERT INTO house_listings(title, description, price) VALUES (#{title}, #{description}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Listing houseListing);
    
    
}
