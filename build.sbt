sbtPlugin := true

organization := "sbt"

name := "sbt-liquibase"

scalaVersion := "2.10.2"

libraryDependencies += "org.liquibase" % "liquibase-core" % "3.0.2"

ScriptedPlugin.scriptedSettings
