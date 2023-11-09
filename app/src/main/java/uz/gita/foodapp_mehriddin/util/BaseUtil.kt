package uz.gita.foodapp_mehriddin.util

import android.content.Context
import android.util.Log
import android.widget.Toast

fun myToast(message:String,context: Context){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun logger(m:String,tag:String = "TTT"){
    Log.d(tag, m)
}