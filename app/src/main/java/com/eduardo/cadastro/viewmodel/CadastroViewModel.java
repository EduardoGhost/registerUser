package com.eduardo.cadastro.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CadastroViewModel extends ViewModel {

    private MutableLiveData<Boolean> senhaValida = new MutableLiveData<>();

    public LiveData<Boolean> getSenhaValida() {
        return senhaValida;
    }

//    public void validarSenha(String senha) {
//        senhaValida.setValue(validarSenha(senha));
//    }
//
//    private boolean validarSenha(String senha) {
//        // Adicione aqui a lógica de validação da senha usando a expressão regular
//        String regex = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$";
//        return senha.matches(regex);
//    }
}

