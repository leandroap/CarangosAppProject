package br.com.caelum.fj59.carangos.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.LeilaoActivity;
import br.com.caelum.fj59.carangos.infra.MyLog;

/**
 * Created by leandro on 21/01/16.
 */
public class GCMBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MyLog.i("Chegou a mensagem do GCM!");
        String mensagem = (String) intent.getExtras().getSerializable("message");

        MyLog.i("mensagem " + mensagem);
        Intent irParaLeilao = new Intent(context, LeilaoActivity.class);

        PendingIntent acaoPendente = PendingIntent.getActivity(context, 0, irParaLeilao, PendingIntent.FLAG_CANCEL_CURRENT);

        irParaLeilao.putExtra("idDaNotificacao", Constantes.ID_NOTIFICACAO);

        Notification notification = new Notification.Builder(context)
                .setContentTitle("Um novo leilao comecou!")
                .setContentText("Leilao: " + mensagem)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(acaoPendente)
                .build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(Constantes.ID_NOTIFICACAO, notification);
        //Toast.makeText(context, "Chegou a mensagem do GCM", Toast.LENGTH_LONG).show();

        //AlertDialog.Builder builder = new AlertDialog.Builder()
    }
}
