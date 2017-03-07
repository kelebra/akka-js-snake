package com.github.kelebra.akka.js.snake

case class Block(x: Int, y: Int, radius: Int) {

  def move(direction: Direction): Block = copy(x = direction.dx(x, radius), y = direction.dy(y, radius))

  def intersects(another: Block): Boolean = {
    val `horizontal intersection` = x > another.x + another.radius || another.x > x + radius
    val `vertical intersection` = y < another.y - another.radius || another.y < y - radius
    !(`horizontal intersection` || `vertical intersection`)
  }
}
