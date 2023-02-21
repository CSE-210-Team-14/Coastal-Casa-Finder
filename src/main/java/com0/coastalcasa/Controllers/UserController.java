package com0.coastalcasa.Controllers;

import com0.coastalcasa.Mapper.LandlordMapper;
import com0.coastalcasa.Mapper.ListingImageMapper;
import com0.coastalcasa.Mapper.ListingMapper;
import com0.coastalcasa.Models.Landlord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class UserController {

    public UserController(LandlordMapper llm){
        this.landlordMapper = llm;
    }

    private LandlordMapper landlordMapper;

    @GetMapping("/alllandlords")
    public List<Landlord> getAll(){
        List<Landlord> all = landlordMapper.findAll();
        return all;
    }

    @PostMapping("/landlordsignup")
    private ResponseEntity<?> createLandLord(@RequestBody Landlord landlord){
        int result = landlordMapper.insert(landlord);
        Map<String, Object> response = new HashMap<>();
        if (result == 1) {
            response.put("success", "true");
        } else {
            response.put("success", "false");
        }
        return ResponseEntity.ok(response);

    }

}
