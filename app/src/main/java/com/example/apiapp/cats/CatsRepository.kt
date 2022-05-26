package com.example.apiapp.cats

import com.example.apiapp.model.NetCat
import com.example.apiapp.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * This guy extends Repository class so the retrofit variable will be available to use as it's instantiated in the init!
 */
class CatsRepository(
    isDebugEnabled: Boolean,
) : Repository(isDebugEnabled) {

    private val catsDataSource: CatsDataSource = CatsDataSource(retrofit)

    // a class to wrap around the response to make things easier later
    inner class Result(val netCats: List<NetCat>? = null, val errorMessage: String? = null) {

        fun hasCats(): Boolean {
            return netCats != null && netCats.isNotEmpty()
        }

        fun hasError(): Boolean {
            return errorMessage != null
        }
    }

    // the method that's gonna be called by our activity

    fun getNumber(catCallBack: CatCallBack){

        catsDataSource.getNumberOfRandomCats(10, null).enqueue(object: Callback<List<NetCat>> {
            override fun onResponse(
                call: Call<List<NetCat>>,
                response: Response<List<NetCat>>
            ) {
                if(response.isSuccessful){
                    catCallBack.onResult(Result(netCats = response.body()!!))
                }
            }
            override fun onFailure(call: Call<List<NetCat>>, t: Throwable) {
                t.printStackTrace()
                catCallBack.onResult(Result(errorMessage = "Error"))
            }
        })
    }
}

interface CatCallBack {
    fun onResult(result: CatsRepository.Result)
}