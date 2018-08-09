name := "in_mem_db"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.2"

libraryDependencies ++= List(
  "com.h2database" % "h2" % "1.4.192",
  "io.getquill" %% "quill-jdbc" % "2.5.4"

)

scalacOptions ++= Seq("-Xfatal-warnings", "-deprecation", "-print", "-unchecked", "-feature", "-Xlint", "-Ywarn-inaccessible", "-Ywarn-nullary-override", "-Ywarn-nullary-unit")
