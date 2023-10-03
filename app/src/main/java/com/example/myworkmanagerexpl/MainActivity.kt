package com.example.myworkmanagerexpl

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.myworkmanagerexpl.ui.theme.MyWorkManagerExplTheme

class MainActivity : ComponentActivity() {
    private lateinit var workManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MyWorkManagerExplTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var stats by remember {
                        mutableStateOf("")
                    }
                    // what is the work ? = show notification
                    // what is the condition = constrains
                    // periodic or onetime? = onetime
                    workManager = WorkManager.getInstance(applicationContext)

                       val powerConstraint = Constraints.Builder().setRequiredNetworkType(networkType = NetworkType.NOT_ROAMING).setRequiresCharging(true).build()
                    val taskData = Data.Builder().putString("MESSAGE_STATUS", "Notify Done.").build()


                    //PeriodicWorkRequest
                    val request = OneTimeWorkRequest.Builder(MyWorkCls::class.java).setConstraints(powerConstraint).build()

                    var bt by remember { mutableStateOf(false) }

                    if (bt) {
                        workManager.enqueue(request)

                        workManager.getWorkInfoByIdLiveData(request.id)
                            .observe(this) {
                                    println("========== Work status: $it.status  \n")
                              //  stats= "\n"+it.state.name+"\n"
                            }
                    }
                    Column() {
                        Button(onClick = {
                            bt = !bt
                           // notifexpl("Message received from xxxxx065","Good Morning!",applicationContext)
                        }) {
                            Text(text = "show notification!")
                        }
                        Text(text = stats)
                    }
                }
            }
        }
    }
}


fun notifexpl(title: String, description: String,context: Context)
:Boolean{

    var notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel =
            NotificationChannel("101", "channel", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    var inte = Intent(context,MainActivity::class.java)

    var pendin = PendingIntent.getActivity(
        context,
        0,
        inte,
        PendingIntent.FLAG_IMMUTABLE
    )
    val notificationBuilder = NotificationCompat.Builder(context, "101")
        .setContentTitle(title)
        .setContentText(description)
        .setContentIntent(pendin)
        .setSmallIcon(android.R.drawable.star_big_on)

    var p = notificationManager.notify(1, notificationBuilder.build())

    return true

    //Notification:
    // Intent .
    // pendingIntent.
    // Notification.builder.
    // NotificationManager.
    // Notification Channel
    // version check

}
