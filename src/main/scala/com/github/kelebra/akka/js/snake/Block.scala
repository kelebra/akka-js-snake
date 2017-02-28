package com.github.kelebra.akka.js.snake

sealed trait Block {

  def x: Int

  def y: Int

  def radius: Int

  def draw(implicit d: Drawing): Block = d.draw(this)

  def erase(implicit d: Drawing): Block = d.erase(this)
}
