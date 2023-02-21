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

    @Select("SELECT * FROM listing")
    List<Listing> findAll();

    @Select("SELECT * FROM listing WHERE id = #{id}")
    Listing findById(Integer id);
    
    @Select("SELECT * FROM listing WHERE landlord_email = #{landlordEmail}")
    List<Listing> findByLandlordEmail(String landlordEmail);
    
    @Insert("INSERT INTO listing(landlord_email, description, location, price, num_bathrooms, num_bedrooms, amenities) " +
            "VALUES (#{landlordEmail}, #{description}, #{location}, #{price}, #{numBathrooms}, #{numBedrooms}, #{amenities})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Listing listing);
    
    
}
