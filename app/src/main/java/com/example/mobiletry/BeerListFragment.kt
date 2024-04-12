package com.example.mobiletry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletry.models.BeerAdapter
import com.example.mobiletry.models.BeerViewModel
import com.google.firebase.auth.FirebaseAuth


class BeerListFragment : Fragment() {

    private lateinit var viewModel: BeerViewModel
    private lateinit var beersAdapter: BeerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_beer_list, container, false)
        viewModel = ViewModelProvider(this).get(BeerViewModel::class.java)
        setHasOptionsMenu(true)

        // Her bliver beer passed med safeargs
        beersAdapter = BeerAdapter { beer ->
            val action = BeerListFragmentDirections.actionBeerListFragmentToBeerDetailFragment(beer)
            findNavController().navigate(action)
        }

        // RecyclerView
        view.findViewById<RecyclerView>(R.id.beersRecyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = beersAdapter
        }

        // SearchView for filtering beers
        val searchView = view.findViewById<SearchView>(R.id.searchViewFilterName)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.filterByQuery(query.orEmpty())
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterByQuery(newText.orEmpty())
                return true
            }
        })

        // Observeere  live data og updaterer UI'et
        viewModel.beersLiveData.observe(viewLifecycleOwner) { beers ->
            val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""
            val userBeers = beers.filter { it.user == currentUserEmail }
            beersAdapter.setBeers(userBeers)
        }

        // Knap
        view.findViewById<Button>(R.id.addBeerButton).setOnClickListener {
            findNavController().navigate(R.id.action_beerListFragment_to_createAndUpdateFragment)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_name -> {
                viewModel.sortByName()
                true
            }
            R.id.sort_by_name_desc -> {
                viewModel.sortByNameDescending()
                true
            }
            R.id.sort_by_brewery -> {
                viewModel.sortByBrewery()
                true
            }
            R.id.sort_by_brewery_desc -> {
                viewModel.sortByBreweryDescending()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.reload() // Jeg behøves nu ikke længere at gå spadongo med at reloade
    }
}
