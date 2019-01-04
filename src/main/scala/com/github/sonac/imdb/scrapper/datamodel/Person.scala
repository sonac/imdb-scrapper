package com.github.sonac.imdb.scrapper.datamodel

case class Person(fullName: String, url: String, photoLink: String) extends IMDBObject {

  def filmography: Map[String, List[Movie]] = ???

  def filmography(role: String): List[Movie] = filmography.apply(role)

  def bio: String = ???

}