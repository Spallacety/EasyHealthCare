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

import br.edu.ifpi.easyhealthcare.dao.ConsultaDAO;
import br.edu.ifpi.easyhealthcare.dao.PrescricaoDAO;
import br.edu.ifpi.easyhealthcare.modelo.Consulta;
import br.edu.ifpi.easyhealthcare.modelo.Prescricao;

public class PrescricaoPerfilConsulta extends AppCompatActivity {

    private int idatual;
    private PrescricaoDAO prescricaoDAO = new PrescricaoDAO(this);
    private Prescricao p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescricao_perfil_consulta);
        idatual = prescricaoDAO.getIdPrescricaoAtual();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView nome = (TextView)findViewById(R.id.nome_prescricao_perfil_consulta);
        TextView detalhes = (TextView)findViewById(R.id.detalhes_prescricao_perfil_consulta);
        p = prescricaoDAO.getPrescricao(idatual);
        nome.setText(p.getNome());
        detalhes.setText(p.getDetalhes());
    }

    @Override
    public void onBackPressed() {
        Intent voltar = new Intent(PrescricaoPerfilConsulta.this, PrescricoesConsulta.class);
        startActivity(voltar);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_prescricao, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.editarPrescricao:
                Intent intent = new Intent(PrescricaoPerfilConsulta.this, EditarPrescricao.class);
                startActivity(intent);
                finish();
                break;
            case R.id.excluirPrescricao:
                AlertDialog confirmarApagar;
                AlertDialog.Builder builder2 = new AlertDialog.Builder(PrescricaoPerfilConsulta.this);
                builder2.setMessage(R.string.apagar_prescricao);
                builder2.setNegativeButton(R.string.voltar, null);
                builder2.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PrescricaoDAO prescricaoDAO = new PrescricaoDAO(PrescricaoPerfilConsulta.this);
                        prescricaoDAO.excluir(idatual);
                        Toast.makeText(PrescricaoPerfilConsulta.this, R.string.confirmacao_apagar, Toast.LENGTH_LONG).show();
                        Intent voltar = new Intent(PrescricaoPerfilConsulta.this, PrescricoesConsulta.class);
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
