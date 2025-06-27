package com.mhd.accountManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhd.accountManagement.Clients.VitalSignManagementMS.VitalSignManagementClient;
import com.mhd.accountManagement.model.DTO.PatientDTO.PatientRegisterRequest;
import com.mhd.accountManagement.model.DTO.PatientDTO.PatientRegisterResponse;
import com.mhd.accountManagement.services.PatientService;
import com.mhd.accountManagement.services.authentication.CustomUserDetailsService;
import com.mhd.accountManagement.services.authentication.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;


@WebMvcTest(
        controllers = PatientController.class,
        excludeAutoConfiguration = {
                SecurityException.class,
                UserDetailsServiceAutoConfiguration.class
        }
)
@TestPropertySource(properties = {
        "application.config.vitalSignManagement-url=http://localhost:8080" // Provide a valid dummy URL
})
@AutoConfigureMockMvc(addFilters = false)
public class PatientControllerTest_Functional {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PatientService patientService;

    @MockitoBean
    private VitalSignManagementClient vitalSignManagementClient;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper mapper;



    @Test
    void registerPatient_functionalTest_validInput() throws Exception{
        String json = """
                {
                  "name": "john",
                  "dateOfBirth": "1990-05-15",
                  "allergies": "Peanuts, Penicillin",
                  "user": {
                    "username": "john",
                    "password": "verySecurePassword123",
                    "addresses": [
                      {
                        "country": "USA",
                        "city": "Los Angeles",
                        "streetName": "Sunset Boulevard",
                        "streetNumber": "400",
                        "label": "Home"
                      }
                    ],
                    "contactInfoDTO": {
                      "contacts": {
                        "PHONE": "+11234567890",
                        "EMAIL": "jane.doe@example.com"
                      }
                    },
                    "role": "PATIENT"
                  }
                }""";

        PatientRegisterRequest request = mapper.readValue(json, PatientRegisterRequest.class);
        Mockito.when(patientService.register(request))
                .thenReturn(new PatientRegisterResponse(2, "some-token-123"));

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/register")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.activationToken").value("some-token-123"));
    }


    @Test
    void registerPatient_functionalTest_DOBIsInFuture() throws Exception{
        String json = """
                {
                   "name": "john",
                   "dateOfBirth": "2026-05-15",
                   "allergies": "Peanuts, Penicillin",
                   "user": {
                     "username": "john",
                     "password": "verySecurePassword123",
                     "addresses": [
                       {
                         "country": "USA",
                         "city": "Los Angeles",
                         "streetName": "Sunset Boulevard",
                         "streetNumber": "400",
                         "label": "Home"
                       }
                     ],
                     "contactInfoDTO": {
                       "contacts": {
                         "PHONE": "+11234567890",
                         "EMAIL": "jane.doe@example.com"
                       }
                     },
                     "role": "PATIENT"
                   }
                 }""";

        PatientRegisterRequest request = mapper.readValue(json, PatientRegisterRequest.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/register")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Date of birth must be in the past"));

    }

    @Test
    void registerPatient_functionalTest_NameWithNumbers() throws Exception{
        String json = """
                {
                    "name": "john123",
                    "dateOfBirth": "1990-05-15",
                    "allergies": "Peanuts, Penicillin",
                    "user": {
                      "username": "john",
                      "password": "verySecurePassword123",
                      "addresses": [
                        {
                          "country": "USA",
                          "city": "Los Angeles",
                          "streetName": "Sunset Boulevard",
                          "streetNumber": "400",
                          "label": "Home"
                        }
                      ],
                      "contactInfoDTO": {
                        "contacts": {
                          "PHONE": "+11234567890",
                          "EMAIL": "jane.doe@example.com"
                        }
                      },
                      "role": "PATIENT"
                    }
                  }""";

        PatientRegisterRequest request = mapper.readValue(json, PatientRegisterRequest.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/register")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Patient name cannot contain numbers"));

    }

    @Test
    void registerPatient_functionalTest_DOBIsInFutureAndInValidEmail() throws Exception{
        String json = """
                {
                  "name": "john",
                  "dateOfBirth": "2030-01-01",
                  "allergies": "Peanuts, Penicillin",
                  "user": {
                    "username": "john",
                    "password": "verySecurePassword123",
                    "addresses": [
                      {
                        "country": "USA",
                        "city": "Los Angeles",
                        "streetName": "Sunset Boulevard",
                        "streetNumber": "400",
                        "label": "Home"
                      }
                    ],
                    "contactInfoDTO": {
                      "contacts": {
                        "PHONE": "+11234567890",
                        "EMAIL": "invalid-email-format"
                      }
                    },
                    "role": "PATIENT"
                  }
                }""";


        mockMvc.perform(MockMvcRequestBuilders.post("/patient/register")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Date of birth must be in the past")))
                .andExpect(content().string(containsString("Invalid contact info")));

    }

    @Test
    void registerPatient_functionalTest_EmptyUsernameAndInvalidPhone() throws Exception{
        String json = """
                {
                  "name": "",
                  "dateOfBirth": "2015-01-01",
                  "allergies": "Peanuts, Penicillin",
                  "user": {
                    "username": "john",
                    "password": "verySecurePassword123",
                    "addresses": [
                      {
                        "country": "USA",
                        "city": "Los Angeles",
                        "streetName": "Sunset Boulevard",
                        "streetNumber": "400",
                        "label": "Home"
                      }
                    ],
                    "contactInfoDTO": {
                      "contacts": {
                        "PHONE": "asdasd",
                        "EMAIL": "invalid-email-format"
                      }
                    },
                    "role": "PATIENT"
                  }
                }""";


        mockMvc.perform(MockMvcRequestBuilders.post("/patient/register")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Patient name can't be null")))
                .andExpect(content().string(containsString("Invalid contact info")));

    }



    @Test
    void updatePatient_functionalTest_validInput() throws Exception {
        String json = """
        {
          "id": 1,
          "name": "Ahmed Al Farsi",
          "dateOfBirth": "1990-06-15",
          "allergies": "Peanuts, Dust",
          "contactInfo": {
            "id": 3,
            "PHONE": [
              "0551234567",
              "0509876543"
            ],
            "EMAIL": "ahmed.alfarsi@example.com"
          },
          "addresses": [
            {
              "id": 10,
              "country": "UAE",
              "city": "Dubai",
              "streetName": "Sheikh Zayed Road",
              "streetNumber": "101",
              "label": "HOME"
            },
            {
              "id": 11,
              "country": "UAE",
              "city": "Abu Dhabi",
              "streetName": "Corniche",
              "streetNumber": "202",
              "label": "WORK"
            }
          ]
        }
    """;

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/update")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isAccepted());
    }


    @Test
    void updatePatient_functionalTest_futureDOB() throws Exception {
        String json = """
        {
          "id": 1,
          "name": "Ahmed Al Farsi",
          "dateOfBirth": "2030-06-15",
          "allergies": "Peanuts, Dust",
          "contactInfo": {
            "id": 3,
            "PHONE": [
              "0551234567"
            ],
            "EMAIL": "ahmed.alfarsi@example.com"
          },
          "addresses": [
            {
              "id": 10,
              "country": "UAE",
              "city": "Dubai",
              "streetName": "Sheikh Zayed Road",
              "streetNumber": "101",
              "label": "HOME"
            }
          ]
        }
    """;

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/update")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Date of birth must be in the past")));
    }



    @Test
    void updatePatient_functionalTest_nameWithNumbers() throws Exception {
        String json = """
        {
          "id": 1,
          "name": "Ahmed123",
          "dateOfBirth": "1990-06-15",
          "allergies": "Dust",
          "contactInfo": {
            "id": 3,
            "PHONE": [
              "0551234567"
            ],
            "EMAIL": "ahmed.alfarsi@example.com"
          },
          "addresses": [
            {
              "id": 10,
              "country": "UAE",
              "city": "Dubai",
              "streetName": "SZR",
              "streetNumber": "101",
              "label": "HOME"
            }
          ]
        }
    """;

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/update")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Patient name cannot contain numbers")));
    }

    @Test
    void updatePatient_functionalTest_tooManyAddresses() throws Exception {
        String json = """
        {
          "id": 1,
          "name": "Ahmed Al Farsi",
          "dateOfBirth": "1990-06-15",
          "allergies": "Peanuts",
          "contactInfo": {
            "id": 3,
            "PHONE": ["0551234567"],
            "EMAIL": "ahmed.alfarsi@example.com"
          },
          "addresses": [
            {
              "id": 10, "country": "UAE", "city": "Dubai", "streetName": "A", "streetNumber": "1", "label": "A"
            },
            {
              "id": 11, "country": "UAE", "city": "Abu Dhabi", "streetName": "B", "streetNumber": "2", "label": "B"
            },
            {
              "id": 12, "country": "UAE", "city": "Sharjah", "streetName": "C", "streetNumber": "3", "label": "C"
            },
            {
              "id": 13, "country": "UAE", "city": "Ajman", "streetName": "D", "streetNumber": "4", "label": "D"
            }
          ]
        }
    """;

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/update")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("You can provide at most 3 addresses")));
    }

    @Test
    void updatePatient_functionalTest_invalidEmail() throws Exception {
        String json = """
        {
          "id": 1,
          "name": "Ahmed Al Farsi",
          "dateOfBirth": "1990-06-15",
          "allergies": "Dust",
          "contactInfo": {
            "id": 3,
            "PHONE": [
              "0551234567"
            ],
            "EMAIL": "invalid-email"
          },
          "addresses": [
            {
              "id": 10,
              "country": "UAE",
              "city": "Dubai",
              "streetName": "SZR",
              "streetNumber": "101",
              "label": "HOME"
            }
          ]
        }
    """;

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/update")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid contact info")));
    }

    @Test
    void updatePatient_functionalTest_EmptyNameAndAddress() throws Exception {
        String json = """
        {
          "id": 1,
          "name": null,
          "dateOfBirth": "1990-06-15",
          "allergies": "Peanuts",
          "contactInfo": {
            "id": 3,
            "PHONE": ["0551234567"],
            "EMAIL": "ahmed.alfarsi@example.com"
          },
          "addresses": null
        }
    """;

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/update")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Patient name can't be null")))
                .andExpect(content().string(containsString("address can't be null")));
    }
}
