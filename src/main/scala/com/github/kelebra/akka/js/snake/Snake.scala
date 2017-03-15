package com.github.kelebra.akka.js.snake

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.github.kelebra.akka.js.snake.state.{StateTransition, State}

import scala.language.postfixOps
import scala.scalajs.js.annotation.JSExport

@JSExport
case class Snake(pane: ActorRef) extends Actor with ActorLogging with OpticalBlockOps with StateTransition {

  def receive: Receive = behavior(State())

  private def behavior(current: State): Receive = {
    case Start(direction, head) => behave(initialized(current, direction, head))
    case next: Direction => behave(directed(current, next))
    case Grow => behave(grow(current)(move))
    case Fruit(block) => behave(fruit(current, block))
    case Move if eats(current)(∩) =>
      fruit(current)(f => pane ! Erase(f))
      sender() ! Fruitless
      self ! Grow
      behave(fruitless(current))
    case Move =>
      val (head, tail, next) = movement(current)(move)
      pane ! Erase(tail)
      pane ! Draw(head)
      behave(next)
  }

  private def behave(state: State): Unit = context.become(behavior(state))
}