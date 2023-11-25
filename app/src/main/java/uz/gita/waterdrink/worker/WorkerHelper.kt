package uz.gita.waterdrink.worker

import android.annotation.SuppressLint
import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import uz.gita.waterdrink.utils.NotificationHelper
import java.util.Locale
import javax.inject.Inject

@HiltWorker
class WorkerHelper @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
) :
    CoroutineWorker(context, workerParameters) {
    private val notificationHelper by lazy { NotificationHelper(context) }

    @SuppressLint("MissingPermission")
    override suspend fun doWork(): Result {
        notificationHelper.notificationManagerCompat.notify(1, notificationHelper.getNotification())
        return Result.success()
    }

}