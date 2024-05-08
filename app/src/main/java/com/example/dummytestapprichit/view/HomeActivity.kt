package com.example.dummytestapprichit.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dummytestapprichit.R
import com.example.dummytestapprichit.adapter.ItemsAdapter
import com.example.dummytestapprichit.beans.ArticleItem
import com.example.dummytestapprichit.beans.Item
import com.example.dummytestapprichit.beans.ItemNew
import com.example.dummytestapprichit.beans.UserItem
import com.example.dummytestapprichit.databinding.ActivityHomeBinding
import com.example.dummytestapprichit.utils.Utils
import com.example.dummytestapprichit.viewmodel.GetAllProductsViewModel
import com.example.dummytestapprichit.viewmodel.GetFilteredProductsViewModel
import com.example.dummytestapprichit.viewmodel.GetSortedProductsViewModel

class HomeActivity : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var getAllProductsViewModel: GetAllProductsViewModel
    private lateinit var getFilteredProductsViewModel: GetFilteredProductsViewModel
    private lateinit var getSortedProductsViewModel: GetSortedProductsViewModel
    val utils = Utils()
    lateinit var itemsAdapter: ItemsAdapter
    var productItems: MutableList<ItemNew> = mutableListOf()
    var usersItems: MutableList<UserItem> = mutableListOf()
    var articleItems: MutableList<ArticleItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvToolbarTitle.text = "Articles"
        getAllProductsViewModel = ViewModelProvider(this)[GetAllProductsViewModel::class.java]
        getFilteredProductsViewModel =
            ViewModelProvider(this)[GetFilteredProductsViewModel::class.java]
        getSortedProductsViewModel = ViewModelProvider(this)[GetSortedProductsViewModel::class.java]

        binding.amvMenu.setOnMenuItemClickListener({ menuItem ->
            onOptionsItemSelected(
                menuItem
            )
        })

        setupRecyclerView()
        bindObserver()
        fetchAllProducts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menuitems, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.sort_name -> {
                utils.displayToast(this@HomeActivity, getString(R.string.coming_soon))
            }
            R.id.sort_price -> {
                utils.displayToast(this@HomeActivity, getString(R.string.coming_soon))
            }
            R.id.filter_name -> {
                utils.displayToast(this@HomeActivity, getString(R.string.coming_soon))
            }
            R.id.filter_brand -> {
                utils.displayToast(this@HomeActivity, getString(R.string.coming_soon))
            }
        }
        return true
    }

    private fun setupRecyclerView() {
        itemsAdapter = ItemsAdapter(this@HomeActivity)
        val mLayoutManager = LinearLayoutManager(this@HomeActivity)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvItems.layoutManager = mLayoutManager
        binding.rvItems.itemAnimator = DefaultItemAnimator()
        itemsAdapter.list = articleItems
        binding.rvItems.adapter = itemsAdapter
        itemsAdapter.notifyDataSetChanged()
    }

    private fun fetchAllProducts() {
        val userId = "1"
        getAllProductsViewModel.makeApiCall(userId)

        /*
                val filterQueryText = ""
                getFilteredProductsViewModel.makeApiCall(userId,filterQueryText)

                val sortByText = ""
                getSortedProductsViewModel.makeApiCall(userId, sortByText)
        */
    }

    private fun bindObserver() {
        getAllProductsViewModel.getAllProductsObserver().observe(this, Observer {
            /*it.items.forEach { item ->
                utils.displayToast(this@HomeActivity, item.toString())
            }
            productItems.clear()
            productItems.addAll(it.items)
            usersItems.clear()
            usersItems.addAll(it.items)
            */
            articleItems.clear()
            articleItems.addAll(it.items)
            itemsAdapter.list = articleItems
            itemsAdapter?.notifyDataSetChanged()


        })
        getAllProductsViewModel.getApiFailureObserver()
            .observe(this, Observer { apiFailureStatusCode ->
                //TODO("Handle Failure")
            })

        getSortedProductsViewModel.getSortedProductsObserver().observe(this, Observer { items ->
            TODO("Handle Sorted Products Response")
        })
        getSortedProductsViewModel.getApiFailureObserver()
            .observe(this, Observer { apiFailureStatusCode ->
                TODO("Handle Failure")
            })

        getFilteredProductsViewModel.getFilteredProductsObserver().observe(this, Observer { items ->
            TODO("Handle Filtered Products Response")
        })
        getFilteredProductsViewModel.getApiFailureObserver()
            .observe(this, Observer { apiFailureStatusCode ->
                TODO("Handle Failure")
            })
    }
}