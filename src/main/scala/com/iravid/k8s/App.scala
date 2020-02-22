package com.iravid.k8s

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import org.apache.logging.log4j.scala.Logging

import scala.util.Random

object Main extends Logging {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("k8s-workshop")
    implicit val mat = ActorMaterializer()
    implicit val ec = system.dispatcher

    val binding = Http().bindAndHandle(routes, "0.0.0.0", 8080)

    sys.addShutdownHook {
      binding
        .flatMap(_.unbind())
        .onComplete(_ => system.terminate())
    }
  }

  val routes =
    concat(
      pathSingleSlash {
        get {
          complete {
            logger.info("Received request")
            "Hello Kubernetes Workshop!"
          }
        }
      },
      path("health") {
        get {
          val random = Random.nextInt(100) + 1

          if (random <= 50) complete(StatusCodes.InternalServerError)
          else complete(StatusCodes.OK)
        }
      }
    )
}
