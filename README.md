 sbt-slickcodegen
generate slick code by using sbt

In this project root directory, type command <br />
    `sbt publishLocal` <br/>
Then in your own project, adding the following code in **plugins.sbt** 

    addSbtPlugin("com.doddot" % "sbt-slickcodegen" % "1.0") 

In **build.sbt**, adding <br />

    enablePlugins(CodesGen) 
to enable this plugin in root. <br />

Initialize some setting: <br />

    dbCategory := "mysql"
    
    dbUrl := "jdbc:mysql://localhost:3306/test?useOldAliasMetadataBehavior=true&zeroDateTimeBehavior=convertToNull"
    
    dbUser := "root"
    
    dbPassword := ""
    
    codesLayout := Seq((Seq("test"), "src/main/scala", "com.doddot.models", "TestTables", "TestTables.scala"))
The setting key **codesLayout** is very importen, the type is Seq[(Seq[String], String, String, String, String)]

The tuple in seq is a layout to indicate how to put codes generated for some tables in one source file.

1. The first element is a seq to list the tables name. 
1. The second element is a string to define the location to put the code. 
1. The third element is a string to define the package name. 
1. The fourth element is a string to define a cotainer name in source code. 
1. The fifth element is a string to define the name of source file generated.

The last step is to generate the code: in your own project directory, type command

    sbt genCode
