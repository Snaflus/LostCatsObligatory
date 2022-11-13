package com.example.lostcats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lostcats.databinding.FragmentAddCatBinding
import com.example.lostcats.models.Cat
import com.example.lostcats.models.CatsViewModel
import com.example.lostcats.models.UsersViewModel

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
    ): View {

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
            catsViewModel.reload()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}