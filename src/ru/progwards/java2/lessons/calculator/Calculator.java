package ru.progwards.java2.lessons.calculator;

public class Calculator {
    public static int calculate(String expression){
        int nOpen;
        int nClose;
        int result = 0;
        while (expression.contains("(")) {
            // обрабатываем выражение в скобках
            nOpen = 0;
            nClose = 0;
            for (int i = expression.indexOf("("); i < expression.length(); i ++)
                if (expression.substring(i, i+1).compareTo("(") == 0) nOpen ++;
                else if (expression.substring(i, i +1).compareTo(")") == 0) {
                    nClose ++;
                    if (nOpen == nClose) {
                        result = calculate(expression.substring(expression.indexOf("(") + 1, i));
                        expression = expression.substring(0, expression.indexOf("(")) +
                                String.valueOf(result) +
                                expression.substring(i + 1);
                        break;
                    }
                }
        }

        while (expression.contains("*") || expression.contains("/")){
            for (int i = 0; i < expression.length(); i ++) {
                if (expression.substring(i, i + 1).compareTo("*") == 0) {
                    result = Integer.valueOf(expression.substring(i - 1, 1)) *
                             Integer.valueOf(expression.substring(i + 1, i + 2));
                expression = expression.substring(0, i - 1) + String.valueOf(result) +
                expression.substring(i + 2);
                break;
                }
                if (expression.substring(i, i + 1).compareTo("/") == 0) {
                    result = Integer.valueOf(expression.substring(i - 1, 1)) /
                            Integer.valueOf(expression.substring(i + 1, i + 2));
                    expression = expression.substring(0, i - 1) + String.valueOf(result) +
                            expression.substring(i + 2);
                    break;
                }

            }
        }

        while (expression.contains("+") || expression.contains("-")){
            for (int i = 0; i < expression.length(); i ++) {
                if (expression.substring(i, i + 1).compareTo("+") == 0) {
                    result = Integer.valueOf(expression.substring(i - 1, 1)) +
                            Integer.valueOf(expression.substring(i + 1, i + 2));
                    expression = expression.substring(0, i - 1) + String.valueOf(result) +
                            expression.substring(i + 2);
                    break;
                }
                if (expression.substring(i, i + 1).compareTo("-") == 0) {
                    result = Integer.valueOf(expression.substring(i - 1, 1)) -
                            Integer.valueOf(expression.substring(i + 1, i + 2));
                    expression = expression.substring(0, i - 1) + String.valueOf(result) +
                            expression.substring(i + 2);
                    break;
                }

            }
        }


        return result;
    }

    public static void main(String[] args) {
        System.out.println(calculate("(5+3)*2"));
    }
}
