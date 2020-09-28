@file:Suppress("ImplicitThis")

package com.example.testapp2
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*




class Home : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: ArrayAdapter<*>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.market_items)
        )
        lv_listView.adapter = adapter
        lv_listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                Toast.makeText(
                    applicationContext,
                    parent?.getItemAtPosition(position).toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }






        auth = FirebaseAuth.getInstance()

        toggle = ActionBarDrawerToggle(this, drawable_layout, R.string.open, R.string.close)
        drawable_layout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.UPLOAD -> {
                    Toast.makeText(
                        applicationContext,
                        "Click Item 1", Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, UploadPhotos::class.java)
                    startActivity(intent)
                }
                R.id.inbox -> {
                    Toast.makeText(
                        applicationContext,
                        "Click Item 2", Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, Market::class.java)
                    startActivity(intent)
                }
                R.id.market -> {
                    Toast.makeText(
                        applicationContext,
                        "Click Item 3", Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, Market::class.java)
                    startActivity(intent)
                }
                R.id.logOut -> {
                    Logout()
                    Toast.makeText(
                        applicationContext,
                        "Click Item 4", Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun Logout() {
        auth.signOut()
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        val search: MenuItem? = menu?.findItem(R.id.nav_search)
        val searchView: SearchView = search?.actionView as SearchView
        searchView.queryHint = "search something"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

}





