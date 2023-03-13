package com.medwiz.medwizdoctor.ui.profile

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.medwiz.medwiz.model.Doctors

import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentEditProfileBinding
import com.medwiz.medwizdoctor.ui.main.MainActivity
import com.medwiz.medwizdoctor.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@AndroidEntryPoint
class EditProfileFragment:Fragment(R.layout.fragment_edit_profile),View.OnClickListener {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private var user: Doctors?=null
    private var token:String=""
    private var userId:String=""
    var currencyList = arrayOf("SELECT CURRENCY","INR", "USD")
    private var strCurrency:String="INR"
    private val getFile=registerForActivityResult(
        ActivityResultContracts.GetContent()) {
        val file: File = FileUtil2.from(requireContext(), it)
        binding.tvUpload.text = file.name
        val filePart=FileUtil2.prepareFilePart("file",file,it,requireContext())
       // viewModel.uploadFile(token,filePart,userId)


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)
        this.user=(activity as MainActivity).getUserDetails()
         setUi(user!!)
        binding.tvBack.setOnClickListener (this)
        binding.tvUpload.setOnClickListener(this)
        binding.btAddClinic.setOnClickListener  (this)
        binding.ivCamera.setOnClickListener (this)
        setCurrency()
         token=
            MedWizUtils.storeValueInPreference(requireContext(), UtilConstants.accessToken,"",false)
         userId=  MedWizUtils.storeValueInPreference(
            requireContext(),
            UtilConstants.userId,
            "",
            false
        )

        viewModel.uploadFile.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Loading -> {
                    (activity as MainActivity).showLoading()
                }

                is Resource.Success -> {
                    (activity as MainActivity).hideLoading()
                    MedWizUtils.showErrorPopup(
                        requireActivity(),
                        it.data!!.message
                    )
                }
                is Resource.Error -> {
                    (activity as MainActivity).hideLoading()
                    if(it.message==UtilConstants.unauthorized){
                        MedWizUtils.performLogout(requireContext(),requireActivity())
                    }else{
                        MedWizUtils.showErrorPopup(
                            requireActivity(),
                            it.message.toString()
                        )
                    }
                }
            }
        })


    }

    private fun setCurrency() {
        val aa = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currencyList)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        binding.spinnerCurrency.adapter = aa

        binding.spinnerCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if(position>0){
                    strCurrency=currencyList[position]
                }

            }

        }
    }

    private fun setUi(userDetails: Doctors) {
        binding.etFirstName.setText(userDetails.user.firstName)
        binding.etLastName.setText(userDetails.user.lastName)
        binding.etPhoneNumber.setText(userDetails.user.userPhoneNumber)
        binding.etMail.setText(userDetails.user.email)

        if(userDetails.user.imageUrl!=null&&userDetails.user.imageUrl.isNotEmpty()){
        Glide.with(requireContext())
            .load(user!!.user.imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(binding.profilePic)
        }
        if(userDetails.certificateUrl!=null&&userDetails.certificateUrl.isNotEmpty()){
            binding.tvBack.text="Update your Certificate"
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.tvUpload -> {
                getFile.launch("application/pdf")
               // askCameraOrGalleryOptions()
            }
            binding.ivCamera->{
                askCameraOrGalleryOptions()
            }

            binding.tvBack->{
                findNavController().navigateUp()
            }
            binding.btAddClinic->{
                findNavController().navigate(R.id.action_editProfileFragment_to_addClinicFragment)
            }

        }}


    /**
     * ## Gallery Picker
     * - The selected image from gallery returns Uri
     * this Uri we can convert image and file also
     */
    private val pickImagesLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                lifecycleScope.launch {
                    val dispatcher = this.coroutineContext
                    CoroutineScope(dispatcher).launch {
                        compressAndSetImage(uri)
                    }
                }
            }
        }

    /**
     * ## Camera Picker
     * - The selected image from gallery returns Uri
     * this Uri we can convert image and file also
     */
    private val pickCameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                photoURI?.let { uri ->
                    lifecycleScope.launch {
                        val dispatcher = this.coroutineContext
                        CoroutineScope(dispatcher).launch {
                            compressAndSetImage(uri)
                        }
                    }
                }
            }
        }

    var photoURI: Uri? = null
    private fun askCameraOrGalleryOptions() {
        if (!hasPermissions(activity as Context, UtilConstants.PERMISSIONS)) {
            val items = arrayOf("Camera", "Gallery")
            val builder = AlertDialog.Builder(requireContext())
            with(builder) {
                setTitle("Select Option")
                setItems(items) { dialog, which ->
                    if (items[which] == "Camera") {
                        photoURI = FileUtils.createImageFile(requireActivity())
                        pickCameraLauncher.launch(photoURI)
                    } else {
                        pickImagesLauncher.launch("image/*")
                    }
                }
                show()
            }
        } else {
            permissionLauncher.launch(UtilConstants.PERMISSIONS)
        }
    }
    /**
     * ## Checking RunTime permission
     */
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (!granted) {
                askCameraOrGalleryOptions()
            }
        }


    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    /**
     * ## Creating Image file Using Glide
     * @param result is the Uri
     */
    private suspend fun compressAndSetImage(result: Uri) {
        Glide.with(requireActivity()).load(result).override(800, 400)
            .into(object : Target<Drawable> {
                override fun onLoadStarted(placeholder: Drawable?) {}

                override fun onLoadFailed(errorDrawable: Drawable?) {}

                override fun onResourceReady(
                    resource: Drawable, transition: Transition<in Drawable>?
                ) {
                    val file: File = FileUtil2.from(requireContext(),result)
                    binding.profilePic.setImageDrawable(resource)//setImage(ImageSource.bitmap(bitmap));
//                    if (resource is BitmapDrawable) {
//                        lifecycleScope.launch {
//                            val file: File = FileUtils.saveFileInCache(requireActivity(), resource.bitmap,
//                                userId + System.currentTimeMillis() + ".jpg")!!
//                            withContext(Dispatchers.Main) {
//                                val filePart= FileUtil2.prepareFilePart(userId,file,result,requireContext())
//                                viewModel.uploadFile(token,filePart,userId)
//                            }
//                        }
//                    }

                    lifecycleScope.launch {withContext(Dispatchers.Main) {
                        val filePart= FileUtil2.prepareFilePart("file",file,result,requireContext())
                        viewModel.uploadFile(token,filePart,userId)
                    }
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun getSize(cb: SizeReadyCallback) {}
                override fun removeCallback(cb: SizeReadyCallback) {}
                override fun getRequest(): Request? {
                    return null
                }

                override fun setRequest(request: Request?) {}
                override fun onStart() {}
                override fun onStop() {}
                override fun onDestroy() {}
            })
    }

}