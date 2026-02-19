package com.tomorrowland.audioplayer.ui.nowplaying

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class NowPlayingFragmentArgs(
  public val trackId: Int = 0,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("trackId", this.trackId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("trackId", this.trackId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): NowPlayingFragmentArgs {
      bundle.setClassLoader(NowPlayingFragmentArgs::class.java.classLoader)
      val __trackId : Int
      if (bundle.containsKey("trackId")) {
        __trackId = bundle.getInt("trackId")
      } else {
        __trackId = 0
      }
      return NowPlayingFragmentArgs(__trackId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): NowPlayingFragmentArgs {
      val __trackId : Int?
      if (savedStateHandle.contains("trackId")) {
        __trackId = savedStateHandle["trackId"]
        if (__trackId == null) {
          throw IllegalArgumentException("Argument \"trackId\" of type integer does not support null values")
        }
      } else {
        __trackId = 0
      }
      return NowPlayingFragmentArgs(__trackId)
    }
  }
}
