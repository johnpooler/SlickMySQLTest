//import slick.dbio.DBIO
//import slick.lifted.{Query, TableQuery}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.ExecutionContext.Implicits.global



object Main extends App {
  val db = Database.forConfig("mysqlDB")

  val peopleTable = TableQuery[People]

  val dropPeopleCmd = DBIO.seq(peopleTable.schema.drop)

  val initPeopleCmd = DBIO.seq(peopleTable.schema.create)



  def dropDB = {
    val dropFuture = Future {
      db.run(dropPeopleCmd)}

    Await.result(dropFuture, Duration.Inf).andThen {
      case Success(_) => initialisePeople
      case Failure(error) => println("dropping the table failed due to" + error.getMessage)
        initialisePeople

    }
  }

  def initialisePeople = {
    val setupFuture =  Future {
      db.run(initPeopleCmd)
    }

  Await.result(setupFuture, Duration.Inf).andThen{
    case Success(_) => runQuery
  case Failure(error) => println("Initialising the table failed due to: " + error.getMessage)    }}




  def runQuery={
    val insertPeople=Future{
      val query= peopleTable ++= Seq(
        (10, "Jack", "Brownie", 47),
        (12, "jeniflor", "Lowpes", 55))
      println(query.statements.head)
      db.run(query)
    }
    Await.result(insertPeople, Duration.Inf).andThen {
      case Success(_) => runQuery
      case Failure(error) => println("dropping the table failed due to" + error.getMessage)
    }
    }


  def listPeople = {
    val queryFuture = Future {
      // simple query that selects everything from People and prints them out
      db.run(peopleTable.result).map(_.foreach {
        case (id, fName, lName, age) => println(s" $id $fName $lName $age")
      })
    }
    Await.result(queryFuture, Duration.Inf).andThen {
      case Success(_) =>  db.close()  //cleanup DB connection
      case Failure(error) =>
        println("Listing people failed due to: " + error.getMessage)
    }
  }


  dropDB
  Thread.sleep(3000)
    }
