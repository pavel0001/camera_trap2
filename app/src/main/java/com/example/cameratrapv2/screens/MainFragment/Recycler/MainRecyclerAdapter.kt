package com.example.cameratrapv2.screens.MainFragment.Recycler

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.cameratrapv2.R
import com.example.cameratrapv2.activity.MainActivity
import com.example.cameratrapv2.models.CameraData
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.util.*

class MainRecyclerAdapter(context: Context?): RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var elements = emptyList<CameraData>()
    private val cont = context;



    inner class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val number = itemView.findViewById<TextView>(R.id.item_number)
        val battery = itemView.findViewById<TextView>(R.id.item_battery)
        val signal = itemView.findViewById<TextView>(R.id.item_signal)
        val storage = itemView.findViewById<TextView>(R.id.item_storage)
        val images = itemView.findViewById<TextView>(R.id.item_images)
        val last_photo = itemView.findViewById<TextView>(R.id.item_last_photo)
        val last_update = itemView.findViewById<TextView>(R.id.item_last_update)
        val refresh = itemView.findViewById<ImageButton>(R.id.item_refreah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView = inflater.inflate(R.layout.card_view_for_recycler, parent, false)



        val mainViewHolder = MainViewHolder(itemView).apply {
            refresh.setOnClickListener {
               //TODO refresh
            }


        }
        val onClickIndividual = View.OnClickListener {
            val position = mainViewHolder.adapterPosition
            val bundle = Bundle()
            bundle.putString("number", position?.let { it1 -> elements.get(it1).number })
            (it.context as MainActivity).navController.navigate(R.id.action_mainFragment_to_cameraIndFragment, bundle)
        }
        itemView.setOnClickListener(onClickIndividual)
        return mainViewHolder
    }

    override fun getItemCount() = elements.size

    fun setElemetList(elements: List<CameraData>){
        this.elements = elements
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val element = elements.get(position)
        holder.number.text = element.number
        holder.battery.text = element.battery
        holder.signal.text = element.signal
        holder.storage.text = element.storage
        holder.images.text = element.total_images.toString()
        holder.last_photo.text = Date(element.last_photo).toString()
        holder.last_update.text = Date(element.last_update).toString()
    }
}