package com.eduardo.cadastro.view;

import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.eduardo.cadastro.R;
import com.eduardo.cadastro.model.ClienteEntity;
import com.eduardo.cadastro.model.database.local.Dao;
import com.eduardo.cadastro.utils.DateUtils;
import com.eduardo.cadastro.utils.MaskUtils;
import com.eduardo.cadastro.viewmodel.DetailViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.io.File;

public class DetailsClientActivity extends AppCompatActivity {

    private TextView textNome, textUserName, textAdress, textEmail, textDate, textCpfOrCnpj, textGender;
    private DetailViewModel detailViewModel;
    private ImageView imageViewPhoto;
    private static final int REQUEST_STORAGE_PERMISSION = 1;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1;
    private ClienteEntity detalhes = new ClienteEntity();

    public void initViews(){
        textNome = findViewById(R.id.txtNome);
        textUserName = findViewById(R.id.txtUserName);
        textAdress = findViewById(R.id.txtAdress);
        textEmail = findViewById(R.id.txtEmail);
        textDate = findViewById(R.id.txtDate);
        textCpfOrCnpj = findViewById(R.id.txtCpfOrCnpj);
        textGender = findViewById(R.id.txtGender);
        imageViewPhoto = findViewById(R.id.imageViewDetails);
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
        textGender.setText(detalhes.getGender());

        // Verifica se a permissão de leitura externa não foi concedida
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        } else {
            loadImageView(detalhes);
        }
    }

    private void loadImageView(ClienteEntity detalhes) {
        // Verificar se a permissão já foi concedida
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            String imagePath = detalhes.getPicture();
            if (imagePath != null && !imagePath.isEmpty()) {
                // Use Picasso para carregar a imagem
                Picasso.get().load(new File(imagePath)).into(imageViewPhoto, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(DetailsClientActivity.this, "Erro ao carregar a imagem", Toast.LENGTH_SHORT).show();
                        Log.e("Picasso", "erro", e);
                        e.printStackTrace();
                    }
                });
            } else {
                Toast.makeText(DetailsClientActivity.this, "Sem imagem disponível", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Permissão não concedida, solicite permissão
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

}