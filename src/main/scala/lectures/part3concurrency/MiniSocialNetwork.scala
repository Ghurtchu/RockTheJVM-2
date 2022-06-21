package lectures.part3concurrency

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object MiniSocialNetwork {

  case class Profile(id: String, name: String):
    def poke(anotherProfile: Profile): Unit = println(s"${this.name} poking ${anotherProfile.name}")

  object SocialNetwork:
    // "database" as a simple map
    val names: Map[String, String] = Map(
      "fb.id.1-zuck" -> "Mark",
      "fb.id.1-bill" -> "Bill",
      "fb.id.0-dummy" -> "Dummy",
    )

    val friends: Map[String, String] = Map(
      "fb.id.1.-zuck" -> "fb.id.2-bill"
    )

    // API - simulation of fetching profile from db
    def fetchProfile(id: String): Future[Profile] = Future {
      Thread.sleep(scala.util.Random.nextInt(300))

      Profile(id, names(id))
    }

    def fetchFriend(profile: Profile): Future[Profile] = Future {
      Thread.sleep(scala.util.Random.nextInt(400))
      val bfId = friends(profile.id)

      Profile(bfId, names(bfId))
    }

    // client: mark to poke bill

    // nested futures
    val mark: Future[Profile] = SocialNetwork.fetchProfile("fb.id.1-zuck")
    mark.onComplete {
      case scala.util.Success(m) => {
        val bill: Future[Profile] = SocialNetwork.fetchFriend(m)
        bill.onComplete {
          case scala.util.Success(b) => m.poke(b)
          case _ => println("failure")
        }
      }
      case _ => println("failure")
    }

    // better ways to do the same
    // using: functional composition namely map, flatMap and filter

    val nameOnTheWall: Future[String] = mark.map(_.name)

    val marksBestFriend: Future[Profile] = mark.flatMap { prof =>
      Future(prof)
    }

    marksBestFriend.filter { prof =>
      prof.name.startsWith("Z")
    }

    // for comprehensions

    val bill: Future[Profile] = for {
      mark <- SocialNetwork.fetchProfile("fb.id.1-zuck")
      bill <- SocialNetwork.fetchFriend(mark)
    } yield bill

    Thread sleep 1000

    // fallbacks
    // example: recovering

    val a: Future[Profile] = SocialNetwork.fetchProfile("unknown_id").recover {
      case e: Throwable => Profile("dummy_id", "dummy_name")
    } // turned into the Total Function

    val aFetchedProfileNoMatterWhat: Future[Profile] = SocialNetwork.fetchProfile("unknown id").recoverWith {
      case e: Throwable => SocialNetwork.fetchProfile("fb.id.0-dummy")
    }

    val fallBackResult: Future[Profile] = SocialNetwork.fetchProfile("fb.id.0-dummy").fallbackTo {
      SocialNetwork.fetchProfile("fb.id.0-dummy")
    }
}

