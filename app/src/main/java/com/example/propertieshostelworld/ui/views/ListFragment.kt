package com.example.propertieshostelworld.ui.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.propertieshostelworld.R
import com.example.propertieshostelworld.databinding.FragmentListBinding
import com.example.propertieshostelworld.model.Location
import com.example.propertieshostelworld.model.Property
import com.example.propertieshostelworld.ui.adapters.ItemsAdapter
import com.example.propertieshostelworld.ui.viewmodels.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), ItemsAdapter.ItemClickListener<Property, Location> {
    lateinit var viewModel: ListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentListBinding.inflate(inflater, container, false)

        val itemsAdapter = ItemsAdapter().also {
            with(binding.propertiesRecyclerView) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = it
            }
            it.setClickListener(this)
        }

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]

        with(viewModel) {
            properties.observe(requireActivity(), Observer {
                itemsAdapter.updateItemsList(it)
            })
            location.observe(requireActivity(), Observer {
                itemsAdapter.updateLocation(it)
            })

            callApi()
        }

        return binding.root
    }

    override fun onItemClick(item: Property, location: Location) {
        view?.findNavController()?.navigate(R.id.detailsFragment, DetailsFragmentArgs(item, location).toBundle())
    }

    override fun onDestroy() {
        //stop sending events once the fragment is destroyed
        viewModel.disposable.dispose()
        super.onDestroy()
    }
}
