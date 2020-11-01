lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """url-shortener""",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      guice,
      "net.debasishg" %% "redisclient" % "3.30",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )
