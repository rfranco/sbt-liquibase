libraryDependencies <+= sbtVersion(v => "org.scala-sbt" % "scripted-plugin" % v)

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.8.3")

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.6.4")