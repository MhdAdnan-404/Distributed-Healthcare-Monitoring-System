package com.mhd.accountManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhd.accountManagement.model.DTO.DoctorDTO.DoctorRegisterRequest;
import com.mhd.accountManagement.model.DTO.DoctorDTO.DoctorRegisterResponse;
import com.mhd.accountManagement.services.DoctorService;
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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = DoctorController.class,
        excludeAutoConfiguration = {
                SecurityException.class,
                UserDetailsServiceAutoConfiguration.class
        }
)
@TestPropertySource(properties = {
        "application.config.vitalSignManagement-url=http://localhost:8080"
})
@AutoConfigureMockMvc(addFilters = false)
public class DoctorControllerTest_Functional {
        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private DoctorService doctorService;

        @MockitoBean
        private JwtUtil jwtUtil;

        @MockitoBean
        private CustomUserDetailsService customUserDetailsService;

        @Autowired
        private ObjectMapper mapper;

        @Test
        void registerDoctor_functionalTest_validInput() throws Exception {
                String json = """
            {
              "name": "Dr. Sarah",
              "specialty": "CARDIOLOGIST",
              "user": {
                "username": "dr.sarah",
                "password": "securePass123",
                "addresses": [
                  {
                    "country": "UAE",
                    "city": "Dubai",
                    "streetName": "Health Street",
                    "streetNumber": "20",
                    "label": "Clinic"
                  }
                ],
                "contactInfoDTO": {
                  "contacts": {
                    "PHONE": "+971501234567",
                    "EMAIL": "dr.sarah@example.com"
                  }
                },
                "role": "DOCTOR"
              }
            }""";

                DoctorRegisterRequest request = mapper.readValue(json, DoctorRegisterRequest.class);
                Mockito.when(doctorService.register(request))
                        .thenReturn(new DoctorRegisterResponse(5, "token-doctor-xyz"));

                mockMvc.perform(MockMvcRequestBuilders.post("/doctor/register")
                                .contentType("application/json")
                                .content(json))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(5))
                        .andExpect(jsonPath("$.token").value("token-doctor-xyz"));
        }


        @Test
        void registerDoctor_functionalTest_NameWithNumbers() throws Exception {
                String json = """
        {
          "name": "Dr123",
          "specialty": "CARDIOLOGIST",
          "user": {
            "username": "dr.invalid",
            "password": "pass1234",
            "addresses": [],
            "contactInfoDTO": {
              "contacts": {
                "PHONE": "+971501234567",
                "EMAIL": "dr.invalid@example.com"
              }
            },
            "role": "DOCTOR"
          }
        }
    """;
                mockMvc.perform(MockMvcRequestBuilders.post("/doctor/register")
                                .contentType("application/json")
                                .content(json))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("Doctor name cannot contain numbers")));
        }

        @Test
        void registerDoctor_functionalTest_nullSpecialtyAndInvalidEmail() throws Exception {
                String json = """
        {
          "name": "Dr. Sarah",
          "specialty": null,
          "user": {
            "username": "dr.sarah",
            "password": "securePass123",
            "addresses": [],
            "contactInfoDTO": {
              "contacts": {
                "PHONE": "+971501234567",
                "EMAIL": "invalid-email-format"
              }
            },
            "role": "DOCTOR"
          }
        }
    """;

                mockMvc.perform(MockMvcRequestBuilders.post("/doctor/register")
                                .contentType("application/json")
                                .content(json))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("Doctor Specialty can't be null")))
                        .andExpect(content().string(containsString("Invalid contact info")));
        }

        @Test
        void registerDoctor_functionalTest_EmptyNameAndInvalidPhone() throws Exception {
                String json = """
        {
          "name": "",
          "specialty": "CARDIOLOGIST",
          "user": {
            "username": "dr.empty",
            "password": "pass1234",
            "addresses": [],
            "contactInfoDTO": {
              "contacts": {
                "PHONE": "invalid-phone",
                "EMAIL": "dr.empty@example.com"
              }
            },
            "role": "DOCTOR"
          }
        }
    """;

                mockMvc.perform(MockMvcRequestBuilders.post("/doctor/register")
                                .contentType("application/json")
                                .content(json))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("Doctor name can't be null")))
                        .andExpect(content().string(containsString("Invalid contact info")));
        }



        @Test
        void updateDoctor_functionalTest_validInput() throws Exception {
                String json = """
{
  "id": 1,
  "name": "Dr. Aisha Kareem",
  "specialty": "CARDIOLOGIST",
  "contactInfo": {
    "id": 5,
    "contacts": {
      "PHONE": "+971501234567",
      "EMAIL": "aisha.kareem@healthcare.com"
    }
  },
  "addresses": [
    {
      "id": 7,
      "country": "UAE",
      "city": "Dubai",
      "streetName": "Sheikh Zayed Road",
      "streetNumber": "101",
      "label": "Clinic"
    },
    {
      "id": 8,
      "country": "UAE",
      "city": "Sharjah",
      "streetName": "Al Wahda Street",
      "streetNumber": "202",
      "label": "Home"
    }
  ]
}
""";

                mockMvc.perform(MockMvcRequestBuilders.post("/doctor/update")
                                .contentType("application/json")
                                .content(json))
                        .andExpect(status().isAccepted());
        }


        @Test
        void updateDoctor_functionalTest_nameWithNumbers() throws Exception {
                String json = """
{
  "id": 1,
  "name": "Dr. Aisha Kareem12",
  "specialty": "CARDIOLOGIST",
  "contactInfo": {
    "id": 5,
    "contacts": {
      "PHONE": "+971501234567",
      "EMAIL": "aisha.kareem@healthcare.com"
    }
  },
  "addresses": [
    {
      "id": 7,
      "country": "UAE",
      "city": "Dubai",
      "streetName": "Sheikh Zayed Road",
      "streetNumber": "101",
      "label": "Clinic"
    },
    {
      "id": 8,
      "country": "UAE",
      "city": "Sharjah",
      "streetName": "Al Wahda Street",
      "streetNumber": "202",
      "label": "Home"
    }
  ]
}
""";

                mockMvc.perform(MockMvcRequestBuilders.post("/doctor/update")
                                .contentType("application/json")
                                .content(json))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("Doctor name cannot contain numbers")));
        }

        @Test
        void updateDoctor_functionalTest_tooManyAddresses() throws Exception {
                String json = """
        {
          "id": 2,
          "name": "Dr. Youssef",
          "specialty": "CARDIOLOGIST",
          "contactInfo": {
    "id": 5,
    "contacts": {
      "PHONE": "+971501234567",
      "EMAIL": "aisha.kareem@healthcare.com"
    }
  },
          "addresses": [
            {"id": 1, "country": "UAE", "city": "Dubai", "streetName": "A", "streetNumber": "1", "label": "A"},
            {"id": 2, "country": "UAE", "city": "Abu Dhabi", "streetName": "B", "streetNumber": "2", "label": "B"},
            {"id": 3, "country": "UAE", "city": "Sharjah", "streetName": "C", "streetNumber": "3", "label": "C"},
            {"id": 4, "country": "UAE", "city": "Ajman", "streetName": "D", "streetNumber": "4", "label": "D"}
          ]
        }
    """;

                mockMvc.perform(MockMvcRequestBuilders.post("/doctor/update")
                                .contentType("application/json")
                                .content(json))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("You can provide at most 3 addresses")));
        }

        @Test
        void updateDoctor_functionalTest_invalidEmail() throws Exception {
                String json = """
{
  "id": 1,
  "name": "Dr. Aisha Kareem12",
  "specialty": "CARDIOLOGIST",
  "contactInfo": {
    "id": 5,
    "contacts": {
      "PHONE": "+971501234567",
      "EMAIL": "invalid-email"
    }
  },
  "addresses": [
    {
      "id": 7,
      "country": "UAE",
      "city": "Dubai",
      "streetName": "Sheikh Zayed Road",
      "streetNumber": "101",
      "label": "Clinic"
    },
    {
      "id": 8,
      "country": "UAE",
      "city": "Sharjah",
      "streetName": "Al Wahda Street",
      "streetNumber": "202",
      "label": "Home"
    }
  ]
}
""";

                mockMvc.perform(MockMvcRequestBuilders.post("/doctor/update")
                                .contentType("application/json")
                                .content(json))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("Invalid contact info")));
        }


        @Test
        void updateDoctor_functionalTest_EmptyNameAndAddress() throws Exception {
                String json = """
{
  "id": 1,
  "name": "",
  "specialty": "CARDIOLOGIST",
  "contactInfo": {
    "id": 5,
    "contacts": {
      "PHONE": "+971501234567",
      "EMAIL": "aisha.kareem@healthcare.com"
    }
  },
  "addresses": null
}
""";

                mockMvc.perform(MockMvcRequestBuilders.post("/doctor/update")
                                .contentType("application/json")
                                .content(json))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("Doctor name can't be null")))
                        .andExpect(content().string(containsString("address can't be null")));
        }
}
