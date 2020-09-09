package com.sanatorium.sanatorium.controllers;

import com.sanatorium.sanatorium.models.Room;
import com.sanatorium.sanatorium.models.User;
import com.sanatorium.sanatorium.repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
public class RoomController {

    @Autowired
    RoomRepo roomRepo;
    /**
     * Metoda zwracająca widok z listą wszystkich pokoi w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/showRooms")
    public ModelAndView showAll(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        List<Room> rooms = roomRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));

        mav.setViewName("room/viewAll");

        if (rooms.isEmpty()) {


            mav.addObject("message", "Nie znaleźliśmy żadnego pacjenta");
            return mav;
        } else {
            mav.addObject("rooms", rooms);
            return mav;
        }

    }

    /**
     * Metoda zwracająca widok z formularzem dodawania pokoju
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/addRoom")
    public ModelAndView addRoom(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        String referer = req.getHeader("Referer");

        mav.setViewName("room/add");


        Room floors = roomRepo.findFirstByFloorIsGreaterThanOrderByFloorDesc(0);


        if (floors == null) {


            return new ModelAndView("redirect:" + referer, "error", "Wystąpił błąd!");

        } else {

            mav.addObject("floor", floors.getFloor());
            return mav;
        }

    }
    /**
     * Metoda obsługująca formularz dodawania pokoju do bazy
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @PostMapping("/saveRoom")
    public ModelAndView saveRoom(HttpServletRequest req) {
        String referer = req.getHeader("Referer");

        try {
            Room room = new Room();

            room.setFloor(Integer.parseInt(req.getParameter("floor")));
            room.setNumber(Integer.parseInt(req.getParameter("number")));
            room.setState("wolny");
            roomRepo.save(room);

            return new ModelAndView("redirect:/showRooms", "message", "Pokój dodany.");
        } catch (Exception e) {
            return new ModelAndView("redirect:" + referer, "error", "Wystąpił błąd!");

        }

    }

    /**
     * Metoda wyświetlająca formularz usuwania pokoju
     * @param id  identyfikator pokoju w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/deleteRoom/{id}")
    public ModelAndView deleteRoom(@PathVariable("id") Long id, HttpServletRequest req) {

        try {
            Long roomid = new Long(id);
            roomRepo.removeRoomById(roomid);
            return new ModelAndView("redirect:/showRooms", "message", "Pokój usunięty pomyślnie.");

        } catch (Exception e) {
            return new ModelAndView("redirect:/showRooms", "error", "Błąd podczas usuwania pokoju!");

        }

    }
    /**
     * Metoda wyświetlająca formularz edycji danych pokoju
     * @param id  identyfikator pokoju w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/editRoom/{id}")
    public ModelAndView editRoom(@PathVariable("id") Long id, HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        String referer = req.getHeader("Referer");

        Room room = roomRepo.findRoomById(id);
        if (room != null) {
            mav.setViewName("room/edit");
            mav.addObject("room", room);
            return mav;
        }

        return new ModelAndView("redirect:" + referer, "error", "Nie pokoju!");

    }

    /**
     * Metoda obsługująca formularz edycji danych pokoju
     * @param id  identyfikator pokoju w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @PostMapping("/editRoom/{id}")
    public ModelAndView updateRoom(@PathVariable("id") Long id, HttpServletRequest req) {
        String referer = req.getHeader("Referer");

        Room room = roomRepo.findRoomById(id);
        if (room != null) {
            try {
                room.setState(req.getParameter("state"));
                roomRepo.save(room);
                return new ModelAndView("redirect:/showRooms", "message", "Stan pokoju zaktualizowany pomyślnie.");

            } catch (Exception e) {
                return new ModelAndView("redirect:" + referer, "error", "Błąd podczas aktualizacji stanu.");

            }
        }
        return new ModelAndView("redirect:" + referer, "error", "Błąd podczas aktualizacji stanu.");

    }
}
