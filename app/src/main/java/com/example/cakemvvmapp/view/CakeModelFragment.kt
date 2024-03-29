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
import com.example.cakemvvmapp.dagger.*
import com.example.cakemvvmapp.model.CakeModel
import kotlinx.android.synthetic.main.cake_model_fragment.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


class CakeModelFragment : Fragment() {

    @Inject
    lateinit var cakeModelViewFactory: CakeModelViewFactory

    private lateinit var viewModel: CakeModelViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DaggerFragmentComponent.builder()
            .networkModule(NetworkModule(activity!!.application as MyApplication))
            .fragmentModule(FragmentModule())
            .build()
            .inject(this)


        viewModel = ViewModelProviders.of(this, cakeModelViewFactory).get(CakeModelViewModel::class.java)

        viewModel.processCall() // if make a retrofit call

   //     viewModel.getAllCakesFromDb()// if getting data from database


        val cakeList : MutableLiveData<List<CakeModel>>? = viewModel.onShowList()
        val checkProgress : MutableLiveData<Boolean>? = viewModel.getShowProgress()
        val checkDBSuccess: MutableLiveData<Boolean>? = viewModel.getShowDBSuccess()
        val checkDBAddSuccess: MutableLiveData<Boolean>? = viewModel.getShowDBAddSuccess()

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


                val adapter = CakeAdapter(t!!)
                rv_list.layoutManager = LinearLayoutManager(activity)
                rv_list.adapter = adapter

    //            viewModel.addCakeToDatabase(t)

      /*          checkDBAddSuccess?.observe(this, object : Observer<Boolean>{
                    override fun onChanged(t: Boolean?) {

                    }
                })*/
            }
        })

        //use this if getting data from database

   /*     viewModel.cakeListFromDb?.observe(this, Observer {

            Log.i("MainActivity",it.size.toString())
            val adapter = CakeAdapter(it!!)
            rv_list.layoutManager = LinearLayoutManager(activity)
            rv_list.adapter = adapter
        })*/



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

