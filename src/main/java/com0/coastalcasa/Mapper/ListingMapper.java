package com0.coastalcasa.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com0.coastalcasa.Models.Listing;

@Mapper
public interface ListingMapper {

    static final Logger logger = LogManager.getLogger(ListingMapper.class);

    @Select("SELECT * FROM listing")
    List<Listing> findAll();

    @Select("SELECT * FROM listing WHERE id = #{id}")
    Listing findById(Integer id);
    
    @Select("SELECT * FROM listing WHERE landlord_email = #{landlord_email}")
    List<Listing> findByLandlordEmail(String landlordEmail);
    
    @Insert("INSERT INTO listing(landlord_email, description, location, price, num_bathrooms, num_bedrooms, amenities) " +
        "VALUES (#{landlord_email}, #{description}, #{location}, #{price}, #{num_bathrooms}, #{num_bedrooms}, #{amenities})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Listing listing);

    @Select("SELECT * FROM listing ORDER BY id DESC LIMIT 1;")
    Listing lastListing();
    
    @Select("SELECT * FROM listing WHERE landlord_email = #{landlordEmail}")
    List<Listing> getListingsByLandlordEmail(@Param("landlordEmail") String landlordEmail);

    @Select({"<script>",
            "SELECT * FROM listing ",
            "WHERE 1 = 1 " ,
            "<if test='landlordEmail != null'> AND landlord_email = #{landlordEmail} </if>",
            "<if test='description != null'> AND description = #{description} </if>" ,
            "<if test='location != null'> AND location LIKE #{location} </if>" ,
            "<if test='price != -1.0'> AND price = #{price} </if>" ,
            "<if test='num_bathrooms != -1'> AND num_bathrooms = #{num_bathrooms} </if>" ,
            "<if test='num_bedrooms != -1'> AND num_bedrooms = #{num_bedrooms} </if>" ,
            "<if test='amenities != null'> AND amenities LIKE #{amenities} </if>" ,
            "</script>"})
    List<Listing> searchListings(@Param("landlordEmail") String landlordEmail, @Param("description") String description,@Param("location") String location, @Param("price") double price, @Param("num_bathrooms") int num_bathrooms, @Param("num_bedrooms") int num_bedrooms, @Param("amenities") String amenities);
    
    @Update("UPDATE listing SET landlord_email = #{landlord_email}, description = #{description}, location = #{location}, " +
            "price = #{price}, num_bedrooms = #{num_bedrooms}, num_bathrooms = #{num_bathrooms}, amenities = #{amenities} " +
            "WHERE id = #{id}")
    int updateListing(Listing listing);


    @Delete("DELETE FROM listing WHERE id = #{id}")
    int deleteListing(int id);


    
}
