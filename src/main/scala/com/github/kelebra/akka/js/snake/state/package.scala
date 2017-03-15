package com.github.kelebra.akka.js.snake

import com.softwaremill.quicklens._

import scala.language.implicitConversions

package object state {

  case class State(direction: Direction = →←, body: List[Block] = Nil, fruit: Option[Block] = None)

  trait StateTransition {

    type Fruit = Block
    type Head = Block
    type Tail = Block
    type Transition = (Block, Direction) => Block

    def initialized(state: State, direction: Direction, head: Head): State = {
      val uninitialized = state.body.isEmpty
      state
        .modify(_.direction).setToIf(uninitialized)(direction)
        .modify(_.body).setToIf(uninitialized)(head :: Nil)
    }

    def directed(state: State, next: Direction): State = {
      val `should reverse` = next == state.direction.opposite
      state
        .modify(_.body).usingIf(`should reverse`)(_.reverse)
        .modify(_.direction).setTo(next)
    }

    def grow(state: State)(transition: Transition): State =
      state.modify(_.body).using(body => body :+ transition(body.last, state.direction))

    def fruit(state: State, fruit: Fruit): State = state.modify(_.fruit).setTo(fruit)

    def fruit[T](state: State)(f: Fruit => Unit): Unit = state.fruit.foreach(f)

    def fruitless(state: State): State = state.modify(_.fruit).setTo(None)

    def eats(state: State)(intersection: (Block, Block) => Boolean): Boolean =
      state.fruit.exists(intersection(_, state.body.head))

    def movement(state: State)(transition: Transition): (Head, Tail, State) = {
      val `next head` = transition(state.body.head, state.direction)
      (
        `next head`,
        state.body.last,
        state
          .modify(_.body).using(`next head` :: _)
          .modify(_.body).using(_.init)
      )
    }

    private implicit def valueToOption[T](any: T): Option[T] = Option(any)
  }

}
