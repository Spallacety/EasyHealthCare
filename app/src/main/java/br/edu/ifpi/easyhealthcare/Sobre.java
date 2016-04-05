package br.edu.ifpi.easyhealthcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Sobre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
    }

    @Override
    public void onBackPressed() {
        Intent voltar = new Intent(Sobre.this, TelaPrincipal.class);
        startActivity(voltar);
        finish();
    }
}
