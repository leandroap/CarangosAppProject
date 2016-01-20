package br.com.caelum.fj59.carangos.tasks;

import android.os.AsyncTask;

import java.io.Serializable;
import java.util.ArrayList;

import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.converter.PublicacaoConverter;
import br.com.caelum.fj59.carangos.evento.EventoPublicacoesRecebidas;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.webservice.Pagina;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by erich on 7/16/13.
 */
public class BuscaMaisPublicacoesTask extends AsyncTask<Pagina, Void, ArrayList<Publicacao>> {

    private Exception erro;
    private BuscaMaisPublicacoesDelegate delegate;
    private CarangosApplication application;

    public BuscaMaisPublicacoesTask(BuscaMaisPublicacoesDelegate delegate) {
        this.delegate = delegate;
        this.delegate.getCarangosApplication().registra(this);
    }

    public BuscaMaisPublicacoesTask(CarangosApplication application) {
        this.application = application;
        application.registra(this);
    }

    @Override
    protected ArrayList<Publicacao> doInBackground(Pagina... paginas) {
        try {
            Pagina paginaParaBuscar = paginas.length > 1? paginas[0] : new Pagina();

            Thread.sleep(2000);
            String jsonDeResposta = new WebClient("post/list?" + paginaParaBuscar).get();

            ArrayList<Publicacao> publicacoesRecebidas = new PublicacaoConverter().converte(jsonDeResposta);

            return publicacoesRecebidas;
        } catch (Exception e) {
            this.erro = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Publicacao> retorno) {
        MyLog.i("RETORNO OBTIDO!" + retorno);

        if (retorno!=null) {
            //this.delegate.lidaComRetorno(retorno);
            EventoPublicacoesRecebidas.notifica(this.application, (Serializable) retorno, true);
        } else {
            //this.delegate.lidaComErro(this.erro);
            EventoPublicacoesRecebidas.notifica(this.application, erro, false);
        }
        this.application.desregistra(this);
    }
}
