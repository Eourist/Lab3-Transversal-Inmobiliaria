package com.grupo4.inmobiliaria.ui.ui.inmuebles;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grupo4.inmobiliaria.modelo.Contrato;
import com.grupo4.inmobiliaria.modelo.Inmueble;
import com.grupo4.inmobiliaria.request.ApiClient;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Inmueble>> inmueblesMutable;

    public LiveData<ArrayList<Inmueble>> getInmueblesMutable(){
        if (inmueblesMutable == null){
            inmueblesMutable = new MutableLiveData<>();
        }
        return inmueblesMutable;
    }

    public void LeerInmuebles(){
        Call<ArrayList<Inmueble>> resAsync = ApiClient.getMyApiClient().inmuebles(15006);
        resAsync.enqueue(new Callback<ArrayList<Inmueble>>() {
            @Override
            public void onResponse(Call<ArrayList<Inmueble>> call, Response<ArrayList<Inmueble>> response) {
                if (response.isSuccessful()){
                    inmueblesMutable.setValue(response.body());

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