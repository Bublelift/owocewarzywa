/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.owocewarzywa

//import android.R
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.owocewarzywa.auth.LoginFragment


/**
 * Activity for cupcake order flow.
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve NavController from the NavHostFragment
        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set up the action bar for use with the NavController
        setupActionBarWithNavController(navController)
    }

    /**
     * Handle navigation when the user chooses Up from the action bar.
     */
    override fun onSupportNavigateUp(): Boolean {
////        val current_fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
//        val current_fragment = navController.currentDestination?.id
//        Log.i("current_fragment",current_fragment.toString())
////        if (current_fragment is LoginFragment) { //the fragment on which you want to handle your back press
//        if (current_fragment == 2131231159) { //the fragment on which you want to handle your back press
//            Log.e("BACK PRESSED", "BACK PRESSED")
//        } else {
//            return navController.navigateUp() || super.onSupportNavigateUp()
//            //super.onBackPressed()
//        }
//        return false
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

//    private fun setNavigationGraph() {
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.startFragment) as NavHostFragment
//        val navController = navHostFragment.navController
//
//        val navGraph = navController.navInflater.inflate(R.navigation.navigation_graph)
//        navGraph.startDestination =
//            if (viewModel.isLoggedIn) {
//                R.id.homeFragment
//            } else {
//                R.id.loginFragment
//            }
//
//        navController.graph = navGraph
//    }

//    override fun onBackPressed() {
//        val current_fragment = navController.currentDestination?.id
//        if (current_fragment == 2131231159) {
//            finish()
//        } else {
//            super.onBackPressed()
//        }
//    }
//    override fun onBackPressed() {
//        val current_fragment = supportFragmentManager.findFragmentById(R.id.startFragment)
//        if (current_fragment is StartFragment) { //the fragment on which you want to handle your back press
//            Log.i("BACK PRESSED", "BACK PRESSED")
//        } else {
//            super.onBackPressed()
//        }
//    }
    fun setAppBarVisibility(visible: Boolean) {
//        (visible == true) ? supportActionBar?.hide() : supportActionBar?.show()
        if (visible) supportActionBar?.hide() else supportActionBar?.show()
    }


}