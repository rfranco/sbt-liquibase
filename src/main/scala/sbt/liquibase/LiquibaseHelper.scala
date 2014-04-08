package sbt.liquibase

import liquibase.database.Database
import liquibase.Liquibase
import liquibase.integration.commandline.CommandLineUtils
import liquibase.resource.FileSystemResourceAccessor
import sbt._
import sbt.Keys._
import sbt.classpath.ClasspathUtilities
import sbt.complete.DefaultParsers._
import sbt.complete.Parser

object LiquibaseHelper {

  import SbtLiquibase.autoImport.LiquibaseKeys._

  private[liquibase] val database = Def.task[Database] {
    val fc = fullClasspath.in(Runtime).value

    val defaultSchema = defaultSchemaName.value.getOrElse(null)
    val liquibaseSchema = schemaName.value.getOrElse(defaultSchema)

    val database = CommandLineUtils.createDatabaseObject(
      ClasspathUtilities.toLoader(fc.map(_.data)),
      url.value, username.value, password.value, driver.value,
      defaultSchema, defaultSchema, false, false,
      null, null, liquibaseSchema, liquibaseSchema
    )

    val tablePrefix = changeLogTablePrefix.value.map(_ + "_").getOrElse("")
    val tableName = tablePrefix + changeLogTableName.value.getOrElse(database.getDatabaseChangeLogTableName)
    val lockTableName = tablePrefix + changeLogLockTableName.value.getOrElse(database.getDatabaseChangeLogLockTableName)

    database.setDatabaseChangeLogTableName(tableName)
    database.setDatabaseChangeLogLockTableName(lockTableName)

    database
  }

  private[liquibase] val liquibase = Def.task[Liquibase] {
    new Liquibase(
      changelog.value,
      new FileSystemResourceAccessor(changelogDirectory.value.getPath),
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
