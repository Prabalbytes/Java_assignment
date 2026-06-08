package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdmissionStyle {

    // ─────────────────────────────────────────
    // STEP PANEL
    // ─────────────────────────────────────────

    public static JPanel createStepPanel(
        String number,
        String title,
        Color accentColor
    ) {

        JPanel panel = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 8, 8)
        );
        panel.setAlignmentX(
            Component.LEFT_ALIGNMENT
        );
        panel.setBackground(Color.WHITE);
        
        panel.setMaximumSize(new Dimension(
            Integer.MAX_VALUE, 60
        ));
        panel.setPreferredSize(new Dimension(
            950, 60
        ));
        panel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(
                    0, 5, 0, 0, accentColor
                ),
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                        AdmissionTheme.BORDER_COLOR
                    ),
                    BorderFactory.createEmptyBorder(
                        5, 10, 5, 10
                    )
                )
            )
        );

        // Badge
        JLabel badge = new JLabel(number);
        badge.setFont(new Font(
            "SansSerif", Font.BOLD, 12
        ));
        badge.setForeground(Color.WHITE);
        badge.setBackground(accentColor);
        badge.setOpaque(true);
        badge.setBorder(
            BorderFactory.createEmptyBorder(
                3, 8, 3, 8
            )
        );

        // Title
        JLabel label = new JLabel(title);
        label.setFont(new Font(
            "SansSerif", Font.BOLD, 13
        ));
        label.setForeground(AdmissionTheme.TEXT_DARK);

        panel.add(badge);
        panel.add(label);
        panel.add(Box.createHorizontalStrut(10));

        return panel;
    }

    // ─────────────────────────────────────────
    // LABEL
    // ─────────────────────────────────────────

    public static JLabel makeLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font(
            "SansSerif", Font.PLAIN, 13
        ));
        label.setForeground(AdmissionTheme.TEXT_DARK);
        return label;
    }

    // ─────────────────────────────────────────
    // COMBO BOX
    // ─────────────────────────────────────────

    public static void styleComboBox(
        JComboBox<String> box
    ) {

        box.setFont(new Font(
            "SansSerif", Font.PLAIN, 13
        ));
        box.setPreferredSize(
            new Dimension(180, 30)
        );
        box.setBackground(Color.WHITE);
    }

    // ─────────────────────────────────────────
    // TEXT FIELD
    // ─────────────────────────────────────────

    public static void styleField(
        JTextField field
    ) {

        field.setFont(new Font(
            "SansSerif", Font.PLAIN, 13
        ));
        field.setPreferredSize(new Dimension(
            field.getPreferredSize().width, 30
        ));
    }

    // ─────────────────────────────────────────
    // BUTTON
    // ─────────────────────────────────────────

    public static void styleButton(
        JButton button,
        Color color
    ) {

        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font(
            "SansSerif", Font.BOLD, 13
        ));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(
            BorderFactory.createEmptyBorder(
                7, 16, 7, 16
            )
        );
        button.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );

        // Hover effect
        button.addMouseListener(
            new MouseAdapter() {

                @Override
                public void mouseEntered(
                    MouseEvent e
                ) {
                    button.setBackground(
                        color.darker()
                    );
                }

                @Override
                public void mouseExited(
                    MouseEvent e
                ) {
                    button.setBackground(color);
                }
            }
        );
    }
}