package com.iravid.k8s

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import org.apache.logging.log4j.scala.Logging
import scala.io.Source

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
          complete(StatusCodes.OK)
        }
      },
      path("name") {
        complete(s"Hello from ${System.getenv("POD_NAME")}")
      },
      path("print-env") {
        complete(System.getenv("ENABLE_EMAILAGE"))
      },
      path("print-envfrom") {
        complete(System.getenv("ENV_FROM_ENABLE_ENRICHMENT"))
      },
      path("print-file") {
        complete(Source.fromFile("/app/config/APPCONF").getLines().mkString("\n"))
      }
    )
}
