package sbt.liquibase.command

import _root_.liquibase.util.ISODateFormat
import sbt._

object RollbackCommand {

  import SbtLiquibase.autoImport._
  import liquibase.LiquibaseHelper._

  private val liquibaseRollback = inputKey[Unit]("Rolls back the database to the state it was in when the <tag> was applied")
  private val liquibaseRollbackToDate = inputKey[Unit]("Rolls back the database to the state it was in at the given date/time")
  private val liquibaseRollbackCount = inputKey[Unit]("Rolls back the last <value> change sets")
  private val liquibaseRollbackSQL = inputKey[Unit]("Writes SQL to roll back the database to the state it was in when the tag was applied to STDOUT")
  private val liquibaseRollbackToDateSQL = inputKey[Unit]("Writes SQL to roll back the database to the state it was in at the given date/time version to STDOUT")
  private val liquibaseRollbackCountSQL = inputKey[Unit]("Writes SQL to roll back the last <value> change sets to STDOUT")
  private val liquibaseFutureRollbackSQL = taskKey[Unit]("Writes SQL to roll back the database to the current state after the changes in the changeslog have been applied")

  val settings: Seq[Setting[_]] = Seq(
    liquibaseRollback := rollback.evaluated,
    liquibaseRollbackToDate := rollbackDate.evaluated,
    liquibaseRollbackCount := rollbackCount.evaluated,
    liquibaseRollbackSQL := rollbackSql.evaluated,
    liquibaseRollbackToDateSQL := rollbackDateSql.evaluated,
    liquibaseRollbackCountSQL := rollbackCountSql.evaluated
  )

  private lazy val rollback = Def.inputTask {
    val tag = StringArg("<tag>").parsed
    val contexts = LiquibaseKeys.contexts.value.mkString(",")
    liquibase.value.rollback(tag, contexts)
  }

  private lazy val rollbackDate = Def.inputTask {
    val date = StringArg("<yyyy-MM-dd'T'HH:mm:ss>").parsed
    val contexts = LiquibaseKeys.contexts.value.mkString(",")
    liquibase.value.rollback(new ISODateFormat().parse(date), contexts)
  }

  private lazy val rollbackCount = Def.inputTask {
    val count = IntArg("<count>").parsed
    val contexts = LiquibaseKeys.contexts.value.mkString(",")
    liquibase.value.rollback(count, contexts)
  }

  private lazy val rollbackSql = Def.inputTask {
    val tag = StringArg("<tag>").parsed
    val contexts = LiquibaseKeys.contexts.value.mkString(",")
    liquibase.value.rollback(tag, contexts, outputWriter)
  }

  private lazy val rollbackDateSql = Def.inputTask {
    val date = StringArg("<yyyy-MM-dd'T'HH:mm:ss>").parsed
    val contexts = LiquibaseKeys.contexts.value.mkString(",")
    liquibase.value.rollback(new ISODateFormat().parse(date), contexts, outputWriter)
  }

  private lazy val rollbackCountSql = Def.inputTask {
    val count = IntArg("<count>").parsed
    val contexts = LiquibaseKeys.contexts.value.mkString(",")
    liquibase.value.rollback(count, contexts, outputWriter)
  }
}
