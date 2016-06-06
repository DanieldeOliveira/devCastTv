package com.example.admin.developcasttv.validacao;


import com.backendless.BackendlessUser;
import com.example.admin.developcasttv.domain.Usuario;


/**
 * Created by admin on 16/02/2016.
 */
public class Validacao {

    public boolean validarCamposCadastroUsuario(BackendlessUser usuario){

        boolean retorno = true;

        if(usuario.getProperty("name") == null || "".equals(usuario.getProperty("name"))){

            retorno = false;
        }
        if(usuario.getEmail() == null || "".equals(usuario.getEmail())){
            retorno = false;
        }
        if(usuario.getPassword() == null || "".equals(usuario.getPassword())){
            retorno = false;
        }
        return retorno;
    }

       public boolean validarCamposLogin(BackendlessUser usuario){
        boolean retorno = true;

        if(usuario.getEmail() == null || "".equals(usuario.getEmail())){
            retorno = false;
        }
        if(usuario.getPassword() == null || "".equals(usuario.getPassword())){
            retorno = false;
        }

        return retorno;

    }

    public boolean verificaPreferences(Usuario usuario){
        if(usuario.getEmail() != null && usuario.getSenha() != null){
            return true;
        }
        return false;
    }

}
