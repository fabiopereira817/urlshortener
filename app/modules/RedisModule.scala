package modules

import dao.UrlStorage
import dao.implementations.RedisUrlStorage
import play.api.inject.Binding
import play.api.{Configuration, Environment}

class RedisModule extends play.api.inject.Module {

  def bindings(environment: Environment, config: Configuration): Seq[Binding[UrlStorage]] = {
    Seq(bind[UrlStorage].toInstance(new RedisUrlStorage(config.get[String]("redis.host"), config.get[Int]("redis.port"))))
  }

}
