package com.sanatorium.sanatorium.controller;
import com.sanatorium.sanatorium.models.User;
import com.sanatorium.sanatorium.models.Visit;
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
public class DoctorControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PermissionRepo permissionRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    VisitRepo visitRepo;

    @BeforeAll()
    void createUsers() throws Exception {
        User receptionist = new User(); receptionist.setPermission(permissionRepo.findPermissionByName("receptionist")); receptionist.setEmail("dreceptionist@gmail.com"); receptionist.setName("Ania"); receptionist.setSurname("Recepcjonistka");
        User admin = new User(); admin.setPermission(permissionRepo.findPermissionByName("admin")); admin.setEmail("dadmin@gmail.com"); admin.setName("Wallace"); admin.setSurname("Breen");
        User doctor = new User(); doctor.setPermission(permissionRepo.findPermissionByName("doctor")); doctor.setEmail("ddoctor@gmail.com"); doctor.setName("Jan"); doctor.setSurname("Lekarz");
        User physiotherapist = new User(); physiotherapist.setPermission(permissionRepo.findPermissionByName("physiotherapist")); physiotherapist.setEmail("dphysiotherapist@gmail.com"); physiotherapist.setName("Tomasz"); physiotherapist.setSurname("Fizjo");
        User patient = new User(); patient.setPermission(permissionRepo.findPermissionByName("patient")); patient.setEmail("dpatient@gmail.com"); patient.setName("Michał"); patient.setSurname("Pacjent");

        userRepo.save(receptionist);userRepo.save(admin);userRepo.save(doctor);userRepo.save(physiotherapist);userRepo.save(patient);
    }

    @Test
    void shouldShowDoctors() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","dadmin@gmail.com");
        mockMvc.perform(
                get("/showDoctors")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldGetAddDoctorForm() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","dadmin@gmail.com");
        mockMvc.perform(get("/addDoctor").with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldSaveDoctor() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","dadmin@gmail.com");
        mockMvc.perform(
                post("/saveDoctor")
                        .param("email","d2doctor@gmail.com")
                        .param("name","Zbigniew")
                        .param("surname","Stonoga")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal))))
                .andDo(print())
                .andExpect(status().is(302));

        User doctor = userRepo.findUserByEmail("d2doctor@gmail.com");
        if(doctor != null)
            userRepo.delete(doctor);
    }

    @Test
    void shouldGetEditDoctorForm() throws Exception {


        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","dadmin@gmail.com");
        mockMvc.perform(get("/editDoctor/"+userRepo.findUserByEmail("ddoctor@gmail.com").getId().toString()).with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldUpdateDoctor() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","dadmin@gmail.com");
        mockMvc.perform(
                post("/editDoctor/"+userRepo.findUserByEmail("ddoctor@gmail.com").getId().toString())
                        .param("name","Jan")
                        .param("surname","Lekarz")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal))))
                .andDo(print())
                .andExpect(status().is(302));
    }

    @AfterAll()
    void destroyUsers(){
        userRepo.delete(userRepo.findUserByEmail("dpatient@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("ddoctor@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("dreceptionist@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("dphysiotherapist@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("dadmin@gmail.com"));
    }
}
