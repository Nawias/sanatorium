package com.sanatorium.sanatorium.helpers;

import com.sanatorium.sanatorium.models.Room;
import com.sanatorium.sanatorium.models.Turnus;
import com.sanatorium.sanatorium.repo.RoomRepo;
import com.sanatorium.sanatorium.repo.TurnusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UpdateHelper {

    TurnusRepo turnusRepo;

    RoomRepo roomRepo;

    @Autowired
    public UpdateHelper(TurnusRepo turnusRepo, RoomRepo roomRepo) {
        this.turnusRepo = turnusRepo;
        this.roomRepo = roomRepo;
    }

    /**
     * Metoda odświeżająca listę aktywnych turnusów
     */
    public void refreshTurnuses() {
        List<Turnus> turnuses = turnusRepo.findAllByActive(true);
        Date date = new Date();


        for (Turnus turnus : turnuses) {

            if (date.after(turnus.getToDateTime())) {
                Room room = roomRepo.findRoomById(turnus.getRoom().getId());
                room.setState("wolny");
                roomRepo.save(room);
                turnus.setActive(false);
                turnusRepo.save(turnus);
            }


        }
    }


}
