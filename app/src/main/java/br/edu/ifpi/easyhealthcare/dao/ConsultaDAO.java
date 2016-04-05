package br.edu.ifpi.easyhealthcare.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.easyhealthcare.modelo.Consulta;

/**
 * Created by Lucas on 31/03/2016.
 */
public class ConsultaDAO extends SQLiteOpenHelper {

    public ConsultaDAO(Context context) {
        super(context, "consultas.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE consulta (id INTEGER PRIMARY KEY AUTOINCREMENT, data TEXT, " +
                "especialidade TEXT, local TEXT, horario TEXT, consultaRealizada INTEGER);";
        db.execSQL(sql);
        sql = "CREATE TABLE idatual (id INTEGER PRIMARY KEY AUTOINCREMENT, idatual INTEGER);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS consulta;";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS idatual;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void inserir(Consulta consulta){
        ContentValues cv = new ContentValues();

        cv.put("data", consulta.getData());
        cv.put("especialidade", consulta.getEspecialidade());
        cv.put("local", consulta.getLocal());
        cv.put("horario", consulta.getHorário());
        cv.put("consultaRealizada", consulta.isConsultaRealizada());

        getWritableDatabase().insert("consulta", null, cv);
    }

    public List<Consulta> getLista(){
        String sql = "SELECT * FROM consulta;";
        Cursor c = getWritableDatabase().rawQuery(sql, null);
        List<Consulta> lista = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Consulta con = new Consulta();
                int id = c.getInt(c.getColumnIndex("id"));
                String data = c.getString(c.getColumnIndex("data"));
                String especialidade = c.getString(c.getColumnIndex("especialidade"));
                String local = c.getString(c.getColumnIndex("local"));
                String horario = c.getString(c.getColumnIndex("horario"));
                int consultaRealizada = c.getInt(c.getColumnIndex("consultaRealizada"));
                con.setId(id);
                con.setData(data);
                con.setEspecialidade(especialidade);
                con.setHorário(horario);
                con.setLocal(local);
                con.setConsultaRealizada(consultaRealizada);
                lista.add(con);
            } while (c.moveToNext());
        }
        return lista;
    }

    public List<Consulta> getListaNaoRealizada(){
        String sql = "SELECT * FROM consulta WHERE consultaRealizada = 0;";
        Cursor c = getWritableDatabase().rawQuery(sql, null);
        List<Consulta> lista = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Consulta con = new Consulta();
                int id = c.getInt(c.getColumnIndex("id"));
                String data = c.getString(c.getColumnIndex("data"));
                String especialidade = c.getString(c.getColumnIndex("especialidade"));
                String local = c.getString(c.getColumnIndex("local"));
                String horario = c.getString(c.getColumnIndex("horario"));
                int consultaRealizada = c.getInt(c.getColumnIndex("consultaRealizada"));
                con.setId(id);
                con.setData(data);
                con.setEspecialidade(especialidade);
                con.setHorário(horario);
                con.setLocal(local);
                con.setConsultaRealizada(consultaRealizada);
                lista.add(con);
            } while (c.moveToNext());
        }
        return lista;
    }

    public List<Consulta> getListaRealizada(){
        String sql = "SELECT * FROM consulta WHERE consultaRealizada = 1;";
        Cursor c = getWritableDatabase().rawQuery(sql, null);
        List<Consulta> lista = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Consulta con = new Consulta();
                int id = c.getInt(c.getColumnIndex("id"));
                String data = c.getString(c.getColumnIndex("data"));
                String especialidade = c.getString(c.getColumnIndex("especialidade"));
                String local = c.getString(c.getColumnIndex("local"));
                String horario = c.getString(c.getColumnIndex("horario"));
                int consultaRealizada = c.getInt(c.getColumnIndex("consultaRealizada"));
                con.setId(id);
                con.setData(data);
                con.setEspecialidade(especialidade);
                con.setHorário(horario);
                con.setLocal(local);
                con.setConsultaRealizada(consultaRealizada);
                lista.add(con);
            } while (c.moveToNext());
        }
        return lista;
    }

    public Consulta getConsulta(int id){
        String sql = "SELECT * FROM consulta WHERE id = " + id + ";";
        Cursor c = getWritableDatabase().rawQuery(sql, null);
        Consulta con = new Consulta();
        if (c.moveToFirst()) {
            int id2 = c.getInt(c.getColumnIndex("id"));
            String data = c.getString(c.getColumnIndex("data"));
            String especialidade = c.getString(c.getColumnIndex("especialidade"));
            String local = c.getString(c.getColumnIndex("local"));
            String horario = c.getString(c.getColumnIndex("horario"));
            int consultaRealizada = c.getInt(c.getColumnIndex("consultaRealizada"));
            con.setId(id2);
            con.setData(data);
            con.setEspecialidade(especialidade);
            con.setHorário(horario);
            con.setLocal(local);
            con.setConsultaRealizada(consultaRealizada);
        }
        return con;
    }

    public void inserirIdAtual(int id){
        String sql = "SELECT * FROM idatual ORDER BY id DESC limit 1;";
        Cursor c = getReadableDatabase().rawQuery(sql, null);
        if(c.moveToNext()){
            sql = "DELETE FROM idatual WHERE id = " + c.getInt(c.getColumnIndex("id")) + ";";
            getWritableDatabase().execSQL(sql);
        }

        ContentValues cv = new ContentValues();

        cv.put("idatual", id);

        getWritableDatabase().insert("idatual", null, cv);
    }

    public int getIdConsultaAtual(){
        String sql = "SELECT * FROM idatual ORDER BY id DESC limit 1;";
        Cursor c = getReadableDatabase().rawQuery(sql, null);
        c.moveToNext();
        return c.getInt(c.getColumnIndex("idatual"));
    }

    public void editarConsulta(int id, Consulta consulta){
        ContentValues cv = new ContentValues();
        cv.put("data", consulta.getData());
        cv.put("especialidade", consulta.getEspecialidade());
        cv.put("local", consulta.getLocal());
        cv.put("horario", consulta.getHorário());
        String[] id2 = {String.valueOf(id)};
        getWritableDatabase().update("consulta", cv, "id = ?", id2);
    }

    public void marcarRealizada(int id){
        ContentValues cv = new ContentValues();
        cv.put("consultaRealizada", 1);
        String[] id2 = {String.valueOf(id)};
        getWritableDatabase().update("consulta", cv, "id = ?", id2);
    }

    public void excluir(int id){
        String sql = "DELETE FROM consulta WHERE ID = " + id + ";";
        getWritableDatabase().execSQL(sql);
    }
}
