import java.io.{BufferedWriter, File, FileWriter}

import org.apache.poi.ss.usermodel.{Sheet, WorkbookFactory}

object IOControler {
  def readXLSXFile(pathName: String): Sheet = {
    val file = new File(pathName)
    val workbook = WorkbookFactory.create(file)
    workbook.getSheetAt(0)
  }

  def writeToFile(filePath: String, fileName: String, data:String): Unit = {
    val file = new File(filePath + fileName)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(data)
    bw.close()
  }
}
