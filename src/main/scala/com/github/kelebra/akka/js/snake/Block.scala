package com.github.kelebra.akka.js.snake

case class Block(x: Int, y: Int, radius: Int) {

  def move(direction: Direction): Block = copy(x = direction.dx(x), y = direction.dy(y))
}
