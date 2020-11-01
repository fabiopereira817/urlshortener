package dto

import play.api.libs.json.{Json, Reads, Writes}

case class ShortUrlResponse(sourceUrl: String, shortUrl: String, ttl: Option[Long])

object ShortUrlResponse {

  implicit val shortUrlRequestReads: Reads[ShortUrlResponse] = Json.reads[ShortUrlResponse]
  implicit val shortUrlRequestWrites: Writes[ShortUrlResponse] = Json.writes[ShortUrlResponse]

}


