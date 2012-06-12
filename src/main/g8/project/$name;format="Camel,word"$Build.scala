import sbt._
import Keys._

object $name;format="Camel,word"$Build extends sbt.Build {
  
  lazy val core =
    Project(id = "$name;format="normalize"$-core",
            base = file("."),
            settings = Project.defaultSettings ++ Seq(
              organization := "$organization$",
              version := "$version$",
              scalaVersion := "2.9.2",
              crossScalaVersions := Seq("2.8.1", "2.9.0", "2.9.0-1", "2.9.1"),
              initialCommands := "import $organization$.$name;format="normalize,word"$._",
              resolvers += "Scala-Tools Maven2 Snapshots Repository" at "http://scala-tools.org/repo-snapshots",
              libraryDependencies <++= scala"0.6.2" (v => Seq(
                "net.databinder" %% "unfiltered-filter" % "0.6.2",
                "net.databinder" %% "unfiltered-json" % "0.6.2",
                "net.databinder" %% "unfiltered-jetty" % "0.6.2",
                Shared.specsDep(v):_*
            ))))
}

object Shared {
  
  /** Resolve specs version for the current scala version (thanks @n8han). */
  def specsDep(sv: String, cfg: String = "test") =
    (sv.split("[.-]").toList match {
      case "2" :: "8" :: "1" :: Nil =>
        "org.specs2" %% "specs2" % "1.5" ::
        "org.specs2" %% "specs2-scalaz-core" % "5.1-SNAPSHOT" ::
        Nil
      case "2" :: "9" :: "0" :: _ => "org.specs2" % "specs2_2.9.1" % "1.7.1" :: Nil
      case "2" :: "9" :: _ :: _ => "org.specs2" % "specs2_2.9.1" % "1.8.2" :: Nil
      case _ => sys.error("Specs not supported for scala version %s" format sv)
    }) map (_ % cfg)
  
}
