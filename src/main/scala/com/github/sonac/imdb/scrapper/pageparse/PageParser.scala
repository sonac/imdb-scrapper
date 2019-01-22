package com.github.sonac.imdb.scrapper.pageparse

import com.github.sonac.imdb.scrapper.datamodel.{ Movie, Person }
import com.github.sonac.imdb.scrapper.rootUrl
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.extractor
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{ attr, element, elementList, text, table }
import net.ruippeixotog.scalascraper.dsl.DSL._

trait PageParser {

  protected val browser = JsoupBrowser()

}

case class MoviePageParser(movieId: String) extends PageParser {
  private val movieUrl = rootUrl + "/title/tt" + movieId
  private val creditsUrl = movieUrl + "/fullcredits"
  private lazy val moviePage = browser.get(movieUrl)
  private lazy val creditsPage = browser.get(creditsUrl)
  private lazy val creditsTables = creditsPage >> elementList(".simpleTable.simpleCreditsTable")
  private val boxOfficeAttrs = List(
    "Budget:",
    "Opening Weekend USA:",
    "Gross USA:",
    "Cumulative Worldwide Gross:")

  private def parseIntFromBudget(budgetString: String): Int = {
    budgetString
      .substring(budgetString.indexOf('$') + 1)
      .takeWhile(_ != ' ')
      .replaceAll(",", "")
      .toInt
  }

  private def optionalAttrsFilter(attr: Option[String]): Boolean = {
    boxOfficeAttrs.contains(attr.getOrElse(""))
  }

  def getMovie: Movie = {
    val mainBlock = moviePage >> element(".vital")
    Movie(mainBlock >> text("h1"), movieUrl, mainBlock >> extractor("img", attr("src")))
  }

  def summary: Map[String, String] = {
    val plotSummary = moviePage >> element(".plot_summary")
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
        .reduce(_ + ", " + _))
  }

  def cast: List[Person] = {
    val castTable = creditsPage >> table(".cast_list")
    castTable
      .map(vec => (vec.tail.head >?> text("a"),
        vec.tail.head >?> extractor("a[href]", attr("href")),
        vec.head >?> extractor("img", attr("loadlate"))))
      .filter(opts => opts._1.isDefined && opts._2.isDefined).map(s => s match {
        case (Some(x1), Some(x2), Some(x3)) => Person(x1, x2, x3)
        case (Some(x1), Some(x2), None) => Person(x1, x2, "")
      }).toList
  }

  def director: Person = {
    Person(
      creditsTables.head >> text("a"),
      creditsTables.head >> extractor("a[href]", attr("href")),
      "")
  }

  def writers: List[Person] = {
    val writersRows = creditsTables.tail.head >> elementList(".name")
    writersRows.map { el =>
      val a = el >> element("a")
      Person(a.text, a.attr("href"), "")
    }
  }

  def boxOffice: Map[String, Int] = {
    (moviePage >> elementList(".txt-block"))
      .filter(el => optionalAttrsFilter(el >?> text(".inline")))
      .map(el => (el >> text(".inline")) -> parseIntFromBudget(el.text))
      .toMap
  }

}

case class PersonPageParser(personId: String) extends PageParser {
  private val personUrl = rootUrl + "/name/nm" + personId
  private val bioUrl = personUrl + "/bio"
  private lazy val personPage = browser.get(personUrl)
  private lazy val bioPage = browser.get(bioUrl)

  def getPerson: Person = {
    val mainBlock = personPage >> element("tbody")
    Person(
      (mainBlock >> element("h1")) >> text(".itemprop"),
      personUrl,
      mainBlock >> extractor("img", attr("src")))
  }

  def getBio: String = {
    println(bioPage >> element(".soda.odd"))
    (bioPage >> element(".soda.odd")) >> text("p")
  }

}