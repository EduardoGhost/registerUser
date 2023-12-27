package com.eduardo.cadastro.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.eduardo.cadastro.R;
import com.eduardo.cadastro.model.ClienteEntity;
import com.eduardo.cadastro.model.database.local.Dao;
import com.eduardo.cadastro.view.adapter.Adapter;

import java.util.ArrayList;
import java.util.List;

public class listClientActivity extends AppCompatActivity {

    private List<ClienteEntity> listClientes = new ArrayList<>();
    private RecyclerView recyclerViewClientes;
    private Adapter adapter;

    public void initViews() {
        recyclerViewClientes = (RecyclerView) findViewById(R.id.idRecyclerViewListedClients);
    }
    public void loadListclients() {

        Dao daoCliente = new Dao(getBaseContext());
        listClientes = daoCliente.listClientes();
        adapter = new Adapter(listClientes, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewClientes.setLayoutManager(layoutManager);
        recyclerViewClientes.setHasFixedSize(true);
        recyclerViewClientes.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerViewClientes.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_client);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();

    }
    @Override
    protected void onStart() {
        loadListclients();
        super.onStart();
    }

}