package com.eduardo.cadastro.model.database.local;

import static com.eduardo.cadastro.model.database.local.ValidationUtil.isValidEmail;
import static com.eduardo.cadastro.model.database.local.ValidationUtil.isValidNome;
import static com.eduardo.cadastro.model.database.local.ValidationUtil.isValidPassword;
import static com.eduardo.cadastro.model.database.local.ValidationUtil.isValidUsername;
import android.annotation.SuppressLint;
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
        String senha = mCliente.getPassword();

        if (isValidEmail(mCliente.getEmail())){

        if (isValidNome(mCliente.getName())) {
            showToast("Nome deve ter mais de 15 caracteres.");
            return false;
        }

        if (isValidUsername(mCliente.getUserName(), sqlRead)) {
            showToast("Username já está em uso. Escolha outro.");
            return false;
        }

        if (ValidationUtil.isClienteMaiorIdade(mCliente)) {

        if (isValidPassword(senha)) {
            ContentValues values = new ContentValues();
            values.put("clienteNome", mCliente.getName());
            values.put("clienteUserName", mCliente.getUserName());
            values.put("password", senha);
            values.put("adress", mCliente.getAdress());
            values.put("email", mCliente.getEmail());
            values.put("date", mCliente.getDate());
            values.put("cpfOrCnpj", mCliente.getCpfOrCnpj());
            values.put("gender", mCliente.getGender());
            values.put("picture", mCliente.getPicture());

            try {
                sqlWrite.insert(SQLite.TABELA_CLIENTE, null, values);
                Log.i("Cliente Dados", SQLite.TABELA_CLIENTE + values);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        } else {
            showToast("Senha inválida. Deve ter pelo menos 8 caracteres, um número e uma letra maiúscula.");
            return false;
        }

        } else {
            showToast("É necessário ter mais de 18 anos para se cadastrar.");
            return false;
        }

        } else {
            showToast("Email inválido. Insira um email válido.");
            return false;
        }
    }

    @Override
    public boolean alterarCliente(ClienteEntity mCliente) {
        String senha = mCliente.getPassword();

        if (isValidEmail(mCliente.getEmail())) {

        if (isValidNome(mCliente.getName())) {
            showToast("Nome deve ter mais de 15 caracteres.");
            return false;
        }

        if (ValidationUtil.isClienteMaiorIdade(mCliente)) {

        if (isValidPassword(senha)) {
            ContentValues values = new ContentValues();
            values.put("clienteNome", mCliente.getName());
            values.put("clienteUserName", mCliente.getUserName());
            values.put("password", mCliente.getPassword());
            values.put("adress", mCliente.getAdress());
            values.put("email", mCliente.getEmail());
            values.put("date", mCliente.getDate());
            values.put("cpfOrCnpj", mCliente.getCpfOrCnpj());
    //    values.put("gender", mCliente.getGender());
      //  values.put("picture", mCliente.getPicture());

            try {
            String[] id = {String.valueOf(mCliente.getCodeId())};
            sqlWrite.update(SQLite.TABELA_CLIENTE, values, "cliCodigo = ?", id);
            sqlWrite.close();

            return true;
        }catch (Exception e){
            Log.i("Informação: ","Erro ao atualizar dados: "+e.getMessage());
            return false;
        }
            finally {
                sqlWrite.close();
        }
        }
         else {
                showToast("Senha inválida. Deve ter pelo menos 8 caracteres, um número e uma letra maiúscula.");
                return false;
        }

        } else {
            showToast("É necessário ter mais de 18 anos para se cadastrar.");
            return false;
            }

    } else {
        showToast("Email inválido. Insira um email válido.");
        return false;
    }
    }

    @Override
    public boolean deleteCliente(ClienteEntity mCliente) {
        try {
            String[] id = {mCliente.getCodeId().toString()};
            sqlWrite.delete(SQLite.TABELA_CLIENTE,"cliCodigo = ?", id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @SuppressLint("Range")
    public List<ClienteEntity> listClientes() {
        List<ClienteEntity> listClientes = new ArrayList<>();
        String sqlSelect = "select * from " + SQLite.TABELA_CLIENTE + ";";
        Cursor cursor = sqlRead.rawQuery(sqlSelect, null);

        while (cursor.moveToNext()) {
            ClienteEntity cliente = new ClienteEntity();
            Long codigo = cursor.getLong(cursor.getColumnIndexOrThrow("cliCodigo"));

            String nome, userName, password, adress, email, date, cpfOrCnpj, gender, picture;
            nome = cursor.getString(cursor.getColumnIndex("clienteNome"));
            userName = cursor.getString(cursor.getColumnIndex("clienteUserName"));
            password = cursor.getString(cursor.getColumnIndex("password"));
            adress = cursor.getString(cursor.getColumnIndex("adress"));
            email = cursor.getString(cursor.getColumnIndex("email"));
            date = String.valueOf(cursor.getLong(cursor.getColumnIndex("date")));
            cpfOrCnpj = cursor.getString(cursor.getColumnIndex("cpfOrCnpj"));
            gender = cursor.getString(cursor.getColumnIndex("gender"));
            picture = cursor.getString(cursor.getColumnIndex("picture"));

            cliente.setCodeId(codigo);
            cliente.setName(nome);
            cliente.setUserName(userName);
            cliente.setPassword(password);
            cliente.setAdress(adress);
            cliente.setEmail(email);
            cliente.setDate(Long.parseLong(date));
            cliente.setCpfOrCnpj(cpfOrCnpj);
            cliente.setGender(gender);
            cliente.setPicture(picture);

            listClientes.add(cliente);
        }
        return listClientes;
    }

    @SuppressLint("Range")
    public ClienteEntity getClienteById(long clienteId) {
        String sqlSelect = "SELECT * FROM " + SQLite.TABELA_CLIENTE + " WHERE cliCodigo = ?;";
        String[] selectionArgs = {String.valueOf(clienteId)};

        Cursor cursor = sqlRead.rawQuery(sqlSelect, selectionArgs);

        ClienteEntity cliente = null;

        if (cursor != null && cursor.moveToFirst()) {
            cliente = new ClienteEntity();
            cliente.setCodeId(cursor.getLong(cursor.getColumnIndexOrThrow("cliCodigo")));
            cliente.setName(cursor.getString(cursor.getColumnIndex("clienteNome")));
            cliente.setUserName(cursor.getString(cursor.getColumnIndex("clienteUserName")));
            cliente.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            cliente.setAdress(cursor.getString(cursor.getColumnIndex("adress")));
            cliente.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            cliente.setDate(cursor.getLong(cursor.getColumnIndex("date")));
            cliente.setCpfOrCnpj(cursor.getString(cursor.getColumnIndex("cpfOrCnpj")));
            cliente.setPicture(cursor.getString(cursor.getColumnIndex("picture")));
            cursor.close();
        }
        return cliente;
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
