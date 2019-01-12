package com.github.sonac.imdb.scrapper.datamodel

import com.github.sonac.imdb.scrapper.pageparse.MoviePageParser
import com.github.sonac.imdb.scrapper.rootUrl

trait IMDBObject {

  protected def adaptObjectUrl(objectUrl: String): String = {
    (if (objectUrl.contains("imdb")) objectUrl else rootUrl + objectUrl)
      .split("\\?")
      .head
  }

  protected def removeSizeFromImgUrl(imgUrl: String): String = {
    if (imgUrl.contains("_")) imgUrl.substring(0, imgUrl.indexOf("_")) + "jpg"
    else imgUrl
  }

}
