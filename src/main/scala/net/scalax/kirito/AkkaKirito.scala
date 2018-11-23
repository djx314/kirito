package net.scalax.kirito

import akka.actor.Props
import net.scalax.asuna.core.common.Placeholder
import net.scalax.asuna.core.decoder.DecoderShape.Aux
import net.scalax.asuna.core.decoder.{DecoderShape, SplitData}
import net.scalax.asuna.mapper.common.{SingleRepContentWithDefault, SingleRepContentWithNonDefault}
import net.scalax.asuna.mapper.decoder.{DecoderContent, DecoderWrapperHelper}

import scala.reflect.ClassTag

trait AkkaImplicit {

  implicit def implicit1[D]: DecoderShape.Aux[SingleRepContentWithNonDefault[D, D], D, D, AkkaTag[AkkaImplicit], (Any, Any)] =
    new DecoderShape[SingleRepContentWithNonDefault[D, D], AkkaTag[AkkaImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = D
      override def wrapRep(base: => SingleRepContentWithNonDefault[D, D]): D               = base.rep
      override def buildRep(base: D, oldRep: AkkaTag[AkkaImplicit]): AkkaTag[AkkaImplicit] = new AkkaTagImpl[AkkaImplicit](base :: oldRep.param)
      override def takeData(rep: D, oldData: (Any, Any)): SplitData[D, (Any, Any)]         = SplitData(current = rep, left = oldData)
    }

  implicit def implicit2[D](implicit model: D): DecoderShape.Aux[SingleRepContentWithNonDefault[Placeholder[D], D], D, D, AkkaTag[AkkaImplicit], (Any, Any)] =
    new DecoderShape[SingleRepContentWithNonDefault[Placeholder[D], D], AkkaTag[AkkaImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = D
      override def wrapRep(base: => SingleRepContentWithNonDefault[Placeholder[D], D]): D  = model
      override def buildRep(base: D, oldRep: AkkaTag[AkkaImplicit]): AkkaTag[AkkaImplicit] = new AkkaTagImpl[AkkaImplicit](base :: oldRep.param)
      override def takeData(rep: D, oldData: (Any, Any)): SplitData[D, (Any, Any)]         = SplitData(current = rep, left = oldData)
    }

  implicit def implicit3[D]: DecoderShape.Aux[SingleRepContentWithDefault[Option[D], D], D, D, AkkaTag[AkkaImplicit], (Any, Any)] =
    new DecoderShape[SingleRepContentWithDefault[Option[D], D], AkkaTag[AkkaImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = D
      override def wrapRep(base: => SingleRepContentWithDefault[Option[D], D]): D = {
        val base1 = base
        base1.rep.getOrElse(base1.defaultValue)
      }
      override def buildRep(base: D, oldRep: AkkaTag[AkkaImplicit]): AkkaTag[AkkaImplicit] = new AkkaTagImpl[AkkaImplicit](base :: oldRep.param)
      override def takeData(rep: D, oldData: (Any, Any)): SplitData[D, (Any, Any)]         = SplitData(current = rep, left = oldData)
    }

  implicit def implicit4[D](
      implicit model: D = null.asInstanceOf[D]
  ): DecoderShape.Aux[SingleRepContentWithDefault[Placeholder[D], D], D, D, AkkaTag[AkkaImplicit], (Any, Any)] =
    new DecoderShape[SingleRepContentWithDefault[Placeholder[D], D], AkkaTag[AkkaImplicit], (Any, Any)] {
      override type Data   = D
      override type Target = D
      override def wrapRep(base: => SingleRepContentWithDefault[Placeholder[D], D]): D     = Option(model).getOrElse(base.defaultValue)
      override def buildRep(base: D, oldRep: AkkaTag[AkkaImplicit]): AkkaTag[AkkaImplicit] = new AkkaTagImpl[AkkaImplicit](base :: oldRep.param)
      override def takeData(rep: D, oldData: (Any, Any)): SplitData[D, (Any, Any)]         = SplitData(current = rep, left = oldData)
    }

}

object AkkaImplicit extends AkkaImplicit

trait AkkaTag[Poly] {
  val param: List[Any]
}

class AkkaTagImpl[Poly](override val param: List[Any]) extends AkkaTag[Poly]

trait AkkaKirito {

  trait AkkaInjectModel[Out, Data] extends DecoderContent[Out, Data] {
    def model(implicit classTag: ClassTag[Data]): Props
  }

  object akkaKirito extends DecoderWrapperHelper[AkkaTag[AkkaImplicit], (Any, Any), AkkaInjectModel] {
    override def effect[Rep, D, Out](rep: Rep)(implicit shape: Aux[Rep, D, Out, AkkaTag[AkkaImplicit], (Any, Any)]): AkkaInjectModel[Out, D] = {
      val shape1 = shape
      new AkkaInjectModel[Out, D] {
        override def model(implicit classTag: ClassTag[D]): Props =
          Props(classTag.runtimeClass, shape1.buildRep(shape1.wrapRep(rep), new AkkaTagImpl(List.empty)).param: _*)
      }
    }
  }

}
