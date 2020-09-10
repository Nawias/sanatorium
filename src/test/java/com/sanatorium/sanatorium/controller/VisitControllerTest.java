package com.sanatorium.sanatorium.controller;

import com.sanatorium.sanatorium.models.Prescription;
import com.sanatorium.sanatorium.models.Referral;
import com.sanatorium.sanatorium.models.User;
import com.sanatorium.sanatorium.models.Visit;
import com.sanatorium.sanatorium.repo.*;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VisitControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PermissionRepo permissionRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    VisitRepo visitRepo;

    @Autowired
    ReferalRepo referalRepo;

    @Autowired
    PatientCardRepo patientCardRepo;

    @Autowired
    PrescriptionRepo prescriptionRepo;

    @BeforeAll()
    void createUsers() throws Exception {
        User receptionist = new User(); receptionist.setPermission(permissionRepo.findPermissionByName("receptionist")); receptionist.setEmail("vreceptionist@gmail.com"); receptionist.setName("Ania"); receptionist.setSurname("Recepcjonistka");
        User admin = new User(); admin.setPermission(permissionRepo.findPermissionByName("admin")); admin.setEmail("vadmin@gmail.com"); admin.setName("Wallace"); admin.setSurname("Breen");
        User doctor = new User(); doctor.setPermission(permissionRepo.findPermissionByName("doctor")); doctor.setEmail("vdoctor@gmail.com"); doctor.setName("Jan"); doctor.setSurname("Lekarz");
        User physiotherapist = new User(); physiotherapist.setPermission(permissionRepo.findPermissionByName("physiotherapist")); physiotherapist.setEmail("vphysiotherapist@gmail.com"); physiotherapist.setName("Tomasz"); physiotherapist.setSurname("Fizjo");
        User patient = new User(); patient.setPermission(permissionRepo.findPermissionByName("patient")); patient.setEmail("vpatient@gmail.com"); patient.setName("Michał"); patient.setSurname("Pacjent");

        userRepo.save(receptionist);userRepo.save(admin);userRepo.save(doctor);userRepo.save(physiotherapist);userRepo.save(patient);
    }

    @Test
    void shouldGetAddVisitForm() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","vadmin@gmail.com");
        mockMvc.perform(get("/addVisit").with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldSaveVisit() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","vadmin@gmail.com");
        mockMvc.perform(
                post("/saveVisit")
                        .param("doctor",userRepo.findUserByEmail("vdoctor@gmail.com").getId().toString())
                        .param("patient",userRepo.findUserByEmail("vpatient@gmail.com").getId().toString())
                        .param("date","2020-12-12T12:12")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal))))
                .andDo(print())
                .andExpect(status().is(302));

        Visit visit = visitRepo.findByActiveAndPatientOrderByDateTimeAsc(true, userRepo.findUserByEmail("vpatient@gmail.com")).get(0);
        visitRepo.delete(visit);
    }

    @Test
    void shouldShowVisits() throws Exception {
        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","vadmin@gmail.com");
        mockMvc.perform(
                get("/showVisits")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldGetEditVisitForm() throws Exception {
        Visit visit = new Visit();
        visit.setActive(true);
        visit.setDoctor(userRepo.findUserByEmail("vdoctor@gmail.com"));
        visit.setPatient(userRepo.findUserByEmail("vpatient@gmail.com"));
        visit.setDateTime(new Date());
        Visit savedVisit = visitRepo.save(visit);

        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","vadmin@gmail.com");
        mockMvc.perform(get("/editVisit/"+savedVisit.getId().toString()).with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))).andDo(print()).andExpect(status().isOk());

        visitRepo.delete(savedVisit);
    }

    @Test
    void shouldUpdateVisit() throws Exception {
        Visit visit = new Visit();
        visit.setActive(true);
        visit.setDoctor(userRepo.findUserByEmail("vdoctor@gmail.com"));
        visit.setPatient(userRepo.findUserByEmail("vpatient@gmail.com"));
        visit.setDateTime(new Date());
        Visit savedVisit = visitRepo.save(visit);


        OAuth2User principal = OAuth2Util.createOAuth2User("Jan Lekarz","vdoctor@gmail.com");
        mockMvc.perform(
                post("/editVisit/"+savedVisit.getId().toString())
                        .param("doctor",userRepo.findUserByEmail("vdoctor@gmail.com").getId().toString())
                        .param("patient",userRepo.findUserByEmail("vpatient@gmail.com").getId().toString())
                        .param("date","2020-12-12T12:12")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal))))
                .andDo(print())
                .andExpect(status().is(302));


        visitRepo.delete(savedVisit);
    }

    @Test
    void shouldDeleteVisit() throws Exception {
        Visit visit = new Visit();
        visit.setActive(true);
        visit.setDoctor(userRepo.findUserByEmail("vdoctor@gmail.com"));
        visit.setPatient(userRepo.findUserByEmail("vpatient@gmail.com"));
        visit.setDateTime(new Date());
        Visit savedVisit = visitRepo.save(visit);

        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","vadmin@gmail.com");
        mockMvc.perform(get("/deleteVisit/"+savedVisit.getId().toString()).with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))).andDo(print()).andExpect(status().is(302));

        if(visitRepo.findVisitById(savedVisit.getId())!=null)
            visitRepo.delete(savedVisit);
    }

    @Test
    void shouldStartVisit() throws Exception {
        Visit visit = new Visit();
        visit.setActive(true);
        visit.setDoctor(userRepo.findUserByEmail("vdoctor@gmail.com"));
        visit.setPatient(userRepo.findUserByEmail("vpatient@gmail.com"));
        visit.setDateTime(new Date());
        Visit savedVisit = visitRepo.save(visit);

        OAuth2User principal = OAuth2Util.createOAuth2User("Wallace Breen","vadmin@gmail.com");
        mockMvc.perform(get("/startVisit/"+savedVisit.getId().toString()).with(authentication(OAuth2Util.getOauthAuthenticationFor(principal)))).andDo(print()).andExpect(status().isOk());

        visitRepo.delete(savedVisit);
    }

    @Test
    void shouldEndVisit() throws Exception {
        Visit visit = new Visit();
        visit.setActive(true);
        visit.setDoctor(userRepo.findUserByEmail("vdoctor@gmail.com"));
        visit.setPatient(userRepo.findUserByEmail("vpatient@gmail.com"));
        visit.setDateTime(new Date());
        Visit savedVisit = visitRepo.save(visit);


        OAuth2User principal = OAuth2Util.createOAuth2User("Jan Lekarz","vdoctor@gmail.com");
        mockMvc.perform(
                post("/endVisit/"+savedVisit.getId().toString())
                        .param("prescription","Eutanazol 120mg")
                        .param("referal","skierowanko na badanko")
                        .param("description","chory na głowę")
                        .param("medicament","22")
                        .param("referal_service","TEST_REFERAL_SERVICE_KEYBOARD_CAT")
                        .param("referal_description","Posadźcie go na krześle elektrycznym")
                        .with(authentication(OAuth2Util.getOauthAuthenticationFor(principal))))
                .andDo(print())
                .andExpect(status().is(302));

        patientCardRepo.delete(patientCardRepo.findPatientCardsByPatientOrderByIdDesc(savedVisit.getPatient()).get(0));
        Prescription prescription = prescriptionRepo.findByVisit(savedVisit);
        if(prescription!=null)
            prescriptionRepo.delete(prescription);
        Referral ref = referalRepo.findFirstByService("TEST_REFERAL_SERVICE_KEYBOARD_CAT");
        if(ref!=null)
            referalRepo.delete(ref);
        visitRepo.delete(savedVisit);
    }

    @AfterAll()
    void destroyUsers(){
        userRepo.delete(userRepo.findUserByEmail("vpatient@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("vdoctor@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("vreceptionist@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("vphysiotherapist@gmail.com"));
        userRepo.delete(userRepo.findUserByEmail("vadmin@gmail.com"));
    }
}
