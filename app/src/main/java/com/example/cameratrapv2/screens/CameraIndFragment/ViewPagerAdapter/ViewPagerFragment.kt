package com.example.cameratrapv2.screens.CameraIndFragment.ViewPagerAdapter


import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.cameratrapv2.R

const val ARGUMENT_PAGE_NUMBER = "arg_page_number"
const val ARGUMENT_PAGE_URI = "arg_page_uri"
class ViewPagerFragment: Fragment() {
    fun newInstance(pos: Int, uri: Uri): Fragment{
        val pageFragment = ViewPagerFragment()
        val arguments = Bundle()
        arguments.putInt(ARGUMENT_PAGE_NUMBER, pos)
        arguments.putString(ARGUMENT_PAGE_URI, uri.toString())
        pageFragment.setArguments(arguments)
        return pageFragment
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)
        var pageNumber = arguments?.getInt(ARGUMENT_PAGE_NUMBER)
        var pageUri = Uri.parse(arguments?.getString(ARGUMENT_PAGE_URI))
        val imgViewPhoto = view.findViewById<ImageView>(R.id.imgViewPhoto)
        Glide.with(view)
            .load(pageUri)
            .placeholder(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(imgViewPhoto)
        return view
    }
}