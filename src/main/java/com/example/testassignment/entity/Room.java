package com.example.testassignment.entity;

public class Room {
    private int id;
    private int[][] points;

    public Room(int id, int[][] points) {
        this.id = id;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[][] getPoints() {
        return points;
    }

    public void setPoints(int[][] points) {
        this.points = points;
    }
}
