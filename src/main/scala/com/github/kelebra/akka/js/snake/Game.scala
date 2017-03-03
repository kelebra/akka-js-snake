package com.github.kelebra.akka.js.snake

import akka.actor.{Actor, ActorRef, Cancellable, PoisonPill}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps

case class Game(snake: ActorRef, fps: Int) extends Actor {

  private val frequency: FiniteDuration = (1000 milliseconds) / fps

  def receive: Receive = behavior()

  private def behavior(state: Option[Cancellable] = None): Receive = {
    case start: Start =>
      snake forward start
      context.become(
        behavior(Option(context.system.scheduler.schedule(frequency, frequency, snake, Move)))
      )
    case Lost =>
      snake ! PoisonPill
      state.foreach(_.cancel())
      self ! PoisonPill
  }
}
