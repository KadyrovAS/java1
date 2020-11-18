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
                    result = Integer.valueOf(value(expression, i - 1)) *
                             Integer.valueOf(value(expression,i + 1));
                expression = expression.substring(0, i - 1) + String.valueOf(result) +
                expression.substring(i + 2);
                break;
                }
                if (expression.substring(i, i + 1).compareTo("/") == 0) {
                    result = Integer.valueOf(value(expression, i - 1)) /
                            Integer.valueOf(value(expression,i + 1));
                    expression = expression.substring(0, i - 1) + String.valueOf(result) +
                            expression.substring(i + 2);
                    break;
                }

            }
        }

        while (expression.contains("+") || expression.contains("-")){
            for (int i = 0; i < expression.length(); i ++) {
                if (expression.substring(i, i + 1).compareTo("+") == 0) {
                    result = Integer.valueOf(value(expression, i - 1)) +
                            Integer.valueOf(value(expression,i + 1));
                    expression = expression.substring(0, i - 1) + String.valueOf(result) +
                            expression.substring(i + 2);
                    break;
                }
                if (expression.substring(i, i + 1).compareTo("-") == 0) {
                    result = Integer.valueOf(value(expression, i - 1)) -
                            Integer.valueOf(value(expression,i + 1));
                    expression = expression.substring(0, i - 1) + String.valueOf(result) +
                            expression.substring(i + 2);
                    break;
                }

            }
        }


        return result;
    }

    public static String value(String expression, int index) {
        int indexStart = 0;
        int indexEnd = 0;
        char[] simbol;
        for (int i = index; i >= 0; i--) {
            simbol = expression.substring(i, i + 1).toCharArray();
            if (!Character.isDigit(simbol[0])) {
                indexStart = i + 1;
                break;
            }
        }
        for (int i = index; i < expression.length(); i ++) {
            simbol = expression.substring(i, i + 1).toCharArray();
            if (!Character.isDigit(simbol[0])) {
                indexEnd = i;
                break;
            }
        }
        if (indexEnd == 0) indexEnd = expression.length();
        return expression.substring(indexStart, indexEnd);
    }

    public static void main(String[] args) {
        System.out.println(calculate("(2+3)*3"));
    }
}
