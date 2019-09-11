package com.example.cakemvvmapp.view

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cakemvvmapp.model.CakeModel
import kotlinx.android.synthetic.main.cake_model_fragment.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class CakeModelFragment : Fragment() {

    companion object {
        fun newInstance() = CakeModelFragment()
    }

    private lateinit var viewModel: CakeModelViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

  //      var isThereInternet = verifyAvailableNetwork(AppCompatActivity())

   //     Log.d("CakeModelFrag", "$isThereInternet")


        viewModel = ViewModelProviders.of(this).get(CakeModelViewModel::class.java)
//        viewModel.processCall()
        viewModel.getAllCakesFromDb()
        val cakeList : MutableLiveData<List<CakeModel>>? = viewModel.onShowList()
        val checkProgress : MutableLiveData<Boolean>? = viewModel.getShowProgress()
        val checkDBSuccess: MutableLiveData<Boolean>? = viewModel.getShowDBSuccess()

        checkProgress?.observe(this, object : Observer<Boolean>{
            override fun onChanged(t: Boolean?) {
                if (t == false)
                    prgs_bar.visibility = View.GONE
                else
                    prgs_bar.visibility = View.VISIBLE
            }
        })

        cakeList?.observe(this, object: Observer<List<CakeModel>> {
            override fun onChanged(t: List<CakeModel>?) {
                Log.i("CakeFragment","${t?.get(0)?.title}")


//                val adapter = CakeAdapter(t!!)
//                rv_list.layoutManager = LinearLayoutManager(activity)
//                rv_list.adapter = adapter
            }
        })

        viewModel.cakeListFromDb?.observe(this, Observer {

            val adapter = CakeAdapter(it!!)
            rv_list.layoutManager = LinearLayoutManager(activity)
            rv_list.adapter = adapter
        })

        checkDBSuccess?.observe(this, object : Observer<Boolean>{
            override fun onChanged(t: Boolean?) {
                if (t == true){
                    Toast.makeText(activity,"got user successfully",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(activity,"Something went wrong with db",Toast.LENGTH_LONG).show()
                }
            }
        })

        return inflater.inflate(com.example.cakemvvmapp.R.layout.cake_model_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}

