package com.github.kelebra.akka.js.snake

import akka.actor.ActorSystem
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

abstract class ActorTest extends //ReflectiveTestKit(ActorSystem(s"snake-test-${System.currentTimeMillis()}"))
   WordSpecLike with Matchers with BeforeAndAfterAll {

//  override def afterAll(): Unit = shutdown()
}
