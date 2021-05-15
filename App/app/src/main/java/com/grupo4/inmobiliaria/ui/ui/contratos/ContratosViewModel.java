package com.grupo4.inmobiliaria.ui.ui.contratos;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grupo4.inmobiliaria.modelo.Inmueble;
import com.grupo4.inmobiliaria.request.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends ViewModel {

    public MutableLiveData<ArrayList<Inmueble>> inmueblesMutable;
    public MutableLiveData<String> mensajeMutable;

    public LiveData<ArrayList<Inmueble>> getInmueblesMutable(){
        if (inmueblesMutable == null)
            inmueblesMutable = new MutableLiveData<>();
        return inmueblesMutable;
    }

    public LiveData<String> getMensajeMutable(){
        if (mensajeMutable == null)
            mensajeMutable = new MutableLiveData<>();
        return mensajeMutable;
    }

    public void LeerInmueblesAlquilados(){
        /*ApiClient api = ApiClient.getApi();
        ArrayList<Inmueble> inmuebles = api.obtenerPropiedadesAlquiladas();

        inmueblesMutable.setValue(inmuebles);*/

        Call<ArrayList<Inmueble>> resAsync = ApiClient.getMyApiClient().inmueblesAlquilados(15006);
        resAsync.enqueue(new Callback<ArrayList<Inmueble>>() {
            @Override
            public void onResponse(Call<ArrayList<Inmueble>> call, Response<ArrayList<Inmueble>> response) {
                if (response.isSuccessful()){
                    ArrayList<Inmueble> inmuebles = response.body();
                    if (inmuebles.isEmpty())
                        mensajeMutable.setValue("No existen contratos vigentes con sus inmuebles.");
                    else
                        inmueblesMutable.setValue(response.body());
                }
                Log.d("salida", response.message());
            }

            @Override
            public void onFailure(Call<ArrayList<Inmueble>> call, Throwable t) {
                Log.d("salida", t.getMessage());
                mensajeMutable.setValue("No se pudo establecer una conexión con el servidor.");
            }
        });
    }
}