package com.example.cameratrapv2.screens.MainFragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cameratrapv2.R
import com.example.cameratrapv2.activity.MainActivity
import com.example.cameratrapv2.models.CameraData
import com.example.cameratrapv2.screens.MainFragment.Recycler.MainRecyclerAdapter
import com.example.cameratrapv2.screens.MainFragment.TouchHelper.SimpleItemTouchHelperCallback
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    lateinit var viewModel: MainFragmentViewModel
    lateinit var adapter: MainRecyclerAdapter
    lateinit var toolbar: MaterialToolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
       toolbar = view.findViewById<MaterialToolbar>(R.id.my_toolbar)
       (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_about -> Log.i("MyTag","about")
            R.id.action_cmd -> Log.i("MyTag","cmd")
            R.id.action_map -> Log.i("MyTag","map")
            else -> Log.i("MyTag","Else"+item.itemId)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel =  ViewModelProvider(this).get(MainFragmentViewModel::class.java)
    }



    override fun onStart() {
        super.onStart()
        val recycler = recycler
        adapter = MainRecyclerAdapter(context, this, viewModel.getCameraList())
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter
        val callback = SimpleItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recycler)
        //viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
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
    fun deleteItem(camera: CameraData){
        viewModel.let {
            it.delete(camera)
        }
    }


}