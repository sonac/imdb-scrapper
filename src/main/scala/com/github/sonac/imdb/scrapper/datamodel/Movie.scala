package com.github.sonac.imdb.scrapper.datamodel

import com.github.sonac.imdb.scrapper.pageparse.MoviePageParser
import com.github.sonac.imdb.scrapper.rootUrl

class Movie(title: String, url: String, posterLink: String) extends IMDBObject {

  private def getId: String = url.split("\\/")(4).substring(2)

  override def toString: String = {
    s"Movie($title, $url, $posterLink)"
  }

  def titleContains(pattern: String): Boolean = {
    title.contains(pattern)
  }

  def movieSummary: Map[String, String] = {
    MoviePageParser(getId).summary
  }

  def cast: List[Person] = ???

  def director: Person = ???

  def writers: List[Person] = ???

  def boxOffice: Map[String, Int] = ???

}

object Movie extends IMDBObject {
  def apply(title: String, url: String, posterLink: String): Movie = {
    new Movie(
      title,
      (if (url.contains("imdb")) url else rootUrl + url).split("\\?").head,
      //Removing size of picture from url
      posterLink.substring(0, posterLink.indexOf("_")) + "jpg")
  }

  def apply(id: String): Movie = getObjectInfo(id).asInstanceOf[Movie]
}