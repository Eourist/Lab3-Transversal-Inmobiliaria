package com.grupo4.inmobiliaria.ui.ui.contratos;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grupo4.inmobiliaria.modelo.Contrato;
import com.grupo4.inmobiliaria.modelo.Inmueble;
import com.grupo4.inmobiliaria.modelo.Pago;
import com.grupo4.inmobiliaria.request.ApiClient;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends ViewModel {

    public MutableLiveData<Contrato> contratoMutable;
    public MutableLiveData<List<Pago>> pagosMutable;

    public LiveData<List<Pago>> getPagosMutable(){
        if (pagosMutable == null){
            pagosMutable = new MutableLiveData<>();
        }
        return pagosMutable;
    }

    public LiveData<Contrato> getContratoMutable(){
        if (contratoMutable == null){
            contratoMutable = new MutableLiveData<>();
        }
        return contratoMutable;
    }

    public void LeerContrato(Bundle bundle){
        Call<Contrato> resAsync = ApiClient.getMyApiClient().contratoVigente(((Inmueble)bundle.getSerializable("inmueble")).getId());
        resAsync.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful()){
                    Contrato c = response.body();
                    c.setFechaDesde(c.getFechaDesde().substring(0, 10));
                    c.setFechaHasta(c.getFechaHasta().substring(0, 10));
                    contratoMutable.setValue(response.body());
                }
                Log.d("salida", response.message());
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.d("salida", t.getMessage());
            }
        });
    }

    public void LeerPagosContrato(Contrato contrato){
        Call<ArrayList<Pago>> resAsync = ApiClient.getMyApiClient().pagos(contrato.getId());
        resAsync.enqueue(new Callback<ArrayList<Pago>>() {
            @Override
            public void onResponse(Call<ArrayList<Pago>> call, Response<ArrayList<Pago>> response) {
                if(response.isSuccessful()){
                    ArrayList<Pago> listaPagos = response.body();
                    pagosMutable.setValue(listaPagos);
                }
                Log.d("salida", response.message());
            }
            @Override
            public void onFailure(Call<ArrayList<Pago>> call, Throwable t) {
                Log.d("salida", t.getMessage());
            }
        });
    }
}
