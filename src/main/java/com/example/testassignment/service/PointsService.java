package com.example.testassignment.service;

import com.example.testassignment.dtos.RoomUpdateDto;
import com.example.testassignment.entity.Room;
import com.example.testassignment.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PointsService {
    @Autowired
    private RoomRepository roomRepository;

    public String addNewRoom(int[][] points) {
        if (!rightAngleChecker(points)){
            return "Error! There are no right angles in your room!";
        }

        if (!roomAreaChecker(points)) {
            return "Error! X and Y value cannot be more than 10!";
        }

        int preLastX = points[points.length - 1][0] - points[points.length - 2][0];
        int preLastY = points[points.length - 1][1] - points[points.length - 2][1];

        int lastX = points[0][0] - points[points.length - 1][0];
        int lastY = points[0][1] - points[points.length - 1][1];

        boolean clockwiseCond1 = (preLastX > 0 && preLastY == 0) && (lastY > 0 && lastX == 0);
        boolean clockwiseCond2 = (preLastY > 0 && preLastX == 0) && (lastX < 0 && lastY == 0);
        boolean clockwiseCond3 = (preLastX < 0 && preLastY == 0) && (lastY < 0 && lastX == 0);
        boolean clockwiseCond4 = (preLastY < 0 && preLastX == 0) && (lastX > 0 && lastY == 0);

        if (!(clockwiseCond1 || clockwiseCond2 || clockwiseCond3 || clockwiseCond4)) {
            return "Error! There are no clockwise points in your room!";
        }

        List<int[][]> onBoardPoints = getOnBoardRooms().stream()
                .map(Room::getPoints)
                .collect(Collectors.toList());

        if (!intersectionChecker(points, onBoardPoints)) {
            return "Error! Your room intersects with another room!";
        }

        roomRepository.addNewRoom(points);
        return "";
    }

    private boolean roomAreaChecker(int[][] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i][0] > 10 || points[i][1] > 10) {
                return false;
            }
        }

        return true;
    }

    public List<Room> getRooms() {
        return roomRepository.getRooms();
    }

    public List<Room> getOnBoardRooms() {
        List<Room> rooms = roomRepository.getRooms();

        return rooms.stream()
                .filter(Room::isOnBoard)
                .collect(Collectors.toList());
    }

    public Room getRoomById(int roomId) {
        return roomRepository.getRoomById(roomId);
    }

    public Optional<RoomUpdateDto> updateOnBoardValue(Room room) {
        int[][] points = roomRepository.updateOnBoardValue(room);

        RoomUpdateDto roomUpdateDto = roomUpdateDtoParser(points);

        List<int[][]> onBoardPoints = getOnBoardRooms().stream()
                .filter(s -> s.getId() != room.getId())
                .map(Room::getPoints)
                .collect(Collectors.toList());

        if (!room.isOnBoard() || onBoardPoints.size() <= 1) {
            return Optional.of(roomUpdateDto);
        }

        if (!intersectionChecker(points, onBoardPoints)) {
            return Optional.empty();
        }

        return Optional.of(roomUpdateDto);
    }

    private boolean rightAngleChecker(int[][] points) {
        for (int i = 1; i < points.length; i++) {
            int x = points[i][0] - points[i - 1][0];
            int y = points[i][1] - points[i - 1][1];

            if (x != 0 && y != 0) {
                return false;
            }
        }

        return true;
    }

    private boolean intersectionChecker(int[][] points, List<int[][]> onBoardPoints) {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < onBoardPoints.size(); j++) {
                for (int k = 0; k < onBoardPoints.get(j).length; k++) {
                    int v1x = points[(i + 1) % points.length][0] - points[i][0];
                    int v1y = points[(i + 1) % points.length][1] - points[i][1];

                    int v2x = onBoardPoints.get(j)[k][0] - points[i][0];
                    int v2y = onBoardPoints.get(j)[k][1] - points[i][1];

                    int v3x = onBoardPoints.get(j)[(k + 1) % onBoardPoints.get(j).length][0] - points[i][0];
                    int v3y = onBoardPoints.get(j)[(k + 1) % onBoardPoints.get(j).length][1] - points[i][1];

                    int v4x = onBoardPoints.get(j)[(k + 1) % onBoardPoints.get(j).length][0] - onBoardPoints.get(j)[k][0];
                    int v4y = onBoardPoints.get(j)[(k + 1) % onBoardPoints.get(j).length][1] - onBoardPoints.get(j)[k][1];

                    int v5x = points[(i + 1) % points.length][0] - onBoardPoints.get(j)[k][0];
                    int v5y = points[(i + 1) % points.length][1] - onBoardPoints.get(j)[k][1];

                    int v6x = points[i][0] - onBoardPoints.get(j)[k][0];
                    int v6y = points[i][1] - onBoardPoints.get(j)[k][1];

                    int c1 = v1x * v2y - v1y * v2x;
                    int c2 = v1x * v3y - v1y * v3x;
                    int c3 = v4x * v5y - v4y * v5x;
                    int c4 = v4x * v6y - v4y * v6x;

                    boolean result = (c1 * c2 <= 0) && (c3 * c4 <= 0);

                    if (result) {
                        boolean condY1 = points[i][1] == points[(i + 1) % points.length][1];
                        boolean condY2 = onBoardPoints.get(j)[k][1] == onBoardPoints.get(j)[(k + 1) % onBoardPoints.get(j).length][1];
                        boolean condY3 = points[i][1] == onBoardPoints.get(j)[k][1];

                        if (condY1 && condY2 && condY3) {
                            boolean condX1 = points[i][0] > onBoardPoints.get(j)[k][0] && points[i][0] > onBoardPoints.get(j)[(k + 1) % onBoardPoints.get(j).length][0];
                            boolean condX2 = points[i][0] < onBoardPoints.get(j)[k][0] && points[i][0] < onBoardPoints.get(j)[(k + 1) % onBoardPoints.get(j).length][0];
                            boolean condX3 = condX1 || condX2;
                            boolean condX4 = points[(i + 1) % points.length][0] > onBoardPoints.get(j)[k][0] && points[i][0] > onBoardPoints.get(j)[(k + 1) % onBoardPoints.get(j).length][0];
                            boolean condX5 = points[(i + 1) % points.length][0] < onBoardPoints.get(j)[k][0] && points[i][0] < onBoardPoints.get(j)[(k + 1) % onBoardPoints.get(j).length][0];
                            boolean condX6 = condX4 || condX5;

                            if (condX3 && condX6) {
                                continue;
                            }
                        }

                        boolean condX1 = points[i][0] == points[(i + 1) % points.length][0];
                        boolean condX2 = onBoardPoints.get(j)[k][0] == onBoardPoints.get(j)[(k + 1) % onBoardPoints.get(j).length][0];
                        boolean condX3 = points[i][0] == onBoardPoints.get(j)[k][0];

                        if (condX1 && condX2 && condX3) {
                            condY1 = points[i][1] > onBoardPoints.get(j)[k][1] && points[i][1] > onBoardPoints.get(j)[(k + 1) % onBoardPoints.get(j).length][1];
                            condY2 = points[i][1] < onBoardPoints.get(j)[k][1] && points[i][1] < onBoardPoints.get(j)[(k + 1) % onBoardPoints.get(j).length][1];
                            condY3 = condY1 || condY2;
                            boolean condY4 = points[(i + 1) % points.length][1] > onBoardPoints.get(j)[k][1] && points[i][1] > onBoardPoints.get(j)[(k + 1) % onBoardPoints.get(j).length][1];
                            boolean condY5 = points[(i + 1) % points.length][1] < onBoardPoints.get(j)[k][1] && points[i][1] < onBoardPoints.get(j)[(k + 1) % onBoardPoints.get(j).length][1];
                            boolean condY6 = condY4 || condY5;

                            if (condY3 && condY6) {
                                continue;
                            }
                        }
                        return false;
                    }

                }
            }
        }

        return true;
    }

    public RoomUpdateDto deleteRoom(int roomId) {
        int[][] points = roomRepository.deleteRoomById(roomId);

        return roomUpdateDtoParser(points);
    }

    private RoomUpdateDto roomUpdateDtoParser(int[][] points) {
        RoomUpdateDto roomUpdateDto = new RoomUpdateDto();

        Integer[] x = Arrays.stream(points).map(s -> s[0]).toArray(Integer[]::new);
        Integer[] y = Arrays.stream(points).map(s -> s[1]).toArray(Integer[]::new);

        roomUpdateDto.setX(x);
        roomUpdateDto.setY(y);

        return roomUpdateDto;
    }
}
