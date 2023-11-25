package uz.gita.waterdrink.utils

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import uz.gita.waterdrink.R
import java.util.Locale
import javax.inject.Inject


class NotificationHelper @AssistedInject constructor(private val context: Context)  {
    private val notificationBuilder: NotificationCompat.Builder by lazy {
        NotificationCompat.Builder(context, "Notification")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("200 ml suv ichish vaqti bo'ldi!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
    }
    val notificationManagerCompat: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(
            context
        )
    }

    @SuppressLint("NewApi")
    fun getNotification(): Notification {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "First"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("Notification", name, importance)
            notificationManagerCompat.createNotificationChannel(mChannel)
        }else{
            val name2 = "First"
            val importance2 = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel2 = NotificationChannel("Notification", name2, importance2)
            notificationManagerCompat.createNotificationChannel(mChannel2)
        }

        return notificationBuilder.build()
    }


}