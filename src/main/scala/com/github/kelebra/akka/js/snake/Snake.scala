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
    case Grow =>
      val tail = `new tail`(state last)
      pane ! Draw(tail)
      context.become(behavior(state :+ tail))
    case Fruit(block) => context.become(behavior(state.copy(fruit = Option(block))))
    case Move =>
      val `next head` = state.head.move(state.direction)
      val `fruit consumed` = state.fruit.exists(_.intersects(`next head`))
      if (`fruit consumed`) {
        state.fruit.foreach(fruit => pane ! Erase(fruit))
        self ! Grow
        sender() ! Fruitless
      } else {
        pane ! Erase(state.last)
        pane ! Draw(`next head`)
        context.become(behavior((`next head` +: state) :-))
      }
  }

  private def `new tail`(last: Block): Block = {
    val radius = last.radius
    Block(last.x, last.y + radius + 1, radius)
  }
}

private case class State(direction: Direction = →←,
                         body: Seq[Block] = Nil,
                         fruit: Option[Block] = None) {

  def :+(block: Block): State = copy(body = body :+ block)

  def +:(block: Block): State = copy(body = block +: body)

  def :- : State = copy(body = body.dropRight(1))

  def ~>(direction: Direction): State = copy(direction = direction)

  def head: Block = body.head

  def last: Block = body.last
}