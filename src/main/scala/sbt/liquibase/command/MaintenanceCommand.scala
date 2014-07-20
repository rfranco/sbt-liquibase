package sbt.liquibase
package command

import _root_.liquibase.CatalogAndSchema
import sbt._

object MaintenanceCommand {

  import Import._
  import Helper._

  private val liquibaseStatus = taskKey[Unit]("Outputs count (list if --verbose) of unrun change sets")
  private val liquibaseTag = inputKey[Unit]("<tag>	'Tags' the current database state for future rollback")
  private val liquibaseValidate = taskKey[Unit]("Checks the changelog for errors")
  private val liquibaseChangelogSync = taskKey[Unit]("Mark all changes as executed in the database")
  private val liquibaseChangelogSyncSQL = taskKey[Unit]("Writes SQL to mark all changes as executed in the database to STDOUT")
  private val liquibaseMarkNextChangeSetRan = taskKey[Unit]("Mark the next change set as executed in the database")
  private val liquibaseListLocks = taskKey[Unit]("Lists who currently has locks on the database changelog")
  private val liquibaseReleaseLocks = taskKey[Unit]("Releases all locks on the database changelog")
  private val liquibaseDropAll = inputKey[Unit]("Drops all database objects owned by the user. Note that functions, procedures and packages are not dropped (limitation in 1.8.1).")
  private val liquibaseClearCheckSums = taskKey[Unit]("Removes current checksums from database. On next run checksums will be recomputed")

  val settings: Seq[Setting[_]] = Seq(
    liquibaseStatus := status.value,
    liquibaseTag := tag.evaluated,
    liquibaseValidate := validate.value,
    liquibaseChangelogSync := changelogSync.value,
    liquibaseChangelogSyncSQL := changelogSyncSQL.value,
    liquibaseMarkNextChangeSetRan := markNextChangeSetRan.value,
    liquibaseListLocks := listLocks.value,
    liquibaseReleaseLocks := releaseLocks.value,
    liquibaseDropAll := dropAll.evaluated,
    liquibaseClearCheckSums := clearCheckSums.value
  )

  private lazy val status = Def.task {
    val contexts = LiquibaseKeys.contexts.value.mkString(",")
    liquibase.value.reportStatus(true, contexts, outputWriter)
  }

  private lazy val tag = Def.inputTask {
    val tag = StringArg("<tag>").parsed
    liquibase.value.tag(tag)
  }

  private lazy val validate = Def.task {
    liquibase.value.validate()
  }

  private lazy val changelogSync = Def.task {
    val contexts = LiquibaseKeys.contexts.value.mkString(",")
    liquibase.value.changeLogSync(contexts)
  }

  private lazy val changelogSyncSQL = Def.task {
    val contexts = LiquibaseKeys.contexts.value.mkString(",")
    liquibase.value.changeLogSync(contexts, outputWriter)
  }

  private lazy val markNextChangeSetRan = Def.task {
    val contexts = LiquibaseKeys.contexts.value.mkString(",")
    liquibase.value.markNextChangeSetRan(contexts, outputWriter)
  }

  private lazy val listLocks = Def.task {
    liquibase.value.reportLocks(System.out)
  }

  private lazy val releaseLocks = Def.task {
    liquibase.value.forceReleaseLocks()
  }

  private lazy val dropAll = Def.inputTask {
    val args = StringArgs("<schemas>").parsed
    val schemas = args.map(s => new CatalogAndSchema(null, s))
    liquibase.value.dropAll(schemas: _*)
  }

  private lazy val clearCheckSums = Def.task {
    liquibase.value.clearCheckSums()
  }

}
