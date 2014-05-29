package sbt

import sbt.Keys._
import sbt.liquibase.command._

object Liquibase extends AutoPlugin {self =>

  object LiquibaseKeys {
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

  val LiquibaseSettings = Seq(
    LiquibaseKeys.changelog := "changelog.xml",
    LiquibaseKeys.changelogDirectory := resourceDirectory.in(Compile).value / "db",
    LiquibaseKeys.contexts := Nil,
    LiquibaseKeys.defaultSchemaName := None,
    LiquibaseKeys.schemaName := None,
    LiquibaseKeys.changeLogTablePrefix := None,
    LiquibaseKeys.changeLogTableName := None,
    LiquibaseKeys.changeLogLockTableName := None
  ) ++
    DiffCommand.settings ++
    DocumentationCommand.settings ++
    GenerateCommand.settings ++
    MaintenanceCommand.settings ++
    PackageCommand.settings ++
    RollbackCommand.settings ++
    UpdateCommand.settings


  override val projectSettings = LiquibaseSettings

  object autoImport {
    val LiquibaseKeys = self.LiquibaseKeys
    val LiquibaseSettings = self.LiquibaseSettings
  }

}