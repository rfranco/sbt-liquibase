lazy val root = (project in file(".")).enablePlugins(Liquibase)

libraryDependencies += "com.h2database" % "h2" % "1.3.170"

Liquibase.Keys.driver := "org.h2.Driver"

Liquibase.Keys.url := "jdbc:h2:target/db/test;AUTO_SERVER=TRUE"

Liquibase.Keys.username := ""

Liquibase.Keys.password := ""