package lectures.part1as

object GveshvelebaRame {

  case class Mosazreba(value: String)

  sealed trait MosazrebaQartvelebze {
    def mosazreba: Mosazreba
  }

  extension (str: String) def rogorcMosazreba: Mosazreba = Mosazreba(str)

  object MosazrebaQartvelebze {
    def apply(moazrovne: Moazrovne): MosazrebaQartvelebze = moazrovne match {
      case TemurAlasania => Sidzulvili
      case Qoci          => MeDebiliVarDaAzrovnebaArShemidzlia
      case _             => Siyvaruli
    }
  }

  case object Siyvaruli extends MosazrebaQartvelebze {
    override def mosazreba: Mosazreba = "me vgijdebi qartvelebze!".rogorcMosazreba
  }

  case object Sidzulvili extends MosazrebaQartvelebze {
    override def mosazreba: Mosazreba = "me mdzuls qartvelebi".rogorcMosazreba
  }

  case object MeDebiliVarDaAzrovnebaArShemidzlia extends MosazrebaQartvelebze {
    override def mosazreba: Mosazreba = "me debili var da azrovneba ar shemidzlia".rogorcMosazreba
  }

  trait AqvsAzriQartvelebze {
    def mosazrebaQartvelebze: MosazrebaQartvelebze
  }

  abstract class Moazrovne extends AqvsAzriQartvelebze

  case object TemurAlasania extends Moazrovne {
    override def mosazrebaQartvelebze: MosazrebaQartvelebze = Sidzulvili
  }

  case object NebismieriAntiQoci extends Moazrovne {
    override def mosazrebaQartvelebze: MosazrebaQartvelebze = Siyvaruli
  }

  case object Qoci extends Moazrovne {
    override def mosazrebaQartvelebze: MosazrebaQartvelebze = MeDebiliVarDaAzrovnebaArShemidzlia
  }

  sealed trait XvalindeliAqciisShedegi {
    def shedegi: String
  }

  sealed trait XalxisRaodenoba

  case object Bevri extends XalxisRaodenoba
  case object Cota extends XalxisRaodenoba

  object XvalindeliAqciisShedegi {
    def apply(xalxisRaodenoba: XalxisRaodenoba): XvalindeliAqciisShedegi = xalxisRaodenoba match {
      case Cota  => new XvalindeliAqciisShedegi:
        override def shedegi: String = "Temur alasania martali iyo"
      case Bevri => new XvalindeliAqciisShedegi:
        override def shedegi: String = "Temur alasania scdeboda qartvelebis shesaxeb"
    }
  }

}


