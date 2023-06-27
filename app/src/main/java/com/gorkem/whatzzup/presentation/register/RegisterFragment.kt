package com.gorkem.whatzzup.presentation.register

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
import com.gorkem.whatzzup.databinding.FragmentRegisterBinding
import com.gorkem.whatzzup.presentation.login.LoginVM
import com.gorkem.whatzzup.util.Result
import com.gorkem.whatzzup.util.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding get() = _binding!!
    private val viewModel by viewModels<RegisterVM>()
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var username:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
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
            getFieldData()
            register()
        }
    }


    private fun register(){
        viewModel.registerUser(email, password, username).observe(viewLifecycleOwner){
            handleRegisterResponse(it)
            viewModel.resetResult()
        }
    }


    private fun handleRegisterResponse(result:Result){
        when(result){
            is Result.Success -> {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
            is Result.Error -> {
                snackbar(result.message)
            }
            else -> {}
        }
    }

    private fun getFieldData(){
        username = binding.etUsername.text.toString().trim()
        password = binding.etPassword.text.toString().trim()
        email = binding.actvEmail.text.toString().trim()
    }


}