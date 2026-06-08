package model;

import java.util.ArrayList;

public class Department {

    private String name;
    private int totalSeats;
    private int availableSeats;
    private ArrayList<Student> students;

    // ─────────────────────────────────────────
    // CONSTRUCTOR
    // ─────────────────────────────────────────

    public Department(
        String name,
        int totalSeats,
        int availableSeats
    ) {

        this.name           = name;
        this.totalSeats     = totalSeats;
        this.availableSeats = availableSeats;
        this.students       = new ArrayList<>();
    }

    // ─────────────────────────────────────────
    // GETTERS
    // ─────────────────────────────────────────

    public String getName() {
        return name;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    // ─────────────────────────────────────────
    // ADMIT SECTION
    // ─────────────────────────────────────────

    public boolean admitStudent(Student s) {

        if (availableSeats <= 0) return false;

        students.add(s);
        availableSeats--;

        return true;
    }

    // ─────────────────────────────────────────
    // DISPLAY
    // ─────────────────────────────────────────

    public String getStudentData() {

        if (students.isEmpty()) {
            return "No Students Found";
        }

        String data = "";

        for (Student s : students) {
            data += s.display() + "\n";
        }

        return data;
    }
    // ─────────────────────────────────────────
    // RESTORE SEAT — when student deleted
    // ─────────────────────────────────────────

    public void restoreSeat() {

        if (availableSeats < totalSeats) {
            availableSeats++;
        }
    }
}