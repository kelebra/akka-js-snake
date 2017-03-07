package com.github.kelebra.akka.js.snake

sealed trait Command

case class Start(direction: Direction, block: Block) extends Command

case object GameOver extends Command

case object Grow extends Command

case object Move extends Command

case class Draw(block: Block) extends Command

case class Erase(block: Block) extends Command

case class Fruit(block: Block) extends Command

case object Fruitless extends Command

case object Consumed extends Command

sealed trait Direction extends Command {

  def dx(x: Int, radius: Int): Int = x

  def dy(y: Int, radius: Int): Int = y

  def opposite: Direction
}

case object `↑` extends Direction {

  override def dy(y: Int, radius: Int): Int = y - radius - 1

  def opposite: Direction = ↓
}

case object `↓` extends Direction {

  override def dy(y: Int, radius: Int): Int = y + radius + 1

  def opposite: Direction = ↑
}

case object `→` extends Direction {

  override def dx(x: Int, radius: Int): Int = x + radius + 1

  def opposite: Direction = `←`
}

case object `←` extends Direction {

  override def dx(x: Int, radius: Int): Int = x - radius - 1

  def opposite: Direction = `→`
}

case object `→←` extends Direction {

  def opposite: Direction = `↑`
}