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
import com.google.firebase.auth.FirebaseAuth


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

        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""


        viewModel.beersLiveData.observe(viewLifecycleOwner) { beers ->
            val userBeers = beers.filter { it.user == currentUserEmail }
            beersAdapter.setBeers(userBeers)
        }

        view.findViewById<Button>(R.id.addBeerButton).setOnClickListener {
            findNavController().navigate(R.id.action_beerListFragment_to_create_and_update_beer_fragment)
        }







    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // Clear the existing menu items
        menu.clear()
        // Inflate the new menu
        inflater.inflate(R.menu.menu_main, menu)


    }

    private fun setupSearchView(searchView: SearchView?) {
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterByName(newText ?: "")
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // Optionally hide the keyboard or handle query submission
                return true
            }
        })
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