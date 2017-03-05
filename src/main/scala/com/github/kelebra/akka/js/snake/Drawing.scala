package com.github.kelebra.akka.js.snake

import akka.actor.{Actor, PoisonPill}
import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js.annotation.JSExport

trait Drawing extends Actor {

  def draw(block: Block): Unit

  def erase(block: Block): Unit
}

@JSExport
case class CanvasDrawing(canvas: html.Canvas) extends Drawing {

  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  def draw(block: Block): Unit = ctx.fillRect(block.x, block.y, block.radius, block.radius)

  def erase(block: Block): Unit = ctx.clearRect(block.x, block.y, block.radius, block.radius)

  def canDraw(block: Block): Boolean = {
    val radius = block.radius
    (block.x + radius) < canvas.width &&
      (block.x - radius) > 0 &&
      (block.y + radius) < canvas.height &&
      (block.y - radius) > 0
  }

  def receive: Receive = {
    case Draw(block) if canDraw(block) => draw(block)
    case Erase(block) => erase(block)
    case _ =>
      sender() ! PoisonPill
      ctx.clearRect(0, 0, canvas.width, canvas.height)
      ctx.font = "20px Georgia"
      ctx.fillText("You lost!", canvas.width / 2, canvas.height / 2)
  }
}