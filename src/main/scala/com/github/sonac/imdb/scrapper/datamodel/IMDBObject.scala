package com.github.sonac.imdb.scrapper.datamodel

import com.github.sonac.imdb.scrapper.pageparse.MoviePageParser

trait IMDBObject {

  protected def getObjectInfo(id: String): IMDBObject = {
    MoviePageParser(id).getMovie
  }

}
