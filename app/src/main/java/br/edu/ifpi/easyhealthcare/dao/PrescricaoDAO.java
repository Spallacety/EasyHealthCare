package br.edu.ifpi.easyhealthcare.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.easyhealthcare.modelo.Consulta;
import br.edu.ifpi.easyhealthcare.modelo.Prescricao;

/**
 * Created by Lucas on 02/04/2016.
 */
public class PrescricaoDAO extends SQLiteOpenHelper {

    public PrescricaoDAO(Context context){
        super(context, "prescricoes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE prescricao (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, " +
                "detalhes TEXT, idConsulta INTEGER);";
        db.execSQL(sql);
        sql = "CREATE TABLE idatual (id INTEGER PRIMARY KEY AUTOINCREMENT, idatual INTEGER);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS prescricao;";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS idatual;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void inserir(Prescricao p){
        ContentValues cv = new ContentValues();

        cv.put("nome", p.getNome());
        cv.put("detalhes", p.getDetalhes());
        cv.put("idConsulta", p.getIdConsulta());

        getWritableDatabase().insert("prescricao", null, cv);
    }

    public Prescricao getPrescricao(int id){
        String sql = "SELECT * FROM prescricao WHERE id = " + id + ";";
        Cursor c = getWritableDatabase().rawQuery(sql, null);
        Prescricao p = new Prescricao();
        if (c.moveToFirst()) {
            int id2 = c.getInt(c.getColumnIndex("id"));
            int idConsulta = c.getInt(c.getColumnIndex("idConsulta"));
            String nome = c.getString(c.getColumnIndex("nome"));
            String detalhes = c.getString(c.getColumnIndex("detalhes"));
            p.setNome(nome);
            p.setId(id2);
            p.setIdConsulta(idConsulta);
            p.setDetalhes(detalhes);
        }
        return p;
    }

    public List<Prescricao> getListaPrescricao(){
        String sql = "SELECT * FROM prescricao;";
        Cursor c = getWritableDatabase().rawQuery(sql, null);
        List<Prescricao> lista = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Prescricao p = new Prescricao();
                int id = c.getInt(c.getColumnIndex("id"));
                String nome = c.getString(c.getColumnIndex("nome"));
                String detalhes = c.getString(c.getColumnIndex("detalhes"));
                int idConsulta = c.getInt(c.getColumnIndex("idConsulta"));
                p.setId(id);
                p.setDetalhes(detalhes);
                p.setIdConsulta(idConsulta);
                p.setNome(nome);
                lista.add(p);
            } while (c.moveToNext());
        }
        return lista;
    }

    public List<Prescricao> getListaPrescricaoConsulta(int id){
        String sql = "SELECT * FROM prescricao WHERE idConsulta = " + id + ";";
        Cursor c = getWritableDatabase().rawQuery(sql, null);
        List<Prescricao> lista = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Prescricao p = new Prescricao();
                int id2 = c.getInt(c.getColumnIndex("id"));
                String nome = c.getString(c.getColumnIndex("nome"));
                String detalhes = c.getString(c.getColumnIndex("detalhes"));
                int idConsulta = c.getInt(c.getColumnIndex("idConsulta"));
                p.setId(id2);
                p.setDetalhes(detalhes);
                p.setIdConsulta(idConsulta);
                p.setNome(nome);
                lista.add(p);
            } while (c.moveToNext());
        }
        return lista;
    }

    public void editarPrescricao(int id, Prescricao prescricao){
        ContentValues cv = new ContentValues();
        cv.put("nome", prescricao.getNome());
        cv.put("detalhes", prescricao.getDetalhes());
        String[] id2 = {String.valueOf(id)};
        getWritableDatabase().update("prescricao", cv, "id = ?", id2);
    }

    public void excluir(int id){
        String sql = "DELETE FROM prescricao WHERE ID = " + id + ";";
        getWritableDatabase().execSQL(sql);
    }

    public void excluirConsulta(int idConsulta){
        String sql = "DELETE FROM prescricao WHERE idConsulta = " + idConsulta + ";";
        getWritableDatabase().execSQL(sql);
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

    public int getIdPrescricaoAtual(){
        String sql = "SELECT * FROM idatual ORDER BY id DESC limit 1;";
        Cursor c = getReadableDatabase().rawQuery(sql, null);
        c.moveToNext();
        return c.getInt(c.getColumnIndex("idatual"));
    }
}
