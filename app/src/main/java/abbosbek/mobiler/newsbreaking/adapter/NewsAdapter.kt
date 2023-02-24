package abbosbek.mobiler.newsbreaking.adapter

import abbosbek.mobiler.newsbreaking.databinding.NewsItemLayoutBinding
import abbosbek.mobiler.newsbreaking.models.Article
import abbosbek.mobiler.newsbreaking.models.NewsResponse
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ItemHolder>() {

    inner class ItemHolder(val binding : NewsItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil =object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            NewsItemLayoutBinding
                .inflate(LayoutInflater.from(
                    parent.context
                ),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val article = differ.currentList[position]

        holder.binding.apply {

            Glide.with(holder.itemView.context).load(article.urlToImage).into(articleImage)
            articleImage.clipToOutline = true

            articleTitle.text = article.title
            articleData.text = article.publishedAt
        }
    }

    private var onItemClickListener : ((Article) -> Unit) ?= null

    fun setOnItemClickListener(listener : (Article) -> Unit){
        onItemClickListener = listener
    }

}