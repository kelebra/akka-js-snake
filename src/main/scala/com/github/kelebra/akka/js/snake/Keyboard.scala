package com.github.kelebra.akka.js.snake

import akka.actor.{Actor, ActorRef}

case class Keyboard(snake: ActorRef) extends Actor {

  def receive: Receive = {
    case 39 => snake ! →
    case 37 => snake ! `←`
    case 40 => snake ! ↓
    case 38 => snake ! ↑
  }
}
