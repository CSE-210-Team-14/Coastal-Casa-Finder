package com0.coastalcasa.Services;

import com0.coastalcasa.Mapper.LandlordMapper;
import com0.coastalcasa.Models.Landlord;
import com0.coastalcasa.Utils.JWTUtil;
import org.springframework.stereotype.Service;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class UserService {

    private LandlordMapper landlordMapper;

    public UserService(LandlordMapper landlordMapper) {
        this.landlordMapper = landlordMapper;
    }

    public String signup(HttpServletResponse response, Landlord landlord) {

        landlord.setPassword(new StrongPasswordEncryptor().encryptPassword(landlord.getPassword()));

        int result = landlordMapper.insert(landlord);

        if (result == 1) {
            String token = JWTUtil.sign(landlord);
            return token;
        } else {
            return null;
        }

    }

    public String login(HttpServletResponse response, Landlord landlord) {
        List<Landlord> landlords = landlordMapper.findLandLordByEmail(landlord);
        System.out.println(landlords);
        if (landlords.size() != 1) {
            return null;
        } else {
            Landlord landlordInDB = landlords.get(0);
            if (new StrongPasswordEncryptor().checkPassword(landlord.getPassword(), landlordInDB.getPassword())) {
                String token = JWTUtil.sign(landlord);
                System.out.println(token);
                return token;
            } else {
                return null;
            }
        }


    }



}
