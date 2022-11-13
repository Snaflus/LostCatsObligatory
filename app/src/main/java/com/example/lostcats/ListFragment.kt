package com.example.lostcats

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lostcats.databinding.FragmentListBinding
import com.example.lostcats.models.CatsAdapter
import com.example.lostcats.models.CatsViewModel
import com.example.lostcats.models.UsersViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val catsViewModel: CatsViewModel by activityViewModels()
    private val usersViewModel: UsersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                catsViewModel.getFilter("$query")
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                catsViewModel.getFilter("$query")
                return false
            }
        })

        binding.chipGroupFilter.setOnCheckedStateChangeListener { group, checkedIds ->
            // https://howtodoandroid.com/android-chips-material-component/
            val chip: Chip? = group.findViewById(group.checkedChipId)

            val sorting = with(chip.toString()) {
                when {
                    contains("date") -> "date"
                    contains("name") -> "name"
                    contains("place") -> "place"
                    contains("reward") -> "reward"
                    else -> ""
                }
            }
            catsViewModel.getSort(sorting)
        }

        catsViewModel.catsLiveData.observe(viewLifecycleOwner) { cats ->
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

        catsViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotBlank()) {
                Snackbar.make(
                    binding.root,
                    errorMessage,
                    Snackbar.LENGTH_LONG
                ).show()
                Log.d("KIWI", errorMessage)
            }
        }

        binding.swiperefresh.setOnRefreshListener {
            catsViewModel.reload()
            binding.swiperefresh.isRefreshing = false
        }

        binding.floatingPlusButton.setOnClickListener {
            if (usersViewModel.userLiveData.value != null) {
                findNavController().navigate(R.id.action_ListFragment_to_AddCatFragment)
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.not_logged_in_error),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        catsViewModel.reload()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}