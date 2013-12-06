package sbt

import sbt.Def._
import sbt.Keys._
import sbt.liquibase.command._

object SbtLiquibase extends Plugin {

  val liquibaseChangelog = settingKey[File]("The changelog file to use")
  val liquibaseUsername = settingKey[String]("Database username")
  val liquibasePassword = settingKey[String]("Database password")
  val liquibaseUrl = settingKey[String]("Database URL")
  val liquibaseDriver = settingKey[String]("Database driver class name")

  //Optional Parameters
  val liquibaseContexts = settingKey[Seq[String]]("ChangeSet contexts to execute")
  val liquibaseDefaultSchemaName = settingKey[Option[String]]("Specifies the default schema to use for managed database objects and for Liquibase control tables")
  val liquibaseSchemaName = settingKey[Option[String]]("Specifies the schema to use for Liquibase control tables")

  val liquibaseSettings: Seq[Setting[_]] = Seq(
    liquibaseChangelog := resourceDirectory.in(Compile).value / "migrations" / "changelog.xml",
    liquibaseContexts := Nil,
    liquibaseDefaultSchemaName := None,
    liquibaseSchemaName := None
  ) ++
    DiffCommand.settings ++
    DocumentationCommand.settings ++
    MaintenanceCommand.settings ++
    RollbackCommand.settings ++
    UpdateCommand.settings

}
