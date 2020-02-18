package com.dhimandasgupta.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.dhimandasgupta.news.data.api.NewsApi
import com.dhimandasgupta.news.data.api.RetrofitGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO) {
                val retrofit = RetrofitGenerator.getRetrofit()
                val newsApi = retrofit.create(NewsApi::class.java)

                kotlin.runCatching {
                    return@runCatching newsApi.getEverythingByQuery(query = "google")
                }.onSuccess { networkResponse->
                    withContext(Dispatchers.Main) {
                        Toast.makeText(baseContext, "API: $networkResponse", Toast.LENGTH_LONG).show()
                    }
                }.onFailure { throwable ->
                    withContext(Dispatchers.Main) {
                        Toast.makeText(baseContext, "${throwable.localizedMessage}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
