package com.example.admin.developcasttv.activity.autenticacao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.admin.developcasttv.CastApplication;
import com.example.admin.developcasttv.R;
import com.example.admin.developcasttv.validacao.Validacao;


/**
 * Created by admin on 14/02/2016.
 */
public class CadastroUsuarioActivity extends AppCompatActivity {


   // private DBHelper dbHelper;
    BackendlessUser usuario;
   // private Usuario usuario;

    private Button btn_salvar;
    private Button btn_cancelar;

    private EditText et_nome;
    private EditText et_email;
    private EditText et_senha;
    private Validacao validacao;

    private String operacao;
    private CastApplication aplication;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrousuario);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        final Intent intent = getIntent();
        operacao = intent.getStringExtra("operacao");
        //dbHelper = new DBHelper(getApplicationContext());
        validacao = new Validacao();
        aplication = CastApplication.getInstance();
        usuario = new BackendlessUser();


        et_nome = (EditText)findViewById(R.id.et_nomeCadastroUsuario);
        et_email = (EditText)findViewById(R.id.et_emailCadastroUsuario);
        et_senha = (EditText)findViewById(R.id.et_senhaUsuario);
        btn_salvar = (Button)findViewById(R.id.btn_salvarUsuario);
        btn_cancelar = (Button)findViewById(R.id.btn_cancelarUsuario);

      

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario.setProperty("name", et_nome.getText().toString());
                usuario.setEmail(et_email.getText().toString());
                usuario.setPassword(et_senha.getText().toString());



                boolean isValido = validacao.validarCamposCadastroUsuario(usuario);

                if(isValido){
                   adicionarUsuario(usuario);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Todos os campos são obrigatorios",Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void adicionarUsuario(BackendlessUser usuario){


       // CastApplication.inicializarBackend(getApplicationContext());
        final BackendlessUser user = new BackendlessUser();
        user.setProperty("name", usuario.getProperty("name"));
        user.setProperty("email", usuario.getEmail());
        user.setPassword(usuario.getPassword());
        Log.d("danielback", "valor do id antes de inserir " + user.getObjectId());
        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser backendlessUser) {

                onBackPressed();


            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Toast.makeText(getApplicationContext(), "O usuário " + user.getProperty("name").toString() + " já está cadastrado!", Toast.LENGTH_SHORT).show();
            }
        });


    }



}
