package uz.gita.waterdrink

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.waterdrink.presentor.components.WatterBottle
import uz.gita.waterdrink.ui.theme.WaterDrinkTheme
import uz.gita.waterdrink.worker.WorkerHelper
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("ObsoleteSdkInt", "InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val periodic15TimeRequest =
            PeriodicWorkRequestBuilder<WorkerHelper>(15L, TimeUnit.MINUTES, 5L, TimeUnit.MINUTES)
                .build()
        val periodic30TimeRequest =
            PeriodicWorkRequestBuilder<WorkerHelper>(30L, TimeUnit.MINUTES, 5L, TimeUnit.MINUTES)
                .build()
        val periodicHourTimeRequest =
            PeriodicWorkRequestBuilder<WorkerHelper>(1L, TimeUnit.HOURS, 5L, TimeUnit.MINUTES)
                .build()


        setContent {
            WaterDrinkTheme {

                val permissionState =
                    rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

                val lifeCicleOwner = LocalLifecycleOwner.current
                DisposableEffect(key1 = lifeCicleOwner) {
                    val obcerver = LifecycleEventObserver { _, event ->

                        if (event == Lifecycle.Event.ON_RESUME) {
                            permissionState.launchPermissionRequest()
                        }
                    }
                    lifeCicleOwner.lifecycle.addObserver(obcerver)
                    onDispose { lifeCicleOwner.lifecycle.removeObserver(obcerver) }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    var usedWaterAmount by remember {
                        mutableStateOf(0)
                    }

                    val totalWaterAmount = remember {
                        3000
                    }



                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF00F9FF))
                    ) {


//                        var waterL by remember { mutableStateOf("") }

//                        TextField(
//                            value = waterL,
//                            onValueChange = {
//                                waterL = it
//                            },
//                            modifier = Modifier
//                                .padding(top = 20.dp)
//                                .padding(start = 20.dp)
//                                .width(150.dp)
//                                .height(56.dp)
//
//                        )

                        Button(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 20.dp)
                                .padding(end = 20.dp)
                                .height(50.dp)
                                .width(150.dp)
                                .background(Color(0xFF00F9FF)),
                            onClick = {
                                WorkManager.getInstance(this@MainActivity)
                                    .enqueue(periodic15TimeRequest)
                            },
                        ) {
                            Text(text = "Every 15 min")
                        }
                        Button(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 90.dp)
                                .padding(end = 20.dp)
                                .height(50.dp)
                                .width(150.dp)
                                .background(Color(0xFF00F9FF)),
                            onClick = {
                                WorkManager.getInstance(this@MainActivity)
                                    .enqueue(periodic30TimeRequest)
                            },
                        ) {
                            Text(text = "Every 30 min")
                        }



                        Button(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 160.dp)
                                .padding(end = 20.dp)
                                .width(150.dp)
                                .height(50.dp)
                                .background(Color(0xFF00F9FF)),
                            onClick = {
                                WorkManager.getInstance(this@MainActivity)
                                    .enqueue(periodicHourTimeRequest)
                            },

                            ) {
                            Text(text = "Every Hours")
                        }


                        WatterBottle(
                            totalWaterAmount = totalWaterAmount,
                            unit = "ml",
                            usedWaterAmount = usedWaterAmount,
                            modifier = Modifier.align(Alignment.Center)
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Total Amount is : $totalWaterAmount",
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 150.dp)
                        )

                        Button(
                            onClick = {
                                if (usedWaterAmount >= 3000) {
                                    usedWaterAmount = 3000
                                } else {
                                    usedWaterAmount += 300
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff279EFF)),
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 20.dp)
                        ) {
                            Text(text = "Drink")
                        }

                    }
                }
            }
        }
    }
}



