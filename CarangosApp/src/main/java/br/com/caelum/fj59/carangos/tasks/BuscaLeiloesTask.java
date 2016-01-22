package br.com.caelum.fj59.carangos.tasks;

import android.os.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by leandro on 22/01/16.
 */
public class BuscaLeiloesTask extends TimerTask {
    private CustomHandle handle;
    private Calendar horarioUltimaBusca;

    public BuscaLeiloesTask(CustomHandle handle, Calendar horarioUltimaBusca) {
        this.handle = handle;
        this.horarioUltimaBusca = horarioUltimaBusca;
    }

    @Override
    public void run() {
        MyLog.i("Efeutuando nova busca!");

        WebClient webClient = new WebClient("leilao/leilaoid54635/" +
                new SimpleDateFormat("ddMMyy-HHmmss")
                        .format(horarioUltimaBusca.getTime()));

        String json = webClient.get();

        MyLog.i("Lances recebidos: "+json);

        Message message = handle.obtainMessage();
        message.obj = json;
        handle.sendMessage(message);

        horarioUltimaBusca = Calendar.getInstance();
    }

    public void executar(){
        Timer timer = new Timer();
        timer.schedule(this, 0, 30*1000);
    }
}
