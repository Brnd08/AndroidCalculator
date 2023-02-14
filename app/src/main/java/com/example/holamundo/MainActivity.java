package com.example.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    TextView tvResultado;
    float numero1 = 0.0f;
    float numero2 = 0.0f;
    Pattern twoNumbersOperationsPattern;
    Pattern singleNumberOperationsPattern;
    String operacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        twoNumbersOperationsPattern = Pattern.compile("(\\-?\\d*\\.?\\d+)([\\+\\-÷%x\\^])(\\-?\\d*\\.?\\d+)"); // operation regex
        singleNumberOperationsPattern = Pattern.compile("(\\btan|\\bcos|\\bsin|\\b1\\/x|\\blog|\\bln)(\\-?\\d*\\.?\\d+)"); // second operation regex

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResultado = findViewById(R.id.tvResultado);
        tvResultado.setText("");
    }

    public void writeDigit(View view) {
        tvResultado.setText(processDigitWriting(getButtonText(view), getScreenString()));
    }

    public void writeSymbol(View view) {
        tvResultado.setText(processSymbolWriting(getButtonText(view), getScreenString()));
    }

    private String getButtonText(View view) {
        return (((Button) view).getText().toString());
    }

    private String processDigitWriting(String pressedNumber, String screenText) {
        if (screenText.isEmpty()) return pressedNumber;
        return screenText + pressedNumber;
    }

    private float toFloat(String toParse) {
        if (toParse.startsWith(".")) toParse = "0" + toParse;
        try {
            return Float.parseFloat(toParse);
        } catch (NumberFormatException | NullPointerException e) {
            return 0.0f;
        }
    }

    private String processSymbolWriting(String pressedSymbol, String screenText) {
        if (screenText.isEmpty()) return pressedSymbol;
        return screenText + pressedSymbol;
    }

    private float getScreenFloat() {
        return toFloat(getScreenString());
    }

    private String getScreenString() {
        return (tvResultado.getText().toString());
    }

    public void operate(View view) {
        try {

            Matcher twoNumbersOperationMatcher = twoNumbersOperationsPattern.matcher(getScreenString());
            Matcher singleNumberOperationMatcher = singleNumberOperationsPattern.matcher(getScreenString());

            if (twoNumbersOperationMatcher.matches()) {
                float number1 = toFloat(twoNumbersOperationMatcher.group(1));
                float number2 = toFloat(twoNumbersOperationMatcher.group(3));
                String operator = twoNumbersOperationMatcher.group(2);

                System.out.println(number1 + " " + operator + " " + number2);

                String result = String.valueOf(getResult(number1, number2, operator));
                tvResultado.setText(result);


            } else if (singleNumberOperationMatcher.matches()) {
                Toast.makeText(this, "operacion de un numero", Toast.LENGTH_LONG).show();
                float number1 = toFloat(singleNumberOperationMatcher.group(1));
                String operator = singleNumberOperationMatcher.group(2);

                System.out.println(operator + " " + number1);

                String result = String.valueOf(getResult(number1, operator));
                tvResultado.setText(result);
            } else {
                Toast.makeText(this, "Input no valido", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private float getResult(float number1, float number2, String operation) throws Exception {
        float result;
        switch (operation) {
            case "x":
                result = number1 * number2;
                break;
            case "÷":
                if (number2 == 0.0f) throw new Exception("No se puede dividir entre 0");
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
                if (number1 == 0.0f) throw new Exception("0^0 no esta definido");
                result = (float) Math.pow(number1, number2);
                break;
            default:
                result = 0.0f;
        }

        System.out.println(number1 + " " + operation + " " + number2);
        return result;
    }

    private float getResult(float number, String operation) throws Exception {
        double result = 0.0f;
        switch (operation) {
            case "cos":
                result = Math.cos(number);
                break;
            case "tan":
                result = Math.tan(number);
                break;
            case "sin":
                result = Math.sin(number);
                break;
            case "ln":
                if (number == 0.0f) throw new Exception("ln(0) no esta definido");
                result = Math.log(number);
                break;
            case "log":
                if (number == 0.0f) throw new Exception("log10(0) no esta definido");
                result = Math.log10(number);
                break;
            case "1/x":
                if (number == 0.0f) throw new Exception("El cero no tiene inverso multiplicativo");
                result = Math.pow(number, -1);
                break;
        }
        System.out.println(operation + " of " + number);
        return (float) result;
    }

    public void borrarUltimo(View view) {
        String numeroPantalla = getScreenString();
        if (numeroPantalla.isEmpty()) return;

        tvResultado.setText(numeroPantalla.substring(0, numeroPantalla.length() - 1));
    }

    public void borrarPantalla(View view) {
        tvResultado.setText("");
    }


    public void writeOperation(View view) {
        tvResultado.setText(processOperationWriting(getButtonText(view), getScreenString()));
    }


    public String processOperationWriting(String buttonText, String screenText) {
        String newScreenText;
        switch (buttonText) {
            case "sin":
            case "cos":
            case "tan":
            case "1/x":
                newScreenText = buttonText;
                break;
            case "a^b":
                newScreenText = "^";
                break;
            case "ln/log":
                newScreenText = screenText.contains("log") ? newScreenText = screenText.replaceFirst("log", "ln")
                        : screenText.contains("ln") ? newScreenText = screenText.replaceFirst("ln", "log")
                        : "ln";
                break;
            default:
                newScreenText = "";
        }
        return newScreenText;
    }
}