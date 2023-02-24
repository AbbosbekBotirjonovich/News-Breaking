package abbosbek.mobiler.newsbreaking.repository

import abbosbek.mobiler.newsbreaking.api.NewsService
import javax.inject.Inject

class NewsRepository
@Inject constructor(private val newsService: NewsService){

    suspend fun getNews(countryCode : String,pageNumber : Int) =
        newsService.getHeadLines(countryCode = countryCode, page = pageNumber)

}