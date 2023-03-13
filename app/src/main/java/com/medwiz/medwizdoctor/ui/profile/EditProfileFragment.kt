package com.medwiz.medwizdoctor.ui.profile

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.medwiz.medwiz.model.Doctors
import com.medwiz.medwiz.model.ProfileItemModel

import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentEditProfileBinding
import com.medwiz.medwizdoctor.databinding.FragmentProfileBinding
import com.medwiz.medwizdoctor.model.User
import com.medwiz.medwizdoctor.ui.main.MainActivity
import com.medwiz.medwizdoctor.util.FileUtils
import com.medwiz.medwizdoctor.util.MedWizUtils
import com.medwiz.medwizdoctor.util.UtilConstants
import com.medwiz.medwizdoctor.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

@AndroidEntryPoint
class EditProfileFragment:Fragment(R.layout.fragment_edit_profile),View.OnClickListener {
    private lateinit var binding: FragmentEditProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    private var user: Doctors?=null
    private var adapter: ProfileItemAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)
        binding.tvBack.setOnClickListener (this)
        binding.tvUpload.setOnClickListener(this)
        binding.btAddClinic.setOnClickListener  (this)
        binding.ivCamera.setOnClickListener (this)
        this.user=(activity as MainActivity).getUserDetails()

    }

    override fun onClick(view: View?) {
        when (view) {
            binding.tvUpload -> {
                askCameraOrGalleryOptions()
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
        if (hasPermissions(activity as Context, UtilConstants.PERMISSIONS)) {
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
            if (granted) {
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
                    if (resource is BitmapDrawable) {
                        lifecycleScope.launch {
                            val file: File = FileUtils.saveFileInCache(
                                requireActivity(),
                                resource.bitmap,
                                "tmp_" + System.currentTimeMillis() + ".jpg")!!
                            withContext(Dispatchers.Main) {
//                                profileViewModel.uploadFile.value = FileUploadRequest(
//                                    file = file
//                                )
                            }
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