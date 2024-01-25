package com.eduardo.cadastro.model.database.local;

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
import java.util.regex.Pattern;

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

        //validador de email
        if (isValidEmail(mCliente.getEmail())) {

        //verifica se o nome tem mais de 15 caracteres
        if (mCliente.getName().length() <= 15) {
            showToast("Nome deve ter mais de 15 caracteres.");
            return false;
        }

        //vertifica se userName já existe
        if (checkIfUsernameExists(mCliente.getUserName())) {
            showToast("Username já está em uso. Escolha outro.");
            return false;
        }

        //verifica se é maior do que 18 anos
        int idade = mCliente.calculateAge();
        if (idade < 18) {
            showToast("É necessário ter mais de 18 anos para se cadastrar.");
            return false;
            }

        if (isPasswordValid(senha)) {
            ContentValues values = new ContentValues();
            values.put("clienteNome", mCliente.getName());
            values.put("clienteUserName", mCliente.getUserName());
            values.put("password", senha);
            values.put("adress", mCliente.getAdress());
            values.put("email", mCliente.getEmail());
            values.put("date", mCliente.getDate());
            values.put("cpfOrCnpj", mCliente.getCpfOrCnpj());

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
            showToast("Email inválido. Insira um email válido.");
            return false;
        }
    }

    @Override
    public boolean alterarCliente(ClienteEntity mCliente) {
        String senha = mCliente.getPassword();

        //validador de email
        if (isValidEmail(mCliente.getEmail())) {

        //verifica se o nome tem mais de 15 caracteres
        if (mCliente.getName().length() <= 15) {
            showToast("Nome deve ter mais de 15 caracteres.");
            return false;
        }
            //verifica se é maior do que 18 anos
            int idade = mCliente.calculateAge();
            if (idade < 18) {
                showToast("É necessário ter mais de 18 anos para se cadastrar.");
                return false;
            }

        if (isPasswordValid(senha)) {

        ContentValues values = new ContentValues();
        values.put("clienteNome", mCliente.getName());
        values.put("clienteUserName", mCliente.getUserName());
        values.put("password", mCliente.getPassword());
        values.put("adress", mCliente.getAdress());
        values.put("email", mCliente.getEmail());
        values.put("date", mCliente.getDate());
        values.put("cpfOrCnpj", mCliente.getCpfOrCnpj());

            try {
            String[] id = {String.valueOf(mCliente.getCodeId())};
            sqlWrite.update(SQLite.TABELA_CLIENTE, values, "cliCodigo = ?", id);
            sqlWrite.close();

            return true;
        }catch (Exception e){
            Log.i("Informação: ","Erro ao atualizar dados: "+e.getMessage());
            return false;
        }
        }
         else {
                showToast("Senha inválida. Deve ter pelo menos 8 caracteres, um número e uma letra maiúscula.");
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

            String nome, userName, password, adress, email, date, cpfOrCnpj;
            nome = cursor.getString(cursor.getColumnIndex("clienteNome"));
            userName = cursor.getString(cursor.getColumnIndex("clienteUserName"));
            password = cursor.getString(cursor.getColumnIndex("password"));
            adress = cursor.getString(cursor.getColumnIndex("adress"));
            email = cursor.getString(cursor.getColumnIndex("email"));
            date = String.valueOf(cursor.getLong(cursor.getColumnIndex("date")));
            cpfOrCnpj = cursor.getString(cursor.getColumnIndex("cpfOrCnpj"));

            cliente.setCodeId(codigo);
            cliente.setName(nome);
            cliente.setUserName(userName);
            cliente.setPassword(password);
            cliente.setAdress(adress);
            cliente.setEmail(email);
            cliente.setDate(Long.parseLong(date));
            cliente.setCpfOrCnpj(cpfOrCnpj);

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
            cliente.setEmail(cursor.getString(cursor.getColumnIndex("cpfOrCnpj")));
            cursor.close();
        }
        return cliente;
    }

    //metodo para checkar o userName existente
    private boolean checkIfUsernameExists(String username) {
        String sqlSelect = "select * from " + SQLite.TABELA_CLIENTE + " where clienteUserName = ?";
        Cursor cursor = sqlRead.rawQuery(sqlSelect, new String[]{username});
        boolean usernameExists = cursor.getCount() > 0;
        cursor.close();
        return usernameExists;
    }

    //metodo para validar senha
    public boolean isPasswordValid(String password) {
        return password.length() >= 8 && password.matches(".*\\d.*") && password.matches(".*[A-Z].*");
    }

    //metodo para validar email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
