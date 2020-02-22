package com.iravid.k8s

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import org.apache.logging.log4j.scala.Logging

object Main extends Logging {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("k8s-workshop")
    implicit val mat = ActorMaterializer()
    implicit val ec = system.dispatcher

    val threshold = System.currentTimeMillis() + 30*1000
    val binding = Http().bindAndHandle(routes(threshold), "0.0.0.0", 8080)

    sys.addShutdownHook {
      binding
        .flatMap(_.unbind())
        .onComplete(_ => system.terminate())
    }
  }

  def routes(threshold: Long) =
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
          complete(StatusCodes.OK)
        }
      },
      path("name") {
        complete(s"Hello from ${System.getenv("POD_NAME")}")
      },
      path("ready") {
        if (System.currentTimeMillis >= threshold) 
          complete(StatusCodes.OK)
        else
          complete(StatusCodes.InternalServerError)
      }
    )
}
