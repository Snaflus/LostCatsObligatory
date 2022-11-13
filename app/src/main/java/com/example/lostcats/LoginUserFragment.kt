package com.example.lostcats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lostcats.databinding.FragmentLoginUserBinding
import com.example.lostcats.models.UsersViewModel
import com.google.android.material.snackbar.Snackbar

class LoginUserFragment : Fragment() {

    private var _binding: FragmentLoginUserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val usersViewModel: UsersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginUserBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (usersViewModel.userLiveData.value != null) {
            // current user exists: No need to login again
            findNavController().navigate(R.id.action_LoginUserFragment_to_ListFragment)
        }
        binding.buttonLogin.setOnClickListener {
            val email = binding.edittextEmail.text.toString().trim()
            val password = binding.edittextPassword.text.toString().trim()
            if (email.isEmpty()) {
                binding.edittextEmail.error = getString(R.string.username_error)
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.edittextPassword.error = getString(R.string.password_error)
                return@setOnClickListener
            }
            // https://firebase.google.com/docs/auth/android/password-auth

            usersViewModel.signInWithEmailAndPassword(email, password)
        }

        binding.buttonRegister.setOnClickListener {
            val action = LoginUserFragmentDirections.actionLoginUserFragmentToRegisterUserFragment()
            findNavController().navigate(action)
        }

        usersViewModel.userLiveData.observe(viewLifecycleOwner) {
            if (usersViewModel.userLiveData.value != null) {
                val action = LoginUserFragmentDirections.actionLoginUserFragmentToListFragment()
                findNavController().navigate(action)
                Snackbar.make(
                    binding.root,
                    getString(R.string.welcome_snackbar, (usersViewModel.userLiveData.value?.email ?: String)),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        usersViewModel.errorLiveData.observe(viewLifecycleOwner) {
            if (usersViewModel.errorLiveData.value != null) {
                Snackbar.make(
                    binding.root,
                    usersViewModel.errorLiveData.value.toString(),
                    Snackbar.LENGTH_LONG
                ).show()
                Log.d("KIWI", usersViewModel.errorLiveData.value.toString())
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