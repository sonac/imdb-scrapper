package com.github.sonac.imdb.scrapper.search

import com.github.sonac.imdb.scrapper.datamodel.Movie
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.extractor
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{attr, elementList, text}
import net.ruippeixotog.scalascraper.dsl.DSL._

trait IMDBSearch {

  protected val notMovieKeys = List(
    "(TV Episode)", "(Short)", "(TV Mini-Series)", "(Video Game)", "(TV Series)", "(Video)")
  private val browser = JsoupBrowser()


  protected def listObjectsByPattern(searchUrl: String)(f: Movie => Boolean): List[Movie] = {
    (browser.get(searchUrl) >> extractor(".findList tr", elementList))
      .map(tr => Movie(tr >> extractor(".result_text", text),
        tr >> extractor("a[href]", attr("href")),
        tr >> extractor("img", attr("src"))))
      .filter(f)
  }

}
