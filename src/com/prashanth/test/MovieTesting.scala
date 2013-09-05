package com.prashanth.test

import java.io.PrintWriter

import scala.io.Source

import com.prashanth.movie.scrapper.WikiPediaScrapper
import com.prashanth.movie.scrapper.YouTubeScrapper
import com.prashanth.movie.scrapper.YoutubeDataApi

object MovieTesting {

  val wikilink = "http://en.wikipedia.org/w/api.php?format=xml&action=query&prop=revisions&rvprop=content&titles=List%20of%20Telugu%20films%20of%20"
  val wikilink2013 = "http://en.wikipedia.org/w/api.php?format=xml&action=query&prop=revisions&rvprop=content&titles=2013%20in%20Telugu%20film"
  val currentId = "BUglULzqDPM"
  val youtubeDataApiKey = "AIzaSyD_45TGyvAampe1vaHZWQj2eiGSx05cbwo"
  val youtubeDataApiParams = "&part=snippet,contentDetails,statistics,status&fields=items(id,snippet(title,description,thumbnails),status,statistics)"
  val youtubeDataApiUrl = "https://www.googleapis.com/youtube/v3/videos?key=" + youtubeDataApiKey + youtubeDataApiParams + "&id="

  //https://www.googleapis.com/youtube/v3/videos?key=AIzaSyD_45TGyvAampe1vaHZWQj2eiGSx05cbwo&part=snippet,contentDetails,statistics,status&fields=items(id,snippet(title,description,thumbnails),status,statistics)&id=BUglULzqDPM
  def main(args: Array[String]) {

    /**
     * This would get Data from wikipedia
     */
    def getWikiOutput() {
      var i = 0;
      var wikipedia = new WikiPediaScrapper
      var output = new PrintWriter("output/wikioutputFile.txt")

      for (i <- 1941 to 2013) {
        output.write(wikipedia.getWikiPediaLinkData(wikilink, i))
      }
      output.write(wikipedia.getWikiPediaLinkData(wikilink2013))

    }

    /**
     *
     * Scraps all youtube Data
     *
     */
    def getYoutubeOutput() {
      var youtube = new YouTubeScrapper
      var output = new PrintWriter("output/youtubeTeluguIds.txt")
      val content = Source.fromFile("input/teluguyoutubelinks.txt").mkString
      val splittedcontent = """\n""".r.split(content);
      for (currentsource <- splittedcontent) {
        var splittedlinks = currentsource.split("""\|\|""")
        println(splittedlinks(0))
        for (eachlink <- splittedlinks.drop(1)) {
          var currentdata = youtube.getYoutubeLinkData(eachlink)
          println(currentdata)
          output.write(currentdata)
        }
      }
      output.close()
    }

    /**
     *
     *  Utilizing preivous Data -  Fix this before use
     *
     */
    def filterdata() {
      var output = new PrintWriter("output/youtubelinks.txt")
      val content = Source.fromFile("input/previousdata.txt").mkString
      val splittedcontent = content.split("||")
      for (currentmovie <- splittedcontent) {
        println(("""\s+""".r).replaceAllIn(currentmovie, " "))
        //        output.write(currentmovie + "\n")
      }
    }

    /**
     *
     * Removing duplicate youtube Id's
     *
     */
    def getDistinctYoutubeList() {
      var output = new PrintWriter("output/distinctyoutubeTeluguIds.txt")
      val content = Source.fromFile("output/youtubeTeluguIds.txt").mkString
      val splittedcontent = (content.split("\\n")).distinct
      for (currentmovie <- splittedcontent) {
        output.write(currentmovie + "\n")
      }
      output.close()
    }

    /**
     *
     * Gets all the youtube data using Youtube API and prints to file
     *
     */
    def getYoutubeDataApi() {
      var output = new PrintWriter("output/youtubetotaldata1.txt")
      val content = Source.fromFile("output/distinctyoutubeTeluguIds.txt").mkString
      var youtubeapi = new YoutubeDataApi
      for (currentYoutubeId <- content.split("""\n""")) {
        println(currentYoutubeId)
        val data = youtubeapi.getYoutubeData(youtubeDataApiUrl + currentYoutubeId)
        if (data != null) {
          output.write(data.toString + "\n");
        }
      }
    }

    mixYoutubeWiki()

    def mixYoutubeWiki() {
      var output = new PrintWriter("output/test.txt")
      val content = Source.fromFile("output/wikioutputFile.txt").mkString
      val splitMoviesOnYear = content.split("""movieyear--""")
      for (currentYearData <- splitMoviesOnYear) {
        println(currentYearData)
        val totalCurrentYearData = ("""(\d\d\d\d)""".r findAllIn currentYearData)
        val currentYear = totalCurrentYearData.group(1)
       // val moviesList = totalCurrentYearData.group(2)

        output.write(currentYear + "\n -- \n" )
        output.write("\n\n\n\n\n\n")
      }

    }

  }

}