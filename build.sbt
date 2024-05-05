import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}


ThisBuild / organization := "io.github.nafg.scala-phonenumber"

ThisBuild / scalaVersion := "3.4.1"
ThisBuild / crossScalaVersions := Seq("2.13.14", (ThisBuild / scalaVersion).value)

ThisBuild / scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

def circeVersion = "0.14.7"

lazy val scalaPhoneNumber =
  crossProject(JVMPlatform, JSPlatform).crossType(CrossType.Full)
    .in(file("."))
    .settings(
      name := "scala-phonenumber",
      libraryDependencies += "io.circe" %%% "circe-generic" % circeVersion
    )
    .jvmSettings(libraryDependencies += "com.googlecode.libphonenumber" % "libphonenumber" % "8.13.37")
    .jsConfigure(_.enablePlugins(ScalaJSBundlerPlugin))
    .jsSettings(Compile / npmDependencies += "libphonenumber-js" -> "1.9.47")
