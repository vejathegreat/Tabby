package com.velaphi.tabby.ui.view

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.velaphi.tabby.R
import com.velaphi.tabby.ui.viewmodel.TabbyViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mainAdapter: MainAdapter
    private lateinit var mainRecyclerview: RecyclerView

    private val KEY_RECYCLER_STATE = "recycler_state"
    private var mBundleRecyclerViewState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         mainRecyclerview = findViewById(R.id.main_recyclerview)
        checkNetworkState()
        setupRecyclerview()
        setUpViewModelData()
    }

    private fun checkNetworkState() {
        if (!isNetworkAvailable()) {
            val contextView = findViewById<View>(R.id.parent_layout)
            Snackbar.make(contextView, R.string.no_network, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerview() {
        val orientation: Int = resources.configuration.orientation
        mainAdapter = MainAdapter()

        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            mainRecyclerview.layoutManager = GridLayoutManager(this, PORTRAIT_SPAN_COUNT)
        else if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            mainRecyclerview.layoutManager = GridLayoutManager(this, LANDSCAPE_SPAN_COUNT)

        mainRecyclerview.itemAnimator = DefaultItemAnimator()
        mainRecyclerview.adapter = mainAdapter
    }


    private fun setUpViewModelData() {
        val viewModel: TabbyViewModel = ViewModelProviders.of(this).get<TabbyViewModel>(
            TabbyViewModel::class.java
        )
        viewModel.imageListLiveData.observe(this,  Observer {
            if(!it.isNullOrEmpty()){
                mainAdapter.setItems(it)
                main_recyclerview.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        })
    }


    override fun onPause() {
        super.onPause()

        mBundleRecyclerViewState = Bundle()
        val listState: Parcelable? = mainRecyclerview.getLayoutManager()?.onSaveInstanceState()
        mBundleRecyclerViewState!!.putParcelable(KEY_RECYCLER_STATE, listState)
    }

    override fun onResume() {
        super.onResume()

        if (mBundleRecyclerViewState != null) {
            val listState =
                mBundleRecyclerViewState!!.getParcelable<Parcelable>(KEY_RECYCLER_STATE)
            mainRecyclerview.layoutManager?.onRestoreInstanceState(listState)
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    companion object {
        private const val PORTRAIT_SPAN_COUNT: Int = 2
        private const val LANDSCAPE_SPAN_COUNT: Int = 3
    }
}