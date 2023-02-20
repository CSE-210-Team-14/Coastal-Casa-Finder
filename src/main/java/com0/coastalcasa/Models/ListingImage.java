package com0.coastalcasa.Models;

public class ListingImage {
    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    private byte[] data;
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
    private int listingID;
    public int getListingID() {
        return listingID;
    }
    public void setListingID(int listingID) {
        this.listingID = listingID;
    }

}
