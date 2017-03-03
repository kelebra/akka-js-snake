package com.github.kelebra.akka.js.snake

import org.scalatest.{Matchers, WordSpecLike}

import scala.language.postfixOps

class BlockSpecification extends WordSpecLike with Matchers {

  "Block" should {

    "move right" in {
      (Block(0, 0, 1) move →) shouldBe Block(1, 0, 1)
    }

    "move left" in {
      (Block(1, 1, 1) move `←`) shouldBe Block(0, 1, 1)
    }

    "move up" in {
      (Block(1, 1, 1) move ↑) shouldBe Block(1, 0, 1)
    }

    "move down" in {
      (Block(1, 1, 1) move ↓) shouldBe Block(1, 2, 1)
    }

    "not move" in {
      (Block(1, 1, 1) move →←) shouldBe Block(1, 1, 1)
    }

    "be drawn in one cell" in {
      implicit val drawing = InMemoryCanvas(10)
      Block(5, 5, 1) draw()
      drawing.filledCells shouldBe Set(5 -> 5)
    }

    "be drawn in multiple cells" in {
      implicit val drawing = InMemoryCanvas(10)
      Block(5, 5, 2) draw()
      drawing.filledCells shouldBe Set(5 -> 5, 5 -> 6, 6 -> 5, 6 -> 6)
    }

    "be erased in one cell" in {
      implicit val drawing = InMemoryCanvas(10)
      val block = Block(5, 5, 1)
      block draw()
      block erase()
      drawing.filledCells shouldBe Set.empty
    }

    "be erased in multiple cells" in {
      implicit val drawing = InMemoryCanvas(10)
      val block = Block(5, 5, 2)
      block draw()
      block erase()
      drawing.filledCells shouldBe Set.empty
    }
  }
}
