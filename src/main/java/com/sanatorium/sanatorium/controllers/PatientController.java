package com.sanatorium.sanatorium.controllers;

import com.sanatorium.sanatorium.models.User;
import com.sanatorium.sanatorium.repo.PermissionRepo;
import com.sanatorium.sanatorium.repo.UserRepo;
import com.sanatorium.sanatorium.repo.VisitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Transactional
public class PatientController {

    @Autowired
    VisitRepo visitRepo;

    @Autowired
    UserRepo userRepo;


    @Autowired
    PermissionRepo permRepo;

    /**
     * Metoda zwracająca widok z listą wszystkich pacjentów w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/showPatients")
    public ModelAndView showAll(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        List<User> patients = userRepo.findUsersByPermissionNameOrderByIdAsc("patient");

        mav.setViewName("patient/viewAll");

        if (patients.isEmpty()) {


            mav.addObject("message", "Nie znaleźliśmy żadnego pacjenta");
            return mav;
        } else {
            mav.addObject("patients", patients);
            return mav;
        }

    }

    /**
     * Metoda zwracająca widok z formularzem dodawania pacjenta
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/addPatient")
    public ModelAndView addPatient(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient/add");
        return mav;
    }
    /**
     * Metoda obsługująca formularz dodawania pacjenta do bazy
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @PostMapping("/savePatient")
    public ModelAndView savePatient(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient/viewAll");
        try {
            User patient = new User();
            patient.setPassword("zaq1@WSX");
            patient.setPermission(permRepo.findPermissionByName("patient"));
            patient.setEmail(req.getParameter("login"));
            patient.setName(req.getParameter("name"));
            patient.setSurname(req.getParameter("surname"));
            userRepo.save(patient);
            return new ModelAndView("redirect:/showPatients", "message", "Pacjent dodany pomyślnie.");

        } catch (Exception e) {
            return new ModelAndView("redirect:/showPatients", "erorr", "Błąd podczas dodawania pacjenta.");
        }
    }
    /**
     * Metoda wyświetlająca formularz edycji danych pacjenta
     * @param id  identyfikator pacjenta w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/editPatient/{id}")
    public ModelAndView editPatient(@PathVariable("id") Long id, HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        String referer = req.getHeader("Referer");

        User patient = userRepo.findUserById(id);

        if (patient != null) {
            mav.setViewName("patient/edit");
            mav.addObject("patient", patient);
            return mav;
        }

        return new ModelAndView("redirect:" + referer, "error", "Nie znaleziono pacjenta!");

    }
    /**
     * Metoda obsługująca formularz edycji danych pacjenta
     * @param id  identyfikator pacjenta w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @PostMapping("/editPatient/{id}")
    public ModelAndView updatePatient(@PathVariable("id") Long id, HttpServletRequest req) {
        User patient = userRepo.findUserById(id);
        String referer = req.getHeader("Referer");

        if (patient != null) {
            try {
                patient.setName(req.getParameter("name"));
                patient.setSurname(req.getParameter("surname"));
                userRepo.save(patient);
                return new ModelAndView("redirect:/showPatients", "message", "Dane pacjenta zaktualizowane pomyślnie.");

            } catch (Exception e) {

            }
        }

        return new ModelAndView("redirect:" + referer, "error", "Bład podczas edycji pacjenta!");

    }

    /**
     * Metoda obsługująca żądanie usunięcia pacjenta z bazy
     * @param id identyfikator pacjenta w bazie
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/deletePatient/{id}")
    public ModelAndView deletePatient(@PathVariable("id") Long id){

        try{
            userRepo.removeUserById(id);
        }catch (Exception e){
            return new ModelAndView("redirect:/showPatients", "error", "Błąd podczas usuwania");

        }
        return new ModelAndView("redirect:/showPatients", "message", "Pacjent usunięty pomyślnie");

    }
}
