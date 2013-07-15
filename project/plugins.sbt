import sbt._
import sbt.Keys._

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies <+= sbtVersion { v => "org.scala-sbt" % "scripted-plugin" % v }

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.0-SNAPSHOT")

libraryDependencies += "org.liquibase" % "liquibase-core" % "3.0.2"
