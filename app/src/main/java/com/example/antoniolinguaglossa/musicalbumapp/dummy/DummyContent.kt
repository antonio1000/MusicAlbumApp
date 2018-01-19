package com.example.antoniolinguaglossa.musicalbumapp.dummy

import com.example.antoniolinguaglossa.musicalbumapp.model.Result
import java.util.ArrayList
import java.util.HashMap


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {


    /**
     * An array of sample (dummy) items.
     */
    var ITEMS: MutableList<Result> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    //val ITEM_MAP: MutableMap<String, Result> = HashMap()

    /*private val COUNT = 10

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            //addItem(createDummyItem(i))
            //addItem(createDummyItem(id, artistName, collectionName, trackName, artworkUrl60, artworkUrl100, trackPrice, releaseDate)
            addItem(createDummyItem(i.toString(), "artistName", "collectionName", "trackName", "artworkUrl60", "artworkUrl100", "trackPrice", "releaseDate"))
        }
    }*/

    /*private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }*/

    //Cancellare
    /*private fun createDummyItem(position: Int): DummyItem {
        return DummyItem(position.toString(), "Item " + position, makeDetails(position))
    }*/

    /*private fun createDummyItem(id: String, artistName: String, collectionName: String, trackName: String, artworkUrl60: String, artworkUrl100: String, trackPrice: String, releaseDate: String): DummyItem {

        return DummyItem(id, artistName, collectionName, trackName, artworkUrl60, artworkUrl100, trackPrice, releaseDate)
    }*/

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    //Cancellare
    /*data class DummyItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }*/

    //data class DummyItem(val id: String, val artistName: String, val collectionName: String, val trackName: String, val artworkUrl60: String, val artworkUrl100: String, val trackPrice: String, val releaseDate: String)
}
