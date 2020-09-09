package com.sanatorium.sanatorium.controllers;


import com.sanatorium.sanatorium.models.User;
import com.sanatorium.sanatorium.repo.PermissionRepo;
import com.sanatorium.sanatorium.repo.RehabilitationRepo;
import com.sanatorium.sanatorium.repo.UserRepo;
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
public class RehabilitationController {

    @Autowired
    RehabilitationRepo rehabilitationRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PermissionRepo permRepo;


    @RequestMapping("/showPhysiotherapists")
    public ModelAndView showAll(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        List<User> physiotherapists = userRepo.findUsersByPermissionNameOrderByIdAsc("physiotherapist");

        mav.setViewName("physiotherapist/viewAll");

        if (physiotherapists.isEmpty()) {


            mav.addObject("message", "Nie znaleźliśmy żadnego doktora");
            return mav;
        } else {
            mav.addObject("physiotherapists", physiotherapists);
            return mav;
        }

    }

    /**
     * Metoda zwracająca widok z formularzem dodawania rehabilitanta
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/addPhysiotherapist")
    public ModelAndView addPhysiotherapist(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("physiotherapist/add");
        return mav;
    }
    /**
     * Metoda obsługująca formularz dodawania rehabilitanta do bazy
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @PostMapping("/savePhysiotherapist")
    public ModelAndView savePhysiotherapist(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("physiotherapist/viewAll");
        try {
            User physiotherapist = new User();
            physiotherapist.setPassword("zaq1@WSX");
            physiotherapist.setPermission(permRepo.findPermissionByName("physiotherapist"));
            physiotherapist.setEmail(req.getParameter("login"));
            physiotherapist.setName(req.getParameter("name"));
            physiotherapist.setSurname(req.getParameter("surname"));
            userRepo.save(physiotherapist);
            return new ModelAndView("redirect:/showPhysiotherapists", "message", "Rehabilitant dodany pomyślnie.");

        } catch (Exception e) {
            return new ModelAndView("redirect:/showPhysiotherapists", "erorr", "Błąd podczas dodawania rehabilitanta.");
        }
    }
    /**
     * Metoda wyświetlająca formularz edycji danych rehabilitanta
     * @param id  identyfikator rehabilitanta w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/editPhysiotherapist/{id}")
    public ModelAndView editPhysiotherapist(@PathVariable("id") Long id, HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        String referer = req.getHeader("Referer");

        User physiotherapist = userRepo.findUserById(id);

        if (physiotherapist != null) {
            mav.setViewName("physiotherapist/edit");
            mav.addObject("physiotherapist", physiotherapist);
            return mav;
        }

        return new ModelAndView("redirect:" + referer, "error", "Nie znaleziono rehabilitanta!");

    }
    /**
     * Metoda obsługująca formularz edycji danych rehabilitanta
     * @param id  identyfikator rehabilitanta w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @PostMapping("/editPhysiotherapist/{id}")
    public ModelAndView updatePhysiotherapist(@PathVariable("id") Long id, HttpServletRequest req) {
        User physiotherapist = userRepo.findUserById(id);
        String referer = req.getHeader("Referer");

        if (physiotherapist != null) {
            try {
                physiotherapist.setName(req.getParameter("name"));
                physiotherapist.setSurname(req.getParameter("surname"));
                userRepo.save(physiotherapist);
                return new ModelAndView("redirect:/showPhysiotherapists", "message", "Dane rehabilitanta zaktualizowane pomyślnie.");

            } catch (Exception e) {

            }
        }

        return new ModelAndView("redirect:" + referer, "error", "Bład podczas edycji rehabilitanta!");

    }

    /**
     * Metoda obsługująca żądanie usunięcia rehabilitanta z bazy
     * @param id identyfikator rehabilitanta w bazie
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/deletePhysiotherapist/{id}")
    public ModelAndView deletePhysiotherapist(@PathVariable("id") Long id){

        try{
            userRepo.removeUserById(id);
        }catch (Exception e){
            return new ModelAndView("redirect:/showPhysiotherapists", "error", "Błąd podczas usuwania");

        }
        return new ModelAndView("redirect:/showPhysiotherapists", "message", "Rehabilitant usunięty pomyślnie");

    }





}
