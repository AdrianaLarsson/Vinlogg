package com.example.vinlogg.ModelClassVine

import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception
import kotlin.math.log

var stringToken: String? = null
class ClassStringToken
{
    fun getTokenCustom(){



        val mUser = FirebaseAuth.getInstance().currentUser
        if (mUser != null) {

            mUser.getIdToken(true)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val idToken = task.result?.token
                        val expirationTimestamp = task.result?.expirationTimestamp
                        stringToken = idToken

                        Log.w("DeleteActivity", "Token  === >>> " + idToken)
                        Log.w("DeleteActivity", "expirationTimestamp  === >>> " + expirationTimestamp)

                    } else {
                        task.exception

                        Log.w("wrong", "wrongwrong" + task.exception)
                    }
                }


        }


    }



}