package com.github.kelebra.akka.js.snake

sealed trait Command

sealed trait Direction extends Command

case class Grow(block: Block) extends Command

case object `↑` extends Direction

case object `↓` extends Direction

case object `→` extends Direction

case object `←` extends Direction

case object `→←` extends Direction