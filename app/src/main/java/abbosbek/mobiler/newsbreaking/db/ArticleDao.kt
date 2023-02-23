package abbosbek.mobiler.newsbreaking.db

import abbosbek.mobiler.newsbreaking.models.Article
import abbosbek.mobiler.newsbreaking.models.NewsResponse
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {

    @Query("select * from articles")
    fun getAllArticles() : LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Delete
    suspend fun delete(article: Article)

}