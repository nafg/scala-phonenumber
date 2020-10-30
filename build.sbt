import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}


ThisBuild / organization := "io.github.nafg.scala-phonenumber"

ThisBuild / crossScalaVersions := Seq("2.12.10", "2.13.1")
ThisBuild / scalaVersion := (ThisBuild / crossScalaVersions).value.last

ThisBuild / scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

def circeVersion = "0.13.0"

lazy val scalaPhoneNumber =
  crossProject(JVMPlatform, JSPlatform).crossType(CrossType.Full)
    .in(file("."))
    .settings(
      name := "scala-phonenumber",
      libraryDependencies += "io.circe" %%% "circe-generic" % circeVersion
    )
    .jvmSettings(libraryDependencies += "com.googlecode.libphonenumber" % "libphonenumber" % "8.12.12")
    .jsConfigure(_.enablePlugins(ScalaJSBundlerPlugin))
    .jsSettings(Compile / npmDependencies += "libphonenumber-js" -> "1.7.34")
