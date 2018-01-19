package com.example.antoniolinguaglossa.musicalbumapp

import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.antoniolinguaglossa.musicalbumapp.dummy.DummyContent
import com.example.antoniolinguaglossa.musicalbumapp.dummy.DummyContent.ITEMS
import com.example.antoniolinguaglossa.musicalbumapp.model.Result
import com.example.antoniolinguaglossa.musicalbumapp.model.ResultCont
import com.example.antoniolinguaglossa.musicalbumapp.util.MyInterface
import com.example.antoniolinguaglossa.musicalbumapp.util.SingletonRetrofit
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*



@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */


    private var mTwoPane: Boolean = false

    private lateinit var simpleItemRecyclerViewAdapter : SimpleItemRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        searchView.setSubmitButtonEnabled(true)
        searchView.setQueryHint("Search...")

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                AsyncTaskExample().execute(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        searchView.isSubmitButtonEnabled = true


        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                    //AsyncTaskExample().execute(searchText.text.toString())
        }*/

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }

        setupRecyclerView(item_list)
    }


    private fun setupRecyclerView(recyclerView: RecyclerView) {

        simpleItemRecyclerViewAdapter = SimpleItemRecyclerViewAdapter(DummyContent.ITEMS)

        recyclerView.adapter = simpleItemRecyclerViewAdapter

        simpleItemRecyclerViewAdapter.setValues(DummyContent.ITEMS)
        simpleItemRecyclerViewAdapter.onTapListener = object : MyInterface {
            override fun tapped(pos: Int) {
                Log.d("Menu position",pos.toString())
                if (mTwoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putInt(ItemDetailFragment.ARG_ITEM_ID, pos)
                        }
                    }
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit()
                } else {
                    val intent = Intent(this@ItemListActivity, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_ITEM_ID, pos)
                    }
                    startActivity(intent)
                }
            }

        }

    }

    class SimpleItemRecyclerViewAdapter(private var mValues: List<Result>) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        lateinit var onTapListener : MyInterface

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = mValues[position]
            holder.mContentView!!.setText(mValues.get(position).artistName)
            holder.mContentView2!!.setText(mValues.get(position).trackName)
            Picasso.with(holder.mContentView3!!.context).load(mValues.get(position).artworkUrl60).into(holder.mContentView3)

            with(holder.itemView) {
                tag = item
                setOnClickListener(View.OnClickListener { onTapListener.tapped(position) })

            }
        }


        fun setValues(list : List<Result>)  {
            mValues = list
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
            val mContentView: TextView? = mView.findViewById(R.id.content);
            val mContentView2: TextView? = mView.findViewById(R.id.content2);
            val mContentView3: ImageView? = mView.findViewById(R.id.content3);
        }
    }


    inner class AsyncTaskExample: AsyncTask<String, String, ResultCont?>() {

        override fun onPreExecute() {
            super.onPreExecute()
            indeterminateBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg p0: String?): ResultCont? {

            Log.i("tag", "Synchronizing data [ START ]")
            val s = p0[0]
            //var headers = BackendServiceHeaderMap.obtain()
            val service = SingletonRetrofit.instance.mySingletonRetrofit
            //val credentials = UserLoginRequest("username", "password")
            //val tokenResponse = service
            //        .login(headers, credentials)
            //        .execute()
            val tokenResponse = service
                    //.getResults("Zucchero")
                    .getResults(s!!)
                    .execute()

            Log.i("tokenResponse", tokenResponse.toString())

            Log.i("tag", "Synchronizing data [ END ]")

            ITEMS = ArrayList(tokenResponse.body()!!.results)

            return tokenResponse.body()
        }

        override fun onPostExecute(result: ResultCont?) {
            super.onPostExecute(result)

            (item_list.adapter as SimpleItemRecyclerViewAdapter).setValues(result?.results!!)

            indeterminateBar.visibility = View.INVISIBLE

            if (result.toString() == "") {
                Log.i("network", "Network Error")
            } else {
                Log.i("network", result.toString())
            }
        }
    }
}

