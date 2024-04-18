package com.example.mobiletry

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobiletry.databinding.FragmentBeerDetailBinding
import com.example.mobiletry.models.Beer
import com.example.mobiletry.models.BeerViewModel

class BeerDetailFragment : Fragment() {

    private var _binding: FragmentBeerDetailBinding? = null
    private val binding get() = _binding!!

    private val args: BeerDetailFragmentArgs by navArgs()
    private lateinit var beer: Beer

    private val beersViewModel: BeerViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beer = args.beer
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBeerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Populate UI with Beer details
        updateUI(beer)

        binding.buttonDelete.setOnClickListener {
            beersViewModel.delete(beer.id)
            findNavController().popBackStack()
        }

        binding.buttonUpdate.setOnClickListener {
            updateBeerFromUI()
        }
    }

    private fun updateUI(beer: Beer) {
        binding.beerUserDetail.setText(beer.user)
        binding.beerNameDetail.setText(beer.name)
        binding.beerBreweryDetail.setText(beer.brewery)
        binding.beerStyleDetail.setText(beer.style)
        binding.beerAbvDetail.setText(beer.abv.toString())
        binding.beerVolumeDetail.setText(beer.volume.toString())
        binding.beerHowManyDetail.setText(beer.howMany.toString())
    }

    private fun updateBeerFromUI() {
        try {
            val updatedBeer = Beer(
                id = beer.id,
                user = binding.beerUserDetail.text.toString(),
                name = binding.beerNameDetail.text.toString(),
                brewery = binding.beerBreweryDetail.text.toString(),
                style = binding.beerStyleDetail.text.toString(),
                abv = binding.beerAbvDetail.text.toString().toDouble(),
                volume = binding.beerVolumeDetail.text.toString().toDouble(),
                howMany = binding.beerHowManyDetail.text.toString().toInt()
            )
            beersViewModel.update(updatedBeer)
        } catch (e: Exception) {
            Toast.makeText(context, "Please check your input values", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

