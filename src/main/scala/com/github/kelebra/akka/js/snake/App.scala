package com.github.kelebra.akka.js.snake

import akka.actor.{ActorSystem, Props}
import org.scalajs.dom.{html, _}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scala.util.Random

object App extends js.JSApp {

  @JSExport
  override def main(): Unit = {
    akka.actor.JSDynamicAccess.injectClass("com.github.kelebra.akka.js.snake.Snake" -> classOf[Snake])
    akka.actor.JSDynamicAccess.injectClass("com.github.kelebra.akka.js.snake.CanvasDrawing" -> classOf[CanvasDrawing])
    akka.actor.JSDynamicAccess.injectClass("com.github.kelebra.akka.js.snake.Game" -> classOf[Game])
    akka.actor.JSDynamicAccess.injectClass("com.github.kelebra.akka.js.snake.Keyboard" -> classOf[Keyboard])

    val system = ActorSystem("system")
    val `moves per second` = 27
    val canvas = document.getElementById("canvas").asInstanceOf[html.Canvas]
    canvas.width = 1000
    canvas.height = 500

    val `random block generator`: () => Block = () => {
      val radius = 5
      Block(
        radius + Random.nextInt(canvas.width - radius),
        radius + Random.nextInt(canvas.height - radius),
        radius
      )
    }

    val pane = system.actorOf(Props(classOf[CanvasDrawing], canvas))
    val snake = system.actorOf(Props(classOf[Snake], pane))
    val keyboard = system.actorOf(Props(classOf[Keyboard], snake))
    val game = system.actorOf(Props(classOf[Game], snake, pane, `random block generator`, `moves per second`))

    game ! Start(↑, Block(canvas.width / 2, canvas.height / 2, 5))
    document.onkeydown = (event: KeyboardEvent) => keyboard ! event.keyCode
  }
}
