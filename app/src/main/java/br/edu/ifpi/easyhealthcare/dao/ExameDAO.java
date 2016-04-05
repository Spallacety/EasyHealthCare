package br.edu.ifpi.easyhealthcare.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.easyhealthcare.modelo.Exame;

/**
 * Created by Lucas on 02/04/2016.
 */
public class ExameDAO extends SQLiteOpenHelper {

    public ExameDAO(Context context) {
        super(context, "exames.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE exame (id INTEGER PRIMARY KEY AUTOINCREMENT, tipo TEXT, " +
                "detalhes TEXT, idConsulta INTEGER);";
        db.execSQL(sql);
        sql = "CREATE TABLE idatual (id INTEGER PRIMARY KEY AUTOINCREMENT, idatual INTEGER);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS exame;";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS idatual;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void inserir(Exame e) {
        ContentValues cv = new ContentValues();

        cv.put("tipo", e.getTipo());
        cv.put("detalhes", e.getDetalhes());
        cv.put("idConsulta", e.getIdConsulta());

        getWritableDatabase().insert("exame", null, cv);
    }

    public Exame getExame(int id) {
        String sql = "SELECT * FROM exame WHERE id = " + id + ";";
        Cursor c = getWritableDatabase().rawQuery(sql, null);
        Exame e = new Exame();
        if (c.moveToFirst()) {
            int id2 = c.getInt(c.getColumnIndex("id"));
            int idConsulta = c.getInt(c.getColumnIndex("idConsulta"));
            String tipo = c.getString(c.getColumnIndex("tipo"));
            String detalhes = c.getString(c.getColumnIndex("detalhes"));
            e.setTipo(tipo);
            e.setId(id2);
            e.setIdConsulta(idConsulta);
            e.setDetalhes(detalhes);
        }
        return e;
    }

    public List<Exame> getListaExame() {
        String sql = "SELECT * FROM exame;";
        Cursor c = getWritableDatabase().rawQuery(sql, null);
        List<Exame> lista = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Exame e = new Exame();
                int id = c.getInt(c.getColumnIndex("id"));
                String tipo = c.getString(c.getColumnIndex("tipo"));
                String detalhes = c.getString(c.getColumnIndex("detalhes"));
                int idConsulta = c.getInt(c.getColumnIndex("idConsulta"));
                e.setId(id);
                e.setDetalhes(detalhes);
                e.setIdConsulta(idConsulta);
                e.setTipo(tipo);
                lista.add(e);
            } while (c.moveToNext());
        }
        return lista;
    }

    public List<Exame> getListaExameConsulta(int id) {
        String sql = "SELECT * FROM exame WHERE idConsulta = " + id + ";";
        Cursor c = getWritableDatabase().rawQuery(sql, null);
        List<Exame> lista = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Exame e = new Exame();
                int id2 = c.getInt(c.getColumnIndex("id"));
                String tipo = c.getString(c.getColumnIndex("tipo"));
                String detalhes = c.getString(c.getColumnIndex("detalhes"));
                int idConsulta = c.getInt(c.getColumnIndex("idConsulta"));
                e.setId(id2);
                e.setDetalhes(detalhes);
                e.setIdConsulta(idConsulta);
                e.setTipo(tipo);
                lista.add(e);
            } while (c.moveToNext());
        }
        return lista;
    }

    public void editarExame(int id, Exame exame) {
        ContentValues cv = new ContentValues();
        cv.put("tipo", exame.getTipo());
        cv.put("detalhes", exame.getDetalhes());
        String[] id2 = {String.valueOf(id)};
        getWritableDatabase().update("exame", cv, "id = ?", id2);
    }

    public void excluir(int id) {
        String sql = "DELETE FROM exame WHERE ID = " + id + ";";
        getWritableDatabase().execSQL(sql);
    }

    public void excluirConsulta(int idConsulta) {
        String sql = "DELETE FROM exame WHERE idConsulta = " + idConsulta + ";";
        getWritableDatabase().execSQL(sql);
    }

    public void inserirIdAtual(int id) {
        String sql = "SELECT * FROM idatual ORDER BY id DESC limit 1;";
        Cursor c = getReadableDatabase().rawQuery(sql, null);
        if (c.moveToNext()) {
            sql = "DELETE FROM idatual WHERE id = " + c.getInt(c.getColumnIndex("id")) + ";";
            getWritableDatabase().execSQL(sql);
        }

        ContentValues cv = new ContentValues();

        cv.put("idatual", id);

        getWritableDatabase().insert("idatual", null, cv);
    }

    public int getIdExameAtual() {
        String sql = "SELECT * FROM idatual ORDER BY id DESC limit 1;";
        Cursor c = getReadableDatabase().rawQuery(sql, null);
        c.moveToNext();
        return c.getInt(c.getColumnIndex("idatual"));
    }
}
