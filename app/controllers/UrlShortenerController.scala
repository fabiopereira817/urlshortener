package controllers

import dto.ShortUrlRequest
import javax.inject._
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc._
import services.UrlShortenerService

@Singleton
class UrlShortenerController @Inject()(cc: ControllerComponents, urlShortenerService: UrlShortenerService)
  extends AbstractController(cc) {

  def saveUrl: Action[JsValue] = Action(parse.json) { request =>
    val urlResult = request.body.validate[ShortUrlRequest]
    urlResult.fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      shortUrlRequest => {
        val shortUrlResponse = urlShortenerService.createShortUrl(shortUrlRequest.url, shortUrlRequest.ttl)
        Ok(Json.toJson(shortUrlResponse))
      }
    )
  }

  def getOriginalUrl(shortUrl: String): Action[AnyContent] = Action { _ =>
    urlShortenerService.retrieveOriginalUrl(shortUrl) match {
      case Some(url) => Redirect(url, statusCode = MOVED_PERMANENTLY)
      case None => NotFound("Url not found")
    }
  }

}
