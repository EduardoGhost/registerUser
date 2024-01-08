package com.eduardo.cadastro.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import com.eduardo.cadastro.R;
import com.eduardo.cadastro.model.ClienteEntity;
import com.eduardo.cadastro.model.database.local.Dao;

public class EditClientActivity extends AppCompatActivity {

    private EditText editTextNome, editTextUserName;
    private ClienteEntity detalhes = new ClienteEntity();

    public void initViews(){
        editTextNome = findViewById(R.id.idNome);
        editTextUserName = findViewById(R.id.idUserName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        detalhes = (ClienteEntity) getIntent().getSerializableExtra("keyEdit");

        if (detalhes != null) {
            editTextNome.setText(detalhes.getName());
            editTextUserName.setText(detalhes.getUserName());
        }else{
            Toast.makeText(EditClientActivity.this,
                    "Vazio",Toast.LENGTH_LONG).show();
        }

    }

    //colocar em um botao
    public void alterarCliente() {
        String setNewNameClient, setNewUserNameClient;
        setNewNameClient = editTextNome.getText().toString();
        setNewUserNameClient = editTextUserName.getText().toString();

        Dao dadoAlterado = new Dao(getBaseContext());
        ClienteEntity setAlterar = new ClienteEntity();

        setAlterar.setName(setNewNameClient);
        setAlterar.setUserName(setNewUserNameClient);

        Boolean resultado = dadoAlterado.alterarClient(setAlterar);
        System.out.println("Resultado Alterado: " + resultado + " NomeAltered: " + setAlterar.getName()
                + " UserNameAltered: " + setAlterar.getUserName());

        Toast.makeText(EditClientActivity.this, "Dados atualizado com sucesso!", Toast.LENGTH_LONG).show();
        //clearFields();


    }

    //criar função, esta sendo usada tmb no Dao
    public void clearFields(){
        editTextNome.setText("");
        editTextUserName.setText("");
    }


}