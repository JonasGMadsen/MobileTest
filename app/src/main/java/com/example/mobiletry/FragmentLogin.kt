package com.example.mobiletry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobiletry.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up click listeners for login and create account buttons
        binding.loginButton.setOnClickListener {
            // Handle login button click
            // You can implement login logic here
        }

        binding.createAccountButton.setOnClickListener {
            // Handle create account button click
            // You can implement navigation to create account page here
            // For example:
            // val action = LoginFragmentDirections.actionLoginFragmentToCreateAccountFragment()
            // findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}