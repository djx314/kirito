package net.scalax.kirito.impl

import net.scalax.asuna.core.common.Placeholder
import net.scalax.asuna.core.decoder.DecoderShape.Aux
import net.scalax.asuna.core.decoder.{DecoderShape, SplitData}
import net.scalax.asuna.mapper.common.{RepContentWithDefault, RepContentWithNonDefault}
import net.scalax.asuna.mapper.decoder.{DecoderContent, DecoderWrapperHelper}

trait StrictImplicit {

  implicit def implicit1[D]: DecoderShape.Aux[RepContentWithNonDefault[D, D], D, D, StrictTag[StrictImplicit], (Any, Any)] =
    new DecoderShape[RepContentWithNonDefault[D, D], StrictTag[StrictImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = D
      override def wrapRep(base: => RepContentWithNonDefault[D, D]): D                             = base.rep
      override def buildRep(base: D, oldRep: StrictTag[StrictImplicit]): StrictTag[StrictImplicit] = new StrictTag[StrictImplicit] {}
      override def takeData(rep: D, oldData: (Any, Any)): SplitData[D, (Any, Any)]                 = SplitData(current = rep, left = oldData)
    }

  implicit def implicit2[D](implicit model: D): DecoderShape.Aux[RepContentWithNonDefault[Placeholder[D], D], D, D, StrictTag[StrictImplicit], (Any, Any)] =
    new DecoderShape[RepContentWithNonDefault[Placeholder[D], D], StrictTag[StrictImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = D
      override def wrapRep(base: => RepContentWithNonDefault[Placeholder[D], D]): D                = model
      override def buildRep(base: D, oldRep: StrictTag[StrictImplicit]): StrictTag[StrictImplicit] = new StrictTag[StrictImplicit] {}
      override def takeData(rep: D, oldData: (Any, Any)): SplitData[D, (Any, Any)]                 = SplitData(current = rep, left = oldData)
    }

  implicit def implicit3[D]: DecoderShape.Aux[RepContentWithDefault[Option[D], D], D, D, StrictTag[StrictImplicit], (Any, Any)] =
    new DecoderShape[RepContentWithDefault[Option[D], D], StrictTag[StrictImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = D
      override def wrapRep(base: => RepContentWithDefault[Option[D], D]): D = {
        val base1 = base
        base1.rep.getOrElse(base1.defaultValue)
      }
      override def buildRep(base: D, oldRep: StrictTag[StrictImplicit]): StrictTag[StrictImplicit] = new StrictTag[StrictImplicit] {}
      override def takeData(rep: D, oldData: (Any, Any)): SplitData[D, (Any, Any)]                 = SplitData(current = rep, left = oldData)
    }

  implicit def implicit4[D](
      implicit model: D = null.asInstanceOf[D]
  ): DecoderShape.Aux[RepContentWithDefault[Placeholder[D], D], D, D, StrictTag[StrictImplicit], (Any, Any)] =
    new DecoderShape[RepContentWithDefault[Placeholder[D], D], StrictTag[StrictImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = D
      override def wrapRep(base: => RepContentWithDefault[Placeholder[D], D]): D                   = Option(model).getOrElse(base.defaultValue)
      override def buildRep(base: D, oldRep: StrictTag[StrictImplicit]): StrictTag[StrictImplicit] = new StrictTag[StrictImplicit] {}
      override def takeData(rep: D, oldData: (Any, Any)): SplitData[D, (Any, Any)]                 = SplitData(current = rep, left = oldData)
    }

}

object StrictImplicit extends StrictImplicit

trait StrictTag[Poly]

trait InjectModel[Out, Data] extends DecoderContent[Out, Data] {
  def model: Data
}

object strictKiritoImpl extends DecoderWrapperHelper[StrictTag[StrictImplicit], (Any, Any), InjectModel] {
  override def effect[Rep, D, Out](rep: Rep)(implicit shape: Aux[Rep, D, Out, StrictTag[StrictImplicit], (Any, Any)]): InjectModel[Out, D] = {
    val shape1 = shape
    new InjectModel[Out, D] {
      override def model: D = shape1.takeData(shape1.wrapRep(rep), null).current
    }
  }
}
