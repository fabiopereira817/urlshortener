package urlshortener.implementations.implementations

import java.net.URI
import java.time.Clock

import external.com.github.tototoshi.base62._
import urlshortener.implementations.UrlShortener

/**
 * This Url Shortener will create a base62 ([A-Z][a-z][0-9]) short id that can be used to represent a long url.
 * Needs a clock to generate the unique id. Plus an optional prefix. and a max number of representations per millisecond
 *
 * It will be created as a Base62 representation of: ${currentDate}${internalCounter}
 *
 * CurrentDate will be YYMMDDHHmmssSSS
 * internal counter will be a number between 0 and 9999, and once it reaches max value we reset currentDate value to now()
 * This will allow us to have 10000 possibles ids per millisecond.
 *
 * As an example: ID 2011011210060000, will be transformed to 9D2ylTJT6 in base62, and this will become our url id
 * 201101 YYMMDD
 * 121006000 HHmmssSSS
 * 0 counter (capped at max number of representations per millisecond allowed). The greater the value the longer the short url,
 * too small value and collisions will arise and client will have to handle them.
 * This value is recommended based on the estimated load of server.
 *
 * If optional prefix is added, it will be prepended to the generated id, this will allow to have unique ids when running multiple servers
 *
 * @param clock the clock used to generate a unique id for this url
 */
class BasicShortUrlGenerator(prefixCandidate: Option[String], representationsPerMillisecond: Long, clock: Clock) extends UrlShortener {

  import java.text.SimpleDateFormat

  private val sdf = new SimpleDateFormat("YYMMddHHmmssSSS")
  private val prefix = prefixCandidate.getOrElse("").trim

  private var lastDateSet = clock.millis()
  private var idBasedOnDate = sdf.format(lastDateSet)
  private var idCounter: Int = 0

  override def generateShortUrl(url: URI): String = {
    val uniqueId = generateUniqueId
    val base62 = new Base62
    s"$prefix${base62.encode(uniqueId.toLong)}"
  }

  private def generateUniqueId: String = {
    this.synchronized {
      val uniqueId = s"$idBasedOnDate$idCounter"
      idCounter = idCounter + 1
      resetBaseDateAndCounterIfNeeded()
      uniqueId
    }
  }

  private def resetBaseDateAndCounterIfNeeded(): Unit = {
    if (idCounter == representationsPerMillisecond) {
      idCounter = 0
      lastDateSet = clock.millis()
      idBasedOnDate = sdf.format(lastDateSet)
    }
  }
}
