package com.eduardo.cadastro.view;

import static com.eduardo.cadastro.utils.DateUtils.formatDateFromTimestamp;
import static com.eduardo.cadastro.utils.DateUtils.getTimestampFromDateString;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.eduardo.cadastro.R;
import com.eduardo.cadastro.model.ClienteEntity;
import com.eduardo.cadastro.model.database.local.Dao;
import com.eduardo.cadastro.viewmodel.CadastroViewModel;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

        private CadastroViewModel cadastroViewModel;

        private EditText editTextName, editTextUserName, editTextPassword, editAdress, editTextEmail,  editTextDate;
        private Button botaoAdicionarCliente;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cadastro);
            initializeComponents();

            cadastroViewModel = new ViewModelProvider(this).get(CadastroViewModel.class);

            cadastroViewModel.getCadastroResult().observe(this, resultado -> {
                if (resultado) {
                    // Sucesso no cadastro
                    showToast("Cliente cadastrado com sucesso!");
                    clearFields();
                }
            });

            // Observa mensagens de erro
            cadastroViewModel.getErrorMessage().observe(this, errorMessage -> {
                if (!errorMessage.isEmpty()) {
                    showToast(errorMessage);
                }
            });

        }

        public void initializeComponents() {
            editTextName = findViewById(R.id.editTextName);
            editTextUserName = findViewById(R.id.editTextUserName);
            editTextPassword = findViewById(R.id.editTextPassword);
            editAdress = findViewById(R.id.editTextAdress);
            editTextEmail = findViewById(R.id.editTextEmail);
            editTextDate = findViewById(R.id.editTextDate);
            botaoAdicionarCliente = findViewById(R.id.idBtnCadastro);
        }

    public void cadastroCliente(View view) {
        Dao dao = new Dao(getBaseContext());
        ClienteEntity setCliente = new ClienteEntity();

        setCliente.setName(getText(editTextName));
        setCliente.setUserName(getText(editTextUserName));
        setCliente.setPassword(getText(editTextPassword));
        setCliente.setAdress(getText(editAdress));
        setCliente.setEmail(getText(editTextEmail));
        setCliente.setDate(getTimestampFromDateString(getText(editTextDate)));

        // Chame o método da ViewModel para cadastrar o cliente
        cadastroViewModel.cadastrarCliente(dao, setCliente);

        //Log de dados
        String formattedDate = formatDateFromTimestamp(setCliente.getDate());

            Log.i("Resultado: ",  " Nome: " + setCliente.getName() + " UserName: "
                    + setCliente.getUserName() + " Senha: " + setCliente.getPassword() + " Endereço: " + setCliente.getAdress()
                    + " Email: " + setCliente.getEmail() + " Data de Nascimento: " + formattedDate);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
            editTextDate.setText("");
        }
}
