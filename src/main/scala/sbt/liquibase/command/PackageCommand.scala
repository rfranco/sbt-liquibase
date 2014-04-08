package sbt.liquibase.command

import sbt._
import sbt.Keys._
import java.io.File

object PackageCommand {

  import SbtLiquibase.autoImport.LiquibaseKeys._

  private val liquibasePackage = taskKey[File]("Package migrations file")

  val settings: Seq[Setting[_]] = Seq(
    liquibasePackage := packageSQL.value
  )

  private lazy val packageSQL = Def.task {
    val packageName = s"${normalizedName.value}-${version.value}-sql"
    val zip = target.value / (packageName + ".zip")
    streams.value.log.info("Packaging " + zip.getAbsolutePath)
    IO.zip(Path.allSubpaths(changelogDirectory.value), zip)
    streams.value.log.info("Done packaging.")
    zip
  }

}
