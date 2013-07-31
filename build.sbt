import sbt._
import sbt.Keys._

sbtPlugin := true

organization := "sbt"

name := "sbt-liquibase"

sbtVersion in Global := "0.13.0-RC4"

scalaVersion in Global := "2.10.2"

libraryDependencies += "org.liquibase" % "liquibase-core" % "3.0.2"

ScriptedPlugin.scriptedSettings
