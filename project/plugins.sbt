logLevel := Level.Warn

resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"

resolvers += Classpaths.sbtPluginReleases

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.12.0")

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.6.0")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.0.4")