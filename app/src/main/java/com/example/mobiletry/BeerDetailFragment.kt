package com.example.mobiletry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    // Safe args henter argumentet
    private val args: BeerDetailFragmentArgs by navArgs()
    private lateinit var beer: Beer

    private val beersViewModel: BeerViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Modtager beer objectet. Type safe.
        beer = args.beer



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflater layout
        _binding = FragmentBeerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Updatere UI med Beer details
        binding.beerUserDetail.text = beer.user
        binding.beerNameDetail.text = beer.name
        binding.beerBreweryDetail.text = beer.brewery
        binding.beerStyleDetail.text = beer.style
        binding.beerAbvDetail.text = beer.abv.toString()
        binding.beerVolumeDetail.text = beer.volume.toString()
        binding.beerHowManyDetail.text = beer.howMany.toString()

        binding.buttonDelete.setOnClickListener {
            beersViewModel.delete(beer.id) //Yeet!
            findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
