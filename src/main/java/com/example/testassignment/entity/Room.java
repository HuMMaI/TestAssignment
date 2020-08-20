package com.example.testassignment.entity;

public class Room {
    private int id;
    private int numberOfPoint;
    private int[][] points;
    private boolean onBoard;

    public Room(int id, int numberOfPoint, int[][] points, boolean onBoard) {
        this.id = id;
        this.numberOfPoint = numberOfPoint;
        this.points = points;
        this.onBoard = onBoard;
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

    public boolean isOnBoard() {
        return onBoard;
    }

    public void setOnBoard(boolean onBoard) {
        this.onBoard = onBoard;
    }
}
