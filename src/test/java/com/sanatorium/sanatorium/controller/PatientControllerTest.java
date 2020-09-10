package com.sanatorium.sanatorium.controller;

import com.sanatorium.sanatorium.models.User;
import com.sanatorium.sanatorium.repo.PermissionRepo;
import com.sanatorium.sanatorium.repo.UserRepo;
import com.sanatorium.sanatorium.repo.VisitRepo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PatientControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    VisitRepo visitRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PermissionRepo permissionRepo;

    @BeforeAll()
    void createUsers() throws Exception {
        User receptionist = new User(); receptionist.setPermission(permissionRepo.findPermissionByName("receptionist")); receptionist.setEmail("preceptionist@gmail.com"); receptionist.setName("Ania"); receptionist.setSurname("Recepcjonistka");
        User admin = new User(); admin.setPermission(permissionRepo.findPermissionByName("admin")); admin.setEmail("padmin@gmail.com"); admin.setName("Wallace"); admin.setSurname("Breen");
        User doctor = new User(); doctor.setPermission(permissionRepo.findPermissionByName("doctor")); doctor.setEmail("pdoctor@gmail.com"); doctor.setName("Jan"); doctor.setSurname("Lekarz");
        User physiotherapist = new User(); physiotherapist.setPermission(permissionRepo.findPermissionByName("physiotherapist")); physiotherapist.setEmail("pphysiotherapist@gmail.com"); physiotherapist.setName("Tomasz"); physiotherapist.setSurname("Fizjo");
        User patient = new User(); patient.setPermission(permissionRepo.findPermissionByName("patient")); patient.setEmail("ppatient@gmail.com"); patient.setName("Michał"); patient.setSurname("Pacjent");

        userRepo.save(receptionist);userRepo.save(admin);userRepo.save(doctor);userRepo.save(physiotherapist);userRepo.save(patient);
    }

    @Test
    void shouldShowPatients() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","padmin@gmail.com");
        mockMvc.perform(
                get("/showPatients")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldGetAddPatientForm() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","padmin@gmail.com");
        mockMvc.perform(
                get("/addPatient")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldSavePatient() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","padmin@gmail.com");
        mockMvc.perform(
                post("/savePatient")
                        .param("email","p2patient@gmail.com")
                        .param("name","Zbigniew")
                        .param("surname","Ziobro")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().is(302));

        User patient = userRepo.findUserByEmail("p2patient@gmail.com");
        if(patient!=null)
            userRepo.delete(patient);
    }
    @Test
    void shouldGetEditPatientForm() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","padmin@gmail.com");
        mockMvc.perform(
                get("/editPatient/"+userRepo.findUserByEmail("ppatient@gmail.com").getId())
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldUpdatePatient() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen", "padmin@gmail.com");
        User patient = userRepo.findUserByEmail("ppatient@gmail.com");
        mockMvc.perform(
                post("/editPatient/"+patient.getId())
                        .param("name", patient.getName())
                        .param("surname", patient.getSurname())
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().is(302));


    }

    @Test
    void shouldDeletePatient() throws Exception {

        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","padmin@gmail.com");
        mockMvc.perform(get("/deletePatient/"+userRepo.findUserByEmail("ppatient@gmail.com").getId().toString()).with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))).andDo(print()).andExpect(status().is(302));
        User patient = new User(); patient.setPermission(permissionRepo.findPermissionByName("patient")); patient.setEmail("ppatient@gmail.com"); patient.setName("Michał"); patient.setSurname("Pacjent");
        userRepo.save(patient);
    }



    @AfterAll()
    void destroyUsers(){
        userRepo.delete(userRepo.findUserByEmail("ppatient@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("pdoctor@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("preceptionist@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("pphysiotherapist@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("padmin@gmail.com"));
    }
}
