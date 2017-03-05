package com.github.kelebra.akka.js.snake

import akka.actor.Actor
import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js.annotation.JSExport

trait Drawing extends Actor {

  def draw(block: Block): Unit

  def erase(block: Block): Unit

  def receive: Receive = {
    case Draw(delete, create) =>
      delete foreach erase
      create foreach draw
  }
}

@JSExport
case class CanvasDrawing(canvas: html.Canvas) extends Drawing {

  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  def draw(block: Block): Unit = ctx.fillRect(block.x, block.y, block.radius, block.radius)

  def erase(block: Block): Unit = ctx.clearRect(block.x, block.y, block.radius, block.radius)
}