package com0.coastalcasa.Controllers;

import com0.coastalcasa.Mapper.ListingImageMapper;
import com0.coastalcasa.Mapper.ListingMapper;
import com0.coastalcasa.Models.Listing;
import com0.coastalcasa.Models.ListingImage;
import com0.coastalcasa.Utils.ResponseInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Api(tags="ListingController")
@RestController
@RequestMapping("/listings")
public class ListingController {

	private ListingMapper listingMapper;
	
	
	private ListingImageMapper listingImageMapper;

	public ListingController(ListingMapper lm, ListingImageMapper lim){
		this.listingMapper = lm;
		this.listingImageMapper = lim;
	}

	@ApiOperation(value="ResponseInfo Index", notes="Return ResponseInfo.success(). NOTE: This endpoint requires the request headers to contain the JWT token.")
	@GetMapping("/test")
	public ResponseInfo<String> index() {
		return ResponseInfo.success();
	}


	// get all listings by a landlord email
	@ApiOperation(value="Get landlord listings", notes="Get all listings of a landlord by the landlord's email.")
	@GetMapping("/{email}")
	public ResponseInfo<List<ListingResponse>> getLandlordListings(@PathVariable("email") String landlord_email) {
		try {
			List<Listing> listings = listingMapper.getListingsByLandlordEmail(landlord_email);
			List<ListingResponse> listingsWithImages = new ArrayList<>();
			
			for (Listing listing : listings) {
				List<ListingImage> imagesData = listingImageMapper.findByListingId(listing.getId());
				ListingResponse listingWithImage = new ListingResponse(listing, imagesData);
				listingsWithImages.add(listingWithImage);
			}
			return ResponseInfo.success(listingsWithImages);
		} catch (Exception e) {
			return ResponseInfo.fail(e.getMessage());
		}
	}


	// get all listings in database
	@ApiOperation(value="Get listings", notes="Get all listings in the database.")
	@GetMapping("/alllistings")
	public ResponseInfo<List<ListingResponse>> getListings() {
		List<Listing> listings = listingMapper.findAll();
		List<ListingResponse> responses = new ArrayList<>();
		
		for (Listing listing : listings) {
			System.out.println("Here"+listing.getLandlord_email());
			List<ListingImage> images = listingImageMapper.findByListingId(listing.getId());

			ListingResponse response = new ListingResponse(listing, images);
			responses.add(response);
		}
		
		return ResponseInfo.success(responses);
	}

	// get all listings in database
	@ApiOperation(value="Search Listings", notes="Search Listings with required parameters")
	@GetMapping("/search")
	public ResponseInfo<List<ListingResponse>> searchListings(@RequestParam Map<String, String> requestParams) {
		System.out.println(requestParams);
		double price = -1.0;
		int numBath = -1;
		int numBed = -1;
		String location = null;
		List<String> amenities = new ArrayList<>();
		List<ListingResponse> responses = new ArrayList<>();
		List<Listing> listings = new ArrayList<>();

		if(requestParams.get("price")!=null){
			price = Double.parseDouble(requestParams.get("price"));
		}
		if(requestParams.get("num_bathrooms")!=null){
			numBath = Integer.parseInt(requestParams.get("num_bathrooms"));
		}
		if(requestParams.get("num_bedrooms")!=null){
			numBed = Integer.parseInt(requestParams.get("num_bedrooms"));
		}
		if(requestParams.get("location")!=null){
			location = "%"+requestParams.get("location")+"%";
		}
		if(requestParams.get("amenities")!=null){
			amenities = new ArrayList<>(Arrays.asList(requestParams.get("amenities").split(",")));
		}
		if(amenities.size() != 0){
			for (String a : amenities){
				listings.addAll(listingMapper.searchListings(requestParams.get("landlord_email"),requestParams.get("description"),location,price,numBath,numBed,"%"+a+"%"));
			}
		}
		else{
			listings.addAll(listingMapper.searchListings(requestParams.get("landlord_email"),requestParams.get("description"),location,price,numBath,numBed,null));
		}
		 
		System.out.println(listings);
		for (Listing listing : listings) {
			System.out.println("Here"+listing.getLandlord_email());
			List<ListingImage> images = listingImageMapper.findByListingId(listing.getId());
			ListingResponse response = new ListingResponse(listing, images);
			responses.add(response);
		}
		
		return ResponseInfo.success(responses);

	}
	@ApiOperation(value="Update Listing", notes="Create a listing with the required parameters. NOTE: This endpoint requires the request headers to contain the JWT token.")
	@PutMapping(value = "/update/{id}",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseInfo<String> updateListing(@PathVariable("id") int id, @RequestParam("landlord_email") String landlordEmail,
	@RequestParam("description") String description, @RequestParam("location") String location,
	@RequestParam("price") double price, @RequestParam("num_bathrooms") int numBathrooms,
	@RequestParam("num_bedrooms") int numBedrooms, @RequestParam("amenities") String amenities,
	@RequestParam("images") List<MultipartFile> listingImages) {

		Listing existingListing = listingMapper.findById(id);

		existingListing.setLandlord_email(landlordEmail);
		existingListing.setDescription(description);
		existingListing.setLocation(location);
		existingListing.setPrice(price);
		existingListing.setNum_bathrooms(numBathrooms);
		existingListing.setNum_bedrooms(numBedrooms);
		existingListing.setAmenities(amenities);

		listingMapper.updateListing(existingListing);
		

		for (MultipartFile listingImage : listingImages) {
			ListingImage newImage = new ListingImage();
			newImage.setListing_id(existingListing.getId());

			try {
				newImage.setImage_data(listingImage.getBytes());
			} catch (IOException e) {
				return ResponseInfo.fail("Error saving image");
			}

			listingImageMapper.insert(newImage);
		}

		return ResponseInfo.success("Listing updated successfully");
	}


	@ApiOperation(value="Create listing", notes="Create a listing with the required parameters. NOTE: This endpoint requires the request headers to contain the JWT token.")
	@PostMapping(value = "/createlisting", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseInfo<String> createListing(@RequestParam("landlord_email") String landlordEmail,
			@RequestParam("description") String description, @RequestParam("location") String location,
			@RequestParam("price") double price, @RequestParam("num_bathrooms") int numBathrooms,
			@RequestParam("num_bedrooms") int numBedrooms, @RequestParam("amenities") String amenities,
			@RequestParam("images") List<MultipartFile> listingImages) {
			
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
				return ResponseInfo.fail("Error saving image");
			}

			listingImageMapper.insert(newImage);
		}

		return ResponseInfo.success("Listing created successfully");
	}
	@ApiOperation(value = "Delete", notes = "Delete listing by listing id. NOTE: This endpoint requires the request headers to contain the JWT token.")
	@DeleteMapping("/delete/{id}")
	public ResponseInfo<String> deleteListing(@PathVariable Integer id) {
		
		listingMapper.deleteListing(id);
		listingImageMapper.deleteListing(id);
		return ResponseInfo.success("Listing Deleted");
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
	