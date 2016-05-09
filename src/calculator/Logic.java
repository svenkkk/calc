package calculator;

import static calculator.Settings.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Stack;

public class Logic implements ActionListener, KeyListener {
    private Stack<BigDecimal> numStack;
    private Stack<String> opeStack;
    private StringBuilder tempStack;

    public Logic() {
        numStack = new Stack<BigDecimal>();
        opeStack = new Stack<String>();
        tempStack = new StringBuilder(16);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        handleButtons(e.getActionCommand());

    }

    @Override
    public void keyTyped(KeyEvent e) {
        handleButtons(String.valueOf(e.getKeyChar()));
    }

    public void handleButtons(String actionCommand) {
        if (isNumber(actionCommand))
            handleNumber(actionCommand);
        if (isAction(actionCommand))
            handleAction(actionCommand);
        if (isOperator(actionCommand))
            handleOperator(actionCommand);
    }

    private boolean isNumber(String command) {
        return ("1234567890.,".indexOf(command) != -1) ? true : false;
    }

    private boolean isAction(String command) {
        for (int i = 0; i < ACTION.length; i++)
            if (ACTION[i].equals(command))
                return true;
        return false;
    }

    private boolean isOperator(String command) {
        for (int i = 0; i < OPERATION.length; i++)
            if (OPERATION[i].equals(command))
                return true;
        return false;
    }

    private void handleNumber(String str) {
        if (tempStack.length() < 16) {
            if (str.equals(".") || str.equals(",")) {
                handlePoint();
            } else
                handleDigit(str);
        }
    }

    private void handleDigit(String num) {
        if (opeStack.empty() && !numStack.empty()) {
            numStack.pop();
        }
        tempStack.append(num);
        View.setDisplayTo(tempStack.toString());
    }

    private void handlePoint() {
        if (tempStack.length() == 0)
            tempStack.append("0.");
        if (tempStack.indexOf(".") == -1)
            tempStack.append(".");
        View.setDisplayTo(tempStack.toString());
    }

    private void handleOperator(String str) throws ArithmeticException {
        try {
            if (tempStack.length() != 0) {
                numStack.push(getEnteredNum());
                if (isPrior(str)) {
                    opeStack.push(str);
                } else {
                    doTheMath();
                    opeStack.push(str);
                }
            } else if (!opeStack.empty()) {
                opeStack.pop();
                opeStack.push(str);
            } else if (!numStack.empty())
                opeStack.push(str);
            if (!opeStack.empty() && opeStack.peek().equals("=")) {
                opeStack.pop(); // pop the equal operator.
                doTheMath();
            }
        } catch (ArithmeticException e) {
            View.setDisplayTo("INF");
            e.printStackTrace();
            System.out.println(e.getMessage());
            return;
        }
        if (!numStack.empty())
            View.setDisplayTo(numStack.peek());
    }

    /**
     * Tests if the current operator is prior than the last.
     *
     * @param operator
     *            current operator.
     * @return true if current operator is prior.
     */
    private boolean isPrior(String operator) {
        if (opeStack.empty())
            return true;
        if (operator.equals(EQUAL))
            return true;
        if ((operator.equals(MULTIPLY) || operator.equals(DIVIDE))
                && (opeStack.peek().equals(ADD) || opeStack.peek().equals(SUBTRACT)))
            return true;

        else
            return false;
    }

    private void handleAction(String str) {
        if (str.equals(CLEAR) || str.equals(clear))
            allClear();

        if (str.equals(DELETE) || str.equals(delete)) {
            delete();
        }

        if (str.equals(PERCENT))
            percent();
    }

    private void allClear() {
        numStack.removeAllElements();
        opeStack.removeAllElements();
        tempStack.setLength(0);
        View.setDisplayTo("0");
    }

    private void delete() {
        if (tempStack.length() != 0)
            tempStack.setLength(tempStack.length() - 1);
        if (tempStack.length() == 0 && !numStack.empty())
            numStack.pop();
        if (tempStack.length() != 0)
            View.setDisplayTo(tempStack.toString());
        else
            View.setDisplayTo("0");
    }

    private void percent() {
        if (tempStack.length() != 0) {
            numStack.push(getEnteredNum().divide(BigDecimal.valueOf(100)));
            View.setDisplayTo(numStack.peek());
        } else if (!numStack.empty()) {
            numStack.push(numStack.pop().divide(BigDecimal.valueOf(100)));
            View.setDisplayTo(numStack.peek());
        }
    }

    private BigDecimal getEnteredNum() {
        BigDecimal currentNum = new BigDecimal(tempStack.toString());
        tempStack.setLength(0);
        return currentNum;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            handleButtons("=");
        else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
            handleButtons(DELETE);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Do The Math.
     */
    private void doTheMath() throws ArithmeticException {
        while (numStack.size() > 1) {
            BigDecimal secondNum = numStack.pop();
            BigDecimal firstNum = numStack.pop();
            String operator = opeStack.pop();
            numStack.push(calculate(firstNum, operator, secondNum));
        }
    }

    /**
     * Calculation method.
     *
     * @param firstNumber
     * @param operator
     * @param secondNumber
     * @return calculated value.
     * @throws ArithmeticException
     *             zero denominator exception.
     */
    private BigDecimal calculate(BigDecimal firstNumber, String operator, BigDecimal secondNumber)
            throws ArithmeticException {
        if (operator.equals(ADD))
            return firstNumber.add(secondNumber, MathContext.DECIMAL64);
        if (operator.equals(SUBTRACT))
            return firstNumber.subtract(secondNumber, MathContext.DECIMAL64);
        if (operator.equals(MULTIPLY))
            return firstNumber.multiply(secondNumber, MathContext.DECIMAL64);
        if (operator.equals(DIVIDE))
            return firstNumber.divide(secondNumber, MathContext.DECIMAL64);
        return null;
    }

    // TODO For testing purposes. Will be deleted later.
    @SuppressWarnings("unused")
    private void printStatus() {
        String nt = !numStack.empty() ? String.valueOf(numStack.peek()) : "";
        String tt = tempStack.length() != 0 ? String.valueOf(tempStack.toString()) : "";
        String ot = !opeStack.empty() ? opeStack.peek() : "";
        System.out.println("NS:" + nt + "\t" + "TS:" + tt + "\t" + "OS:" + ot);
    }
}