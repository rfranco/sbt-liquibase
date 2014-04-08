lazy val root = (project in file(".")).addPlugins(SbtLiquibase)

libraryDependencies += "com.h2database" % "h2" % "1.3.170"

LiquibaseKeys.username := ""

LiquibaseKeys.password := ""

LiquibaseKeys.driver := "org.h2.Driver"

LiquibaseKeys.url := "jdbc:h2:target/db/test;AUTO_SERVER=TRUE"