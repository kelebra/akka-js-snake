package com.github.kelebra.akka.js.snake

sealed trait Command

case class Start(direction: Direction, block: Block) extends Command

case object Grow extends Command

case object Move extends Command

case class Draw(block: Block)

case object DrawRandomBlock

case class Erase(block: Block)

case object Consumed extends Command

sealed trait Direction extends Command {

  def dx(x: Int): Int = x

  def dy(y: Int): Int = y
}

case object `↑` extends Direction {

  override def dy(y: Int): Int = y - 1
}

case object `↓` extends Direction {

  override def dy(y: Int): Int = y + 1
}

case object `→` extends Direction {

  override def dx(x: Int): Int = x + 1
}

case object `←` extends Direction {

  override def dx(x: Int): Int = x - 1
}

case object `→←` extends Direction