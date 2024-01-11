package com.eduardo.cadastro.model.database.local;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
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

    public boolean cadastroCliente(ClienteEntity mCliente) {
        if (!validateCliente(mCliente)) {
            return false;
        }

        ContentValues values = createContentValues(mCliente);
        return databaseOperation(values, null, null);
    }

    private boolean validateCliente(ClienteEntity mCliente) {
        String senha = mCliente.getPassword();

        if (mCliente.getName().length() <= 15) {
            showToast("Nome deve ter mais de 15 caracteres.");
            return false;
        }

        if (checkIfUsernameExists(mCliente.getUserName())) {
            showToast("Username já está em uso. Escolha outro.");
            return false;
        }

        if (!isPasswordValid(senha)) {
            showToast("Senha inválida. Deve ter pelo menos 8 caracteres, um número e uma letra maiúscula.");
            return false;
        }

        return true;
    }

    @Override
    public boolean alterarCliente(ClienteEntity mCliente) {
        if (!validateCliente(mCliente)) {
            return false;
        }
        ContentValues values = createContentValues(mCliente);
        String[] id = {String.valueOf(mCliente.getCodeId())};

        return databaseOperation(values, "cliCodigo = ?", id);
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

            String nome, userName, password;
            nome = cursor.getString(cursor.getColumnIndex("clienteNome"));
            userName = cursor.getString(cursor.getColumnIndex("clienteUserName"));
            password = cursor.getString(cursor.getColumnIndex("password"));

            cliente.setCodeId(codigo);
            cliente.setName(nome);
            cliente.setUserName(userName);
            cliente.setPassword(password);

            listClientes.add(cliente);
        }
        return listClientes;
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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

    //escreve dado no banco
    private boolean databaseOperation(ContentValues values, String whereClause, String[] whereArgs) {
        try {
            sqlWrite.insertOrThrow(SQLite.TABELA_CLIENTE, null, values);
            showToast("Cliente cadastrado com sucesso!");
            return true;
        } catch (SQLiteConstraintException e) {
            showToast("Erro ao cadastrar cliente. Nome ou Username já em uso.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Erro ao cadastrar cliente.");
            return false;
        }
    }
    /**
     * Cria um objeto ContentValues preenchido com os valores do ClienteEntity para inserção no banco de dados.
     *
     * @param mCliente Objeto ClienteEntity contendo os dados a serem inseridos no banco de dados.
     * @return Objeto ContentValues preenchido.
     */
    private ContentValues createContentValues(ClienteEntity mCliente) {
        ContentValues values = new ContentValues();
        values.put("clienteNome", mCliente.getName());
        values.put("clienteUserName", mCliente.getUserName());
        values.put("password", mCliente.getPassword());
        return values;
    }
}
