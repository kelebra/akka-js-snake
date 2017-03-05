package com.github.kelebra.akka.js.snake

import akka.actor.ActorSystem
import akka.testkit.TestKit

class ReflectiveTestKit(_system: => ActorSystem) extends TestKit(_system) {

  override implicit val system: ActorSystem = {
    akka.actor.JSDynamicAccess.injectClass("com.github.kelebra.akka.js.snake.Snake" -> classOf[Snake])
    akka.actor.JSDynamicAccess.injectClass("com.github.kelebra.akka.js.snake.CanvasDrawing" -> classOf[CanvasDrawing])
    akka.actor.JSDynamicAccess.injectClass("com.github.kelebra.akka.js.snake.Game" -> classOf[Game])

    TestKit.initialization(_system)
  }
}

