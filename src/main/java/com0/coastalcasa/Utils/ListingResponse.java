package com0.coastalcasa.Utils;

import java.util.List;

import com0.coastalcasa.Models.Listing;
import com0.coastalcasa.Models.ListingImage;

public class ListingResponse {
    private Listing listing;
    private List<ListingImage> images;
    

    public ListingResponse(Listing listing, List<ListingImage> images) {
      this.listing = listing;
      this.images = images;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public List<ListingImage> getImages() {
        return images;
    }

    public void setImages(List<ListingImage> images) {
        this.images = images;
    }

  }