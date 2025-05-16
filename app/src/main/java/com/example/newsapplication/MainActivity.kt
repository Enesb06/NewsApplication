package com.example.newsapplication



import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.adapter.NewsAdapter
import com.example.newsapplication.databinding.ActivityMainBinding
import com.example.newsapplication.model.Article
import com.example.newsapplication.viewmodel.NewsViewModel

class MainActivity : AppCompatActivity(), NewsAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        newsAdapter.setOnItemClickListener(this)
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeViewModel() {
        newsViewModel.articles.observe(this) { articles ->
            articles?.let {
                newsAdapter.differ.submitList(it)
            }
        }

        newsViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.rvNews.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        newsViewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                newsViewModel.onErrorMessageShown() // Mesaj gösterildikten sonra null yap
            }
        }
    }

    // NewsAdapter.OnItemClickListener implementasyonu
    override fun onItemClick(article: Article) {
        // (İsteğe bağlı Adım 7)
        article.url?.let { url ->
            if (url.startsWith("http://") || url.startsWith("https://")) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            } else {
                Toast.makeText(this, "Geçersiz URL", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(this, "Haber linki bulunamadı", Toast.LENGTH_SHORT).show()
        }
    }
}