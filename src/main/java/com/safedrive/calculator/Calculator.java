package com.safedrive.calculator;

import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    
    public static double evaluate(String expression, Map<String, Double> parameters) {
        // 파라미터 치환
        expression = replaceParameters(expression, parameters);
        
        // 공백 제거 및 유효성 검사
        expression = expression.replaceAll("\\s+", "");
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("빈 수식입니다.");
        }
        
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();
        
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            
            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder num = new StringBuilder();
                while (i < expression.length() && 
                      (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    num.append(expression.charAt(i));
                    i++;
                }
                i--;
                numbers.push(Double.parseDouble(num.toString()));
            }
            else if (ch == '(') {
                operators.push(ch);
            }
            else if (ch == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.pop(); // Remove '('
            }
            else if (isOperator(ch)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(ch);
            }
        }
        
        while (!operators.isEmpty()) {
            numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
        }
        
        return numbers.pop();
    }
    
    private static String replaceParameters(String expression, Map<String, Double> parameters) {
        if (parameters == null) {
            throw new IllegalArgumentException("파라미터 맵이 null입니다.");
        }
        
        // {paramName} 형식의 모든 파라미터를 찾아서 치환
        Pattern pattern = Pattern.compile("\\{([^}]+)}");
        Matcher matcher = pattern.matcher(expression);
        StringBuffer sb = new StringBuffer();
        
        while (matcher.find()) {
            String paramName = matcher.group(1);
            Double value = parameters.get(paramName);
            
            // 파라미터 값 유효성 검사
            if (value == null) {
                throw new IllegalArgumentException("파라미터 '" + paramName + "'의 값이 null입니다.");
            }
            
            // NaN 검사
            if (value.isNaN()) {
                throw new IllegalArgumentException("파라미터 '" + paramName + "'의 값이 숫자가 아닙니다(NaN).");
            }
            
            // Infinity 검사
            if (value.isInfinite()) {
                throw new IllegalArgumentException("파라미터 '" + paramName + "'의 값이 무한대입니다.");
            }
            
            matcher.appendReplacement(sb, value.toString());
        }
        matcher.appendTail(sb);
        
        return sb.toString();
    }
    
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }
    
    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return 0;
    }
    
    private static double applyOperation(char operator, double b, double a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("0으로 나눌 수 없습니다.");
                }
                return a / b;
        }
        return 0;
    }
} 