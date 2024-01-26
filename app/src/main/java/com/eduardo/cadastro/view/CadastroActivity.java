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
import com.vicmikhailau.maskededittext.MaskedFormatter;
import com.vicmikhailau.maskededittext.MaskedWatcher;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

        private CadastroViewModel cadastroViewModel;
        private EditText editTextName, editTextUserName, editTextPassword, editAdress, editTextEmail,  editTextDate, editTextCpfOrCnpj;
        private Button botaoAdicionarCliente;
        private RadioGroup radioGroup, radioGroupGender;
        private RadioButton radioCPF, radioCNPJ, radioMasculino, radioFeminino;

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

            editTextCpfOrCnpj = findViewById(R.id.editTextCpfOrCnpj);

            //criar metodo separado para evitar a dublicação
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (checkedId == R.id.radioCPF) {
                    // CPF foi selecionado
                    editTextCpfOrCnpj.setHint("CPF");
                    setMaskCpf(); // Se necessário, aplique a máscara de CPF
                } else if (checkedId == R.id.radioCNPJ) {
                    // CNPJ foi selecionado
                    editTextCpfOrCnpj.setHint("CNPJ");
                    setMaskCnpj(); // Se necessário, aplique a máscara de CNPJ
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
            radioGroupGender = findViewById(R.id.radioGroupGender);
            botaoAdicionarCliente = findViewById(R.id.idBtnCadastro);
            radioGroup = findViewById(R.id.radioGroup);
            radioCPF = findViewById(R.id.radioCPF);
            radioCNPJ = findViewById(R.id.radioCNPJ);
            editTextCpfOrCnpj = findViewById(R.id.editTextCpfOrCnpj);

            // Configurar o RadioGroup para inicialmente desmarcar todos os RadioButtons
            radioGroup.clearCheck();

            // Configurar o ouvinte de alteração no RadioGroup
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (checkedId == R.id.radioCPF) {
                    // CPF foi selecionado
                    editTextCpfOrCnpj.setHint("CPF");
                    setMaskCpf(); // Se necessário, aplique a máscara de CPF
                } else if (checkedId == R.id.radioCNPJ) {
                    // CNPJ foi selecionado
                    editTextCpfOrCnpj.setHint("CNPJ");
                    setMaskCnpj(); // Se necessário, aplique a máscara de CNPJ
                }
            });
            radioGroupGender.clearCheck();
            radioGroupGender = findViewById(R.id.radioGroupGender);
            radioMasculino = findViewById(R.id.radioMasculine);
            radioFeminino = findViewById(R.id.radioFeminine);
        }

    public void cadastroCliente(View view) {
        String genderSelect = getGenderSelect();

        Dao dao = new Dao(getBaseContext());
        ClienteEntity setCliente = new ClienteEntity();

        // Obtém o CPF digitado e aplica a desmascaração
        String cpfOrCnpj = editTextCpfOrCnpj.getText().toString();
        String cpfUnmasked = unmask(cpfOrCnpj);

        setCliente.setName(getText(editTextName));
        setCliente.setUserName(getText(editTextUserName));
        setCliente.setPassword(getText(editTextPassword));
        setCliente.setAdress(getText(editAdress));
        setCliente.setEmail(getText(editTextEmail));
        setCliente.setDate(getTimestampFromDateString(getText(editTextDate)));
        setCliente.setCpfOrCnpj(cpfUnmasked);
        setCliente.setGender(genderSelect);

        // Chame o método da ViewModel para cadastrar o cliente
        cadastroViewModel.cadastrarCliente(dao, setCliente);

        String formattedDate = formatDateFromTimestamp(setCliente.getDate());
            Log.i("Resultado: ",  " Nome: " + setCliente.getName() + " UserName: "
                    + setCliente.getUserName() + " Senha: " + setCliente.getPassword() + " Endereço: " + setCliente.getAdress()
                    + " Email: " + setCliente.getEmail() + " Data de Nascimento: " + formattedDate + " Documento: " + setCliente.getCpfOrCnpj()
                    + " Genero: " + setCliente.getGender());
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
            editTextCpfOrCnpj.setText("");
        }

    // Método para definir a máscara do CPF
    private void setMaskCpf() {
        MaskedFormatter formatter = new MaskedFormatter("###.###.###-##");
        editTextCpfOrCnpj.addTextChangedListener(new MaskedWatcher(formatter, editTextCpfOrCnpj));
    }
    // Método para remover a máscara do CPF (retorna apenas os números)
    private String unmask(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }

    // Método para definir a máscara do CNPJ
    private void setMaskCnpj() {
        MaskedFormatter formatter = new MaskedFormatter("##.###.###/####-##");
        editTextCpfOrCnpj.addTextChangedListener(new MaskedWatcher(formatter, editTextCpfOrCnpj));
    }

    private String getGenderSelect() {
        int radioButtonId = radioGroupGender.getCheckedRadioButtonId();
        switch (radioButtonId) {
            case R.id.radioMasculine:
                return "Masculino";
            case R.id.radioFeminine:
                return "Feminino";
            default:
                return "Outro";
        }
        }
}
