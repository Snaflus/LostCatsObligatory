package com.example.lostcats

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lostcats.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //binding.emailInputField.setText(currentUser.email) // half automatic login
            // current user exists: No need to login again
            findNavController().navigate(R.id.action_LoginFragment_to_ListFragment)
        }
        binding.buttonLogin.setOnClickListener {
            val email = binding.edittextEmail.text.toString().trim()
            val password = binding.edittextPassword.text.toString().trim()
            if (email.isEmpty()) {
                binding.edittextEmail.error = "No email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.edittextPassword.error = "No password"
                return@setOnClickListener
            }
            // https://firebase.google.com/docs/auth/android/password-auth
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_LoginFragment_to_ListFragment)
                } else {
                    Snackbar.make(
                        binding.root,
                        task.exception?.message.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                    Log.d("KIWI", task.exception?.message.toString())
                }
            }
        }

        binding.buttonRegister.setOnClickListener {
            val email = binding.edittextEmail.text.toString().trim()
            val password = binding.edittextPassword.text.toString().trim()
            if (email.isEmpty()) {
                binding.edittextEmail.error = "No email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.edittextPassword.error = "No password"
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Snackbar.make(
                        binding.root,
                        "User created. Now please login",
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    Snackbar.make(
                        binding.root,
                        task.exception?.message.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                    Log.d("KIWI", task.exception?.message.toString())
                }
            }
        }

        binding.buttonContinue.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}