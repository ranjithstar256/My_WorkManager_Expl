package com.example.myworkmanagerexpl

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.myworkmanagerexpl.ui.theme.MyWorkManagerExplTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWorkManagerExplTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // what is the work ? = show notification
                    // what is the condition = constrains
                    // periodic or onetime? = onetime

                       val powerConstraint = Constraints.Builder().setRequiresCharging(true).build()
                    // val taskData = Data.Builder().putString("MESSAGE_STATUS", "Notify Done.").build()


                    val request = OneTimeWorkRequest.Builder(MyWorkCls::class.java).setConstraints(powerConstraint).build()

                    var bt by remember { mutableStateOf(false) }
                    if (bt) {
                        WorkManager.getInstance(applicationContext).enqueue(request)
                    }
                    Column() {
                        Button(onClick = {
                            bt = !bt
                        }) {
                            Text(text = "show notification!")
                        }
                    }
                }
            }
        }
    }
}


