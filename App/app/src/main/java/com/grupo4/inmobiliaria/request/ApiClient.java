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
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class ApiClient {
    private ArrayList<Propietario> propietarios=new ArrayList<>();
    private ArrayList<Inquilino> inquilinos=new ArrayList<>();
    private ArrayList<Inmueble> inmuebles=new ArrayList<>();
    private ArrayList<Contrato> contratos=new ArrayList<>();
    private ArrayList<Pago> pagos=new ArrayList<>();
    private static Propietario usuarioActual=null;
    private static ApiClient api=null;

    //private static final String PATH="http://192.168.0.107:45455/api/";
    //private static final String PATH="http://192.168.0.108:45455/api/";
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
    }

    private ApiClient(){
        //Nos conectamos a nuestra "Base de Datos"
        cargaDatos();
    }

    //Método para crear una instancia de ApiClient
    public static ApiClient getApi(){
        if (api==null){
            api=new ApiClient();
        }
        return api;
    }

    //Servicios
    //Para que pueda iniciar sesion
    public Propietario login(String mail, final String password){
        for(Propietario propietario:propietarios){
            if(propietario.getEmail().equals(mail)&&propietario.getClave().equals(password)){
                usuarioActual=propietario;
                return propietario;
            }
        }
        return null;
    }

    //Retorna el usuario que inició Sesión
    public Propietario obtenerUsuarioActual(){
        return usuarioActual;
    }

    //Retorna las propiedades del usuario propietario logueado
    public ArrayList<Inmueble> obtnerPropiedades(){
        ArrayList<Inmueble> temp=new ArrayList<>();
        for(Inmueble inmueble:inmuebles){
            if(inmueble.getPropietario().equals(usuarioActual)){
                temp.add(inmueble);
            }
        }
        return temp;
    }

    //Lista de inmuebles con contratos vigentes del Propietario logueado
    public ArrayList<Inmueble> obtenerPropiedadesAlquiladas(){
        ArrayList<Inmueble> temp=new ArrayList<>();
        for(Contrato contrato:contratos){
            if(contrato.getInmueble().getPropietario().equals(usuarioActual)){
                temp.add(contrato.getInmueble());
            }
        }
        return temp;
    }


//Dado un inmueble retorna el contrato activo de dicho inmueble
    public Contrato obtenerContratoVigente(Inmueble inmueble){

        for(Contrato contrato:contratos){
            if(contrato.getInmueble().equals(inmueble)){
                return contrato;
            }
        }
        return null;
    }

    //Dado un inmueble, retorna el inquilino del ultimo contrato activo de ese inmueble.
    public Inquilino obtenerInquilino(Inmueble inmueble){
        for(Contrato contrato:contratos){
            if(contrato.getInmueble().equals(inmueble)){
                return contrato.getInquilino();
            }
        }
        return null;
    }
    //Dado un Contrato, retorna los pagos de dicho contrato
    public ArrayList<Pago> obtenerPagos(Contrato contratoVer){
        ArrayList<Pago> temp=new ArrayList<>();
        for(Contrato contrato:contratos){
            if(contrato.equals(contratoVer)){
                for(Pago pago:pagos){
                    if(pago.getContrato().equals(contrato)){
                        temp.add(pago);
                    }
                }
            }
            break;
        }
        return temp;
    }
    //Actualizar Perfil
    /*public void actualizarPerfil(Propietario propietario){
        int posición=propietarios.indexOf(propietario);
        if(posición!=-1){
            propietarios.set(posición,propietario);
        }
    }

    //ActualizarInmueble
    public void actualizarInmueble(Inmueble inmueble){
        int posicion=inmuebles.indexOf(inmueble);
        if(posicion!=-1){
            inmuebles.set(posicion,inmueble);
        }
    }*/

    private void cargaDatos(){

        //Propietarios
        Propietario juan=new Propietario(15006,"23492012L","Juan","Perez","juan@mail.com","123","2664553447");
        Propietario sonia=new Propietario(2,"17495869L","Sonia","Lucero","sonia@mail.com","123","266485417");
        propietarios.add(juan);
        propietarios.add(sonia);

        //Inquilinos
        Inquilino prueba = new Inquilino(101,"30000000", "Prueba", "Prueba","trabajo","prueba@mail.com", "00000000000", "Garante", "Garante", "400000000", "garante@mail.com", "03213712983");
        //Inquilino mario=new Inquilino(100,"25340691L","Mario","Luna","Aiello sup.","luna@mail.com","2664253411","Lucero Roberto","2664851422");
        inquilinos.add(prueba);

        //Inmuebles
        /*Inmueble salon=new Inmueble(501,"Colon 340","comercial","salon",2,20000,juan,true,"http://www.secsanluis.com.ar/servicios/salon1.jpg");
        Inmueble casa=new Inmueble(502,"Mitre 800","particular","casa",2,15000,juan,true,"http://www.secsanluis.com.ar/servicios/casa1.jpg");
        Inmueble otraCasa=new Inmueble(503,"Salta 325","particular","casa",3,17000,sonia,true,"http://www.secsanluis.com.ar/servicios/casa2.jpg");
        Inmueble dpto=new Inmueble(504,"Lavalle 450","particular","dpto",2,25000,sonia,true,"http://www.secsanluis.com.ar/servicios/departamento1.jpg");
        Inmueble casita=new Inmueble(505,"Belgrano 218","particular","casa",5,90000,sonia,true,"http://www.secsanluis.com.ar/servicios/casa3.jpg");*/
        Inmueble casa = new Inmueble(13016, "Calle Falsa 123", "Comercial", "Deparatamento", 3, 17000, juan, 1, "http://www.secsanluis.com.ar/servicios/salon1.jpg", 45);

        inmuebles.add(casa);

        //Contratos
        Contrato uno = new Contrato(701, "05/01/2020","05/01/2021",17000,prueba,casa);
        contratos.add(uno);
        //Pagos
        pagos.add(new Pago(900,uno,17000,"10/02/2020"));
        pagos.add(new Pago(901,uno,17000,"10/03/2020"));
        pagos.add(new Pago(902,uno,17000,"10/04/2020"));
    }
}
