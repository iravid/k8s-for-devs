ThisBuild / scalaVersion     := "2.12.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.iravid"
ThisBuild / organizationName := "k8s"

lazy val root = (project in file("."))
  .settings(
    name := "K8s Workshop",
    libraryDependencies ++= Seq(
      "io.circe"                         %% "circe-parser"           % "0.12.3",
      "io.circe"                         %% "circe-generic"          % "0.12.3",
      "com.typesafe.akka"                %% "akka-stream"            % "2.5.26",
      "com.typesafe.akka"                %% "akka-http"              % "10.1.10",
      "de.heikoseeberger"                %% "akka-http-circe"        % "1.29.1",
      "io.prometheus"                    % "simpleclient"            % "0.6.0",
      "io.prometheus"                    % "simpleclient_hotspot"    % "0.6.0",
      "io.prometheus"                    % "simpleclient_httpserver" % "0.6.0",
      "org.apache.logging.log4j"         % "log4j-api"               % "2.11.0",
      "org.apache.logging.log4j"         % "log4j-core"              % "2.11.0",
      "org.apache.logging.log4j"         %% "log4j-api-scala"        % "11.0",
      "org.apache.logging.log4j"         % "log4j-slf4j-impl"        % "2.11.0",
      "com.fasterxml.jackson.core"       % "jackson-databind"        % "2.9.10",
      "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % "2.9.10"
    ),
    fork in run := true,
    cancelable in Global := false
  )
