package com.example.lostcats

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lostcats.databinding.FragmentListBinding
import com.example.lostcats.models.CatsAdapter
import com.example.lostcats.models.CatsViewModel
import com.google.android.material.chip.Chip

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: CatsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.filterChip.setOnClickListener {
//            binding.messageView.text = "Clicked"
//        }
//
//        binding.filterChip.setOnCheckedChangeListener { compoundButton, checked ->
//            binding.messageView.text = checked.toString()
//        }
//
//        binding.chipGroupFilter.setOnCheckedStateChangeListener { group, checkedIds ->
//            // https://howtodoandroid.com/android-chips-material-component/
//            val chip: Chip = group.findViewById(checkedIds[0])
//            binding.messageView.text = "You want " + chip.text
//        }

        viewModel.catsLiveData.observe(viewLifecycleOwner) { cats ->
            binding.progresscircle.visibility = View.GONE

            binding.recyclerView.visibility = if (cats == null) View.GONE else View.VISIBLE
            if (cats != null) {
                val adapter = CatsAdapter(cats) { id ->
                    val action =
                        ListFragmentDirections.actionListFragmentToDetailedCatFragment(id)
                    findNavController().navigate((action))
                }
                var columns = 1
                val currentOrientation = this.resources.configuration.orientation
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    columns = 2
                } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    columns = 1
                }
                binding.recyclerView.layoutManager = GridLayoutManager(this.context, columns)

                binding.recyclerView.adapter = adapter
            }
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Log.d("KIWI", errorMessage)
        }

        binding.swiperefresh.setOnRefreshListener {
            viewModel.reload()
            binding.swiperefresh.isRefreshing = false
        }

        binding.floatingPlusButton.setOnClickListener {
            findNavController().navigate(R.id.action_ListFragment_to_AddCatFragment)
        }

        viewModel.reload()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}