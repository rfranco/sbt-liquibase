package sbt

import sbt.Keys._
import sbt.liquibase.command._

object SbtLiquibase extends Plugin {

  val liquibaseChangelog = settingKey[String]("The changelog file to use")
  val liquibaseChangelogDirectory = settingKey[File]("The changelog directory")
  val liquibaseUsername = settingKey[String]("Database username")
  val liquibasePassword = settingKey[String]("Database password")
  val liquibaseUrl = settingKey[String]("Database URL")
  val liquibaseDriver = settingKey[String]("Database driver class name")

  //Optional Parameters
  val liquibaseContexts = settingKey[Seq[String]]("ChangeSet contexts to execute")
  val liquibaseDefaultSchemaName = settingKey[Option[String]]("Specifies the default schema to use for managed database objects and for Liquibase control tables")
  val liquibaseSchemaName = settingKey[Option[String]]("Specifies the schema to use for Liquibase control tables")
  val liquibaseChangeLogTablePrefix = settingKey[Option[String]]("Specifies a prefix for change log tables")
  val liquibaseChangeLogTableName = settingKey[Option[String]]("Specifies the change log table name")
  val liquibaseChangeLogLockTableName = settingKey[Option[String]]("Specifies the change log lock table name")

  val liquibaseSettings: Seq[Setting[_]] = Seq(
    liquibaseChangelog := "changelog.xml",
    liquibaseChangelogDirectory := resourceDirectory.in(Compile).value / "migrations",
    liquibaseContexts := Nil,
    liquibaseDefaultSchemaName := None,
    liquibaseSchemaName := None,
    liquibaseChangeLogTablePrefix := None,
    liquibaseChangeLogTableName := None,
    liquibaseChangeLogLockTableName := None
  ) ++
    DiffCommand.settings ++
    DocumentationCommand.settings ++
    GenerateCommand.settings ++
    MaintenanceCommand.settings ++
    RollbackCommand.settings ++
    UpdateCommand.settings

}
