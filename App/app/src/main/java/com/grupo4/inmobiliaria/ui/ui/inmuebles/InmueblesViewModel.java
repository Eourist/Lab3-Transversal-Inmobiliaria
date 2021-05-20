package com.grupo4.inmobiliaria.ui.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.grupo4.inmobiliaria.modelo.Inmueble;
import com.grupo4.inmobiliaria.request.ApiClient;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {
    private Context context;
    public MutableLiveData<ArrayList<Inmueble>> inmueblesMutable;

    public LiveData<ArrayList<Inmueble>> getInmueblesMutable(){
        if (inmueblesMutable == null){
            inmueblesMutable = new MutableLiveData<>();
        }
        return inmueblesMutable;
    }

    public InmueblesViewModel(@NonNull Application app){
        super(app);
        context = app.getApplicationContext();
    }

    public void LeerInmuebles(){
        Call<ArrayList<Inmueble>> resAsync = ApiClient.getMyApiClient().inmuebles(ApiClient.getApi().getToken(context));
        resAsync.enqueue(new Callback<ArrayList<Inmueble>>() {
            @Override
            public void onResponse(Call<ArrayList<Inmueble>> call, Response<ArrayList<Inmueble>> response) {
                if (response.isSuccessful()){
                    inmueblesMutable.setValue(response.body());

                }
                if (response.code() == 401){
                    Log.d("salida", "Error de autorización"); // si el token esta vencido debería entrar aca
                    inmueblesMutable.setValue(new ArrayList<Inmueble>());
                }
                Log.d("salida", response.message());
            }

            @Override
            public void onFailure(Call<ArrayList<Inmueble>> call, Throwable t) {
                Log.d("salida", t.getMessage());
                inmueblesMutable.setValue(new ArrayList<Inmueble>());
            }
        });
    }
}