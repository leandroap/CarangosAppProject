package br.com.caelum.fj59.carangos.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.modelo.Lance;
import br.com.caelum.fj59.carangos.tasks.BuscaLeiloesTask;
import br.com.caelum.fj59.carangos.tasks.CustomHandle;

public class LeilaoActivity extends ActionBarActivity {

    private Calendar horarioUltimaBusca = Calendar.getInstance();

    private List<Lance> lancesAteMomento = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leilao);

        ListView lancesList = (ListView) findViewById(R.id.lances_list);

        ArrayAdapter<Lance> adapter = new ArrayAdapter<Lance>(
                LeilaoActivity.this, android.R.layout.simple_list_item_1,
                lancesAteMomento);

        lancesList.setAdapter(adapter);

        CustomHandle handle = new CustomHandle(adapter, lancesAteMomento);
        new BuscaLeiloesTask(handle, horarioUltimaBusca).executar();
    }


}
