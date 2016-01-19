package br.com.caelum.fj59.carangos.tasks;

import android.os.AsyncTask;

import java.util.List;

import br.com.caelum.fj59.carangos.converter.PublicacaoConverter;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.webservice.Pagina;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by erich on 7/16/13.
 */
public class BuscaMaisPublicacoesTask extends AsyncTask<Pagina, Void, List<Publicacao>> {

    private Exception erro;
    //private MainActivity activity;
    private BuscaMaisPublicacoesDelegate delegate;

    public BuscaMaisPublicacoesTask(BuscaMaisPublicacoesDelegate delegate) {
        this.delegate = delegate;
        this.delegate.getCarangosApplication().registra(this);
    }

    @Override
    protected List<Publicacao> doInBackground(Pagina... paginas) {
        try {
            Pagina paginaParaBuscar = paginas.length > 1? paginas[0] : new Pagina();

            Thread.sleep(2000);
            String jsonDeResposta = new WebClient("post/list?" + paginaParaBuscar).get();

            List<Publicacao> publicacoesRecebidas = new PublicacaoConverter().converte(jsonDeResposta);

            return publicacoesRecebidas;
        } catch (Exception e) {
            this.erro = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Publicacao> retorno) {
        MyLog.i("RETORNO OBTIDO!" + retorno);

        if (retorno!=null) {
            this.delegate.lidaComRetorno(retorno);
        } else {
            this.delegate.lidaComErro(this.erro);
        }
        delegate.getCarangosApplication().desregistra(this);
    }
}
