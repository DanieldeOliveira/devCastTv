package com.example.admin.developcasttv.utils;

import android.util.Log;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.libraries.cast.companionlibrary.cast.DataCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.NoConnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.TransientNetworkDisconnectionException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 25/04/2016.
 */

/*
    Classe utilitária responsável pelo encapsular alguns procedimentos relativos ao envio de menssagem
    para o Chromecast.
 */
public class SenderUtils {

    private static final String TAG = "Daniel";



    private static SenderUtils sInstance;
    private VideoCastManager mCastManager;
    private DataCastManager mDataCastManager;
    private int qtdImg = 0;


    private SenderUtils(){

        mCastManager = VideoCastManager.getInstance();
        mDataCastManager = DataCastManager.getInstance();
    }

    public static SenderUtils initialize(){

        sInstance = new SenderUtils();
        return sInstance;
    }

    public static SenderUtils getInstance(){
        return sInstance;
    }





    /* Método responsável por iniciar um vídeo no Chromecast */

    public void startVideo(String url){

        MediaMetadata mediaMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
        mediaMetadata.putString(MediaMetadata.KEY_TITLE, "Testando Cast Companion Library");

        MediaInfo mediaInfo = new MediaInfo.Builder(url)
                .setContentType("video/mp4")
                .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                .setMetadata(mediaMetadata)
                .build();

        try {
            mCastManager.loadMedia(mediaInfo, true, 0);


        } catch (TransientNetworkDisconnectionException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();

        }
    }

    /* Método responsável por montar o JSon que será enviado ao Chromecast */
    public String criaJson(String escolha, String url)throws JSONException {
        JSONObject json = new JSONObject();

        json.put("choice", escolha);
        json.put("url",url);
        json.put("id","img" + String.valueOf(qtdImg + 1));
        qtdImg++;
        return json.toString();
    }


    /* Classe responsável por enviar mensagem ao Chromecast */

    public void enviarMensagemCast(String mensagem){

        try {
            mCastManager.sendDataMessage(mensagem);
            Log.d(TAG, "Enviou a mensagem de dentro do enviarMensagem");
        } catch (TransientNetworkDisconnectionException e) {
            Log.d(TAG,"Excecao TransientNetworkDisconnectionException");
            e.printStackTrace();
        } catch (NoConnectionException e) {
            Log.d(TAG,"Excecao NoConnectionException");
            e.printStackTrace();
        }
    }


}
