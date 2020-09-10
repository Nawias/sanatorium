package com.sanatorium.sanatorium.controllers;

import com.sanatorium.sanatorium.models.*;
import com.sanatorium.sanatorium.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@Transactional
public class DoctorController {

    @Autowired
    VisitRepo visitRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PermissionRepo permRepo;

    @Autowired
    PatientCardRepo patientCardRepo;

    @Autowired
    MedicammentRepo medRepo;

    @Autowired
    PrescriptionRepo prescriptionRepo;

    @Autowired
    ReferalRepo referalRepo;

    @RequestMapping("/showDoctors")
    public ModelAndView showAll(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        List<User> doctors = userRepo.findUsersByPermissionNameOrderByIdAsc("doctor");

        mav.setViewName("doctor/viewAll");

        if (doctors.isEmpty()) {


            mav.addObject("message", "Nie znaleźliśmy żadnego doktora");
            return mav;
        } else {
            mav.addObject("doctors", doctors);
            return mav;
        }

    }

    /**
     * Metoda zwracająca widok z formularzem dodawania lekarza
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/addDoctor")
    public ModelAndView addDoctor(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("doctor/add");
        return mav;
    }
    /**
     * Metoda obsługująca formularz dodawania lekarza do bazy
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @PostMapping("/saveDoctor")
    public ModelAndView saveDoctor(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("doctor/viewAll");
        try {
            User doctor = new User();
            doctor.setPassword("zaq1@WSX");
            doctor.setPermission(permRepo.findPermissionByName("doctor"));
            doctor.setEmail(req.getParameter("email"));
            doctor.setName(req.getParameter("name"));
            doctor.setSurname(req.getParameter("surname"));
            userRepo.save(doctor);
            return new ModelAndView("redirect:/showDoctors", "message", "Lekarz dodany pomyślnie.");

        } catch (Exception e) {
            return new ModelAndView("redirect:/showDoctors", "erorr", "Błąd podczas dodawania lekarza.");
        }
    }
    /**
     * Metoda wyświetlająca formularz edycji danych lekarza
     * @param id  identyfikator lekarza w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/editDoctor/{id}")
    public ModelAndView editDoctor(@PathVariable("id") Long id, HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        String referer = req.getHeader("Referer");

        User doctor = userRepo.findUserById(id);

        if (doctor != null) {
            mav.setViewName("doctor/edit");
            mav.addObject("doctor", doctor);
            return mav;
        }

        return new ModelAndView("redirect:" + referer, "error", "Nie znaleziono lekarza!");

    }
    /**
     * Metoda obsługująca formularz edycji danych lekarza
     * @param id  identyfikator lekarza w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @PostMapping("/editDoctor/{id}")
    public ModelAndView updateDoctor(@PathVariable("id") Long id, HttpServletRequest req) {
        User doctor = userRepo.findUserById(id);
        String referer = req.getHeader("Referer");

        if (doctor != null) {
            try {
                doctor.setName(req.getParameter("name"));
                doctor.setSurname(req.getParameter("surname"));
                userRepo.save(doctor);
                return new ModelAndView("redirect:/showDoctors", "message", "Dane lekarza zaktualizowane pomyślnie.");

            } catch (Exception e) {

            }
        }

        return new ModelAndView("redirect:" + referer, "error", "Bład podczas edycji lekarza!");

    }

    /**
     * Metoda obsługująca żądanie usunięcia lekarza z bazy
     * @param id identyfikator lekarza w bazie
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/deleteDoctor/{id}")
    public ModelAndView deleteDoctor(@PathVariable("id") Long id){

        try{
            userRepo.removeUserById(id);
        }catch (Exception e){
            return new ModelAndView("redirect:/showDoctors", "error", "Błąd podczas usuwania");

        }
        return new ModelAndView("redirect:/showDoctors", "message", "Lekarz usunięty pomyślnie");

    }



}
