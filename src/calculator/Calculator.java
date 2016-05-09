package calculator;

public class Calculator {

    public static void main(String[] args) {
        try {
            new View();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}