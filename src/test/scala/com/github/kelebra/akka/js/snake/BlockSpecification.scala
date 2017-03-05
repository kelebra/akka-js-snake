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
  }
}
