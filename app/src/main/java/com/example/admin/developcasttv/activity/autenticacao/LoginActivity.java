package com.example.admin.developcasttv.activity.autenticacao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserTokenStorageFactory;
import com.example.admin.developcasttv.CastApplication;
import com.example.admin.developcasttv.activity.MainActivity;
import com.example.admin.developcasttv.R;
import com.example.admin.developcasttv.validacao.Validacao;


public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private Button btn_cadastro;
    private Button btn_recuperarSenha;
    private CheckBox checkBoxPreferences;
    private EditText et_usuario;
    private EditText et_senha;
    private SharedPreferences preferences;
    private SharedPreferences preferenciaInicializacao = null;
    private SharedPreferences numeroFotoPreferencia;
    private BackendlessUser usuario;
    private BackendlessUser usuarioPreferencias;
    //private DBHelper dbHelper;
    private Validacao validar;
    private CastApplication aplication;
    private String userToken;
    private  boolean lembrar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
       // dbHelper = new DBHelper(getApplicationContext());
        usuario = new BackendlessUser();
        validar = new Validacao();
        aplication = CastApplication.getInstance();
        preferenciaInicializacao = getSharedPreferences("firstRun", Context.MODE_PRIVATE);
        et_usuario = (EditText)findViewById(R.id.et_emailLogin);
        et_senha  = (EditText)findViewById(R.id.et_senhaLogin);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_cadastro = (Button)findViewById(R.id.btn_cadastroUsuario);
        btn_recuperarSenha = (Button)findViewById(R.id.btn_RecuperarSenha);
        checkBoxPreferences = (CheckBox)findViewById(R.id.chkBox_login);
        userToken = UserTokenStorageFactory.instance().getStorage().get();
        //userToken = UserTokenStorageFactory.instance().getStorage().get();
        if (userToken != null && !userToken.equals("")){

            Log.d("danielback","Entrou no if do userToken");
            preferences = getSharedPreferences("preferencia", Context.MODE_PRIVATE);
            usuarioPreferencias = new BackendlessUser();
            usuarioPreferencias.setEmail(preferences.getString("email", null));
            usuarioPreferencias.setPassword(preferences.getString("senha", null));

            Backendless.UserService.login(usuarioPreferencias.getEmail(), usuarioPreferencias.getPassword(), new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser backendlessUser) {

                    startDashBoard();
                }

                @Override
                public void handleFault(BackendlessFault backendlessFault) {
                    Log.d("danielback","valor do erro no login = " + backendlessFault.toString());

                }
            });



        }


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario.setEmail(et_usuario.getText().toString());
                usuario.setPassword(et_senha.getText().toString());

                if(validar.validarCamposLogin(usuario)){

                    lembrar = false;

                    if(checkBoxPreferences.isChecked()){
                        lembrar = true;
                        Log.d("danielback","valor de lembrar na verificação de marcacao = " + lembrar);
                    }

                    //AluguelLivroAplication.inicializarBackend(getApplicationContext());
                    Backendless.UserService.login(usuario.getEmail(), usuario.getPassword(), new AsyncCallback<BackendlessUser>() {



                        @Override
                        public void handleResponse(BackendlessUser backendlessUser) {


                            if (lembrar){

                                salvarPreferencia(usuario);
                                Log.d("danielback","entrou no lembrar igual a true no handleResponse");
                                //salvaUsuarioLogado(backendlessUser);
                            }
                            startDashBoard();
                        }

                        @Override
                        public void handleFault(BackendlessFault backendlessFault) {
                            Log.d("danielback","valor do erro = " + backendlessFault.toString());
                            Toast.makeText(getApplicationContext(),"Usuário ou senha incorretos!",Toast.LENGTH_SHORT).show();
                        }
                    },lembrar);



                }
                else {
                    Toast.makeText(getApplicationContext(),"Todos os campos são obrigatorios",Toast.LENGTH_LONG).show();
                }


            }
        });

        btn_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,CadastroUsuarioActivity.class);
                startActivity(intent);
            }
        });

        btn_recuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RecuperarSenhaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(preferenciaInicializacao.getBoolean("firstRun",true)){
            //aplication.setNumeroFoto(1);
            preferenciaInicializacao.edit().putBoolean("firstRun",false).apply();
            //aplication.salvarPreferencia();

        }
        else {
            numeroFotoPreferencia = getSharedPreferences("numeroFoto",Context.MODE_PRIVATE);
           // aplication.setNumeroFoto(Integer.parseInt(numeroFotoPreferencia.getString("numero", null)));
            Log.d("daniel","valor de numero preferencia " + numeroFotoPreferencia.getString("numero", null));
        }
    }


    private void startDashBoard(){

        BackendlessUser user = Backendless.UserService.CurrentUser();
        Log.d("danielback","valor de userId no start do dashboad = " + user.getObjectId());
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void salvarPreferencia(BackendlessUser usuario){
        SharedPreferences.Editor editor = getSharedPreferences("preferencia", Context.MODE_PRIVATE).edit();
        editor.putString("email",usuario.getEmail());
        editor.putString("senha", usuario.getPassword());
        editor.commit();
    }








}
