package com0.coastalcasa.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com0.coastalcasa.Mapper.ApplicantMapper;
import com0.coastalcasa.Mapper.LandlordMapper;
import com0.coastalcasa.Mapper.ListingImageMapper;
import com0.coastalcasa.Mapper.ListingMapper;
import com0.coastalcasa.Models.Applicant;
import com0.coastalcasa.Models.Landlord;
import com0.coastalcasa.Models.Listing;
import com0.coastalcasa.Utils.ResponseInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="ApplicantController")
@RestController
@RequestMapping("/applicants")
public class ApplicantController {

    private ListingMapper listingMapper;
	private ListingImageMapper listingImageMapper;
    private ApplicantMapper applicantMapper;
    private LandlordMapper landlordMapper;

    public ApplicantController(ListingMapper lm, ListingImageMapper lim, ApplicantMapper am,LandlordMapper llm){
		this.listingMapper = lm;
		this.listingImageMapper = lim;
        this.applicantMapper = am;
        this.landlordMapper = llm;
	}

    // Get all applicants
    @ApiOperation(value="Get", notes="Gets All Applications in the database")
    @GetMapping("/all")
    public ResponseInfo<List<Applicant>> getAllApplicants() {
        return ResponseInfo.success(applicantMapper.findAll());
    }

    // Get applicants by Landlord ID
    @ApiOperation(value="Get ", notes="Gets All Applications by landlord ID")
    @GetMapping("get/{email}")
    public ResponseInfo<List<Applicant>> getApplicantsByEmail(@PathVariable(value = "email") String email) {
        List<Listing> listings = listingMapper.getListingsByLandlordEmail(email);
        List<Applicant> applicants = new ArrayList<>();

        for (Listing listing : listings){
            applicants.addAll(applicantMapper.findByListingId(listing.getId()));
        }
        
        return ResponseInfo.success(applicants);
    }

    // Create a new applicant
    @ApiOperation(value="Post", notes="Create Applicants by ID")
    @PostMapping("/create")
    public ResponseInfo<Applicant> createApplicant(@RequestParam("listingId") int listingId,
    @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
    @RequestParam("email") String email,@RequestParam("message") String message,
    @RequestParam("moveInDate") String moveInDateRange) {
        Applicant applicant = new Applicant();
        System.out.println(firstName);
        applicant.setListing_id(listingId);
        applicant.setFirst_name(firstName);
        applicant.setLast_name(lastName);
        applicant.setEmail(email);
        applicant.setMessage(message);
        applicant.setMove_in_date_range(moveInDateRange);

        applicantMapper.insert(applicant);

        return ResponseInfo.success(applicant);
    }


    // Delete an applicant
    @ApiOperation(value="Post", notes="Delete Application by ID. NOTE: This should be called when the user approves or denies an application")
    @DeleteMapping("/delete/{id}")
    public ResponseInfo<String> deleteApplicant(@PathVariable(value = "id") Integer id) {
        Applicant applicant = applicantMapper.findById(id);
        if (applicant == null) {
            return ResponseInfo.fail();
        }

        applicantMapper.delete(id);
        return ResponseInfo.success("Deleted");
    }

}