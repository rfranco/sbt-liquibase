package sbt.liquibase

import liquibase.database.Database
import liquibase.integration.commandline.CommandLineUtils
import liquibase.resource.FileSystemResourceAccessor
import liquibase.{Liquibase => JLiquibase}
import sbt.Def.{macroValueI, macroValueIT}
import sbt.Keys._
import sbt._
import sbt.classpath.ClasspathUtilities
import sbt.complete.DefaultParsers._
import sbt.complete.Parser.token

object LiquibaseHelper {

  import Liquibase._

  private lazy val database = Def.task[Database] {
    val fc = (fullClasspath in(ThisProject, Runtime)).value
    CommandLineUtils.createDatabaseObject(
      ClasspathUtilities.toLoader(fc.map(_.data)),
      liquibaseUrl.value, liquibaseUsername.value, liquibasePassword.value, liquibaseDriver.value,
      null, liquibaseDefaultSchemaName.value.getOrElse(null), null, null
    )
  }

  lazy val liquibase = Def.task[JLiquibase] {
    val changelog = liquibaseChangelog.value.absolutePath
    new JLiquibase(changelog, new FileSystemResourceAccessor, database.value)
  }

  def IntArg(label: String) = (Space ~> token(IntBasic, label))

  def StringArg(label: String) = (Space ~> token(StringBasic, label))

  def StringArgs(label:String) = spaceDelimited(label)

}
