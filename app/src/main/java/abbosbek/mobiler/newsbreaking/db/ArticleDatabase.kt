package abbosbek.mobiler.newsbreaking.db

import abbosbek.mobiler.newsbreaking.models.Article
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Article::class], version = 1, exportSchema = true)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao() : ArticleDao

//    companion object{
//        @Volatile
//        private var instance : ArticleDatabase ?= null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
//            instance ?: createDatabase(context).also { instance = it }
//        }
//
//        private fun createDatabase(context: Context): ArticleDatabase {
//            return Room.databaseBuilder(
//                context.applicationContext,
//                ArticleDatabase::class.java,
//                "article_db.db"
//            )
//                .build()
//        }
//
//    }

}