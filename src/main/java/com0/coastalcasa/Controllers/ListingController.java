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
import java.util.List;


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
	@GetMapping("/")
	public ResponseInfo<List<ListingResponse>> getLandlordListings(@RequestParam String landlord_email) {
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

	@ApiOperation(value="Create listing", notes="Create a listing with the required parameters. NOTE: This endpoint requires the request headers to contain the JWT token.")
	@PostMapping(value = "/createlisting", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseInfo<String> createListing(@RequestParam("landlord_email") String landlordEmail,
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
				return ResponseInfo.fail("Error saving image");
			}

			listingImageMapper.insert(newImage);
		}

		return ResponseInfo.success("Listing created successfully");
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
	