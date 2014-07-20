sbtPlugin := true

organization := "sbt"

name := "sbt-liquibase"

libraryDependencies += "org.liquibase" % "liquibase-core" % "3.1.1"

scalacOptions += "-deprecation"

scriptedSettings

scriptedLaunchOpts <+= version(v => "-Dproject.version=" + v)

releaseSettings