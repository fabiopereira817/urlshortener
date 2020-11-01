package urlshortener.implementations

import java.net.URI

import org.scalatestplus.play.PlaySpec
import urlshortener.implementations.implementations.BasicShortUrlGenerator

class BasicShortUrlGeneratorTest extends PlaySpec {

  private val dateInEpochMillis = 1604232606000L

  "BasicShortUrlGenerator" should {

    "return a base64 id for url" in {
      val urlGenerator = new BasicShortUrlGenerator(None, 10, new MockClock(dateInEpochMillis))
      urlGenerator.generateShortUrl(URI.create("http://sample.url")) mustBe "9D2ylTJT6"
    }

    "return a base64 id for url with a prefix" in {
      val urlGenerator = new BasicShortUrlGenerator(Some("myPrefix"), 10, new MockClock(dateInEpochMillis))
      urlGenerator.generateShortUrl(URI.create("http://sample.url")) mustBe "myPrefix9D2ylTJT6"
    }

    "return a new base64 id when called in sequence" in {
      val urlGenerator = new BasicShortUrlGenerator(None, 10, new MockClock(dateInEpochMillis))
      urlGenerator.generateShortUrl(URI.create("http://sample.url")) mustBe "9D2ylTJT6"
      urlGenerator.generateShortUrl(URI.create("http://sample.url")) mustBe "9D2ylTJT7"
    }

    "return a new base64 id when called in sequence and max representations reached" in {
      val urlGenerator = new BasicShortUrlGenerator(None, 2, new MockClock(dateInEpochMillis))
      urlGenerator.generateShortUrl(URI.create("http://sample.url")) mustBe "9D2ylTJT6"
      urlGenerator.generateShortUrl(URI.create("http://sample.url")) mustBe "9D2ylTJT7"
      urlGenerator.generateShortUrl(URI.create("http://sample.url")) mustBe "9D2ylTJTG"
      urlGenerator.generateShortUrl(URI.create("http://sample.url")) mustBe "9D2ylTJTH"
    }
  }

}
