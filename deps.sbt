resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http"         % "10.1.3"
  , "com.typesafe.akka" %% "akka-http-testkit" % "10.1.3"
  , "com.typesafe.akka" %% "akka-stream"       % "2.5.12"
)

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.25" % Test