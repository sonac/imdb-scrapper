package com.github.sonac.imdb.scrapper.datamodel

import com.github.sonac.imdb.scrapper.pageparse.PersonPageParser

case class Person(fullName: String, url: String, photoLink: String) extends IMDBObject {

  private def getId: String = url.split("\\/")(4).substring(2)
  private lazy val personPageParser = PersonPageParser(getId)

  override def toString: String = {
    s"Person($fullName, $url, $photoLink)"
  }

  def bio: String = personPageParser.getBio

}

object Person extends IMDBObject {
  def apply(fullName: String, url: String, photoLink: String): Person = {
    new Person(fullName, adaptObjectUrl(url), removeSizeFromImgUrl(photoLink))
  }
  def apply(id: String): Person = PersonPageParser(id).getPerson
}