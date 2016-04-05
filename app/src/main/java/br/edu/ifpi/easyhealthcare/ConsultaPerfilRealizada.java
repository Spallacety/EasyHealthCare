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
import br.edu.ifpi.easyhealthcare.dao.ExameDAO;
import br.edu.ifpi.easyhealthcare.dao.PrescricaoDAO;
import br.edu.ifpi.easyhealthcare.modelo.Consulta;

public class ConsultaPerfilRealizada extends AppCompatActivity {

    private ConsultaDAO consultaDAO = new ConsultaDAO(this);
    private int idatual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_perfil_realizada);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView data = (TextView)findViewById(R.id.data_consulta_perfil_realizada);
        TextView especialidade = (TextView)findViewById(R.id.especialidade_consulta_perfil_realizada);
        TextView horario = (TextView)findViewById(R.id.horario_consulta_perfil_realizada);
        TextView local = (TextView)findViewById(R.id.local_consulta_perfil_realizada);

        idatual = consultaDAO.getIdConsultaAtual();
        Consulta c = consultaDAO.getConsulta(idatual);

        data.setText(c.getData());
        especialidade.setText(c.getEspecialidade());
        horario.setText(c.getHorário());
        local.setText(c.getLocal());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_consultas_perfil_realizadas, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.excluirConsulta:
                AlertDialog confirmarApagar;
                AlertDialog.Builder builder2 = new AlertDialog.Builder(ConsultaPerfilRealizada.this);
                builder2.setMessage(R.string.apagar_consulta_all);
                builder2.setNegativeButton(R.string.voltar, null);
                builder2.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConsultaDAO consultaDAO = new ConsultaDAO(ConsultaPerfilRealizada.this);
                        consultaDAO.excluir(idatual);
                        PrescricaoDAO prescricaoDAO = new PrescricaoDAO(ConsultaPerfilRealizada.this);
                        prescricaoDAO.excluirConsulta(idatual);
                        ExameDAO exameDAO = new ExameDAO(ConsultaPerfilRealizada.this);
                        exameDAO.excluirConsulta(idatual);
                        Toast.makeText(ConsultaPerfilRealizada.this, R.string.confirmacao_apagar, Toast.LENGTH_LONG).show();
                        Intent voltar = new Intent(ConsultaPerfilRealizada.this, Consultas.class);
                        startActivity(voltar);
                        finish();
                    }
                });
                confirmarApagar = builder2.create();
                confirmarApagar.show();
                break;
            case R.id.listaMedicamento:
                Intent lista = new Intent(ConsultaPerfilRealizada.this, PrescricoesConsulta.class);
                startActivity(lista);
                finish();
                break;
            case R.id.listaExame:
                Intent exames = new Intent(ConsultaPerfilRealizada.this, ExamesConsulta.class);
                startActivity(exames);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ConsultaPerfilRealizada.this, Consultas.class);
        startActivity(intent);
        finish();
    }
}
