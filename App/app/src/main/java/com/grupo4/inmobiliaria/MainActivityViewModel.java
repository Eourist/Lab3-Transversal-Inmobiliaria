package com.grupo4.inmobiliaria;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.grupo4.inmobiliaria.modelo.Propietario;
import com.grupo4.inmobiliaria.request.ApiClient;
import com.grupo4.inmobiliaria.request.LoginRequest;
import com.grupo4.inmobiliaria.request.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> mensajeMutable;
    private MutableLiveData<Boolean> resultadoMutable;
    private SharedPreferences preferences;

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

    public MainActivityViewModel(@NonNull Application app){
        super(app);
        preferences = app.getApplicationContext().getSharedPreferences("data.dat", 0);
    }

    public void verificarDatos(String mail, String clave){
        if (mail != null && clave != null && clave.length() > 0 && mail.length() > 0){
            Call<LoginResponse> resAsync = ApiClient.getMyApiClient().login(new LoginRequest(mail, clave));
            resAsync.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()){
                        Propietario p = response.body().getPropietario();
                        if (p != null){
                            resultadoMutable.setValue(true);
                            String token = response.body().getToken();
                            preferences.edit().putString("token", "Bearer " + token).apply();
                            ApiClient.getApi().setUsuarioActual(p);
                        } else {
                            mensajeMutable.setValue("Error desconocido");
                        }

                    }else {
                        mensajeMutable.setValue("Error de validación");
                    }
                    Log.d("salida", response.message());
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("salida", t.getMessage());
                    mensajeMutable.setValue("Error de conexión");
                }
            });
        } else {
            mensajeMutable.setValue("Datos insuficientes");
        }
    }
}
