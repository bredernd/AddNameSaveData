package com.example.addnamesavedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.addnamesavedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Declare variables
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private val ENTERED_NAMES_KEY = "enteredNamesKey"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        if(mainViewModel.enteredNames.isEmpty()) {
            binding.displayNames.text = "No names to display"
        } else {
            binding.displayNames.text = mainViewModel.enteredNames.joinToString { ("\n") }
        }

        binding.addName.setOnClickListener {
            addName()
            }
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(ENTERED_NAMES_KEY, ArrayList(mainViewModel.enteredNames))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val enteredNames = savedInstanceState.getStringArrayList(ENTERED_NAMES_KEY)
        if (!enteredNames.isNullOrEmpty()) {
            binding.displayNames.text = enteredNames.joinToString("\n")
            mainViewModel.enteredNames.addAll(enteredNames)
        }
    }

    private fun addName() {
        val enteredName = binding.editNameText.text.toString().trim()

        if (enteredName.isNotEmpty()) {
            if (binding.displayNames.text.toString().equals("No Names to Display", ignoreCase = true) ||
                binding.displayNames.text.toString().equals("No Name Entered", ignoreCase = true)
                ) {
                binding.displayNames.text = ""
            } else {
                binding.displayNames.append("\n")
            }
            binding.displayNames.append(enteredName)
            binding.editNameText.text.clear()
            mainViewModel.enteredNames.add(enteredName)
        } else {
           binding.displayNames.text = "No Name Entered"
        }
    }
}