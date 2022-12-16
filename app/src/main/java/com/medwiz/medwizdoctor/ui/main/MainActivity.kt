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
import com.medwiz.medwizdoctor.util.CustomLoaderDialog
import com.medwiz.medwizdoctor.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var fragmentFactory: DefaultFragmentFactory
    private var userDetails: LoginResponse?=null
    private var medicineList=ArrayList<Medication>()
    private var labTestList=ArrayList<Medication>()
    private var consultation:Consultation?=null
    private var mCustomLoader: CustomLoaderDialog? = null
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }

        mCustomLoader = CustomLoaderDialog(this, true)
        supportFragmentManager.fragmentFactory = fragmentFactory
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activity_main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomDoctorBar.setupWithNavController(
            navHostFragment.navController
        )


        navController
            .addOnDestinationChangedListener { _, destination, _ ->

                when (destination.id) {
                    R.id.homeFragment -> {
                        showBottomLayout()
                    }
                    R.id.docProfileFragment -> {
                        showBottomLayout()
                    }
                    R.id.baseFragment -> {
                       hideBottomLayout()
                    }
                    R.id.loginFragment -> {
                        hideBottomLayout()
                    }
                    R.id.addDocInfoFragment -> {
                        hideBottomLayout()
                    }
                    R.id.signUpFragment -> {
                        hideBottomLayout()
                    }
                    R.id.fragmentAddPrescriptions -> {
                        hideBottomLayout()
                    }
                    R.id.fragmentSendPrescription -> {
                        hideBottomLayout()
                    }
                    R.id.previewPrescriptionFragment -> {
                        hideBottomLayout()
                    }


                }
            }

    }

    private fun hideBottomLayout() {
        binding.bottomDoctorBar.visibility = View.GONE
    }

    private fun showBottomLayout() {
        binding.bottomDoctorBar.visibility = View.VISIBLE
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

    fun setUserDetails(userDetails:LoginResponse){
        this.userDetails=userDetails;
    }

    fun getUserDetails(): LoginResponse {
        return this.userDetails!!
    }

    fun setPrescriptionData(medicineList: ArrayList<Medication>, labTestList: ArrayList<Medication>) {
        this.medicineList=medicineList
        this.labTestList=labTestList

    }

    fun getMedicineList():ArrayList<Medication>{
        return medicineList
    }

    fun getTestList():ArrayList<Medication>{
        return labTestList
    }

     fun setConsultation(consultation: Consultation){
        this.consultation=consultation
    }
    fun getConsultation(): Consultation {
        return this.consultation!!
    }
}