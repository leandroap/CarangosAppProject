package br.com.caelum.fj59.carangos.navegacao;

import android.app.Fragment;
import android.app.FragmentTransaction;


import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.fragments.ListaDePublicacoesFragment;
import br.com.caelum.fj59.carangos.fragments.ProgressFragment;

/**
 * Created by android5628 on 19/01/16.
 */
public enum EstadoMainActivity {
    INICIO {
        @Override
        public void executa(MainActivity activity) {
            activity.buscaPublicacoes();
            activity.alteraEstadoEEceuta(EstadoMainActivity.AGUARDANDO_PUBLICACOES);
        }
    }, AGUARDANDO_PUBLICACOES {
        @Override
        public void executa(MainActivity activity) {
            ProgressFragment progressFragment = ProgressFragment.comMensagem(R.string.carregando);
            this.colocaFragmentNaTela(activity, progressFragment);
        }
    }, PRIMEIRAS_PUBLICACOES_RECEBIDAS {
        @Override
        public void executa(MainActivity activity) {
            ListaDePublicacoesFragment listaDePublicacoesFragment = new ListaDePublicacoesFragment();
            this.colocaFragmentNaTela(activity, listaDePublicacoesFragment);
        }
    }, PULL_TO_REFRESH_REQUISITADO {
        @Override
        public void executa(MainActivity activity) {
            activity.buscaPublicacoes();
        }
    };

    void colocaFragmentNaTela(MainActivity activity, Fragment fragment){
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_principal, fragment);
        fragmentTransaction.commit();
    }

    public abstract void executa(MainActivity activity);
}
