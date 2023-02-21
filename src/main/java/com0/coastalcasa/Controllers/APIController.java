package com0.coastalcasa.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

	@GetMapping("/listings")
	public ResponseEntity<List<ListingResponse>> getLandlordListings(@RequestParam String landlord_email) {
		try {
			List<Listing> listings = listingMapper.getListingsByLandlordEmail(landlord_email);
			List<ListingResponse> listingsWithImages = new ArrayList<>();
			for (Listing listing : listings) {
				List<ListingImage> imagesData = listingImageMapper.findByListingId(listing.getId());
				ListingResponse listingWithImage = new ListingResponse(listing, imagesData);
				listingsWithImages.add(listingWithImage);
			}
			return ResponseEntity.ok(listingsWithImages);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}


	@GetMapping("/alllistings")
	public List<ListingResponse> getListings() {
		List<Listing> listings = listingMapper.findAll();
		List<ListingResponse> responses = new ArrayList<>();
		
		for (Listing listing : listings) {
		System.out.println("Here"+listing.getLandlord_email());
		List<ListingImage> images = listingImageMapper.findByListingId(listing.getId());
		
		ListingResponse response = new ListingResponse(listing, images);
		responses.add(response);
		}
		
		return responses;
	}

	@PostMapping(value = "/createlisting", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<String> createListing(@RequestParam("landlord_email") String landlordEmail,
			@RequestParam("description") String description, @RequestParam("location") String location,
			@RequestParam("price") double price, @RequestParam("num_bathrooms") int numBathrooms,
			@RequestParam("num_bedrooms") int numBedrooms, @RequestParam("amenities") String amenities,
			@RequestParam("images") List<MultipartFile> listingImages) {
			
		System.out.println(landlordEmail);
		// Create the listing object
		Listing newListing = new Listing();
		newListing.setLandlord_email(landlordEmail);
		newListing.setDescription(description);
		newListing.setLocation(location);
		newListing.setPrice(price);
		newListing.setNum_bathrooms(numBathrooms);
		newListing.setNum_bedrooms(numBedrooms);
		newListing.setAmenities(amenities);

		// Save the listing to the database
		listingMapper.insert(newListing);
		Listing savedListing = listingMapper.lastListing();
				
		
		// Save the images to the database
		for (MultipartFile listingImage : listingImages) {
			ListingImage newImage = new ListingImage();
			newImage.setListing_id(savedListing.getId());

			try {
				newImage.setImage_data(listingImage.getBytes());
			} catch (IOException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving image");
			}

			listingImageMapper.insert(newImage);
		}

		return ResponseEntity.ok("Listing created successfully");
	}

	public static class ListingResponse {
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

}
	