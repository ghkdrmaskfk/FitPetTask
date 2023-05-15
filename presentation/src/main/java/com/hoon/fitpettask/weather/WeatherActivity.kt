package com.hoon.fitpettask.weather

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoon.data.weather.model.WeatherState
import com.hoon.fitpettask.databinding.ActivityWeatherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val weatherRvAdapter = WeatherRvAdapter(viewModel)

        binding.apply {

            weatherRv.apply {
                layoutManager = LinearLayoutManager(this@WeatherActivity, RecyclerView.VERTICAL, false)
                adapter = weatherRvAdapter
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    this@WeatherActivity.viewModel.weather.collect {
                        when (it) {
                            is WeatherState.Success -> {
                                weatherRvAdapter.submitList(it.recordList)
                            }
                            is WeatherState.Failure -> {}
                            else -> {}
                        }
                    }
                }
            }
        }

        CoroutineScope(Default).launch {
            launch {
                viewModel.getWeather(37.532600, 127.024612)
            }.join()

            launch {
                viewModel.getWeather(51.5286416, -0.1015987)
            }.join()

            launch {
                viewModel.getWeather(41.8336152, -87.8967704)
            }
        }
    }
}