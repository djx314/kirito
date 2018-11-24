package net.scalax

import net.scalax.kirito.impl.{akkaKiritoImpl, nonStrictKiritoImpl, strictKiritoImpl}

package object kirito {
  val wireAkka      = akkaKiritoImpl
  val wireStrict    = strictKiritoImpl
  val wireNonStrict = nonStrictKiritoImpl
}
