package calculator;

import static calculator.Settings.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class View extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -3233952892479045709L;
    private static JTextField display;
    private static DecimalFormat formatter = new DecimalFormat("0.########");

    public View() {
        // Create application
        super("Light calculator");
        Logic controller = new Logic();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds((int) (screen_width - APP_WIDTH) / 2, (int) (screen_height - APP_HEIGHT) / 2, APP_WIDTH, APP_HEIGHT);
        setResizable(false);
        addKeyListener(controller);
        // Create content pane.
        JPanel contentPane = new JPanel(new BorderLayout(GAP, GAP));
        contentPane.setBorder(new EmptyBorder(GAP, GAP, GAP, GAP));

        // Create display.
        display = new JTextField();
        display.setText("0");
        display.setEditable(false);
        display.setFocusable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));
        display.setFont(new Font("Sans", 0, (int) DISPLAY_HEIGHT / 2));
        contentPane.add(display, BorderLayout.NORTH);
        setContentPane(contentPane);
        // Create buttons.
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(5, 4, GAP, GAP));
        for (int i = 0; i < BUTTONS.length; i++) {
            JButton btn = new JButton(BUTTONS[i]);
            btn.setMargin(new Insets(0, 0, 0, 0));
            btn.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            btn.setFont(new Font("Sans", 0, (int) BUTTON_HEIGHT / 3));
            btn.setActionCommand(btn.getText());
            btn.addActionListener(controller);
            btn.setFocusable(false);
            btnPanel.add(btn);
        }
        contentPane.add(btnPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    /**
     * Displays the numbers and adjusts display's font size.
     *
     * @param object
     *            to be displayed,String or double.
     */
    public static void setDisplayTo(Object object) {
        if (object.getClass().equals(String.class))
            display.setText((String) object);
        else
            display.setText(formatter.format(object));
        setFontSize();
    }

    /**
     * Adjusts display's font size.
     *
     */
    private static void setFontSize() {
        int len = display.getText().length();
        if (len < 14)
            display.setFont(new Font("Sans", 0, (int) (DISPLAY_HEIGHT / 2)));
        else if (len < 19)
            display.setFont(new Font("Sans", 0, (int) (DISPLAY_HEIGHT / 2.5)));
        else if (len < 23)
            display.setFont(new Font("Sans", 0, (int) (DISPLAY_HEIGHT / 3)));
        else if (len < 27)
            display.setFont(new Font("Sans", 0, (int) (DISPLAY_HEIGHT / 3.5)));
        else if (len < 30)
            display.setFont(new Font("Sans", 0, (int) (DISPLAY_HEIGHT / 4)));
        else
            display.setFont(new Font("Sans", 0, (int) (DISPLAY_HEIGHT / 4.5)));
    }
}