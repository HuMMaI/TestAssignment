package com.example.testassignment.controller;

import com.example.testassignment.dtos.CoordinateDto;
import com.example.testassignment.dtos.RoomUpdateDto;
import com.example.testassignment.entity.Room;
import com.example.testassignment.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class IndexController {
    @Autowired
    private PointsService pointsService;

    @GetMapping
    public String getMainPage() {
        return "index.html";
    }

    @ResponseBody
    @PostMapping("/add")
    public ResponseEntity<?> addNewRoom(@ModelAttribute CoordinateDto coordinateDto) {
        String result = pointsService.addNewRoom(coordinateDto.getRoomPoints());

        if (result.equals("")) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping("/rooms")
    public List<Room> getRooms() {
        return pointsService.getRooms();
    }

    @ResponseBody
    @GetMapping("/rooms/on-board")
    public List<Room> getOnBoardRooms() {
        return pointsService.getOnBoardRooms();
    }

    @ResponseBody
    @PutMapping("/rooms/on-board-upd")
    public ResponseEntity<?> updateOnBoardRooms(@RequestParam("roomId") int roomId, @RequestParam("value") boolean value) {
        Room room = pointsService.getRoomById(roomId);

        room.setOnBoard(value);

        Optional<RoomUpdateDto> roomUpdateDtoMaybe = pointsService.updateOnBoardValue(room);
        if (roomUpdateDtoMaybe.isPresent()) {
            return new ResponseEntity(roomUpdateDtoMaybe.get(), HttpStatus.OK);
        }

        return new ResponseEntity("Error! Your room intersects with another room!", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @DeleteMapping("/rooms/delete/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable("roomId") int roomId) {
        RoomUpdateDto roomUpdateDto = pointsService.deleteRoom(roomId);

        return new ResponseEntity(roomUpdateDto, HttpStatus.OK);
    }

}
