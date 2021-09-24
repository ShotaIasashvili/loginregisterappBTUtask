package com.example.myfirstapp.activities
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.ActivityLoginBinding
import com.example.myfirstapp.helpers.InputValidation
import com.example.myfirstapp.viewmodel.UserViewModelLogin
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity(){
    lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModelLogin: UserViewModelLogin
    private val activity = this
    private lateinit var inputValidation: InputValidation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide();
        userViewModelLogin = ViewModelProvider(this).get(UserViewModelLogin::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.userViewModelLogin = userViewModelLogin
        inputValidation = InputValidation(activity)
        userViewModelLogin.checkUser.observe(this, Observer { users ->
            users?.let {
                if(it) {
                    userActivityRedirect()
                }
                else {
                    Snackbar.make(binding.nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show()
                }
             }
            })
    }
    fun redirectToRegister(view:View) {
        val intentRegister = Intent(applicationContext,RegisterActivity::class.java)
        startActivity(intentRegister)
    }
     fun verifyFromSQLite(view:View) {
        if (!inputValidation.isInputEditTextFilled(binding.textInputEditTextEmail,
                binding.textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation.isInputEditTextEmail(binding.textInputEditTextEmail,
                binding.textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation.isInputEditTextFilled(binding.textInputEditTextPassword,
                binding.textInputLayoutPassword, getString(R.string.error_message_email))) {
            return
        }
        userViewModelLogin.findByEmail()
    }
    private fun userActivityRedirect() {
        val accountsIntent = Intent(activity, UsersListActivity::class.java)
        var bundle = Bundle()
        bundle.putString("email",binding.textInputEditTextEmail.text.toString().trim { it <= ' ' })
        emptyInputEditText()
        accountsIntent.putExtras(bundle)
        startActivity(accountsIntent)
    }
    private fun emptyInputEditText() {
        binding.textInputEditTextEmail.text = null
        binding.textInputEditTextPassword.text = null
    }

}
