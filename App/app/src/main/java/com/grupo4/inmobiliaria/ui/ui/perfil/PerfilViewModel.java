package com.grupo4.inmobiliaria.ui.ui.perfil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grupo4.inmobiliaria.modelo.Propietario;
import com.grupo4.inmobiliaria.request.ApiClient;

public class PerfilViewModel extends ViewModel {
    public MutableLiveData<Propietario> propietarioMutable;

    public LiveData<Propietario> getPropietarioMutable(){
        if (propietarioMutable == null){
            propietarioMutable = new MutableLiveData<>();
        }
        return propietarioMutable;
    }

    public void LeerPropietario(){
        ApiClient api = ApiClient.getApi();
        Propietario p = api.getUsuarioActual();

        propietarioMutable.setValue(p);
    }
}