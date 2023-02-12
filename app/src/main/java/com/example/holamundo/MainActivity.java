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

    public void escribirNumero(View view){
        tvResultado.setText(
                ((Button)view) .getText() .toString()
        );
    }

    public void escribirUno(View view){
        tvResultado.setText(procesarEscrituraNumero("1"));
    }
    public void escribirDos(View view){
        tvResultado.setText(procesarEscrituraNumero("2"));

    }
    public void escribirTres(View view){
        tvResultado.setText(procesarEscrituraNumero("3"));

    }
    public void escribirCuatro(View view){
        tvResultado.setText(procesarEscrituraNumero("4"));

    }
    public void escribirCinco(View view){
        tvResultado.setText(procesarEscrituraNumero("5"));

    }
    public void escribirSeis(View view){
        tvResultado.setText(procesarEscrituraNumero("6"));

    }
    public void escribirSiete(View view){
        tvResultado.setText(procesarEscrituraNumero("7"));

    }
    public void escribirOcho(View view){
        tvResultado.setText(procesarEscrituraNumero("8"));

    }
    public void escribirNueve(View view){
        tvResultado.setText(procesarEscrituraNumero("9"));

    }
    public void escribirCero(View view){
        tvResultado.setText(procesarEscrituraNumero("10"));

    }

    private String procesarEscrituraNumero(String numeroPulsado){

        String valorEscrito = tvResultado.getText().toString();
        float valor = Float.parseFloat(valorEscrito);

        return (valor==0.0f)?
                numeroPulsado
                :valorEscrito + numeroPulsado;
    }
    private float obtenerNumeroPantalla(){
        return Float.parseFloat(tvResultado.getText().toString());
    }
    private String obtenerCadenaPantalla(){
        return (tvResultado.getText().toString());
    }

    public void dividir(){
        numero1 = obtenerNumeroPantalla();
        operacion = "/";
        tvResultado.setText("0");
    }
    public void mostrarResultado(View view){
        numero2 = obtenerNumeroPantalla();
        switch (operacion){
            case "/" : {

            }
            case "*": {}
        }

    }

    public void borrarUltimo(View view){
        String numeroPantalla = obtenerCadenaPantalla();
        tvResultado.setText(numeroPantalla.substring(0,numeroPantalla.length()-1));
    }
    public void borrarPantalla(View view){
        tvResultado.setText("0");
    }
}