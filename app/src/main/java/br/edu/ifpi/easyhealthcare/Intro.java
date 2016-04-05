package br.edu.ifpi.easyhealthcare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import br.edu.ifpi.easyhealthcare.dao.PessoaDAO;
import br.edu.ifpi.easyhealthcare.modelo.Pessoa;

public class Intro extends AppCompatActivity {

    AlertDialog sairDoApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    public void click(View v){
        Intent novaPessoa = new Intent(this, NovaPessoa.class);
        startActivity(novaPessoa);
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.deseja_sair);
        builder.setNegativeButton(R.string.voltar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        builder.setPositiveButton(R.string.sair, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        sairDoApp = builder.create();
        sairDoApp.show();

    }
}
