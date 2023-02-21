package com0.coastalcasa.Models;

import java.util.List;

public class CreateListingRequest {
    private String landlord_email;
    private String description;
    private String location;
    private double price;
    private int num_bathrooms;
    private int num_bedrooms;
    private List<byte[]> images;
    private String amenities;
     
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getAmenities() {
        return amenities;
    }
    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }
    public String getLandlord_email() {
        return landlord_email;
    }
    public void setLandlord_email(String landlord_email) {
        this.landlord_email = landlord_email;
    }
    public int getNum_bathrooms() {
        return num_bathrooms;
    }
    public void setNum_bathrooms(int num_bathrooms) {
        this.num_bathrooms = num_bathrooms;
    }
    public int getNum_bedrooms() {
        return num_bedrooms;
    }
    public void setNum_bedrooms(int num_bedrooms) {
        this.num_bedrooms = num_bedrooms;
    }
    public List<byte[]> getImages() {
        return images;
    }
    public void setImages(List<byte[]> images) {
        this.images = images;
    }
  
    // Getters and setters for all fields
  }