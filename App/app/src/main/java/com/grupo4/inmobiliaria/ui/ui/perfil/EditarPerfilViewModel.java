package com.grupo4.inmobiliaria.ui.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grupo4.inmobiliaria.modelo.Propietario;
import com.grupo4.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPerfilViewModel extends AndroidViewModel {
    public Context context;
    public MutableLiveData<Propietario> propietarioMutable;
    public MutableLiveData<String> errorMutable;

    public LiveData<Propietario> getPropietarioMutable(){
        if (propietarioMutable == null){
            propietarioMutable = new MutableLiveData<>();
        }
        return propietarioMutable;
    }

    public LiveData<String> getErrorMutable(){
        if (errorMutable == null){
            errorMutable = new MutableLiveData<>();
        }
        return errorMutable;
    }

    public EditarPerfilViewModel(@NonNull Application app){
        super(app);
        context = app.getApplicationContext();
    }

    public void ObtenerPropietario(){
        ApiClient api = ApiClient.getApi();
        Propietario p = api.getUsuarioActual();

        propietarioMutable.setValue(p);
    }

    public void ModificarPropietario(Propietario p){
        if(p.getNombre().isEmpty() || p.getApellido().isEmpty() || p.getDni().isEmpty() || p.getEmail().isEmpty() || p.getTelefono().isEmpty()){
            errorMutable.setValue("Los campos no pueden estar vacios.");
        }else if(p.getDni().length() != 8){
            errorMutable.setValue("El DNI ingresado no es válido (8 dígitos necesarios)");
        }else if(p.getNombre().length() > 16 || p.getNombre().length() < 3 || p.getApellido().length() > 16 || p.getApellido().length() < 3){
            errorMutable.setValue("El nombre/apellido ingresado no es válido (3 caracteres mínimo, 16 máximo)");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(p.getEmail()).matches()){
            errorMutable.setValue("La dirección de correo electrónico ingresada no es válida.");
        }else if(p.getTelefono().length() > 15 || p.getTelefono().length() < 9){
            errorMutable.setValue("El número de teléfono ingresado no es válido (9-15 dígitos)");
        }else{
            Call<Propietario> resAsync = ApiClient.getMyApiClient().modificarPropietario(p, ApiClient.getApi().getToken(context));
            resAsync.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if(response.isSuccessful()){
                        Propietario r = response.body();
                        errorMutable.setValue("EXITO");
                    }
                    Log.d("salida", response.message()+"onresponse");
                }
                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Log.d("salida", t.getMessage()+"onfailure");
                }
            });
        }

    }
}