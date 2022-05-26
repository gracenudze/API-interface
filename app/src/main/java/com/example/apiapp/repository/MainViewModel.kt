package com.example.apiapp.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apiapp.cats.CatCallBack
import com.example.apiapp.cats.CatsRepository
import com.example.apiapp.model.NetCat


class MainViewModel : ViewModel() {
    // the list the will be observed by the activity
    val bunchOfCats = MutableLiveData<List<NetCat>>()
    // the error message observed
    val errorMessage = MutableLiveData<String>()
    private val catsRepository = CatsRepository(isDebugEnabled = true)

    private val catCallBack = object:CatCallBack{
        override fun onResult(result: CatsRepository.Result) {
            when {
                result.hasError() -> result.errorMessage?.let {
                    // anyone who observes this will be notified of the change automatically
                    errorMessage.postValue("Error getting cats $it")
                }
                    ?: run {
                        // anyone who observes this will be notified of the change automatically
                        errorMessage.postValue("Null error :(")
                    }
                result.hasCats() -> result.netCats?.let {
                    // anyone who observes this will be notified of the change automatically
                    bunchOfCats.postValue(it)
                    // clearing the error if it existed (hacky and optional)
                    errorMessage.postValue("")
                }
                    ?: run {
                        // anyone who observes this will be notified of the change automatically
                        errorMessage.postValue("Null list of cats :(")
                    }
                else -> {
                    // anyone who observes this will be notified of the change automatically
                    errorMessage.postValue("No cats available :(")
                }
            }
        }

    }

    fun getCats() {
        catsRepository.getNumber(catCallBack)
    }
}