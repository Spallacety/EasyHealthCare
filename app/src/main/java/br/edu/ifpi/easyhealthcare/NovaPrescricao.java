package br.edu.ifpi.easyhealthcare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifpi.easyhealthcare.dao.ConsultaDAO;
import br.edu.ifpi.easyhealthcare.dao.PrescricaoDAO;
import br.edu.ifpi.easyhealthcare.modelo.Consulta;
import br.edu.ifpi.easyhealthcare.modelo.Prescricao;

public class NovaPrescricao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_prescricao);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    hideKeyboard(v);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void salvarPrescricao(View v){
        EditText nome = (EditText)findViewById(R.id.nome_nova_prescricao);
        EditText detalhes = (EditText)findViewById(R.id.detalhes_nova_prescricao);
        ConsultaDAO consultaDAO = new ConsultaDAO(this);
        int id = consultaDAO.getIdConsultaAtual();
        Prescricao p = new Prescricao();
        p.setNome(nome.getText().toString());
        p.setIdConsulta(id);
        p.setDetalhes(detalhes.getText().toString());
        if ((p.getNome().matches("")) || (p.getDetalhes().matches(""))) {
            Toast.makeText(NovaPrescricao.this, R.string.preencha_dados, Toast.LENGTH_SHORT).show();
        } else {
            PrescricaoDAO prescricaoDAO = new PrescricaoDAO(this);
            prescricaoDAO.inserir(p);
            Toast.makeText(NovaPrescricao.this, R.string.dados_salvos_prescricao, Toast.LENGTH_LONG).show();
            Intent voltar = new Intent(NovaPrescricao.this, PrescricoesConsulta.class);
            startActivity(voltar);
            finish();
        }
    }
}
