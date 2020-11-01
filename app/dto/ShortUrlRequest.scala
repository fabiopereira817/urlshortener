package dto

import play.api.libs.json._

case class ShortUrlRequest(url: String, ttl: Option[Long])

object ShortUrlRequest {

  implicit val shortUrlRequestReads: Reads[ShortUrlRequest] = Json.reads[ShortUrlRequest]
  implicit val shortUrlRequestWrites: Writes[ShortUrlRequest] = Json.writes[ShortUrlRequest]

}
