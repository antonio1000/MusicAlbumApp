package com.example.antoniolinguaglossa.musicalbumapp

import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.design.widget.Snackbar
import android.system.Os.bind
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import com.example.antoniolinguaglossa.musicalbumapp.api.BackendServiceHeaderMap
import com.example.antoniolinguaglossa.musicalbumapp.api.JournalerBackendService
import com.example.antoniolinguaglossa.musicalbumapp.api.TokenManager
import com.example.antoniolinguaglossa.musicalbumapp.api.UserLoginRequest

import com.example.antoniolinguaglossa.musicalbumapp.dummy.DummyContent
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*

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

    @BindView(R.id.searchText)
    var textView1: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                    AsyncTaskExample().execute();
        }

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
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane)
    }

    class SimpleItemRecyclerViewAdapter(private val mParentActivity: ItemListActivity,
                                        private val mValues: List<DummyContent.DummyItem>,
                                        private val mTwoPane: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val mOnClickListener: View.OnClickListener

        init {
            mOnClickListener = View.OnClickListener { v ->
                val item = v.tag as DummyContent.DummyItem
                if (mTwoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(ItemDetailFragment.ARG_ITEM_ID, item.id)
                        }
                    }
                    mParentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = mValues[position]
            holder.mIdView.text = item.id
            holder.mContentView.text = item.content

            with(holder.itemView) {
                tag = item
                setOnClickListener(mOnClickListener)
            }
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
            val mIdView: TextView = mView.id_text
            val mContentView: TextView = mView.content
        }
    }

    /**
     * Authenticates user synchronously,
     * then executes async calls for notes and TODOs fetching.
     * Pay attention on synchronously triggered call via execute() method.
     * Its asynchronous equivalent is: enqueue().
     */
    private fun startMe() {
        //executor.execute {
            Log.i("tag", "Synchronizing data [ START ]")
            var headers = BackendServiceHeaderMap.obtain()
            val service = JournalerBackendService.obtain()
            //val credentials = UserLoginRequest("username", "password")
            //val tokenResponse = service
            //        .login(headers, credentials)
            //        .execute()
            val tokenResponse = service
                    .getResults("Zucchero")
                    .execute()
            //if (tokenResponse.isSuccessful) {
            //    val token = tokenResponse.body()
            //    token?.let {
            //        TokenManager.currentToken = token
            //        headers = BackendServiceHeaderMap.obtain(true)
                    //fetchNotes(service, headers)
                    //fetchTodos(service, headers)
            //    }
            //}

            Log.i("tag", "Synchronizing data [ END ]")
        //}
    }

    inner class AsyncTaskExample: AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            //MyprogressBar.visibility = View.VISIBLE;
        }

        override fun doInBackground(vararg p0: String?): String {

            var Result: String = "";
            //It will return current data and time.
            startMe()

            return Result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            //MyprogressBar.visibility = View.INVISIBLE;

            if (result == "") {
                Log.i("network", "Network Error...Is OK");
            } else {
                Log.i("network", result);
            }
        }
    }
}
