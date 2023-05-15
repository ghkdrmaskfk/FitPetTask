package com.hoon.fitpettask.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hoon.domain.model.RecordItem
import com.hoon.fitpettask.databinding.RvBottomWatherBinding
import com.hoon.fitpettask.databinding.RvHeaderWeatherBinding
import com.hoon.fitpettask.databinding.RvWeatherBinding
import com.hoon.fitpettask.utils.GlideTarget

private const val HEADER = 0 // 헤더 뷰
private const val ITEM = 1 // 리사이클러 아이템 뷰
private const val BOTTOM = 2 // 바텀 뷰

class WeatherRvAdapter(private val viewModel: WeatherViewModel): ListAdapter<RecordItem, RecyclerView.ViewHolder>(diffUtil) {
    private lateinit var headerVH: HeaderVH
    private lateinit var bottomVH: BottomVH
    private lateinit var weatherVH: WeatherVH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            HEADER -> {
                headerVH = HeaderVH(RvHeaderWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                headerVH
            }

            BOTTOM -> {
                bottomVH = BottomVH(RvBottomWatherBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                bottomVH
            }

            ITEM -> {
                weatherVH = WeatherVH(RvWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                weatherVH
            }

            else -> {
                throw ClassCastException("Unknown viewType $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WeatherVH -> {
                getItem(position)?.let {
                    holder.bind(it as RecordItem.Item)
                }
            }

            is HeaderVH -> {
                getItem(position)?.let {
                    holder.bind(it as RecordItem.Header)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is RecordItem.Header -> HEADER
            is RecordItem.Item -> ITEM
            is RecordItem.Bottom -> BOTTOM
        }
    }


    companion object{
        val diffUtil = object: DiffUtil.ItemCallback<RecordItem>(){
            override fun areItemsTheSame(oldItem: RecordItem, newItem: RecordItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RecordItem, newItem: RecordItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class WeatherVH(private val binding: RvWeatherBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: RecordItem.Item){
            binding.apply {
                dateTv.text = viewModel.intervalBetweenDate(data.item.dt)
                temperatureTv.text = viewModel.getTemperature(data.item.temp.min, data.item.temp.max)

                data.item.weathers[0]?.let {
                    val resourceID = binding.root
                        .resources
                        .getIdentifier(
                            viewModel.updateWeatherIcon(it.id),
                            "drawable",
                            binding.root.context.packageName)

                    Glide.with(binding.root).load(resourceID).into(GlideTarget(weatherIv))

                    descriptionTv.text = it.description
                }
            }
        }
    }

    inner class HeaderVH(private val binding: RvHeaderWeatherBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: RecordItem.Header){
            binding.apply {
                cityName.text = data.cityName
            }
        }
    }

    inner class BottomVH(private val binding: RvBottomWatherBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(){

        }
    }
}