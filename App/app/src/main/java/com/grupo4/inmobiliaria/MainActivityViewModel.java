package com.grupo4.inmobiliaria;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.common.api.Api;
import com.grupo4.inmobiliaria.modelo.Inmueble;
import com.grupo4.inmobiliaria.modelo.Propietario;
import com.grupo4.inmobiliaria.request.ApiClient;
import com.grupo4.inmobiliaria.request.LoginRequest;
import com.grupo4.inmobiliaria.request.LoginResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<String> mensajeMutable;
    private MutableLiveData<Boolean> resultadoMutable;

    public LiveData<String> getMensajeMutable(){
        if (mensajeMutable == null)
            mensajeMutable = new MutableLiveData<>();
        return mensajeMutable;
    }

    public LiveData<Boolean> getResultadoMutable(){
        if (resultadoMutable == null)
            resultadoMutable = new MutableLiveData<>();
        return resultadoMutable;
    }

    public void verificarDatos(String mail, String clave){
        if (mail != null && clave != null && clave.length() > 0 && mail.length() > 0){
            /*ApiClient api = ApiClient.getApi();
            Propietario propietario = api.login(mail, clave);

            if (propietario == null){
                mensajeMutable.setValue("Credenciales incorrectas");
            } else {
                resultadoMutable.setValue(true);
            }*/
            Call<LoginResponse> resAsync = ApiClient.getMyApiClient().login(new LoginRequest(mail, clave));
            resAsync.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()){
                        Propietario p = response.body().getPropietario();
                        if (p != null){ // faltaria guardar el token en archivo local??
                            resultadoMutable.setValue(true);
                            ApiClient.getApi().setUsuarioActual(p);
                        } else {
                            mensajeMutable.setValue("Error A");
                        }

                    }else {
                        mensajeMutable.setValue("Error B");
                    }
                    Log.d("salida", response.message());
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("salida", t.getMessage());
                    mensajeMutable.setValue("Error C");
                }
            });
        } else {
            mensajeMutable.setValue("Datos insuficientes");
        }
    }
}
