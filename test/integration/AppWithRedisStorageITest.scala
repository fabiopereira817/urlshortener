package integration

import com.dimafeng.testcontainers.{ForAllTestContainer, GenericContainer}
import dao.UrlStorage
import dao.implementations.RedisUrlStorage
import dto.{ShortUrlRequest, ShortUrlResponse}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Configuration
import play.api.http.Status
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

class AppWithRedisStorageITest extends PlaySpec with GuiceOneAppPerSuite with ForAllTestContainer {

  override val container: GenericContainer = GenericContainer("redis:latest", exposedPorts = Seq(6379))

  "UrlShortenerController" should {

    "saveUrl in storage" in {
      val storage = new RedisUrlStorage("localhost", container.container.getMappedPort(6379))

      val myApp = new GuiceApplicationBuilder()
        .overrides(bind[UrlStorage].toInstance(storage))
        .build()

      val configuration = myApp.injector.instanceOf[Configuration]

      val json = Json.toJson(ShortUrlRequest(url = "http://www.google.com", ttl = None))
      val request = FakeRequest(POST, "/").withJsonBody(json)

      val generateUrl = route(myApp, request).get

      status(generateUrl) mustBe Status.OK

      val response = Json.parse(contentAsString(generateUrl)).validate[ShortUrlResponse].get
      response.sourceUrl mustBe "http://www.google.com"
      response.shortUrl mustNot be(empty)

      val expectedShortUrl = response.shortUrl.replaceAll(configuration.get[String]("application.baseUrl"), "")
      storage.load(expectedShortUrl) mustBe Some(response.sourceUrl)
    }

    "getOriginalUrl should return 404 when url not found" in {
      val myApp = new GuiceApplicationBuilder()
        .overrides(bind[UrlStorage].toInstance(new RedisUrlStorage("localhost", container.container.getMappedPort(6379))))
        .build()

      val getRequest = route(myApp, FakeRequest(GET, "/invalidUrl")).get

      status(getRequest) mustBe Status.NOT_FOUND
      redirectLocation(getRequest) mustBe None
    }

    "getOriginalUrl should return a redirect to a valid url" in {
      val storage = new RedisUrlStorage("localhost", container.container.getMappedPort(6379))
      storage.save("http://www.google.com", "abcd", 1)

      val myApp = new GuiceApplicationBuilder()
        .overrides(bind[UrlStorage].toInstance(storage))
        .build()

      val getRequest = route(myApp, FakeRequest(GET, "/abcd")).get

      status(getRequest) mustBe Status.MOVED_PERMANENTLY
      redirectLocation(getRequest) mustBe Some("http://www.google.com")
    }
  }

}
