package com.eduardo.cadastro.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.eduardo.cadastro.R;
import com.eduardo.cadastro.model.ClienteEntity;
import com.eduardo.cadastro.model.database.Dao;

public class CadastroActivity extends AppCompatActivity {

    private EditText editText;
    private Button botaoAdicionarCliente;

    public void inicializarComponentes(){
        editText = (EditText)findViewById(R.id.editTextExample);
        botaoAdicionarCliente = (Button)findViewById(R.id.idBtnCadastro);
    }

    public void cadastroCliente(View view) {

        Dao escreverCliente = new Dao(getBaseContext());
        ClienteEntity setCliente = new ClienteEntity();

        String nome;
        nome = editText.getText().toString();
        setCliente.setName(nome);

        boolean resultado;
        resultado = escreverCliente.cadastroCliente(setCliente);
        System.out.println("Nome "+ resultado + nome);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editText = findViewById(R.id.editTextExample);
        inicializarComponentes();

    }
}