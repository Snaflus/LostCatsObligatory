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
import com.example.lostcats.models.Cat
import com.example.lostcats.models.CatsAdapter
import com.example.lostcats.models.CatsViewModel
import com.example.lostcats.models.UsersViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AddCatFragment : Fragment() {

    private var _binding: FragmentAddCatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val catsViewModel: CatsViewModel by activityViewModels()
    private val usersViewModel: UsersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddCatBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAddCat.setOnClickListener {
            val name = binding.edittextName.text.toString().trim()
            val description = binding.edittextDescription.text.toString().trim()
            val place = binding.edittextPlace.text.toString().trim()
            val reward = binding.edittextReward.text
            val userId: String = usersViewModel.userLiveData.value?.email!!
//            val tempUserId: String? = usersViewModel.userLiveData.value?.email
//            var userId: String = ""
//            if (tempUserId != null) {
//                userId = tempUserId
//            }



            if (name.isEmpty()) {
                binding.edittextName.error = getString(R.string.field_empty)
                return@setOnClickListener
            }
            if (description.isEmpty()) {
                binding.edittextDescription.error = getString(R.string.field_empty)
                return@setOnClickListener
            }
            if (place.isEmpty()) {
                binding.edittextPlace.error = getString(R.string.field_empty)
                return@setOnClickListener
            }
            if (reward.isNullOrEmpty()) {
                binding.edittextReward.error = getString(R.string.field_empty)
                return@setOnClickListener
            }
            val newCat = Cat(name, description, place, reward.toString().toInt(), userId, "")
            catsViewModel.add(newCat)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}