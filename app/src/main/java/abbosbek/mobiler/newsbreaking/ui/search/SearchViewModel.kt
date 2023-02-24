package abbosbek.mobiler.newsbreaking.ui.search

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
class SearchViewModel
@Inject constructor(
    private val repository: NewsRepository
) : ViewModel(){

    val searchNewsLiveData : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    var searchPage = 1

    init {
        getSearchNews("")
    }

    fun getSearchNews(query : String) =
        viewModelScope.launch {
            searchNewsLiveData.postValue(Resource.Loading())
            val response = repository.getSearchNews(query = query, pageNumber = searchPage)

            if (response.isSuccessful){
                response.body().let {newsResponse ->
                    searchNewsLiveData.postValue(Resource.Success(newsResponse))
                }
            }else{
                searchNewsLiveData.postValue(Resource.Error(response.message()))
            }

        }

}