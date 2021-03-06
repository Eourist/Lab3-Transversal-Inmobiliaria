package com.grupo4.inmobiliaria.request;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo4.inmobiliaria.modelo.*;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.Path;


public class ApiClient {
    private static ApiClient api = null;

    //private static final String PATH="http://192.168.0.107:45455/api/"; //Diego
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
        @GET("contratos/contrato_vigente/{InmuebleId}")
        public Call<Contrato> contratoVigente(@Path("InmuebleId") int InmuebleId, @Header("Authorization") String token);

        @GET("inmuebles/inmuebles")
        public Call<ArrayList<Inmueble>> inmuebles(@Header("Authorization") String token);

        @GET("inmuebles/inmuebles_alquilados")
        public Call<ArrayList<Inmueble>> inmueblesAlquilados(@Header("Authorization") String token);

        @GET ("pagos/pagos_contrato/{ContratoId}")
        public Call<ArrayList<Pago>> pagos(@Path("ContratoId") int ContratoId, @Header("Authorization") String token);

        @PATCH("inmuebles/cambiar_visibilidad/{InmuebleId}")
        public Call<Integer> visibilidad(@Path("InmuebleId") int InmuebleId, @Header("Authorization") String token);

        @PUT("propietarios/editar_propietario")
        public Call<Propietario> modificarPropietario(@Body Propietario Propietario, @Header("Authorization") String token);

        @GET("propietarios/propietarioLogueado")
        public Call<Propietario> propietarioLogueado(@Header("Authorization") String token);

        @POST("propietarios/login")
        public Call<LoginResponse> login(@Body LoginRequest loginRequest);
    }

    public static ApiClient getApi(){
        if (api==null){
            api=new ApiClient();
        }
        return api;
    }

    public String getToken(Context context) {
        return context.getSharedPreferences("data.dat", 0).getString("token", "Error al recuperar el token");
    }
}
