package com.eduardo.cadastro.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.eduardo.cadastro.R;
import com.eduardo.cadastro.model.ClienteEntity;
import com.eduardo.cadastro.model.database.local.Dao;

public class CadastroActivity extends AppCompatActivity {

        private EditText editTextName;
        private EditText editTextUserName;
        private Button botaoAdicionarCliente;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cadastro);
            inicializarComponentes();
        }

        public void inicializarComponentes() {
            editTextName = findViewById(R.id.editTextName);
            editTextUserName = findViewById(R.id.editTextUserName);
            botaoAdicionarCliente = findViewById(R.id.idBtnCadastro);
        }

        public void cadastroCliente(View view) {
            Dao escreverCliente = new Dao(getBaseContext());
            ClienteEntity setCliente = new ClienteEntity();

            setCliente.setName(obterTexto(editTextName));
            setCliente.setUserName(obterTexto(editTextUserName));

            boolean resultado = escreverCliente.cadastroCliente(setCliente);
            System.out.println("Resultado: " + resultado + " Nome: " + setCliente.getName() + " UserName: " + setCliente.getUserName());
        }

        private String obterTexto(EditText editText) {
            return editText.getText().toString();
        }
    }
