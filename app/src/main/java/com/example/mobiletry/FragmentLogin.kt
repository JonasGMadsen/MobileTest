package com.example.mobiletry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobiletry.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            // Knap til at logge ind
        }

        binding.loginButton.setOnClickListener {
            val email = binding.edittextEmail.text.trim().toString()
            if (email.isEmpty()) {
                binding.edittextEmail.error = "No email"
                return@setOnClickListener
            }
            val password = binding.passwordEditText.text.trim().toString()
            if (password.isEmpty()) {
                binding.passwordEditText.error = "No password"
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("APPLE", "createUserWithEmail:success")
                        auth.currentUser
                        //updateUI(user)
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("APPLE", "createUserWithEmail:failure", task.exception)
                        binding.createAccountText.text =
                            "Authentication failed: " + task.exception?.message
                        //updateUI(null)
                    }
                }
        }

        binding.createAccountButton.setOnClickListener {
            val email = binding.edittextEmail.text.trim().toString()
            if (email.isEmpty()) {
                binding.edittextEmail.error = "No email"
                return@setOnClickListener
            }
            val password = binding.passwordEditText.text.trim().toString()
            if (password.isEmpty()) {
                binding.passwordEditText.error = "No password"
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("APPLE", "createUserWithEmail:success")
                        val user = auth.currentUser
                        //updateUI(user)
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("APPLE", "createUserWithEmail:failure", task.exception)
                        binding.createAccountText.text =
                            "Registration failed: " + task.exception?.message
                    }
        }
    }
}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}