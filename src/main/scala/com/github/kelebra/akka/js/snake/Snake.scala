package com.github.kelebra.akka.js.snake

import akka.actor.Actor

sealed trait Snake extends Actor with Drawing {

  def receive: Receive = behavior(State())

  private def behavior(state: State): Receive = {
    case Start(direction, block) => context.become(behavior(State(direction, block :: Nil)))
    case direction: Direction => context.become(behavior(state ~> direction))
    case Grow(block) => context.become(behavior(state :+ block))
    case Lost => context.become(behavior(state ~> →←))
  }
}

private case class State(direction: Direction = →←, blocks: Seq[Block] = Nil) {

  def :+(block: Block): State = copy(blocks = blocks :+ block)

  def ~>(direction: Direction): State = copy(direction = direction)
}