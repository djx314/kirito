# kirito
A inject framework like macwire in scala.

- Case 1

You can wire a strict value use `strictKirito`

```scala
implicit val i1Miao = "12348944"
implicit val i2Miao = 23
object Aa

class Abc(i1: String, i2: Int)

val model: Abc = strictKirito.effect(strictKirito.singleModel[Abc](Aa).compile).model
```

- Case 2

If your case class's field have a default value.
Kirito will fill the default value when the implicit of this field is not founded.
And you can special the autal value in the poly object.

```scala
implicit val i1Miao = "12348944"
object Aa {
    val i3 = Option(1234)
}

case class Abc(i1: String, i2: Int = 1234, i3: Int = 744964)

val model: Abc = strictKirito.effect(strictKirito.singleModel[Abc](Aa).compile).model
```

- Case 3

When there are mutiply implicit are the same time.
You can special the value in the poly object.

```scala
implicit val i1Miao = "12348944"
implicit val i2Miao = "ghhfyufty"
implicit val i3Miao = 389
object Aa {
    val i1 = i1Miao
    val i2 = i2Miao
}

case class Abc(i1: String, i2: String, i3: Int)

val model: Abc = strictKirito.effect(strictKirito.singleModel[Abc](Aa).compile).model
```

- Case 4

You can wire a non-strict value as a function when some property
can only provide a () => PropertyType implicit.

```scala
implicit val i1Miao = "12348944"
implicit val i2Miao = 389
implicit val i3Miao = () => 892L
object Aa

class Abc(i1: String, i2: () => Int, i3: Long)

val model: () => Abc = nonStrictKirito.effect(nonStrictKirito.singleModel[Abc](Aa).compile).model
```

- Case 5

You can provide a value of () => Option[D], Option[() => D] or Option[D]
in poly object when this property hava a default value.
Of cause, provide type D is working.

```scala
implicit val i1Miao = "12348944"
  implicit val i2Miao = 389
  object Aa {
    val i3 = () => Option.empty
    val i4 = Option(() => 1L)
  }

  case class Abc(i1: String, i2: () => Int, i3: Long = 3L, i4: Long = 2L)

  val model: () => Abc = nonStrictKirito.effect(nonStrictKirito.singleModel[Abc](Aa).compile).model
```

- Case 6

Kirito also have a simple akka support.
Juse wire a actor to Props use `akkaKirito` then define it in poly object is Ok.
No use to tag it to a special type.

```scala
case class I2Actor(i3: Int, i4: Short = 7812: Short) extends Actor {
  override val receive = {
    case _ =>
  }
}

implicit val i1Miao = "joirhjrjiorthjnt"
implicit val i3Miao = 8
object Bb
object Aa {
    val i2 = akkaKirito.effect(akkaKirito.singleModel[I2Actor](Bb).compile).model
}

class Abc(i1: String, i2: Props)

val model: Abc = strictKirito.effect(strictKirito.singleModel[Abc](Aa).compile).model
```