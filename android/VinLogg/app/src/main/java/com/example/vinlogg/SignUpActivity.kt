package com.example.vinlogg

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.vinlogg.ModelClassVine.FetchApi
import com.example.vinlogg.ModelClassVine.PostNewUserFirebaseRealTimeDatabse
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.signup_activity.*




var  postFirstName : String? = null
var  postLastName: String? = null
var  postUserName : String? = null
var  postEmail : String? = null
var  postPassword : String? = null
class SignUpActivity : AppCompatActivity() {


    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity)

        auth = FirebaseAuth.getInstance()
        var signInBtn: Button? = null

        signInBtn = findViewById(R.id.btnSignIn)




        signInBtn?.setOnClickListener {

           signUpUser()




            //postFirebase()


        }


        toLoginSide()

    }


  fun signUpUser() {

      if (signInFirstName.text.toString().isEmpty()){
          signInFirstName.error = "Skriv in ditt Förnamn"
          signInFirstName.requestFocus()
          return
      }

      if (signInLastName.text.toString().isEmpty()){
          signInLastName.error = "Skriv in ditt Efternamn"
          signInLastName.requestFocus()
          return
      }

      if (signInUserName.text.toString().isEmpty()){
          signInUserName.error = "Skriv in ditt Anvädarnamn"
          signInUserName.requestFocus()
          return
      }

      if (signInEmail.text.toString().isEmpty()) {
          signInEmail.error = "Skrv in Email adress"
          signInEmail.requestFocus()

          return

      }



      if (!Patterns.EMAIL_ADDRESS.matcher(signInEmail.text.toString()).matches()) {

          signInEmail.error = "Skrv in rätt Email-adress"
          signInEmail.requestFocus()
          return

      }

      if (signInPassword.text.toString().isEmpty()) {
          signInPassword.error = "Snälla skriv in lösenord"
          signInPassword.requestFocus()

         var lengtP= signInPassword.text.toString()
          signInPassword.setError(getString(R.string.PassWordCharacter))


          Log.w("PASSWORD", "LENGHT" + lengtP.toInt())
          return

      }





      auth.createUserWithEmailAndPassword(
          signInEmail.text.toString(),
          signInPassword.text.toString()
      )
          .addOnCompleteListener(this) { task ->
              if (task.isSuccessful) {


                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                  Log.d("TAG", "createUserWithEmail:success")

                  auth = FirebaseAuth.getInstance()
                  val userId = auth!!.currentUser!!.uid
                  val user = task.result!!.user


                  val db = FirebaseDatabase.getInstance()
                  val myRef = db!!.getReference("Users")
                  val users = PostNewUserFirebaseRealTimeDatabse(

                      "${signInFirstName.text}",
                      "${signInLastName.text}",
                      "${signInUserName.text}",
                      "${signInEmail.text}"

                  )
                  myRef.child(userId).setValue(users)


                  Log.w("USERUIDDDD", "Get user id " + user?.uid)
                  // updateUI(user)*//*

                  postUserName = signInUserName.text.toString()
                  FetchApi().postNewUser()

                  successSignUpAlertDialog()
                  finish()
              } else {

                  if (!task.isSuccessful) {
                      try {
                          throw task.exception!!
                      } catch (task: FirebaseAuthWeakPasswordException) {
                          task.reason//  show error toast to user or do something
                      } catch (e: FirebaseAuthInvalidCredentialsException) { //  show error toast to user or do something
                      } catch (e: FirebaseNetworkException) { //  show error toast to user or do something
                      } catch (e: Exception) {
                          Log.e("LOGTAG", e.message)
                      }
                      Log.w("LOGTOG", "signInWithEmail:failed", task.exception)
                  }


                  // If sign in fails, display a message to the user.
                  Log.w("TAG", "createUserWithEmail:failure", task.exception?.cause)


                  Toast.makeText(baseContext, "Sign up Failed", Toast.LENGTH_SHORT).show()
                  //  updateUI(null)


              }


              // ...
          }




    }

fun successSignUpAlertDialog(){

    val builder = AlertDialog.Builder(this)
    builder.setTitle("VÄLKOMMEN")
    builder.setMessage("Du är nu medlem!")


    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
        Toast.makeText(applicationContext,
            android.R.string.yes, Toast.LENGTH_SHORT).show()
    }

    builder.setNegativeButton(android.R.string.no) { dialog, which ->
        Toast.makeText(applicationContext,
            android.R.string.no, Toast.LENGTH_SHORT).show()
    }

    builder.setNeutralButton("Maybe") { dialog, which ->
        Toast.makeText(applicationContext,
            "Maybe", Toast.LENGTH_SHORT).show()
    }
    builder.show()
}




    fun toLoginSide() {


        var txtLoginBtnSide = findViewById<TextView>(R.id.loginSide)

        txtLoginBtnSide.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)


        }
    }



    fun postFirebase(){


        val userId = auth!!.currentUser!!.uid
        val db = FirebaseDatabase.getInstance()
        val myRef = db!!.getReference("Users")
        val users = PostNewUserFirebaseRealTimeDatabse(

            "${signInFirstName.text}",
            "${signInLastName.text}",
            "${signInUserName.text}",
            "${signInEmail.text}"


        )


        Log.w("FIRSTNAME", "Firstname" + signInFirstName.text + userId)

        myRef.child(userId).setValue(users)
        finish()


    }



}







/*

private fun signUpUser() {


    if (signInEmail.text.toString().isEmpty()) {
        signInEmail.error = "Snälla skrv in Email adress"
        signInEmail.requestFocus()

        return

        if (Patterns.EMAIL_ADDRESS.matcher(signInEmail.text.toString()).matches()) {

            signInEmail.error = "Snälla skrv in rätt Email-adress"

            signInEmail.requestFocus()
            return

        }

        if (signInPassword.text.toString().isEmpty()) {
            signInPassword.error = "Snälla skriv in lösenord"
            signInPassword.requestFocus()
            return

        }


        auth.createUserWithEmailAndPassword(signInEmail.text.toString(), signInPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var intent = Intent(this, LoginActivity::class.java)

                    startActivity(intent)
                    finish()

                      Log.d("TAG", "createUserWithEmail:success")
                      val user = auth.currentUser
                      updateUI(user)*//*

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Sign up Failed", Toast.LENGTH_SHORT).show()
                    //  updateUI(null)
                }

                // ...
            }


    }

*/
