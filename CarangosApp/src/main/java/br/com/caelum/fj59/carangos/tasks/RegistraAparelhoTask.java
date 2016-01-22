package br.com.caelum.fj59.carangos.tasks;

import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.gcm.Constantes;
import br.com.caelum.fj59.carangos.gcm.InformacoesDoUsuario;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by leandro on 21/01/16.
 */
public class RegistraAparelhoTask extends AsyncTask<Void, Void, String> {

    private CarangosApplication application;

    public RegistraAparelhoTask(CarangosApplication application) {
        this.application = application;
    }

    @Override
    protected String doInBackground(Void... params) {
        String registrationId = null;

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this.application);
        try {
            registrationId = gcm.register(Constantes.GCM_SERVER_ID);
            MyLog.i("Aparelho registrado com o ID: " + registrationId);

            String email = InformacoesDoUsuario.getEmail(this.application);

            String url = "device/register/"+email+"/"+registrationId;
            WebClient client = new WebClient(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return registrationId;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        application.lidaComRespostaDoRegistroNoServidor(result);
    }
}
