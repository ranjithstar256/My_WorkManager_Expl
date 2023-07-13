package com.example.myworkmanagerexpl

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
                    // what is the work ? = show notification
                    // what is the condition = constrains
                    // periodic or onetime? = onetime
                    workManager = WorkManager.getInstance(applicationContext)

                       val powerConstraint = Constraints.Builder().setRequiredNetworkType(networkType = NetworkType.NOT_ROAMING).setRequiresCharging(true).build()
                   // val taskData = Data.Builder().putString("MESSAGE_STATUS", "Notify Done.").build()


                    //PeriodicWorkRequest
                    val request = OneTimeWorkRequest.Builder(MyWorkCls::class.java).setConstraints(powerConstraint).build()

                    var bt by remember { mutableStateOf(false) }

                    if (bt) {
                        WorkManager.getInstance(applicationContext).enqueue(request)

                        workManager.getWorkInfoByIdLiveData(request.id)
                            .observe(this) {
                                    println("========== Work status: $it.status  \n")
                            }
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


