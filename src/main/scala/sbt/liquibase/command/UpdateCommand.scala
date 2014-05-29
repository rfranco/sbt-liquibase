package sbt.liquibase.command

import sbt._

object UpdateCommand {

  import sbt.Liquibase.LiquibaseKeys
  import sbt.liquibase.Helper._

  private val liquibaseUpdate = taskKey[Unit]("Updates database to current version")
  private val liquibaseUpdateCount = inputKey[Unit]("Applies the next <value> change sets")
  private val liquibaseUpdateSQL = taskKey[Unit]("Writes SQL to update database to current version to STDOUT")
  private val liquibaseUpdateCountSQL = inputKey[Unit]("Writes SQL to apply the next <value> change sets to STDOUT")
  private val liquibaseUpdateTestingRollback = taskKey[Unit]("Updates database, then rolls back changes before updating again. Useful for testing rollback support")

  val settings: Seq[Setting[_]] = Seq(
    liquibaseUpdate := update.value,
    liquibaseUpdateCount := updateCount.evaluated,
    liquibaseUpdateSQL := updateSql.value,
    liquibaseUpdateCountSQL := updateCountSql.evaluated,
    liquibaseUpdateTestingRollback := updateTestingRollback.value
  )

  private lazy val update = Def.task {
    val contexts = LiquibaseKeys.contexts.value.mkString(",")
    liquibase.value.update(contexts)
  }

  private lazy val updateCount = Def.inputTask {
    val count = IntArg("<count>").parsed
    val contexts = LiquibaseKeys.contexts.value.mkString(",")
    liquibase.value.update(count, contexts)
  }

  private lazy val updateSql = Def.task {
    val contexts = LiquibaseKeys.contexts.value.mkString(",")
    liquibase.value.update(contexts, outputWriter)
  }

  private lazy val updateCountSql = Def.inputTask {
    val count = IntArg("<count>").parsed
    val contexts = LiquibaseKeys.contexts.value.mkString(",")
    liquibase.value.update(count, contexts, outputWriter)
  }

  private lazy val updateTestingRollback = Def.task {
    val contexts = LiquibaseKeys.contexts.value.mkString(",")
    liquibase.value.updateTestingRollback(contexts)
  }
}
