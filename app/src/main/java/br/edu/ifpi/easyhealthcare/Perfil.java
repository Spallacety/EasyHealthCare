package br.edu.ifpi.easyhealthcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.zip.Inflater;

import br.edu.ifpi.easyhealthcare.dao.PessoaDAO;
import br.edu.ifpi.easyhealthcare.modelo.Pessoa;

public class Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        setResult(0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        PessoaDAO dao = new PessoaDAO(this);
        Pessoa p = dao.getPessoa();

        TextView nome = (TextView)findViewById(R.id.nomeNoPerfil);
        nome.setText(p.getNome());

        TextView data = (TextView)findViewById(R.id.dataNoPerfil);
        data.setText(p.getDataDeNascimento());

        TextView sexo = (TextView)findViewById(R.id.sexoNoPerfil);
        if(p.getSexo().equals(getText(R.string.masculino))){
            sexo.setText(" ♂");
            sexo.setTextColor(getResources().getColor(R.color.babyBlue));
        }else{
            sexo.setText(" ♀");
            sexo.setTextColor(getResources().getColor(R.color.babyPink));
        }

        final Calendar c = Calendar.getInstance();
        TextView idade = (TextView)findViewById(R.id.idadeNoPerfil);
        String str = null;
        if((Integer.parseInt(data.getText().toString().substring(3, 5))-1) > c.get(Calendar.MONTH)){
            str = (c.get(Calendar.YEAR) - Integer.parseInt(data.getText().toString().substring(6, 10)) -1) + " ";
        } else if((Integer.parseInt(data.getText().toString().substring(3, 5))-1) < c.get(Calendar.MONTH)) {
            str = (c.get(Calendar.YEAR) - Integer.parseInt(data.getText().toString().substring(6, 10))) + " ";
        } else {
            if(Integer.parseInt(data.getText().toString().substring(0, 2)) > c.get(Calendar.DAY_OF_MONTH)){
                str = (c.get(Calendar.YEAR) - Integer.parseInt(data.getText().toString().substring(6, 10)) -1) + " ";
            } else {
                str = (c.get(Calendar.YEAR) - Integer.parseInt(data.getText().toString().substring(6, 10))) + " ";
            }
        }
        idade.setText(str);

        CheckBox hipertenso = (CheckBox)findViewById(R.id.hipertensoNoPerfil);
        if(p.isHipertenso() == 1){
            hipertenso.setChecked(true);
        } else {
            hipertenso.setChecked(false);
        }

        CheckBox cardiaco = (CheckBox)findViewById(R.id.cardiacoNoPerfil);
        if(p.isCardiaco() == 1){
            cardiaco.setChecked(true);
        } else {
            cardiaco.setChecked(false);
        }

        CheckBox diabetico = (CheckBox)findViewById(R.id.diabeticoNoPerfil);
        if(p.isDiabetico() == 1){
            diabetico.setChecked(true);
        } else {
            diabetico.setChecked(false);
        }

    }

    @Override
    public void onBackPressed() {
        Intent voltar = new Intent(this, TelaPrincipal.class);
        startActivity(voltar);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_perfil, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.editarPerfil:
                Intent editarPerfil = new Intent(Perfil.this, EditarDados.class);
                startActivity(editarPerfil);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
