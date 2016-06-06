package com.example.admin.developcasttv.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.admin.developcasttv.CastApplication;
import com.example.admin.developcasttv.R;
import com.example.admin.developcasttv.activity.autenticacao.LoginActivity;
import com.example.admin.developcasttv.utils.CastUtils;
import com.google.android.libraries.cast.companionlibrary.cast.BaseCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.callbacks.VideoCastConsumer;
import com.google.android.libraries.cast.companionlibrary.cast.callbacks.VideoCastConsumerImpl;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.NoConnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.TransientNetworkDisconnectionException;
import com.google.android.libraries.cast.companionlibrary.widgets.IntroductoryOverlay;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    private static String TAG = "mensagemParaOSender";
    private boolean mIsHoneyCombOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    private IntroductoryOverlay mOverlay;
    private boolean autorizado = false;
    private boolean dono = false;
    private CastApplication castApplication;
    private MenuItem mediaRouteMenuItem;
    private VideoCastManager videoCastManager;
    private VideoCastConsumer videoCastConsumer;
    private Button btnCriarApp;
    private Button btnExecutarApp;
    private JSONObject mensagem;
    private JSONObject mensagemRecebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseCastManager.checkGooglePlayServices(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        castApplication = CastApplication.getInstance();
        btnCriarApp = (Button)findViewById(R.id.btn_criarAplicacao);
        btnExecutarApp = (Button)findViewById(R.id.btn_executarAplicacao);
        videoCastManager = VideoCastManager.getInstance();

        videoCastConsumer = new VideoCastConsumerImpl(){

            @Override
            public void onCastAvailabilityChanged(boolean castPresent) {

                if (castPresent && mIsHoneyCombOrAbove){
                    showOverlay();
                }
            }

            @Override
            public void onDataMessageReceived(String message) {

                try {
                    Log.d(TAG,"dimensões = " + message);
                    mensagemRecebida = new JSONObject(message);
                    if (mensagemRecebida.get("tipo").equals("dimensaoTela")){
                        Log.d("oitopilhas","vai adicionar as dimensões da TV");
                        castApplication.setWidthTV((int)mensagemRecebida.get("width"));
                        castApplication.setHeightTV((int)mensagemRecebida.get("height"));
                        Log.d("oitopilhas","valor das dimensões adicionadas; width = " + castApplication.getWidthTV() + " " +
                                " height = " + castApplication.getHeightTV());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG,"Sender recebe a seguinte msg do receiver = " + message);

            }
        };
        videoCastManager.addVideoCastConsumer(videoCastConsumer);

        btnCriarApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //
                if (videoCastManager.isConnected()){

                        /*Toast.makeText(getApplicationContext(),"" + videoCastManager.isConnected(),Toast.LENGTH_SHORT).show();
                        videoCastManager.sendDataMessage("teste");
                        Toast.makeText(getApplicationContext(),"Mensagem enviada ao receiver",Toast.LENGTH_SHORT).show();*/
                    try {
                        mensagem = new JSONObject();
                        mensagem.put("tipo","criarAplicacao");
                        videoCastManager.sendDataMessage(mensagem.toString());
                    } catch (TransientNetworkDisconnectionException e) {
                        e.printStackTrace();
                    } catch (NoConnectionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(MainActivity.this,CriarAplicacaoActivity.class);
                        startActivity(intent);

                }
                else {
                    Toast.makeText(getApplicationContext(),"Por favor conecte-se ao ChormeCast",Toast.LENGTH_SHORT).show();
                }

            }
        });




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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mediaRouteMenuItem = videoCastManager.addMediaRouterButton(menu,R.id.media_route_menu_item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void showOverlay() {

        if (mOverlay != null){
            mOverlay.remove();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mediaRouteMenuItem.isVisible()){
                    mOverlay = new IntroductoryOverlay.Builder(MainActivity.this)
                            .setMenuItem(mediaRouteMenuItem)
                            .setSingleTime()
                            .setTitleText("Conecte-se ao Chromecast")
                            .setOnDismissed(new IntroductoryOverlay.OnOverlayDismissedListener() {
                                @Override
                                public void onOverlayDismissed() {
                                    mOverlay = null;
                                }
                            })
                            .build();
                    mOverlay.show();
                }
            }
        },1000);
    }

}
