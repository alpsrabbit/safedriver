package com.safedrive.calculator;

import java.util.HashMap;
import java.util.Map;

public class CalculatorTest {
    public static void main(String[] args) {
        // 테스트할 파라미터 맵 생성
        Map<String, Double> parameters = new HashMap<>();
        parameters.put("param1", 10.0);
        parameters.put("param2", 5.0);
        parameters.put("param3", 2.0);

        // 테스트할 수식들
        String[] expressions = {
            "{param1} + {param2}",                  // 예상 결과: 15.0
            "{param1} * ({param2} + {param3})",     // 예상 결과: 70.0
            "{param1} / {param2}",                  // 예상 결과: 2.0
            "({param1} + {param2}) * {param3}",     // 예상 결과: 30.0
            "{param1} - {param2} * {param3}",       // 예상 결과: 0.0
            "({param1} - {param2}) / {param3}"      // 예상 결과: 2.5
        };

        System.out.println("=== Calculator 테스트 시작 ===");
        System.out.println("파라미터 값들:");
        parameters.forEach((key, value) -> System.out.println(key + " = " + value));
        System.out.println("\n=== 수식 계산 결과 ===");

        for (String expr : expressions) {
            try {
                double result = Calculator.evaluate(expr, parameters);
                System.out.printf("수식: %s%n결과: %.2f%n%n", expr, result);
            } catch (Exception e) {
                System.out.printf("수식: %s%n오류: %s%n%n", expr, e.getMessage());
            }
        }

        // 오류 케이스 테스트
        System.out.println("=== 오류 케이스 테스트 ===");
        
        // 1. 존재하지 않는 파라미터
        try {
            String invalidParam = "{param1} + {param4}";
            Calculator.evaluate(invalidParam, parameters);
        } catch (Exception e) {
            System.out.println("존재하지 않는 파라미터 테스트: " + e.getMessage());
        }

        // 2. 0으로 나누기
        try {
            parameters.put("zero", 0.0);
            String divByZero = "{param1} / {zero}";
            Calculator.evaluate(divByZero, parameters);
        } catch (Exception e) {
            System.out.println("0으로 나누기 테스트: " + e.getMessage());
        }

        // 3. 빈 수식
        try {
            Calculator.evaluate("", parameters);
        } catch (Exception e) {
            System.out.println("빈 수식 테스트: " + e.getMessage());
        }
    }
} 