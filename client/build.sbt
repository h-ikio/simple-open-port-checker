// Include only src/main/java in the compile configuration
unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(_ :: Nil)

// Include only src/test/java in the test configuration
unmanagedSourceDirectories in Test <<= (scalaSource in Test)(_ :: Nil)