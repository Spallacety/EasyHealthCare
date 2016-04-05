package br.edu.ifpi.easyhealthcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.edu.ifpi.easyhealthcare.dao.ConsultaDAO;
import br.edu.ifpi.easyhealthcare.dao.PrescricaoDAO;
import br.edu.ifpi.easyhealthcare.modelo.Prescricao;

public class PrescricaoPerfil extends AppCompatActivity {

    private int idatual;
    private PrescricaoDAO prescricaoDAO = new PrescricaoDAO(this);
    private Prescricao p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescricao_perfil);
        idatual = prescricaoDAO.getIdPrescricaoAtual();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView nome = (TextView)findViewById(R.id.nome_prescricao_perfil);
        TextView detalhes = (TextView)findViewById(R.id.detalhes_prescricao_perfil);
        p = prescricaoDAO.getPrescricao(idatual);
        nome.setText(p.getNome());
        detalhes.setText(p.getDetalhes());
    }

    @Override
    public void onBackPressed() {
        Intent voltar = new Intent(PrescricaoPerfil.this, Prescricoes.class);
        startActivity(voltar);
        finish();
    }

    public void verConsulta(View v){
        Intent intent = new Intent(PrescricaoPerfil.this, ConsultaPerfilRealizada.class);
        ConsultaDAO consultaDAO = new ConsultaDAO(this);
        consultaDAO.inserirIdAtual(p.getIdConsulta());
        startActivity(intent);
        finish();
    }
}
