package com0.coastalcasa;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com0.coastalcasa.Controllers.ApplicantController;
import com0.coastalcasa.Models.Applicant;
import com0.coastalcasa.Services.ApplicantService;
import com0.coastalcasa.Utils.ResponseCodeEnums;
import com0.coastalcasa.Utils.ResponseInfo;

public class ApplicantControllerTest {
    
    @Mock
    private ApplicantService applicantService;
    
    @InjectMocks
    private ApplicantController applicantController;
    
    @Test
    public void testGetAllApplicants_Success() {
        // Arrange
        List<Applicant> applicants = new ArrayList<>();
        Applicant applicant = new Applicant();
        applicants.add(applicant);
        when(applicantService.getAllApplicants()).thenReturn(applicants);
        
        // Act
        ResponseInfo<List<Applicant>> response = applicantController.getAllApplicants();
        
        // Assert
        assertEquals(ResponseCodeEnums.SUCCESS.getCode(), response.getStatus());
        assertEquals(applicants, response.getData());
    }
    
    @Test
    public void testGetAllApplicants_Failure() {
        // Arrange
        when(applicantService.getAllApplicants()).thenThrow(new RuntimeException());
        
        // Act
        ResponseInfo<List<Applicant>> response = applicantController.getAllApplicants();
        
        // Assert
        assertEquals(ResponseCodeEnums.FAIL.getCode(), response.getStatus());
        assertNull(response.getData());
    }
    
    @Test
    public void testGetApplicantsByEmail_Success() {
        // Arrange
        String email = "test@example.com";
        List<Applicant> applicants = new ArrayList<>();
        Applicant applicant = new Applicant();
        applicants.add(applicant);
        when(applicantService.getApplicantsByEmail(email)).thenReturn(applicants);
        
        // Act
        ResponseInfo<List<Applicant>> response = applicantController.getApplicantsByEmail(email);
        
        // Assert
        assertEquals(ResponseCodeEnums.SUCCESS.getCode(), response.getStatus());
        assertEquals(applicants, response.getData());
    }
    
    @Test
    public void testGetApplicantsByEmail_Failure() {
        // Arrange
        String email = "test@example.com";
        when(applicantService.getApplicantsByEmail(email)).thenThrow(new RuntimeException());
        
        // Act
        ResponseInfo<List<Applicant>> response = applicantController.getApplicantsByEmail(email);
        
        // Assert
        assertEquals(ResponseCodeEnums.FAIL.getCode(), response.getStatus());
        assertNull(response.getData());
    }
    
    @Test
    public void testCreateApplicant_Success() {
        // Arrange
        int listingId = 1;
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String message = "I am interested in this listing";
        String moveInDateRange = "2022-01-01 to 2022-02-01";
        when(applicantService.createApplicant(listingId, firstName, lastName, email, message, moveInDateRange)).thenReturn(true);
        
        // Act
        ResponseInfo response = applicantController.createApplicant(listingId, firstName, lastName, email, message, moveInDateRange);
        
        // Assert
        assertEquals(ResponseCodeEnums.SUCCESS.getCode(), response.getStatus());
        assertEquals("Created", response.getMessage());
    }
    
    @Test
    public void testCreateApplicant_Failure() {
        // Arrange
        int listingId = 1;
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String message = "I am interested in this listing";
        String moveInDateRange = "2022-01-01 to 2022-02-01";
        when(applicantService.createApplicant(listingId, firstName, lastName, email, message, moveInDateRange)).thenReturn(false);
        
        // Act
        ResponseInfo response = applicantController.createApplicant(listingId, firstName, lastName, email, message, moveInDateRange);
        
        // Assert
        assertEquals(ResponseCodeEnums.SUCCESS.getCode(), response.getStatus());
    }
}