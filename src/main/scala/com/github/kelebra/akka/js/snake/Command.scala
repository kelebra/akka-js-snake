package com.github.kelebra.akka.js.snake

sealed trait Command

case object Grow extends Command

case object Move extends Command

case class Draw(erase: Seq[Block] = Seq.empty, draw: Seq[Block]= Seq.empty)

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