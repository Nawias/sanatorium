package com.sanatorium.sanatorium.controllers;

import com.sanatorium.sanatorium.helpers.PermissionResolver;
import com.sanatorium.sanatorium.helpers.UpdateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    PermissionResolver permissionResolver;

    @Autowired
    UpdateHelper updateHelper;

    /**
     * Metoda zwracająca stronę startową
     * @return plik JSP zawierający odpowiedną stronę
     */
    @RequestMapping("/")
    public ModelAndView homePage(HttpServletRequest req, Authentication authentication){
        ModelAndView mav = new ModelAndView();
        String email = "";
        try {
            DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
            Map attributes = oAuth2User.getAttributes();
            email = (String) attributes.get("email");
        }catch(Exception e){e.printStackTrace();}
        if (req.getSession().getAttribute("user") != "" ){
            updateHelper.refreshTurnuses();
            return permissionResolver.selectHome(email );
        }else {
            req.getSession().setAttribute("user","");
        }
        mav.setViewName("index");

        return mav;
    }
}
