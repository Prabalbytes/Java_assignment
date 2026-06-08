package model;

import java.util.ArrayList;

public class College {

    private String name;
    private ArrayList<Department> departments;

    // ─────────────────────────────────────────
    // CONSTRUCTOR
    // ─────────────────────────────────────────

    public College(String name) {

        this.name        = name;
        this.departments = new ArrayList<>();
    }

    // ─────────────────────────────────────────
    // GETTERS
    // ─────────────────────────────────────────

    public String getName() {
        return name;
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    // ─────────────────────────────────────────
    // DEPARTMENT SECTION
    // ─────────────────────────────────────────

    public void addDepartment(Department d) {
        departments.add(d);
    }

    public Department getDepartment(String name) {

        for (Department d : departments) {

            if (d.getName()
                 .equalsIgnoreCase(name)) {

                return d;
            }
        }

        return null;
    }
}