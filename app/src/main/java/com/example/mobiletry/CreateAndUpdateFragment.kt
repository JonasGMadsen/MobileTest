package com.example.mobiletry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobiletry.models.Beer
import com.example.mobiletry.models.BeerViewModel
import com.google.firebase.auth.FirebaseAuth

class CreateAndUpdateFragment : Fragment() {

    private lateinit var viewModel: BeerViewModel
    private val firebaseUser = FirebaseAuth.getInstance().currentUser


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_create_and_update, container, false)

        val breweryInput = view.findViewById<EditText>(R.id.breweryInput)
        val nameInput = view.findViewById<EditText>(R.id.nameInput)
        val styleInput = view.findViewById<EditText>(R.id.styleInput)
        val abvInput = view.findViewById<EditText>(R.id.abvInput)
        val volumeInput = view.findViewById<EditText>(R.id.volumeInput)
        val howManyInput = view.findViewById<EditText>(R.id.howManyInput)

        val beer = arguments?.getSerializable("beer") as? Beer
        beer?.let {
            breweryInput.setText(it.brewery)
            nameInput.setText(it.name)
            styleInput.setText(it.style)
            abvInput.setText(it.abv.toString())
            volumeInput.setText(it.volume.toString())
            howManyInput.setText(it.howMany.toString())
        }

        view.findViewById<Button>(R.id.submitButton).setOnClickListener {
            val user = firebaseUser?.email ?: "Unknown"
            val brewery = breweryInput.text.toString()
            val name = nameInput.text.toString()
            val style = styleInput.text.toString()
            val abv = abvInput.text.toString().toDoubleOrNull() ?: 0.0
            val volume = volumeInput.text.toString().toDoubleOrNull() ?: 0.0
            val howMany = howManyInput.text.toString().toIntOrNull() ?: 0

            val newBeer = Beer(user = user, brewery = brewery, name = name, style = style, abv = abv, volume = volume, howMany = howMany)
            viewModel.add(newBeer)
            findNavController().popBackStack()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[BeerViewModel::class.java]
    }
}