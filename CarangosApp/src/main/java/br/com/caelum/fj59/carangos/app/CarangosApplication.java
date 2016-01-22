package br.com.caelum.fj59.carangos.app;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.tasks.RegistraAparelhoTask;

/**
 * Created by android5628 on 18/01/16.
 */
public class CarangosApplication extends Application {
    private static final String ID_DO_REGISTRO = "idDoRegistro";
    private static final String REGISTRADO = "registradoNoGcm";

    private List<AsyncTask<?,?,?>> tasks = new ArrayList<AsyncTask<?, ?, ?>>();
    private ArrayList<Publicacao> publicacoes = new ArrayList<Publicacao>();
    private SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = getSharedPreferences("configs", Activity.MODE_PRIVATE);
        registraNoGcm();
    }

    private void registraNoGcm() {
        if (!usuarioRegistrado()){
            new RegistraAparelhoTask(this).execute();
        } else {
            MyLog.i("Aparelho ja cadastrado! Seu id e: "+ preferences.getString(ID_DO_REGISTRO, null));
        }
    }

    private boolean usuarioRegistrado() {
        return preferences.getBoolean(REGISTRADO, false);
    }

    public void cancela(){
        for (AsyncTask task : this.tasks){
            task.cancel(false);
            MyLog.i("CANCELAD0OOOOOO");
        }
        tasks.clear();
    }

    public void registra(AsyncTask<?,?,?> task){
        tasks.add(task);
    }

    public void desregistra(AsyncTask<?,?,?> task){
        tasks.remove(task);
    }

    public ArrayList<Publicacao> getPublicacoes(){
        return publicacoes;
    }

    public void lidaComRespostaDoRegistroNoServidor(String registro) {

        if (registro != null){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(REGISTRADO, true);
            editor.putString(ID_DO_REGISTRO, registro);
            editor.commit();
        }
    }
}
