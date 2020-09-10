package com.sanatorium.sanatorium.controller;

import com.sanatorium.sanatorium.models.User;
import com.sanatorium.sanatorium.repo.PermissionRepo;
import com.sanatorium.sanatorium.repo.RehabilitationRepo;
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
public class RehabilitationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PermissionRepo permissionRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RehabilitationRepo rehabilitationRepo;

    @BeforeAll()
    void createUsers() throws Exception {
        User receptionist = new User(); receptionist.setPermission(permissionRepo.findPermissionByName("receptionist")); receptionist.setEmail("hreceptionist@gmail.com"); receptionist.setName("Ania"); receptionist.setSurname("Recepcjonistka");
        User admin = new User(); admin.setPermission(permissionRepo.findPermissionByName("admin")); admin.setEmail("hadmin@gmail.com"); admin.setName("Wallace"); admin.setSurname("Breen");
        User doctor = new User(); doctor.setPermission(permissionRepo.findPermissionByName("physiotherapist")); doctor.setEmail("hdoctor@gmail.com"); doctor.setName("Jan"); doctor.setSurname("Lekarz");
        User physiotherapist = new User(); physiotherapist.setPermission(permissionRepo.findPermissionByName("physiotherapist")); physiotherapist.setEmail("hphysiotherapist@gmail.com"); physiotherapist.setName("Tomasz"); physiotherapist.setSurname("Fizjo");
        User patient = new User(); patient.setPermission(permissionRepo.findPermissionByName("patient")); patient.setEmail("hpatient@gmail.com"); patient.setName("Michał"); patient.setSurname("Pacjent");

        userRepo.save(receptionist);userRepo.save(admin);userRepo.save(doctor);userRepo.save(physiotherapist);userRepo.save(patient);
    }

    @Test
    void shouldShowPhysiotherapists() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","hadmin@gmail.com");
        mockMvc.perform(
                get("/showPhysiotherapists")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldGetAddPhysiotherapistForm() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","hadmin@gmail.com");
        mockMvc.perform(get("/addPhysiotherapist").with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldSavePhysiotherapist() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","hadmin@gmail.com");
        mockMvc.perform(
                post("/savePhysiotherapist")
                        .param("email","d2phys@gmail.com")
                        .param("name","Zbigniew")
                        .param("surname","Stonoga")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal))))
                .andDo(print())
                .andExpect(status().is(302));

        User physiotherapist = userRepo.findUserByEmail("d2phys@gmail.com");
        if(physiotherapist != null)
            userRepo.delete(physiotherapist);
    }

    @Test
    void shouldGetEditPhysiotherapistForm() throws Exception {


        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","hadmin@gmail.com");
        mockMvc.perform(get("/editPhysiotherapist/"+userRepo.findUserByEmail("hphysiotherapist@gmail.com").getId().toString()).with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldUpdatePhysiotherapist() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","hadmin@gmail.com");
        mockMvc.perform(
                post("/editPhysiotherapist/"+userRepo.findUserByEmail("hphysiotherapist@gmail.com").getId().toString())
                        .param("name","Tomasz")
                        .param("surname","Fizjo")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal))))
                .andDo(print())
                .andExpect(status().is(302));
    }

    @Test
    void shouldDeletePhysiotherapist() throws Exception {

        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","hadmin@gmail.com");
        mockMvc.perform(get("/deletePhysiotherapist/"+userRepo.findUserByEmail("hphysiotherapist@gmail.com").getId().toString()).with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))).andDo(print()).andExpect(status().is(302));
        User physiotherapist = new User(); physiotherapist.setPermission(permissionRepo.findPermissionByName("physiotherapist")); physiotherapist.setEmail("hphysiotherapist@gmail.com"); physiotherapist.setName("Tomasz"); physiotherapist.setSurname("Fizjo");
        userRepo.save(physiotherapist);
    }

    @AfterAll()
    void destroyUsers(){
        userRepo.delete(userRepo.findUserByEmail("hpatient@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("hdoctor@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("hreceptionist@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("hphysiotherapist@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("hadmin@gmail.com"));
    }
}
