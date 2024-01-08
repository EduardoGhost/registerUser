package com.eduardo.cadastro.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eduardo.cadastro.R;
import com.eduardo.cadastro.model.ClienteEntity;

public class DetailsClientActivity extends AppCompatActivity {

    private TextView textNome, textUserName;
    private ClienteEntity detalhes = new ClienteEntity();

    public void initViews(){
        textNome = findViewById(R.id.txtNome);
        textUserName = findViewById(R.id.txtUserName);
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
        }else{
            Toast.makeText(DetailsClientActivity.this,
                    "Vazio",Toast.LENGTH_LONG).show();
        }
    }
}