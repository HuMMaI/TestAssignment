package com.example.testassignment.service;

import org.springframework.stereotype.Service;

@Service
public class PointsService {
    public void addNewRoom(int[][] points) {
        int lastXDiv = points[points.length - 1][0] - points[0][0];
        int lastYDiv = points[points.length - 1][1] - points[0][1];

        if (lastYDiv == 0 && lastXDiv > 0) {
            System.out.println("OK x:" + lastXDiv + ", y:" + lastYDiv);
        } else if (lastYDiv > 0 && lastXDiv == 0) {
            System.out.println("OK x:" + lastXDiv + ", y:" + lastYDiv);
        } else {
            System.out.println("NOT OK x:" + lastXDiv + ", y:" + lastYDiv);
        }
    }
}
