import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}


ThisBuild / organization := "io.github.nafg.scala-phonenumber"

ThisBuild / scalaVersion := "3.2.0"
ThisBuild / crossScalaVersions := Seq("2.13.8", (ThisBuild / scalaVersion).value)

ThisBuild / scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

def circeVersion = "0.14.3"

lazy val scalaPhoneNumber =
  crossProject(JVMPlatform, JSPlatform).crossType(CrossType.Full)
    .in(file("."))
    .settings(
      name := "scala-phonenumber",
      libraryDependencies += "io.circe" %%% "circe-generic" % circeVersion
    )
    .jvmSettings(libraryDependencies += "com.googlecode.libphonenumber" % "libphonenumber" % "8.12.57")
    .jsConfigure(_.enablePlugins(ScalaJSBundlerPlugin))
    .jsSettings(Compile / npmDependencies += "libphonenumber-js" -> "1.9.47")
