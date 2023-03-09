package com0.coastalcasa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com0.coastalcasa.Controllers.ListingController;
import com0.coastalcasa.Services.ListingService;
import com0.coastalcasa.Utils.ListingResponse;
import com0.coastalcasa.Utils.ResponseInfo;


public class ListingControllerTest {
	
	private ListingService listingService;

	private ListingController listingController;
	
    @BeforeEach
    void setUp() {
        listingService = mock(ListingService.class);
        listingController = new ListingController(listingService);
    }
    
	@Test
	public void testIndex() {
		ResponseInfo<String> response = listingController.index();
		assertNotNull(response);
		assertEquals(ResponseInfo.success().getStatus(), response.getStatus());
	}
	
	@Test
    void testGetLandlordListings() {
        String email = "test@email.com";
        ResponseInfo<List<ListingResponse>> response = listingController.getLandlordListings(email);
        assertNotNull(response);
        assertEquals(ResponseInfo.success().getStatus(), response.getStatus());
    }
	
	@Test
	public void testGetListings() {
		List<ListingResponse> listings = new ArrayList<>();
		ResponseInfo<List<ListingResponse>> response = listingController.getListings();
		assertNotNull(response);
		assertEquals(ResponseInfo.success().getStatus(), response.getStatus());
	}
	
	@Test
	public void testSearchListings() {
		Map<String, String> requestParams = new HashMap<>();
		requestParams.put("location", "San Diego");
		
		ResponseInfo<List<ListingResponse>> response = listingController.searchListings(requestParams);
		assertNotNull(response);
		assertEquals(ResponseInfo.success().getStatus(), response.getStatus());
		
	}
	
	@Test
	public void testCreateListing() {
		String name = "Test Listing";
		String landlordEmail = "test@test.com";
		String description = "Test description";
		String location = "Test location";
		double price = 100.0;
		int numBathrooms = 1;
		int numBedrooms = 2;
		String amenities = "Test amenities";
		List<MultipartFile> listingImages = new ArrayList<>();
		
		when(listingService.create(name, landlordEmail, description, location, price, numBathrooms, numBedrooms, amenities, listingImages)).thenReturn(true);
		
		ResponseInfo<String> response = listingController.createListing(name, landlordEmail, description, location, price, numBathrooms, numBedrooms, amenities, listingImages);
		assertNotNull(response);
		assertEquals(ResponseInfo.success().getStatus(), response.getStatus());
		assertEquals("Listing created successfully", response.getData());
	}
	
	@Test
	public void testUpdateListing() {
		int id = 1;
		String name = "Test Listing";
		String landlordEmail = "test@test.com";
		String description = "Test description";
		String location = "Test location";
		double price = 100.0;
		int numBathrooms = 1;
		int numBedrooms = 2;
		String amenities = "Test amenities";
		List<MultipartFile> listingImages = new ArrayList<>();
		
		when(listingService.update(id, name, landlordEmail, description, location, price, numBathrooms, numBedrooms, amenities, listingImages)).thenReturn(true);
		
		ResponseInfo<String> response = listingController.updateListing(id, name, landlordEmail, description, location, price, numBathrooms, numBedrooms, amenities, listingImages);
		assertNotNull(response);
		assertEquals(ResponseInfo.success().getStatus(), response.getStatus());
    }
}