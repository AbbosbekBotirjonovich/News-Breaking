package abbosbek.mobiler.newsbreaking.ui.detail

import abbosbek.mobiler.newsbreaking.models.Article
import abbosbek.mobiler.newsbreaking.repository.NewsRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject constructor(private val repository: NewsRepository) : ViewModel(){

    fun getSavedArticles() = repository.getFavoriteArticles()

    fun saveFavoriteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.addToFavorite(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteToFavorite(article)
    }

}