package modules

import java.time.Clock

import play.api.inject.Binding
import play.api.{Configuration, Environment}
import urlshortener.implementations.UrlShortener
import urlshortener.implementations.implementations.BasicShortUrlGenerator

class BasicShortUrlGeneratorModule extends play.api.inject.Module {

  def bindings(environment: Environment, config: Configuration): Seq[Binding[UrlShortener]] = {
    val uniqueServerId: Option[String] = config.getOptional[String]("server.id")

    val maxPossibleUrlRepresentationsPerMillis: Int =
      config.getOptional[Int]("url.basic.shortener.max.representations.per.millisec").getOrElse(10000)

    Seq(bind[UrlShortener].toInstance(new BasicShortUrlGenerator(uniqueServerId, maxPossibleUrlRepresentationsPerMillis, Clock.systemUTC())))
  }

}
