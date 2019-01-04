package com.github.sonac.imdb.scrapper

import com.github.sonac.imdb.scrapper.datamodel.Movie
import com.github.sonac.imdb.scrapper.search.MovieSearch

object Main extends App {
  MovieSearch("the", "godfather").getAllMovies.foreach(println(_))
  println(Movie("0118842").movieSummary)
}
