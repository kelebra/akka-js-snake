import java.io.PrintWriter
import java.nio.file.{Files, StandardCopyOption}

import scala.io.Source

enablePlugins(ScalaJSPlugin)

name := "akka-js-snake"

scalaVersion := "2.11.8"

scalaBinaryVersion := "2.11"

sbtVersion := "0.13.13"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.0",
  "com.lihaoyi" %%% "scalatags" % "0.4.5",
  "org.akka-js" %%% "akkajsactor" % "1.2.5.1",
  "com.softwaremill.quicklens" %%% "quicklens" % "1.4.8",
  "org.akka-js" %%% "akkajstestkit" % "1.2.5.1" % Test,
  "org.scalatest" %% "scalatest" % "3.0.1" % Test,
  "org.scalatest" %%% "scalatest" % "3.0.1" % Test
)

scalaJSUseMainModuleInitializer := true

scalaJSStage := FastOptStage

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
