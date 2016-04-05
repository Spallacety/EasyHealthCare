package br.edu.ifpi.easyhealthcare;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;

import br.edu.ifpi.easyhealthcare.dao.PessoaDAO;
import br.edu.ifpi.easyhealthcare.modelo.Pessoa;

public class EditarDados extends AppCompatActivity{

    int ano, mes, dia;
    String dataFormat = "0/0/0";
    EditText nome;
    EditText data;
    Spinner sexo;
    CheckBox hipertenso;
    CheckBox cardiaco;
    CheckBox diabetico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_dados);
        nome = (EditText) findViewById(R.id.nomePessoaEditar);
        data = (EditText) findViewById(R.id.dataPessoaEditar);
        sexo = (Spinner) findViewById(R.id.sexoPessoaEditar);
        hipertenso = (CheckBox) findViewById(R.id.hipertensoEditar);
        cardiaco = (CheckBox) findViewById(R.id.cardiacoEditar);
        diabetico = (CheckBox) findViewById(R.id.diabeticoEditar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final PessoaDAO dao = new PessoaDAO(this);
        final Pessoa p = dao.getPessoa();

        nome.setText(p.getNome());
        nome.setSelection(p.getNome().length());
        data.setText(p.getDataDeNascimento());
        data.setSelection(p.getDataDeNascimento().length());
        if(p.getSexo().equals(getText(R.string.feminino))) {
            sexo.setSelection(1);
        }

        if(p.isHipertenso() == 1){
            hipertenso.setChecked(true);
        }
        if(p.isCardiaco() == 1){
            cardiaco.setChecked(true);
        }
        if(p.isDiabetico() == 1){
            diabetico.setChecked(true);
        }
        data.setInputType(InputType.TYPE_NULL);
        data.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    datePicker(v, p);
                }
            }
        });
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(v, p);
            }
        });
        nome.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    nome.clearFocus();
                    data.requestFocus();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent voltarParaPerfil = new Intent(this, Perfil.class);
        startActivity(voltarParaPerfil);
        finish();
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

    public void datePicker(View v, final Pessoa p){
        hideKeyboard(v);
        DatePickerDialog.OnDateSetListener odsl = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                ano = year;
                mes = monthOfYear + 1;
                dia = dayOfMonth;
                dataFormat = String.format("%02d/%02d/%04d", dia, mes, ano);
                data.setText(dataFormat);
                data.setSelection(p.getDataDeNascimento().length());
            }
        };
        DatePickerDialog dlg = new DatePickerDialog(EditarDados.this, odsl, Integer.parseInt(p.getDataDeNascimento().substring(6, 10)), (Integer.parseInt(p.getDataDeNascimento().substring(3, 5)) - 1), Integer.parseInt(p.getDataDeNascimento().substring(0, 2)));
        if (dataFormat != "0/0/0") {
            dlg.updateDate(ano, mes - 1, dia);
        }
        dlg.show();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void editarPessoa(View v) {
        Pessoa p = new Pessoa();
        PessoaDAO dao = new PessoaDAO(this);

        p.setNome(nome.getText().toString());
        p.setDataDeNascimento(data.getText().toString());
        p.setSexo(sexo.getSelectedItem().toString());
        if(hipertenso.isChecked()){
            p.setHipertenso(1);
        } else {
            p.setHipertenso(0);
        }
        if(cardiaco.isChecked()){
            p.setCardiaco(1);
        } else {
            p.setCardiaco(0);
        }
        if(diabetico.isChecked()){
            p.setDiabetico(1);
        } else {
            p.setDiabetico(0);
        }


        if((p.getNome().matches("")) || (p.getDataDeNascimento().matches(""))){
            Toast.makeText(EditarDados.this, R.string.preencha_dados, Toast.LENGTH_SHORT).show();
        } else {
            dao.deletarPessoa();
            dao.inserir(p);

            Toast.makeText(EditarDados.this, R.string.dados_salvos, Toast.LENGTH_LONG).show();

            Intent voltarParaPerfil = new Intent(this, Perfil.class);
            startActivity(voltarParaPerfil);
            finish();
        }
    }
}