package com0.coastalcasa.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import com0.coastalcasa.Mapper.LandlordMapper;
import com0.coastalcasa.Mapper.ListingImageMapper;
import com0.coastalcasa.Mapper.ListingMapper;
import com0.coastalcasa.Models.CreateListingRequest;
import com0.coastalcasa.Models.Landlord;
import com0.coastalcasa.Models.Listing;
import com0.coastalcasa.Models.ListingImage;

@RestController
public class APIController {

	private LandlordMapper landlordMapper;

	
	private ListingMapper listingMapper;
	
	
	private ListingImageMapper listingImageMapper;

	public APIController(LandlordMapper llm, ListingMapper lm, ListingImageMapper lim){
		this.landlordMapper = llm;
		this.listingMapper = lm;
		this.listingImageMapper = lim;
	}

	@GetMapping("/")
	public String index() {
		return "Coastal Casa Finder";
	}

	@GetMapping("/alllandlords")
	public List<Landlord> getAll(){
		return landlordMapper.findAll();
	}

	@PostMapping("/landlordsignup")
	private void createLandLord(@RequestBody Landlord landlord){
		landlordMapper.insert(landlord);
	}

	@GetMapping("/alllistings")
	public List<ListingResponse> getListings() {
		List<Listing> listings = listingMapper.findAll();
		List<ListingResponse> responses = new ArrayList<>();
		
		for (Listing listing : listings) {
		System.out.println("Here"+listing.getLandlord_email());
		List<ListingImage> images = listingImageMapper.findByListingId(listing.getId());
		for(ListingImage l: images){
			System.out.println("Here"+l.getImage_data());
		}
		ListingResponse response = new ListingResponse(listing, images);
		responses.add(response);
		}
		
		return responses;
	}

	@PostMapping("/create")
	public ResponseEntity<?> createListing(@RequestBody CreateListingRequest request) {
	try {
		// Create a new listing object
		Listing newListing = new Listing();
		newListing.setLandlord_email(request.getLandlord_email());
		newListing.setDescription(request.getDescription());
		newListing.setLocation(request.getLocation());
		newListing.setPrice(request.getPrice());
		newListing.setNum_bathrooms(request.getNum_bedrooms());
		newListing.setNum_bedrooms(request.getNum_bathrooms());
		newListing.setAmenities(request.getAmenities());
		
		// Insert the new listing into the database using the ListingMapper
		listingMapper.insert(newListing);
		
		// Create new listing images and insert them into the database using the ListingImageMapper
		for (byte[] imageData : request.getImages()) {
		ListingImage newImage = new ListingImage();
		newImage.setListing_id(newListing.getId());
		newImage.setImage_data(imageData);
		listingImageMapper.insert(newImage);
		}
		
		// Return a success response with the new listing ID
		Map<String, Integer> response = new HashMap<>();
		response.put("id", newListing.getId());
		return ResponseEntity.ok(response);
	} catch (Exception e) {
		// Return an error response if there's an exception
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
	}

	
	public static class ListingResponse {
		@JsonProperty
		private Listing listing;
		@JsonProperty
		private List<byte[]> images;
		

		public ListingResponse(Listing listing, List<ListingImage> images) {
		  this.listing = listing;
		  
		  List<byte[]> imageDataList = new ArrayList<>();
		  for (ListingImage image : images) {
			imageDataList.add(image.getImage_data());
		  }
		  this.images = imageDataList;
		}

		public Listing getListing() {
			return listing;
		}

		public void setListing(Listing listing) {
			this.listing = listing;
		}

		public List<byte[]> getImages() {
			return images;
		}

		public void setImages(List<byte[]> images) {
			this.images = images;
		}
		
	  }

}
	