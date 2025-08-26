package com.example.semaforo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView imgSemaforo;
    Button btnIniciar;
    boolean corriendo = false; // solo controlamos si el hilo ya se inició

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgSemaforo = findViewById(R.id.imgSemaforo);
        btnIniciar = findViewById(R.id.btnIniciar);

        btnIniciar.setOnClickListener(v -> {
            if (!corriendo) {
                corriendo = true;
                Thread hilo = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int contador = 0; // este número nos dice si toca encendido o apagado

                        while (corriendo) {
                            int estado = contador % 2; // alterna entre 0 y 1

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (estado == 0) {
                                        imgSemaforo.setImageResource(R.drawable.circulo_encendido);
                                    } else {
                                        imgSemaforo.setImageResource(R.drawable.circulo_apagado);
                                    }
                                }
                            });

                            contador++; // pasa al siguiente estado

                            try {
                                Thread.sleep(5000); // espera 5 segundos
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
                hilo.start();
            }
        });
    }
}
