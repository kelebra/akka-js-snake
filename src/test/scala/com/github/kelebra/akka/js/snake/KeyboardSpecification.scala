package com.github.kelebra.akka.js.snake

import akka.actor.{ActorRef, ActorSystem, Props}

class KeyboardSpecification extends AkkaSpec(ActorSystem("keyboard-spec")) {

  lazy val keyboard: ActorRef = system.actorOf(Props(classOf[Keyboard], self))

  "Keyboard actor" should {

    "send up direction for key code 38" in {
      keyboard ! 38
      expectMsg(↑)
    }

    "send down direction for key code 40" in {
      keyboard ! 40
      expectMsg(↓)
    }

    "send down direction for key code 39" in {
      keyboard ! 39
      expectMsg(→)
    }

    "send down direction for key code 37" in {
      keyboard ! 37
      expectMsg(`←`)
    }
  }
}
