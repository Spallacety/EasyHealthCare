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
import br.edu.ifpi.easyhealthcare.dao.ExameDAO;
import br.edu.ifpi.easyhealthcare.modelo.Exame;

public class EditarExame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_exame);
        ExameDAO exameDAO = new ExameDAO(this);
        EditText tipo = (EditText)findViewById(R.id.tipo_editar_exame);
        EditText detalhes = (EditText)findViewById(R.id.detalhes_editar_exame);
        Exame e = exameDAO.getExame(exameDAO.getIdExameAtual());
        tipo.setText(e.getTipo());
        detalhes.setText(e.getDetalhes());
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

    public void editarExame(View v){
        EditText tipo = (EditText)findViewById(R.id.tipo_editar_exame);
        EditText detalhes = (EditText)findViewById(R.id.detalhes_editar_exame);
        ConsultaDAO consultaDAO = new ConsultaDAO(this);
        int id = consultaDAO.getIdConsultaAtual();
        Exame e = new Exame();
        e.setTipo(tipo.getText().toString());
        e.setIdConsulta(id);
        e.setDetalhes(detalhes.getText().toString());
        if ((e.getTipo().matches("")) || (e.getDetalhes().matches(""))) {
            Toast.makeText(EditarExame.this, R.string.preencha_dados, Toast.LENGTH_SHORT).show();
        } else {
            ExameDAO exameDAO = new ExameDAO(this);
            exameDAO.editarExame(exameDAO.getIdExameAtual(), e);
            Toast.makeText(EditarExame.this, R.string.dados_salvos_exame, Toast.LENGTH_LONG).show();
            Intent voltar = new Intent(EditarExame.this, ExamePerfilConsulta.class);
            startActivity(voltar);
            finish();
        }
    }
}
