package com.eduardo.cadastro.view;

import static com.eduardo.cadastro.utils.DateUtils.getTimestampFromDateString;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.eduardo.cadastro.R;
import com.eduardo.cadastro.model.ClienteEntity;
import com.eduardo.cadastro.model.database.local.Dao;
import com.eduardo.cadastro.utils.DateUtils;
import com.eduardo.cadastro.viewmodel.EditViewModel;

public class EditClientActivity extends AppCompatActivity {

    private EditText editTextNome, editTextUserName, editTextPassword, editTextAdress, editTextEmail, editTextDate, editTextCpfOrCnpj;
    private Button btnSave;
    private EditViewModel editViewModel;
    private ClienteEntity detalhes = new ClienteEntity();

    public void initViews(){
        editTextNome = findViewById(R.id.idNome);
        editTextUserName = findViewById(R.id.idUserName);
        editTextPassword = findViewById(R.id.idPassword);
        editTextAdress = findViewById(R.id.idAdress);
        editTextEmail = findViewById(R.id.idEmail);
        editTextDate = findViewById(R.id.idDate);
        editTextCpfOrCnpj = findViewById(R.id.idCpfOrCnpj);
        btnSave = findViewById(R.id.idBtnSave);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inicialize o EditViewModel
        editViewModel = new ViewModelProvider(this).get(EditViewModel.class);

        // Observa o resultado da edição e mensagens de erro
        editViewModel.getEditResult().observe(this, editResult -> {
            if (editResult) {
                showToast("Dados atualizados com sucesso!");
                finish();
            }
        });

        editViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (!errorMessage.isEmpty()) {
                showToast(errorMessage);
            }
        });

        initViews();
        detalhes = (ClienteEntity) getIntent().getSerializableExtra("keyEdit");

        if (detalhes != null) {
            editTextNome.setText(detalhes.getName());
            editTextUserName.setText(detalhes.getUserName());
            editTextPassword.setText(detalhes.getPassword());
            editTextAdress.setText(detalhes.getAdress());
            editTextEmail.setText(detalhes.getEmail());
            long timestamp = detalhes.getDate();
            String formattedDate = DateUtils.formatDateFromTimestamp(timestamp);
            editTextDate.setText(formattedDate);
            editTextCpfOrCnpj.setText(detalhes.getCpfOrCnpj());

//            String cpfOrCnpj = detalhes.getCpfOrCnpj();
//            if (MaskUtils.isCpf(cpfOrCnpj)) {
//                editTextCpfOrCnpj.setText(MaskUtils.formatCpf(cpfOrCnpj));
//            } else {
//                editTextCpfOrCnpj.setText(MaskUtils.formatCnpj(cpfOrCnpj));
//            }

        }else{
            Toast.makeText(EditClientActivity.this,
                    "Vazio",Toast.LENGTH_LONG).show();
        }

    }

    public void alterarCliente(View view) {

        String setNewNameClient, setNewUserNameClient, setNewPassword, setNewAdress, setNewEmail,  setNewDate, setNewCpfOrCnpj;
        setNewNameClient = editTextNome.getText().toString();
        setNewUserNameClient = editTextUserName.getText().toString();
        setNewPassword = editTextPassword.getText().toString();
        setNewAdress = editTextAdress.getText().toString();
        setNewEmail = editTextEmail.getText().toString();
        setNewDate = editTextDate.getText().toString();
        setNewCpfOrCnpj = editTextCpfOrCnpj.getText().toString();

        Dao dao = new Dao(getBaseContext());
        ClienteEntity setAlterar = new ClienteEntity();

        setAlterar.setCodeId(detalhes.getCodeId());
        setAlterar.setName(setNewNameClient);
        setAlterar.setUserName(setNewUserNameClient);
        setAlterar.setPassword(setNewPassword);
        setAlterar.setAdress(setNewAdress);
        setAlterar.setEmail(setNewEmail);
        setAlterar.setDate(getTimestampFromDateString(setNewDate));
        setAlterar.setCpfOrCnpj(setNewCpfOrCnpj);

        editViewModel.alterarCliente(new Dao(getBaseContext()), setAlterar);


        Boolean resultado = dao.alterarCliente(setAlterar);

        if (resultado) {
            Toast.makeText(EditClientActivity.this, "Dados atualizados com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(EditClientActivity.this, "Erro ao atualizar dados!", Toast.LENGTH_LONG).show();
        }

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}