
package com.example.demo;


import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;



import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultMatcher;


/** TODO
 * Implement all the unit test in its corresponding class.
 * Make sure to be as exhaustive as possible. Coverage is checked ;)
 */

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getDoctorById() throws Exception {
        Doctor doctor1 = new Doctor("Profesor", "Layton", 40, "mail@mail.com");

        doctorRepository.save(doctor1);

        when(doctorRepository.findById(doctor1.getId())).thenReturn(Optional.of(doctor1));

        mockMvc.perform(get("/api/doctors/{id}", doctor1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(doctor1.getId()))
                .andExpect(jsonPath("$.firstName").value(doctor1.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(doctor1.getLastName()))
                .andExpect(jsonPath("$.age").value(doctor1.getAge()))
                .andExpect(jsonPath("$.email").value(doctor1.getEmail()));
    }
    @Test
    void getAllDoctors() throws Exception {
        Doctor doctor1 = new Doctor("John", "Doe", 30, "john.doe@mail.com");
        Doctor doctor2 = new Doctor("Jane", "Smith", 35, "jane.smith@mail.com");
        List<Doctor> doctors = Arrays.asList(doctor1, doctor2);

        when(doctorRepository.findAll()).thenReturn(doctors);

        // Perform the GET request and validate the results
        mockMvc.perform(get("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))

                // Doctor 1 assertions
                .andExpect(jsonPath("$[0].id").value(doctor1.getId()))
                .andExpect(jsonPath("$[0].firstName").value(doctor1.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(doctor1.getLastName()))
                .andExpect(jsonPath("$[0].age").value(doctor1.getAge()))
                .andExpect(jsonPath("$[0].email").value(doctor1.getEmail()))

                // Doctor 2 assertions
                .andExpect(jsonPath("$[1].id").value(doctor2.getId()))
                .andExpect(jsonPath("$[1].firstName").value(doctor2.getFirstName()))
                .andExpect(jsonPath("$[1].lastName").value(doctor2.getLastName()))
                .andExpect(jsonPath("$[1].age").value(doctor2.getAge()))
                .andExpect(jsonPath("$[1].email").value(doctor2.getEmail()));
    }

    @Test
    void getAllDoctorsEmpty() throws Exception {
        // Mock the behavior of the doctorRepository
        when(doctorRepository.findAll()).thenReturn(Collections.emptyList());

        // Perform the GET request and validate the results
        mockMvc.perform(get("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Test
    void postDoctor() throws Exception {
        mockMvc.perform(post("/api/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Ash\",\"lastName\":\"Ketchup\",\"age\":16,\"email\":\"mail@mail.com\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    void deleteDoctor() throws Exception {
        // Create a doctor for testing
        mockMvc.perform(post("/api/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Ash\",\"lastName\":\"Ketchup\",\"age\":16,\"email\":\"mail@mail.com\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/api/doctor/0"))
                .andExpect(status().isNotFound());
    }
    @Test
    void deleteAllDoctors_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(doctorRepository).deleteAll();
    }
}


@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


}
