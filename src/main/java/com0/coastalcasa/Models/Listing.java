package com0.coastalcasa.Models;

import java.util.List;

public class Listing {
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    private String landlordEmail;
    public String getLandlordEmail() {
        return landlordEmail;
    }
    public void setLandlordEmail(String landlordEmail) {
        this.landlordEmail = landlordEmail;
    }
    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    private List<ListingImage> images;
    public List<ListingImage> getImages() {
        return images;
    }
    public void setImages(List<ListingImage> images) {
        this.images = images;
    }
    private int bathrooms;
    public int getBathrooms() {
        return bathrooms;
    }
    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }
    private int bedrooms;
    public int getBedrooms() {
        return bedrooms;
    }
    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }
    private int sqft;
    public int getSqft() {
        return sqft;
    }
    public void setSqft(int sqft) {
        this.sqft = sqft;
    }

    
}
