package com.eduardo.cadastro.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.eduardo.cadastro.R;
import com.eduardo.cadastro.model.ClienteEntity;
import com.eduardo.cadastro.model.database.local.Dao;

public class EditClientActivity extends AppCompatActivity {

    private EditText editTextNome, editTextUserName, editTextPassword, editTextAdress, editTextEmail;
    private Button btnSave;
    private ClienteEntity detalhes = new ClienteEntity();

    public void initViews(){
        editTextNome = findViewById(R.id.idNome);
        editTextUserName = findViewById(R.id.idUserName);
        editTextPassword = findViewById(R.id.idPassword);
        editTextAdress = findViewById(R.id.idAdress);
        editTextEmail = findViewById(R.id.idEmail);
        btnSave = findViewById(R.id.idBtnSave);
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
            editTextPassword.setText(detalhes.getPassword());
            editTextAdress.setText(detalhes.getAdress());
            editTextEmail.setText(detalhes.getEmail());
        }else{
            Toast.makeText(EditClientActivity.this,
                    "Vazio",Toast.LENGTH_LONG).show();
        }

    }

    public void alterarCliente(View view) {
        String setNewNameClient, setNewUserNameClient, setNewPassword, setNewAdress, setNewEmail;
        setNewNameClient = editTextNome.getText().toString();
        setNewUserNameClient = editTextUserName.getText().toString();
        setNewPassword = editTextPassword.getText().toString();
        setNewAdress = editTextAdress.getText().toString();
        setNewEmail = editTextEmail.getText().toString();

        Dao dadoAlterado = new Dao(getBaseContext());
        ClienteEntity setAlterar = new ClienteEntity();

        setAlterar.setCodeId(detalhes.getCodeId());
        setAlterar.setName(setNewNameClient);
        setAlterar.setUserName(setNewUserNameClient);
        setAlterar.setPassword(setNewPassword);
        setAlterar.setAdress(setNewAdress);
        setAlterar.setEmail(setNewEmail);

        Boolean resultado = dadoAlterado.alterarCliente(setAlterar);

        if (resultado) {
            Toast.makeText(EditClientActivity.this, "Dados atualizados com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(EditClientActivity.this, "Erro ao atualizar dados!", Toast.LENGTH_LONG).show();
        }

    }
}