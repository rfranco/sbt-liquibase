package sbt.liquibase

import liquibase.Liquibase
import liquibase.database.Database
import liquibase.integration.commandline.CommandLineUtils
import liquibase.resource.FileSystemResourceAccessor
import sbt.Keys._
import sbt._
import sbt.classpath.ClasspathUtilities
import sbt.complete.DefaultParsers._
import sbt.complete.Parser

object Helper {

  import sbt.Liquibase.LiquibaseKeys

  private[liquibase] val database = Def.task[Database] {
    val fc = fullClasspath.in(Runtime).value

    val defaultSchema = LiquibaseKeys.defaultSchemaName.value.getOrElse(null)
    val liquibaseSchema = LiquibaseKeys.schemaName.value.getOrElse(defaultSchema)

    val database = CommandLineUtils.createDatabaseObject(
      ClasspathUtilities.toLoader(fc.map(_.data)),
      LiquibaseKeys.url.value, LiquibaseKeys.username.value,
      LiquibaseKeys.password.value, LiquibaseKeys.driver.value,
      defaultSchema, defaultSchema, false, false,
      null, null, liquibaseSchema, liquibaseSchema
    )

    val tablePrefix = LiquibaseKeys.changeLogTablePrefix.value.map(_ + "_").getOrElse("")
    val tableName = tablePrefix + LiquibaseKeys.changeLogTableName.value.getOrElse(database.getDatabaseChangeLogTableName)
    val lockTableName = tablePrefix + LiquibaseKeys.changeLogLockTableName.value.getOrElse(database.getDatabaseChangeLogLockTableName)

    database.setDatabaseChangeLogTableName(tableName)
    database.setDatabaseChangeLogLockTableName(lockTableName)

    database
  }

  private[liquibase] val liquibase = Def.task[Liquibase] {
    new Liquibase(
      LiquibaseKeys.changelog.value,
      new FileSystemResourceAccessor(LiquibaseKeys.changelogDirectory.value.getPath),
      database.value
    )
  }

  private[liquibase] lazy val outputWriter = {
    new java.io.OutputStreamWriter(System.out)
  }

  def IntArg(display: String): Parser[Int] = token(Space ~> NatBasic, display)

  def StringArg(display: String): Parser[String] = token(Space ~> StringBasic, display)

  def StringArgs(display: String): Parser[Seq[String]] = spaceDelimited(display)

}
