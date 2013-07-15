import sbt._
import sbt.Keys._

sbtPlugin := true

organization := "sbt"

name := "sbt-liquibase"

libraryDependencies += "org.liquibase" % "liquibase-core" % "3.0.2"

ScriptedPlugin.scriptedSettings
