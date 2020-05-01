package com.example.vinlogg

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.telecom.CallScreeningService
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinlogg.Fragments.MyWineListFragment
import com.example.vinlogg.ModelClassVine.ClassStringToken
import com.example.vinlogg.ModelClassVine.LoginClass
import com.example.vinlogg.R.layout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.signup_activity.*
import org.json.JSONException
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.login_activity)

        signUpAct()
        auth = FirebaseAuth.getInstance()



        loginBtn.setOnClickListener {


            doLogin()


        }


    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun doLogin() {

        if (userEmailLogin.text.toString().isEmpty()) {
            userEmailLogin.error = "Snälla skrv in Email adress"
            userEmailLogin.requestFocus()

            return

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmailLogin.text.toString()).matches()) {

            userEmailLogin.error = "Snälla skrv in rätt Email-adress"
            userEmailLogin.requestFocus()
            return

        }



        if (passwordLogin.text.toString().isEmpty()) {
            passwordLogin.error = "Snälla skriv in lösenord"
            passwordLogin.requestFocus()
            return

        }





        auth.signInWithEmailAndPassword(
            userEmailLogin.text.toString(),
            passwordLogin.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    ClassStringToken().getTokenCustom()

                    // token()
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", ", signInWithEmail:success")
                    Toast.makeText(
                        baseContext, "Du loggaa nu in",
                        Toast.LENGTH_SHORT
                    ).show()
                    val user = auth.currentUser
                    updateUI(user)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Email adressen finns inte",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                    // ...
                }

                // ...
            }

    }

    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {

            var intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
            finish()
        } else {

        }

    }


    fun signUpAct() {


        textViewSignIn.setOnClickListener {

            startActivity(Intent(this, SignUpActivity::class.java))

        }

    }

    override fun onBackPressed() {
        val mUser = FirebaseAuth.getInstance().currentUser
        if (mUser == null) {
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }
}


   /* fun token() {


        var idToken: String? = null

        val mUser = FirebaseAuth.getInstance().currentUser
        if (mUser != null) {
            mUser.getIdToken(true)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        idToken = task.result?.token
                        val expirationTimestamp = task.result?.expirationTimestamp
                        Log.w("IdToken", "Token  === >>> " + idToken)
                        Log.w("IdToken", "expirationTimestamp  === >>> " + expirationTimestamp)

                    } else {
                        task.exception

                        Log.w("wrong", "wrongwrong" + task.exception)
                    }
                }


        }



        idToken?.let {
            auth.signInWithCustomToken(it)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithCustomToken:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithCustomToken:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        }


    }
}









*/















/* fun token(){



        var idToken : String?= null

        val mUser = FirebaseAuth.getInstance().currentUser
        if (mUser != null) {
            mUser.getIdToken(true)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        idToken = task.result?.token
                        val expirationTimestamp = task.result?.expirationTimestamp
                        Log.w("IdToken", "Token  === >>> " + idToken)
                        Log.w("IdToken", "expirationTimestamp  === >>> " + expirationTimestamp)

                    } else {
                        task.exception

                        Log.w("wrong", "wrongwrong" + task.exception)
                    }
                }





        }



        idToken?.let {
            auth.signInWithCustomToken(it)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithCustomToken:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithCustomToken:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }





    }*/


