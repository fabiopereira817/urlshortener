package services

import java.net.URI

import dao.UrlStorage
import dto.ShortUrlResponse
import javax.inject.{Inject, Singleton}
import play.api.Configuration
import urlshortener.implementations.UrlShortener

@Singleton
class UrlShortenerService @Inject()(urlShortener: UrlShortener, urlStorage: UrlStorage, config: Configuration) {

  private val defaultTtl = config.get[Long]("url.ttl.max.in.mins")
  private val baseUrl = config.get[String]("application.baseUrl")

  def createShortUrl(originalUrl: String, ttl: Option[Long]): ShortUrlResponse = {
    val shortUrl = urlShortener.generateShortUrl(URI.create(originalUrl))
    val myTtl = ttl.getOrElse(defaultTtl)
    urlStorage.save(originalUrl, shortUrl, myTtl)
    ShortUrlResponse(originalUrl, baseUrl + shortUrl, Some(myTtl))
  }

  def retrieveOriginalUrl(shortUrl: String): Option[String] = {
    urlStorage.load(shortUrl)
  }

}
