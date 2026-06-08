package model;

public class Student {

    private int id;
    private String name;
    private int semester;

    // ─────────────────────────────────────────
    // CONSTRUCTOR
    // ─────────────────────────────────────────

    public Student(
        int id,
        String name,
        int semester
    ) {

        this.id       = id;
        this.name     = name;
        this.semester = semester;
    }

    // ─────────────────────────────────────────
    // GETTERS — needed for DB save
    // ─────────────────────────────────────────

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSemester() {
        return semester;
    }

    // ─────────────────────────────────────────
    // DISPLAY
    // ─────────────────────────────────────────

    public String display() {

        return
            "ID: "       + id       +
            " | Name: "  + name     +
            " | Semester: " + semester;
    }
}