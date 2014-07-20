package sbt.liquibase

import _root_.liquibase.database.{Database, DatabaseFactory}
import _root_.liquibase.resource.{ClassLoaderResourceAccessor, FileSystemResourceAccessor}
import liquibase.Liquibase
import sbt.Keys._
import sbt._
import sbt.classpath.ClasspathUtilities
import sbt.complete.DefaultParsers._
import sbt.complete.Parser

private[liquibase] object Helper {

  import Import._

  lazy val database = Def.task[Database] {
    val fc = fullClasspath.in(Runtime).value
    val classLoader = ClasspathUtilities.toLoader(fc.map(_.data))
    val defaultSchema = LiquibaseKeys.defaultSchemaName.value.orNull
    val liquibaseSchema = LiquibaseKeys.schemaName.value.getOrElse(defaultSchema)

    val database = DatabaseFactory.getInstance().openDatabase(
      LiquibaseKeys.url.value,
      LiquibaseKeys.user.value.orNull,
      LiquibaseKeys.password.value.orNull,
      LiquibaseKeys.driver.value.orNull,
      null, null,
      new ClassLoaderResourceAccessor(classLoader)
    )

    database.setDefaultCatalogName(defaultSchema)
    database.setDefaultSchemaName(defaultSchema)
    database.setOutputDefaultCatalog(false)
    database.setOutputDefaultSchema(false)
    database.setLiquibaseCatalogName(liquibaseSchema)
    database.setLiquibaseSchemaName(liquibaseSchema)

    val tablePrefix = LiquibaseKeys.changeLogTablePrefix.value.map(_ + "_").getOrElse("")
    val tableName = tablePrefix + LiquibaseKeys.changeLogTableName.value.getOrElse(database.getDatabaseChangeLogTableName)
    val lockTableName = tablePrefix + LiquibaseKeys.changeLogLockTableName.value.getOrElse(database.getDatabaseChangeLogLockTableName)

    database.setDatabaseChangeLogTableName(tableName)
    database.setDatabaseChangeLogLockTableName(lockTableName)

    database
  }

  lazy val liquibase = Def.task[Liquibase] {
    new Liquibase(
      "./" + LiquibaseKeys.changelog.value,
      new FileSystemResourceAccessor(LiquibaseKeys.changelogDirectory.value.getAbsolutePath),
      database.value
    )
  }

  lazy val outputWriter = {
    new java.io.OutputStreamWriter(System.out)
  }

  def IntArg(display: String): Parser[Int] = token(Space ~> NatBasic, display)

  def StringArg(display: String): Parser[String] = token(Space ~> StringBasic, display)

  def StringArgs(display: String): Parser[Seq[String]] = spaceDelimited(display)

}
