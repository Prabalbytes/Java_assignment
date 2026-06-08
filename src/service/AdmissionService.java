package service;

import java.util.ArrayList;

import data.CollegeData;
import model.College;
import model.Department;
import model.Student;

public class AdmissionService {

    private ArrayList<College> colleges;

    // ─────────────────────────────────────────
    // CONSTRUCTOR
    // ─────────────────────────────────────────

    public AdmissionService(
        ArrayList<College> colleges
    ) {

        this.colleges = colleges;
    }

    // ─────────────────────────────────────────
    // FIND SECTION
    // ─────────────────────────────────────────

    public College getCollege(String name) {

        for (College c : colleges) {

            if (c.getName()
                 .equalsIgnoreCase(name)) {

                return c;
            }
        }

        return null;
    }

    // ─────────────────────────────────────────
    // ADMISSION SECTION — saves to DB
    // ─────────────────────────────────────────

    public boolean processAdmission(
        String collegeName,
        String departmentName,
        Student student
    ) {

        // Check duplicate ID first
        if (CollegeData.isStudentIdExists(
            student.getId())
        ) {
            return false; // ID already exists
        }

        College college =
            getCollege(collegeName);

        if (college == null) return false;

        Department dept =
            college.getDepartment(departmentName);

        if (dept == null) return false;

        // Check seat in memory
        boolean admitted =
            dept.admitStudent(student);

        if (!admitted) return false;

        // Save to MySQL
        boolean saved =
            CollegeData.saveStudent(
                collegeName,
                departmentName,
                student.getId(),
                student.getName(),
                student.getSemester()
            );

        return saved;
    }

    // ─────────────────────────────────────────
    // DELETE SECTION
    // ─────────────────────────────────────────

    public boolean deleteStudent(
        String collegeName,
        String departmentName,
        int studentId
    ) {

        // Check if student exists first
        if (!CollegeData
                .isStudentIdExists(studentId)) {
            return false;
        }

        // Delete from DB
        boolean deleted =
            CollegeData.deleteStudent(studentId);

        if (!deleted) return false;

        // Update seat count in memory
        College college =
            getCollege(collegeName);

        if (college == null) return true;

        Department dept =
            college.getDepartment(departmentName);

        if (dept == null) return true;

        dept.restoreSeat();

        return true;
    }

    // ─────────────────────────────────────────
    // UPDATE SECTION
    // ─────────────────────────────────────────

    public boolean updateStudent(
        int studentId,
        String newName,
        int newSemester
    ) {

        if (!CollegeData
                .isStudentIdExists(studentId)) {
            return false;
        }

        return CollegeData.updateStudent(
            studentId, newName, newSemester
        );
    }

    // ─────────────────────────────────────────
    // EXPORT SECTION
    // ─────────────────────────────────────────

    public boolean exportToCSV(String filePath) {

        return CollegeData.exportToCSV(filePath);
    }
    // ─────────────────────────────────────────
    // SEARCH SECTION
    // ─────────────────────────────────────────

    public String searchStudent(int studentId) {

        return CollegeData
            .searchStudentById(studentId);
    }

    // ─────────────────────────────────────────
    // SHOW SECTION — fetches from DB
    // ─────────────────────────────────────────

    public String showStudents(
        String collegeName,
        String departmentName
    ) {

        return CollegeData.fetchStudents(
            collegeName,
            departmentName
        );
    }
}