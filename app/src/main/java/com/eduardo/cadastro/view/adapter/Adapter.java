package com.eduardo.cadastro.view.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eduardo.cadastro.R;
import com.eduardo.cadastro.model.ClienteEntity;
import com.eduardo.cadastro.model.database.local.Dao;

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

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"update client",Toast.LENGTH_LONG).show();
                //add metodo
            }
        });

        //delete usuario
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.activity);
                builder.setCancelable(false);
                builder.setTitle("Excluir");
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dao daoCliente = new Dao(context);
                        Toast.makeText(context, "Usuario Deletado", Toast.LENGTH_LONG).show();
                        ClienteEntity deleteCliente = listClientes.get(i);
                        deleteCliente.getCodeId();
                        daoCliente.Delete(deleteCliente);
                        listClientes.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, getItemCount());
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listClientes.size();
    }

    public class ClienteViewHolder extends RecyclerView.ViewHolder {
        Context activity;
        TextView nome;
        TextView update;
        TextView delete;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            activity = itemView.getContext();
            nome = itemView.findViewById(R.id.idNomeCliente);
            update = itemView.findViewById(R.id.idTxtUpdate);
            delete = itemView.findViewById(R.id.idTxtDelete);

        }
    }
}

