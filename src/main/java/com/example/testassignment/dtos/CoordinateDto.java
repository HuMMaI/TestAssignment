package com.example.testassignment.dtos;

public class CoordinateDto {
    private Integer[] x;
    private Integer[] y;

    public int[][] getRoomPoints() {
        int[][] roomPoints = new int[x.length][2];

        for (int i = 0; i < x.length; i++) {
            roomPoints[i][0] = x[i];
            roomPoints[i][1] = y[i];
        }

        return roomPoints;
    }

    public void setX(Integer[] x) {
        this.x = x;
    }

    public void setY(Integer[] y) {
        this.y = y;
    }
}
