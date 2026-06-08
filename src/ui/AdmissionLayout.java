package ui;

import javax.swing.*;
import java.awt.*;

import model.College;

public class AdmissionLayout {

    // ─────────────────────────────────────────
    // REFERENCE TO FRAME AND LOGIC
    // ─────────────────────────────────────────

    private AdmissionFrame f;
    private AdmissionLogic logic;

    public AdmissionLayout(
        AdmissionFrame frame,
        AdmissionLogic logic
    ) {
        this.f     = frame;
        this.logic = logic;
    }

    // ─────────────────────────────────────────
    // BUILD ALL STEPS
    // ─────────────────────────────────────────

    void buildAll(JPanel content) {

        buildCollegeStep(content);
        buildDepartmentStep(content);
        buildAdmissionStep(content);
        buildDeleteStep(content);
        buildUpdateStep(content);
        buildSearchByIdStep(content);
        buildSearchStep(content);
        buildOutputStep(content);
    }

    // ─────────────────────────────────────────
    // STEP 1 — SELECT COLLEGE
    // ─────────────────────────────────────────

    private void buildCollegeStep(JPanel p) {

        f.collegePanel = AdmissionStyle
            .createStepPanel(
                "1", "Select College",
                AdmissionTheme.BLUE
            );

        f.collegeChoice = new JComboBox<>();
        f.collegeChoice.addItem(
            "-- Choose College --"
        );
        for (College c : f.colleges) {
            f.collegeChoice.addItem(c.getName());
        }
        AdmissionStyle.styleComboBox(
            f.collegeChoice
        );

        f.collegeChoice.addActionListener(e -> {
            String sel =
                (String) f.collegeChoice
                    .getSelectedItem();
            if (sel == null || sel.equals(
                "-- Choose College --")
            ) return;
            logic.loadDepartments(sel);
            f.showStep(2);
            f.setStatus(
                "College: " + sel +
                " — Now select a department"
            );
        });

        f.collegePanel.add(f.collegeChoice);
        p.add(f.collegePanel);
        p.add(Box.createVerticalStrut(8));
    }

    // ─────────────────────────────────────────
    // STEP 2 — SELECT DEPARTMENT
    // ─────────────────────────────────────────

    private void buildDepartmentStep(JPanel p) {

        f.departmentPanel = AdmissionStyle
            .createStepPanel(
                "2", "Select Department",
                AdmissionTheme.BLUE
            );

        f.departmentChoice = new JComboBox<>();
        f.departmentChoice.addItem(
            "-- Choose Branch --"
        );
        AdmissionStyle.styleComboBox(
            f.departmentChoice
        );

        f.seatLabel = new JLabel("          ");
        f.seatLabel.setFont(new Font(
            "SansSerif", Font.BOLD, 13
        ));

        f.departmentChoice.addActionListener(e -> {
            String sel =
                (String) f.departmentChoice
                    .getSelectedItem();
            if (sel == null || sel.equals(
                "-- Choose Branch --")
            ) return;
            logic.checkSeatAvailability();
        });

        f.departmentPanel.add(f.departmentChoice);
        f.departmentPanel.add(
            Box.createHorizontalStrut(15)
        );
        f.departmentPanel.add(f.seatLabel);
        p.add(f.departmentPanel);
        p.add(Box.createVerticalStrut(8));
    }

    // ─────────────────────────────────────────
    // STEP 3 — ADMISSION FORM
    // ─────────────────────────────────────────

    private void buildAdmissionStep(JPanel p) {

        f.admissionPanel = AdmissionStyle
            .createStepPanel(
                "3", "Admission Form",
                AdmissionTheme.GREEN
            );

        f.admissionPanel.add(
            AdmissionStyle.makeLabel("Student ID:")
        );
        f.idField = new JTextField(10);
        AdmissionStyle.styleField(f.idField);
        f.admissionPanel.add(f.idField);
        f.admissionPanel.add(
            Box.createHorizontalStrut(10)
        );

        f.admissionPanel.add(
            AdmissionStyle.makeLabel("Name:")
        );
        f.nameField = new JTextField(15);
        AdmissionStyle.styleField(f.nameField);
        f.admissionPanel.add(f.nameField);
        f.admissionPanel.add(
            Box.createHorizontalStrut(10)
        );

        f.admissionPanel.add(
            AdmissionStyle.makeLabel("Semester:")
        );
        f.semField = new JTextField(5);
        AdmissionStyle.styleField(f.semField);
        f.admissionPanel.add(f.semField);
        f.admissionPanel.add(
            Box.createHorizontalStrut(10)
        );

        f.submitButton =
            new JButton("Submit Admission");
        AdmissionStyle.styleButton(
            f.submitButton, AdmissionTheme.GREEN
        );
        f.submitButton.addActionListener(
            e -> logic.processAdmission()
        );

        f.admissionPanel.add(f.submitButton);
        p.add(f.admissionPanel);
        p.add(Box.createVerticalStrut(8));
    }

    // ─────────────────────────────────────────
    // STEP 4 — DELETE STUDENT
    // ─────────────────────────────────────────

    private void buildDeleteStep(JPanel p) {

        f.deletePanel = AdmissionStyle
            .createStepPanel(
                "4", "Delete Student",
                AdmissionTheme.RED
            );

        f.deletePanel.add(
            AdmissionStyle.makeLabel("Student ID:")
        );
        f.deleteIdField = new JTextField(10);
        AdmissionStyle.styleField(f.deleteIdField);
        f.deletePanel.add(f.deleteIdField);
        f.deletePanel.add(
            Box.createHorizontalStrut(10)
        );

        f.deleteButton =
            new JButton("Delete Student");
        AdmissionStyle.styleButton(
            f.deleteButton, AdmissionTheme.RED
        );
        f.deleteButton.addActionListener(
            e -> logic.deleteStudent()
        );

        f.deletePanel.add(f.deleteButton);
        p.add(f.deletePanel);
        p.add(Box.createVerticalStrut(8));
    }

    // ─────────────────────────────────────────
    // STEP 5 — UPDATE STUDENT
    // ─────────────────────────────────────────

    private void buildUpdateStep(JPanel p) {

        f.updatePanel = AdmissionStyle
            .createStepPanel(
                "5", "Update Student",
                new Color(128, 0, 128)
            );

        f.updatePanel.add(
            AdmissionStyle.makeLabel("Student ID:")
        );
        f.updateIdField = new JTextField(8);
        AdmissionStyle.styleField(f.updateIdField);
        f.updatePanel.add(f.updateIdField);
        f.updatePanel.add(
            Box.createHorizontalStrut(8)
        );

        f.fetchButton = new JButton("Fetch");
        AdmissionStyle.styleButton(
            f.fetchButton,
            new Color(100, 100, 100)
        );
        f.fetchButton.addActionListener(
            e -> logic.fetchForUpdate()
        );
        f.updatePanel.add(f.fetchButton);
        f.updatePanel.add(
            Box.createHorizontalStrut(15)
        );

        f.updatePanel.add(
            AdmissionStyle.makeLabel("New Name:")
        );
        f.updateNameField = new JTextField(13);
        AdmissionStyle.styleField(f.updateNameField);
        f.updatePanel.add(f.updateNameField);
        f.updatePanel.add(
            Box.createHorizontalStrut(8)
        );

        f.updatePanel.add(
            AdmissionStyle.makeLabel("New Semester:")
        );
        f.updateSemField = new JTextField(4);
        AdmissionStyle.styleField(f.updateSemField);
        f.updatePanel.add(f.updateSemField);
        f.updatePanel.add(
            Box.createHorizontalStrut(8)
        );

        f.updateButton =
            new JButton("Update Student");
        AdmissionStyle.styleButton(
            f.updateButton,
            new Color(128, 0, 128)
        );
        f.updateButton.addActionListener(
            e -> logic.updateStudent()
        );
        f.updatePanel.add(f.updateButton);

        p.add(f.updatePanel);
        p.add(Box.createVerticalStrut(8));
    }

    // ─────────────────────────────────────────
    // STEP 6 — SEARCH BY ID
    // ─────────────────────────────────────────

    private void buildSearchByIdStep(JPanel p) {

        f.searchByIdPanel = AdmissionStyle
            .createStepPanel(
                "6", "Search Student by ID",
                AdmissionTheme.ORANGE
            );

        f.searchByIdPanel.add(
            AdmissionStyle.makeLabel("Student ID:")
        );
        f.searchIdField = new JTextField(10);
        AdmissionStyle.styleField(f.searchIdField);
        f.searchByIdPanel.add(f.searchIdField);
        f.searchByIdPanel.add(
            Box.createHorizontalStrut(10)
        );

        f.searchIdButton =
            new JButton("Search Student");
        AdmissionStyle.styleButton(
            f.searchIdButton, AdmissionTheme.ORANGE
        );
        f.searchIdButton.addActionListener(
            e -> logic.searchStudentById()
        );

        f.searchByIdPanel.add(f.searchIdButton);
        p.add(f.searchByIdPanel);
        p.add(Box.createVerticalStrut(8));
    }

    // ─────────────────────────────────────────
    // STEP 7 — SEARCH BY DEPARTMENT
    // ─────────────────────────────────────────

    private void buildSearchStep(JPanel p) {

        f.searchPanel = AdmissionStyle
            .createStepPanel(
                "7",
                "Search Students by Department",
                AdmissionTheme.BLUE
            );

        f.searchPanel.add(
            AdmissionStyle.makeLabel("College:")
        );
        f.searchCollegeChoice = new JComboBox<>();
        for (College c : f.colleges) {
            f.searchCollegeChoice.addItem(
                c.getName()
            );
        }
        AdmissionStyle.styleComboBox(
            f.searchCollegeChoice
        );
        f.searchCollegeChoice.addActionListener(
            e -> logic.loadSearchDepartments(
                (String) f.searchCollegeChoice
                    .getSelectedItem()
            )
        );

        f.searchPanel.add(f.searchCollegeChoice);
        f.searchPanel.add(
            Box.createHorizontalStrut(10)
        );
        f.searchPanel.add(
            AdmissionStyle.makeLabel("Department:")
        );

        f.searchDeptChoice = new JComboBox<>();
        AdmissionStyle.styleComboBox(
            f.searchDeptChoice
        );
        logic.loadSearchDepartments(
            (String) f.searchCollegeChoice
                .getSelectedItem()
        );
        f.searchPanel.add(f.searchDeptChoice);
        f.searchPanel.add(
            Box.createHorizontalStrut(10)
        );

        f.showButton =
            new JButton("Show Students");
        AdmissionStyle.styleButton(
            f.showButton, AdmissionTheme.BLUE
        );
        f.showButton.addActionListener(
            e -> logic.showStudents()
        );

        f.searchPanel.add(f.showButton);
        p.add(f.searchPanel);
        p.add(Box.createVerticalStrut(8));
    }

    // ─────────────────────────────────────────
    // OUTPUT + EXPORT
    // ─────────────────────────────────────────

    private void buildOutputStep(JPanel p) {

        f.outputPanel = new JPanel(
            new BorderLayout()
        );
        f.outputPanel.setAlignmentX(
            Component.LEFT_ALIGNMENT
        );
        f.outputPanel.setBackground(
            AdmissionTheme.PANEL_BG
        );

        // Header with export button
        JPanel outHeader = new JPanel(
            new BorderLayout()
        );
        outHeader.setBackground(
            AdmissionTheme.NAVY
        );
        outHeader.setBorder(
            BorderFactory.createEmptyBorder(
                4, 10, 4, 10
            )
        );

        JLabel outLabel = new JLabel("  Output");
        outLabel.setFont(new Font(
            "SansSerif", Font.BOLD, 13
        ));
        outLabel.setForeground(Color.WHITE);

        f.exportButton =
            new JButton("⬇ Export CSV");
        AdmissionStyle.styleButton(
            f.exportButton,
            new Color(0, 130, 100)
        );
        f.exportButton.addActionListener(
            e -> logic.exportToCSV()
        );

        outHeader.add(
            outLabel, BorderLayout.WEST
        );
        outHeader.add(
            f.exportButton, BorderLayout.EAST
        );

        // Output area
        f.outputArea = new JTextPane();
        f.outputArea.setEditable(false);
        f.outputArea.setFont(new Font(
            "Monospaced", Font.PLAIN, 13
        ));
        f.outputArea.setBackground(Color.WHITE);
        f.outputArea.setBorder(
            BorderFactory.createEmptyBorder(
                10, 10, 10, 10
            )
        );

        JScrollPane scroll =
            new JScrollPane(f.outputArea);
        scroll.setBorder(
            BorderFactory.createLineBorder(
                AdmissionTheme.BORDER_COLOR
            )
        );

        f.outputPanel.add(
            outHeader, BorderLayout.NORTH
        );
        f.outputPanel.add(
            scroll, BorderLayout.CENTER
        );

        p.add(f.outputPanel);
    }
}
