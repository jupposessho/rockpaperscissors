package rockpaperscissors.lib

import rockpaperscissors.lib.IO

object ProgramIO extends Program[IO] {

  def finish[A](a: => A): IO[A] = IO.point(a)

  def chain[A, B](fa: IO[A], afb: A => IO[B]): IO[B] = fa.flatMap(afb)

  def map[A, B](fa: IO[A], ab: A => B): IO[B] = fa.map(ab)
}
