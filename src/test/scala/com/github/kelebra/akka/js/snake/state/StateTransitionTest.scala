package com.github.kelebra.akka.js.snake.state

import com.github.kelebra.akka.js.snake.{Block, `↑`, `←`}
import org.scalatest.{Inside, Matchers, WordSpec}

class StateTransitionTest extends WordSpec with Matchers with StateTransition with Inside {

  "State transition" should {

    "initialize empty state" in {
      val empty = State()
      val start = Block(0, 1, 1)

      inside(initialized(empty, ↑, start)) { case State(direction, blocks, _) =>
        blocks should be(start :: Nil)
        direction should be(↑)
      }
    }

    "not initialize state one more time" in {
      val empty = State()
      val block = Block(0, 0, 1)
      val direction = ↑

      val init = initialized(empty, direction, block)
      init should be(initialized(init, direction.opposite, Block(0, 0, 2)))
    }

    "change direction and reverse blocks for opposite direction" in {
      val direction = ↑
      val blocks = Block(1, 2, 3) :: Block(3, 2, 1) :: Nil
      val state = State(direction, blocks, None)

      directed(state, direction.opposite) should be(state.copy(direction = direction.opposite, body = blocks.reverse))
    }

    "change direction only" in {
      val direction = `↑`
      val blocks = Block(1, 2, 3) :: Block(3, 2, 1) :: Nil
      val state = State(direction, blocks, None)

      directed(state, `←`) should be(state.copy(direction = `←`))
    }
  }
}
