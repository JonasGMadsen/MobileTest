/* package com.example.mobiletry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobiletry.models.Beer

class CreateAndUpdateFragment : Fragment() {

    private var beer: Beer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            beer = it.getSerializable("beer") as? Beer
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_and_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beer?.let { populateFields(it) }

        view.findViewById<Button>(R.id.buttonSaveBeer).setOnClickListener {
            saveBeer()
        }
    }

    private fun populateFields(beer: Beer) {
        view?.findViewById<EditText>(R.id.editTextBeerName)?.setText(beer.name)
        // Populate other fields with beer details
    }

    private fun saveBeer() {
        val name = view?.findViewById<EditText>(R.id.editTextBeerName)?.text.toString()
        // Retrieve other field values

        val newOrUpdatedBeer = beer?.copy(
            name = name
            // Copy other properties from input fields
        ) ?: Beer(
            id = /* Generate ID for new beer or use a placeholder */,
            user = /* Get user input or use a placeholder */,
            brewery = /* Brewery input */,
            name = name,
            style = /* Style input */,
            abv = /* ABV input */,
            volume = /* Volume input */,
            pictureURL = /* Picture URL input, handle nullable */,
            howMany = /* howMany input */
        )

        // Depending on your setup, add newOrUpdatedBeer to your data store or update the existing one
        // This could involve a database operation or an API call

        // After saving, navigate back or update UI as necessary
        // For example, you could pop the fragment off the stack to return to the previous screen
        findNavController().popBackStack()
    }
} */