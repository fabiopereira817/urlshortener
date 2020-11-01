package dao.implementations

import com.redis._
import dao.UrlStorage

import scala.concurrent.duration.Duration

class RedisUrlStorage(host: String, port: Int) extends UrlStorage {

  val r = new RedisClient(host, port)

  override def save(originalUrl: String, shortUrl: String, ttl: Long): Unit = {
    r.set(key = shortUrl, value = originalUrl, expire = Duration.create(ttl, java.util.concurrent.TimeUnit.MINUTES))
  }

  override def load(shortUrl: String): Option[String] = {
    r.get[String](shortUrl)
  }

}
