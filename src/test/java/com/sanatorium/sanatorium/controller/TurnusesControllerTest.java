package com.sanatorium.sanatorium.controller;

import com.sanatorium.sanatorium.models.Room;
import com.sanatorium.sanatorium.models.User;
import com.sanatorium.sanatorium.repo.PermissionRepo;
import com.sanatorium.sanatorium.repo.RoomRepo;
import com.sanatorium.sanatorium.repo.TurnusRepo;
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
public class TurnusesControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PermissionRepo permissionRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    TurnusRepo turnusRepo;

    @Autowired
    RoomRepo roomRepo;

    private Room savedRoom;

    @BeforeAll()
    void createUsers() throws Exception {

        Room room = new Room();
        room.setFloor(21);
        room.setNumber(337);
        room.setState("wolny");
        this.savedRoom = roomRepo.save(room);

        User receptionist = new User(); receptionist.setPermission(permissionRepo.findPermissionByName("receptionist")); receptionist.setEmail("treceptionist@gmail.com"); receptionist.setName("Ania"); receptionist.setSurname("Recepcjonistka");
        User admin = new User(); admin.setPermission(permissionRepo.findPermissionByName("admin")); admin.setEmail("tadmin@gmail.com"); admin.setName("Wallace"); admin.setSurname("Breen");
        User doctor = new User(); doctor.setPermission(permissionRepo.findPermissionByName("doctor")); doctor.setEmail("tdoctor@gmail.com"); doctor.setName("Jan"); doctor.setSurname("Lekarz");
        User physiotherapist = new User(); physiotherapist.setPermission(permissionRepo.findPermissionByName("physiotherapist")); physiotherapist.setEmail("tphysiotherapist@gmail.com"); physiotherapist.setName("Tomasz"); physiotherapist.setSurname("Fizjo");
        User patient = new User(); patient.setPermission(permissionRepo.findPermissionByName("patient")); patient.setEmail("tpatient@gmail.com"); patient.setName("Michał"); patient.setSurname("Pacjent");

        userRepo.save(receptionist);userRepo.save(admin);userRepo.save(doctor);userRepo.save(physiotherapist);userRepo.save(patient);


    }

    @Test
    void shouldShowTurnuses() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","tadmin@gmail.com");
        mockMvc.perform(
                get("/showTurnuses")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldShowActiveTurnuses() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","tadmin@gmail.com");
        mockMvc.perform(
                get("/showTurnuses/active")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldShowNoactiveTurnuses() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","tadmin@gmail.com");
        mockMvc.perform(
                get("/showTurnuses/noactive")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldShowAddTurnusForm() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","tadmin@gmail.com");
        mockMvc.perform(
                get("/addTurnus")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldSaveTurnus_old_user() throws Exception {

        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","tadmin@gmail.com");
        mockMvc.perform(
                post("/saveTurnus")
                        .param("dateStart","2020-05-12")
                        .param("dateEnd","2020-12-12")
                        .param("room",savedRoom.getId().toString())
                        .param("patient",userRepo.findUserByEmail("tpatient@gmail.com").getId().toString())
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().is(302));
        turnusRepo.delete(turnusRepo.findFirstByRoom(savedRoom));

    }

    @Test
    void shouldSaveTurnus_new_user() throws Exception {

        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","tadmin@gmail.com");
        mockMvc.perform(
                post("/saveTurnus")
                        .param("dateStart","2020-05-12")
                        .param("dateEnd","2020-12-12")
                        .param("newPatient","true")
                        .param("room",savedRoom.getId().toString())
                        .param("name","Grzegorz")
                        .param("surname","Brzęczyszczykiewicz")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().is(302));
        turnusRepo.delete(turnusRepo.findFirstByRoom(savedRoom));

    }

    @AfterAll()
    void destroyUsers(){
        userRepo.delete(userRepo.findUserByEmail("tpatient@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("tdoctor@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("treceptionist@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("tphysiotherapist@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("tadmin@gmail.com"));
        roomRepo.delete(savedRoom);
    }
}
