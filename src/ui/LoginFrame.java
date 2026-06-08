package ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    // ImageIcon image = new ImageIcon("icon.png");
    

    // ─────────────────────────────────────────────────────────────
    // THEME
    // ─────────────────────────────────────────────────────────────

    private static final Color NAVY =
        new Color(10, 36, 99);

    private static final Color BLUE =
        new Color(0, 102, 204);

    private static final Color RED =
        new Color(204, 0, 0);

    private static final Color LIGHT_BLUE =
        new Color(235, 243, 255);

    // ─────────────────────────────────────────────────────────────
    // CREDENTIALS — change here if needed
    // ─────────────────────────────────────────────────────────────

    private static final String ADMIN_USER =
        "prabal";

    private static final String ADMIN_PASS =
        "prabal@123";

    // ─────────────────────────────────────────────────────────────
    // FIELDS
    // ─────────────────────────────────────────────────────────────

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel errorLabel;
    private int attempts = 0;
    private static final int MAX_ATTEMPTS = 3;

    // ─────────────────────────────────────────────────────────────
    // CONSTRUCTOR
    // ─────────────────────────────────────────────────────────────

    public LoginFrame() {

        setTitle("Login — College Admission");
        setSize(420, 480);
        setResizable(false);
        setDefaultCloseOperation(
            JFrame.EXIT_ON_CLOSE
        );
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);
        add(buildForm(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);


    // ─────────────────────────────────
    // SET ICON — add these lines
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
    // ─────────────────────────────────

    setLayout(new BorderLayout());
    add(buildHeader(), BorderLayout.NORTH);
    add(buildForm(),   BorderLayout.CENTER);
    add(buildFooter(), BorderLayout.SOUTH);

    setVisible(true);

        
    }

    // ─────────────────────────────────────────────────────────────
    // LAYOUT SECTION
    // ─────────────────────────────────────────────────────────────

    private JPanel buildHeader() {

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(
            header, BoxLayout.Y_AXIS
        ));
        header.setBackground(NAVY);
        header.setBorder(
            BorderFactory.createEmptyBorder(
                30, 20, 25, 20
            )
        );

        // Lock icon label
        JLabel icon = new JLabel(
            "🔒", SwingConstants.CENTER
        );
        icon.setFont(new Font(
            "SansSerif", Font.PLAIN, 36
        ));
        icon.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        JLabel title = new JLabel(
            "Admin Login"
        );
        title.setFont(new Font(
            "SansSerif", Font.BOLD, 22
        ));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        JLabel subtitle = new JLabel(
            "College Admission System"
        );
        subtitle.setFont(new Font(
            "SansSerif", Font.PLAIN, 13
        ));
        subtitle.setForeground(
            new Color(180, 200, 255)
        );
        subtitle.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        header.add(icon);
        header.add(
            Box.createVerticalStrut(10)
        );
        header.add(title);
        header.add(
            Box.createVerticalStrut(5)
        );
        header.add(subtitle);

        return header;
    }

    private JPanel buildForm() {

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(
            form, BoxLayout.Y_AXIS
        ));
        form.setBackground(LIGHT_BLUE);
        form.setBorder(
            BorderFactory.createEmptyBorder(
                30, 40, 20, 40
            )
        );

        // ─────────────────────────────────
        // USERNAME
        // ─────────────────────────────────

        JLabel userLabel =
            new JLabel("Username");
        userLabel.setFont(new Font(
            "SansSerif", Font.BOLD, 13
        ));
        userLabel.setForeground(NAVY);
        userLabel.setAlignmentX(
            Component.LEFT_ALIGNMENT
        );

        usernameField = new JTextField();
        usernameField.setFont(new Font(
            "SansSerif", Font.PLAIN, 14
        ));
        usernameField.setMaximumSize(
            new Dimension(
                Integer.MAX_VALUE, 38
            )
        );
        usernameField.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(180, 200, 230)
                ),
                BorderFactory.createEmptyBorder(
                    5, 10, 5, 10
                )
            )
        );

        // ─────────────────────────────────
        // PASSWORD
        // ─────────────────────────────────

        JLabel passLabel =
            new JLabel("Password");
        passLabel.setFont(new Font(
            "SansSerif", Font.BOLD, 13
        ));
        passLabel.setForeground(NAVY);
        passLabel.setAlignmentX(
            Component.LEFT_ALIGNMENT
        );

        passwordField = new JPasswordField();
        passwordField.setFont(new Font(
            "SansSerif", Font.PLAIN, 14
        ));
        passwordField.setMaximumSize(
            new Dimension(
                Integer.MAX_VALUE, 38
            )
        );
        passwordField.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(180, 200, 230)
                ),
                BorderFactory.createEmptyBorder(
                    5, 10, 5, 10
                )
            )
        );

        // Press Enter to login
        passwordField.addActionListener(
            e -> attemptLogin()
        );

        // ─────────────────────────────────
        // ERROR LABEL
        // ─────────────────────────────────

        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font(
            "SansSerif", Font.BOLD, 12
        ));
        errorLabel.setForeground(RED);
        errorLabel.setAlignmentX(
            Component.LEFT_ALIGNMENT
        );

        // ─────────────────────────────────
        // LOGIN BUTTON
        // ─────────────────────────────────

        loginButton = new JButton("Login");
        loginButton.setFont(new Font(
            "SansSerif", Font.BOLD, 14
        ));
        loginButton.setBackground(BLUE);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setOpaque(true);
        loginButton.setMaximumSize(
            new Dimension(
                Integer.MAX_VALUE, 42
            )
        );
        loginButton.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );
        loginButton.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        loginButton.addActionListener(
            e -> attemptLogin()
        );

        // Hover effect
        loginButton.addMouseListener(
            new MouseAdapter() {
                public void mouseEntered(
                    MouseEvent e
                ) {
                    loginButton.setBackground(
                        BLUE.darker()
                    );
                }
                public void mouseExited(
                    MouseEvent e
                ) {
                    loginButton.setBackground(
                        BLUE
                    );
                }
            }
        );

        // Add all to form
        form.add(userLabel);
        form.add(Box.createVerticalStrut(6));
        form.add(usernameField);
        form.add(Box.createVerticalStrut(18));
        form.add(passLabel);
        form.add(Box.createVerticalStrut(6));
        form.add(passwordField);
        form.add(Box.createVerticalStrut(10));
        form.add(errorLabel);
        form.add(Box.createVerticalStrut(15));
        form.add(loginButton);

        return form;
    }

    private JPanel buildFooter() {

        JPanel footer = new JPanel(
            new FlowLayout(FlowLayout.CENTER)
        );
        footer.setBackground(NAVY);

        JLabel hint = new JLabel(
            "Protected — Authorized Access Only"
        );
        hint.setFont(new Font(
            "SansSerif", Font.PLAIN, 11
        ));
        hint.setForeground(
            new Color(140, 160, 210)
        );

        footer.add(hint);
        return footer;
    }

    // ─────────────────────────────────────────────────────────────
    // LOGIC SECTION
    // ─────────────────────────────────────────────────────────────

    private void attemptLogin() {

        String user =
            usernameField.getText().trim();

        String pass = new String(
            passwordField.getPassword()
        ).trim();

        // ─────────────────────────────────
        // EMPTY CHECK
        // ─────────────────────────────────

        if (user.isEmpty() || pass.isEmpty()) {
            showLoginError(
                "✘ Username and password " +
                "cannot be empty."
            );
            return;
        }

        // ─────────────────────────────────
        // CREDENTIAL CHECK
        // ─────────────────────────────────

        if (user.equals(ADMIN_USER) &&
            pass.equals(ADMIN_PASS)) {

            // Success — open main app
            dispose();
            new AdmissionFrame();

        } else {

            attempts++;
            int remaining =
                MAX_ATTEMPTS - attempts;

            if (remaining <= 0) {

                JOptionPane.showMessageDialog(
                    this,
                    "Too many failed attempts.\n" +
                    "Application will close.",
                    "Access Denied",
                    JOptionPane.ERROR_MESSAGE
                );

                System.exit(0);

            } else {

                showLoginError(
                    "✘ Wrong credentials. " +
                    remaining +
                    " attempt(s) remaining."
                );

                shakeWindow();
                passwordField.setText("");
                passwordField.requestFocus();
            }
        }
    }

    // ─────────────────────────────────────────────────────────────
    // HELPER SECTION
    // ─────────────────────────────────────────────────────────────

    private void showLoginError(String msg) {

        errorLabel.setText(msg);

        usernameField.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    RED, 2
                ),
                BorderFactory.createEmptyBorder(
                    5, 10, 5, 10
                )
            )
        );

        passwordField.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    RED, 2
                ),
                BorderFactory.createEmptyBorder(
                    5, 10, 5, 10
                )
            )
        );
    }

    private void shakeWindow() {

        Point origin = getLocation();
        int x = origin.x;
        int y = origin.y;

        Timer timer = new Timer(30, null);
        int[] count = {0};

        timer.addActionListener(e -> {

            count[0]++;

            if (count[0] % 2 == 0) {
                setLocation(x - 10, y);
            } else {
                setLocation(x + 10, y);
            }

            if (count[0] >= 8) {
                setLocation(x, y);
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
    }
}