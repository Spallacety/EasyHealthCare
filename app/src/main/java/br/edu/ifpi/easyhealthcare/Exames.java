package br.edu.ifpi.easyhealthcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import br.edu.ifpi.easyhealthcare.dao.ExameDAO;
import br.edu.ifpi.easyhealthcare.modelo.Exame;

public class Exames extends AppCompatActivity {

    ArrayAdapter<Exame> adapter;
    ExameDAO exameDAO = new ExameDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exames);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListView list = (ListView)findViewById(R.id.lista_exames_total);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exameDAO.getListaExame());
        list.setAdapter(adapter);
        EditText search = (EditText)findViewById(R.id.exames_total_pesquisa);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                Exames.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Exames.this, ExamePerfil.class);
                String resposta = parent.getItemAtPosition(position).toString();
                String resposta2[] = resposta.split(" ");
                int res = Integer.parseInt(resposta2[1].substring(0, resposta2[1].length() - 1));
                exameDAO.inserirIdAtual(res);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent voltar = new Intent(Exames.this, TelaPrincipal.class);
        startActivity(voltar);
        finish();
    }

}

