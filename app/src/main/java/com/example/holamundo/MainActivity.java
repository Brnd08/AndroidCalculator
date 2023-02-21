package com.example.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    TextView resultScreen;
    Pattern twoNumbersOperationsPattern;
    Pattern singleNumberOperationsPattern;
    Pattern singleOperatorPattern;
    Pattern negativeSignPattern;
    Pattern logPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String regex2Numbers = "(\\-?\\d*\\.?\\d+)([\\+\\-÷%x\\^])(\\-?\\d*\\.?\\d+)";
        String regex1Number = "(\\btan|\\bcos|\\bsin|\\btanh|\\bcosh|\\bsinh|\\b1\\/x|\\blog10\\(|\\bln|\\bsqrt\\()(\\-?\\d*\\.?\\d+)";
        String regex0DoubleOperators = "[%x÷\\+\\.\\/^]$|(\\blog)$";
        String regexMinus = "^[\\-]$|[\\-]{2}$";
        String logRegex = "log\\((\\-?\\d*\\.?\\d+)\\)(\\-?\\d*\\.?\\d+)";

        System.out.println(regex2Numbers + "\n" + regex1Number + "\n" + regex0DoubleOperators + "\n" + regexMinus + "\n" + logRegex);
        twoNumbersOperationsPattern = Pattern.compile("(\\-?\\d*\\.?\\d+)([\\+\\-÷%x\\^]|log)(\\-?\\d*\\.?\\d+)"); // operation regex
        singleNumberOperationsPattern = Pattern.compile("(\\btan|\\bcos|\\bsin|\\btanh|\\bcosh|\\bsinh|\\b1\\/x|\\blog10\\(|\\bln|\\bsqrt\\()(\\-?\\d*\\.?\\d+)"); // second operation regex
        singleOperatorPattern = Pattern.compile("[%x÷\\+\\.\\/^]$|(\\blog)$");// match all operation signs except minus (signs) at the end of the string
        negativeSignPattern = Pattern.compile("^[\\-]$|[\\-]{2}$");// match a negative sign if its at both start and end of string, or double sign at the end of string
        logPattern = Pattern.compile("log\\((\\-?\\d*\\.?\\d+)\\)(\\-?\\d*\\.?\\d+)");// regex for operating with logarithm base n


        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// removes title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultScreen = findViewById(R.id.tvResultado);
        resultScreen.setText("");
    }

    public void writeDigit(View view) {
        resultScreen.setText(processDigitWriting(getButtonText(view), getScreenString()));
    }

    public void writeSymbol(View view) {
        resultScreen.setText(processSymbolWriting(getButtonText(view), getScreenString()));
    }

    private String getButtonText(View view) {
        return (((Button) view).getText().toString());
    }

    private String processDigitWriting(String pressedNumber, String screenText) {
        if (screenText.isEmpty()) return pressedNumber;
        return screenText + pressedNumber;
    }

    private float toFloat(String toParse) {
        if (toParse.startsWith(".")) toParse = "0".concat(toParse);
        try {
            return Float.parseFloat(toParse);
        } catch (NumberFormatException | NullPointerException e) {
            return 0.0f;
        }
    }

    private String processSymbolWriting(String pressedSymbol, String screenText) {
        switch (pressedSymbol) {
            // no double characters allowed without exception
            case "%":
            case "x":
            case "÷":
            case "+":
            case "/":
            case "log":
            case "^":
                if (singleOperatorPattern.matcher(screenText).find() || screenText.isEmpty()) {
                    Toast.makeText(this, "operador no permitido aquí", Toast.LENGTH_SHORT).show();
                    return screenText;
                }
                break;
            case ".":
                if (singleOperatorPattern.matcher(screenText).find()) {
                    Toast.makeText(this, "operador no permitido aquí", Toast.LENGTH_SHORT).show();
                    return screenText;
                } else
                    return screenText + pressedSymbol;

                // double character allowed in middle of expression, but not at the start
            case "-":
                if (negativeSignPattern.matcher(screenText).find()) {
                    Toast.makeText(this, "no mas signos negativos", Toast.LENGTH_SHORT).show();
                    return screenText;
                }
        }

        return screenText + pressedSymbol;
    }

    private String getScreenString() {
        return (resultScreen.getText().toString());
    }

    public void operate(View view) {
        Matcher twoNumbersOperationMatcher = twoNumbersOperationsPattern.matcher(getScreenString());
        Matcher singleNumberOperationMatcher = singleNumberOperationsPattern.matcher(getScreenString());
        Matcher logarithmMatcher = logPattern.matcher(getScreenString());

        if (twoNumbersOperationMatcher.matches())
            try {
                float number1 = toFloat(twoNumbersOperationMatcher.group(1));
                float number2 = toFloat(twoNumbersOperationMatcher.group(3));
                String operator = twoNumbersOperationMatcher.group(2);
                float result = getResult(number1, number2, operator);
                resultScreen.setText(String.format(Locale.US,"%.2f", result));
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

        else if (singleNumberOperationMatcher.matches())
            try {
                String operator = singleNumberOperationMatcher.group(1);
                float number1 = toFloat(singleNumberOperationMatcher.group(2));
                float result = (getResult(number1, operator));
                resultScreen.setText(String.format(Locale.US,"%.2f", result));
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        else if (logarithmMatcher.matches()) {
            try {
                float number1 = toFloat(logarithmMatcher.group(1));
                float number2 = toFloat(logarithmMatcher.group(2));
                float result = (getResult(number1, number2, "log"));
                resultScreen.setText(String.format(Locale.US,"%.2f", result));
            }catch(Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else

            Toast.makeText(this, "Input no valido", Toast.LENGTH_LONG).show();

    }

    private float getResult(float number1, float number2, String operation) throws Exception {
        float result;
        switch (operation) {
            case "x":
                result = number1 * number2;
                break;
            case "÷":
                if (number2 == 0.0f) throw new Exception("No se puede dividir entre cero");
                result = number1 / number2;
                break;
            case "%":
                result = number1 * number2 / 100;
                break;
            case "-":
                result = number1 - number2;
                break;
            case "+":
                result = number1 + number2;
                break;
            case "^":
                if (number1 == 0.0f) throw new Exception("0^0 no definido");
                result = (float) Math.pow(number1, number2);
                break;

            case "log": // logb(n) = loge(n) / loge(b) math identity
                if (number1 <= 1.0f || number2 <= 0.0f)
                    throw new Exception("No se puede resolver log(".concat(String.valueOf(number1)).concat(")").concat(String.valueOf(number2)));
                result = (float) Math.log(number2) / (float) Math.log(number1);
                break;
            default:
                result = 0.0f;
        }
        return result;
    }

    private float getResult(float number, String operation) throws Exception {
        double result = 0.0f;
        switch (operation) {
            case "cos":
                result = Math.cos(Math.toRadians(number));
                break;
            case "tan":
                result = Math.tan(Math.toRadians(number));
                break;
            case "sin":
                result = Math.sin(Math.toRadians(number));
                break;
            case "cosh":
                result = Math.cosh(number);
                break;
            case "tanh":
                result = Math.tanh(number);
                break;
            case "sinh":
                result = Math.sinh(number);
                break;
            case "ln":
                if (number == 0.0f) throw new Exception("ln(0) no esta definido");
                result = Math.log(number);
                break;
            case "log10(":
                if (number == 0.0f) throw new Exception("log10(0) no esta definido");
                result = Math.log10(number);
                break;
            case "1/x":
                if (number == 0.0f) throw new Exception("El cero no tiene inverso multiplicativo");
                result = Math.pow(number, -1);
                break;
            case "sqrt(":
                if (number < 0) throw new Exception("No existe raiz cuadrada de negativos");
                result = Math.sqrt(number);
                break;
        }
        return (float) result;
    }

    public void eraseLast(View view) {
        String numeroPantalla = getScreenString();
        if (numeroPantalla.isEmpty()) return;

        resultScreen.setText(numeroPantalla.substring(0, numeroPantalla.length() - 1));
    }

    public void cleanScreen(View view) {
        resultScreen.setText("");
    }


    public void writeOperation(View view) {
        resultScreen.setText(processOperationWriting(getButtonText(view), getScreenString()));
    }


    public String processOperationWriting(String buttonText, String screenText) {
        String newScreenText;
        switch (buttonText) {
            case "sin":
            case "cos":
            case "tan":
            case "sinh":
            case "cosh":
            case "tanh":
            case "1/x":
            case "ln":
                newScreenText = buttonText;
                break;
            case "√":
                newScreenText = "sqrt(";
                break;
            case "log10":
                newScreenText = "log10(";
                break;
            case "a^b":
                newScreenText = screenText.concat("^");
                break;
            case "log":
                newScreenText = "log(".concat(screenText).concat(")");
                break;
            default:
                newScreenText = "";
        }
        return newScreenText;
    }
}