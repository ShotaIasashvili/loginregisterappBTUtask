package com.example.myfirstapp.activities
import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.MainActivity
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.ActivityUsersListBinding
import com.example.myfirstapp.fragments.loginFragment
import com.example.myfirstapp.fragments.registerFragment
import com.example.myfirstapp.viewmodel.UserInfoViewModel

class UsersListActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityUsersListBinding
    private lateinit var userInfoViewModel: UserInfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar!!.hide();
        super.onCreate(savedInstanceState)
        val bundle: Bundle? = intent.extras
        val email:String? = bundle?.getString("email")
        userInfoViewModel = ViewModelProvider(this).get(UserInfoViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_users_list)
        binding.userInfoViewModel = userInfoViewModel
        if (email != null) {
            userInfoViewModel.findByEmail(email)
        }
    }
    override fun onClick(v: View?) {
    }
    fun redirectToLogin(view: View) {
        val mainIntent = Intent(applicationContext, MainActivity::class.java)
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(mainIntent)
        finish()
    }
}