package com.example.testassignment.controller;

import com.example.testassignment.dto.CoordinateDto;
import com.example.testassignment.entity.Room;
import com.example.testassignment.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
        boolean result = pointsService.addNewRoom(coordinateDto.getRoomPoints());

        if (result) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping("/rooms")
    public List<Room> getRooms() {
        return pointsService.getRooms();
    }

}
