package br.com.caelum.fj59.carangos.adapter;

import android.view.View;

import com.squareup.picasso.Callback;

/**
 * Created by leandro on 20/01/16.
 */
public class VerificadorDeRentorno implements Callback {
    private PublicacaoAdapter.ViewHolder holder;

    public VerificadorDeRentorno(PublicacaoAdapter.ViewHolder holder) {
        this.holder = holder;
    }

    @Override
    public void onSuccess() {
        holder.progress.setVisibility(View.GONE);
    }

    @Override
    public void onError() {

    }
}
