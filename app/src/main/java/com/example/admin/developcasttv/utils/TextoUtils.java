package com.example.admin.developcasttv.utils;

import java.text.DecimalFormat;

/**
 * Created by admin on 29/04/2016.
 */
public class TextoUtils {

    public static String formatarFloat(float numero){
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
}
