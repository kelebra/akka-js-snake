package com.github.kelebra.akka.js.snake

import org.scalajs.dom
import org.scalajs.dom.html

trait Drawing {

  def draw(block: Block): Unit

  def erase(block: Block): Unit
}

trait CanvasDrawing extends Drawing {

  implicit val canvas: html.Canvas
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  def draw(block: Block): Unit = ctx.fillRect(block.x, block.y, block.radius, block.radius)

  def erase(block: Block): Unit = ctx.fillRect(block.x, block.y, block.radius, block.radius)
}