package com.eduardo.cadastro.model.database.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLite extends SQLiteOpenHelper {

    public static final String BASEDADOS = "baseDados.db";
    public static final String TABELA_CLIENTE = "tb_cliente";
    private static final int VERSAO_BANCO = 1;

    public SQLite(Context context) {
        super(context, BASEDADOS, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlCliente = "create table if not exists " + TABELA_CLIENTE + "(cliCodigo integer primary key autoincrement," +
                        "clienteNome text not null, clienteUserName text not null)";

        try {
            db.execSQL(sqlCliente);
            Log.i("Banco de dados criado", "Banco de dados: " + sqlCliente );
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Erro", "Banco de dados: ");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("create table if not exists "+ TABELA_CLIENTE);
        onCreate(db);
    }
}

