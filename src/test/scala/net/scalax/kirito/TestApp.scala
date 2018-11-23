package net.scalax.kirito

import akka.actor.{Actor, Props}

object TestApp extends App {
  println(Test01.model)
  println(Test02.model)
  println(Test03.model)
  println(Test04.model())
  println(Test05.model())
}

object Test01 extends StrictKirito {
  implicit val i1Miao = "12348944"
  implicit val i2Miao = 23
  object Aa

  class Abc(i1: String, i2: Int)

  val model: Abc = strictKirito.effect(strictKirito.singleModel[Abc](Aa).compile).model
}

object Test02 extends StrictKirito {
  implicit val i1Miao = "12348944"
  object Aa {
    val i3 = Option(1234)
  }

  case class Abc(i1: String, i2: Int = 1234, i3: Int = 744964)

  val model: Abc = strictKirito.effect(strictKirito.singleModel[Abc](Aa).compile).model
}

object Test03 extends StrictKirito {
  implicit val i1Miao = "12348944"
  implicit val i2Miao = "ghhfyufty"
  implicit val i3Miao = 389
  object Aa {
    val i1 = i1Miao
    val i2 = i2Miao
  }

  case class Abc(i1: String, i2: String, i3: Int)

  val model: Abc = strictKirito.effect(strictKirito.singleModel[Abc](Aa).compile).model
}

object Test04 extends NonStrictKirito {
  implicit val i1Miao = "12348944"
  implicit val i2Miao = 389
  implicit val i3Miao = () => 892L
  object Aa

  class Abc(i1: String, i2: () => Int, i3: Long)

  val model: () => Abc = nonStrictKirito.effect(nonStrictKirito.singleModel[Abc](Aa).compile).model
}

object Test05 extends NonStrictKirito {
  implicit val i1Miao = "12348944"
  implicit val i2Miao = 389
  object Aa {
    implicit val i3Miao = () => Option.empty
  }

  case class Abc(i1: String, i2: () => Int, i3: Long = 3L)

  val model: () => Abc = nonStrictKirito.effect(nonStrictKirito.singleModel[Abc](Aa).compile).model
}

case class I2Actor(i3: Int, i4: Short = 7812: Short) extends Actor {
  override val receive = {
    case _ =>
  }
}

object Test06 extends StrictKirito with AkkaKirito {
  implicit val i1Miao = "joirhjrjiorthjnt"
  implicit val i3Miao = 8
  object Bb
  object Aa {
    val i2 = akkaKirito.effect(akkaKirito.singleModel[I2Actor](Bb).compile).model
  }

  class Abc(i1: String, i2: Props)

  val model: Abc = strictKirito.effect(strictKirito.singleModel[Abc](Aa).compile).model
}
