sbtPlugin := true

organization := "sbt"

name := "sbt-liquibase"

libraryDependencies += "org.liquibase" % "liquibase-core" % "3.0.+"

ScriptedPlugin.scriptedSettings

sbtrelease.ReleasePlugin.releaseSettings