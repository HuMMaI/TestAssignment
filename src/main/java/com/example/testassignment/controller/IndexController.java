package com.example.testassignment.controller;

import com.example.testassignment.dto.CoordinateDto;
import com.example.testassignment.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {
    @Autowired
    private PointsService pointsService;

    @GetMapping
    public String getMainPage() {
        return "index.html";
    }

    @PostMapping("/add")
    public String addNewRoom(@ModelAttribute CoordinateDto coordinateDto) {
        pointsService.addNewRoom(coordinateDto.getRoomPoints());

        return "redirect:/";
    }

}
