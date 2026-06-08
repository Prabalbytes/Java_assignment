package ui;

import javax.swing.*;
import java.awt.*;

import data.CollegeData;
import model.College;
import model.Department;
import model.Student;

public class AdmissionLogic {

    private AdmissionFrame f;

    public AdmissionLogic(AdmissionFrame frame) {
        this.f = frame;
    }

    // ─────────────────────────────────────────
    // LOAD DEPARTMENTS
    // ─────────────────────────────────────────

    void loadDepartments(String collegeName) {

        f.departmentChoice.removeAllItems();
        f.departmentChoice.addItem(
            "-- Choose Branch --"
        );

        College college =
            f.service.getCollege(collegeName);

        if (college == null) return;

        for (Department d :
                college.getDepartments()) {
            f.departmentChoice.addItem(
                d.getName()
            );
        }
    }

    // ─────────────────────────────────────────
    // CHECK SEAT AVAILABILITY
    // ─────────────────────────────────────────

    void checkSeatAvailability() {

        String collegeName =
            (String) f.collegeChoice
                .getSelectedItem();

        String deptName =
            (String) f.departmentChoice
                .getSelectedItem();

        College college =
            f.service.getCollege(collegeName);

        if (college == null) return;

        Department dept =
            college.getDepartment(deptName);

        if (dept == null) return;

        int seats = dept.getAvailableSeats();

        f.seatLabel.setText(
            "Available Seats: " + seats
        );
        f.seatLabel.setForeground(
            seats > 0
                ? AdmissionTheme.GREEN
                : AdmissionTheme.RED
        );

        if (seats > 0) {
            f.showStep(3);
            f.clearFields();
            f.showSearchAndOutput();
            f.setStatus(
                "Department: " + deptName +
                " — " + seats + " seats available"
            );
        } else {
            f.admissionPanel.setVisible(false);
            f.showSearchAndOutput();
            f.setOutput(
                "✘ No seats available in " +
                deptName + " at " +
                collegeName + ".",
                AdmissionTheme.RED
            );
            f.setStatus(
                "No seats available in " + deptName
            );
        }

        f.revalidate();
        f.repaint();
    }

    // ─────────────────────────────────────────
    // PROCESS ADMISSION
    // ─────────────────────────────────────────

    void processAdmission() {

        try {

            String collegeName =
                (String) f.collegeChoice
                    .getSelectedItem();

            String deptName =
                (String) f.departmentChoice
                    .getSelectedItem();

            // Validate ID
            String idText =
                f.idField.getText().trim();

            if (idText.isEmpty()) {
                f.showError(
                    "✘ ID cannot be empty.",
                    f.idField
                );
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idText);
            } catch (NumberFormatException e) {
                f.showError(
                    "✘ ID must be a number.",
                    f.idField
                );
                return;
            }

            if (id <= 0) {
                f.showError(
                    "✘ ID must be greater than 0.",
                    f.idField
                );
                return;
            }

            // Validate Name
            String name =
                f.nameField.getText().trim();

            if (name.isEmpty()) {
                f.showError(
                    "✘ Name cannot be empty.",
                    f.nameField
                );
                return;
            }

            if (!name.matches("[a-zA-Z ]+")) {
                f.showError(
                    "✘ Name must contain " +
                    "letters only.",
                    f.nameField
                );
                return;
            }

            if (name.length() < 2) {
                f.showError(
                    "✘ Name is too short.",
                    f.nameField
                );
                return;
            }

            // Validate Semester
            String semText =
                f.semField.getText().trim();

            if (semText.isEmpty()) {
                f.showError(
                    "✘ Semester cannot be empty.",
                    f.semField
                );
                return;
            }

            int sem;
            try {
                sem = Integer.parseInt(semText);
            } catch (NumberFormatException e) {
                f.showError(
                    "✘ Semester must be a number.",
                    f.semField
                );
                return;
            }

            if (sem < 1 || sem > 8) {
                f.showError(
                    "✘ Semester must be 1 to 8.",
                    f.semField
                );
                return;
            }

            // Duplicate check
            if (CollegeData.isStudentIdExists(id)) {
                f.showError(
                    "✘ Student ID " + id +
                    " already exists.",
                    f.idField
                );
                return;
            }

            // All valid — admit
            Student student =
                new Student(id, name, sem);

            boolean success =
                f.service.processAdmission(
                    collegeName, deptName, student
                );

            if (success) {
                f.setOutput(
                    "✔ Admission Successful!\n\n" +
                    "ID       : " + id       + "\n" +
                    "Name     : " + name     + "\n" +
                    "Semester : " + sem      + "\n" +
                    "Dept     : " + deptName + "\n" +
                    "College  : " + collegeName,
                    AdmissionTheme.GREEN
                );
                f.clearFields();
                checkSeatAvailability();
                f.setStatus(
                    "✔ Admitted: " + name +
                    " → " + deptName +
                    ", " + collegeName
                );
            } else {
                f.setOutput(
                    "✘ Admission failed. " +
                    "Seats may be full.",
                    AdmissionTheme.RED
                );
            }

        } catch (Exception ex) {
            f.setOutput(
                "✘ Unexpected error: " +
                ex.getMessage(),
                AdmissionTheme.RED
            );
        }
    }

    // ─────────────────────────────────────────
    // DELETE STUDENT
    // ─────────────────────────────────────────

    void deleteStudent() {

        String idText =
            f.deleteIdField.getText().trim();

        if (idText.isEmpty()) {
            f.showError(
                "✘ Please enter a Student ID.",
                f.deleteIdField
            );
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            f.showError(
                "✘ Student ID must be a number.",
                f.deleteIdField
            );
            return;
        }

        if (id <= 0) {
            f.showError(
                "✘ ID must be greater than 0.",
                f.deleteIdField
            );
            return;
        }

        if (!CollegeData.isStudentIdExists(id)) {
            f.showError(
                "✘ Student ID " + id +
                " not found.",
                f.deleteIdField
            );
            return;
        }

        int confirm =
            JOptionPane.showConfirmDialog(
                f,
                "Delete Student ID " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String collegeName =
            (String) f.collegeChoice
                .getSelectedItem();

        String deptName =
            (String) f.departmentChoice
                .getSelectedItem();

        boolean success =
            f.service.deleteStudent(
                collegeName, deptName, id
            );

        if (success) {
            f.setOutput(
                "✔ Student ID " + id +
                " deleted.\nSeat restored.",
                AdmissionTheme.GREEN
            );
            f.deleteIdField.setText("");
            checkSeatAvailability();
            f.setStatus(
                "✔ Student ID " + id +
                " deleted — seat restored"
            );
        } else {
            f.setOutput(
                "✘ Delete failed.",
                AdmissionTheme.RED
            );
        }
    }

    // ─────────────────────────────────────────
    // FETCH FOR UPDATE
    // ─────────────────────────────────────────

    void fetchForUpdate() {

        String idText =
            f.updateIdField.getText().trim();

        if (idText.isEmpty()) {
            f.showError(
                "✘ Please enter a Student ID.",
                f.updateIdField
            );
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            f.showError(
                "✘ Student ID must be a number.",
                f.updateIdField
            );
            return;
        }

        if (id <= 0) {
            f.showError(
                "✘ ID must be greater than 0.",
                f.updateIdField
            );
            return;
        }

        String result =
            f.service.searchStudent(id);

        if (result == null) {
            f.showError(
                "✘ Student ID " + id +
                " not found.",
                f.updateIdField
            );
            return;
        }

        String[] lines = result.split("\n");
        String name = "";
        String sem  = "";

        for (String line : lines) {
            if (line.startsWith("Name")) {
                name = line.split(":")[1].trim();
            }
            if (line.startsWith("Semester")) {
                sem = line.split(":")[1].trim();
            }
        }

        f.updateNameField.setText(name);
        f.updateSemField.setText(sem);
        f.updateNameField.requestFocus();

        f.setOutput(
            "=== Student Fetched ===\n\n" +
            result + "\n\n" +
            "Edit Name or Semester above\n" +
            "then click Update Student.",
            AdmissionTheme.TEXT_DARK
        );

        f.setStatus(
            "Student ID " + id +
            " loaded — edit and update"
        );
    }

    // ─────────────────────────────────────────
    // UPDATE STUDENT
    // ─────────────────────────────────────────

    void updateStudent() {

        String idText =
            f.updateIdField.getText().trim();

        if (idText.isEmpty()) {
            f.showError(
                "✘ Please enter a Student ID.",
                f.updateIdField
            );
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            f.showError(
                "✘ Student ID must be a number.",
                f.updateIdField
            );
            return;
        }

        if (id <= 0) {
            f.showError(
                "✘ ID must be greater than 0.",
                f.updateIdField
            );
            return;
        }

        String name =
            f.updateNameField.getText().trim();

        if (name.isEmpty()) {
            f.showError(
                "✘ Name cannot be empty.",
                f.updateNameField
            );
            return;
        }

        if (!name.matches("[a-zA-Z ]+")) {
            f.showError(
                "✘ Name must contain letters only.",
                f.updateNameField
            );
            return;
        }

        if (name.length() < 2) {
            f.showError(
                "✘ Name is too short.",
                f.updateNameField
            );
            return;
        }

        String semText =
            f.updateSemField.getText().trim();

        if (semText.isEmpty()) {
            f.showError(
                "✘ Semester cannot be empty.",
                f.updateSemField
            );
            return;
        }

        int sem;
        try {
            sem = Integer.parseInt(semText);
        } catch (NumberFormatException e) {
            f.showError(
                "✘ Semester must be a number.",
                f.updateSemField
            );
            return;
        }

        if (sem < 1 || sem > 8) {
            f.showError(
                "✘ Semester must be 1 to 8.",
                f.updateSemField
            );
            return;
        }

        int confirm =
            JOptionPane.showConfirmDialog(
                f,
                "Update Student ID " + id +
                " with new details?",
                "Confirm Update",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success =
            f.service.updateStudent(
                id, name, sem
            );

        if (success) {
            f.setOutput(
                "✔ Student Updated!\n\n" +
                "ID       : " + id   + "\n" +
                "Name     : " + name + "\n" +
                "Semester : " + sem,
                AdmissionTheme.GREEN
            );
            f.updateIdField.setText("");
            f.updateNameField.setText("");
            f.updateSemField.setText("");
            f.setStatus(
                "✔ Student ID " + id + " updated"
            );
        } else {
            f.setOutput(
                "✘ Update failed.",
                AdmissionTheme.RED
            );
        }
    }

    // ─────────────────────────────────────────
    // SEARCH BY ID
    // ─────────────────────────────────────────

    void searchStudentById() {

        String idText =
            f.searchIdField.getText().trim();

        if (idText.isEmpty()) {
            f.showError(
                "✘ Please enter a Student ID.",
                f.searchIdField
            );
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            f.showError(
                "✘ Student ID must be a number.",
                f.searchIdField
            );
            return;
        }

        if (id <= 0) {
            f.showError(
                "✘ ID must be greater than 0.",
                f.searchIdField
            );
            return;
        }

        String result =
            f.service.searchStudent(id);

        if (result == null) {
            f.setOutput(
                "✘ Student ID " + id +
                " not found.",
                AdmissionTheme.RED
            );
            f.setStatus(
                "Search: ID " + id + " not found"
            );
        } else {
            f.setOutput(
                "=== Student Found ===\n\n" + result,
                AdmissionTheme.TEXT_DARK
            );
            f.setStatus(
                "✔ Student ID " + id + " found"
            );
        }

        f.searchIdField.setText("");
    }

    // ─────────────────────────────────────────
    // SHOW STUDENTS BY DEPT
    // ─────────────────────────────────────────

    void showStudents() {

        String collegeName =
            (String) f.searchCollegeChoice
                .getSelectedItem();

        String deptName =
            (String) f.searchDeptChoice
                .getSelectedItem();

        String result =
            f.service.showStudents(
                collegeName, deptName
            );

        f.setOutput(
            "=== Students in " + deptName +
            " — " + collegeName + " ===\n\n" +
            result,
            AdmissionTheme.TEXT_DARK
        );

        f.setStatus(
            "Showing: " + deptName +
            ", " + collegeName
        );
    }


    // ─────────────────────────────────────────
    // EXPORT TO CSV — runs on background thread
    // ─────────────────────────────────────────

    void exportToCSV() {

        // File chooser runs on EDT (UI thread)
        JFileChooser chooser =
            new JFileChooser();

        chooser.setDialogTitle(
            "Save Student Data as CSV"
        );

        chooser.setSelectedFile(
            new java.io.File(
                "students_export.csv"
            )
        );

        chooser.setFileFilter(
            new javax.swing.filechooser
                .FileNameExtensionFilter(
                    "CSV Files (*.csv)", "csv"
                )
        );

        int userChoice =
            chooser.showSaveDialog(f);

        if (userChoice !=
            JFileChooser.APPROVE_OPTION) {
            return;
        }

        String filePath =
            chooser.getSelectedFile()
                .getAbsolutePath();

        final String finalPath =
            filePath.endsWith(".csv")
                ? filePath
                : filePath + ".csv";

        // ─────────────────────────────────
        // Show loading message on UI
        // ─────────────────────────────────

        f.setOutput(
            "⏳ Exporting data...\n" +
            "Please wait.",
            AdmissionTheme.TEXT_DARK
        );

        f.exportButton.setEnabled(false);
        f.setStatus("Exporting...");

        // ─────────────────────────────────
        // Background thread — does the
        // actual file writing
        // ─────────────────────────────────

        Thread exportThread = new Thread(() -> {

            // Heavy work happens here
            // UI stays responsive
            boolean success =
                f.service.exportToCSV(finalPath);

            // ─────────────────────────────
            // UI update must go back to EDT
            // ─────────────────────────────

            SwingUtilities.invokeLater(() -> {

                f.exportButton.setEnabled(true);

                if (success) {
                    f.setOutput(
                        "✔ Export Successful!\n\n" +
                        "File saved to:\n" +
                        finalPath +
                        "\n\nOpen in Excel or " +
                        "any spreadsheet app.",
                        AdmissionTheme.GREEN
                    );
                    f.setStatus(
                        "✔ Exported to: " + finalPath
                    );
                } else {
                    f.setOutput(
                        "✘ Export failed. " +
                        "Please try again.",
                        AdmissionTheme.RED
                    );
                    f.setStatus("✘ Export failed");
                }
            });
        });

        // Name the thread for debugging
        exportThread.setName("ExportThread");

        // Start background thread
        exportThread.start();
    }

    // ─────────────────────────────────────────
    // SYNC SEARCH DROPDOWNS
    // ─────────────────────────────────────────

    void syncSearchDropdowns() {

        String col =
            (String) f.collegeChoice
                .getSelectedItem();

        for (int i = 0;
             i < f.searchCollegeChoice
                     .getItemCount(); i++) {

            if (f.searchCollegeChoice
                    .getItemAt(i).equals(col)) {
                f.searchCollegeChoice
                    .setSelectedIndex(i);
                break;
            }
        }

        loadSearchDepartments(col);

        String dept =
            (String) f.departmentChoice
                .getSelectedItem();

        for (int i = 0;
             i < f.searchDeptChoice
                     .getItemCount(); i++) {

            if (f.searchDeptChoice
                    .getItemAt(i).equals(dept)) {
                f.searchDeptChoice
                    .setSelectedIndex(i);
                break;
            }
        }
    }

    // ─────────────────────────────────────────
    // LOAD SEARCH DEPARTMENTS
    // ─────────────────────────────────────────

    void loadSearchDepartments(
        String collegeName
    ) {

        f.searchDeptChoice.removeAllItems();

        College college =
            f.service.getCollege(collegeName);

        if (college == null) return;

        for (Department d :
                college.getDepartments()) {
            f.searchDeptChoice.addItem(
                d.getName()
            );
        }
    }

    // ─────────────────────────────────────────
    // GET TOTAL DEPTS
    // ─────────────────────────────────────────

    int getTotalDepts() {

        int count = 0;
        for (College c : f.colleges) {
            count += c.getDepartments().size();
        }
        return count;
    }
}