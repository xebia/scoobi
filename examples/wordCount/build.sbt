name := "Scoobi Word Count"

version := "0.1"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
	"com.nicta" %% "scoobi" % "0.2.0" % "provided",
	"org.scalatest" %% "scalatest" % "1.7.1" % "test"
)