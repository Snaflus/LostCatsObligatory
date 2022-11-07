package com.example.lostcats

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lostcats.databinding.FragmentAddCatBinding
import com.example.lostcats.databinding.FragmentDetailedCatBinding
import com.example.lostcats.models.Cat
import com.example.lostcats.models.CatsAdapter
import com.example.lostcats.models.CatsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DetailedCatFragment : Fragment() {

    private var _binding: FragmentDetailedCatBinding? = null

    private val args: DetailedCatFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: CatsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailedCatBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cat = viewModel[args.id]
        if (cat == null) {
            binding.errorTextview.text = getString(R.string.no_cat_found)
            binding.catPicture.isVisible = false
            return
        }

        binding.catId.text = getString(R.string.id_with_colon, cat.id.toString())
        binding.catName.text = getString(R.string.name_with_colon, cat.name)
        binding.catDesc.text = getString(R.string.desc_with_colon, cat.description)
        binding.catPlace.text = getString(R.string.place_with_colon, cat.place)
        binding.catReward.text = getString(R.string.reward_with_colon, cat.reward.toString())
        binding.catUserid.text = getString(R.string.userID_with_colon, cat.userId)
        binding.catDate.text = getString(R.string.date_with_colon, viewModel.humanDate(cat.date))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}