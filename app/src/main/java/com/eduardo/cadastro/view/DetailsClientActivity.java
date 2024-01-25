package com.eduardo.cadastro.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.eduardo.cadastro.R;
import com.eduardo.cadastro.model.ClienteEntity;
import com.eduardo.cadastro.model.database.local.Dao;
import com.eduardo.cadastro.utils.DateUtils;
import com.eduardo.cadastro.utils.MaskUtils;
import com.eduardo.cadastro.viewmodel.DetailViewModel;

public class DetailsClientActivity extends AppCompatActivity {

    private TextView textNome, textUserName, textAdress, textEmail, textDate, textCpfOrCnpj;
    private DetailViewModel detailViewModel;
    private ClienteEntity detalhes = new ClienteEntity();

    public void initViews(){
        textNome = findViewById(R.id.txtNome);
        textUserName = findViewById(R.id.txtUserName);
        textAdress = findViewById(R.id.txtAdress);
        textEmail = findViewById(R.id.txtEmail);
        textDate = findViewById(R.id.txtDate);
        textCpfOrCnpj = findViewById(R.id.txtCpfOrCnpj);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_client);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        detailViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(DetailViewModel.class);

        initViews();

        detalhes = (ClienteEntity) getIntent().getSerializableExtra("keyDetails");

        if (detalhes != null) {
            updateUIWithDetails(detalhes);

            //viewModel
            detailViewModel.detailCliente(new Dao(getBaseContext()), detalhes.getCodeId());
        } else {
            Toast.makeText(DetailsClientActivity.this, "Vazio", Toast.LENGTH_LONG).show();
        }

        detailViewModel.getDetailResult().observe(this, detailResult -> {
            if (detailResult) {
                // Lógica para lidar com os detalhes obtidos com sucesso
                Toast.makeText(DetailsClientActivity.this, "Detalhes obtidos com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });

        detailViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (!errorMessage.isEmpty()) {
                // Lógica para lidar com mensagens de erro
                Toast.makeText(DetailsClientActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    // Interface com os detalhes
    private void updateUIWithDetails(ClienteEntity detalhes) {
        textNome.setText(detalhes.getName());
        textUserName.setText(detalhes.getUserName());
        textAdress.setText(detalhes.getAdress());
        textEmail.setText(detalhes.getEmail());

        long timestamp = detalhes.getDate();
        String formattedDate = DateUtils.formatDateFromTimestamp(timestamp);
        textDate.setText(formattedDate);

        String cpfOrCnpj = detalhes.getCpfOrCnpj();
        if (MaskUtils.isCpf(cpfOrCnpj)) {
            textCpfOrCnpj.setText(MaskUtils.formatCpf(cpfOrCnpj));
        } else {
            textCpfOrCnpj.setText(MaskUtils.formatCnpj(cpfOrCnpj));
        }

    }
}