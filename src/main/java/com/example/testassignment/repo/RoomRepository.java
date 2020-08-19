package com.example.testassignment.repo;

import com.example.testassignment.entity.Room;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomRepository {
    private List<Room> rooms = new ArrayList<>();
    private int lastId = 0;

    {
        int[][] points = {
                {1, 4}, {1, 1}, {2, 1}, {2, 3}, {3, 3}, {3, 4}
        };

        Room room = new Room(lastId++, points.length, points, false);
        rooms.add(room);

        int[][] points2 = {
                {3, 2}, {0, 2}, {0, 0}, {3, 0}
        };

        room = new Room(lastId++, points2.length, points2, false);
        rooms.add(room);

        int[][] points3 = {
                {2, 3}, {2, 1}, {4, 1}, {4, 3}
        };

        room = new Room(lastId++, points3.length, points3, false);
        rooms.add(room);

        int[][] points4 = {
                {10, 10}, {8, 10}, {8, 8}, {10, 8}
        };

        room = new Room(lastId++, points4.length, points4, false);
        rooms.add(room);
    }

    public void addNewRoom(int[][] points) {
        Room room = new Room(lastId++, points.length, points, true);

        rooms.add(room);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Room getRoomById(int roomId) {
        return rooms.stream()
                .filter(s -> s.getId() == roomId)
                .findFirst()
                .get();
    }

    public int[][] updateOnBoardValue(Room newRoom) {
        Room room = getRoomById(newRoom.getId());

        room.setOnBoard(newRoom.isOnBoard());

        return room.getPoints();
    }

    public int[][] deleteRoomById(int roomId) {
        Room roomById = getRoomById(roomId);
        int[][] points = roomById.getPoints();

        int index = rooms.indexOf(roomById);
        rooms.remove(index);

        return points;
    }
}
