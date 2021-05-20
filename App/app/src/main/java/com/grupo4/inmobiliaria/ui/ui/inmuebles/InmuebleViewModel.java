package com.grupo4.inmobiliaria.ui.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.grupo4.inmobiliaria.modelo.Contrato;
import com.grupo4.inmobiliaria.modelo.Inmueble;
import com.grupo4.inmobiliaria.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel extends AndroidViewModel {
    private Context context;
    public MutableLiveData<Inmueble> inmuebleMutable;
    public MutableLiveData<Boolean> contratoVigenteMutable;

    public LiveData<Inmueble> getInmuebleMutable(){
        if (inmuebleMutable == null){
            inmuebleMutable = new MutableLiveData<>();
        }
        return inmuebleMutable;
    }

    public LiveData<Boolean> getContratoVigenteMutable(){
        if (contratoVigenteMutable == null){
            contratoVigenteMutable = new MutableLiveData<>();
        }
        return contratoVigenteMutable;
    }

    public InmuebleViewModel(@NonNull Application app){
        super(app);
        this.context = app.getApplicationContext();
    }

    public void LeerInmueble(Bundle bundle){
        Inmueble inmueble = (Inmueble)bundle.getSerializable("inmueble");
        inmuebleMutable.setValue(inmueble);
    }

    public void CambioEstado(Inmueble inmueble){
        Call<Integer> resAsync = ApiClient.getMyApiClient().visibilidad(inmueble.getId(), ApiClient.getApi().getToken(context));
        resAsync.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    Integer r = response.body();
                    inmuebleMutable.setValue(inmueble);
                }
                Log.d("salida", response.message());
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("salida", t.getMessage());
            }
        });
    }

    public void ConsultarContratoVigente(Inmueble inmueble){
        Call<Contrato> resAsync = ApiClient.getMyApiClient().contratoVigente(inmueble.getId(), ApiClient.getApi().getToken(context));
        resAsync.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if(response.isSuccessful()){
                    Contrato c = response.body();
                    if (c != null)
                        contratoVigenteMutable.setValue(true);
                }
                Log.d("salida", response.message());
            }
            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {

                Log.d("salida", t.getMessage());
            }
        });
        //Contrato c = ApiClient.getApi().obtenerContratoVigente(inmueble);
        /*if (c == null)
            contratoVigenteMutable.setValue(false);
        else
            contratoVigenteMutable.setValue(true);*/
    }
}
