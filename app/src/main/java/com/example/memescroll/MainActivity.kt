package com.example.memescroll

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.AuthFailureError as AuthFailureError1


class MainActivity : AppCompatActivity(), MemeItemClicked {
    private lateinit var mAdapter : MemesListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.addItemDecoration( DividerItemDecoration(getActivity()))
        val mDividerItemDecoration = DividerItemDecoration(recyclerView.context,
            (recyclerView.layoutManager as LinearLayoutManager).getOrientation())
        recyclerView.addItemDecoration(mDividerItemDecoration)


        fetchData()
        mAdapter  = MemesListAdapter(this)
        recyclerView.adapter = mAdapter


    }

    private fun fetchData() {
//        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=da2bdefb5d3e471c829ecff882d99a21"
//        val queue = MySingleton.getInstance(this.applicationContext).requestQueue
        val url = "https://meme-api.herokuapp.com/gimme/50"



        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,

            Response.Listener { response ->

                val memesJsonArray = response.getJSONArray("memes")

                val memesArray = ArrayList<String>()

                for (i in 0 until memesJsonArray.length()) {
                    val memeJsonObject = memesJsonArray.getJSONObject(i)
                    val meme = memeJsonObject.getString("url")


                    memesArray.add(meme)


                }

                mAdapter.updateNews(memesArray)


            },
            Response.ErrorListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()


            })
//                    {
//                                    @Throws(AuthFailureError1::class)
//                        override fun getHeaders(): Map<String, String>? {
//                            val headers = HashMap<String, String>()
//                            //headers.put("Content-Type", "application/json");
//                            headers["key"] = "Value"
//                            return headers
//                        }
//                        override fun getHeaders(): MutableMap<String, String> {
//                            val headers = HashMap<String, String>()
//                            headers["User-Agent"] = "Mozilla/5.0"
//                            return headers
//                        }
//
//                    }


// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(JsonObjectRequest)


    }



    override fun onItemClicked(item: String) {
//        Toast.makeText(this,"You Clicked on $item.title",Toast.LENGTH_LONG).show()

        val  builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item));

    }
}