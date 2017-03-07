package com.github.kelebra.akka.js.snake

import akka.actor.{Actor, ActorLogging, ActorRef}

import scala.language.postfixOps
import scala.scalajs.js.annotation.JSExport

@JSExport
case class Snake(pane: ActorRef) extends Actor with ActorLogging {

  def receive: Receive = behavior(State())

  private def behavior(state: State): Receive = {
    case Start(direction, block) => context.become(behavior(State(direction, block :: Nil)))
    case next: Direction =>
      val body = if (next == state.direction.opposite) state.body.reverse else state.body
      context.become(behavior(State(next, body, state.fruit)))
    case Grow => context.become(behavior(state :+ state.last.move(state.direction)))
    case Fruit(block) => context.become(behavior(state.copy(fruit = Option(block))))
    case Move if `eats fruit`(state) =>
      state.fruit.foreach(fruit => pane ! Erase(fruit))
      sender() ! Fruitless
      self ! Grow
      context.become(behavior(state.copy(fruit = None)))
    case Move =>
      val `next head` = state.head.move(state.direction)
      pane ! Erase(state last)
      pane ! Draw(`next head`)
      context.become(behavior(`next head` +: state :-))
  }

  private def `eats fruit`(state: State): Boolean = state.fruit.exists(_.intersects(state.head))
}

private case class State(direction: Direction = →←,
                         body: Seq[Block] = Nil,
                         fruit: Option[Block] = None) {

  def :+(block: Block): State = copy(body = body :+ block)

  def +:(block: Block): State = copy(body = block +: body)

  def :- : State = copy(body = body.dropRight(1))

  def head: Block = body.head

  def last: Block = body.last
}