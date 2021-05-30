import sbtcrossproject.{CrossType, crossProject}


lazy val commonSettings = Seq (
  scalaVersion := "2.13.3",
  organization := "io.github.code-star"
)
lazy val server = (project in file("server")).settings(commonSettings).settings(
  name := "music_app_play_scalajs_server",
  scalaJSProjects := Seq(client),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  pipelineStages := Seq(digest, gzip),
  // triggers scalaJSPipeline when using compile or continuous compilation
  compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
  libraryDependencies ++= Seq(
    guice,
    "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
//    "com.typesafe.slick" %% "slick" % "3.3.3",
    "com.typesafe.play" %% "play-slick" % "5.0.0",
    "com.typesafe.slick" %% "slick-codegen" % "3.3.2",
    "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
    "com.typesafe.play" %% "play-json" % "2.8.1",
    "org.postgresql" % "postgresql" % "42.2.11",
    "com.zaxxer" % "HikariCP" % "2.4.1",
    "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
    "com.h2database" % "h2" % "1.4.199",
    specs2 % Test,
    evolutions,
    ws,
    "commons-codec" % "commons-codec" % "1.14",
    "org.mockito" %% "mockito-scala" % "1.16.0",
    //  jdbc % Test,
    "org.json4s" %% "json4s-ast" % "3.6.10",
    "com.github.tminglei" %% "slick-pg" % "0.19.4",
    "com.vmunier" %% "scalajs-scripts" % "1.1.4",
    "org.mindrot" % "jbcrypt" % "0.4"

  )
).enablePlugins(PlayScala)
  .dependsOn(sharedJvm)

lazy val client = (project in file("client")).settings(commonSettings).settings(
  name := "music_app_play_scalajs_client",
//  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full),
//  scalacOptions += "-P:scalajs:sjsDefinedByDefault",
  scalaJSUseMainModuleInitializer := true,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "1.1.0",
    "org.querki" %%% "jquery-facade" % "2.0",
    "me.shadaj" %%% "slinky-core" % "0.6.7",
    "me.shadaj" %%% "slinky-web" % "0.6.7",
    "com.typesafe.play" %%% "play-json" % "2.9.0"
  )
).enablePlugins(ScalaJSPlugin, ScalaJSWeb)
  .dependsOn(sharedJs)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(
    name := "music_app_play_scalajs_shared",
    commonSettings,
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.8.1"
    )
  )
//  .jvmSettings(
//    libraryDependencies ++= Seq(
//      "com.typesafe.play" %%% "play-json" % "2.8.1"
//    )
//    )
//  .jsSettings(
//
//  )
lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

onLoad in Global := (onLoad in Global).value andThen {s: State => "project server" :: s}

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
