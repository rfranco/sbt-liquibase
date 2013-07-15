package sbt.liquibase.command

import sbt.Def.{macroValueI, macroValueIInT, macroValueIT, macroValueIn, macroValueT}
import sbt._
import sbt.Keys._
import sbt.liquibase.LiquibaseRef
import java.io.PrintStream

object MaintenanceCommand {

  import Liquibase._
  import LiquibaseRef._

  private val liquibaseStatus = taskKey[Unit]("Outputs count (list if --verbose) of unrun change sets")
  private val liquibaseTag = inputKey[Unit]("<tag>	'Tags' the current database state for future rollback")
  private val liquibaseValidate = taskKey[Unit]("Checks the changelog for errors")
  private val liquibaseChangelogSync = taskKey[Unit]("Mark all changes as executed in the database")
  private val liquibaseChangelogSyncSQL = taskKey[Unit]("Writes SQL to mark all changes as executed in the database to STDOUT")
  private val liquibaseMarkNextChangeSetRan = taskKey[Unit]("Mark the next change set as executed in the database")
  private val liquibaseListLocks = taskKey[Unit]("Lists who currently has locks on the database changelog")
  private val liquibaseReleaseLocks = taskKey[Unit]("Releases all locks on the database changelog")
  private val liquibaseDropAll = taskKey[Unit]("Drops all database objects owned by the user. Note that functions, procedures and packages are not dropped (limitation in 1.8.1).")
  private val liquibaseClearCheckSums = taskKey[Unit]("Removes current checksums from database. On next run checksums will be recomputed")

  val settings: Seq[Setting[_]] = Seq(

    liquibaseStatus := {
      val out = streams.value
      val contexts = liquibaseContexts.value.mkString(",")
      liquibase.value.reportStatus(true, contexts, out.text())
    },

    liquibaseTag := {
      val tag = "" //TODO Change to InputTask to receive tag
      liquibase.value.tag(tag)
    },

    liquibaseValidate := {
      liquibase.value.validate()
    },

    liquibaseChangelogSync := {
      val out = streams.value
      val contexts = liquibaseContexts.value.mkString(",")
      liquibase.value.changeLogSync(contexts, out.text())
    },

    liquibaseMarkNextChangeSetRan := {
      val out = streams.value
      val contexts = liquibaseContexts.value.mkString(",")
      liquibase.value.markNextChangeSetRan(contexts, out.text())
    },

    liquibaseListLocks := {
      val out = streams.value
      liquibase.value.reportLocks(new PrintStream(out.binary()))
    },

    liquibaseReleaseLocks := {
      liquibase.value.forceReleaseLocks()
    },

    liquibaseDropAll := {
      liquibase.value.dropAll() //TODO Change to InputTask to receive schemas
    },

    liquibaseClearCheckSums := {
      liquibase.value.clearCheckSums()
    }

  )

}
