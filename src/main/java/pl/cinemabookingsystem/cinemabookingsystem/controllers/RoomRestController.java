package pl.cinemabookingsystem.cinemabookingsystem.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.cinemabookingsystem.cinemabookingsystem.models.Room;
import pl.cinemabookingsystem.cinemabookingsystem.services.RoomService;

import java.util.List;

@RestController
@RequestMapping(path = "/room")
public class RoomRestController {

    private RoomService roomService;
    @Autowired
    public RoomRestController(RoomService roomService) {
        this.roomService = roomService;
    }
    @PostMapping("/addroom")
    public ResponseEntity<Room> addNewRoom(long roomSeat){
        return roomService.addNewRoom(roomSeat);
    }
    @GetMapping()
    public ResponseEntity<List<Room>> findAllRooms(){
        return roomService.findAllRooms();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Room> findRoomById(@PathVariable long id){
        return roomService.findRoomById(id);
    }

}
