package com.example.cameratrapv2.screens.CameraAddFragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cameratrapv2.R
import com.example.cameratrapv2.activity.MainActivity
import com.example.cameratrapv2.models.CameraData
import com.example.cameratrapv2.screens.MainFragment.MainFragmentViewModel
import com.example.cameratrapv2.utils.MyToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_camera_add.*


class CameraAddFragment : DialogFragment() {
    val RESULT_OK = -1
    lateinit var viewModel: CameraAddViewModel
    lateinit var mainFragmentViewModel: MainFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(CameraAddViewModel::class.java)
        mainFragmentViewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)


        return inflater.inflate(R.layout.fragment_camera_add, null, false)
    }


    override fun onStart() {
        super.onStart()
        val alertDialog =  MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_App_MaterialAlertDialog_info)
            .setTitle("Added number")
            .setMessage("You really wont added camera number from contacts?")
            .setNegativeButton("No") { dialog, which -> }

        button_add.setOnClickListener {                           //When user click ADD
            val number = editText.text.toString().let {
                if( ! it.isNullOrEmpty()) {
                    viewModel.insert(CameraData(it)) // insert number in Room DB
                    MyToast.succes(requireContext())//Toast
                    mainFragmentViewModel.runLoaderMmsData()
                    (activity as MainActivity).navController.navigate(R.id.action_cameraAddFragment_to_mainFragment)
                }
            }
        }
        editText.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                val DRAWABLE_RIGHT = 2
                if(event?.action == MotionEvent.ACTION_UP ){
                    if(event.rawX >= (editText.right - editText.compoundDrawables[DRAWABLE_RIGHT].bounds.width())){
                           alertDialog.setPositiveButton("Yes") { dialog, which ->
                           val intentPickCont = Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI)
                           intentPickCont.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE)
                           startActivityForResult(intentPickCont, 2)
                       }.show()
                        return true
                    }
                }
                return false
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            2 -> if(resultCode == RESULT_OK ){
                lateinit var number: String
                try {
                    val uri = data?.data
                    val cursor = context?.contentResolver?.query(uri!!,null,null,null,null)
                    cursor?.moveToFirst()
                    number = cursor?.getString(cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))!!
                }catch(e: Exception){
                    e.printStackTrace()
                }
                number = number.replace("+","")
                if( ! viewModel.isThisInBase(number)) editText.setText(number);
                else MyToast.error(requireContext(),"Number already added")
            }
            else MyToast.cancel(requireContext())
        }

    }


}

