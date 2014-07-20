package sbt.liquibase
package command

import sbt._
import sbt.Keys._

object DocumentationCommand {

  import Import._
  import Helper._

  private val liquibaseDbDoc = taskKey[Unit]("Generates Javadoc-like documentation based on current database and change log")

  val settings: Seq[Setting[_]] = Seq(
    liquibaseDbDoc := dbDoc.value
  )

  private lazy val dbDoc = Def.task {
    val output = target.value / "migrations" / "docs"
    liquibase.value.generateDocumentation(output.absolutePath)
  }

}
