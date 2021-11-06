
package com.example.newsapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.example.newsapp.utility.NotificationUtility
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
     lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intent= Intent(applicationContext ,NewsService::class.java)
        intent.putExtra("url","https://google.com")
        var constaint=Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiresBatteryNotLow(true).build()
        // var oneTimeWorkRequest=OneTimeWorkRequestBuilder<MyTask>().build()
        var periodicWorkerReqest=PeriodicWorkRequestBuilder<MyTask>(15,TimeUnit.MINUTES).setInitialDelay(5,TimeUnit.SECONDS).setConstraints(constaint) .build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork("newWorker",ExistingPeriodicWorkPolicy.KEEP,periodicWorkerReqest)


        var toolbar=findViewById<Toolbar>(R.id.myToolbar)
        setSupportActionBar(toolbar)


       var navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
       navController= navHostFragment.findNavController()
       setupActionBarWithNavController(navController)
        var bottmomNavigationView=findViewById<BottomNavigationView>(R.id.bottomNV)

        bottmomNavigationView.setupWithNavController(navController)

//
//        var intent= Intent(applicationContext,NewsService::class.java)
//        intent.putExtra("url","https://google.com")
//var constaint=Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiresBatteryNotLow(true).build()
//       // var oneTimeWorkRequest=OneTimeWorkRequestBuilder<MyTask>().build()
//        var periodicWorkerReqest=PeriodicWorkRequestBuilder<MyTask>(15,TimeUnit.MINUTES).setInitialDelay(5,TimeUnit.SECONDS).setConstraints(constaint) .build()
//        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork("newWorker",ExistingPeriodicWorkPolicy.KEEP,periodicWorkerReqest)



       // WorkManager.getInstance(applicationContext).enqueue(oneTimeWorkRequest)
        /*

        if(!NewsService.serviceRunning){


        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            startForegroundService(intent)
        }else{

        startService(intent)
        }
        }*/
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()|| super.onSupportNavigateUp()
    }
}