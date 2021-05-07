package com.cc.randomuserapimvp.view

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.cc.randomuserapimvp.databinding.ActivityMainBinding
import com.cc.randomuserapimvp.model.APIData
import com.cc.randomuserapimvp.presenter.Contract
import com.cc.randomuserapimvp.presenter.Presenter
import com.cc.randomuserapimvp.util.LogToConsole
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), Contract.View {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.adapter = RecyclerViewAdapter()

        binding.progressBar.visibility = View.INVISIBLE

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)


        val presenter = Presenter(this)

        val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            LogToConsole.log("error : ${throwable.localizedMessage}")
            LogToConsole.log("cause : ${throwable.cause}")

            binding.progressBar.visibility = View.INVISIBLE
            Toast.makeText(this, "ERROR !", Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launch (errorHandler) {

            presenter.getData(6)
        }

        runOnUiThread {
            binding.progressBar.visibility = View.VISIBLE

        }

        binding.toggleTheme.setOnClickListener {

            if (isDarkThemeOn()) {
                //Light
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            } else {
                //Dark
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            }

        }

    }

    fun Context.isDarkThemeOn(): Boolean {
        return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    override fun updateRecyclerView(data : APIData) {
       data.results.forEach {
           LogToConsole.log("item : ${it.name}")
           LogToConsole.log("item picture medium : ${it.picture.medium}")
       }
        runOnUiThread {
            val adapter = binding.recyclerView.adapter as RecyclerViewAdapter
            adapter.update(data)
            binding.progressBar.visibility = View.INVISIBLE
        }

    }
}