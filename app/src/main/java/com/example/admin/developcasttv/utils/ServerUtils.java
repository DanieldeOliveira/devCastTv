package com.example.admin.developcasttv.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;


import com.example.admin.developcasttv.server.WebServer;

import java.io.IOException;

/**
 * Created by admin on 02/05/2016.
 */
public class ServerUtils {

    private static String TAG = "inicioServidor";

    private static ServerUtils sInstance;
    static String endereco;
    private static WebServer servidor;
    private ServerUtils(){

    }

    public static ServerUtils initialize(){
        sInstance = new ServerUtils();
        iniciarServidor();
        return sInstance;
    }

    public static ServerUtils getInstance(){
        return sInstance;
    }


    public static String enderecoServidor(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        endereco = String.format("http://%d.%d.%d.%d:8080", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        return endereco;

    }


    /* Inicialização do servidor que permitirá ao usuario enviar arquivos como imagem e vídeo
           de seu smartphone para o chromecast
    */
    private static void iniciarServidor(){
        servidor = new WebServer();

        try {
            servidor.start();
            Log.d(TAG,"Servidor iniciado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
