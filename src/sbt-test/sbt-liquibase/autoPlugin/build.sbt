lazy val root = (project in file(".")).enablePlugins(Liquibase)

libraryDependencies += "com.h2database" % "h2" % "1.3.170"

LiquibaseKeys.driver := "org.h2.Driver"

LiquibaseKeys.url := "jdbc:h2:target/db/test;AUTO_SERVER=TRUE"

LiquibaseKeys.username := ""

LiquibaseKeys.password := ""