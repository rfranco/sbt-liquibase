package sbt.liquibase.command

import sbt._

object DiffCommand {

  import sbt.Liquibase.Keys
  import sbt.liquibase.Helper._

  private val liquibaseDiff = taskKey[Unit]("Writes description of differences to standard out")
  private val liquibaseChangeLog = taskKey[Unit]("Writes Change Log XML to update the base database to the target database to standard out")

  val settings: Seq[Setting[_]] = Seq(
    liquibaseDiff := diff.value,
    liquibaseChangeLog := changelog.value
  )

  private lazy val diff = Def.task {
    ???
  }

  private lazy val changelog = Def.task {
    ???
  }
}
