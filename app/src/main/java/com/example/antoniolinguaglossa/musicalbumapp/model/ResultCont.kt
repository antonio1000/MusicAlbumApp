package com.example.antoniolinguaglossa.musicalbumapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by antoniolinguaglossa on 17/01/18.
 */

class ResultCont {

    @SerializedName("resultCount")
    @Expose
    var resultCount: Int? = null
    @SerializedName("results")
    @Expose
    var results: List<Result>? = null

}