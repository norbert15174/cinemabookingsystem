package pl.cinemabookingsystem.cinemabookingsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.cinemabookingsystem.cinemabookingsystem.Repository.RoomRepository;
import pl.cinemabookingsystem.cinemabookingsystem.models.Room;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    RoomRepository roomRepository;
    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public ResponseEntity<Room> addNewRoom(long seats){
        try{
            Room room = roomRepository.save(new Room(seats));
            return new ResponseEntity<>(room,HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    public ResponseEntity<List<Room>> findAllRooms(){
        return new ResponseEntity<>(roomRepository.findAll(),HttpStatus.OK);
    }
    public ResponseEntity<Room> findRoomById(long id){
        Optional<Room> room = roomRepository.findById(id);
        return room.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElse(null);
    }

}
