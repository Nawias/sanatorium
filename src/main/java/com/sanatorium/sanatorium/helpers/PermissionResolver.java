package com.sanatorium.sanatorium.helpers;

import com.sanatorium.sanatorium.models.PatientCard;
import com.sanatorium.sanatorium.models.User;
import com.sanatorium.sanatorium.models.Visit;
import com.sanatorium.sanatorium.repo.PatientCardRepo;
import com.sanatorium.sanatorium.repo.UserRepo;
import com.sanatorium.sanatorium.repo.VisitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Service
public class PermissionResolver {

    UserRepo repo;
    VisitRepo visitRepo;
    PatientCardRepo patientCardRepo;
    //RehabilitationRepo rehabilitationRepo;

    @Autowired
    public PermissionResolver(UserRepo repo, VisitRepo visitRepo, PatientCardRepo patientCardRepo) {
        this.repo = repo;
        this.visitRepo = visitRepo;
        this.patientCardRepo = patientCardRepo;
    }

    /**
     * Metoda zwracająca odpowiednią stronę domową dla użytkownika
     * @param email - email użytkownika
     * @return - nazwa widoku strony domowej
     */
    public ModelAndView selectHome(String email) {
        ModelAndView mav = new ModelAndView();
        User user = repo.findUserByEmail(email);

        if (user != null) {
            if (user.getPermission().getLevel() == 1) { //admin

                 mav.setViewName("adminPanel");
                 return mav;

            } else if (user.getPermission().getLevel() == 2) { //recepcjonista
                mav.setViewName("home");
                return mav;


            }else if (user.getPermission().getLevel() == 3) { //lekarz
                Date date = new Date();
                List<Visit> visits = visitRepo.findTop10ByActiveAndDoctorOrderByDateTimeAsc(true, user);
                //List<Visit> visits = visitRepo.findAll(Sort.by(Sort.Direction.ASC,"id"));
                mav.addObject("visits", visits);
                mav.setViewName("doctor/home");
                return mav;
            }/*else if (user.getPermission().getLevel() == 4) { //lekarz
                Date date = new Date();
                List<Rehabilitation> rehabilitations =rehabilitationRepo.findTop10DoctorOrderByDateTimeAsc(user);

                mav.addObject("rehabilitations", rehabilitations);
                mav.setViewName("physiotherapist/home");
                return mav;
            }*/else if (user.getPermission().getLevel() == 5) { //pacjent
                Date date = new Date();
                List<Visit> visits = visitRepo.findByActiveAndPatientOrderByDateTimeAsc(true, user);
                List<PatientCard> cards = patientCardRepo.findPatientCardsByPatientOrderByIdDesc(user);

                mav.addObject("cards", cards);
                mav.addObject("visits", visits);
                mav.setViewName("patient/home");
                return mav;
            }
        }
        mav.setViewName("index");
        return mav;

    }
}
