package sbt

import sbt.Def._
import sbt.Keys._
import sbt.liquibase.command._

object Liquibase extends Plugin {

  val liquibaseChangelog = settingKey[File]("The changelog file to use")
  val liquibaseUsername = settingKey[String]("Database username")
  val liquibasePassword = settingKey[String]("Database password")
  val liquibaseUrl = settingKey[String]("Database URL")
  val liquibaseDriver = settingKey[String]("Database driver class name")

  //Optional Parameters
  val liquibaseContexts = settingKey[Seq[String]]("ChangeSet contexts to execute")
  val liquibaseDefaultSchemaName = settingKey[Option[String]]("Specifies the default schema to use for managed database objects and for Liquibase control tables")
  val liquibaseLogLevel = settingKey[String]("Execution log level (debug, info, warning, severe, off)")

  val liquibaseSettings: Seq[Setting[_]] = Seq(
    liquibaseChangelog := (resourceDirectory in(ThisProject, Compile)).value / "migrations" / "changelog.xml",
    liquibaseContexts := Nil,
    liquibaseDefaultSchemaName := None,
    liquibaseLogLevel := "warning"
  ) ++
    DiffCommand.settings ++
    DocumentationCommand.settings ++
    MaintenanceCommand.settings ++
    RollbackCommand.settings ++
    UpdateCommand.settings

}
