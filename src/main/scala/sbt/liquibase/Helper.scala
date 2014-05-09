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

object Helper {

  import sbt.Liquibase.Keys

  private[liquibase] val database = Def.task[Database] {
    val fc = fullClasspath.in(Runtime).value

    val defaultSchema = Keys.defaultSchemaName.value.getOrElse(null)
    val liquibaseSchema = Keys.schemaName.value.getOrElse(defaultSchema)

    val database = CommandLineUtils.createDatabaseObject(
      ClasspathUtilities.toLoader(fc.map(_.data)),
      Keys.url.value, Keys.username.value,
      Keys.password.value, Keys.driver.value,
      defaultSchema, defaultSchema, false, false,
      null, null, liquibaseSchema, liquibaseSchema
    )

    val tablePrefix = Keys.changeLogTablePrefix.value.map(_ + "_").getOrElse("")
    val tableName = tablePrefix + Keys.changeLogTableName.value.getOrElse(database.getDatabaseChangeLogTableName)
    val lockTableName = tablePrefix + Keys.changeLogLockTableName.value.getOrElse(database.getDatabaseChangeLogLockTableName)

    database.setDatabaseChangeLogTableName(tableName)
    database.setDatabaseChangeLogLockTableName(lockTableName)

    database
  }

  private[liquibase] val liquibase = Def.task[Liquibase] {
    new Liquibase(
      Keys.changelog.value,
      new FileSystemResourceAccessor(Keys.changelogDirectory.value.getPath),
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
