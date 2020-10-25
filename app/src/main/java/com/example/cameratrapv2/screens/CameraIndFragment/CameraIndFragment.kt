package com.example.cameratrapv2.screens.CameraIndFragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cameratrapv2.R
import com.example.cameratrapv2.activity.MainActivity
import com.example.cameratrapv2.utils.APP_ACTIVITY
import com.example.cameratrapv2.utils.RECEIVE_SMS
import com.example.cameratrapv2.utils.SEND_SMS
import com.example.cameratrapv2.utils.checkPermissions
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class CameraIndFragment : Fragment(), View.OnClickListener {
    lateinit private var viewModel: IndFragmentViewModel
    lateinit var trapNumber: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_ind, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<MaterialButton>(R.id.button_pir).setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_get_photo).setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_add_contact).setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_gps).setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_progr_first).setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_delete).setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_logs).setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_date).setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_sms).setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_progr_secend).setOnClickListener(this)







        val number = view.findViewById<TextView>(R.id.number)
        trapNumber = arguments?.getString("number")!!
        number.text = trapNumber
        viewModel = ViewModelProvider(this).get(IndFragmentViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        val logs = view?.findViewById<MaterialTextView>(R.id.logs_view)
        viewModel.logsLiveData.observe(this, Observer {
            var tmp = ""
            logs?.text = ""
            it.forEach {
                if(it.number.equals(trapNumber)) {
                    logs?.text = logs?.text.toString() + "\n" + it.toString()
                }
            }

        })
    }

    override fun onClick(view: View?) {
            if (checkPermissions(SEND_SMS) && checkPermissions(RECEIVE_SMS)){
                val myView = layoutInflater.inflate(R.layout.dialog_number_picker, null)
                        when (view?.id) {
                            R.id.button_pir -> sendCmd(1, trapNumber) // key = 1
                            R.id.button_get_photo -> sendCmd(2, trapNumber) // key = 2

                            R.id.button_gps ->
                                (activity as MainActivity).navController.navigate(R.id.action_cameraIndFragment_to_getPosFragment) // open fragment with map

                            R.id.button_add_contact ->
                                MaterialAlertDialogBuilder(
                                    requireContext(),
                                    R.style.ThemeOverlay_App_MaterialAlertDialog_info
                                )
                                    .setTitle(R.string.text_title_dialog_add_number)
                                    .setMessage(R.string.text_message_dialog_add_number)
                                    .setView(myView)
                                    .setNegativeButton(R.string.button_dialog_add_number_cancel) { dialog, which -> }
                                    .setPositiveButton(R.string.button_dialog_add_number_do) { dialog, which ->
                                        val numb =
                                            myView.findViewById<TextInputEditText>(R.id.number_from_dialog)
                                        val text = numb.text.toString()
                                        if (!text.isNullOrBlank()) {
                                            val radio_group = myView.findViewById<RadioGroup>(R.id.radio_group)
                                            when (radio_group.checkedRadioButtonId) {
                                                R.id.radio_add -> sendCmd(3, trapNumber, text)
                                                R.id.radio_delete -> sendCmd(4, trapNumber, text)
                                            }
                                       }
                                    }
                                    .show()//show dialog with chose add or delete contact  key = 3(add) key=4(delete) only with param

                            R.id.button_progr_first -> viewModel.updateCameraInfo(trapNumber,"3","5","97")//sendCmd(5, trapNumber)

                            R.id.button_delete -> Log.i("MyTag", "GET")// delete camera

                            R.id.button_logs -> Log.i("MyTag", "GET") // show logs

                            R.id.button_date -> sendCmd(6, trapNumber)

                            R.id.button_sms -> sendCmd(7, trapNumber)

                            R.id.button_progr_secend -> sendCmd(8, trapNumber)


                            else -> Log.i("MyTag", "else")
                        }
            }
        }

    fun sendCmd(key: Int, number: String, param: String =""){
        MaterialAlertDialogBuilder(requireContext(),R.style.ThemeOverlay_App_MaterialAlertDialog_negative)
            .setTitle(R.string.text_title_dialog_send_sms)
            .setMessage(R.string.text_message_dialog_send_sms)
            .setNegativeButton(R.string.button_dialog_add_number_cancel) { dialog, which -> }
            .setPositiveButton(R.string.button_dialog_add_number_do) { dialog, which ->
                viewModel.sendCmd(key, number, param)
            }
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(ContextCompat.checkSelfPermission(APP_ACTIVITY, SEND_SMS)
        == PackageManager.PERMISSION_GRANTED){
            //When user accept permission
        }
    }
}