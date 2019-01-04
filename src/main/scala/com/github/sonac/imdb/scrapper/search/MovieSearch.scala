package com.github.sonac.imdb.scrapper.search

import com.github.sonac.imdb.scrapper.datamodel.Movie
import com.github.sonac.imdb.scrapper.rootUrl

case class MovieSearch(pattern: String*) extends IMDBSearch {

  private val adaptedPattern = pattern.reduce((w1, w2) => w1 + "+" + w2)
  private val searchUrl = rootUrl + "/find?ref_=nv_sr_fn&q=" + adaptedPattern + "&s=tt"

  def getAllMovies: List[Movie] = {
    listObjectsByPattern(searchUrl)((x: Movie) => !notMovieKeys.exists(x.titleContains))
  }

}
