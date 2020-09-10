package com.sanatorium.sanatorium.controller;

import com.sanatorium.sanatorium.models.User;
import com.sanatorium.sanatorium.repo.PermissionRepo;
import com.sanatorium.sanatorium.repo.UserRepo;
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
public class HomeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PermissionRepo permissionRepo;

    @Autowired
    UserRepo userRepo;
    

    @BeforeAll()
    void createUsers() throws Exception {
        User receptionist = new User(); receptionist.setPermission(permissionRepo.findPermissionByName("receptionist")); receptionist.setEmail("receptionist@gmail.com"); receptionist.setName("Ania"); receptionist.setSurname("Recepcjonistka");
        User admin = new User(); admin.setPermission(permissionRepo.findPermissionByName("admin")); admin.setEmail("admin@gmail.com"); admin.setName("Wallace"); admin.setSurname("Breen");
        User doctor = new User(); doctor.setPermission(permissionRepo.findPermissionByName("doctor")); doctor.setEmail("doctor@gmail.com"); doctor.setName("Jan"); doctor.setSurname("Lekarz");
        User physiotherapist = new User(); physiotherapist.setPermission(permissionRepo.findPermissionByName("physiotherapist")); physiotherapist.setEmail("physiotherapist@gmail.com"); physiotherapist.setName("Tomasz"); physiotherapist.setSurname("Fizjo");
        User patient = new User(); patient.setPermission(permissionRepo.findPermissionByName("patient")); patient.setEmail("patient@gmail.com"); patient.setName("Michał"); patient.setSurname("Pacjent");

        userRepo.save(receptionist);userRepo.save(admin);userRepo.save(doctor);userRepo.save(physiotherapist);userRepo.save(patient);
    }

    @Test
    void should_see_home_blank() throws Exception {
        mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }


    @Test
    void should_see_home_admin() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","admin@gmail.com");
        mockMvc.perform(get("/").with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void should_see_home_rec() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Ania Recepcjonistka","receptionist@gmail.com");
        mockMvc.perform(get("/").with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void should_see_home_pat() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Michał Pacjent","patient@gmail.com");
        mockMvc.perform(get("/").with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void should_see_home_doc() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Jan Lekarz","doctor@gmail.com");
        mockMvc.perform(get("/").with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void should_see_home_phys() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Tomasz Fizjo","physiotherapist@gmail.com");
        mockMvc.perform(get("/").with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))).andDo(print()).andExpect(status().isOk());
    }

    @AfterAll()
    void destroyUsers(){
        userRepo.delete(userRepo.findUserByEmail("patient@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("doctor@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("receptionist@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("physiotherapist@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("admin@gmail.com"));
    }
}
