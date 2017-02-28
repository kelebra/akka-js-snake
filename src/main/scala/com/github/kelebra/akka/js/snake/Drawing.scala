package com.github.kelebra.akka.js.snake

trait Drawing {

  def draw(block: Block): Block

  def erase(block: Block): Block
}
