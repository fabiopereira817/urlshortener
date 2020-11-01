package dao

trait UrlStorage {

  def save(originalUrl: String, shortUrl: String, ttl: Long): Unit
  def load(shortUrl: String): Option[String]

}
