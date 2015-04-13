name := "Thrift-Http-Client"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.4"

resolvers ++= Seq(
  "repo.codahale.com" at "http://repo.codahale.com"
)

libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-http" % "6.24.0",
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test",
  "net.liftweb" %% "lift-json" % "2.5.1" % "test"
)

parallelExecution in Test := false
