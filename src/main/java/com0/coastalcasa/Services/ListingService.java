package com0.coastalcasa.Services;

import org.springframework.stereotype.Service;

import com0.coastalcasa.Mapper.ListingImageMapper;
import com0.coastalcasa.Mapper.ListingMapper;
import com0.coastalcasa.Models.Listing;
import com0.coastalcasa.Models.ListingImage;
import com0.coastalcasa.Utils.ListingResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ListingService {
    private ListingMapper listingMapper;
    private ListingImageMapper listingImageMapper;

    public ListingService(ListingMapper lm, ListingImageMapper lim){
		this.listingMapper = lm;
		this.listingImageMapper = lim;
    }
    
    public List<ListingResponse> findAll(){
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

    public List<ListingResponse> getListingByEmail(String email){
        List<Listing> listings = listingMapper.getListingsByLandlordEmail(email);
        List<ListingResponse> listingsWithImages = new ArrayList<>();

        for (Listing listing : listings) {
            List<ListingImage> imagesData = listingImageMapper.findByListingId(listing.getId());
            ListingResponse listingWithImage = new ListingResponse(listing, imagesData);
            listingsWithImages.add(listingWithImage);
        }
        
        return listingsWithImages;
    }

    public List<ListingResponse> search(Map<String, String> requestParams){
        double price = -1.0;
		int numBath = -1;
		int numBed = -1;
		String location = null;
		List<String> amenities = new ArrayList<>();
		List<ListingResponse> responses = new ArrayList<>();
		List<Listing> listings = new ArrayList<>();
		String name = null;
		if (requestParams.get(name)!= null){
			name = "%" + requestParams.get(name) + "%";
		}
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
				listings.addAll(listingMapper.searchListings(requestParams.get("name"),requestParams.get("landlord_email"),requestParams.get("description"),location,price,numBath,numBed,"%"+a+"%"));
			}
		}
		else{
			listings.addAll(listingMapper.searchListings(requestParams.get("name"),requestParams.get("landlord_email"),requestParams.get("description"),location,price,numBath,numBed,null));
		}
		 
		System.out.println(listings);
		for (Listing listing : listings) {
			System.out.println("Here"+listing.getLandlord_email());
			List<ListingImage> images = listingImageMapper.findByListingId(listing.getId());
			ListingResponse response = new ListingResponse(listing, images);
			responses.add(response);
		}
        return responses;
    }

    public boolean update(int id,String name,String landlordEmail,
	String description,String location, double price,int numBathrooms,
	int numBedrooms,String amenities,
	List<MultipartFile> listingImages){

        Listing existingListing = listingMapper.findById(id);

		existingListing.setName(name);
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
			} catch (Exception e) {
				return false;
			}

			listingImageMapper.insert(newImage);
		}

        return true;
    }

    public boolean create(String name,String landlordEmail,
    String description, String location,
    double price, int numBathrooms,
    int numBedrooms, String amenities,
    List<MultipartFile> listingImages){

        // Create the listing object
		Listing newListing = new Listing();
		newListing.setName(name);
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
			} catch (Exception e) {
				return false;
			}

			listingImageMapper.insert(newImage);
		}
        return true;
    }

    public boolean delete(int id){
        try{
            listingMapper.deleteListing(id);
		    listingImageMapper.deleteListing(id);
        }catch (Exception e) {
            return false;
        }
        return true;
    }
}
