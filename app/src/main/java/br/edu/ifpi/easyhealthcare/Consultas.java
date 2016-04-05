package br.edu.ifpi.easyhealthcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import br.edu.ifpi.easyhealthcare.dao.ConsultaDAO;
import br.edu.ifpi.easyhealthcare.modelo.Consulta;

public class Consultas extends AppCompatActivity {

    private ArrayAdapter<Consulta> adapter1, adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        TabHost abas = (TabHost) findViewById(R.id.tabhost);
        abas.setup();

        TabHost.TabSpec descritor = abas.newTabSpec("aba1");
        descritor.setContent(R.id.nao_realizadas);
        descritor.setIndicator(getString(R.string.nao_realizadas));
        abas.addTab(descritor);

        descritor = abas.newTabSpec("aba2");
        descritor.setContent(R.id.realizadas);
        descritor.setIndicator(getString(R.string.realizadas));
        abas.addTab(descritor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ConsultaDAO consultaDAO = new ConsultaDAO(this);

        ListView naoRealizadas = (ListView)findViewById(R.id.nao_realizadas);
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, consultaDAO.getListaNaoRealizada());
        naoRealizadas.setAdapter(adapter1);
        naoRealizadas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Consultas.this, ConsultaPerfilNaoRealizada.class);
                String resposta = parent.getItemAtPosition(position).toString();
                String resposta2[] = resposta.split(" ");
                int res = Integer.parseInt(resposta2[1].substring(0, resposta2[1].length() - 1));
                consultaDAO.inserirIdAtual(res);
                startActivity(intent);
            }
        });

        ListView realizadas = (ListView)findViewById(R.id.realizadas);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, consultaDAO.getListaRealizada());
        realizadas.setAdapter(adapter2);
        realizadas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Consultas.this, ConsultaPerfilRealizada.class);
                String resposta = parent.getItemAtPosition(position).toString();
                String resposta2[] = resposta.split(" ");
                int res = Integer.parseInt(resposta2[1].substring(0, resposta2[1].length() - 1));
                consultaDAO.inserirIdAtual(res);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent voltar = new Intent(Consultas.this, TelaPrincipal.class);
        startActivity(voltar);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_consultas, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.novaConsulta:
                Intent novaConsulta = new Intent(Consultas.this, NovaConsulta.class);
                startActivity(novaConsulta);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}