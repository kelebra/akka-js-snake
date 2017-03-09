package com.github.kelebra.akka.js.snake

object EnableReflectiveActorCreation {

  def apply(): Unit = {
    akka.actor.JSDynamicAccess.injectClass("com.github.kelebra.akka.js.snake.Snake" -> classOf[Snake])
    akka.actor.JSDynamicAccess.injectClass("com.github.kelebra.akka.js.snake.CanvasDrawing" -> classOf[CanvasDrawing])
    akka.actor.JSDynamicAccess.injectClass("com.github.kelebra.akka.js.snake.Game" -> classOf[Game])
    akka.actor.JSDynamicAccess.injectClass("com.github.kelebra.akka.js.snake.Keyboard" -> classOf[Keyboard])
  }
}
