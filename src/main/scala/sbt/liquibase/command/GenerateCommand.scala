package sbt.liquibase.command

import _root_.liquibase.diff.output.DiffOutputControl
import _root_.liquibase.integration.commandline.CommandLineUtils
import sbt._

object GenerateCommand {

  import sbt.Liquibase.Keys
  import sbt.liquibase.Helper._

  private val liquibaseGenerateChangeLog = taskKey[Unit]("Generate ChangeLog of the database to standard out")

  val settings: Seq[Setting[_]] = Seq(
    liquibaseGenerateChangeLog := generateChangeLog.value
  )

  private lazy val generateChangeLog = Def.task {
    val defaultSchema = Keys.defaultSchemaName.value.getOrElse(null)
    val changelogFile = Keys.changelogDirectory.value / Keys.changelog.value
    CommandLineUtils.doGenerateChangeLog(
      changelogFile.getAbsolutePath,
      database.value,
      defaultSchema, defaultSchema,
      null, null, null, null,
      new DiffOutputControl(true, true, true)
    )
  }
}