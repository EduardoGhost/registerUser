package com.eduardo.cadastro.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.eduardo.cadastro.model.ClienteEntity;
import com.eduardo.cadastro.model.database.local.Dao;

public class DetailViewModel extends ViewModel {

    private final MutableLiveData<Boolean> detailResult = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<Boolean> getDetailResult() {
        return detailResult;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void detailCliente(Dao dao, long clienteId) {

        try {
            ClienteEntity cliente = dao.getClienteById(clienteId);

            if (cliente != null) {
                // Detalhes obtidos com sucesso
                detailResult.setValue(true);
            } else {
                // Não foi possível obter os detalhes do cliente
                errorMessage.setValue("Detalhes do cliente não encontrados.");
            }
        } catch (Exception e) {
            // Lidar com exceções, por exemplo, erro no acesso ao banco de dados
            errorMessage.setValue("Erro ao obter detalhes do cliente: " + e.getMessage());
        }
    }


}
