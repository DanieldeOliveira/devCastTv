package com.example.admin.developcasttv.activity.autenticacao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.admin.developcasttv.R;


public class RecuperarSenhaActivity extends AppCompatActivity {

    private Button btn_Enviar;
    private EditText edt_Email;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);
        edt_Email = (EditText)findViewById(R.id.edt_recuparSenha);
        btn_Enviar = (Button)findViewById(R.id.btn_EnviarRecuperacaoSenha);

        btn_Enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edt_Email.getText().toString();
                Backendless.UserService.restorePassword(email, new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Um email foi enviado para " + email,Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {
                        Toast.makeText(getApplicationContext(),"Não foi possível enviar um email para " + email,Toast.LENGTH_SHORT).show();
                        edt_Email.setText("");
                    }
                });
            }
        });

    }



}
