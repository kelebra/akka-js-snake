package com.github.kelebra.akka.js.snake

sealed trait Event

case class Start(direction: Direction, block: Block) extends Event

case object Lost extends Event