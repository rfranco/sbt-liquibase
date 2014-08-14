package sbt.liquibase

import sbt.Keys._
import sbt._
import sbt.liquibase.command._

object Import {

  object LiquibaseKeys {

    val url = settingKey[String]("Database URL")
    val user = settingKey[Option[String]]("Database username")
    val password = settingKey[Option[String]]("Database password")
    val driver = settingKey[Option[String]]("Database driver class name")

    val changelog = settingKey[String]("The changelog file to use")
    val changelogDirectory = settingKey[File]("The changelog directory")
    val contexts = settingKey[Seq[String]]("ChangeSet contexts to execute")

    val schemaName = settingKey[Option[String]]("Specifies the schema to use for Liquibase control tables")
    val defaultSchemaName = settingKey[Option[String]]("Specifies the default schema to use for managed database objects and for Liquibase control tables")

    val changeLogTablePrefix = settingKey[Option[String]]("Specifies a prefix for change log tables")
    val changeLogTableName = settingKey[Option[String]]("Specifies the change log table name")
    val changeLogLockTableName = settingKey[Option[String]]("Specifies the change log lock table name")
  }

}

object SbtLiquibase extends AutoPlugin {

  val autoImport = Import

  import Import._

  override val projectSettings = Seq(
    LiquibaseKeys.changelog := "changelog.xml",
    LiquibaseKeys.changelogDirectory := resourceDirectory.in(Compile).value / "db",
    LiquibaseKeys.user := None,
    LiquibaseKeys.password := None,
    LiquibaseKeys.driver := None,
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

}