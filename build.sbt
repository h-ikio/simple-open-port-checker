lazy val root =
  (project in file(".")).settings(
    scalaSource := file(""),
    javaSource := file(""),
    assembleArtifact in assembly := false,
    fullClasspath := Seq()
  ) aggregate(server, client)

lazy val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "me.ikio",
  scalaVersion := "2.11.4",
  libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.3.7",
  fork in Test := true
)

lazy val server =
	(project in file("server")).
    settings(commonSettings: _*).
    settings(
      name := "sopc-server",
      mainClass in assembly := Some("me.ikio.sopc.server.Main"),
      assemblyJarName in assembly := "sopc-server.jar"
    )

lazy val client =
  (project in file("client")).
    settings(commonSettings: _*).
    settings(
      name := "sopc-client",
      mainClass in assembly := Some("me.ikio.sopc.client.Main"),
      assemblyJarName in assembly := "sopc-client.jar"
    )
