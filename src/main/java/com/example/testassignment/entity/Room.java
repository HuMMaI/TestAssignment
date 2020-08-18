package com.example.testassignment.entity;

public class Room {
    private int id;
    private int numberOfPoint;
    private int[][] points;

    public Room(int id, int numberOfPoint, int[][] points) {
        this.id = id;
        this.numberOfPoint = numberOfPoint;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfPoint() {
        return numberOfPoint;
    }

    public void setNumberOfPoint(int numberOfPoint) {
        this.numberOfPoint = numberOfPoint;
    }

    public int[][] getPoints() {
        return points;
    }

    public void setPoints(int[][] points) {
        this.points = points;
    }
}
