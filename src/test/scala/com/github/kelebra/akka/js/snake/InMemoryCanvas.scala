package com.github.kelebra.akka.js.snake

case class InMemoryCanvas(size: Int) extends Drawing {

  private val cells: Array[Array[Boolean]] = Array.fill(size, size) {
    false
  }

  def draw(block: Block): Unit = set(block, value = true)

  def erase(block: Block): Unit = set(block, value = false)

  private def set(block: Block, value: Boolean): Unit =
    for {
      y <- block.y until block.y + block.radius
      x <- block.x until block.x + block.radius
    } cells(y)(x) = value

  def filledCells: Set[(Int, Int)] =
    (for {
      y <- 0 until size
      x <- 0 until size
      if cells(y)(x)
    } yield (x, y)).toSet
}
