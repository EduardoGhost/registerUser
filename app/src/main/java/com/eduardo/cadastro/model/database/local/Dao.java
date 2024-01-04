package com.eduardo.cadastro.model.database.local;

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
        values.put("clienteUserName", mCliente.getUserName());

        try {
            sqlWrite.insert(SQLite.TABELA_CLIENTE, null, values);
            Log.i("Cliente Dados", SQLite.TABELA_CLIENTE + values);
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
        String sqlSelect = "select * from " + SQLite.TABELA_CLIENTE + ";";
        Cursor cursor = sqlRead.rawQuery(sqlSelect, null);

        while (cursor.moveToNext()) {
            ClienteEntity cliente = new ClienteEntity();
            Long codigo = cursor.getLong(cursor.getColumnIndexOrThrow("cliCodigo"));

            String nome, userName;
            nome = cursor.getString(cursor.getColumnIndex("clienteNome"));
            userName = cursor.getString(cursor.getColumnIndex("clienteUserName"));

            cliente.setCodeId(codigo);
            cliente.setName(nome);
            cliente.setUserName(userName);

            listClientes.add(cliente);
        }
        return listClientes;
    }

    //deleta por id
    public boolean Delete(ClienteEntity cliente) {
        try {
            String[] id = {cliente.getCodeId().toString()};
            sqlWrite.delete(SQLite.TABELA_CLIENTE,"cliCodigo = ?", id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
