package com.example.cakemvvmapp.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.cakemvvmapp.R
import com.example.cakemvvmapp.model.CakeModel
import kotlinx.android.synthetic.main.cake_model_fragment.*

class CakeModelFragment : Fragment() {

    companion object {
        fun newInstance() = CakeModelFragment()
    }

    private lateinit var viewModel: CakeModelViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(CakeModelViewModel::class.java)
        viewModel.processCall()
        val cakeList : MutableLiveData<List<CakeModel>>? = viewModel.onShowList()
        val checkProgress : MutableLiveData<Boolean>? = viewModel.getShowProgress()

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
            }
        })
        return inflater.inflate(R.layout.cake_model_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        error_include.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}

