package com.example.cameratrapv2.screens.MainFragment.Recycler

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cameratrapv2.R
import com.example.cameratrapv2.activity.MainActivity
import com.example.cameratrapv2.models.CameraData
import com.example.cameratrapv2.screens.MainFragment.MainFragment
import com.example.cameratrapv2.screens.MainFragment.TouchHelper.ItemTouchHelperAdapter
import com.example.cameratrapv2.screens.MainFragment.TouchHelper.ItemTouchHelperViewHolder
import com.example.cameratrapv2.utils.MyToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

class MainRecyclerAdapter(val context: Context?,
                          mFragment: MainFragment,
var elements: MutableList<CameraData>): RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>(), ItemTouchHelperAdapter{
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val mainFragment = mFragment


    class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
    ItemTouchHelperViewHolder{
        val number = itemView.findViewById<TextView>(R.id.item_number)
        val battery = itemView.findViewById<TextView>(R.id.item_battery)
        val signal = itemView.findViewById<TextView>(R.id.item_signal)
        val storage = itemView.findViewById<TextView>(R.id.item_storage)
        val images = itemView.findViewById<TextView>(R.id.item_images)
        val last_photo = itemView.findViewById<TextView>(R.id.item_last_photo)
        val last_update = itemView.findViewById<TextView>(R.id.item_last_update)
        val refresh = itemView.findViewById<ImageButton>(R.id.item_refreah)
        val back_image = itemView.findViewById<ImageView>(R.id.background_image)

        override fun onItemSelected() {
            //TODO COLOR CHANGED
        }

        override fun onItemClear() {

        }

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

    fun setElemetList(initElements: List<CameraData>){
        this.elements = initElements.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val df = SimpleDateFormat("dd.MMM.yyyy HH:mm:ss", Locale.ROOT)
        val element = elements.get(position)
        holder.number.text = element.number
        holder.battery.text = element.battery
        holder.signal.text = element.signal
        holder.storage.text = element.storage+"%"
        holder.images.text = element.total_images.toString()
        holder.last_photo.text = df.format(Date(element.last_photo))
        holder.last_update.text = df.format(Date(element.last_update))
        Glide.with(context!!)
            .load(element.last_photo_uri)
            .placeholder(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(holder.back_image)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if(fromPosition < toPosition){
            for ( i  in fromPosition until toPosition){
                Collections.swap(elements, i, i+1)
            }
        }else {
            for(i in fromPosition downTo toPosition+1){
                Collections.swap(elements, i,i-1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        val tmp = elements.get(position)

            MaterialAlertDialogBuilder(context!!, R.style.ThemeOverlay_App_MaterialAlertDialog_negative)
            .setTitle("Delete camera")
            .setMessage("The camera will be removed. All data about her will also be deleted.\nProceed?")
            .setNeutralButton("Back"){ dialog, which ->
                notifyDataSetChanged()
            }
            .setPositiveButton("Yes") { dialog, which ->
                 elements.removeAt(position)
                 notifyItemRemoved(position)
                 mainFragment.deleteItem(tmp)
                MyToast.succes(context, "Deleted!")
            }
            .setIcon(R.drawable.ic_delete)
            .setOnDismissListener {  notifyDataSetChanged() }
            .show()
    }
}