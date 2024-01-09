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

    private EditText editTextNome, editTextUserName;
    private Button btnSave;
    private ClienteEntity detalhes = new ClienteEntity();

    public void initViews(){
        editTextNome = findViewById(R.id.idNome);
        editTextUserName = findViewById(R.id.idUserName);
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
        }else{
            Toast.makeText(EditClientActivity.this,
                    "Vazio",Toast.LENGTH_LONG).show();
        }

    }

    public void alterarCliente(View view) {
        String setNewNameClient, setNewUserNameClient;
        setNewNameClient = editTextNome.getText().toString();
        setNewUserNameClient = editTextUserName.getText().toString();

        Dao dadoAlterado = new Dao(getBaseContext());
        ClienteEntity setAlterar = new ClienteEntity();

        setAlterar.setCodeId(detalhes.getCodeId());
        setAlterar.setName(setNewNameClient);
        setAlterar.setUserName(setNewUserNameClient);

        Boolean resultado = dadoAlterado.alterarClient(setAlterar);

        if (resultado) {
            Toast.makeText(EditClientActivity.this, "Dados atualizados com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(EditClientActivity.this, "Erro ao atualizar dados!", Toast.LENGTH_LONG).show();
        }

    }
}