package br.edu.ifpi.easyhealthcare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import br.edu.ifpi.easyhealthcare.dao.PessoaDAO;
import br.edu.ifpi.easyhealthcare.dao.VerificadorDAO;
import br.edu.ifpi.easyhealthcare.modelo.Pessoa;

public class TelaPrincipal extends AppCompatActivity {

    PessoaDAO dao = new PessoaDAO(this);
    AlertDialog confirmarApagarDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
    }

    @Override
    protected void onResume() {
        super.onResume();

        VerificadorDAO verificador = new VerificadorDAO(this);

        if(verificador.getVerificador() == 0){
            Intent intro = new Intent(TelaPrincipal.this, Intro.class);
            startActivity(intro);
            finish();
        } else {
            Pessoa p = dao.getPessoa();
            TextView ola = (TextView)findViewById(R.id.ola);
            String str = getString(R.string.ola, p.getNome());
            ola.setText(str);
            menu();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.apagarDados:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage(R.string.apagar_dados);
                builder.setPositiveButton(R.string.cancelar, null);
                builder.setNegativeButton(R.string.apagar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        AlertDialog confirmarApagar;
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(TelaPrincipal.this);
                        builder2.setMessage(R.string.encerrar_aplicacao);
                        builder2.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deletarDados();
                            }
                        });
                        confirmarApagar = builder2.create();
                        confirmarApagar.show();
                    }
                });
                confirmarApagarDados = builder.create();
                confirmarApagarDados.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deletarDados() {
        String deleteCmd = "pm clear br.edu.ifpi.easyhealthcare";
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(deleteCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog sairDoApp;
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

    public void menu(){
        final RelativeLayout consultas = (RelativeLayout)findViewById(R.id.menuConsultas);
        consultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text = (TextView)findViewById(R.id.principal_consultas);
                text.setTextColor(getResources().getColor(R.color.white));
                consultas.setBackground(getResources().getDrawable(R.drawable.menu_gradient_dark));
                Intent irParaConsultas = new Intent(TelaPrincipal.this, Consultas.class);
                startActivity(irParaConsultas);
                finish();
            }
        });
        final RelativeLayout prescricoes = (RelativeLayout)findViewById(R.id.menuPrescricoes);
        prescricoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text = (TextView)findViewById(R.id.principal_prescricoes);
                text.setTextColor(getResources().getColor(R.color.white));
                prescricoes.setBackground(getResources().getDrawable(R.drawable.menu_gradient_dark));
                Intent irParaPrescricoes = new Intent(TelaPrincipal.this, Prescricoes.class);
                startActivity(irParaPrescricoes);
                finish();
            }
        });
        final RelativeLayout exames = (RelativeLayout)findViewById(R.id.menuExames);
        exames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text = (TextView)findViewById(R.id.principal_exames);
                text.setTextColor(getResources().getColor(R.color.white));
                exames.setBackground(getResources().getDrawable(R.drawable.menu_gradient_dark));
                Intent irParaExames = new Intent(TelaPrincipal.this, Exames.class);
                startActivity(irParaExames);
                finish();
            }
        });
        final RelativeLayout perfil = (RelativeLayout)findViewById(R.id.menuPerfil);
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text = (TextView)findViewById(R.id.principal_perfil);
                text.setTextColor(getResources().getColor(R.color.white));
                perfil.setBackground(getResources().getDrawable(R.drawable.menu_gradient_dark));
                Intent irParaPerfil = new Intent(TelaPrincipal.this, Perfil.class);
                startActivityForResult(irParaPerfil, 0);
                finish();
            }
        });
        final RelativeLayout sobre = (RelativeLayout)findViewById(R.id.menuSobre);
        sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text = (TextView)findViewById(R.id.principal_sobre);
                text.setTextColor(getResources().getColor(R.color.white));
                sobre.setBackground(getResources().getDrawable(R.drawable.menu_gradient_dark));
                Intent sobre = new Intent(TelaPrincipal.this, Sobre.class);
                startActivity(sobre);
                finish();
            }
        });
    }
}

