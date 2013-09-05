package com.prashanth.movie.scrapper

import scala.io.Source
import scala.util.parsing.json.JSON

class YouTubeScrapper {

  def getYoutubeLinkData(url: String): String = {
    val urlData = Source.fromURL(url, "UTF-8").mkString
    var totalIds = ""
    val yoututbeVideoIdPattern = """data-video-ids="([^"]*)""".r
    for (eachId <- (yoututbeVideoIdPattern findAllMatchIn urlData).toList.drop(1).dropRight(1)) {
      totalIds += (eachId.group(1) + "\n")
    }
    totalIds
  }

  def getYoutubeData(url: String) {
    val urlData = Source.fromURL(url, "UTF-8").mkString
    JSON.Parser(urlData)
  }

}

object YouTubeScrapper {

}