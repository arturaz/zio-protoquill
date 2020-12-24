package io.getquill

import scala.language.implicitConversions
import io.getquill.quoter.Dsl._
import io.getquill.quoter.QueryDsl._

object MiniExample2_LiftOrAny {
  import io.getquill._
  case class Person(name: String, age: Int)

  val ctx = new MirrorContext(MirrorSqlDialect, Literal)
  import ctx._

  def usingLike() = {
    inline def liftOrAny(inline field: String, inline filter: Option[String]) =
      field.like(lift(filter.getOrElse("%")))

    val runtimeValue = Some("Joe")
    inline def q = quote {
      query[Person].filter(p => liftOrAny(p.name, runtimeValue))
    }

    println( run(q) )
  }

  def usingEqual() = {
    inline def liftOrAny(inline field: String, inline filter: Option[String]) =
      lift(filter.getOrElse(null)) == field || lift(filter.getOrElse(null)) == null

    val runtimeValue = Some("Joe")
    inline def q = quote {
      query[Person].filter(p => liftOrAny(p.name, runtimeValue))
    }

    println( run(q) )
  }

  def main(args: Array[String]): Unit = {
    usingLike()
    usingEqual() //hello
  }
}
