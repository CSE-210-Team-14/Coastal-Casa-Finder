package com0.coastalcasa.Models;


public class ListingImage {
    private int id;
    private int listing_id;
    private byte[] image_data;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getListing_id() {
        return listing_id;
    }
    public void setListing_id(int listing_id) {
        this.listing_id = listing_id;
    }
    public byte[] getImage_data() {
        return image_data;
    }
    public void setImage_data(byte[] data) {
        this.image_data = data;
    }

    // Constructors, getters, and setters
}
