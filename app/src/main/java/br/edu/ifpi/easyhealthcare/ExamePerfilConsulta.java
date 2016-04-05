package br.edu.ifpi.easyhealthcare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifpi.easyhealthcare.dao.ExameDAO;
import br.edu.ifpi.easyhealthcare.modelo.Exame;

public class ExamePerfilConsulta extends AppCompatActivity {
    private int idatual;
    private ExameDAO exameDAO = new ExameDAO(this);
    private Exame e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exame_perfil_consulta);
        idatual = exameDAO.getIdExameAtual();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tipo = (TextView)findViewById(R.id.tipo_exame_perfil_consulta);
        TextView detalhes = (TextView)findViewById(R.id.detalhes_exame_perfil_consulta);
        e = exameDAO.getExame(idatual);
        tipo.setText(e.getTipo());
        detalhes.setText(e.getDetalhes());
    }

    @Override
    public void onBackPressed() {
        Intent voltar = new Intent(ExamePerfilConsulta.this, ExamesConsulta.class);
        startActivity(voltar);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_exame, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.editarExame:
                Intent intent = new Intent(ExamePerfilConsulta.this, EditarExame.class);
                startActivity(intent);
                finish();
                break;
            case R.id.excluirExame:
                AlertDialog confirmarApagar;
                AlertDialog.Builder builder2 = new AlertDialog.Builder(ExamePerfilConsulta.this);
                builder2.setMessage(R.string.apagar_exame);
                builder2.setNegativeButton(R.string.voltar, null);
                builder2.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ExameDAO exameDAO = new ExameDAO(ExamePerfilConsulta.this);
                        exameDAO.excluir(idatual);
                        Toast.makeText(ExamePerfilConsulta.this, R.string.confirmacao_apagar, Toast.LENGTH_LONG).show();
                        Intent voltar = new Intent(ExamePerfilConsulta.this, ExamesConsulta.class);
                        startActivity(voltar);
                        finish();
                    }
                });
                confirmarApagar = builder2.create();
                confirmarApagar.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
