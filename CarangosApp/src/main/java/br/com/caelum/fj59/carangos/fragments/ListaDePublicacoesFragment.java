package br.com.caelum.fj59.carangos.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.adapter.PublicacaoAdapter;
import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.navegacao.EstadoMainActivity;

/**
 * Created by erich on 9/11/13.
 */
public class ListaDePublicacoesFragment extends Fragment
implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView publicacoesList;
    private MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.swipeRefreshLayout = (SwipeRefreshLayout)
                inflater.inflate(R.layout.publicacoes_list, container, false);

        this.swipeRefreshLayout.setOnRefreshListener(this);
        this.swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);

        this.publicacoesList = (ListView) this.swipeRefreshLayout.findViewById(R.id.publicacoes_list);
        this.activity = (MainActivity) this.getActivity();

        CarangosApplication application = activity.getCarangosApplication();

        PublicacaoAdapter adapter = new PublicacaoAdapter(getActivity(), application.getPublicacoes());
        this.publicacoesList.setAdapter(adapter);

        return this.swipeRefreshLayout;
    }

    @Override
    public void onRefresh() {
        MyLog.i("PULL TO REFRESH INICIANDO !!!");
        this.activity.alteraEstadoEEceuta(EstadoMainActivity.PULL_TO_REFRESH_REQUISITADO);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.swipeRefreshLayout.setRefreshing(false);
        this.swipeRefreshLayout.clearAnimation();
    }
}
