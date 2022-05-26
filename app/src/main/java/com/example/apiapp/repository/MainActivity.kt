package com.example.apiapp.repository

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apiapp.adapter.CatAdapter
import com.example.apiapp.databinding.ActivityMainBinding
import com.example.apiapp.model.NetCat

class MainActivity : AppCompatActivity() {
    private val  myAdapter by lazy { CatAdapter() }
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy {ViewModelProviders.of(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerview.adapter = myAdapter

        // observing the stuff we are interested about.
        // any change observed will run the corresponding method
        viewModel.bunchOfCats.observe(this) { displayCats(it) }
        viewModel.errorMessage.observe(this) { onError(it) }

        // click listener so you can perform the API call manually
        viewModel.getCats()
    }

    /**
     * Method triggered when we observe a change in MainViewModel.bunchOfCats MutableLiveData
     * @param bunchOfCats An updated list of cats we got from the API
     */
//    private fun onResult(bunchOfCats: List<NetCat>) {
//
//        // Not doing anything yet with this list except a toast
//        Toast.makeText(this@MainActivity, "Got ${bunchOfCats.size} cats", Toast.LENGTH_SHORT).show()
//    }

    private fun displayCats(list: List<NetCat>) {
        myAdapter.submitList(list)
        Log.d("Cats", list.toString())
    }


    /**
     * Method triggered when we observe a change in MainViewModel.errorMessage MutableLiveData
     * @param error Error message describing what went wrong
     */
    private fun onError(error: String) {
        // a simple toast in case things went wrong
        error.let {
            if (it.isNotBlank()) {
                Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
            }
            Log.d("Cats", error)


        }
    }

}
