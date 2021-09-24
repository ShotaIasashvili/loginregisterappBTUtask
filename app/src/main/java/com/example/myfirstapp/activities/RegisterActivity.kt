package com.example.myfirstapp.activities
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.R
import com.example.myfirstapp.Room.UserEntity
import com.example.myfirstapp.databinding.ActivityRegisterBinding
import com.example.myfirstapp.helpers.InputValidation
import com.example.myfirstapp.viewmodel.UserViewModelRegister
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity(){
    lateinit var binding: ActivityRegisterBinding
    private lateinit var userViewModelRegister: UserViewModelRegister
    private val activity = this
    private var gender = "male"
    private lateinit var inputValidation: InputValidation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide();
        userViewModelRegister = ViewModelProvider(this).get(UserViewModelRegister::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.userViewModelRegister = userViewModelRegister
        initObjects()
        userViewModelRegister.checkUser.observe(this, Observer { users ->
            users?.let {
                if (it) {
                    Snackbar.make(
                        binding.nestedScrollView,
                        getString(R.string.error_email_exists),
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    postRoomDataBase()
                }
            }
        })
    }

    private fun initObjects() {
        inputValidation = InputValidation(activity)
    }

    fun endRegisterActivity(view: View) {
        finish()
    }
     fun postDataToSQLite(view: View) {
        if (!inputValidation.isInputEditTextFilled(binding.textInputEditTextName, binding.textInputLayoutName, getString(R.string.error_message_name))) {
            return
        }
        if (!inputValidation.isInputEditTextFilled(binding.textInputEditTextEmail, binding.textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation.isInputEditTextEmail(binding.textInputEditTextEmail, binding.textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation.isInputEditTextFilled(binding.textInputEditTextPassword, binding.textInputLayoutPassword, getString(R.string.error_message_password))) {
            return
        }
        if (!inputValidation.isInputEditTextMatches(binding.textInputEditTextPassword, binding.textInputEditTextConfirmPassword,
                binding.textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return
        }
        if(!inputValidation.isInputEditTextFilled(binding.textInputEditTextAge,binding.textInputLayoutAge,getString(R.string.error_message_city))) {
            return
        }
         userViewModelRegister.findByEmail()
    }

    private fun postRoomDataBase() {
        userViewModelRegister.insert()
        Snackbar.make(binding.nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show()
        var bundle = Bundle()
        bundle.putString("email",binding.textInputEditTextEmail.text.toString().trim { it <= ' ' })
        emptyInputEditText()
        val accountsIntent = Intent(activity, UsersListActivity::class.java)
        accountsIntent.putExtras(bundle)
        startActivity(accountsIntent)
    }

    private fun emptyInputEditText() {
        binding.textInputEditTextName.text = null
        binding.textInputEditTextEmail.text = null
        binding.textInputEditTextPassword.text = null
        binding.textInputEditTextConfirmPassword.text = null
        binding.textInputEditTextAge.text  = null
    }
    fun onRadioButtonClicked(view:View) {
        if(view is RadioButton) {
            val checked = view.isChecked
            when(view.getId()) {
                R.id.maleRadio ->
                    if(checked) {
                      gender = "male"
                    }
                R.id.femaleRadio -> {
                    if(checked) {
                        gender = "female"
                    }
                }
            }
        }
    }
}

