package com.medwiz.medwizdoctor.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.medwiz.medwiz.model.Consultation
import com.medwiz.medwiz.model.Medication
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.ActivityMainBinding
import com.medwiz.medwizdoctor.model.LoginResponse
import com.medwiz.medwizdoctor.model.PatientResponse
import com.medwiz.medwizdoctor.util.CustomLoaderDialog
import com.medwiz.medwizdoctor.viewmodels.BottomSheetViewModel
import com.medwiz.medwizdoctor.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    public val bottomSheetViewModel: BottomSheetViewModel by viewModels()
    private var userDetails: PatientResponse?=null
    @Inject
    lateinit var fragmentFactory: DefaultFragmentFactory
    private var mCustomLoader: CustomLoaderDialog? = null
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mCustomLoader = CustomLoaderDialog(this, true)
        supportFragmentManager.fragmentFactory = fragmentFactory
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activity_main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomBar.setupWithNavController(navHostFragment.navController)
        navController
            .addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.loginFragment -> {
                        hideBottomLayout()
                    }
                    R.id.homeFragment->{
                        showBottomLayout()
                    }
                    R.id.profileFragment->{
                        showBottomLayout()
                    }
                    R.id.analysisFragment->{
                        showBottomLayout()
                    }
                    R.id.appointmentFragment->{
                        showBottomLayout()
                    }


                    else ->  hideBottomLayout()
                }
            }

    }

    private fun hideBottomLayout() {
        binding.bottomBar.visibility = View.GONE
    }

    private fun showBottomLayout() {
        binding.bottomBar.visibility = View.VISIBLE
    }


    fun showLoading() {
        if (mCustomLoader?.window != null) {
            (mCustomLoader?.window)!!.setBackgroundDrawableResource(android.R.color.transparent)
            mCustomLoader?.show()
        }
    }

    fun hideLoading() {
        if (mCustomLoader != null) mCustomLoader?.cancel()
    }

    fun setUserDetails(data: PatientResponse?) {
        this.userDetails=data
    }
    fun getUserDetails():PatientResponse{
        return userDetails!!
    }


}