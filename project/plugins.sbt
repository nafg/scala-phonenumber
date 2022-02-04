val sjsVer = sys.env.getOrElse("SCALAJS_VERSION", "1.5.0")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % sjsVer)

sjsVer.split("\\.") match {
  case Array("0", "6", _)             => addSbtPlugin("ch.epfl.scala" % s"sbt-scalajs-bundler-sjs06" % "0.19.0")
  case Array("1", "0" | "1" | "2", _) => addSbtPlugin("ch.epfl.scala" % s"sbt-scalajs-bundler" % "0.19.0")
  case Array("1", _, _)               => addSbtPlugin("ch.epfl.scala" % s"sbt-scalajs-bundler" % "0.20.0")
}

addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.0.0")

addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.5.10")
addSbtPlugin("io.github.nafg.mergify" % "sbt-mergify-github-actions" % "0.4.0")
