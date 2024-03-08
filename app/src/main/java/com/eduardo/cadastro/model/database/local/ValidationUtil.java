package com.eduardo.cadastro.model.database.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.eduardo.cadastro.model.ClienteEntity;
import java.util.regex.Pattern;

public class ValidationUtil {

    // Validador de email
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Validador nome
    public static boolean isValidNome(String nome) {
        return nome != null && nome.length() <= 15 && nome.matches("[a-zA-Z]+");
    }

    // Vertifica se userName já existe
    public static boolean isValidUsername(String username, SQLiteDatabase sqlRead) {
        String sqlSelect = "select * from " + SQLite.TABELA_CLIENTE + " where clienteUserName = ?";
        Cursor cursor = sqlRead.rawQuery(sqlSelect, new String[]{username});
        boolean usernameExists = cursor.getCount() > 0;
        cursor.close();
        return usernameExists;
    }

    // Verifica se é maior do que 18 anos
    public static boolean isClienteMaiorIdade(ClienteEntity mCliente) {
        int idade = mCliente.calculateAge();
        return idade >= 18;
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*\\d.*") && password.matches(".*[A-Z].*");
    }

}

