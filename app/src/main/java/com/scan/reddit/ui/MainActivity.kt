package com.scan.reddit.ui

import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.scan.reddit.R
import com.scan.reddit.adapter.LikedListAdapter
import com.scan.reddit.adapter.RedditPostAdapter
import com.scan.reddit.databinding.ActivityMainBinding
import com.scan.reddit.db.PostEntity
import com.scan.reddit.ui.viewmodel.MainActivityViewModel
import com.scan.reddit.utils.OnPostLiked
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.observeOn


@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() , OnPostLiked{

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private var  isChecked = false
    private lateinit var recyclerAdapter: RedditPostAdapter
    private lateinit var likedAdapter: LikedListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initRecyclerView()
        getData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val  searchItem = menu?.findItem(R.id.search)
        val tv =  TypedValue()
        var actionBarHeight = 0
        if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
        }
        val searchView =  searchItem?.actionView as SearchView
        val params =  LinearLayout.LayoutParams(actionBarHeight*2/3, actionBarHeight *2/3)
        params.marginEnd = 20
        val button =  Button(this)
        button.setBackgroundResource(R.drawable.like)
        (searchView.getChildAt(0) as LinearLayout).addView(button,params)
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null)
                    viewModel.setFilter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.equals("")) {
                    viewModel.setFilter("")
                }
                return false
            }

        })
        button.setOnClickListener {
            if (isChecked) {
                button.setBackgroundResource(R.drawable.like)
                viewModel.setFilter("")
                isChecked = false
            } else {
                button.setBackgroundResource(R.drawable.liked)
                viewModel.getLikedArticles()
                isChecked = true
            }}

        return true
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = RedditPostAdapter()
        recyclerAdapter.setListener(this)
        binding.recyclerView.adapter = recyclerAdapter
    }

    private fun initLikedList() {
        likedAdapter.setListener(this)
        binding.recyclerView.adapter = likedAdapter
    }
    private fun getData() {
        lifecycleScope.launchWhenCreated {
            viewModel.listResult.collectLatest {
                recyclerAdapter.submitData(it)
            }
        }
        viewModel.likedList.observe(this,{
            likedAdapter = LikedListAdapter(it)
            initLikedList()
        })
    }

    override fun onPostLiked(post: PostEntity) {
        if (post.fav == 1) {
            viewModel.insert(post)
        } else {
            viewModel.remove(post)
        }
    }
}