package sbt.liquibase
package command

import sbt._
import sbt.Keys._
import java.io.File

object PackageCommand {

  import Import._
  import Helper._

  private val liquibasePackage = taskKey[File]("Package migrations file")

  val settings: Seq[Setting[_]] = Seq(
    liquibasePackage := packageSQL.value
  )

  private lazy val packageSQL = Def.task {
    val packageName = s"${normalizedName.value}-${version.value}-sql"
    val zip = target.value / (packageName + ".zip")
    val changelogDirectory = LiquibaseKeys.changelogDirectory.value
    streams.value.log.info("Packaging " + zip.getAbsolutePath)
    IO.zip(Path.allSubpaths(changelogDirectory), zip)
    streams.value.log.info("Done packaging.")
    zip
  }

}
