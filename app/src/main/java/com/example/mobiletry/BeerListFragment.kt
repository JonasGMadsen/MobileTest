package com.example.mobiletry

import com.example.mobiletry.models.BeerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletry.models.BeerViewModel


class BeerListFragment : Fragment() {

    private lateinit var viewModel: BeerViewModel
    private lateinit var beersAdapter: BeerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_beer_list, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(BeerViewModel::class.java)

        beersAdapter = BeerAdapter { beer ->
            val bundle = Bundle().apply {
                putSerializable("beer", beer)
            }
            findNavController().navigate(R.id.action_beerListFragment_to_beerDetailFragment, bundle)
        }

        view.findViewById<RecyclerView>(R.id.beersRecyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = beersAdapter
        }

        viewModel.beersLiveData.observe(viewLifecycleOwner) { beers ->
            beersAdapter.setBeers(beers)
        }
    }


}