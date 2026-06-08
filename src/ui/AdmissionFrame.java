package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import data.CollegeData;
import model.College;
import service.AdmissionService;

public class AdmissionFrame extends JFrame {

    // ─────────────────────────────────────────────────────────────
    // FIELDS
    // ─────────────────────────────────────────────────────────────

    // Step 1
    JPanel collegePanel;
    JComboBox<String> collegeChoice;

    // Step 2
    JPanel departmentPanel;
    JComboBox<String> departmentChoice;
    JLabel seatLabel;

    // Step 3 - Admission
    JPanel admissionPanel;
    JTextField idField;
    JTextField nameField;
    JTextField semField;
    JButton submitButton;

    // Step 4 - Delete
    JPanel deletePanel;
    JTextField deleteIdField;
    JButton deleteButton;

    // Step 5 - Update
    JPanel updatePanel;
    JTextField updateIdField;
    JTextField updateNameField;
    JTextField updateSemField;
    JButton fetchButton;
    JButton updateButton;

    // Step 6 - Search by ID
    JPanel searchByIdPanel;
    JTextField searchIdField;
    JButton searchIdButton;

    // Step 7 - Search by Dept
    JPanel searchPanel;
    JComboBox<String> searchCollegeChoice;
    JComboBox<String> searchDeptChoice;
    JButton showButton;

    // Output
    JPanel outputPanel;
    JTextPane outputArea;
    JButton exportButton;

    // Status Bar
    JLabel statusBar;

    // Backend
    AdmissionService service;
    ArrayList<College> colleges;

    // Handlers
    private AdmissionLogic logic;
    private AdmissionLayout layout;

    // ─────────────────────────────────────────────────────────────
    // CONSTRUCTOR
    // ─────────────────────────────────────────────────────────────

    public AdmissionFrame() {

        colleges = CollegeData.loadColleges();
        service  = new AdmissionService(colleges);
        logic    = new AdmissionLogic(this);
        layout   = new AdmissionLayout(this, logic);

        setTitle("College Admission System");
        setSize(1500, 750);
        setMinimumSize(new Dimension(900, 650));
        setDefaultCloseOperation(
            JFrame.EXIT_ON_CLOSE
        );
        setLocationRelativeTo(null);
        
         // ─────────────────────────────────
        // SET ICON
        // ─────────────────────────────────
    try {
        ImageIcon icon = new ImageIcon(
            getClass().getResource("icon.png")
        );
        setIconImage(icon.getImage());
    } catch (Exception e) {
        System.out.println(
            "Icon not found: " + e.getMessage()
        );
    }
    



        setLayout(new BorderLayout());

        add(buildHeader(),      BorderLayout.NORTH);
        add(buildMainContent(), BorderLayout.CENTER);
        add(buildStatusBar(),   BorderLayout.SOUTH);

        showStep(1);
        setVisible(true);
    }

    // ─────────────────────────────────────────────────────────────
    // HEADER
    // ─────────────────────────────────────────────────────────────

    private JPanel buildHeader() {

        JPanel header = new JPanel(
            new BorderLayout()
        );
        header.setBackground(AdmissionTheme.NAVY);
        header.setBorder(
            BorderFactory.createEmptyBorder(
                18, 25, 18, 25
            )
        );

        JLabel title = new JLabel(
            "College Admission System"
        );
        title.setFont(new Font(
            "SansSerif", Font.BOLD, 22
        ));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
            "Manage student admissions efficiently"
        );
        subtitle.setFont(new Font(
            "SansSerif", Font.PLAIN, 13
        ));
        subtitle.setForeground(
            new Color(180, 200, 255)
        );

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(
            titlePanel, BoxLayout.Y_AXIS
        ));
        titlePanel.setBackground(
            AdmissionTheme.NAVY
        );
        titlePanel.add(title);
        titlePanel.add(
            Box.createVerticalStrut(4)
        );
        titlePanel.add(subtitle);

        JLabel badge = new JLabel(
            colleges.size() + " Colleges  |  " +
            logic.getTotalDepts() + " Departments"
        );
        badge.setFont(new Font(
            "SansSerif", Font.PLAIN, 12
        ));
        badge.setForeground(
            new Color(180, 200, 255)
        );
        badge.setHorizontalAlignment(
            SwingConstants.RIGHT
        );

        header.add(titlePanel, BorderLayout.WEST);
        header.add(badge, BorderLayout.EAST);

        return header;
    }

    // ─────────────────────────────────────────────────────────────
    // MAIN CONTENT
    // ─────────────────────────────────────────────────────────────

    private JScrollPane buildMainContent() {

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(
            content, BoxLayout.Y_AXIS
        ));
        content.setBackground(
            AdmissionTheme.PANEL_BG
        );
        content.setBorder(
            BorderFactory.createEmptyBorder(
                15, 20, 15, 20
            )
        );

        // All steps built by layout class
        layout.buildAll(content);

        JScrollPane scroll =
            new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.getVerticalScrollBar()
              .setUnitIncrement(16);

        return scroll;
    }

    // ─────────────────────────────────────────────────────────────
    // STATUS BAR
    // ─────────────────────────────────────────────────────────────

    private JPanel buildStatusBar() {

        JPanel bar = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 15, 5)
        );
        bar.setBackground(AdmissionTheme.NAVY);
        bar.setBorder(
            BorderFactory.createMatteBorder(
                1, 0, 0, 0,
                new Color(60, 80, 140)
            )
        );

        statusBar = new JLabel(
            "Ready — Select a college to begin"
        );
        statusBar.setFont(new Font(
            "SansSerif", Font.PLAIN, 12
        ));
        statusBar.setForeground(
            new Color(180, 200, 255)
        );

        bar.add(statusBar);
        return bar;
    }

    // ─────────────────────────────────────────────────────────────
    // VISIBILITY SECTION
    // ─────────────────────────────────────────────────────────────

    void showStep(int upTo) {

        collegePanel.setVisible(upTo >= 1);
        departmentPanel.setVisible(upTo >= 2);
        admissionPanel.setVisible(upTo >= 3);
        deletePanel.setVisible(upTo >= 3);
        updatePanel.setVisible(upTo >= 3);
        searchByIdPanel.setVisible(upTo >= 3);
        searchPanel.setVisible(upTo >= 3);
        outputPanel.setVisible(upTo >= 3);

        revalidate();
        repaint();
    }

    void showSearchAndOutput() {

        searchPanel.setVisible(true);
        outputPanel.setVisible(true);
        logic.syncSearchDropdowns();

        revalidate();
        repaint();
    }

    // ─────────────────────────────────────────────────────────────
    // HELPER SECTION
    // ─────────────────────────────────────────────────────────────

    void clearFields() {

        idField.setText("");
        nameField.setText("");
        semField.setText("");

        javax.swing.border.Border normal =
            UIManager.getLookAndFeel()
                .getDefaults()
                .getBorder("TextField.border");

        idField.setBorder(normal);
        nameField.setBorder(normal);
        semField.setBorder(normal);
    }

    void showError(
        String message,
        JTextField field
    ) {

        setOutput(message, AdmissionTheme.RED);
        field.requestFocus();
        field.selectAll();
        field.setBorder(
            BorderFactory.createLineBorder(
                AdmissionTheme.RED, 2
            )
        );
        setStatus(message);
    }

    void setStatus(String message) {
        statusBar.setText("  " + message);
    }

    // ─────────────────────────────────────────────────────────────
    // COLORED OUTPUT
    // ─────────────────────────────────────────────────────────────

    void setOutput(String message, Color color) {

        outputArea.setText("");

        javax.swing.text.StyledDocument doc =
            outputArea.getStyledDocument();

        javax.swing.text.SimpleAttributeSet style =
            new javax.swing.text.SimpleAttributeSet();

        javax.swing.text.StyleConstants
            .setForeground(style, color);

        javax.swing.text.StyleConstants
            .setFontFamily(style, "Monospaced");

        javax.swing.text.StyleConstants
            .setFontSize(style, 13);

        try {
            doc.insertString(
                0, message, style
            );
        } catch (Exception e) {
            outputArea.setText(message);
        }
    }
}