package com.eduardo.cadastro.viewmodel;

import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.eduardo.cadastro.model.ClienteEntity;
import com.eduardo.cadastro.model.database.local.Dao;

public class EditViewModel  extends ViewModel {

    private final MutableLiveData<Boolean> editResult = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<Boolean> getEditResult() {
        return editResult;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }


    public void alterarCliente(Dao dao, ClienteEntity cliente) {
        // Lógica de validação e cadastro
        if (isValidCliente(cliente)) {
            boolean resultado = dao.alterarCliente(cliente);

            if (resultado) {
                editResult.setValue(true);

            } else {
                errorMessage.setValue("Erro ao atualizar cliente.");
            }
        } else {
            errorMessage.setValue("Dados do cliente inválidos.");
        }
    }

    private boolean isValidCliente(ClienteEntity cliente) {

        // Cliente não pode ser nulo
        if (cliente == null) {
            return false;
        }

        // nome não pode está vazio
        if (TextUtils.isEmpty(cliente.getName())) {
            return false;
        }


        return true;
    }

}
