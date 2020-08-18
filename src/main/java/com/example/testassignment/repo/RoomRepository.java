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
        Room room = new Room(lastId++, points.length, points, true);

        rooms.add(room);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Room getRoomById(int roomId) {
        return rooms.get(roomId);
    }

    public int[][] updateOnBoardValue(Room newRoom) {
        Room room = rooms.get(newRoom.getId());

        room.setOnBoard(newRoom.isOnBoard());

        return room.getPoints();
    }

    public int[][] deleteRoomById(int roomId) {
        int[][] points = rooms.get(roomId).getPoints();

        rooms.remove(roomId);

        return points;
    }
}
