package com.github.sonac.imdb.scrapper.pageparse

import com.github.sonac.imdb.scrapper.datamodel.Movie
import com.github.sonac.imdb.scrapper.rootUrl
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.extractor
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{attr, elementList, text, element}
import net.ruippeixotog.scalascraper.dsl.DSL._

trait PageParser {

  protected val browser = JsoupBrowser()

}

case class MoviePageParser(movieId: String) extends PageParser {
  private val movieUrl = rootUrl + "/title/tt" + movieId
  private val page = browser.get(movieUrl)

  def getMovie: Movie = {
    val mainBlock = page >> element(".vital")
    Movie(mainBlock >> text("h1"), movieUrl, mainBlock >> extractor("img", attr("src")))
  }

  def summary: Map[String, String] = {
    val plotSummary = page >> element(".plot_summary")
    Map(
      "story" -> plotSummary >> text(".summary_text"),
      "director" -> (plotSummary >> elementList(".credit_summary_item"))
        .filter(el => (el >> text(".inline")) == "Director:").head >> text("a[href]"),
      "writer" -> ((plotSummary >> elementList(".credit_summary_item"))
        .filter(el => (el >> text(".inline")).startsWith("Writer")).head >> elementList("a[href]"))
        .map(el => el.text)
        .takeWhile(!_.contains("more credits")) //Removing link to others
        .reduce(_ + ", " + _),
      "stars" -> ((plotSummary >> elementList(".credit_summary_item"))
        .filter(el => (el >> text(".inline")) == "Stars:").head >> elementList("a[href]"))
        .map(el => el.text)
        .dropRight(1) //Removing See full cast & crew link
        .reduce(_ + ", " + _)
    )
  }

}