package com.example.admin.developcasttv.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import com.example.admin.developcasttv.CastApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by admin on 25/04/2016.
 */

/*
    Classe responsável por gerenciar o nome das imagens, bem como os seus caminhos
 */
public class ImagemUtils {


    private CastApplication castApplication;


    private JSONObject dimensoes;





    /*
       Método responsável por incrementar o contador de quantidade de imagem.
    */


    public static String getUrlImagem(Uri uri, Context ctx){

        Context context = ctx;
        Uri contentUri = uri;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        String path = null;

        try{
            if(Build.VERSION.SDK_INT > 19){
                String wholeID = DocumentsContract.getDocumentId(contentUri);
                // Split at colon, use second item in the array
                String id = wholeID.split(":")[1];
                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";


                cursor = context.getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, sel, new String[] { id }, null);
            }
            else{
                cursor = context.getContentResolver().query(contentUri,
                        projection, null, null, null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            int column_index = cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA);

            cursor.moveToFirst();
            path = cursor.getString(column_index).toString();
            cursor.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return path;

    }

    public String formatarFloat(float numero){
        String retorno = "";

        DecimalFormat formatter = new DecimalFormat("#.00");
        try{
            retorno = formatter.format(numero);
            retorno = retorno.replaceAll(",",".");
        }catch(Exception ex){
            System.err.println("Erro ao formatar numero: " + ex);
        }

        return retorno;

    }

    public JSONObject calcularPosicao(int xSender,int ySender) throws JSONException {

        castApplication = CastApplication.getInstance();
        dimensoes = new JSONObject();
        int xEnviar = (int) ((xSender * castApplication.getWidthTV())/castApplication.getWidthTelaSmartphone());
        int yEnviar = (int) ((ySender * castApplication.getHeightTV())/castApplication.getHeightTelaSmartphone());

        dimensoes.put("x",xEnviar);
        dimensoes.put("y",yEnviar);

        return dimensoes;
    }



}
