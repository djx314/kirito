package net.scalax.kirito

import net.scalax.asuna.core.common.Placeholder
import net.scalax.asuna.core.decoder.DecoderShape.Aux
import net.scalax.asuna.core.decoder.{DecoderShape, SplitData}
import net.scalax.asuna.mapper.common.{RepContentWithDefault, RepContentWithNonDefault}
import net.scalax.asuna.mapper.decoder.{DecoderContent, DecoderWrapperHelper}

trait NonStrictImplicit {

  implicit def implicit1[D]: DecoderShape.Aux[RepContentWithNonDefault[D, D], D, () => D, NonStrictTag[NonStrictImplicit], (Any, Any)] =
    new DecoderShape[RepContentWithNonDefault[D, D], NonStrictTag[NonStrictImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = () => D
      override def wrapRep(base: => RepContentWithNonDefault[D, D]): () => D = () => base.rep
      override def buildRep(base: () => D, oldRep: NonStrictTag[NonStrictImplicit]): NonStrictTag[NonStrictImplicit] =
        new NonStrictTagImpl[NonStrictImplicit](() => (base(), oldRep.func()))
      override def takeData(rep: () => D, oldData: (Any, Any)): SplitData[D, (Any, Any)] =
        SplitData(current = oldData._1.asInstanceOf[D], left = oldData._2.asInstanceOf[(Any, Any)])
    }

  implicit def implicit2[D]: DecoderShape.Aux[RepContentWithNonDefault[() => D, D], D, () => D, NonStrictTag[NonStrictImplicit], (Any, Any)] =
    new DecoderShape[RepContentWithNonDefault[() => D, D], NonStrictTag[NonStrictImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = () => D
      override def wrapRep(base: => RepContentWithNonDefault[() => D, D]): () => D = base.rep
      override def buildRep(base: () => D, oldRep: NonStrictTag[NonStrictImplicit]): NonStrictTag[NonStrictImplicit] =
        new NonStrictTagImpl[NonStrictImplicit](() => (base(), oldRep.func()))
      override def takeData(rep: () => D, oldData: (Any, Any)): SplitData[D, (Any, Any)] =
        SplitData(current = oldData._1.asInstanceOf[D], left = oldData._2.asInstanceOf[(Any, Any)])
    }

  implicit def implicit3[D](
      implicit model: D
  ): DecoderShape.Aux[RepContentWithNonDefault[Placeholder[() => D], () => D], () => D, D, NonStrictTag[NonStrictImplicit], (Any, Any)] =
    new DecoderShape[RepContentWithNonDefault[Placeholder[() => D], () => D], NonStrictTag[NonStrictImplicit], (Any, Any)] {
      override type Data   = () => D
      override type Target = D
      override def wrapRep(base: => RepContentWithNonDefault[Placeholder[() => D], () => D]): D                = model
      override def buildRep(base: D, oldRep: NonStrictTag[NonStrictImplicit]): NonStrictTag[NonStrictImplicit] = oldRep
      override def takeData(rep: D, oldData: (Any, Any)): SplitData[() => D, (Any, Any)]                       = SplitData(current = () => rep, left = oldData)
    }

  implicit def implicit4[D](
      implicit model: D
  ): DecoderShape.Aux[RepContentWithNonDefault[Placeholder[D], D], D, D, NonStrictTag[NonStrictImplicit], (Any, Any)] =
    new DecoderShape[RepContentWithNonDefault[Placeholder[D], D], NonStrictTag[NonStrictImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = D
      override def wrapRep(base: => RepContentWithNonDefault[Placeholder[D], D]): D                            = model
      override def buildRep(base: D, oldRep: NonStrictTag[NonStrictImplicit]): NonStrictTag[NonStrictImplicit] = oldRep
      override def takeData(rep: D, oldData: (Any, Any)): SplitData[D, (Any, Any)]                             = SplitData(current = rep, left = oldData)
    }

  implicit def implicit5[D](
      implicit model: () => D
  ): DecoderShape.Aux[RepContentWithNonDefault[Placeholder[D], D], D, () => D, NonStrictTag[NonStrictImplicit], (Any, Any)] =
    new DecoderShape[RepContentWithNonDefault[Placeholder[D], D], NonStrictTag[NonStrictImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = () => D
      override def wrapRep(base: => RepContentWithNonDefault[Placeholder[D], D]): () => D = model
      override def buildRep(base: () => D, oldRep: NonStrictTag[NonStrictImplicit]): NonStrictTag[NonStrictImplicit] =
        new NonStrictTagImpl[NonStrictImplicit](() => (base(), oldRep.func()))
      override def takeData(rep: () => D, oldData: (Any, Any)): SplitData[D, (Any, Any)] =
        SplitData(current = oldData._1.asInstanceOf[D], left = oldData._2.asInstanceOf[(Any, Any)])
    }

  implicit def implicit6[D]: DecoderShape.Aux[RepContentWithDefault[Option[D], D], D, () => D, NonStrictTag[NonStrictImplicit], (Any, Any)] =
    new DecoderShape[RepContentWithDefault[Option[D], D], NonStrictTag[NonStrictImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = () => D
      override def wrapRep(base: => RepContentWithDefault[Option[D], D]): () => D = {
        val base1 = base
        () =>
          base1.rep.getOrElse(base1.defaultValue)
      }
      override def buildRep(base: () => D, oldRep: NonStrictTag[NonStrictImplicit]): NonStrictTag[NonStrictImplicit] =
        new NonStrictTagImpl[NonStrictImplicit](() => (base(), oldRep.func()))
      override def takeData(rep: () => D, oldData: (Any, Any)): SplitData[D, (Any, Any)] =
        SplitData(current = oldData._1.asInstanceOf[D], left = oldData._2.asInstanceOf[(Any, Any)])
    }

  implicit def implicit7[D]: DecoderShape.Aux[RepContentWithDefault[Option[() => D], D], D, () => D, NonStrictTag[NonStrictImplicit], (Any, Any)] =
    new DecoderShape[RepContentWithDefault[Option[() => D], D], NonStrictTag[NonStrictImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = () => D
      override def wrapRep(base: => RepContentWithDefault[Option[() => D], D]): () => D = {
        val base1 = base
        () =>
          base1.rep.map(_.apply).getOrElse(base1.defaultValue)
      }
      override def buildRep(base: () => D, oldRep: NonStrictTag[NonStrictImplicit]): NonStrictTag[NonStrictImplicit] =
        new NonStrictTagImpl[NonStrictImplicit](() => (base(), oldRep.func()))
      override def takeData(rep: () => D, oldData: (Any, Any)): SplitData[D, (Any, Any)] =
        SplitData(current = oldData._1.asInstanceOf[D], left = oldData._2.asInstanceOf[(Any, Any)])
    }

  implicit def implicit8[D]: DecoderShape.Aux[RepContentWithDefault[() => Option[D], D], D, () => D, NonStrictTag[NonStrictImplicit], (Any, Any)] =
    new DecoderShape[RepContentWithDefault[() => Option[D], D], NonStrictTag[NonStrictImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = () => D
      override def wrapRep(base: => RepContentWithDefault[() => Option[D], D]): () => D = {
        val base1 = base
        () =>
          base1.rep().getOrElse(base1.defaultValue)
      }
      override def buildRep(base: () => D, oldRep: NonStrictTag[NonStrictImplicit]): NonStrictTag[NonStrictImplicit] =
        new NonStrictTagImpl[NonStrictImplicit](() => (base(), oldRep.func()))
      override def takeData(rep: () => D, oldData: (Any, Any)): SplitData[D, (Any, Any)] =
        SplitData(current = oldData._1.asInstanceOf[D], left = oldData._2.asInstanceOf[(Any, Any)])
    }

  implicit def implicit9[D]: DecoderShape.Aux[RepContentWithDefault[D, D], D, () => D, NonStrictTag[NonStrictImplicit], (Any, Any)] =
    new DecoderShape[RepContentWithDefault[D, D], NonStrictTag[NonStrictImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = () => D
      override def wrapRep(base: => RepContentWithDefault[D, D]): () => D = { () =>
        base.rep
      }
      override def buildRep(base: () => D, oldRep: NonStrictTag[NonStrictImplicit]): NonStrictTag[NonStrictImplicit] =
        new NonStrictTagImpl[NonStrictImplicit](() => (base(), oldRep.func()))
      override def takeData(rep: () => D, oldData: (Any, Any)): SplitData[D, (Any, Any)] =
        SplitData(current = oldData._1.asInstanceOf[D], left = oldData._2.asInstanceOf[(Any, Any)])
    }

  implicit def implicit10[D]: DecoderShape.Aux[RepContentWithDefault[() => D, D], D, () => D, NonStrictTag[NonStrictImplicit], (Any, Any)] =
    new DecoderShape[RepContentWithDefault[() => D, D], NonStrictTag[NonStrictImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = () => D
      override def wrapRep(base: => RepContentWithDefault[() => D, D]): () => D = {
        val base1 = base
        () =>
          base1.rep()
      }
      override def buildRep(base: () => D, oldRep: NonStrictTag[NonStrictImplicit]): NonStrictTag[NonStrictImplicit] =
        new NonStrictTagImpl[NonStrictImplicit](() => (base(), oldRep.func()))
      override def takeData(rep: () => D, oldData: (Any, Any)): SplitData[D, (Any, Any)] =
        SplitData(current = oldData._1.asInstanceOf[D], left = oldData._2.asInstanceOf[(Any, Any)])
    }

  implicit def implicit11[D](
      implicit model: ProImplicit[D] = null.asInstanceOf[ProImplicit[D]]
  ): DecoderShape.Aux[RepContentWithDefault[Placeholder[D], D], D, () => D, NonStrictTag[NonStrictImplicit], (Any, Any)] =
    new DecoderShape[RepContentWithDefault[Placeholder[D], D], NonStrictTag[NonStrictImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = () => D
      override def wrapRep(base: => RepContentWithDefault[Placeholder[D], D]): () => D =
        Option(model)
          .map {
            case m: StrictProImplicit[D] =>
              () =>
                m.model
            case r: NonStrictProImplicit[D] => r.func
          }
          .getOrElse(() => base.defaultValue)
      override def buildRep(base: () => D, oldRep: NonStrictTag[NonStrictImplicit]): NonStrictTag[NonStrictImplicit] =
        new NonStrictTagImpl[NonStrictImplicit](() => (base(), oldRep.func()))
      override def takeData(rep: () => D, oldData: (Any, Any)): SplitData[D, (Any, Any)] =
        SplitData(current = oldData._1.asInstanceOf[D], left = oldData._2.asInstanceOf[(Any, Any)])
    }

}

sealed trait ProImplicit[D]

object ProImplicit {
  implicit def implicit1[D](implicit m: D): ProImplicit[D] = {
    new StrictProImplicit[D] {
      override val model = m
    }
  }

  implicit def implicit2[D](implicit m: () => D): ProImplicit[D] = {
    new NonStrictProImplicit[D] {
      override val func = m
    }
  }
}

trait StrictProImplicit[D] extends ProImplicit[D] {
  val model: D
}

trait NonStrictProImplicit[D] extends ProImplicit[D] {
  val func: () => D
}

object NonStrictImplicit extends NonStrictImplicit

trait NonStrictTag[Poly] {
  def func: () => (Any, Any)
}

class NonStrictTagImpl[Poly](override val func: () => (Any, Any)) extends NonStrictTag[Poly]

trait NonStrictKirito {

  trait NonStrictInjectModel[Out, Data] extends DecoderContent[Out, Data] {
    def model: () => Data
  }

  object nonStrictKirito extends DecoderWrapperHelper[NonStrictTag[NonStrictImplicit], (Any, Any), NonStrictInjectModel] {
    override def effect[Rep, D, Out](rep: Rep)(implicit shape: Aux[Rep, D, Out, NonStrictTag[NonStrictImplicit], (Any, Any)]): NonStrictInjectModel[Out, D] = {
      val shape1  = shape
      val wrapRep = shape1.wrapRep(rep)
      val repCol  = shape1.buildRep(wrapRep, new NonStrictTagImpl[NonStrictImplicit](() => null))
      val model1  = () => shape1.takeData(wrapRep, repCol.func()).current
      new NonStrictInjectModel[Out, D] {
        override val model: () => D = model1
      }
    }
  }

}
