package sbt.liquibase.command

import sbt.Def.{macroValueI, macroValueIT, macroValueIInT}
import sbt._

object RollbackCommand {

  import SbtLiquibase._
  import liquibase.LiquibaseHelper._

  private val liquibaseRollback = inputKey[Unit]("Rolls back the database to the state it was in when the <tag> was applied")
  private val liquibaseRollbackToDate = taskKey[Unit]("Rolls back the database to the state it was in at the given date/time")
  private val liquibaseRollbackCount = inputKey[Unit]("Rolls back the last <value> change sets")
  private val liquibaseRollbackSQL = taskKey[Unit]("Writes SQL to roll back the database to the state it was in when the tag was applied to STDOUT")
  private val liquibaseRollbackToDateSQL = taskKey[Unit]("Writes SQL to roll back the database to the state it was in at the given date/time version to STDOUT")
  private val liquibaseRollbackCountSQL = taskKey[Unit]("Writes SQL to roll back the last <value> change sets to STDOUT")
  private val liquibaseFutureRollbackSQL = taskKey[Unit]("Writes SQL to roll back the database to the current state after the changes in the changeslog have been applied")
  private val liquibaseUpdateTestingRollback = taskKey[Unit]("Updates the database, then rolls back changes before updating againWrites SQL to roll back the database to the current state after the changes in the changeslog have been applied")
  private val liquibaseGenerateChangeLog = taskKey[Unit]("Generate ChangeLog of the database to standard out")

  val settings: Seq[Setting[_]] = Seq(
    liquibaseRollback := rollback.evaluated,
    liquibaseRollbackCount := rollbackCount.evaluated
  )

  private lazy val rollback = Def.inputTask {
    val tag = StringArg("<tag>").parsed
    val contexts = liquibaseContexts.value.mkString(",")
    liquibase.value.rollback(tag, contexts)
  }

  private lazy val rollbackCount = Def.inputTask {
    val count = IntArg("<count>").parsed
    val contexts = liquibaseContexts.value.mkString(",")
    liquibase.value.rollback(count, contexts)
  }
}
