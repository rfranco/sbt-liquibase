import sbt._
import sbt.Keys._
import sbt.Liquibase._

liquibaseSettings

libraryDependencies += "com.h2database" % "h2" % "1.3.170"

liquibaseUsername := ""

liquibasePassword := ""

liquibaseDriver := "org.h2.Driver"

liquibaseUrl := "jdbc:h2:target/db/test;AUTO_SERVER=TRUE"