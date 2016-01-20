package br.com.caelum.fj59.carangos.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.navegacao.EstadoMainActivity;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesDelegate;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesTask;

public class MainActivity extends ActionBarActivity implements BuscaMaisPublicacoesDelegate {
    private ListView listView;
    private ArrayList<Publicacao> publicacoesArray;
    private EstadoMainActivity estado;
    private static final String ESTADO_ATUAL = "ESTADO_ATUAL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.listView = (ListView) findViewById(R.id.publicacoes_list);
        this.publicacoesArray = new ArrayList<Publicacao>();

        this.estado = EstadoMainActivity.INICIO;
    }

    public void buscaPublicacoes() {
        new BuscaMaisPublicacoesTask(this).execute();
    }

    public void alteraEstadoEEceuta(EstadoMainActivity estado){
        this.estado = estado;
        this.estado.executa(this);
    }

    public ArrayList<Publicacao> getPublicacoesArray() {
        return publicacoesArray;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyLog.i("EXECUTANDO ESTADO " + this.estado);
        this.estado.executa(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MyLog.i("SALVANDO ESTADO!");
        outState.putSerializable("LISTA", this.publicacoesArray);
        outState.putSerializable(ESTADO_ATUAL, this.estado);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        MyLog.i("RESTAURANDO ESTADO!");
        this.estado = (EstadoMainActivity) savedInstanceState.getSerializable(ESTADO_ATUAL);
        this.publicacoesArray = (ArrayList<Publicacao>) savedInstanceState.getSerializable("LISTA");
    }

    @Override
    public void lidaComRetorno(ArrayList<Publicacao> retorno) {
        CarangosApplication application = (CarangosApplication) getApplication();
        //List<Publicacao> publicacoes = application.getPublicacoes();
        //this.publicacoesArray = retorno;

        this.publicacoesArray.clear();
        this.publicacoesArray.addAll(retorno);

        this.estado = EstadoMainActivity.PRIMEIRAS_PUBLICACOES_RECEBIDAS;
        this.estado.executa(this);
    }

    @Override
    public void lidaComErro(Exception e) {

        e.printStackTrace();
        Toast.makeText(this, "Erro ao buscar dados", Toast.LENGTH_LONG).show();
    }

    @Override
    public CarangosApplication getCarangosApplication() {
        return (CarangosApplication) getApplication();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        super.onStop();
        getCarangosApplication().cancela();
    }
}
