import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}


ThisBuild / organization := "io.github.nafg.scala-phonenumber"
ThisBuild / scalaVersion := "2.12.10"
ThisBuild / scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

def circeVersion = "0.12.3"

lazy val scalaPhoneNumber =
  crossProject(JVMPlatform, JSPlatform).crossType(CrossType.Full)
    .in(file("."))
    .settings(
      name := "scala-phonenumber",
      libraryDependencies += "io.circe" %%% "circe-generic" % circeVersion
    )
    .jvmSettings(libraryDependencies += "com.googlecode.libphonenumber" % "libphonenumber" % "8.11.3")
    .jsConfigure(_.enablePlugins(ScalaJSBundlerPlugin))
    .jsSettings(Compile / npmDependencies += "libphonenumber-js" -> "1.7.34")
