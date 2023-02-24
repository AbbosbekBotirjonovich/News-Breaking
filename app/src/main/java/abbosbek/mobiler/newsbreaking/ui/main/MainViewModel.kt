package abbosbek.mobiler.newsbreaking.ui.main

import abbosbek.mobiler.newsbreaking.models.NewsResponse
import abbosbek.mobiler.newsbreaking.repository.NewsRepository
import abbosbek.mobiler.newsbreaking.utils.Resource
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(private val repository: NewsRepository) : ViewModel(){

    val newsLiveData : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    var numberPage = 1

    init {
        getNews("ru")
    }

    private fun getNews(countryCode : String) =
        viewModelScope.launch {
            newsLiveData.postValue(Resource.Loading())
            val response = repository.getNews(countryCode = countryCode, pageNumber = numberPage)
            if (response.isSuccessful){

                response.body().let { res->
                    newsLiveData.postValue(Resource.Success(res))
                }

            }else{
                newsLiveData.postValue(Resource.Error(message = response.message()))
            }
        }

}