package com0.coastalcasa.Controllers;

import com0.coastalcasa.Mapper.LandlordMapper;
import com0.coastalcasa.Models.Landlord;
import com0.coastalcasa.Services.UserService;
import com0.coastalcasa.Utils.ResponseInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags="UserController")
@RestController
@RequestMapping("/landlord")
public class UserController {

    private UserService userService;

    public UserController(LandlordMapper llm, UserService userService){
        this.landlordMapper = llm;
        this.userService = userService;
    }

    private LandlordMapper landlordMapper;

    @ApiOperation(value="Get all landlords", notes="Get a list of landlords")
    @GetMapping("/all")
    public ResponseInfo<List<Landlord>> getAll(){
        List<Landlord> all = landlordMapper.findAll();
        return ResponseInfo.success(all);
    }

    @ApiOperation(value="Create a landlord user", notes="Create a landlord object: success if landlord has not been created before, else fail. NOTE: If the request is successful, JWT token will be put in the response header.")
    @PostMapping("/signup")
    private ResponseInfo<String> createLandLord(HttpServletResponse res, @RequestBody Landlord landlord){
        String token = userService.signup(res, landlord);
        if (token != null) {
            return ResponseInfo.success(token);
        } else {
            return ResponseInfo.fail();
        }
    }

    @ApiOperation(value="Landlord user login", notes="Check if the landlord user login information is correct. NOTE: If the request is successful, JWT token will be put in the response header.")
    @PostMapping("/signin")
    public ResponseInfo<String> login(HttpServletResponse response, @RequestBody Landlord landlord) {
        String token = userService.login(response, landlord);
        if (token != null) {
            return ResponseInfo.success(token);
        } else {
            return ResponseInfo.fail();
        }
    }

}
