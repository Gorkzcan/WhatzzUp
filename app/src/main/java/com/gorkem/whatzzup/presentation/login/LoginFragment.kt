package com.gorkem.whatzzup.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gorkem.whatzzup.R
import com.gorkem.whatzzup.databinding.FragmentLoginBinding
import com.gorkem.whatzzup.databinding.FragmentSplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import com.gorkem.whatzzup.util.Result
import com.gorkem.whatzzup.util.snackbar

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!
    private val viewModel by viewModels<LoginVM>()
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        setupActionbar()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupActionbar(){
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.elevation = 0F
    }


    private fun setupListeners(){
        binding.btnCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.btnLogin.setOnClickListener {
            getFieldData()
            login()
        }
    }


    private fun login() {
        viewModel.loginUser(email, password).observe(viewLifecycleOwner) { result ->
            handleLoginResult(result)
            viewModel.resetResult()
        }
    }

    private fun handleLoginResult(result: Result) {
        when (result) {
            is Result.Success -> {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
            is Result.Error -> {
                snackbar(result.message)
            }
            else -> {}
        }
    }

    private fun getFieldData() {
        email = binding.actEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()
    }

}