package lectures.part3concurrency

import scala.concurrent.Future
import scala.concurrent.Promise
import scala.concurrent.ExecutionContext.Implicits.global

object FutureExercises extends scala.App {

  // 1 - fulfill a future immediately with a value
  // 2 - inSequence(fut[A], fut[B]) two futures in sequence
  // 3 - first(fut[A], fut[B]) => get the first future out of two parallel futures

  // 1
  def fulfillImmediately[T](value: T): Future[T] = Future(value)

  // 2
  def inSequence[A, B](first: Future[A], second: Future[B]): Future[B] =
    first.flatMap(_ => second)

  // 3
  def first[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val promise = Promise[A]

    fa.onComplete(promise.tryComplete)
    fb.onComplete(promise.tryComplete)

    promise.future
  }

}

