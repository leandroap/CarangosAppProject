package br.com.caelum.fj59.carangos.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.adapter.PublicacaoAdapter;
import br.com.caelum.fj59.carangos.app.CarangosApplication;

/**
 * Created by erich on 9/11/13.
 */
public class ListaDePublicacoesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView publicacoesList = (ListView) inflater.inflate(R.layout.publicacoes_list, container, false);

        final MainActivity activity = (MainActivity) this.getActivity();

        CarangosApplication application = activity.getCarangosApplication();

        PublicacaoAdapter adapter = new PublicacaoAdapter(getActivity(), activity.getPublicacoesArray());
        publicacoesList.setAdapter(adapter);

        return publicacoesList;
    }

}
