package com.example.lostcats

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lostcats.databinding.FragmentDetailedCatBinding
import com.example.lostcats.models.CatsViewModel
import com.example.lostcats.models.UsersViewModel

class DetailedCatFragment : Fragment() {

    private var _binding: FragmentDetailedCatBinding? = null

    private val args: DetailedCatFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val catsViewModel: CatsViewModel by activityViewModels()
    private val usersViewModel: UsersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailedCatBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cat = catsViewModel[args.id]
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
        binding.catDate.text =
            getString(R.string.date_with_colon, catsViewModel.humanDate(cat.date))

        binding.buttonContact.setOnClickListener() {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(cat.userId))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, cat.name + " " + cat.id.toString())
            // TODO: try catch error handling
            startActivity(emailIntent)
        }

        if (cat.userId == usersViewModel.userLiveData.toString()) {
            binding.buttonDelete.visibility = View.VISIBLE
            binding.buttonDelete.setOnClickListener() {
                catsViewModel.delete(cat.id)
                findNavController().popBackStack()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}