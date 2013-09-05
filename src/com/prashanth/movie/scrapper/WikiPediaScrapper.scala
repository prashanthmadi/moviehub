package com.prashanth.movie.scrapper

import scala.io.Source
import scala.xml.XML
import scala.util.matching.Regex

class WikiPediaScrapper {

  def getWikiPediaLinkData(url: String, year: Int): String = {
    var output = ""
    output += ("movieyear--" + year + "\n")
    output += getWikiPediaLinkData(url + year)
    output
  }

  def getWikiPediaLinkData(url: String): String = {
    var output = ""
    val urlData = Source.fromURL(url, "UTF-8").mkString
    val xmlObj = XML.loadString(urlData);
    val data = (xmlObj \\ "api" \\ "query" \\ "pages" \\ "page" \\ "revisions" \\ "rev").text
    val splitteddata = data.toString().split("\\{\\|")
    for (eachdata <- splitteddata if eachdata.contains("wikitable")) {
      for (currentline <- eachdata.split("\n") if ("^\\|(\\s)?\\'\\'".r findAllIn (currentline)).nonEmpty) {
        output += (currentline + "\n")
      }
    }
    output
  }

}