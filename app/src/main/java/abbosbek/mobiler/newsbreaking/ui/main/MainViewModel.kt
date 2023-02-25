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

    var breakingNewsResponse : NewsResponse ?= null

    init {
        getNews("us")
    }

    fun getNews(countryCode : String) =
        viewModelScope.launch {
            newsLiveData.postValue(Resource.Loading())
            val response = repository.getSearchNews(query = countryCode, pageNumber = numberPage)
            if (response.isSuccessful){
                response.body().let { res->
                    numberPage++
                    if (breakingNewsResponse == null){
                        breakingNewsResponse = res
                    }else{
                        val oldArticles = breakingNewsResponse?.articles
                        val newArticles = res!!.articles
                        oldArticles?.addAll(newArticles)
                    }
                    newsLiveData.postValue(Resource.Success(breakingNewsResponse ?: res))
                }

            }else{
                newsLiveData.postValue(Resource.Error(message = response.message()))
            }
        }

}