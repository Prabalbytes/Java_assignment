package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.College;
import model.Department;

public class CollegeData {

    // ─────────────────────────────────────────
    // LOAD SECTION — fetch from DB
    // ─────────────────────────────────────────

    public static ArrayList<College> loadColleges() {

        ArrayList<College> colleges =
            new ArrayList<>();

        try {

            Connection con =
                DatabaseConnection.getConnection();

            // Fetch all colleges
            String collegeSQL =
                "SELECT * FROM colleges";

            PreparedStatement collegeStmt =
                con.prepareStatement(collegeSQL);

            ResultSet collegeRs =
                collegeStmt.executeQuery();

            while (collegeRs.next()) {

                int collegeId =
                    collegeRs.getInt("id");

                String collegeName =
                    collegeRs.getString("name");

                College college =
                    new College(collegeName);

                // Fetch departments for this college
                String deptSQL =
                    "SELECT * FROM departments " +
                    "WHERE college_id = ?";

                PreparedStatement deptStmt =
                    con.prepareStatement(deptSQL);

                deptStmt.setInt(1, collegeId);

                ResultSet deptRs =
                    deptStmt.executeQuery();

                while (deptRs.next()) {

                    String deptName =
                        deptRs.getString("name");

                    int totalSeats =
                        deptRs.getInt("total_seats");

                    int deptId =
                        deptRs.getInt("id");

                    // Count admitted students
                    int admittedCount =
                        getAdmittedCount(
                            con, deptId
                        );

                    int availableSeats =
                        totalSeats - admittedCount;

                    Department dept =
                        new Department(
                            deptName,
                            totalSeats,
                            availableSeats
                        );

                    college.addDepartment(dept);
                }

                colleges.add(college);
            }

        } catch (Exception e) {

            System.out.println(
                "Error loading colleges: " +
                e.getMessage()
            );
        }

        return colleges;
    }

    // ─────────────────────────────────────────
    // HELPER — count admitted students
    // ─────────────────────────────────────────

    private static int getAdmittedCount(
        Connection con,
        int deptId
    ) {

        try {

            String sql =
                "SELECT COUNT(*) FROM students " +
                "WHERE department_id = ?";

            PreparedStatement stmt =
                con.prepareStatement(sql);

            stmt.setInt(1, deptId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

            System.out.println(
                "Count error: " + e.getMessage()
            );
        }

        return 0;
    }

    // ─────────────────────────────────────────
    // SAVE SECTION — insert student to DB
    // ─────────────────────────────────────────

    public static boolean saveStudent(
        String collegeName,
        String deptName,
        int studentId,
        String studentName,
        int semester
    ) {

        try {

            Connection con =
                DatabaseConnection.getConnection();

            // Get department_id
            String sql =
                "SELECT d.id FROM departments d " +
                "JOIN colleges c " +
                "ON d.college_id = c.id " +
                "WHERE c.name = ? " +
                "AND d.name = ?";

            PreparedStatement stmt =
                con.prepareStatement(sql);

            stmt.setString(1, collegeName);
            stmt.setString(2, deptName);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) return false;

            int deptId = rs.getInt("id");

            // Insert student
            String insertSQL =
                "INSERT INTO students " +
                "(id, department_id, name, semester) " +
                "VALUES (?, ?, ?, ?)";

            PreparedStatement insertStmt =
                con.prepareStatement(insertSQL);

            insertStmt.setInt(1, studentId);
            insertStmt.setInt(2, deptId);
            insertStmt.setString(3, studentName);
            insertStmt.setInt(4, semester);

            insertStmt.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                "Save error: " + e.getMessage()
            );

            return false;
        }
    }

    // ─────────────────────────────────────────
    // FETCH SECTION — get students from DB
    // ─────────────────────────────────────────

    public static String fetchStudents(
        String collegeName,
        String deptName
    ) {

        try {

            Connection con =
                DatabaseConnection.getConnection();

            String sql =
                "SELECT s.id, s.name, s.semester " +
                "FROM students s " +
                "JOIN departments d " +
                "ON s.department_id = d.id " +
                "JOIN colleges c " +
                "ON d.college_id = c.id " +
                "WHERE c.name = ? " +
                "AND d.name = ?";

            PreparedStatement stmt =
                con.prepareStatement(sql);

            stmt.setString(1, collegeName);
            stmt.setString(2, deptName);

            ResultSet rs = stmt.executeQuery();

            StringBuilder sb = new StringBuilder();

            while (rs.next()) {

                sb.append("ID: ")
                  .append(rs.getInt("id"))
                  .append(" | Name: ")
                  .append(rs.getString("name"))
                  .append(" | Semester: ")
                  .append(rs.getInt("semester"))
                  .append("\n");
            }

            return sb.length() == 0
                ? "No Students Found"
                : sb.toString();

        } catch (Exception e) {

            return "Error: " + e.getMessage();
        }
    }

    // ─────────────────────────────────────────
    // CHECK SECTION — duplicate ID check
    // ─────────────────────────────────────────

    public static boolean isStudentIdExists(
        int studentId
    ) {

        try {

            Connection con =
                DatabaseConnection.getConnection();

            String sql =
                "SELECT id FROM students " +
                "WHERE id = ?";

            PreparedStatement stmt =
                con.prepareStatement(sql);

            stmt.setInt(1, studentId);

            ResultSet rs = stmt.executeQuery();

            return rs.next(); // true = ID exists

        } catch (Exception e) {

            System.out.println(
                "ID check error: " +
                e.getMessage()
            );

            return false;
        }
    }
    // ─────────────────────────────────────────
    // DELETE SECTION — remove student from DB
    // ─────────────────────────────────────────

    public static boolean deleteStudent(
        int studentId
    ) {

        try {

            Connection con =
                DatabaseConnection.getConnection();

            String sql =
                "DELETE FROM students " +
                "WHERE id = ?";

            PreparedStatement stmt =
                con.prepareStatement(sql);

            stmt.setInt(1, studentId);

            int rows = stmt.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                "Delete error: " +
                e.getMessage()
            );

            return false;
        }
    }

    // ─────────────────────────────────────────
    // UPDATE SECTION — update student in DB
    // ─────────────────────────────────────────

    public static boolean updateStudent(
        int studentId,
        String newName,
        int newSemester
    ) {

        try {

            Connection con =
                DatabaseConnection.getConnection();

            String sql =
                "UPDATE students " +
                "SET name = ?, semester = ? " +
                "WHERE id = ?";

            PreparedStatement stmt =
                con.prepareStatement(sql);

            stmt.setString(1, newName);
            stmt.setInt(2, newSemester);
            stmt.setInt(3, studentId);

            int rows = stmt.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                "Update error: " +
                e.getMessage()
            );

            return false;
        }
    }

    // ─────────────────────────────────────────
    // EXPORT SECTION — export all students
    // ─────────────────────────────────────────

    public static boolean exportToCSV(
        String filePath
    ) {

        try {

            Connection con =
                DatabaseConnection.getConnection();

            String sql =
                "SELECT s.id, s.name, " +
                "s.semester, " +
                "d.name AS department, " +
                "c.name AS college " +
                "FROM students s " +
                "JOIN departments d " +
                "ON s.department_id = d.id " +
                "JOIN colleges c " +
                "ON d.college_id = c.id " +
                "ORDER BY c.name, d.name, s.id";

            PreparedStatement stmt =
                con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            java.io.FileWriter fw =
                new java.io.FileWriter(filePath);

            java.io.BufferedWriter bw =
                new java.io.BufferedWriter(fw);

            // Write header row
            bw.write(
                "ID,Name,Semester," +
                "Department,College"
            );
            bw.newLine();

            // Write data rows
            while (rs.next()) {

                bw.write(
                    rs.getInt("id")            +
                    "," +
                    rs.getString("name")       +
                    "," +
                    rs.getInt("semester")      +
                    "," +
                    rs.getString("department") +
                    "," +
                    rs.getString("college")
                );
                bw.newLine();
            }

            bw.close();
            fw.close();

            return true;

        } catch (Exception e) {

            System.out.println(
                "Export error: " +
                e.getMessage()
            );

            return false;
        }
    }
    // ─────────────────────────────────────────
    // SEARCH SECTION — find student by ID
    // ─────────────────────────────────────────

    public static String searchStudentById(
        int studentId
    ) {

        try {

            Connection con =
                DatabaseConnection.getConnection();

            String sql =
                "SELECT s.id, s.name, " +
                "s.semester, " +
                "d.name AS department, " +
                "c.name AS college " +
                "FROM students s " +
                "JOIN departments d " +
                "ON s.department_id = d.id " +
                "JOIN colleges c " +
                "ON d.college_id = c.id " +
                "WHERE s.id = ?";

            PreparedStatement stmt =
                con.prepareStatement(sql);

            stmt.setInt(1, studentId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return
                    "ID       : " +
                    rs.getInt("id")         + "\n" +
                    "Name     : " +
                    rs.getString("name")    + "\n" +
                    "Semester : " +
                    rs.getInt("semester")   + "\n" +
                    "Dept     : " +
                    rs.getString("department") + "\n" +
                    "College  : " +
                    rs.getString("college");
            }

            return null; // not found

        } catch (Exception e) {

            System.out.println(
                "Search error: " +
                e.getMessage()
            );

            return null;
        }
    }


}