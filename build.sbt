organization in ThisBuild := "com.github.sonac"

scalaVersion in ThisBuild := "2.12.8"

lazy val root = project.in(file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "imdb-scrapper",
    version := "1.0.1",
    libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "2.1.0"
  )

lazy val commonSettings = Seq(
  credentials += Credentials(Path.userHome / ".sbt" / "sonatype_credentials"),
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
    else Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  licenses := Seq("MIT License" -> url("http://www.opensource.org/licenses/mit-license.php")),
  homepage := Some(url("https://github.com/sonac/imdb-scrapper")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/sonac/imdb-scrapper"),
      "scm:git@github.com:sonac/imdb-scrapper.git"
    )
  ),
  developers := List(
    Developer(
      id    = "sonac",
      name  = "Andrii",
      email = "sonag007@gmail.com ",
      url   = url("http://github.com/sonac")
    )
  )
)