package com0.coastalcasa.Controllers;

import java.util.*;

import com0.coastalcasa.Models.Landlord;
import com0.coastalcasa.Repo.LandlordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class APIController {

	@GetMapping("/")
	public String index() {
		return "Coastal Casa Finder";
	}

	@Autowired
	private LandlordRepo landlordRepo;

	

	@GetMapping("/landlordSignUp")
	public String landlordSignUp() {
		return "Sign Up";
	}

	@GetMapping("/landlords")
	public List<Landlord> landlords() {
		return landlordRepo.findAll();
	}


}