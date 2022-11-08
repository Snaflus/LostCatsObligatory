package com.example.lostcats

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lostcats.databinding.FragmentAddCatBinding
import com.example.lostcats.databinding.FragmentRegisterUserBinding
import com.example.lostcats.databinding.FragmentSettingsBinding
import com.example.lostcats.models.Cat
import com.example.lostcats.models.CatsAdapter
import com.example.lostcats.models.CatsViewModel
import com.example.lostcats.models.UsersViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterUserFragment : Fragment() {

    private var _binding: FragmentRegisterUserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val usersViewModel: UsersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterUserBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegister.setOnClickListener {
            val email = binding.edittextEmail.text.toString().trim()
            val password = binding.edittextPassword.text.toString().trim()
            val passwordConfirm = binding.edittextPasswordConfirm.text.toString().trim()
            if (email.isEmpty()) {
                binding.edittextEmail.error = getString(R.string.username_error)
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.edittextPassword.error = getString(R.string.password_error)
                return@setOnClickListener
            }
            if (passwordConfirm == password) {
                binding.edittextPasswordConfirm.error = getString(R.string.password_match_error)
                return@setOnClickListener
            }
            usersViewModel.createUserWithEmailAndPassword(email, password)
        }

        usersViewModel.userLiveData.observe(viewLifecycleOwner) {
            if (usersViewModel.userLiveData.value != null) {
                val action = RegisterUserFragmentDirections.actionRegisterUserFragmentToListFragment()
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}