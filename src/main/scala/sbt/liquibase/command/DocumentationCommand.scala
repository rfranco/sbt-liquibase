package sbt.liquibase.command

import sbt.Def.{macroValueIT, macroValueI}
import sbt.Keys._
import sbt._

object DocumentationCommand {

  import liquibase.LiquibaseHelper._

  private val liquibaseDbDoc = taskKey[Unit]("Generates Javadoc-like documentation based on current database and change log")

  val settings: Seq[Setting[_]] = Seq(
    liquibaseDbDoc := dbDoc.value
  )

  private lazy val dbDoc = Def.task {
    val output = target.value / "liquibase" / "docs"
    liquibase.value.generateDocumentation(output.absolutePath)
  }

}
