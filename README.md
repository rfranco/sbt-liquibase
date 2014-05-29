Liquibase plugin for sbt 0.13+
====================================
[![Build Status](https://travis-ci.org/rfranco/sbt-liquibase.png)](https://travis-ci.org/rfranco/sbt-liquibase)

# Instructions for use:
### Step 1: Include the plugin in your build

Add the following to your `project/plugins.sbt`:

## sbt-0.11+ and sbt-0.12+

[Liquibase plugin for sbt 0.11+ and 0.12+](https://github.com/bigtoast/sbt-liquibase)

## sbt-0.13.5

```scala
resolvers += "" at ""

addSbtPlugin("sbt" % "sbt-liquibase" % "0.8.0")
```

### Step 2: Add sbt-liquibase settings to your build

Add the following to your 'build.sbt' ( if you are using build.sbt )

```scala
LiquibaseSettings

LiquibaseKeys.driver   := "com.mysql.jdbc.Driver"

LiquibaseKeys.url      := "jdbc:mysql://localhost:3306/testdb?createDatabaseIfNotExist=true"

LiquibaseKeys.username := "name"

LiquibaseKeys.password := "secret"
```

Or if you are using a build object extending from Build:

```scala
import sbt._
import sbt.Liquibase._

class MyBuild extends Build {
    val myProject = Project("myProject", file("."))
        .settings(LiquibaseSettings: _*)
        .settings(
            LiquibaseKeys.username := "name",
            LiquibaseKeys.password := "secret",
            LiquibaseKeys.driver   := "com.mysql.jdbc.Driver",
            LiquibaseKeys.url      := "jdbc:mysql://localhost:3306/testdb?createDatabaseIfNotExist=true"
        )
}
```

## Settings

<table>
    <tr>
        <td> <b>LiquibaseKeys.username</b> </td>
        <td>Username for the database. This defaults to blank.</td>
    </tr>
    <tr>
        <td></td>
        <td>LiquibaseKeys.username := "asdf"</td>
    </tr>
    <tr>
        <td> <b>LiquibaseKeys.password</b> </td>
        <td>Password for the database. This defaults to blank.</td>
    </tr>
    <tr>
        <td></td>
        <td>LiquibaseKeys.password := "secretstuff"</td>
    </tr>
    <tr>
        <td> <b>LiquibaseKeys.driver</b> </td>
        <td>Database driver classname. There is no default.</td>
    </tr>
    <tr>
        <td></td>
        <td>LiquibaseKeys.driver := "com.mysql.jdbc.Driver"</td>
    </tr>
    <tr>
        <td> <b>LiquibaseKeys.url</b> </td>
        <td>Database connection uri. There is no default.</td>
    </tr>
    <tr>
        <td></td>
        <td>LiquibaseKeys.url := "jdbc:mysql://localhost:3306/mydb"</td>
    </tr>
    <tr>
        <td> <b>liquibaseDefaultSchemaName</b> </td>
        <td>Default schema name for the db if it isn't defined in the uri. This defaults to null.</td>
    </tr>
    <tr>
        <td></td>
        <td>liquibaseDefaultSchemaName := "dbname"</td>
    </tr>
    <tr>
        <td> <b>liquibaseChangelog</b> </td>
        <td>Full path to your changelog file. This defaults 'src/main/resources/migrations/changelog.xml'.</td>
    </tr>
    <tr>
        <td></td>
        <td>liquibaseChangelog := "other/path/dbchanges.xml"</td>
    </tr>
</table>

## Database Update Commands
--TODO

## Database Rollback Commands
--TODO

## Diff Commands
--TODO

## Documentation Commands
--TODO

## Maintenance Commands
--TODO

## Tasks

<table>
    <tr>
        <td> <b>liquibaseUpdate</b> </td>
        <td>Run the liquibase migration</td>
    </tr>
    <tr>
        <td><b>liquibaseStatus</b></td>
        <td>Print count of yet to be run changesets</td>
    </tr>
    <tr>
        <td><b>liquibaseClearChecksums</b></td>
        <td>Removes all saved checksums from database log. Useful for 'MD5Sum Check Failed' errors</td>
    </tr>
    <tr>
        <td><b>liquibaseListLocks</b></td>
        <td>Lists who currently has locks on the database changelog</td>
    </tr>
    <tr>
        <td><b>liquibaseReleaseLocks</b></td>
        <td>Releases all locks on the database changelog.</td>
    </tr>
    <tr>
        <td><b>liquibaseValidateChangelog</b></td>
        <td>Checks changelog for errors.</td>
    </tr>
    <tr>
        <td><b>liquibaseDbDiff</b></td>
        <td>( this isn't implemented yet ) Generate changeSet(s) to make Test DB match Development</td>
    </tr>
    <tr>
        <td><b>liquibaseDbDoc</b></td>
        <td>Generates Javadoc-like documentation based on current database and change log</td>
    </tr>
    <tr>
        <td><b>liquibaseGenerateChangelog</b></td>
        <td>Writes Change Log XML to copy the current state of the database to the file defined in the changelog setting</td>
    </tr>
    <tr>
        <td><b>liquibaseChangelogSyncSql</b></td>
        <td>Writes SQL to mark all changes as executed in the database to STDOUT</td>
    </tr>

    <tr>
        <td><b>liquibaseTag</b> {tag}</td>
        <td>Tags the current database state for future rollback with {tag}</td>
    </tr>
    <tr>
        <td><b>liquibaseRollback</b> {tag}</td>
        <td>Rolls back the database to the the state is was when the {tag} was applied.</td>
    </tr>
    <tr>
        <td><b>liquibaseRollbackSql</b> {tag}</td>
        <td>Writes SQL to roll back the database to that state it was in when the {tag} was applied to STDOUT</td>
    </tr>
    <tr>
        <td><b>liquibaseRollbackCount</b> {int}</td>
        <td>Rolls back the last {int i} change sets applied to the database</td>
    </tr>
    <tr>
        <td><b>liquibaseRollbackSqlCount</b> {int}</td>
        <td>Writes SQL to roll back the last {int i} change sets to STDOUT applied to the database</td>
    </tr>
    <tr>
        <td><b>liquibaseRollbackToDate</b> { yyyy-MM-dd HH:mm:ss }</td>
        <td>Rolls back the database to the the state it was at the given date/time. Date Format: yyyy-MM-dd HH:mm:ss</td>
    </tr>
    <tr>
        <td><b>liquibaseRollbackToDateSql { yyyy-MM-dd HH:mm:ss }</b></td>
        <td>Writes SQL to roll back the database to that state it was in at the given date/time version to STDOUT</td>
    </tr>
    <tr>
        <td><b>liquibaseFutureRollbackSql</b></td>
        <td>Writes SQL to roll back the database to the current state after the changes in the changelog have been applied.</td>
    </tr>
</table>
