package com.github.kelebra.akka.js.snake

sealed trait Command

case class Start(direction: Direction, block: Block) extends Command

case object Grow extends Command

case object Move extends Command

case class Draw(block: Block) extends Command

case class Erase(block: Block) extends Command

case class Fruit(block: Block) extends Command

case object Fruitless extends Command

case object Consumed extends Command

sealed abstract class Direction(op: => Direction) extends Command {

  def opposite: Direction = op
}

case object `↑` extends Direction(↓)

case object `↓` extends Direction(↑)

case object `→` extends Direction(`←`)

case object `←` extends Direction(`→`)

case object `→←` extends Direction(`↑`)