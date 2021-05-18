package com.grupo4.inmobiliaria.request;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.load.engine.Resource;
import com.google.android.gms.ads.internal.gmsg.HttpClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo4.inmobiliaria.BuildConfig;
import com.grupo4.inmobiliaria.R;
import com.grupo4.inmobiliaria.modelo.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class ApiClient {
    private static Propietario usuarioActual=null;
    private static ApiClient api=null;

    private static final String PATH="http://192.168.0.107:45455/api/"; //Diego
    //private static final String PATH="http://192.168.0.108:45455/api/"; //Sebastian
    private static  MyApiInterface myApiInteface;

    public static MyApiInterface getMyApiClient(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(PATH)
                //.client(generarHttpClient(context)) si quiero hacer resto pedir contexto por parametro (y obtener contexto en todos los ViewModel -> AndroidViewModel)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        myApiInteface=retrofit.create(MyApiInterface.class);
        Log.d("salida",retrofit.baseUrl().toString());
        return myApiInteface;
    }

    /*private static OkHttpClient generarHttpClient(Context context){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS);
        InputStream res = context.getResources().openRawResource(R.raw.conveyor_root);

        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(res, "".toCharArray());

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("sha256RSA");
            keyManagerFactory.init(keyStore, "".toCharArray());

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());

            return httpClient.socketFactory(sslContext.getSocketFactory()).build();
        } catch (Exception e) {
            Log.d("salida", e.getMessage());
        }
        return null;
    }*/

    public interface MyApiInterface {
        @GET("apicontratos/contrato_vigente/{InmuebleId}")
        public Call<Contrato> contratoVigente(@Path("InmuebleId") int InmuebleId);

        @GET("apiinmuebles/inmuebles/{PropietarioId}") // OK
        public Call<ArrayList<Inmueble>> inmuebles(@Path("PropietarioId") int PropietarioId);

        @GET("apiinmuebles/inmuebles_alquilados/{PropietarioId}")
        public Call<ArrayList<Inmueble>> inmueblesAlquilados(@Path("PropietarioId") int PropietarioId);

        @GET ("apipagos/pagos_contrato/{ContratoId}")
        public Call<ArrayList<Pago>> pagos(@Path("ContratoId") int ContratoId);

        @PATCH("apiinmuebles/cambiar_visibilidad/{InmuebleId}")
        public Call<Integer> visibilidad(@Path("InmuebleId") int InmuebleId);

        @PUT("apipropietarios/editar_propietario")
        public Call<Propietario> modificarPropietario(@Body Propietario Propietario);

        @POST("apipropietarios/login")
        public Call<LoginResponse> login(@Body LoginRequest loginRequest);
    }

    public static ApiClient getApi(){
        if (api==null){
            api=new ApiClient();
        }
        return api;
    }

    public Propietario getUsuarioActual(){
        return usuarioActual;
    }

    public void setUsuarioActual(Propietario propietario) { usuarioActual = propietario; }
}
