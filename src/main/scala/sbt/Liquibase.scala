package sbt

import sbt.Keys._
import sbt.liquibase.command._

object Liquibase extends AutoPlugin {

  object Keys {
    val changelog = settingKey[String]("The changelog file to use")
    val changelogDirectory = settingKey[File]("The changelog directory")
    val username = settingKey[String]("Database username")
    val password = settingKey[String]("Database password")
    val url = settingKey[String]("Database URL")
    val driver = settingKey[String]("Database driver class name")

    //Optional Parameters
    val contexts = settingKey[Seq[String]]("ChangeSet contexts to execute")
    val defaultSchemaName = settingKey[Option[String]]("Specifies the default schema to use for managed database objects and for Liquibase control tables")
    val schemaName = settingKey[Option[String]]("Specifies the schema to use for Liquibase control tables")
    val changeLogTablePrefix = settingKey[Option[String]]("Specifies a prefix for change log tables")
    val changeLogTableName = settingKey[Option[String]]("Specifies the change log table name")
    val changeLogLockTableName = settingKey[Option[String]]("Specifies the change log lock table name")
  }

  object autoImport {
    val LiquibaseKeys = Keys
  }

  override lazy val projectSettings = Seq(
    Keys.changelog := "changelog.xml",
    Keys.changelogDirectory := resourceDirectory.in(Compile).value / "db",
    Keys.contexts := Nil,
    Keys.defaultSchemaName := None,
    Keys.schemaName := None,
    Keys.changeLogTablePrefix := None,
    Keys.changeLogTableName := None,
    Keys.changeLogLockTableName := None
  ) ++
    DiffCommand.settings ++
    DocumentationCommand.settings ++
    GenerateCommand.settings ++
    MaintenanceCommand.settings ++
    PackageCommand.settings ++
    RollbackCommand.settings ++
    UpdateCommand.settings

}