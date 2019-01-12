package com.github.sonac.imdb.scrapper

import com.github.sonac.imdb.scrapper.datamodel.{Movie, Person}
import com.github.sonac.imdb.scrapper.search.MovieSearch

object Main extends App {
  val godfather = MovieSearch("the", "godfather").getAllMovies.head
  /*
  Movie(Der Pate (1972) aka "The Godfather", https://www.imdb.com/title/tt0068646/, https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkE
  yXkFqcGdeQXVyNzkwMjQ5NzM@.jpg)
  */
  println(godfather.cast.take(5))
}
