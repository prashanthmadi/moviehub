package com.prashanth.movie.scrapper

import scala.io.Source

import com.prashanth.pojo.YoutubeVideoIdData
import com.prashanth.pojo.YoutubeVideoIdSnippet
import com.prashanth.pojo.YoutubeVideoIdStats
import com.prashanth.pojo.YoutubeVideoIdStatus

class YoutubeDataApi {

  def getYoutubeData(url: String): YoutubeVideoIdData = {
    try {
      val urlData = Source.fromURL(url, "UTF-8").mkString
      val youtube = JsonElement.parse(urlData).get
      val totalData = youtube.items.at(0)
      val snippet = YoutubeVideoIdSnippet(totalData.snippet.title, totalData.snippet.thumbnails.default.url)
      val status = YoutubeVideoIdStatus(totalData.status.privacyStatus, totalData.status.publicStatsViewable)
      val stats = YoutubeVideoIdStats(totalData.statistics.viewCount, totalData.statistics.likeCount, totalData.statistics.dislikeCount, totalData.statistics.viewCount, totalData.statistics.commentCount)
      val youtubeout = YoutubeVideoIdData(totalData.id, snippet, status, stats)
      return youtubeout
    } catch {
      case e: Exception =>
        println(e.getMessage())
        return null;
    }

  }

}