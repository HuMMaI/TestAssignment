package com.example.testassignment.repo;

import com.example.testassignment.entity.Room;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomRepository {
    private List<Room> rooms = new ArrayList<>();
    private int lastId = 0;

    public void addNewRoom(int[][] points) {
        Room room = new Room(lastId++, points.length, points);

        rooms.add(room);
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
