package com.example.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvResultado;
    float numero1 = 0.0f;
    float numero2 = 0.0f;
    String operacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResultado = findViewById(R.id.tvResultado);
    }

    public void escribirNumero(View view) {
        String numeroPulsado = (((Button) view).getText().toString());
        tvResultado.setText(
                procesarEscrituraNumero(numeroPulsado)
        );
    }

    private String procesarEscrituraNumero(String numeroPulsado) {

        String valorEscrito = obtenerCadenaPantalla();
        if(valorEscrito.isEmpty()){
            return numeroPulsado;
        }

        float valor = Float.parseFloat(valorEscrito);
        return (valor == 0.0f) ?
                numeroPulsado
                : valorEscrito + numeroPulsado;

    }

    private float obtenerNumeroPantalla() {
        return Float.parseFloat(obtenerCadenaPantalla());
    }

    private String obtenerCadenaPantalla() {
        return (tvResultado.getText().toString());
    }

    public void dividir() {
        numero1 = obtenerNumeroPantalla();
        operacion = "/";
        tvResultado.setText("0");
    }

    public void mostrarResultado(View view) {
        numero2 = obtenerNumeroPantalla();
        switch (operacion) {
            case "/": {

            }
            case "*": {
            }
        }

    }

    public void borrarUltimo(View view) {
        String numeroPantalla = obtenerCadenaPantalla();
        if(numeroPantalla.isEmpty())
            return;
        tvResultado.setText(numeroPantalla.substring(0, numeroPantalla.length() - 1));
    }

    public void borrarPantalla(View view) {
        tvResultado.setText("");
    }
}