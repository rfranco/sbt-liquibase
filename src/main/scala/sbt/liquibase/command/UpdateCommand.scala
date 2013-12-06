package sbt.liquibase.command

import sbt.Def.{macroValueI, macroValueIT, macroValueIInT}
import sbt._

object UpdateCommand {

  import SbtLiquibase._
  import liquibase.LiquibaseHelper._

  private val liquibaseUpdate = taskKey[Unit]("Updates database to current version")
  private val liquibaseUpdateCount = inputKey[Unit]("Applies the next <value> change sets")
  private val liquibaseUpdateSQL = taskKey[Unit]("Writes SQL to update database to current version to STDOUT")
  private val liquibaseUpdateCountSQL = taskKey[Unit]("Writes SQL to apply the next <value> change sets to STDOUT")

  val settings: Seq[Setting[_]] = Seq(
    liquibaseUpdate := update.value,
    liquibaseUpdateCount := updateCount.evaluated,
    liquibaseUpdateSQL := updateSql.value,
    liquibaseUpdateCountSQL := updateCountSql.value
  )

  private lazy val update = Def.task {
    val contexts = liquibaseContexts.value.mkString(",")
    liquibase.value.update(contexts)
  }

  private lazy val updateCount = Def.inputTask {
    val count = IntArg("<count>").parsed
    val contexts = liquibaseContexts.value.mkString(",")
    liquibase.value.update(count, contexts)
  }

  private lazy val updateSql = Def.task {

  }

  private lazy val updateCountSql = Def.task {

  }

}
