package abbosbek.mobiler.newsbreaking.viewModel

import abbosbek.mobiler.newsbreaking.models.NewsResponse
import abbosbek.mobiler.newsbreaking.repository.TestRepo
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(private val repo: TestRepo) : ViewModel(){

    init {
        getAll()
    }

    private val _all = MutableLiveData<NewsResponse>()

    val all : LiveData<NewsResponse> get() = _all

    fun getAll() = viewModelScope.launch {
        repo.getAll().let {
            if (it.isSuccessful){
                _all.postValue(it.body())
            }else{
                Log.d("checkData", "getAll: ${it.errorBody()}")
            }
        }
    }

}