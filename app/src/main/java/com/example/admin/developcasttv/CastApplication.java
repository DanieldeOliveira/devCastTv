package com.example.admin.developcasttv;
import android.app.Application;
import android.util.DisplayMetrics;
import android.util.Log;


import com.example.admin.developcasttv.utils.ServerUtils;
import com.google.android.libraries.cast.companionlibrary.cast.CastConfiguration;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;


import java.util.Locale;

/**
 * Created by admin on 06/05/2016.
 */
public class CastApplication extends Application {


    private static CastApplication instance;
    private int widthTV;
    private int heightTV;
    //private BackendlessUser usuarioLogado;
    public static final double VOLUME_INCREMENT = 0.05;
    public static final int PRELOAD_TIME_S = 20;
    private String nameSpaceSenderToChromeCast = String.valueOf(R.string.nameSpaceComunicaoChromeCast);
    private int contador;
    private int widthTelaSmartphone;
    private int heightTelaSmartphone;
    private DisplayMetrics metrics;



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        contador = 0;
       /* Backendless.initApp(getApplicationContext(), BackendlessSettings.APPLICATION_ID,
                BackendlessSettings.ANDROID_SECRET_KEY,BackendlessSettings.VERSION);*/

        metrics = getResources().getDisplayMetrics();
        widthTelaSmartphone = metrics.heightPixels;
        heightTelaSmartphone = metrics.widthPixels;

        String applicationId = getString(R.string.app_id);

        CastConfiguration options = new CastConfiguration.Builder(applicationId)
                .enableAutoReconnect()
                .enableCaptionManagement()
                .enableDebug()
                .enableLockScreen()
                .enableNotification()
                .enableWifiReconnection()
                .setCastControllerImmersive(true)
                .addNamespace(getString(R.string.nameSpaceComunicaoChromeCast))
                .setLaunchOptions(false, Locale.getDefault())
                .setNextPrevVisibilityPolicy(CastConfiguration.NEXT_PREV_VISIBILITY_POLICY_DISABLED)
                .addNotificationAction(CastConfiguration.NOTIFICATION_ACTION_REWIND, false)
                .addNotificationAction(CastConfiguration.NOTIFICATION_ACTION_PLAY_PAUSE, true)
                .addNotificationAction(CastConfiguration.NOTIFICATION_ACTION_DISCONNECT, true)
                .setForwardStep(10)
                .build();


        VideoCastManager.initialize(this, options);
        ServerUtils.initialize();
        Log.d("metricas","valor de widthTelefone = " + widthTelaSmartphone );
        Log.d("metricas","valor de heightTelefone = " + heightTelaSmartphone );



    }





    public static CastApplication getInstance() {
        return instance;
    }

    /*public static void inicializarBackend(Context context){

        Backendless.initApp(getInstance().getApplicationContext(), BackendlessSettings.APPLICATION_ID,
                BackendlessSettings.ANDROID_SECRET_KEY, BackendlessSettings.VERSION);
    }*/






    public String getNameSpaceSenderToChromeCast() {
        return nameSpaceSenderToChromeCast;
    }


    public int getWidthTV() {
        return widthTV;
    }

    public void setWidthTV(int widthTV) {
        this.widthTV = widthTV;
    }

    public int getHeightTV() {
        return heightTV;
    }

    public void setHeightTV(int heightTV) {
        this.heightTV = heightTV;
    }

    private void incrementaContador(int num){
        contador += num;
    }

    public int getContador() {
        return contador;
    }

    public int getWidthTelaSmartphone() {
        return widthTelaSmartphone;
    }

    public void setWidthTelaSmartphone(int widthTelaSmartphone) {
        this.widthTelaSmartphone = widthTelaSmartphone;
    }

    public int getHeightTelaSmartphone() {
        return heightTelaSmartphone;
    }

    public void setHeightTelaSmartphone(int heightTelaSmartphone) {
        this.heightTelaSmartphone = heightTelaSmartphone;
    }
}
