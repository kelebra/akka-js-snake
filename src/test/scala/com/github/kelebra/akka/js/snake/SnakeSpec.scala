package com.github.kelebra.akka.js.snake

import akka.actor.{ActorRef, ActorSystem, Props}

class SnakeSpec extends AkkaSpec(ActorSystem("snake-spec")) {

  "Snake actor" should {

    "erase itself and draw new head when size is one" in {
      val head = Block(0, 2, 1)
      val snake = snakeRef

      snake ! Start(↑, head)
      snake ! Move

      expectMsg(Erase(head))
      expectMsg(Draw(Block(0, 0, 1)))
    }

    "consume fruit and notify about it when head crosses fruit" in {
      val head = Block(0, 2, 1)
      val fruit = Block(0, 1, 1)
      val snake = snakeRef

      snake ! Start(↑, head)
      snake ! Fruit(fruit)
      snake ! Move

      expectMsg(Erase(fruit))
      expectMsg(Fruitless)
    }

    "move in opposite direction with reversed block sequence" in {
      val head = Block(0, 2, 1)
      val snake = snakeRef

      snake ! Start(↓, head)
      snake ! Move

      expectMsg(Erase(Block(0, 2, 1)))
      expectMsg(Draw(Block(0, 4, 1)))

      snake ! Grow
      snake ! ↑
      snake ! Move

      expectMsg(Erase(Block(0, 4, 1)))
      expectMsg(Draw(Block(0, 4, 1)))

      snake ! Move

      expectMsg(Erase(Block(0, 6, 1)))
      expectMsg(Draw(Block(0, 2, 1)))
    }
  }

  def snakeRef: ActorRef = system.actorOf(Props(classOf[Snake], self))
}
