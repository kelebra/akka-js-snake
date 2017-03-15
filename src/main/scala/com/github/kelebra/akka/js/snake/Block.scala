package com.github.kelebra.akka.js.snake

import com.softwaremill.quicklens._

case class Block(x: Int, y: Int, radius: Int)

trait BlockOps {

  def move(block: Block, direction: Direction): Block

  def ∩(first: Block, second: Block): Boolean
}

trait OpticalBlockOps extends BlockOps {

  def move(block: Block, direction: Direction): Block = {
    val radius = block.radius
    direction match {
      case `↑` => block.modify(_.y).using(_ - radius - 1)
      case `↓` => block.modify(_.y).using(_ + radius + 1)
      case `→` => block.modify(_.x).using(_ + radius + 1)
      case `←` => block.modify(_.x).using(_ - radius - 1)
      case `→←` => block
    }
  }

  def ∩(first: Block, second: Block): Boolean = {
    val `horizontal intersection` = first.x > second.x + second.radius || second.x > first.x + first.radius
    val `vertical intersection` = first.y < second.y - second.radius || second.y < first.y - first.radius
    !(`horizontal intersection` || `vertical intersection`)
  }
}
