package dao.implementations

import dao.UrlStorage

import scala.collection.mutable

class InMemoryUrlStorage extends UrlStorage {

  private val urls: mutable.Map[String, String] = new mutable.HashMap[String, String]()

  override def save(originalUrl: String, shortUrl: String, ttl: Long): Unit = {
    urls.put(shortUrl, originalUrl)
  }

  override def load(shortUrl: String): Option[String] = {
    urls.get(shortUrl)
  }

}
