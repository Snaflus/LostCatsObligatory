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
                val adapter = CatsAdapter(cats) { position ->  }
                

                binding.recyclerView.adapter = adapter
            }
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Log.d("KIWI",errorMessage)
        }

        viewModel.reload()

        binding.floatingPlusButton.setOnClickListener {
            Snackbar.make(view, "Redirect to add a cat fragment", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_ListFragment_to_LoginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}