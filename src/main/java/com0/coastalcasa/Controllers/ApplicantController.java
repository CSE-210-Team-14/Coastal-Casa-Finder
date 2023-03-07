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
import com0.coastalcasa.Services.ApplicantService;
import com0.coastalcasa.Utils.ResponseInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="ApplicantController")
@RestController
@RequestMapping("/applicants")
public class ApplicantController {

    private ApplicantService as;

    public ApplicantController(ApplicantService as){
		this.as = as;
	}

    // Get all applicants
    @ApiOperation(value="Get", notes="Gets All Applications in the database")
    @GetMapping("/all")
    public ResponseInfo<List<Applicant>> getAllApplicants() {
        try{
            List<Applicant> res = as.getAllApplicants();
            return ResponseInfo.success(res);
        }catch(Exception e){
            return ResponseInfo.fail("Fail to retrieve all");
        }
    }

    // Get applicants by Landlord ID
    @ApiOperation(value="Get ", notes="Gets All Applications by landlord ID")
    @GetMapping("get/{email}")
    public ResponseInfo<List<Applicant>> getApplicantsByEmail(@PathVariable(value = "email") String email) {
        try{
            List<Applicant> res = as.getApplicantsByEmail(email);
            return ResponseInfo.success(res);
        }catch(Exception e){
            return ResponseInfo.fail("Fail to get all email");
        }
    }

    // Create a new applicant
    @ApiOperation(value="Post", notes="Create Applicants by ID")
    @PostMapping("/create")
    public ResponseInfo createApplicant(@RequestParam("listingId") int listingId,
    @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
    @RequestParam("email") String email,@RequestParam("message") String message,
    @RequestParam("moveInDate") String moveInDateRange) {
        
        boolean res = as.createApplicant(listingId, firstName, lastName, email, message, moveInDateRange);
        if (res){
            return ResponseInfo.success("Created");
        }
        return ResponseInfo.fail("Failed to Create");
    
        
    }


    // Delete an applicant
    @ApiOperation(value="Post", notes="Delete Application by ID. NOTE: This should be called when the user approves or denies an application REQUIRES JWT TOKEN")
    @DeleteMapping("/delete/{id}")
    public ResponseInfo<String> deleteApplicant(@PathVariable(value = "id") Integer id) {
        boolean res = as.deleteApplicant(id);
        if(res){
            return ResponseInfo.success("Deleted");
        }
        return ResponseInfo.fail("Failed to delete");
    }

}