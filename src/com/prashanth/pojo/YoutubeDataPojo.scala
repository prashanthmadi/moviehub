package com.prashanth.pojo

sealed trait YoutubeData
case class YoutubeVideoIdData(movieId: String, snippet: YoutubeVideoIdSnippet, status: YoutubeVideoIdStatus, stats: YoutubeVideoIdStats) extends YoutubeData
case class YoutubeVideoIdSnippet(title: String, imageUrl: String) extends YoutubeData
case class YoutubeVideoIdStatus(privacyStatus: String, publicStatsViewable: Boolean) extends YoutubeData
case class YoutubeVideoIdStats(viewCount: Int, likes: Int, dislikes: Int, favourites: Int, comments: Int) extends YoutubeData
