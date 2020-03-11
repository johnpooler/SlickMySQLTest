
import slick.jdbc.MySQLProfile.api._


class People (tag:Tag) extends Table [(Int,String,Int)] (tag, "PEOPLE"){
  def id=column[Int] ("PER_ID", O.PrimaryKey, O.AutoInc)
  def fName = column[String] ("PER_FNAME")
  def lName = column[Int]("PER_LNAME")
  def age = column[Int]("PER_AGE")

  def * = (id,fName,lName,age)
}
val people = TableQuery[People]

val peopleOver25 = people.filter(person => person.age > 25).map(person =>
  (person.fName, person.lName))
