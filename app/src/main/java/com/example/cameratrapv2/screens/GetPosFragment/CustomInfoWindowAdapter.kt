package com.example.cameratrapv2.screens.GetPosFragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.cameratrapv2.R
import com.example.cameratrapv2.models.CameraData
import com.example.cameratrapv2.utils.MyToast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import java.io.InputStream

class CustomInfoWindowAdapter(val context: Context, var cameraData : List<CameraData> = emptyList()) : GoogleMap.InfoWindowAdapter{
    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }

    @SuppressLint("CheckResult")
    override fun getInfoContents(marker: Marker?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_for_info_window,null)
        val titleNumber = view.findViewById<TextView>(R.id.info_window_number)
        val lastImage = view.findViewById<ImageView>(R.id.info_window_image)
        val buttonAccept = view.findViewById<AppCompatButton>(R.id.info_window_button_accept)
        val number = marker?.title
        if(number != null) {
            titleNumber.text = number
            val camera = getCameraDataFromNumber(number)
            val imageUri = Uri.parse(camera.last_photo_uri)
            //val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            Glide.with(context)
                .load(imageUri)
                .override(50,50)
                .placeholder(R.drawable.ic_launcher_foreground)
               /* .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        marker.showInfoWindow()
                        return true
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        TODO("Not yet implemented")
                    }


                })*/
                .centerCrop()
                .into(lastImage)




            buttonAccept.setOnClickListener {
                MyToast.succes(context, "Accept!!!")
            }
        }
        return view
    }
    fun updateCameraData(cameraData : List<CameraData>){
        this.cameraData = cameraData ?: emptyList()
    }
    fun getCameraDataFromNumber(number: String) : CameraData
            = cameraData.filter { it.number.equals(number) }.first()

}