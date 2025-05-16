package com.example.newsapplication.viewmodel



import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.api.RetrofitInstance
import com.example.newsapplication.model.Article
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchTopHeadlines()
    }

    fun fetchTopHeadlines(countryCode: String = "us") {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null // Önceki hatayı temizle
            try {
                val response = RetrofitInstance.api.getTopHeadlines(countryCode, RetrofitInstance.API_KEY)
                if (response.isSuccessful) {
                    response.body()?.let { newsResponse ->
                        _articles.value = newsResponse.articles
                    } ?: run {
                        _errorMessage.value = "Boş yanıt geldi."
                        Log.e("NewsViewModel", "Response body is null")
                    }
                } else {
                    _errorMessage.value = "Hata: ${response.code()} - ${response.message()}"
                    Log.e("NewsViewModel", "Error: ${response.code()} - ${response.message()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Veri çekilemedi: ${e.localizedMessage}"
                Log.e("NewsViewModel", "Exception: ${e.localizedMessage}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Hata mesajı gösterildikten sonra temizlemek için
    fun onErrorMessageShown() {
        _errorMessage.value = null
    }
}