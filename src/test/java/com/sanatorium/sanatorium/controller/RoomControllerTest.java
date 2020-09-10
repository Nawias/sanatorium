package com.sanatorium.sanatorium.controller;


import com.sanatorium.sanatorium.models.Room;
import com.sanatorium.sanatorium.models.User;
import com.sanatorium.sanatorium.repo.PermissionRepo;
import com.sanatorium.sanatorium.repo.RoomRepo;
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
public class RoomControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepo userRepo;
    @Autowired
    PermissionRepo permissionRepo;
    @Autowired
    RoomRepo roomRepo;

    @BeforeAll()
    void createUsers() throws Exception {
        User receptionist = new User(); receptionist.setPermission(permissionRepo.findPermissionByName("receptionist")); receptionist.setEmail("rreceptionist@gmail.com"); receptionist.setName("Ania"); receptionist.setSurname("Recepcjonistka");
        User admin = new User(); admin.setPermission(permissionRepo.findPermissionByName("admin")); admin.setEmail("radmin@gmail.com"); admin.setName("Wallace"); admin.setSurname("Breen");
        User doctor = new User(); doctor.setPermission(permissionRepo.findPermissionByName("doctor")); doctor.setEmail("rdoctor@gmail.com"); doctor.setName("Jan"); doctor.setSurname("Lekarz");
        User physiotherapist = new User(); physiotherapist.setPermission(permissionRepo.findPermissionByName("physiotherapist")); physiotherapist.setEmail("rphysiotherapist@gmail.com"); physiotherapist.setName("Tomasz"); physiotherapist.setSurname("Fizjo");
        User patient = new User(); patient.setPermission(permissionRepo.findPermissionByName("patient")); patient.setEmail("rpatient@gmail.com"); patient.setName("Michał"); patient.setSurname("Pacjent");

        userRepo.save(receptionist);userRepo.save(admin);userRepo.save(doctor);userRepo.save(physiotherapist);userRepo.save(patient);
    }

    @Test
    void shouldShowRooms() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","dadmin@gmail.com");
        mockMvc.perform(
                get("/showRooms")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldGetAddRoomForm() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","dadmin@gmail.com");
        mockMvc.perform(
                get("/addRoom")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldSaveRoom() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","dadmin@gmail.com");
        mockMvc.perform(
                post("/saveRoom")
                        .param("floor","21")
                        .param("number","37")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal))))
                .andDo(print())
                .andExpect(status().is(302));

        Room room = roomRepo.findRoomByFloorAndNumber(21,37);
        if(room != null)
            roomRepo.delete(room);
    }

    @Test
    void shouldDeleteRoom() throws Exception {
        Room room = new Room();
        room.setFloor(21);
        room.setNumber(37);
        room.setState("zajęty");
        Room savedRoom = roomRepo.save(room);

        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","dadmin@gmail.com");
        mockMvc.perform(
                get("/deleteRoom/"+savedRoom.getId())
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().is(302));


    }

    @Test
    void shouldGetEditRoomForm() throws Exception {
        Room room = new Room();
        room.setFloor(21);
        room.setNumber(37);
        room.setState("zajęty");
        Room savedRoom = roomRepo.save(room);

        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","dadmin@gmail.com");
        mockMvc.perform(
                get("/editRoom/"+savedRoom.getId())
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().isOk());
        roomRepo.delete(savedRoom);
    }

    @Test
    void shouldUpdateRoom() throws Exception {
        Room room = new Room();
        room.setFloor(21);
        room.setNumber(37);
        room.setState("zajęty");
        Room savedRoom = roomRepo.save(room);

        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","dadmin@gmail.com");
        mockMvc.perform(
                post("/editRoom/"+savedRoom.getId())
                        .param("state","wolny")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal))))
                .andDo(print())
                .andExpect(status().is(302));

        roomRepo.delete(savedRoom);
    }

    @AfterAll()
    void destroyUsers(){
        userRepo.delete(userRepo.findUserByEmail("rpatient@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("rdoctor@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("rreceptionist@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("rphysiotherapist@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("radmin@gmail.com"));
    }
}
