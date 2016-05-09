package calculator;

import java.awt.Toolkit;

public class Settings {
    // Application view size settings.
    public static double screen_width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static double screen_height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public static final int APP_WIDTH = (int) (screen_width / 6);
    public static final int APP_HEIGHT = (int) (APP_WIDTH * 1.3);
    public static final int GAP = 6;
    public static final int DISPLAY_WIDTH = APP_WIDTH - 2 * GAP;
    public static final int DISPLAY_HEIGHT = (APP_HEIGHT - 5 * GAP) / 6;
    public static final int BUTTON_WIDTH = (APP_WIDTH - 5 * GAP) / 4;
    public static final int BUTTON_HEIGHT = (APP_HEIGHT - 7 * GAP) / 6;
    // Variable definitions.
    public static final String CLEAR = "C";
    public static final String DELETE = "D";
    public static final String clear = "c";
    public static final String delete = "d";
    public static final String PERCENT = "%";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = "/";
    public static final String SUBTRACT = "-";
    public static final String ADD = "+";
    public static final String EQUAL = "=";
    public static final String[] BUTTONS = { CLEAR, DELETE, PERCENT, DIVIDE, "7", "8", "9", MULTIPLY, "4", "5", "6",
            SUBTRACT, "1", "2", "3", ADD, "0", ".", " ", EQUAL };
    public static final String[] OPERATION = { ADD, SUBTRACT, DIVIDE, MULTIPLY, EQUAL };
    public static final String[] ACTION = { CLEAR, clear, DELETE, delete, PERCENT };

}
