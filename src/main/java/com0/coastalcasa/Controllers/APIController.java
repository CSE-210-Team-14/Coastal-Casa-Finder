package com0.coastalcasa.Controllers;

import java.util.List;


import org.springframework.web.bind.annotation.*;

import com0.coastalcasa.Mapper.LandlordMapper;
import com0.coastalcasa.Models.Landlord;

@RestController
public class APIController {

	private LandlordMapper landlordMapper;

	public APIController(LandlordMapper llm){
		this.landlordMapper = llm;
	}

	@GetMapping("/")
	public String index() {
		return "Coastal Casa Finder";
	}

	@GetMapping("/all")
	public List<Landlord> getAll(){
		return landlordMapper.findAll();
	}

}