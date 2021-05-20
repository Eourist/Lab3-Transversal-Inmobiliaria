package com.grupo4.inmobiliaria.ui.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.common.api.Api;
import com.grupo4.inmobiliaria.modelo.Propietario;
import com.grupo4.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private Context context;
    public MutableLiveData<Propietario> propietarioMutable;

    public LiveData<Propietario> getPropietarioMutable(){
        if (propietarioMutable == null){
            propietarioMutable = new MutableLiveData<>();
        }
        return propietarioMutable;
    }

    public PerfilViewModel(@NonNull Application app){
        super(app);
        this.context = app.getApplicationContext();
    }

    public void LeerPropietario(){
        Call<Propietario> resAsync = ApiClient.getMyApiClient().propietarioLogueado(ApiClient.getApi().getToken(context));
        resAsync.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        propietarioMutable.postValue(response.body());
                    else
                        Log.d("salida", "Propietario no encontrado");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.d("salida", "Error de conexion");
            }
        });
    }
}