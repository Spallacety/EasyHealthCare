package br.edu.ifpi.easyhealthcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.edu.ifpi.easyhealthcare.dao.ConsultaDAO;
import br.edu.ifpi.easyhealthcare.dao.ExameDAO;
import br.edu.ifpi.easyhealthcare.modelo.Exame;

public class ExamePerfil extends AppCompatActivity {

    private int idatual;
    private ExameDAO exameDAO = new ExameDAO(this);
    private Exame e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exame_perfil);
        idatual = exameDAO.getIdExameAtual();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tipo = (TextView)findViewById(R.id.tipo_exame_perfil);
        TextView detalhes = (TextView)findViewById(R.id.detalhes_exame_perfil);
        e = exameDAO.getExame(idatual);
        tipo.setText(e.getTipo());
        detalhes.setText(e.getDetalhes());
    }

    @Override
    public void onBackPressed() {
        Intent voltar = new Intent(ExamePerfil.this, Exames.class);
        startActivity(voltar);
        finish();
    }

    public void verConsulta(View v){
        Intent intent = new Intent(ExamePerfil.this, ConsultaPerfilRealizada.class);
        ConsultaDAO consultaDAO = new ConsultaDAO(this);
        consultaDAO.inserirIdAtual(e.getIdConsulta());
        startActivity(intent);
        finish();
    }
}
