package urlshortener.implementations

import java.net.URI

trait UrlShortener {

  def generateShortUrl(url: URI): String

}
