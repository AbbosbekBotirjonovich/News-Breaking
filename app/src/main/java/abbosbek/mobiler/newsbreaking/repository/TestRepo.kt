package abbosbek.mobiler.newsbreaking.repository

import abbosbek.mobiler.newsbreaking.api.NewsService
import javax.inject.Inject

class TestRepo
@Inject constructor(private val newsService: NewsService){

    suspend fun getAll() = newsService.getHeadLines()

}