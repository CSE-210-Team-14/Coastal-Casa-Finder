package com0.coastalcasa.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com0.coastalcasa.Mapper.ApplicantMapper;
import com0.coastalcasa.Mapper.LandlordMapper;
import com0.coastalcasa.Mapper.ListingImageMapper;
import com0.coastalcasa.Mapper.ListingMapper;
import com0.coastalcasa.Models.Applicant;
import com0.coastalcasa.Models.Listing;


@Service
public class ApplicantService {

    private ListingMapper listingMapper;
    private ListingImageMapper listingImageMapper;
    private ApplicantMapper applicantMapper;
    private LandlordMapper landlordMapper;

    public ApplicantService(ListingMapper lm, ListingImageMapper lim, ApplicantMapper am,LandlordMapper llm){
        this.listingMapper = lm;
        this.listingImageMapper = lim;
        this.applicantMapper = am;
        this.landlordMapper = llm;
    }

    // Get all applicants

    public List<Applicant> getAllApplicants() {
        return applicantMapper.findAll();
    }

    // Get applicants by Landlord ID

    public List<Applicant> getApplicantsByEmail(String email) {
        List<Listing> listings = listingMapper.getListingsByLandlordEmail(email);
        List<Applicant> applicants = new ArrayList<>();

        for (Listing listing : listings){
            applicants.addAll(applicantMapper.findByListingId(listing.getId()));
        }
        
        return applicants;
    }

    // Create a new applicant
    public boolean createApplicant( int listingId,
    String firstName,  String lastName,
    String email, String message,
    String moveInDateRange) {
        try{
            Applicant applicant = new Applicant();
            applicant.setListing_id(listingId);
            applicant.setFirst_name(firstName);
            applicant.setLast_name(lastName);
            applicant.setEmail(email);
            applicant.setMessage(message);
            applicant.setMove_in_date_range(moveInDateRange);

            applicantMapper.insert(applicant);
        }catch(Exception e){
            return false;
        }
        return true;
    }


    // Delete an applicant
    public boolean deleteApplicant(Integer id) {
        Applicant applicant = applicantMapper.findById(id);
        if (applicant == null) {
            return false;
        }

        applicantMapper.delete(id);
        return true;
    }
}
