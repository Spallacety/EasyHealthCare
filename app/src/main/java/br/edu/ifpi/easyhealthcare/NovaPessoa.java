package br.edu.ifpi.easyhealthcare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
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
import br.edu.ifpi.easyhealthcare.dao.VerificadorDAO;
import br.edu.ifpi.easyhealthcare.modelo.Pessoa;

public class NovaPessoa extends AppCompatActivity {

    private AlertDialog sairDoApp;
    int ano, mes, dia;
    String dataFormat = "0/0/0";
    EditText data;
    EditText nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_pessoa);
        data = (EditText) findViewById(R.id.dataPessoa);
        nome = (EditText) findViewById(R.id.nomePessoa);
    }

    @Override
    protected void onResume() {
        super.onResume();
        data.setInputType(InputType.TYPE_NULL);
        data.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    datePicker(v);
                }
            }
        });
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(v);
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

    public void datePicker(View v){
        hideKeyboard(v);
        DatePickerDialog.OnDateSetListener odsl = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                ano = year;
                mes = monthOfYear + 1;
                dia = dayOfMonth;
                dataFormat = String.format("%02d/%02d/%04d", dia, mes, ano);
                data.setText(dataFormat);
                data.setSelection(dataFormat.length());
            }
        };
        final Calendar c = Calendar.getInstance();
        DatePickerDialog dlg = new DatePickerDialog(NovaPessoa.this, odsl, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        if (dataFormat != "0/0/0") {
            dlg.updateDate(ano, mes - 1, dia);
        }
        dlg.show();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void salvarPessoa(View v) {
        final EditText nome = (EditText) findViewById(R.id.nomePessoa);
        final EditText data = (EditText) findViewById(R.id.dataPessoa);
        Spinner sexo = (Spinner) findViewById(R.id.sexoPessoa);
        CheckBox hipertenso = (CheckBox) findViewById(R.id.hipertenso);
        CheckBox cardiaco = (CheckBox) findViewById(R.id.cardiaco);
        CheckBox diabetico = (CheckBox) findViewById(R.id.diabetico);

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

        Pessoa p = new Pessoa();
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

        PessoaDAO dao = new PessoaDAO(this);
        if((p.getNome().matches("")) || (p.getDataDeNascimento().matches(""))){
            Toast.makeText(NovaPessoa.this, R.string.preencha_dados, Toast.LENGTH_SHORT).show();
        } else {
            dao.inserir(p);

            Toast.makeText(NovaPessoa.this, R.string.dados_salvos, Toast.LENGTH_LONG).show();

            VerificadorDAO verificador = new VerificadorDAO(this);
            verificador.atualizaVerificador();

            Intent irParaPrincipal = new Intent(NovaPessoa.this, TelaPrincipal.class);
            startActivity(irParaPrincipal);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.deseja_sair);
        builder.setNegativeButton(R.string.voltar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        builder.setPositiveButton(R.string.sair, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        sairDoApp = builder.create();
        sairDoApp.show();
    }
}