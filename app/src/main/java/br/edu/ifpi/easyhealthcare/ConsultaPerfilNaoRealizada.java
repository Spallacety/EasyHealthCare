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
import br.edu.ifpi.easyhealthcare.modelo.Consulta;

public class ConsultaPerfilNaoRealizada extends AppCompatActivity {

    ConsultaDAO consultaDAO = new ConsultaDAO(this);
    int idatual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_perfil_nao_realizada);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView data = (TextView)findViewById(R.id.data_consulta_perfil);
        TextView especialidade = (TextView)findViewById(R.id.especialidade_consulta_perfil);
        TextView horario = (TextView)findViewById(R.id.horario_consulta_perfil);
        TextView local = (TextView)findViewById(R.id.local_consulta_perfil);

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

        inflater.inflate(R.menu.menu_consultas_perfil_nao_realizadas, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.excluirConsulta:
                AlertDialog confirmarApagar;
                AlertDialog.Builder builder2 = new AlertDialog.Builder(ConsultaPerfilNaoRealizada.this);
                builder2.setMessage(R.string.apagar_consulta);
                builder2.setNegativeButton(R.string.voltar, null);
                builder2.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConsultaDAO consultaDAO = new ConsultaDAO(ConsultaPerfilNaoRealizada.this);
                        consultaDAO.excluir(idatual);
                        Toast.makeText(ConsultaPerfilNaoRealizada.this, R.string.confirmacao_apagar, Toast.LENGTH_LONG).show();
                        Intent voltar = new Intent(ConsultaPerfilNaoRealizada.this, Consultas.class);
                        startActivity(voltar);
                        finish();
                    }
                });
                confirmarApagar = builder2.create();
                confirmarApagar.show();
                break;
            case R.id.editarConsulta:
                Intent editar = new Intent(ConsultaPerfilNaoRealizada.this, EditarConsulta.class);
                startActivity(editar);
                finish();
                break;
            case R.id.marcarRealizada:
                AlertDialog dlg;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage(R.string.marcar_realizada);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        consultaDAO.marcarRealizada(idatual);
                        Intent irParaRealizada = new Intent(ConsultaPerfilNaoRealizada.this, ConsultaPerfilRealizada.class);
                        startActivity(irParaRealizada);
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.nao, null);
                dlg = builder.create();
                dlg.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
