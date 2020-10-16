package com.example.cameratrapv2.screens.MainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cameratrapv2.R
import com.example.cameratrapv2.activity.MainActivity
import com.example.cameratrapv2.screens.MainFragment.Recycler.MainRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    lateinit var viewModel: MainFragmentViewModel
    lateinit var adapter: MainRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onStart() {
        super.onStart()

        val recycler = recycler


        adapter = MainRecyclerAdapter(context)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter

        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        viewModel.allData.observe(this, Observer {
            adapter.setElemetList(it)
        })

    }

    override fun onResume() {
        super.onResume()
        floating_button.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.action_mainFragment_to_cameraAddFragment)
        }
    }


}