package com.github.kelebra.akka.js.snake

import akka.actor.ActorSystem
import akka.testkit.{DefaultTimeout, ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

abstract class AkkaSpec(_system: => ActorSystem) extends TestKit(_system)
  with WordSpecLike with Matchers with BeforeAndAfterAll with ImplicitSender with DefaultTimeout {

  final override def afterAll: Unit = shutdown()

}
