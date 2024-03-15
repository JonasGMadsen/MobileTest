package com.example.mobiletry

import com.example.mobiletry.models.BeerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletry.databinding.FragmentBeerDetailBinding
import com.example.mobiletry.models.Beer
import com.example.mobiletry.models.BeerViewModel

class BeerDetailFragment : Fragment() {

    private lateinit var beer: Beer
    private var _binding: FragmentBeerDetailBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // Make sure to cast the serializable to Beer
            beer = it.getSerializable("beer") as Beer
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using View Binding
        _binding = FragmentBeerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Husk at update dine bindings brudda
        binding.beerNameDetail.text = beer.name
        binding.beerBreweryDetail.text = beer.brewery
        binding.beerStyleDetail.text = beer.style
        binding.beerAbvDetail.text = beer.abv.toString()
        binding.beerVolumeDetail.text = beer.volume.toString()
        binding.beerHowManyDetail.text = beer.howMany.toString()
        binding.beerUserDetail.text = beer.user


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clean up view binding
    }
}