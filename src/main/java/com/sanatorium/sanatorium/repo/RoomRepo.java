package com.sanatorium.sanatorium.repo;

import com.sanatorium.sanatorium.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepo extends JpaRepository<Room, Integer> {

    Room findRoomById(Long id);

    Room removeRoomById(Long id);

    Room findFirstByFloorIsGreaterThanOrderByFloorDesc(int floor);

    List<Room> findRoomsByStateOrderByIdAsc(String state);

}
