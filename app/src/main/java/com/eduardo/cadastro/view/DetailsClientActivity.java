package com.eduardo.cadastro.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.eduardo.cadastro.R;
import com.eduardo.cadastro.model.ClienteEntity;
import com.eduardo.cadastro.utils.DateUtils;

public class DetailsClientActivity extends AppCompatActivity {

    private TextView textNome, textUserName, textAdress, textEmail, textDate;
    private ClienteEntity detalhes = new ClienteEntity();

    public void initViews(){
        textNome = findViewById(R.id.txtNome);
        textUserName = findViewById(R.id.txtUserName);
        textAdress = findViewById(R.id.txtAdress);
        textEmail = findViewById(R.id.txtEmail);
        textDate = findViewById(R.id.txtDate);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_client);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        detalhes = (ClienteEntity) getIntent().getSerializableExtra("keyDetails");

        if (detalhes != null) {
           textNome.setText(detalhes.getName());
           textUserName.setText(detalhes.getUserName());
           textAdress.setText(detalhes.getAdress());
           textEmail.setText(detalhes.getEmail());

           long timestamp = detalhes.getDate();
           String formattedDate = DateUtils.formatDateFromTimestamp(timestamp);
           textDate.setText(formattedDate);
        }else{
            Toast.makeText(DetailsClientActivity.this,
                    "Vazio",Toast.LENGTH_LONG).show();
        }
    }
}