package com.demo.mvvm.app.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.mvvm.app.R
import com.demo.mvvm.app.data.model.FactModel
import com.demo.mvvm.app.databinding.ActivityMainBinding
import com.demo.mvvm.app.ui.base.BaseActivity
import com.demo.mvvm.app.utils.Status
import com.demo.mvvm.app.utils.helper.NetworkHelper

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var adapter: MainAdapter
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var networkHelper: NetworkHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupUI()
        setupObserver()
        fetchFacts(true)
    }

    private fun setupUI() {
        binding.factList.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        binding.factList.addItemDecoration(
                DividerItemDecoration(
                        binding.factList.context,
                        (binding.factList.layoutManager as LinearLayoutManager).orientation
                )
        )
        binding.factList.adapter = adapter

        binding.itemsSwipetorefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
        binding.itemsSwipetorefresh.setColorSchemeColors(Color.BLACK)

        binding.itemsSwipetorefresh.setOnRefreshListener {
           fetchFacts(false)
        }
    }

    private fun setupObserver() {
        mainViewModel.facts.observe(this, Observer {
            binding.itemsSwipetorefresh.isRefreshing = false
            when (it.status) {
                Status.SUCCESS -> {
                    hideLoading()
                    it.data?.let { facts -> renderList(facts) }
                    binding.factList.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    showLoading(R.string.please_wait)
                    binding.factList.visibility = View.GONE
                }
                Status.ERROR -> {
                    hideLoading()
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    binding.factList.visibility = View.GONE
                }
            }
        })
    }

    private fun renderList(factModels: FactModel) {

        binding.toolbar.title = factModels.title ?: ""

        adapter.addData(factModels.rows!!)
        adapter.notifyDataSetChanged()
    }

    private fun fetchFacts(renderLoader: Boolean) {
        if (!networkHelper.isNetworkConnected()) {
            showSnackBar(R.string.no_internet_available)
        }
        mainViewModel.fetchFacts(renderLoader)
    }
}