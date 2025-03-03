package com.safedrive.calculator;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    @PostMapping("/evaluate")
    public CalculatorResponse evaluate(@RequestBody CalculatorRequest request) {
        try {
            if (request == null) {
                return new CalculatorResponse(false, null, "요청이 null입니다.");
            }
            
            if (request.getExpression() == null || request.getExpression().trim().isEmpty()) {
                return new CalculatorResponse(false, null, "수식이 비어있습니다.");
            }

            if (request.getParameters() == null) {
                return new CalculatorResponse(false, null, "파라미터 맵이 null입니다.");
            }

            double result = Calculator.evaluate(request.getExpression(), request.getParameters());
            return new CalculatorResponse(true, String.valueOf(result), null);
        } catch (IllegalArgumentException e) {
            // 파라미터 관련 오류 (null, NaN, Infinity 등)
            return new CalculatorResponse(false, null, e.getMessage());
        } catch (ArithmeticException e) {
            // 0으로 나누기 등의 수학적 오류
            return new CalculatorResponse(false, null, e.getMessage());
        } catch (Exception e) {
            // 기타 예상치 못한 오류
            return new CalculatorResponse(false, null, "계산 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}

class CalculatorRequest {
    private String expression;
    private Map<String, Double> parameters;

    // Getters and Setters
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Map<String, Double> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Double> parameters) {
        this.parameters = parameters;
    }
}

class CalculatorResponse {
    private boolean success;
    private String result;
    private String error;

    public CalculatorResponse(boolean success, String result, String error) {
        this.success = success;
        this.result = result;
        this.error = error;
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getResult() {
        return result;
    }

    public String getError() {
        return error;
    }
} 