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
import com.example.lostcats.databinding.FragmentListBinding
import com.example.lostcats.models.Cat
import com.example.lostcats.models.CatsAdapter
import com.example.lostcats.models.CatsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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

        viewModel.catsLiveData.observe(viewLifecycleOwner) { cats ->
            //Log.d("APPLE", "observer $books")
            //binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (cats == null) View.GONE else View.VISIBLE
            if (cats != null) {
                val adapter = CatsAdapter(cats) { //position ->
                    //val action =
                    //ListFragmentDirections.actionListFragmentToLoginFragment(position)
                    //findNavController().navigate((action))
                }
                var columns = 2
                val currentOrientation = this.resources.configuration.orientation
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    columns = 4
                } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    columns = 2
                }
                binding.recyclerView.layoutManager = GridLayoutManager(this.context, columns)

                binding.recyclerView.adapter = adapter
            }
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Log.d("KIWI", errorMessage)
        }

        viewModel.reload()

//        binding.swiperefresh.setOnClickListener {
//            viewModel.reload()
//            binding.swiperefresh.IsRefreshing = false
//        }

        binding.floatingPlusButton.setOnClickListener {
            findNavController().navigate(R.id.action_ListFragment_to_AddCatFragment)
            //Snackbar.make(view, "Redirect to add a cat fragment", Snackbar.LENGTH_LONG)
            //    .setAction("Action", null).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}