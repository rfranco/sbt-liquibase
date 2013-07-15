package sbt.liquibase

import liquibase.database.Database
import liquibase.integration.commandline.CommandLineUtils
import liquibase.resource.FileSystemResourceAccessor
import liquibase.{Liquibase => JLiquibase}
import sbt.Def.{macroValueI, macroValueIT}
import sbt.Keys._
import sbt._
import sbt.classpath.ClasspathUtilities

object LiquibaseRef {

  import Liquibase._

  private val database = Def.task[Database] {
    val fc = (fullClasspath in(ThisProject, Runtime)).value
    CommandLineUtils.createDatabaseObject(
      ClasspathUtilities.toLoader(fc.map(_.data)),
      liquibaseUrl.value, liquibaseUsername.value, liquibasePassword.value, liquibaseDriver.value,
      null, liquibaseDefaultSchemaName.value.getOrElse(null), null, null
    )
  }

  private[liquibase] val liquibase = Def.task[JLiquibase] {
    val changelog = liquibaseChangelog.value.absolutePath
    new JLiquibase(changelog, new FileSystemResourceAccessor, database.value)
  }

}
