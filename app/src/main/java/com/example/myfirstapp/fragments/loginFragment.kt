package com.example.myfirstapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.R
import com.example.myfirstapp.activities.UsersListActivity
import com.example.myfirstapp.databinding.FragmentLoginBinding
import com.example.myfirstapp.helpers.InputValidation
import com.example.myfirstapp.viewmodel.UserViewModelLogin
import com.google.android.material.snackbar.Snackbar


class loginFragment : Fragment() ,View.OnClickListener {
    lateinit var binding: FragmentLoginBinding
    private val activity = this
    private lateinit var inputValidation: InputValidation
    private lateinit var userViewModelLogin: UserViewModelLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userViewModelLogin = ViewModelProvider(this).get(UserViewModelLogin::class.java)
        inputValidation = context?.let { InputValidation(it) }!!
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false);
        initListeners()
        binding.userViewModelLogin = userViewModelLogin
        userViewModelLogin.checkUser.observe(viewLifecycleOwner, Observer { users ->
            users?.let {
                if(it) {
                    userActivityRedirect()
                }
                else {
                    Snackbar.make(binding.loginFragment, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show()
                }
            }
        })
        return binding.root
    }

    private fun initListeners() {
        binding.appCompatButtonLogin.setOnClickListener(this)
        binding.textViewLinkRegister.setOnClickListener(this)
    }

    private fun verifyFromSQLite() {
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
        val accountsIntent = Intent(context, UsersListActivity::class.java)
        var bundle = Bundle()
        bundle.putString("email",userViewModelLogin.inputEmail.value!!)
        emptyInputEditText()
        accountsIntent.putExtras(bundle)
        startActivity(accountsIntent)
    }
    private fun emptyInputEditText() {
        userViewModelLogin.inputEmail.value = null
        userViewModelLogin.inputPassword.value = null
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.appCompatButtonLogin -> verifyFromSQLite()
                R.id.textViewLinkRegister -> {
                    val fragment: Fragment = registerFragment()
                    val fragmentManager: FragmentManager = getActivity()!!.supportFragmentManager
                    val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, fragment)
//                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
            }
        }
    }
}