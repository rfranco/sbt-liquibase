package sbt.liquibase

import liquibase.database.Database
import liquibase.Liquibase
import liquibase.integration.commandline.CommandLineUtils
import liquibase.resource.FileSystemResourceAccessor
import sbt.Def.{macroValueI, macroValueIT}
import sbt.Keys._
import sbt._
import sbt.classpath.ClasspathUtilities
import sbt.complete.DefaultParsers._
import sbt.complete.Parser

object LiquibaseHelper {

  import SbtLiquibase._

  private def database = Def.task[Database] {
    val fc = fullClasspath.in(Runtime).value


    val url = liquibaseUrl.value
    val username = liquibaseUsername.value
    val password = liquibasePassword.value
    val driver = liquibaseDriver.value
    val defaultSchema = liquibaseDefaultSchemaName.value.getOrElse(null)
    val liquibaseSchema = liquibaseSchemaName.value.getOrElse(defaultSchema)

    val database = CommandLineUtils.createDatabaseObject(
      ClasspathUtilities.toLoader(fc.map(_.data)),
      url, username, password, driver,
      defaultSchema, defaultSchema, false, false,
      null, null, liquibaseSchema, liquibaseSchema
    )

    val changeLogTablePrefix = liquibaseChangeLogTablePrefix.value.map(_ + "_").getOrElse("")
    val changeLogTableName = changeLogTablePrefix + liquibaseChangeLogTableName.value.getOrElse(database.getDatabaseChangeLogTableName)
    val changeLogLockTableName = changeLogTablePrefix + liquibaseChangeLogLockTableName.value.getOrElse(database.getDatabaseChangeLogLockTableName)

    database.setDatabaseChangeLogTableName(changeLogTableName)
    database.setDatabaseChangeLogLockTableName(changeLogLockTableName)

    database
  }

  def liquibase = Def.task[Liquibase] {
    val changelog = liquibaseChangelog.value.absolutePath
    val db = database.value
    new Liquibase(changelog, new FileSystemResourceAccessor(), db)
  }

  def IntArg(display: String): Parser[Int] = Space ~> token(NatBasic, display)
  def StringArg(display: String): Parser[String] = Space ~> token(StringBasic, display)
  def StringArgs(display: String): Parser[Seq[String]] = spaceDelimited(display)

}
