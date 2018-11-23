package net.scalax.kirito

import akka.actor.{Actor, Props}

object TestApp extends App {
  println(Test01.model)
  println(Test02.model)
  println(Test03.model)
  println(Test04.model)
  println(Test05.model)
  println(Test06.model)
}

object Test01 extends StrictKirito {
  implicit val i1Miao = "12348944"
  implicit val i2Miao = 23
  object Aa

  class Abc(i1: String, i2: Int) {
    assert(i1 == "12348944")
    assert(i2 == 23)
  }

  val model: Abc = strictKirito.effect(strictKirito.singleModel[Abc](Aa).compile).model
}

object Test02 extends StrictKirito {
  implicit val i1Miao = "12348944"
  object Aa {
    val i3 = Option(77)
  }

  case class Abc(i1: String, i2: Int = 1234, i3: Int = 744964) {
    assert(i1 == "12348944")
    assert(i2 == 1234)
    assert(i3 == 77)
  }

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

  case class Abc(i1: String, i2: String, i3: Int) {
    assert(i1 == "12348944")
    assert(i2 == "ghhfyufty")
    assert(i3 == 389)
  }

  val model: Abc = strictKirito.effect(strictKirito.singleModel[Abc](Aa).compile).model
}

object Test04 extends NonStrictKirito {
  implicit val i1Miao = "12348944"
  implicit val i2Miao = 389
  implicit val i3Miao = () => 892L
  object Aa

  class Abc(i1: String, i2: () => Int, i3: Long) {
    assert(i1 == "12348944")
    assert(i2() == 389)
    assert(i3 == 892L)
  }

  val func: () => Abc = nonStrictKirito.effect(nonStrictKirito.singleModel[Abc](Aa).compile).model
  val model: Abc      = func()
}

object Test05 extends NonStrictKirito {
  implicit val i1Miao = "12348944"
  implicit val i2Miao = 389
  object Aa {
    val i3 = () => Option.empty
    val i4 = Option(() => 1L)
  }

  case class Abc(i1: String, i2: () => Int, i3: Long = 3L, i4: Long = 2L, i5: Long = 378L, i6: Long = 273L) {
    assert(i1 == "12348944")
    assert(i2() == 389)
    assert(i3 == 3L)
    assert(i4 == 1L)
    assert(i5 == 378L)
    assert(i6 == 273L)
  }

  val func: () => Abc = nonStrictKirito.effect(nonStrictKirito.singleModel[Abc](Aa).compile).model
  val model: Abc      = func()
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

  class Abc(i1: String, i2: Props) {
    assert(i1 == "joirhjrjiorthjnt")
    assert(i2.isInstanceOf[Props] && i2 != null)
  }

  val model: Abc = strictKirito.effect(strictKirito.singleModel[Abc](Aa).compile).model
}
