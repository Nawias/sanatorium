package com.sanatorium.sanatorium.controllers;


import com.sanatorium.sanatorium.models.Room;
import com.sanatorium.sanatorium.models.Turnus;
import com.sanatorium.sanatorium.models.User;
import com.sanatorium.sanatorium.repo.PermissionRepo;
import com.sanatorium.sanatorium.repo.RoomRepo;
import com.sanatorium.sanatorium.repo.TurnusRepo;
import com.sanatorium.sanatorium.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@Transactional
public class TurnusesController {

    @Autowired
    TurnusRepo turnusRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoomRepo roomRepo;

    @Autowired
    PermissionRepo permRepo;
    /**
     * Metoda zwracająca widok z listą wszystkich turnusów w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/showTurnuses")
    public ModelAndView showAll(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("turnus/viewAll");

        List<Turnus> turnuses = turnusRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));

        if (turnuses.isEmpty()) {
            mav.addObject("message", "Nie znaleźliśmy żadnego pobytu.");

        } else {
            mav.addObject("turnuses", turnuses);

        }

        return mav;
    }
    /**
     * Metoda zwracająca widok z listą wszystkich aktywnych turnusów w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/showTurnuses/active")
    public ModelAndView showActive(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("turnus/viewAll");

        List<Turnus> turnuses = turnusRepo.findAllByActiveOrderById(true);

        if (turnuses.isEmpty()) {
            mav.addObject("message", "Nie znaleźliśmy żadnego pobytu.");

        } else {
            mav.addObject("turnuses", turnuses);

        }

        return mav;
    }
    /**
     * Metoda zwracająca widok z listą wszystkich nieaktywnych turnusów w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/showTurnuses/noactive")
    public ModelAndView showNoactive(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("turnus/viewAll");

        List<Turnus> turnuses = turnusRepo.findAllByActiveOrderById(false);

        if (turnuses.isEmpty()) {
            mav.addObject("message", "Nie znaleźliśmy żadnego pobytu.");

        } else {
            mav.addObject("turnuses", turnuses);

        }

        return mav;
    }


    /**
     * Metoda zwracająca widok z formularzem dodawania turnusu
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/addTurnus")
    public ModelAndView addTurnus(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("turnus/add");
        List<User> patients = userRepo.findUsersByPermissionName("patient");
        List<Room> rooms = roomRepo.findRoomsByStateOrderByIdAsc("wolny");//roomRepo.findAll(Sort.by(Sort.Direction.ASC,"id"));
        mav.addObject("patients", patients);
        mav.addObject("rooms", rooms);


        return mav;
    }
    /**
     * Metoda obsługująca formularz dodawania turnusu do bazy
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @PostMapping("/saveTurnus")
    public ModelAndView saveTurnus(HttpServletRequest req) {

        String referer = req.getHeader("Referer");
        User patient = null;
        try {
            ModelAndView mav = new ModelAndView();
            String checkbox = (req.getParameter("newPatient"));
            Turnus turnus = new Turnus();



            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {

                //  System.out.println("date from string:" + date.toString());
                turnus.setFromDateTime(dateFormat.parse(req.getParameter("dateStart")));
                turnus.setToDateTime(dateFormat.parse(req.getParameter("dateEnd")));

                System.out.println(turnus.getFromDateTime().toString());
            } catch (Exception e) {
                return new ModelAndView("redirect:" + referer, "error", "Nie udało się dodać pobytu, nie wybrano daty!");
            }

            Room room = roomRepo.findRoomById(Long.parseLong(req.getParameter("room")));
            turnus.setRoom(room);

            turnus.setActive(true);


            if (checkbox == null) {
                patient = userRepo.findUserById(Long.parseLong(req.getParameter("patient")));
                turnus.setPatient(patient);

            } else {
                patient = new User();

                patient.setName(req.getParameter("name"));
                patient.setSurname(req.getParameter("surname"));
                patient.setPermission(permRepo.findPermissionByName("patient"));
                patient.setPassword("zaq1@WSX");

                String login = req.getParameter("name").substring(0, 1) + "." + req.getParameter("surname");
                login = login.toLowerCase();
                if (userRepo.findUserByEmail(login) == null) {
                    patient.setEmail(login);
                } else {
                    int i = 2;
                    String baseLogin = new String(login);
                    while (true) {
                        login = baseLogin + String.valueOf(i);
                        if (userRepo.findUserByEmail(login) == null) {
                            patient.setEmail(login);
                            break;
                        }

                        i++;
                    }

                }


                userRepo.save(patient);
                turnus.setPatient(patient);

            }

            turnusRepo.save(turnus);
            room.setState("zajety");
            roomRepo.save(room);
            return new ModelAndView("redirect:/showTurnuses", "message", "Podbyt dodany. Pacjent: " + patient.getEmail());


        } catch (Exception e) {

            return new ModelAndView("redirect:" + referer, "error", "Nie udało się dodać pobytu!");
        }

    }

}
