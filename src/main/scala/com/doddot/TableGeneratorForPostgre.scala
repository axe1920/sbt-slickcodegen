package com.doddot

import slick.codegen.SourceCodeGenerator
import slick.model.Model

class TableGeneratorForPostgre(model: Model) extends SourceCodeGenerator(model){
  override def code = "import java.time.ZonedDateTime\n" + "import java.time.LocalTime\n" + "import java.time.LocalDate\n" + super.code


  override def Table = new Table(_) {
    override def Column = new Column(_) {

      // munge rawType -> SQL column type HERE (scaladoc in Slick 2.1.0 is outdated or incorrect, GeneratorHelpers#mapJdbcTypeString does not exist)
      // you can filter on model.name for the column name or model.tpe for the column type
      // your IDE won't like the String here but don't worry, the return type the compiler expects here is String
      override def rawType = model.tpe match {
        case "java.sql.Timestamp"               => "ZonedDateTime" // kill j.s.Timestamp
        case "java.sql.Time"       => "LocalTime"
        case "java.sql.Date"       => "LocalDate"
        case _ => {
//          println(s"${model.table.table}#${model.name} tpe=${model.tpe} rawType=${super.rawType}")
          super.rawType
        }
      }
    }
  }
}
