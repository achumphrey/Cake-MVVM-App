package com.example.cakemvvmapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.cakemvvmapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment()
    }

    fun addFragment(){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.frm_container, CakeModelFragment())
            //      .addToBackStack(null)
            .commit()
    }
}


/*
-Add room database to it
-When the data is received from api,insert it into the database
-In your activity or fragment,add some logic to check if there is internet connection ( google is your friend :) )
-if there is internet,load data from retrofit
if no connection,load data from room
-INTO THE RECYCLERVIEW
*/