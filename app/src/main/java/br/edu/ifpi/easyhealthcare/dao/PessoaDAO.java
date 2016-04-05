package br.edu.ifpi.easyhealthcare.dao;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.view.Display;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import br.edu.ifpi.easyhealthcare.modelo.Pessoa;

/**
 * Created by Lucas on 17/03/2016.
 */
public class PessoaDAO extends SQLiteOpenHelper{

    public PessoaDAO(Context context){
        super(context, "pessoa.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE pessoa (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, " +
                "dataDeNascimento TEXT, sexo TEXT, hipertenso INTEGER, cardiaco INTEGER, diabetico INTEGER);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS pessoa;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void inserir(Pessoa pessoa){
        ContentValues cv = new ContentValues();

        cv.put("nome", pessoa.getNome());
        cv.put("dataDeNascimento", pessoa.getDataDeNascimento());
        cv.put("sexo", pessoa.getSexo());
        cv.put("hipertenso", pessoa.isHipertenso());
        cv.put("cardiaco", pessoa.isCardiaco());
        cv.put("diabetico", pessoa.isDiabetico());

        getWritableDatabase().insert("pessoa", null, cv);
    }

    public Pessoa getPessoa(){
        String sql = "SELECT * FROM pessoa ORDER BY id DESC limit 1;";
        Cursor c = getReadableDatabase().rawQuery(sql, null);
        Pessoa p = new Pessoa();
        c.moveToNext();
        String nome = c.getString(c.getColumnIndex("nome"));
        String dataDeNascimento = c.getString(c.getColumnIndex("dataDeNascimento"));
        String sexo = c.getString(c.getColumnIndex("sexo"));
        int hipertenso = c.getInt(c.getColumnIndex("hipertenso"));
        int cardiaco = c.getInt(c.getColumnIndex("cardiaco"));
        int diabetico = c.getInt(c.getColumnIndex("diabetico"));
        p.setNome(nome);
        p.setDataDeNascimento(dataDeNascimento);
        p.setSexo(sexo);
        p.setHipertenso(hipertenso);
        p.setCardiaco(cardiaco);
        p.setDiabetico(diabetico);
        return p;
    }

    public void deletarPessoa() {
        String where = "nome = ?";
        String[] whereArguments = {getPessoa().getNome()};
        getWritableDatabase().delete("pessoa", where, whereArguments);
    }
}
