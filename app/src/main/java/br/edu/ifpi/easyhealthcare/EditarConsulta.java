package br.edu.ifpi.easyhealthcare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import br.edu.ifpi.easyhealthcare.dao.ConsultaDAO;
import br.edu.ifpi.easyhealthcare.modelo.Consulta;

public class EditarConsulta extends AppCompatActivity {

    private int idatual;
    private int ano, mes, dia;
    private int hora, minuto;
    private String dataFormat = "0/0/0";
    private String horaFormat = "0:0";
    private EditText especialidade, local, data, horario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_consulta);
        especialidade = (EditText)findViewById(R.id.especialidade_editar_consulta);
        local = (EditText)findViewById(R.id.local_editar_consulta);
        data = (EditText)findViewById(R.id.data_editar_consulta);
        horario = (EditText)findViewById(R.id.horario_editar_consulta);
    }

    @Override
    public void onBackPressed() {
        Intent voltar = new Intent(EditarConsulta.this, Consultas.class);
        startActivity(voltar);
        finish();
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
        horario.setInputType(InputType.TYPE_NULL);
        horario.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    timePicker(v);
                }
            }
        });
        ConsultaDAO consultaDAO = new ConsultaDAO(this);
        idatual = consultaDAO.getIdConsultaAtual();
        Consulta c = consultaDAO.getConsulta(idatual);
        especialidade.setText(c.getEspecialidade());
        data.setText(c.getData());
        local.setText(c.getLocal());
        horario.setText(c.getHorário());
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
        DatePickerDialog dlg = new DatePickerDialog(EditarConsulta.this, odsl, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        if (dataFormat != "0/0/0") {
            dlg.updateDate(ano, mes - 1, dia);
        }
        dlg.show();
    }

    public void timePicker(View v){
        hideKeyboard(v);
        TimePickerDialog.OnTimeSetListener otsl = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hora = hourOfDay;
                minuto = minute;
                horaFormat = String.format("%02d:%02d", hora, minuto);
                horario.setText(horaFormat);
                horario.setSelection(horaFormat.length());
            }
        };
        final Calendar c = Calendar.getInstance();
        TimePickerDialog dlg = new TimePickerDialog(EditarConsulta.this, otsl, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
        if (horaFormat != "0:0") {
            dlg.updateTime(hora, minuto);
        }
        dlg.show();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void salvarConsulta(View v) {
        EditText especialidade = (EditText)findViewById(R.id.especialidade_editar_consulta);
        EditText local = (EditText)findViewById(R.id.local_editar_consulta);
        EditText data = (EditText)findViewById(R.id.data_editar_consulta);
        EditText horario = (EditText)findViewById(R.id.horario_editar_consulta);
        Consulta c = new Consulta();
        c.setLocal(local.getText().toString());
        c.setHorário(horario.getText().toString());
        c.setEspecialidade(especialidade.getText().toString());
        c.setData(data.getText().toString());
        c.setConsultaRealizada(0);
        if ((c.getData().matches("")) || (c.getEspecialidade().matches("")) || (c.getHorário().matches("")) || (c.getLocal().matches(""))) {
            Toast.makeText(EditarConsulta.this, R.string.preencha_dados, Toast.LENGTH_SHORT).show();
        } else {
            ConsultaDAO consultaDAO = new ConsultaDAO(this);
            consultaDAO.editarConsulta(idatual, c);
            Toast.makeText(EditarConsulta.this, R.string.dados_salvos_consulta, Toast.LENGTH_LONG).show();
            Intent voltar = new Intent(EditarConsulta.this, Consultas.class);
            startActivity(voltar);
            finish();
        }
    }
}
