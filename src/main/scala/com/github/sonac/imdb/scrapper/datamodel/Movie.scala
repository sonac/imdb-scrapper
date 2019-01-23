package com.github.sonac.imdb.scrapper.datamodel

import com.github.sonac.imdb.scrapper.pageparse.MoviePageParser

case class Movie(title: String, url: String, posterLink: String) extends IMDBObject {

  private def getId: String = url.split("\\/")(4).substring(2)
  private lazy val moviePageParser = MoviePageParser(getId)

  override def toString: String = {
    s"Movie($title, $url, $posterLink)"
  }

  def titleContains(pattern: String): Boolean = {
    title.contains(pattern)
  }

  def movieSummary: Map[String, String] = {
    moviePageParser.summary
  }

  def cast: List[Person] = moviePageParser.cast

  def director(includePhotoLink: Boolean = false): Person = {
    if (includePhotoLink) moviePageParser.director //TODO via go to persona page
    else moviePageParser.director
  }

  def writers(includePhotoLink: Boolean = false): List[Person] = {
    if (includePhotoLink) moviePageParser.writers //TODO via go to persona page
    else moviePageParser.writers
  }

  def boxOffice: Map[String, Int] = moviePageParser.boxOffice

}

object Movie extends IMDBObject {
  def apply(title: String, url: String, posterLink: String) = {
    new Movie(title, adaptObjectUrl(url), removeSizeFromImgUrl(posterLink))
  }
  def apply(id: String): Movie = MoviePageParser(id).getMovie
}