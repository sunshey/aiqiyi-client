package aiqiyi.yc.com.index.model.bean

import android.os.Parcel
import android.os.Parcelable
import com.alibaba.fastjson.annotation.JSONField

/**
 *
 * Created by wanglin  on 2018/4/27 17:32.
 */
class MovieInfo() : Parcelable {
    var id = 0
    @JSONField(name = "moviename")
    var name = ""
    @JSONField(name = "moviecover")
    var cover = ""
    @JSONField(name = "moviescore")
    var score = ""
    @JSONField(name = "movieactor")
    var actor = ""
    @JSONField(name = "movieurl")
    var playUrl = ""

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        cover = parcel.readString()
        score = parcel.readString()
        actor = parcel.readString()
        playUrl = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(cover)
        parcel.writeString(score)
        parcel.writeString(actor)
        parcel.writeString(playUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieInfo> {
        override fun createFromParcel(parcel: Parcel): MovieInfo {
            return MovieInfo(parcel)
        }

        override fun newArray(size: Int): Array<MovieInfo?> {
            return arrayOfNulls(size)
        }
    }


}