package sbt.liquibase.command

import sbt.Def.{macroValueI, macroValueIT, macroValueIInT}
import sbt.Keys._
import sbt._
import sbt.liquibase.LiquibaseHelper

object UpdateCommand {

  import Liquibase._
  import LiquibaseHelper._

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
    val out = streams.value
    val contexts = liquibaseContexts.value.mkString(",")
    liquibase.value.update(contexts, out.text())
  }

  private lazy val updateCount = Def.inputTask {
    val count = IntArg("<count>").parsed
    val contexts = liquibaseContexts.value.mkString(",")
    liquibase.value.update(count, contexts, streams.value.text())
  }

  private lazy val updateSql = Def.task {

  }

  private lazy val updateCountSql = Def.task {

  }

}
