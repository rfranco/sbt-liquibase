package sbt.liquibase
package command

import _root_.liquibase.diff.output.DiffOutputControl
import _root_.liquibase.integration.commandline.CommandLineUtils
import sbt._

object GenerateCommand {

  import Helper._
  import Import._

  private val liquibaseGenerateChangeLog = taskKey[Unit]("Generate ChangeLog of the database to standard out")

  val settings: Seq[Setting[_]] = Seq(
    liquibaseGenerateChangeLog := generateChangeLog.value
  )

  private lazy val generateChangeLog = Def.task {
    val defaultSchema = LiquibaseKeys.defaultSchemaName.value.orNull
    val changelogFile = LiquibaseKeys.changelogDirectory.value / LiquibaseKeys.changelog.value
    CommandLineUtils.doGenerateChangeLog(
      changelogFile.getAbsolutePath,
      database.value,
      defaultSchema, defaultSchema,
      null, null, null, null,
      new DiffOutputControl(true, true, true)
    )
  }
}