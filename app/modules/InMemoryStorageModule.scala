package modules

import dao.UrlStorage
import dao.implementations.InMemoryUrlStorage
import play.api.inject.Binding
import play.api.{Configuration, Environment}

class InMemoryStorageModule extends play.api.inject.Module {

  def bindings(environment: Environment, config: Configuration): Seq[Binding[UrlStorage]] = {
    Seq(bind[UrlStorage].toInstance(new InMemoryUrlStorage()))
  }

}
