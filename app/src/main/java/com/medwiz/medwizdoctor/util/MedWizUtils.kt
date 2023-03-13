package com.medwiz.medwizdoctor.util

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.FragmentActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.android.material.snackbar.Snackbar
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.model.CustomTimeEntity
import com.medwiz.medwizdoctor.ui.main.MainActivity
import org.json.JSONObject
import java.sql.Time
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*


object MedWizUtils {

    fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
        Intent(this, activity).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

     fun getTime(hr: Int, min: Int): String? {
        val tme = Time(hr, min, 0) //seconds by default set to zero
        val formatter: Format
        formatter = SimpleDateFormat("h:mm a")
        return formatter.format(tme)
    }
    fun showToast(message: String, activity: Context) {
        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show()
    }

     fun showErrorPopup(activity: Context, message: String) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_error_layout)
        val body = dialog.findViewById(R.id.tvPopup) as TextView
        body.text = message
        val yesBtn = dialog.findViewById(R.id.btOk) as Button
        yesBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    fun showNoInternetPopup(activity: Context) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.weak_internet_connection_popup)
        val btOk = dialog.findViewById(R.id.btOk) as Button

        btOk.setOnClickListener {
            dialog.dismiss()
        }
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    fun isValidPassword(password: String?) : Boolean {
        password?.let {
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$).{8,}"
            val passwordMatcher = Regex(passwordPattern)

            return passwordMatcher.find(password) != null
        } ?: return false
    }
    fun AlertButton(builder: AlertDialog){
        val negbuttonbackground: Button = builder.getButton(DialogInterface.BUTTON_NEGATIVE)
        negbuttonbackground.setBackgroundColor(Color.TRANSPARENT)
        negbuttonbackground.setTextColor(Color.BLACK)

        val posbuttonbackground: Button = builder.getButton(DialogInterface.BUTTON_POSITIVE)
        posbuttonbackground.setBackgroundColor(Color.TRANSPARENT)
        posbuttonbackground.setTextColor(Color.BLACK)
    }
     fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    fun View.snackbar(message: String, action: (() -> Unit)? = null) {
        val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        action?.let {
            snackbar.setAction("Retry") {
                it()
            }
        }
        snackbar.show()
    }

    fun getContentFromServer(jsonObject: JSONObject):String{
        return jsonObject.get("content").toString()
    }



    fun storeValueInPreference(context: Context,key:String,value:String,isWrite:Boolean):String{

        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val sharedPreferences = EncryptedSharedPreferences.create(context,
            "CariTagEncryptedFiles",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        return if(isWrite){
            // Write into encrypted preference
            sharedPreferences.edit {
                putString(key, value)
            }
            ""
        } else{
            // Read from encrypted preference
            sharedPreferences.getString(key, value).toString()
        }

    }
    fun getCurrentDate():String{

        val sdf = SimpleDateFormat("dd MMM yy")
        val currentDateAndTime: String = sdf.format(Date())
        return currentDateAndTime
    }
    fun changeDateFormat(inputDate:String):String{
        val sdf = SimpleDateFormat("yyyy-MMMM-dd")
        val outPutDate:String=SimpleDateFormat("yyyy-MM-dd").parse(inputDate).toString().substring(0,10)
        return outPutDate

    }
    fun getAllTime(start:Int,end:Int):ArrayList<CustomTimeEntity>{
        val lis=ArrayList<CustomTimeEntity>()
        for (i in start..end) {
            val customTime=CustomTimeEntity()
            if(i<12){
            customTime.amOrPm="am"
            customTime.time="$i:00"
            customTime.isSelected=false
            lis.add(customTime)
            }
            else{
                //all pm
                var t=i-12
                if(t==0){
                    t=12
                }

                customTime.amOrPm="pm"
                customTime.time="$t:00"
                customTime.isSelected=false
                lis.add(customTime)
            }
        }

        return lis
    }
    public fun performLogout(context: Context,requireActivity: FragmentActivity) {
        MedWizUtils.storeValueInPreference(context,UtilConstants.accessToken,"",true)
        MedWizUtils.storeValueInPreference(context,UtilConstants.userId,"",true)
        MedWizUtils.storeValueInPreference(context,UtilConstants.email,"",true)
        MedWizUtils.storeValueInPreference(context,UtilConstants.userType,"",true)
        MedWizUtils.storeValueInPreference(context,UtilConstants.docId,"",true)
        context.startActivity(Intent(requireActivity, MainActivity::class.java))
        requireActivity.finish()

    }

}