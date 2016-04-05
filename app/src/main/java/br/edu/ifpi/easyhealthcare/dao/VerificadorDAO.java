package br.edu.ifpi.easyhealthcare.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.edu.ifpi.easyhealthcare.modelo.Pessoa;

/**
 * Created by Lucas on 17/03/2016.
 */
public class VerificadorDAO extends SQLiteOpenHelper {

    public VerificadorDAO(Context context) {
        super(context, "verificador.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE verificador (id INTEGER PRIMARY KEY AUTOINCREMENT, verifica INTEGER);";
        db.execSQL(sql);
        sql = "INSERT INTO verificador (verifica) VALUES (0);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS verificador;";
        db.execSQL(sql);
        onCreate(db);
    }

    public int getVerificador(){
        String sql = "SELECT * FROM verificador ORDER BY id DESC limit 1;";
        Cursor c = getReadableDatabase().rawQuery(sql, null);
        c.moveToNext();
        int result = c.getInt(c.getColumnIndex("verifica"));
        return result;
    }

    public void atualizaVerificador(){
        String sql = "UPDATE verificador SET verifica=1 WHERE id=1;";
        getWritableDatabase().execSQL(sql);
    }
}
