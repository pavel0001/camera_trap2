package com.example.cameratrapv2.screens.CameraIndFragment.ViewPagerAdapter

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity, var uriList: List<Uri>): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = uriList.size

    override fun createFragment(position: Int): Fragment {
        return ViewPagerFragment().newInstance(position, uriList.get(position))
    }
    fun updateImages(inputUriList: List<Uri>){
        uriList = inputUriList
        notifyDataSetChanged()
    }


}