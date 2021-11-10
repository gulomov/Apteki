package com.example.apteki.ui.login

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import com.example.apteki.databinding.FragmentLogInBinding
import androidx.navigation.fragment.findNavController
import com.example.apteki.utils.*
import android.os.Looper
import android.R

import androidx.navigation.NavOptions
import androidx.navigation.Navigation


class LogInFragment : Fragment() {

    private var _binding: FragmentLogInBinding? = null
    private lateinit var passwordEdit: EditText
    private lateinit var passwordIcon: ImageButton
    private lateinit var loginEdit: EditText
    private lateinit var navOptions: NavOptions

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        setUpViews()
        setUpClickListener()
        goNext()
        navOptions = NavOptions.Builder()
            .setPopUpTo(com.example.apteki.R.id.nav_logIn, true)
            .build()
        return binding.root
    }

    private fun setUpViews() {

        passwordIcon = binding.passwordIcon
        loginEdit = binding.loginEditText
        passwordEdit = binding.passwordEditText
        passwordEdit.transformationMethod = null
        passwordEdit.transformationMethod = PasswordTransformation()
    }
    /*TODO write done option in EditText*/

    private fun setUpClickListener() {
        var clicked = false
        passwordIcon.setOnClickListener {
            if (!clicked) {
                passwordIcon.setImageResource(com.example.apteki.R.drawable.ic_lock_open)
                passwordEdit.transformationMethod = null
                clicked = true
            } else {
                passwordIcon.setImageResource(com.example.apteki.R.drawable.ic_lock)
                passwordEdit.transformationMethod = PasswordTransformation()
                clicked = false
            }
        }
    }

    private fun goNext() {
        binding.logInBtn.setOnClickListener {
            if (loginEdit.text.isNotEmpty() && passwordEdit.text.isNotEmpty()) {
                findNavController().popBackStack(
                    com.example.apteki.R.id.action_nav_logIn_to_nav_branches,
                    true
                )
                val bundle = Bundle()
                findNavController().navigate(
                    com.example.apteki.R.id.action_nav_logIn_to_nav_branches,
                    bundle, navOptions
                )

            } else {
                if (loginEdit.text.isEmpty() && passwordEdit.text.isEmpty()) {
                    binding.toastText.text =
                        resources.getString(com.example.apteki.R.string.both_empty)
                } else if (loginEdit.text.isEmpty()) {
                    binding.toastText.text =
                        resources.getString(com.example.apteki.R.string.login_empty)
                } else if (passwordEdit.text.isEmpty()) {
                    binding.toastText.text =
                        resources.getString(com.example.apteki.R.string.password_empty)
                }
                animUp(binding.toastText)
                Handler(Looper.getMainLooper()).postDelayed({
                    animDown(binding.toastText)
                }, 1000)
            }
        }
    }

}