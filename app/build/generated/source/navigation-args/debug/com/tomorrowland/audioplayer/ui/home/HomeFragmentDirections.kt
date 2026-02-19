package com.tomorrowland.audioplayer.ui.home

import android.os.Bundle
import androidx.navigation.NavDirections
import com.tomorrowland.audioplayer.R
import kotlin.Int

public class HomeFragmentDirections private constructor() {
  private data class ActionHomeToNowPlaying(
    public val trackId: Int = 0,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_home_to_nowPlaying

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("trackId", this.trackId)
        return result
      }
  }

  public companion object {
    public fun actionHomeToNowPlaying(trackId: Int = 0): NavDirections =
        ActionHomeToNowPlaying(trackId)
  }
}
