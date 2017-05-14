package com.github.kelebra.akka.js.snake

import akka.actor.{Actor, ActorRef, Cancellable, Terminated}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps

case class Game(snake: ActorRef,
                pane: ActorRef,
                `random block generator`: () => Block,
                `moves per second`: Int) extends Actor {

  private val `millis per move`: FiniteDuration = (1000 milliseconds) / `moves per second`

  def receive: Receive = behavior()

  private def behavior(movement: Option[Cancellable] = None): Receive = {
    case start: Start =>
      context watch snake
      snake forward start
      self ! Fruitless
      context.become(
        behavior(Option(context.system.scheduler.schedule(`millis per move`, `millis per move`, snake, Move)))
      )
    case Fruitless =>
      val fruit = `random block generator`()
      pane ! Draw(fruit)
      snake ! Fruit(fruit)
    case _: Terminated =>
      movement.foreach(_.cancel())
      context unwatch snake
  }
}
