package com.github.kelebra.akka.js.snake

import akka.actor.{Actor, ActorRef}

import scala.language.postfixOps
import scala.scalajs.js.annotation.JSExport

@JSExport
case class Snake(pane: ActorRef) extends Actor {

  def receive: Receive = behavior(State())

  private def behavior(state: State): Receive = {
    case Start(direction, block) => context.become(behavior(State(direction, block :: Nil)))
    case direction: Direction => context.become(behavior(state ~> direction))
    case Grow => context.become(behavior(state :+ `new tail`(state last)))
    case Move =>
      val head = state.head.move(state.direction)
      pane ! Erase(state.last)
      pane ! Draw(head)
      context.become(behavior(head +: state :-))
  }

  private def `new tail`(last: Block): Block = {
    val radius = last.radius
    Block(last.x + radius, last.y + radius + 1, radius)
  }
}

private case class State(direction: Direction = →←, blocks: Seq[Block] = Nil) {

  def :+(block: Block): State = copy(blocks = blocks :+ block)

  def +:(block: Block): State = copy(blocks = block +: blocks)

  def :- : State = copy(blocks = blocks.dropRight(1))

  def ~>(direction: Direction): State = copy(direction = direction)

  def head: Block = blocks.head

  def last: Block = blocks.last
}