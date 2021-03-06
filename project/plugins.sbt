// Comment to get more information during initialization
//logLevel := Level.Warn

// Sbt plugins

// Use Scala.js v 1.5.0
addSbtPlugin("com.vmunier" % "sbt-web-scalajs" % "1.1.0")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.5.0")


addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.5")
addSbtPlugin("org.foundweekends.giter8" % "sbt-giter8-scaffold" % "0.11.0")
addSbtPlugin("org.portable-scala"        % "sbt-scalajs-crossproject"  % "1.0.0")
addSbtPlugin("com.typesafe.sbt"          % "sbt-gzip"                  % "1.0.2")
addSbtPlugin("com.typesafe.sbt"          % "sbt-digest"                % "1.1.4")
addSbtPlugin("com.typesafe.sbteclipse"   % "sbteclipse-plugin"         % "5.2.4")
