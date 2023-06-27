package com.gorkem.whatzzup.presentation.splash


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.gorkem.whatzzup.R
import com.gorkem.whatzzup.databinding.FragmentSplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {
    private val delay = 3000L
    private var _binding: FragmentSplashScreenBinding? = null
    private val binding: FragmentSplashScreenBinding get() = _binding!!
    private val viewModel by viewModels<SplashVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(viewModel.currentUser != null){
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
            }, delay)
        }else{
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
            }, delay)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}