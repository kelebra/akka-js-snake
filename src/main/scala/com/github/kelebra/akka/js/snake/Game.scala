package com.github.kelebra.akka.js.snake

import akka.actor.{Actor, ActorRef, Cancellable, Terminated}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.scalajs.js.annotation.JSExport

@JSExport
case class Game(snake: ActorRef, pane: ActorRef, fps: Int) extends Actor {

  private val frequency: FiniteDuration = (1000 milliseconds) / fps

  def receive: Receive = behavior()

  private def behavior(processes: Seq[Cancellable] = Seq.empty): Receive = {
    case start: Start =>
      context watch snake
      snake forward start
      context.become(
        behavior(processes
          :+ context.system.scheduler.schedule(frequency, frequency, snake, Move)
          :+ context.system.scheduler.schedule(1 second, 10 seconds, pane, DrawRandomBlock)
        )
      )
    case _: Terminated =>
      processes.foreach(_.cancel())
      context unwatch snake
  }
}
