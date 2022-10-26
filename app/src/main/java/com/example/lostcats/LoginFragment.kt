package com.example.lostcats

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.lostcats.databinding.FragmentLoginBinding
import com.example.lostcats.models.UsersViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val usersViewModel: UsersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = usersViewModel.currentUser()
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

            var exceptionString: CharSequence = ""
            var isSuccessful: Boolean = lifecycleScope.launch(Dispatchers.IO) {
                exceptionString = usersViewModel.signInWithEmailAndPassword(email, password, findNavController())
            }.isCompleted
            if (!isSuccessful) {
                Snackbar.make(
                    binding.root,
                    exceptionString,
                    Snackbar.LENGTH_LONG
                ).show()
                Log.d("KIWI", "Login error")
            }

//            isSuccessful = usersViewModel.signInWithEmailAndPassword(email, password, findNavController())
//            if (!isSuccessful) {
//                Snackbar.make(
//                    binding.root,
//                    "Login error",
//                    Snackbar.LENGTH_LONG
//                ).show()
//                Log.d("KIWI", "Login error")
//            }
        }


        binding.buttonRegister.setOnClickListener {
            var isSuccessful = true
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

            usersViewModel.createUserWithEmailAndPassword(email, password, findNavController())
            if (!isSuccessful) {
                Snackbar.make(
                    binding.root,
                    "User creation error",
                    Snackbar.LENGTH_LONG
                ).show()
                Log.d("KIWI", "User creation error")
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