package com.example.admin.developcasttv.server;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;


/**
 * Created by admin on 22/04/2016.
 */
public class WebServer extends NanoHTTPD {

    private static final String TAG = "Daniel";
    private Response.IStatus status;

    public WebServer(){

        super(8080);
        Log.d(TAG, "Servidor Iniciado na classe servidor");
    }



    @Override
    public Response serve(IHTTPSession session) {
        String mediasend = "";

        Map<String,String> parametros = session.getParms();
        Log.d(TAG,"Valor dos parametros = " + parametros.toString());
        String tipo = parametros.get("tipo");
        String uri = session.getUri();
        File file = null;
        FileInputStream fileInputStream = null;
        if(tipo.equals("img")){

            try {
                mediasend = "image/jpeg";
                file = new File(uri);
                fileInputStream = new FileInputStream(file);
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
            return newFixedLengthResponse(Response.Status.OK,mediasend,fileInputStream,file.length());
        }

        else if (tipo.equals("vdo")){


           try{
               file = new File(uri);
               mediasend = "video/mp4";
               Log.d(TAG,"Valor da uri " + uri.toString());
               fileInputStream = new FileInputStream(file);
               Log.d(TAG,"Valor do fileinputstream = " + fileInputStream.toString());
           }
           catch (FileNotFoundException e){
               e.printStackTrace();
           }

            return newFixedLengthResponse(Response.Status.OK,mediasend,fileInputStream,file.length());

        }

        else {
            mediasend = "text/html";
            String resposta = "Tamo ai";
            return newFixedLengthResponse(Response.Status.OK,mediasend,resposta);
        }
    }
}
