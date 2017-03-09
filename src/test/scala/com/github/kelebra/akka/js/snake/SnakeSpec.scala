package com.github.kelebra.akka.js.snake

import akka.actor.{ActorRef, ActorSystem, Props}

class SnakeSpec extends AkkaSpec(ActorSystem("snake-spec")) {

  lazy val snake: ActorRef = system.actorOf(Props(classOf[Snake], self))

  "Snake actor" should {

    "erase itself and draw new head when size is one" in {
      val head = Block(0, 2, 1)
      snake ! Start(↑, head)
      snake ! Move

      expectMsg(Erase(head))
      expectMsg(Draw(Block(0, 0, 1)))
    }

    "consume fruit and notify about it when head crosses fruit" in {
      val head = Block(0, 2, 1)
      val fruit = Block(0, 1, 1)
      snake ! Start(↑, head)
      snake ! Fruit(fruit)
      snake ! Move

      expectMsg(Erase(fruit))
      expectMsg(Fruitless)
    }
  }
}
