package abbosbek.mobiler.newsbreaking.repository

import abbosbek.mobiler.newsbreaking.api.NewsService
import abbosbek.mobiler.newsbreaking.db.ArticleDao
import abbosbek.mobiler.newsbreaking.models.Article
import javax.inject.Inject

class NewsRepository
@Inject constructor(
    private val newsService: NewsService,
    private val articleDao: ArticleDao
){

    suspend fun getNews(countryCode : String,pageNumber : Int) =
        newsService.getHeadLines(countryCode = countryCode, page = pageNumber)

    suspend fun getSearchNews(
        query : String,
        pageNumber: Int
    ) = newsService.getEveryThing(
        query = query,
        page = pageNumber
    )

    fun getFavoriteArticles() = articleDao.getAllArticles()

    suspend fun addToFavorite(article : Article) = articleDao.insert(article)

    suspend fun deleteToFavorite(article: Article) = articleDao.delete(article)

}