package com.velaphi.tabby.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.velaphi.tabby.R
import com.velaphi.tabby.data.api.ApiHelper
import com.velaphi.tabby.data.api.RetrofitBuilder
import com.velaphi.tabby.data.model.Image
import com.velaphi.tabby.data.model.Response
import com.velaphi.tabby.ui.viewmodel.MainViewModel
import com.velaphi.tabby.ui.viewmodel.ViewModelFactory
import com.velaphi.tabby.utils.Resource
import com.velaphi.tabby.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private var imageList: MutableList<Image> = arrayListOf()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerview()
        setupViewModel()
        setupObservers()
    }

    private fun setupRecyclerview() {
        val mainRecyclerview = findViewById<RecyclerView>(R.id.main_recyclerview)
        mainAdapter = MainAdapter()
        mainRecyclerview.layoutManager = GridLayoutManager(this, SPAN_COUNT)
        mainRecyclerview.itemAnimator = DefaultItemAnimator()
        mainRecyclerview.adapter = mainAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupObservers() {

        viewModel.getImages().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        imageList = getImageList(it)
                        if (!imageList.isNullOrEmpty()) {
                            main_recyclerview.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            mainAdapter.setItems(imageList)
                        }
                    }

                    Status.LOADING -> {
                        main_recyclerview.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }

                    Status.ERROR -> {
                        main_recyclerview.visibility = View.GONE
                        progressBar.visibility = View.GONE
                    }
                }
            }

        })
    }

    private fun getImageList(it: Resource<Response>): MutableList<Image> {
        return it.data?.data?.images as MutableList<Image>
    }

    companion object {
        private const val SPAN_COUNT: Int = 2
    }
}