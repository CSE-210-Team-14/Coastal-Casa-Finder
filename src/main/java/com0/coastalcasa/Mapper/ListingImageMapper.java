package com0.coastalcasa.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com0.coastalcasa.Models.ListingImage;

@Mapper
public interface ListingImageMapper {
  @Select("SELECT * FROM listing_image WHERE id = #{id}")
  ListingImage findById(Integer id);
  
  @Select("SELECT * FROM listing_image WHERE listing_id = #{listingId}")
  List<ListingImage> findByListingId(Integer listingId);
  
  @Insert("INSERT INTO listing_image(listing_id, image_data) VALUES (#{listing_id}, #{image_data})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(ListingImage listingImage);

  @Update("UPDATE listing_image SET image_data = #{image_data}"+
            "WHERE listing_id = #{listing_id}")
    int updateListing(ListingImage listingImage);

    @Delete("DELETE FROM listing_image WHERE listing_id = #{listing_id}")
    int deleteListing(int listing_id);
}