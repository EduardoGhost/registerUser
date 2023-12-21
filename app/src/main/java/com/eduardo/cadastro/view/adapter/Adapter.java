package com.eduardo.cadastro.view.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eduardo.cadastro.R;
import com.eduardo.cadastro.model.ClienteEntity;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ClienteViewHolder> {

    private List<ClienteEntity> listClientes;
    private Context context;

    public Adapter(List<ClienteEntity> listClientes, Context context) {
        this.listClientes = listClientes;
        this.context = context;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View listaDeClientes = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.adapter, viewGroup, false);
        return new ClienteViewHolder(listaDeClientes);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder,  int i) {
        ClienteEntity cliente = listClientes.get(i);
        holder.nome.setText(cliente.getName());

    }

    @Override
    public int getItemCount() {
        return listClientes.size();
    }

    public class ClienteViewHolder extends RecyclerView.ViewHolder {
        Context activity;
        TextView nome;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            activity = itemView.getContext();
            nome = itemView.findViewById(R.id.idNomeCliente);

        }
    }
}

