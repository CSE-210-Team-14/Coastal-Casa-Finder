package com0.coastalcasa.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com0.coastalcasa.Models.Applicant;

@Mapper
public interface ApplicantMapper {
  @Select("SELECT * FROM applicant WHERE id = #{id}")
  Applicant findById(Integer id);
  
  @Select("SELECT * FROM applicant WHERE listing_id = #{listingId}")
  List<Applicant> findByListingId(Integer listingId);
  
  @Insert("INSERT INTO applicant(listing_id, first_name, last_name, email, message, move_in_date_range) " +
          "VALUES (#{listingId}, #{firstName}, #{lastName}, #{email}, #{message}, #{moveInDateRange})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(Applicant applicant);
}