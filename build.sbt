import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}

ThisBuild / organization := "io.github.nafg.scala-phonenumber"

ThisBuild / scalaVersion       := "3.5.0"
ThisBuild / crossScalaVersions := Seq("2.13.14", (ThisBuild / scalaVersion).value)

ThisBuild / scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

def circeVersion = "0.14.10"

lazy val scalaPhoneNumber =
  crossProject(JVMPlatform, JSPlatform)
    .crossType(CrossType.Full)
    .in(file("."))
    .settings(
      name                                    := "scala-phonenumber",
      coverageExcludedPackages                := "io.github.nafg.scalaphonenumber.facade",
      libraryDependencies += "io.circe"      %%% "circe-generic" % circeVersion,
      libraryDependencies += "org.scalameta" %%% "munit"         % "1.0.2" % Test,
      addCommandAlias("testAndCoverage", "test;coverageReport;coverageAggregate")
    )
    .jvmSettings(libraryDependencies += "com.googlecode.libphonenumber" % "libphonenumber" % "8.13.45")
    .jsEnablePlugins(ScalaJSBundlerPlugin, ScalablyTypedConverterGenSourcePlugin)
    .jsSettings(
      Compile / npmDependencies += "libphonenumber-js" -> "1.11.3",
      stOutputPackage                                  := "io.github.nafg.scalaphonenumber.facade",
      stMinimize                                       := Selection.AllExcept("libphonenumber-js")
    )
