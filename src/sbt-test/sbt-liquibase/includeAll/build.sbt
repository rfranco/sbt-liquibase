lazy val root = (project in file(".")).enablePlugins(SbtLiquibase)

libraryDependencies += "com.h2database" % "h2" % "1.3.170"

LiquibaseKeys.url := "jdbc:h2:target/db/test;AUTO_SERVER=TRUE"