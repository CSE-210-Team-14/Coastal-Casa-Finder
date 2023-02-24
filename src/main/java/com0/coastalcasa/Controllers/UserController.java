package com0.coastalcasa.Controllers;

import com0.coastalcasa.Mapper.LandlordMapper;
import com0.coastalcasa.Models.Landlord;
import com0.coastalcasa.Services.UserService;
import com0.coastalcasa.Utils.ResponseInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/landlord")
public class UserController {

    private UserService userService;

    public UserController(LandlordMapper llm, UserService userService){
        this.landlordMapper = llm;
        this.userService = userService;
    }

    private LandlordMapper landlordMapper;

    @GetMapping("/all")
    public ResponseInfo<List<Landlord>> getAll(){
        List<Landlord> all = landlordMapper.findAll();
        return ResponseInfo.success(all);
    }

    @PostMapping("/signup")
    private ResponseInfo<String> createLandLord(HttpServletResponse res, @RequestBody Landlord landlord){
        Boolean result = userService.signup(res, landlord);
        if (result == true) {
            return ResponseInfo.success("successful");
        } else {
            return ResponseInfo.fail("failed");
        }
    }

    @PostMapping("/signin")
    public ResponseInfo<String> login(HttpServletResponse response, @RequestBody Landlord landlord) {
        Boolean loginResult = userService.login(response, landlord);
        if (loginResult) {
            return ResponseInfo.success();
        } else {
            return ResponseInfo.fail();
        }
    }

}
