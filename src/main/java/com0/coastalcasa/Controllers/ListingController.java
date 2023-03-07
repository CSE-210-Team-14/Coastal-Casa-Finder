package com0.coastalcasa.Controllers;

import com0.coastalcasa.Mapper.ListingImageMapper;
import com0.coastalcasa.Mapper.ListingMapper;
import com0.coastalcasa.Models.Listing;
import com0.coastalcasa.Models.ListingImage;
import com0.coastalcasa.Services.ListingService;
import com0.coastalcasa.Utils.ListingResponse;
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

	private ListingService listingService;

	public ListingController(ListingService ls){
		this.listingService = ls;
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
			 	List<ListingResponse> listingsWithImages = listingService.getListingByEmail(landlord_email);
				return ResponseInfo.success(listingsWithImages);
			
		} catch (Exception e) {
			return ResponseInfo.fail(e.getMessage());
		}
	}


	// get all listings in database
	@ApiOperation(value="Get listings", notes="Get all listings in the database.")
	@GetMapping("/alllistings")
	public ResponseInfo<List<ListingResponse>> getListings() {
		try{
			List <ListingResponse> responses = listingService.findAll();
			return ResponseInfo.success(responses);
		}catch (Exception e) {
			return ResponseInfo.fail(e.getMessage());
		}
	}

	// get all listings in database
	@ApiOperation(value="Search Listings", notes="Search Listings with required parameters")
	@GetMapping("/search")
	public ResponseInfo<List<ListingResponse>> searchListings(@RequestParam Map<String, String> requestParams) {
		try{
			List <ListingResponse> responses = listingService.search(requestParams);
			return ResponseInfo.success(responses);
		}catch (Exception e){
			return ResponseInfo.fail(e.getMessage());
		}

	}

	@ApiOperation(value="Update Listing", notes="Create a listing with the required parameters. NOTE: This endpoint requires the request headers to contain the JWT token.")
	@PutMapping(value = "/update/{id}",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseInfo<String> updateListing(@PathVariable("id") int id,@RequestParam("name")String name, @RequestParam("landlord_email") String landlordEmail,
	@RequestParam("description") String description, @RequestParam("location") String location,
	@RequestParam("price") double price, @RequestParam("num_bathrooms") int numBathrooms,
	@RequestParam("num_bedrooms") int numBedrooms, @RequestParam("amenities") String amenities,
	@RequestParam("images") List<MultipartFile> listingImages) {

		boolean res = listingService.update(id, name, landlordEmail, description, location, price, numBathrooms, numBedrooms, amenities, listingImages);
		if(res){
			return ResponseInfo.success("Listing updated successfully");
		}else{
			return ResponseInfo.fail("Failed to Update");
		}
		
	}


	@ApiOperation(value="Create listing", notes="Create a listing with the required parameters. NOTE: This endpoint requires the request headers to contain the JWT token.")
	@PostMapping(value = "/createlisting", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseInfo<String> createListing(@RequestParam("name") String name,@RequestParam("landlord_email") String landlordEmail,
			@RequestParam("description") String description, @RequestParam("location") String location,
			@RequestParam("price") double price, @RequestParam("num_bathrooms") int numBathrooms,
			@RequestParam("num_bedrooms") int numBedrooms, @RequestParam("amenities") String amenities,
			@RequestParam("images") List<MultipartFile> listingImages) {
			

			boolean res = listingService.create( name, landlordEmail, description, location, price, numBathrooms, numBedrooms, amenities, listingImages);
			if(res){
				return ResponseInfo.success("Listing created successfully");
			}else{
				return ResponseInfo.fail("Failed to create");
			}
	}

	@ApiOperation(value = "Delete", notes = "Delete listing by listing id. NOTE: This endpoint requires the request headers to contain the JWT token.")
	@DeleteMapping("/delete/{id}")
	public ResponseInfo<String> deleteListing(@PathVariable Integer id) {
		
		boolean res = listingService.delete(id);
		if(res){
			return ResponseInfo.success("Listing deleted successfully");
		}else{
			return ResponseInfo.fail("Failed to delet");
		}
	}

	

}
	