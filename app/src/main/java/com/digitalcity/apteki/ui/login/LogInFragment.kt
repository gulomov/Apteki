package com.digitalcity.apteki.ui.login

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.os.Looper
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.digitalcity.apteki.R
import com.digitalcity.apteki.data.saveCompanyToken
import com.digitalcity.apteki.data.savePassword
import com.digitalcity.apteki.data.saveToken
import com.digitalcity.apteki.data.setLoggedIn
import com.digitalcity.apteki.databinding.FragmentLogInBinding
import com.digitalcity.apteki.network.Resource
import com.digitalcity.apteki.utils.animDown
import com.digitalcity.apteki.utils.animUp
import com.digitalcity.apteki.utils.optionDone
import org.koin.androidx.viewmodel.ext.android.viewModel


class LogInFragment : Fragment() {


    private var _binding: FragmentLogInBinding? = null
    private lateinit var passwordEdit: AppCompatEditText
    private lateinit var passwordIcon: ImageButton
    private lateinit var loginEdit: AppCompatEditText
    private lateinit var navOptions: NavOptions
    private val viewModel: LoginViewModel by viewModel()

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
            .setPopUpTo(R.id.nav_logIn, true)
            .build()
        getDataFromLogin()
        return binding.root
    }

    private fun setUpViews() {

        passwordIcon = binding.passwordIcon
        loginEdit = binding.loginEditText
        passwordEdit = binding.passwordEditText
        passwordEdit.transformationMethod = null
        passwordEdit.transformationMethod = com.digitalcity.apteki.utils.PasswordTransformation()
        setUpEditText()
    }

    private fun setUpEditText() {
        optionDone(loginEdit)
        optionDone(passwordEdit)
    }

    private fun setUpClickListener() {
        var clicked = false
        passwordIcon.setOnClickListener {
            if (!clicked) {
                passwordIcon.setImageResource(R.drawable.ic_lock_open)
                passwordEdit.transformationMethod = null
                clicked = true
            } else {
                passwordIcon.setImageResource(R.drawable.ic_lock)
                passwordEdit.transformationMethod =
                    com.digitalcity.apteki.utils.PasswordTransformation()
                clicked = false
            }
        }
    }

    private fun goNext() {
        binding.logInBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_logIn_to_nav_branches,
                null, navOptions
            )
            /*if (loginEdit.text!!.isNotEmpty() && passwordEdit.text!!.isNotEmpty()) {
                viewModel.getLogIn(loginEdit.text.toString(), passwordEdit.text.toString())

            } else {
                if (loginEdit.text!!.isEmpty() && passwordEdit.text!!.isEmpty()) {
                    binding.toastText.text =
                        resources.getString(R.string.both_empty)
                } else if (loginEdit.text!!.isEmpty()) {
                    binding.toastText.text =
                        resources.getString(R.string.login_empty)
                } else if (passwordEdit.text!!.isEmpty()) {
                    binding.toastText.text =
                        resources.getString(R.string.password_empty)
                }
                animUp(binding.toastText)
                Handler(Looper.getMainLooper()).postDelayed({
                    animDown(binding.toastText)
                }, 1000)
            }*/
        }
    }

    private fun getDataFromLogin() {
        viewModel.resourceLogin.observe(viewLifecycleOwner, Observer { it ->
            it.getContentIfNotHandled().let { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.logInBtn.isClickable = false
                    }
                    is Resource.Success -> {
                        binding.logInBtn.isClickable = true
                        if (resource.data.success) {
                            requireContext().saveToken(resource.data.data.token)
                            requireContext().saveCompanyToken(resource.data.data.user.company.token)
                            findNavController().navigate(
                                R.id.action_nav_logIn_to_nav_branches,
                                null, navOptions
                            )
                            requireContext().savePassword(passwordEdit.text.toString())
                            requireContext().setLoggedIn()
                        } else {
                            binding.toastText.text = resource.data.error
                            animUp(binding.toastText)
                            Handler(Looper.getMainLooper()).postDelayed({
                                animDown(binding.toastText)
                            }, 1000)
                        }
                    }
                    is Resource.GenericError -> {
                        binding.logInBtn.isClickable = true
                        Log.d(
                            "here",
                            "here2 ${resource.errorResponse.message}"
                        )
                    }
                    is Resource.Error -> {
                        binding.logInBtn.isClickable = true
                        Log.d("here", "here3  ${resource.exception.message}")
                    }
                }
            }
        })
    }
}