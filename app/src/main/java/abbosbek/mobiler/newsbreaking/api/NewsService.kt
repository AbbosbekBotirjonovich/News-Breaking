package abbosbek.mobiler.newsbreaking.api

import abbosbek.mobiler.newsbreaking.models.NewsResponse
import abbosbek.mobiler.newsbreaking.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("/v2/everything")
    suspend fun getEveryThing(
        @Query("q")query : String,
        @Query("page") page : Int = 1,
        @Query("apiKey") apiKey : String = Constants.API_KEY
    ) : Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getHeadLines(
        @Query("country") countryCode : String = "ru",
        @Query("page") page : Int = 1,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ) : Response<NewsResponse>

}