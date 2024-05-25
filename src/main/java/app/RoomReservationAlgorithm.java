package app;

import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import service.DBConnector;
import service.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomReservationAlgorithm {

    public static void main(String[] args) {
        UserService userService = new UserService();

        // Assuming you have TextFields or other UI elements to pass here
        // Example placeholders (Replace with actual UI elements)
        TextField txtStudentsNumber = new TextField();
        SplitMenuButton splitMenuButton = new SplitMenuButton();
        TextField txtStartTime = new TextField();
        TextField txtEndTime = new TextField();

//        // Simulate setting values (replace with actual values from the UI)
//        txtStudentsNumber.setText("30");
//        splitMenuButton.setText("Monday");
//        txtStartTime.setText("10:00:00");
//        txtEndTime.setText("12:00:00");

        int numStudents = userService.getNumStudents(txtStudentsNumber);
        String dayOfWeek = userService.getSelectedRole();
        Time startTime = userService.getTime(txtStartTime);
        Time endTime = userService.getTime(txtEndTime);

        try (Connection conn = DBConnector.getConnection()) {
            List<Room> availableRooms = queryAvailableRooms(conn, numStudents, dayOfWeek, startTime, endTime);
            Room selectedRoom = selectRoom(availableRooms, numStudents);

            if (selectedRoom != null) {
                reserveRoom(numStudents, dayOfWeek, startTime, endTime);
            } else {
                System.out.println("No room available for the given criteria.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Room> queryAvailableRooms(Connection conn, int numStudents, String dayOfWeek, Time startTime, Time endTime) throws SQLException {
        List<Room> availableRooms = new ArrayList<>();
        String query = "SELECT s.* " +
                "FROM salla s " +
                "WHERE s.capacity >= ? " +
                "AND s.id NOT IN (" +
                "    SELECT r.salla_id " +
                "    FROM Orari r " +
                "    JOIN TimeSlot t ON r.time_slot_id = t.id " +
                "    WHERE t.day_of_week = ? " +
                "    AND (" +
                "        (t.start_time >= ? AND t.start_time < ?) " +
                "        OR (t.end_time > ? AND t.end_time <= ?) " +
                "        OR (t.start_time <= ? AND t.end_time >= ?) " +
                "    )" +
                ")";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, numStudents);
            stmt.setString(2, dayOfWeek);
            stmt.setTime(3, startTime);
            stmt.setTime(4, endTime);
            stmt.setTime(5, startTime);
            stmt.setTime(6, endTime);
            stmt.setTime(7, startTime);
            stmt.setTime(8, endTime);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("salla_name");
                int capacity = rs.getInt("capacity");
                availableRooms.add(new Room(id, name, capacity));
            }
        }
        return availableRooms;
    }

    public static Room selectRoom(List<Room> availableRooms, int numStudents) {
        Room selectedRoom = null;
        int maxCapacity = 0;
        for (Room room : availableRooms) {
            if (room.getCapacity() >= numStudents && room.getCapacity() > maxCapacity) {
                selectedRoom = room;
                maxCapacity = room.getCapacity();
            }
        }
        return selectedRoom;
    }

    public static void reserveRoom(int numStudents, String dayOfWeek, Time startTime, Time endTime) {
        try (Connection conn = DBConnector.getConnection()) {
            List<Room> availableRooms = queryAvailableRooms(conn, numStudents, dayOfWeek, startTime, endTime);
            Room selectedRoom = selectRoom(availableRooms, numStudents);
            if (selectedRoom != null) {
                int profesorId = SessionManager.getUser().getId();
                System.out.println(profesorId);
                // Fetch fakulteti_id and lenda_id using profesorId
                String query = "SELECT faculty_id, lenda_id FROM profesor WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, profesorId);
                    ResultSet rs = stmt.executeQuery();
                    System.out.println("ne try eshte");
                    if (rs.next()) {
                        System.out.println("qetu ka hi");
                        int fakultetiId = rs.getInt("faculty_id");
                        int lendaId = rs.getInt("lenda_id");
                        String insertQuery = "INSERT INTO Orari (fakulteti_id, profesori_id, lenda_id, salla_id, time_slot_id, start_time, end_time, day_of_week, capacity) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                            insertStmt.setInt(1, fakultetiId);
                            insertStmt.setInt(2, profesorId);
                            insertStmt.setInt(3, lendaId);
                            insertStmt.setInt(4, selectedRoom.getId());
                            insertStmt.setInt(5, getTimeSlotId(conn, dayOfWeek, startTime, endTime));
                            insertStmt.setTime(6, startTime);
                            insertStmt.setTime(7, endTime);
                            insertStmt.setString(8, dayOfWeek);
                            insertStmt.setInt(9, selectedRoom.getCapacity());
                            insertStmt.executeUpdate();
                            System.out.println("Room reserved: " + selectedRoom.getName());
                        }
                    }
                }
            } else {
                System.out.println("No room available for the given criteria.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getTimeSlotId(Connection conn, String dayOfWeek, Time startTime, Time endTime) throws SQLException {
        String query = "SELECT id FROM TimeSlot WHERE day_of_week = ? AND start_time = ? AND end_time = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, dayOfWeek);
            stmt.setTime(2, startTime);
            stmt.setTime(3, endTime);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new SQLException("Time slot not found for dayOfWeek: " + dayOfWeek + ", startTime: " + startTime + ", endTime: " + endTime);
    }

    // Room class
    static class Room {
        private final int id;
        private final String name;
        private final int capacity;

        public Room(int id, String name, int capacity) {
            this.id = id;
            this.name = name;
            this.capacity = capacity;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getCapacity() {
            return capacity;
        }
    }
}
