package com.eduardo.cadastro.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.eduardo.cadastro.R;
import com.eduardo.cadastro.model.ClienteEntity;
import com.eduardo.cadastro.model.database.local.Dao;

public class CadastroActivity extends AppCompatActivity {

        private EditText editTextName, editTextUserName, editTextPassword, editAdress, editTextEmail;
        private Button botaoAdicionarCliente;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cadastro);
            initializeComponents();
        }

        public void initializeComponents() {
            editTextName = findViewById(R.id.editTextName);
            editTextUserName = findViewById(R.id.editTextUserName);
            editTextPassword = findViewById(R.id.editTextPassword);
            editAdress = findViewById(R.id.editTextAdress);
            editTextEmail = findViewById(R.id.editTextEmail);
            botaoAdicionarCliente = findViewById(R.id.idBtnCadastro);
        }

        public void cadastroCliente(View view) {
            Dao escreverCliente = new Dao(getBaseContext());
            ClienteEntity setCliente = new ClienteEntity();

            setCliente.setName(getText(editTextName));
            setCliente.setUserName(getText(editTextUserName));
            setCliente.setPassword(getText(editTextPassword));
            setCliente.setAdress(getText(editAdress));
            setCliente.setEmail(getText(editTextEmail));

            boolean resultado = escreverCliente.cadastroCliente(setCliente);

            if (resultado) {
                Log.i("Resultado: ",resultado + " Nome: " + setCliente.getName() + " UserName: "
                        + setCliente.getUserName() + " Senha: " + setCliente.getPassword() + " Endereço: " + setCliente.getAdress()
                + " Email: " + setCliente.getEmail()
                );
                clearFields();
            }
        }

        private String getText(EditText editText) {
            return editText.getText().toString();
        }

        public void clearFields(){
            editTextName.setText("");
            editTextUserName.setText("");
            editTextPassword.setText("");
            editAdress.setText("");
            editTextEmail.setText("");
        }
}
