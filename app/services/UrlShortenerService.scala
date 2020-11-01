package services

import java.net.URI

import dao.UrlStorage
import javax.inject.{Inject, Singleton}
import play.api.Configuration
import urlshortener.implementations.UrlShortener

@Singleton
class UrlShortenerService @Inject()(urlShortener: UrlShortener, urlStorage: UrlStorage, config: Configuration) {

  private val defaultTtl = config.get[Long]("url.ttl.max.in.mins")
  private val baseUrl = config.get[String]("application.baseUrl")

  def createShortUrl(originalUrl: String, ttl: Option[Long]): String = {
    val shortUrl = urlShortener.generateShortUrl(URI.create(originalUrl))
    urlStorage.save(originalUrl, shortUrl, ttl.getOrElse(defaultTtl))
    baseUrl + shortUrl
  }

  def retrieveOriginalUrl(shortUrl: String): Option[String] = {
    urlStorage.load(shortUrl)
  }

}
