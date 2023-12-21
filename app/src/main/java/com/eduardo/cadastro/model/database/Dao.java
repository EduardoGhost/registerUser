package com.eduardo.cadastro.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import com.eduardo.cadastro.model.ClienteEntity;
import com.eduardo.cadastro.model.Interface;
import java.util.ArrayList;
import java.util.List;

public class Dao implements Interface {
    //escrever e ler os dados
    SQLiteDatabase sqlWrite;
    SQLiteDatabase sqlRead;
    Context context;

    public Dao(Context context) {
        SQLite base = new SQLite(context);

        sqlWrite = base.getWritableDatabase();
        sqlRead = base.getReadableDatabase();
        this.context = context;
    }

    @Override
    public boolean cadastroCliente(ClienteEntity mCliente) {
        ContentValues values = new ContentValues();
        values.put("clienteNome", mCliente.getName());

        try {
            sqlWrite.insert(SQLite.TABELA_CLIENTE, null, values);
            Log.i("Cliente ", SQLite.TABELA_CLIENTE + values);
            showToast("Cliente cadastrado com sucesso!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Erro ao cadastrar cliente.");
            return false;
        }
    }

    public List<ClienteEntity> listClientes() {
        List<ClienteEntity> listClientes = new ArrayList<>();
        String sqlSelect = "select *from "+ SQLite.TABELA_CLIENTE+";";
        Cursor cursor = sqlRead.rawQuery(sqlSelect, null);

        while (cursor.moveToNext()) {
            ClienteEntity cliente = new ClienteEntity();
            String nome;
            nome = cursor.getString(cursor.getColumnIndex("clienteNome"));
            cliente.setName(nome);
            listClientes.add(cliente);

        }
        return listClientes;
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
