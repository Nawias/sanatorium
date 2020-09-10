package com.sanatorium.sanatorium.controllers;

import com.sanatorium.sanatorium.models.*;
import com.sanatorium.sanatorium.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Transactional
public class VisitController {

    @Autowired
    VisitRepo visitRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PatientCardRepo patientCardRepo;

    @Autowired
    MedicammentRepo medRepo;

    @Autowired
    PrescriptionRepo prescriptionRepo;

    @Autowired
    ReferalRepo referalRepo;
    /**
     * Metoda zwracająca widok z formularzem dodawania wizyty
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/addVisit")
    public ModelAndView addVisit(HttpServletRequest req, Authentication authentication) {

        ModelAndView mav = new ModelAndView();

        String email = "";

        try {
            DefaultOAuth2User oidcUser = (DefaultOAuth2User) authentication.getPrincipal();
            Map attributes = oidcUser.getAttributes();
            email = (String) attributes.get("email");

            User user = userRepo.findUserByEmail(email);

            List<User> doctors = null;
            if (user.getPermission().getName().equals("doctor")) {
                doctors = new LinkedList<User>();
                doctors.add(user);

            } else {
                doctors = userRepo.findUsersByPermissionName("doctor");
            }

            List<User> patients = userRepo.findUsersByPermissionName("patient");

            if (doctors != null && patients != null) {

                mav.addObject("doctors", doctors);
                mav.addObject("patients", patients);

                mav.setViewName("visit/add");

                return mav;
            }
        } catch (Exception e) {
            return new ModelAndView("redirect:/");
        }

        return new ModelAndView("redirect:/");
    }
    /**
     * Metoda obsługująca formularz dodawania wizyty do bazy
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @PostMapping("/saveVisit")
    public ModelAndView saveVisit(HttpServletRequest req, Authentication authentication) {
        ModelAndView mav = new ModelAndView();
        String referer = req.getHeader("Referer");


        Visit visit = new Visit();
        Long doctorId = Long.parseLong(req.getParameter("doctor"));
        Long patientId = Long.parseLong(req.getParameter("patient"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        try {
            Date date = dateFormat.parse(req.getParameter("date"));

            visit.setDoctor(userRepo.findUserById(doctorId));
            visit.setPatient(userRepo.findUserById(patientId));
            visit.setDateTime(date);
            visit.setActive(true);
            visitRepo.save(visit);

            DefaultOAuth2User oidcUser = (DefaultOAuth2User) authentication.getPrincipal();
            Map attributes = oidcUser.getAttributes();
            String email = (String) attributes.get("email");

            User user = userRepo.findUserByEmail(email);
            if (user.getPermission().getName().equals("doctor")) {
                return new ModelAndView("redirect:/", "message", "Wizyta dodana pomyślnie.");
            }


            return new ModelAndView("redirect:/showVisits", "message", "Wizyta dodana pomyślnie.");

        } catch (Exception e) {
            return new ModelAndView("redirect:" + referer, "error", "Nie udało się dodać wizyty!");
        }

    }

    /**
     * Metoda zwracająca widok z listą wszystkich wizyt w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/showVisits")
    public ModelAndView showVisits(HttpServletRequest req){
        ModelAndView mav = new ModelAndView();
        List<Visit> visits = visitRepo.findAll(Sort.by(Sort.Direction.ASC,"dateTime"));
        mav.setViewName("visit/viewAll");
        if (visits != null){


            mav.addObject("visits", visits);
            return mav;

        }

        mav.addObject("message", "Nie znaleźliśmy żadnej wizyty.");
        return mav;

    }

    /**
     * Metoda wyświetlająca formularz edycji danych wizyty
     * @param id  identyfikator wizyty w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/editVisit/{id}")
    public ModelAndView editVisit(@PathVariable("id") Long id, HttpServletRequest req){
        ModelAndView mav = new ModelAndView();
        Visit visit = visitRepo.findVisitById(id);

        List<User> doctors = userRepo.findUsersByPermissionName("doctor");
        List<User> patients = userRepo.findUsersByPermissionName("patient");


        if (visit != null && doctors != null && patients != null){
            mav.addObject("visit", visit);
            mav.addObject("doctors", doctors);
            mav.addObject("patients", patients);
            mav.setViewName("visit/edit");
            return mav;
        }

        String referer = req.getHeader("Referer");

        return new ModelAndView("redirect:" + referer, "error", "Nie znaleziono wizyty!");

    }
    /**
     * Metoda obsługująca formularz edycji danych wizyty
     * @param id  identyfikator wizyty w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @PostMapping("/editVisit/{id}")
    public ModelAndView updateVisit(@PathVariable("id") Long id, HttpServletRequest req, Authentication authentication){
        ModelAndView mav = new ModelAndView();
        String referer = req.getHeader("Referer");


        Visit visit = visitRepo.findVisitById(id);
        if (visit != null){
        Long doctorId = Long.parseLong(req.getParameter("doctor"));
        Long patientId = Long.parseLong(req.getParameter("patient"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm");
        try {
            Date date = dateFormat.parse(req.getParameter("date"));

            visit.setDoctor(userRepo.findUserById(doctorId));
            visit.setPatient(userRepo.findUserById(patientId));
            visit.setDateTime(date);
            visitRepo.save(visit);

            DefaultOAuth2User oidcUser = (DefaultOAuth2User) authentication.getPrincipal();
            Map attributes = oidcUser.getAttributes();
            String email = (String) attributes.get("email");


                User user = userRepo.findUserByEmail(email);
                if (user.getPermission().getName().equals("doctor")) {
                    return new ModelAndView("redirect:/", "message", "Wizyta zaktualizowana pomyślnie.");
                }

                return new ModelAndView("redirect:/showVisits", "message", "Wizyta zaktualizowana pomyślnie.");

        } catch (Exception e) {
            return new ModelAndView("redirect:" + referer, "error", "Nie udało się zaktualizować wizyty!");
        }
        }

        return new ModelAndView("redirect:" + referer, "error", "Nie udało się zaktualizować wizyty!");
    }

    /**
     * Metoda obsługująca żądanie usunięcia wizyty z bazy
     * @param id identyfikator wizyty w bazie
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/deleteVisit/{id}")
    public ModelAndView deleteVisit(@PathVariable("id") Long id){

        try{
            visitRepo.removeVisitById(id);
        }catch (Exception e){
            return new ModelAndView("redirect:/showVisits", "error", "Błąd podczas usuwania");

        }
        return new ModelAndView("redirect:/showVisits", "message", "Wizyta usunięta pomyślnie");

    }



    @RequestMapping("/startVisit/{id}")
    public ModelAndView startVisit(@PathVariable("id") Long id,HttpServletRequest req){
        ModelAndView mav = new ModelAndView();

        Visit visit = visitRepo.findVisitById(id);

        if (visit != null) {
            List<PatientCard> cards = patientCardRepo.findPatientCardsByPatientOrderByIdDesc(visit.getPatient());
            List<Medicament> medicaments = medRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));

            mav.addObject("visit", visit);
            mav.addObject("cards", cards);
            mav.addObject("medicaments", medicaments);
            mav.setViewName("doctor/visit");

            return mav;
        }
        return mav;
    }


    @PostMapping("/endVisit/{id}")
    public ModelAndView endVisit(@PathVariable("id") Long id, HttpServletRequest req){
        String referer = req.getHeader("Referer");
        try{
            ModelAndView mav = new ModelAndView();
            Visit visit = visitRepo.findVisitById(id);

            String medbox = (req.getParameter("prescription"));
            String refbox = (req.getParameter("referal"));

            if (visit != null){
                PatientCard patientCard = new PatientCard();
                patientCard.setVisit(visit);
                patientCard.setPatient(visit.getPatient());
                patientCard.setDescription(req.getParameter("description"));

                if (medbox != null){
                    Prescription prescription = new Prescription();
                    prescription.setVisit(visit);
                    prescription.setMedicament(medRepo.findMedicamentsById(Long.parseLong(req.getParameter("medicament"))));
                    prescriptionRepo.save(prescription);
                    patientCard.setPrescription(prescription);
                }

                if (refbox != null){
                    Referral referral = new Referral();
                    referral.setDate(new Date());
                    referral.setDoctor(visit.getPatient());
                    referral.setPatient(visit.getPatient());
                    referral.setService(req.getParameter("referal_service"));
                    referral.setDecription(req.getParameter("referal_description"));
                    referalRepo.save(referral);
                    patientCard.setReferral(referral);
                }

                patientCardRepo.save(patientCard);
                visit.setActive(false);
                visitRepo.save(visit);
                return new ModelAndView("redirect:/", "message", "Wizyta zakończona.");

            }else{
                return new ModelAndView("redirect:" + referer, "error", "Błąd podczas zapisu wizyty!");
            }

        }catch (Exception e){
            return new ModelAndView("redirect:" + referer, "error", "Błąd podczas zapisu wizyty!");

        }
    }
}
