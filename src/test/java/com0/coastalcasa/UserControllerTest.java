package com0.coastalcasa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import com0.coastalcasa.Controllers.UserController;
import com0.coastalcasa.Mapper.LandlordMapper;
import com0.coastalcasa.Models.Landlord;
import com0.coastalcasa.Services.UserService;
import com0.coastalcasa.Utils.ResponseInfo;

public class UserControllerTest {
    
    @Mock
    private LandlordMapper landlordMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testGetAll() {
        List<Landlord> landlords = new ArrayList<>();
        landlords.add(new Landlord("john.doe@example.com", "password"));
        landlords.add(new Landlord("jane.doe@example.com", "password"));

        when(landlordMapper.findAll()).thenReturn(landlords);

        ResponseInfo<List<Landlord>> response = userController.getAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(landlords, response.getData());
    }

    @Test
    public void testCreateLandlord() {
        Landlord landlord = new Landlord("john.doe@example.com", "password");

        when(userService.signup(any(HttpServletResponse.class), eq(landlord))).thenReturn("token");

        ResponseInfo<String> response = userController.createLandLord(new MockHttpServletResponse(), landlord);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("token", response.getData());
    }

    @Test
    public void testCreateLandlordFail() {
        Landlord landlord = new Landlord("john.doe@example.com", "password");

        when(userService.signup(any(HttpServletResponse.class), eq(landlord))).thenReturn(null);

        ResponseInfo<String> response = userController.createLandLord(new MockHttpServletResponse(), landlord);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    @Test
    public void testLogin() {
        Landlord landlord = new Landlord("john.doe@example.com", "password");

        when(userService.login(any(HttpServletResponse.class), eq(landlord))).thenReturn("token");

        ResponseInfo<String> response = userController.login(new MockHttpServletResponse(), landlord);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("token", response.getData());
    }

    @Test
    public void testLoginFail() {
        Landlord landlord = new Landlord("john.doe@example.com", "password");

        when(userService.login(any(HttpServletResponse.class), eq(landlord))).thenReturn(null);

        ResponseInfo<String> response = userController.login(new MockHttpServletResponse(), landlord);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }
}
