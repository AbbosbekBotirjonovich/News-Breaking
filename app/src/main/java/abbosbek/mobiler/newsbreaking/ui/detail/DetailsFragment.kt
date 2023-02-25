package abbosbek.mobiler.newsbreaking.ui.detail

import abbosbek.mobiler.newsbreaking.MainActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import abbosbek.mobiler.newsbreaking.R
import abbosbek.mobiler.newsbreaking.databinding.FragmentDetailsBinding
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide

class DetailsFragment : Fragment() {

    private var _binding : FragmentDetailsBinding ?= null
    private val binding get() = _binding!!

    private val bundleArgs : DetailsFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNav()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleArgs = bundleArgs.article

        articleArgs.let { article ->
            article.urlToImage.let {
                Glide.with(this).load(article.urlToImage).into(binding.headerImage)
            }
            binding.headerImage.clipToOutline = true
            binding.articleDetailsTitle.text = article.title
            binding.tvDescriptionTitle.text = article.description

            binding.detailsButton.setOnClickListener {
                try {
                    Intent()
                        .setAction(Intent.ACTION_VIEW)
                        .addCategory(Intent.CATEGORY_BROWSABLE)
                        .setData(Uri.parse(takeIf {URLUtil.isValidUrl(article.url)}
                            ?.let {
                                article.url
                            } ?: "https://google.com"
                        ))
                        .let {
                            ContextCompat.startActivity(requireContext(),it,null)
                        }
                }catch (e : Exception){
                    Toast.makeText(
                        requireActivity(),
                        "The device doesn't have any browser to view the document!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).showBottomNav()
        _binding == null
    }
}