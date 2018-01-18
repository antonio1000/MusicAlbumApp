package com.example.antoniolinguaglossa.musicalbumapp.util

/**
 * Created by antoniolinguaglossa on 18/01/18.
 */
public class SingletonRetrofit private constructor() {
    init { println("This ($this) is a singleton") }

    private object Holder { val INSTANCE = SingletonRetrofit() }

    companion object {
        val INSTANCE: SingletonRetrofit by lazy { Holder.INSTANCE }
    }
    var b:String? = null
}