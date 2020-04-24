package com.doddot

import sbt._
import slick.driver.JdbcProfile
import Keys._
import slick.codegen.SourceCodeGenerator

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
/**
  * Created by Ronald on 2016/8/19.
  */
object CodesGen extends AutoPlugin{

  object autoImport{
    //val slickDriver = settingKey[String]("slick driver")
    //val jdbcDriver = settingKey[String]("jdbc driver")
    val dbUrl = settingKey[String]("db url")
    val dbUser = settingKey[String]("db user")
    val dbPassword = settingKey[String]("db password")
    val dbCategory = settingKey[String]("db category")
    val genCode = taskKey[Seq[String]]("auto generate codes")
    val codesLayout = settingKey[Seq[(Seq[String], String, String, String, String)]]("codes layout")
  }
  import autoImport._
  lazy val baseSettings: Seq[Setting[_]] = Seq(
    dbUrl := "",
    dbUser := "",
    dbPassword := "",
    dbCategory := "",
    codesLayout := Nil,
    genCode := {
      val (slickDriver,driver,jdbcDriver) = dbCategory.value match {
        case "Derby" => ("slick.driver.DerbyDriver",slick.jdbc.DerbyProfile, "org.apache.derby.jdbc.ClientDriver")
        case "H2" => ("slick.driver.H2Driver", slick.jdbc.H2Profile, "org.h2.Driver")
        case "PostgreSQL" => ("slick.driver.PostgresDriver", slick.jdbc.PostgresProfile, "org.postgresql.Driver")
        case _ => ("slick.driver.MySQLDriver", slick.jdbc.MySQLProfile, "com.mysql.jdbc.Driver")
      }
      def filterTables(includes: Seq[String]) = {
        driver.defaultTables.map(_.filter(t => includes.contains(t.name.name))).flatMap(driver.createModelBuilder(_, false).buildModel)
      }
      val db = driver.api.Database.forURL(dbUrl.value, driver = jdbcDriver, user = dbUser.value, password = dbPassword.value)

      if (codesLayout.value.isEmpty){
        new TableGenerator(Await.result(db.run(driver.createModel()), Duration.Inf)).writeToFile(slickDriver, "src/main/scala", "models")
        Seq("Tables.scala")
      } else {
        for (t <- codesLayout.value) yield {
          val model = if (t._1.isEmpty) driver.createModel() else filterTables(t._1)
          if (dbCategory.value == "mysql") {
            new TableGenerator(Await.result(db.run(model), Duration.Inf)).writeToFile(slickDriver, t._2, t._3, t._4, t._5)
          }else if (dbCategory.value == "PostgreSQL"){
            new TableGeneratorForPostgre(Await.result(db.run(model), Duration.Inf)).writeToFile(slickDriver, t._2, t._3, t._4, t._5)
          } else {
            new SourceCodeGenerator(Await.result(db.run(model), Duration.Inf)).writeToFile(slickDriver, t._2, t._3, t._4, t._5)

          }
          t._5
        }
      }
    }
  )

  override def projectSettings: Seq[_root_.sbt.Def.Setting[_]] = baseSettings
}
