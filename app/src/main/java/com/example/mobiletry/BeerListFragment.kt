package com.example.mobiletry

import com.example.mobiletry.models.BeerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
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
        setHasOptionsMenu(true)

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

        view.findViewById<Button>(R.id.addBeerButton).setOnClickListener {
            findNavController().navigate(R.id.action_beerListFragment_to_create_and_update_beer_fragment)
        }

        val searchView = view.findViewById<SearchView>(R.id.searchViewFilterName)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.filterByName(query.orEmpty())
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterByName(newText.orEmpty())
                return true
            }


        })





    }













    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // Clearer gamle items (Tjek om skal slettes)
        menu.clear()
        // Inflate menuen
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



    //Henter hele listen igen når jeg går tilbage til fragmentet. Ville være mega dårligt at bruge hvis man fx. Listen har meget data
    override fun onResume() {
        super.onResume()
        viewModel.reload() // Assuming 'reload' fetches the latest data
    }


}