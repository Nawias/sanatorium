package com.sanatorium.sanatorium.controllers;

import com.sanatorium.sanatorium.models.Permission;
import com.sanatorium.sanatorium.models.User;
import com.sanatorium.sanatorium.repo.PermissionRepo;
import com.sanatorium.sanatorium.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Transactional
@Controller
public class UserController {

    @Autowired
    UserRepo repo;

    @Autowired
    PermissionRepo permRepo;

    /**
     * Metoda zwracająca widok z listą wszystkich użytkownikków w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/showUsers")
    public ModelAndView showAll(HttpServletRequest req) {

        List<User> users = repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        ModelAndView mav = new ModelAndView();

        if (users != null && !users.isEmpty()) {
            mav.setViewName("users/viewAll");
            mav.addObject("users", users);
            return mav;
        }

        mav.setViewName("users/viewAll");
        mav.addObject("message", "Nie znaleźliśmy żadnego użytkownika");
        return mav;
    }
    /**
     * Metoda zwracająca widok z formularzem dodawania użytkownika
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/addUser")
    public ModelAndView addUser(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();

        List<Permission> permissions = permRepo.findAll();
        //List<Permission> permissions = permRepo.findByLevelNot(5);
        mav.setViewName("users/add");
        mav.addObject("permissions", permissions);
        return mav;
    }
    /**
     * Metoda obsługująca formularz dodawania użytkownika do bazy
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @PostMapping("/saveUser")
    public ModelAndView store(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String login = req.getParameter("login");
        int permId = Integer.parseInt(req.getParameter("role"));
        Permission permission = permRepo.findPermissionById(permId);


        User user = new User();
        // user.setId(1);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(login);
        user.setPassword("zaq1@WSX");
        user.setPermission(permission);

        for (int i = 0; i < 10; i++) {
            try {

                repo.save(user);

            } catch (Exception e) {
                continue;
            }
            break;
        }

        return new ModelAndView("redirect:/showUsers", "message", "Użytkownik dodany pomyślnie");
    }
    /**
     * Metoda obsługująca żądanie usunięcia użytkownika z bazy
     * @param id identyfikator użytkownika w bazie
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/deleteUser/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Long id) {


      //  System.out.println("id = " + id);
        try {
            repo.removeUserById(id);
        } catch (Exception e) {
            return new ModelAndView("redirect:/showUsers", "error", "Błąd podczas usuwania");

        }

        return new ModelAndView("redirect:/showUsers", "message", "Użytkownik usunięty pomyślnie");

    }

    /**
     * Metoda wyświetlająca formularz edycji danych użytkownika
     * @param id  identyfikator użytkownika w bazie
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/editUser/{id}")
    public ModelAndView getUserEditForm(@PathVariable("id") Long id) {

        User user = repo.findUserById(id);

        if (user != null) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("users/editUser");
            mav.addObject("user", user);
            //List<Permission> permissions = permRepo.findAll();
            List<Permission> permissions = permRepo.findByLevelNot(5);
            mav.addObject("permissions", permissions);
            return mav;
        }

        return new ModelAndView("redirect:/showUsers", "error", "Wystąpił błąd");

    }
    /**
     * Metoda obsługująca formularz edycji danych użytkownika
     * @param id  identyfikator użytkownika w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/updateUser/{id}")
    public ModelAndView updateUser(@PathVariable("id") Long id, HttpServletRequest req) {

        try {
            User user = repo.findUserById(id);

            if (user != null) {
                user.setName(req.getParameter("name"));
                user.setSurname(req.getParameter("surname"));
                user.setPermission(permRepo.findPermissionById(Integer.parseInt(req.getParameter("role"))));
                repo.save(user);

                return new ModelAndView("redirect:/showUsers", "message", "Dane zaktualizowane pomyślnie");
            }
        } catch (Exception e) {
            return new ModelAndView("redirect:/showUsers", "error", "Wystąpił błąd podczas aktualizacji danych");
        }

        return new ModelAndView("redirect:/showUsers", "error", "Wystąpił błąd podczas aktualizacji danych");
    }

    /**
     * Metoda wyświetlająca widok z ustawieniami użytkownika
     * @param req    zapytanie http
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/user/configuration/")
    public ModelAndView accountConfig(HttpServletRequest req, Authentication authentication) {
        String email = "";
        try {
            DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
            Map attributes = oidcUser.getAttributes();
            email = (String) attributes.get("email");
        }catch(Exception e){}
        User user = repo.findUserByEmail(email);
        ModelAndView mav = new ModelAndView();
        if (user != null) {
            mav.addObject(user);
            mav.setViewName("users/config");
            return mav;
        }

        return new ModelAndView("redirect:/");

    }
    /**
     * Metoda wyświetlająca formularz edycji hasła użytkownika
     * @param id  identyfikator użytkownika w bazie
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/user/edit/password/{id}")
    public ModelAndView changePasswordForm(@PathVariable("id") Long id, HttpServletRequest req) {
        User user = repo.findUserById(id);
        ModelAndView mav = new ModelAndView();

        if (user != null) {
            mav.addObject(user);
            mav.setViewName("users/password");
            return mav;
        }

                return new ModelAndView("redirect:/");

    }
    /**
     * Metoda obsługująca żądanie resetowania hasła użytkownika
     * @param id  identyfikator użytkownika w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @RequestMapping("/resetPassword/{id}")
    public ModelAndView resetPassword(@PathVariable("id") Long id, HttpServletRequest req) {
        User user = repo.findUserById(id);
        ModelAndView mav = new ModelAndView();

        if (user != null) {
            user.setPassword("zaq1@WSX");
            repo.save(user);

            return new ModelAndView("redirect:/showUsers", "message", "Hasło zresetowane pomyślnie");

        }

                return new ModelAndView("redirect:/");

    }

    /**
     * Metoda obsługująca żądanie zmiany hasła użytkownika
     * @param id  identyfikator użytkownika w bazie
     * @param req zapytanie HTTP
     * @return obiekt ModelAndView z odpowiedzią
     */
    @PostMapping("/user/edit/password/{id}")
    public ModelAndView changePassword(@PathVariable("id") Long id, HttpServletRequest req) {
        User user = repo.findUserById(id);
        ModelAndView mav = new ModelAndView();

        String password = req.getParameter("current");
        String newPassword = req.getParameter("new");
        String confirm = req.getParameter("confirm");

        if (user != null) {

            if(password.equals(user.getPassword())){
                if (newPassword.equals(confirm)){
                    user.setPassword(newPassword);
                    repo.save(user);

                    mav.addObject(user);
                    mav.addObject("message", "Hasło zostało zmienione.");
                    mav.setViewName("users/config");
                    return mav;
                }else{
                    String referer = req.getHeader("Referer");
                    return new ModelAndView("redirect:"+referer, "error", "Podane hasła nie są zgodne!");
                }
            }

            String referer = req.getHeader("Referer");
            return new ModelAndView("redirect:"+referer, "error", "Stare hasło niepoprawne!");

        }

                return new ModelAndView("redirect:/");

    }


}