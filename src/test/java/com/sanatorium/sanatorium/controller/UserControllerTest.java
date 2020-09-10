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
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PermissionRepo permissionRepo;

    @BeforeAll()
    void createUsers() throws Exception {
        User receptionist = new User(); receptionist.setPermission(permissionRepo.findPermissionByName("receptionist")); receptionist.setEmail("ureceptionist@gmail.com"); receptionist.setName("Ania"); receptionist.setSurname("Recepcjonistka");
        User admin = new User(); admin.setPermission(permissionRepo.findPermissionByName("admin")); admin.setEmail("uadmin@gmail.com"); admin.setName("Wallace"); admin.setSurname("Breen");
        User doctor = new User(); doctor.setPermission(permissionRepo.findPermissionByName("doctor")); doctor.setEmail("udoctor@gmail.com"); doctor.setName("Jan"); doctor.setSurname("Lekarz");
        User physiotherapist = new User(); physiotherapist.setPermission(permissionRepo.findPermissionByName("physiotherapist")); physiotherapist.setEmail("uphysiotherapist@gmail.com"); physiotherapist.setName("Tomasz"); physiotherapist.setSurname("Fizjo");
        User patient = new User(); patient.setPermission(permissionRepo.findPermissionByName("patient")); patient.setEmail("upatient@gmail.com"); patient.setName("Michał"); patient.setSurname("Pacjent");

        userRepo.save(receptionist);userRepo.save(admin);userRepo.save(doctor);userRepo.save(physiotherapist);userRepo.save(patient);
    }

    @Test
    void shouldShowUsers() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","uadmin@gmail.com");
        mockMvc.perform(
                get("/showUsers")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldGetAddUserForm() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","uadmin@gmail.com");
        mockMvc.perform(
                get("/addUser")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldSaveUser() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","uadmin@gmail.com");
        mockMvc.perform(
                post("/saveUser")
                        .param("email","u2user@gmail.com")
                        .param("name","John")
                        .param("surname","Doe")
                        .param("role","5")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().is(302));

        User user = userRepo.findUserByEmail("u2user@gmail.com");
        if(user!=null)
            userRepo.delete(user);
    }
    @Test
    void shouldGetEditUserForm() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","uadmin@gmail.com");
        mockMvc.perform(
                get("/editUser/"+userRepo.findUserByEmail("upatient@gmail.com").getId())
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldUpdateUser() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen", "uadmin@gmail.com");
        User user = userRepo.findUserByEmail("upatient@gmail.com");
        mockMvc.perform(
                post("/updateUser/"+user.getId())
                        .param("name", user.getName())
                        .param("surname", user.getSurname())
                        .param("role","5")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().is(302));


    }

    @Test
    void shouldDeleteUser() throws Exception {

        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","uadmin@gmail.com");
        mockMvc.perform(get("/deleteUser/"+userRepo.findUserByEmail("upatient@gmail.com").getId().toString()).with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))).andDo(print()).andExpect(status().is(302));
        User user = new User(); user.setPermission(permissionRepo.findPermissionByName("patient")); user.setEmail("upatient@gmail.com"); user.setName("Michał"); user.setSurname("Pacjent");
        userRepo.save(user);
    }




    @AfterAll()
    void destroyUsers(){
        userRepo.delete(userRepo.findUserByEmail("upatient@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("udoctor@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("ureceptionist@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("uphysiotherapist@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("uadmin@gmail.com"));
    }
}
