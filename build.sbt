name := "empty scala 3"

version := "0.1"

scalaVersion := "3.3.7"

scalacOptions ++= Seq("-source:future", "-indent", "-rewrite")

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"

wartremoverErrors ++= Warts.allBut(Wart.Any, Wart.Equals, Wart.Nothing,
  Wart.Overloading, Wart.Recursion, Wart.StringPlusAny,
  Wart.ToString, Wart.TripleQuestionMark)
