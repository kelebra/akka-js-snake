import java.io.PrintWriter
import java.nio.file.{Files, StandardCopyOption}

import scala.io.Source

enablePlugins(ScalaJSPlugin)

name := "akka-js-snake"

scalaVersion := "2.11.8"

scalaBinaryVersion := "2.11"

sbtVersion := "0.13.7"

libraryDependencies ++= Seq(
    "org.akka-js" %%% "akkajsactor" % "0.2.4.16",
    "org.akka-js" %%% "akkajstestkit" % "0.2.4.16" % "test",
    "com.lihaoyi" %%% "utest" % "0.4.0" % "test"
)

testFrameworks += new TestFramework("utest.runner.Framework")

scalaJSStage := FastOptStage

jsDependencies += RuntimeDOM

val githubPages = taskKey[Unit]("Copy index.html and js files to match github pages layout")

githubPages := {
  // 1) Copy compiled prod js
  val js = (fullOptJS in Compile).value.data
  val jsTarget = new File(".", js.getName)
  Files.copy(js.toPath, jsTarget.toPath, StandardCopyOption.REPLACE_EXISTING)

  // 2) Read current index.html
  val indexContent = Source.fromFile("./src/main/resources/index.html")
    .mkString
    .replace("fastopt", "opt")
    .replace("target/scala-2.11/", "")

  // 3) Create prod index.html
  val index = new File(".", "index.html")
  if (index.exists()) index.delete()
  val writer = new PrintWriter(index)
  writer.write(indexContent)
  writer.close()
}
