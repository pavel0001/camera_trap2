package com.example.cameratrapv2.screens.GetPosFragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cameratrapv2.R
import com.example.cameratrapv2.models.CameraData
import com.example.cameratrapv2.utils.EXTRAS_ADD_ON_MAP
import com.example.cameratrapv2.utils.EXTRAS_OPEN_MAP_NUMBER
import com.example.cameratrapv2.utils.MyToast
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.switchmaterial.SwitchMaterial



class GetPosFragment : Fragment(), OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private var FLAG_ADD_COORD = false
    lateinit private var viewModel: GetPosFragmentViewModel
    lateinit private var switch: SwitchMaterial
    lateinit private var spinnerView: AppCompatSpinner
    lateinit private var spinerAdapter: ArrayAdapter<String>
    private val listForAdapter = mutableListOf<String>()
    lateinit private var trapList: List<CameraData>
    lateinit private var map: GoogleMap
    lateinit var  infoWindowAdapter: CustomInfoWindowAdapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_get_pos, container, false)
        FLAG_ADD_COORD = arguments?.getBoolean(EXTRAS_ADD_ON_MAP) ?: false
        infoWindowAdapter = CustomInfoWindowAdapter(requireContext())
        viewModel = ViewModelProvider(this).get(GetPosFragmentViewModel::class.java)

        //###############################################################
        //################ SPINER ########################################
        //#################################################################
        spinnerView = view.findViewById<AppCompatSpinner>(R.id.spinner_map_select_camera)
        spinerAdapter = ArrayAdapter<String>(requireContext(), R.layout.spiner_item_for_map, listForAdapter)
        spinnerView.setAdapter(spinerAdapter)
        spinnerView.setOnItemSelectedListener(this)




        //###############################################################
        //################ SWITCH ########################################
        //#################################################################
        switch = view.findViewById<SwitchMaterial>(R.id.switch_add_or_not)
        switch.isChecked = FLAG_ADD_COORD

        trapList = viewModel.getCameraLiveData().value ?: mutableListOf()

        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.fragment) as? SupportMapFragment
        supportMapFragment?.getMapAsync(this)

        return view
    }

    override fun onStart() {
        super.onStart()
        var startCameraNumber = arguments?.getString(EXTRAS_OPEN_MAP_NUMBER)

        viewModel.getCameraLiveData().observe(viewLifecycleOwner, Observer {

            Log.i("MyTag", "Live Data Observer ${it.toString()}")
            val tmpListString = mutableListOf<String>()
            it.forEach { tmpListString.add(it.number) }
            listForAdapter.clear()
            listForAdapter.addAll(tmpListString)
            spinerAdapter.notifyDataSetChanged()
            trapList = it
            notifyMarkersSetChanged(map)

            infoWindowAdapter.updateCameraData(it)

            if(! startCameraNumber.isNullOrEmpty()) {
                spinnerView.setSelection(listForAdapter.indexOf(startCameraNumber!!))
                startCameraNumber = ""
            }

        })
    }

    override fun onResume() {
        super.onResume()
        /* ####################################################################################
           ################### Unfreeze set camera position on the map #########################
        #######################################################################################*/
        switch.setOnCheckedChangeListener { compoundButton, b ->
            MyToast.cancel(requireContext(), b.toString())
            FLAG_ADD_COORD = b
        }






    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            googleMap.setInfoWindowAdapter(infoWindowAdapter)
            map = googleMap


        //################### OnMapLongClickListener #################################################
        googleMap.setOnMapLongClickListener {
            if (FLAG_ADD_COORD) {
                MaterialAlertDialogBuilder(
                    requireContext(),
                    R.style.ThemeOverlay_App_MaterialAlertDialog_negative
                )
                    .setTitle(R.string.text_title_dialog_set_coord)
                    .setMessage(R.string.text_message_dialog_set_coord)
                    .setNegativeButton(R.string.button_dialog_add_number_cancel) { dialog, which -> }
                    .setPositiveButton(R.string.button_dialog_add_number_do) { dialog, which ->

                        if (spinnerView.isEnabled) {
                            val spinerSelectedNumber = spinnerView.selectedItem as String
                                val selectedCamera = getCameraFromNumber(spinerSelectedNumber)
                                selectedCamera.latitude = it.latitude
                                selectedCamera.longitude = it.longitude
                                viewModel.updateCameraData(selectedCamera)
                        }
                    }.show()
            }
        }
        //########################### OnMapLongClickListener #########################################
    }
    }

    fun notifyMarkersSetChanged(googleMap: GoogleMap?){
        if(googleMap != null) {
            googleMap.clear()
            val markers = mutableListOf<MarkerOptions>()

            for (camera in trapList) {
                if (camera.latitude != 0.0 && camera.longitude != 0.0) {
                    val marker = MarkerOptions()
                        .position(LatLng(camera.latitude, camera.longitude))
                        .icon(BitmapDescriptorFactory.defaultMarker())
                        .title(camera.number)

                    markers.add(marker)
                    googleMap.addMarker(marker)
                }
            }
        }
    }
    fun getCameraFromNumber(number: String): CameraData
            = trapList.filter { it.number.equals(number) }.first()


    override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem : String? = listForAdapter.get(position)
        if (selectedItem != null) {
            val camera = getCameraFromNumber(selectedItem)
            val cameraLatLng = LatLng(camera.latitude, camera.longitude)
            val cameraPosition = CameraPosition.Builder()
                .target(cameraLatLng)
                .zoom(12F)
                .bearing(45F)
                .tilt(20F)
                .build()
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
            map.animateCamera(cameraUpdate)

        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}
