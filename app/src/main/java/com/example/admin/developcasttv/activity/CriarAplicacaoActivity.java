package com.example.admin.developcasttv.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.backendless.social.SocialLoginDialog;
import com.example.admin.developcasttv.R;
import com.example.admin.developcasttv.utils.CastUtils;
import com.example.admin.developcasttv.utils.ImagemUtils;
import com.example.admin.developcasttv.utils.ServerUtils;
import com.example.admin.developcasttv.utils.VideoUtils;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.callbacks.VideoCastConsumer;
import com.google.android.libraries.cast.companionlibrary.cast.callbacks.VideoCastConsumerImpl;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.NoConnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.TransientNetworkDisconnectionException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class CriarAplicacaoActivity extends AppCompatActivity {


    public static final int IMAGEM_INTERNA = 1;
    public static final int VIDEO_INTERNO = 2;
    public static final int MOVER_IMAGEM = 3;

    private VideoCastManager videoCastManager;
    private VideoCastConsumer videoCastConsumer;
    private ImageButton btn_addVideo;
    private ImageButton btn_addImagem;
    private ImageButton btn_addComentario;
    private ImageButton btn_listaMidiasEnviadas;
    private ImageButton btn_gerenciarMembros;
    private ImageButton btn_salvarAplicacao;
    private  Intent intent;

    private File file;
    Uri uriSelecionada = null;
    String path = "";
    String urlFinal = "";
    CastUtils castUtils;
    private boolean mIsHoneyCombOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    private MenuItem mediaRouteMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opcoescriacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_addVideo = (ImageButton)findViewById(R.id.Ibtn_addVideo);
        btn_addImagem = (ImageButton)findViewById(R.id.Ibtn_addImg);
        btn_addComentario = (ImageButton)findViewById(R.id.Ibtn_addComentario);
        btn_listaMidiasEnviadas = (ImageButton)findViewById(R.id.Ibtn_elementosEnviados);
        videoCastManager = VideoCastManager.getInstance();



        castUtils = new CastUtils();

        btn_addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(Intent.createChooser(intent, "Selecione o video"), VIDEO_INTERNO);

            }
        });

        btn_addImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject mensagem = new JSONObject();
                try {
                    mensagem.put("tipo","removeMsg");
                    videoCastManager.sendDataMessage(mensagem.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (TransientNetworkDisconnectionException e) {
                    e.printStackTrace();
                } catch (NoConnectionException e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Selecione a foto"), IMAGEM_INTERNA);
            }
        });

        btn_addComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_listaMidiasEnviadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        videoCastManager.addMediaRouterButton(menu,R.id.media_route_menu_item);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoCastManager = VideoCastManager.getInstance();
        videoCastManager.incrementUiCounter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoCastManager.decrementUiCounter();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){

            if (requestCode == VIDEO_INTERNO){

                uriSelecionada = data.getData();
                path = VideoUtils.getPathVideo(uriSelecionada,getApplicationContext());
                file = new File(path);
                urlFinal = ServerUtils.enderecoServidor(getApplicationContext()) + path.toString()
                        + "?tipo=vdo";
                Log.d("tempototal","" + VideoUtils.getDuracaoVideo(getApplicationContext(),path));
                Log.d("tempototal","nome do arquivo = " + file.getName());

               castUtils.startVideo(urlFinal,VideoUtils.getDuracaoVideo(getApplicationContext(),path),file.getName());
            }

            if(requestCode == IMAGEM_INTERNA){
                uriSelecionada = data.getData();
                path = ImagemUtils.getUrlImagem(uriSelecionada,getApplicationContext());
                urlFinal = ServerUtils.enderecoServidor(getApplicationContext()) + path.toString() +
                        "?tipo=img";

                intent = new Intent(CriarAplicacaoActivity.this,MoverImagemActivity.class);
                intent.putExtra("path",path);
                intent.putExtra("urlFinal",urlFinal);
                startActivityForResult(intent,MOVER_IMAGEM);

            }

            if (requestCode == MOVER_IMAGEM){

            }
        }
    }
}
