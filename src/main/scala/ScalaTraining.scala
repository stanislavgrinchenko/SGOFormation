import java.util.Properties
//import scala.collection.immutable._
//import scala.collection.mutable._

object ScalaTraining {
  def main(args: Array[String]) : Unit = {
    val mavar : String = "hello world"
    println(mavar)

    extractFirst(mavar, longeur = 3)

    val newText : String = extractFirstFunction(mavar, longueur = 3)

    println(newText)

    var i = 0

    while(i<=10) {
      println(s"la valeur de ${i}")
      i = i + 1
    }

    for(i<-1 to 10){
      println(s"la valeur de ${i}")
    }

    val maListe : List[String] = List("juv", "tayl", "adr", "brun")
    maListe.foreach(e => e.substring(1,2))
    maListe.foreach(e => println(s"list element : ${e}"))

    maListe.foreach(
      e =>{
        val t = e.substring(1,2)
        val result = "15" + t
        println(result)
    })

    val maliste2 = maListe.map(m => m.substring(1,2))
    maliste2.foreach {
      e => println(e)
    }

    val maListe3 = maliste2.filter(f => f == "a")
    maListe3.foreach(e => println(e))

    val intList : List[Int] = List(10, 40, 80, 90, 4)
    val intList2 = intList.map(_*3)

    intList2.foreach(e => println(e))

    val tp : (String, Int, Boolean) = ("juvenal", 10, true)

    val map1: Map[String, String] = Map("pays" -> "France", "pays" -> "Cameroun", "pays" -> "Canada")
    val map2: Map[String, Int] = Map("distance" -> 100, "distance" -> 250, "distance" -> 350)
    val map3: Map[String, List[String]] = Map("villes" -> List("Paris", "Tokyo", "New York"), "pays" -> List("France", "Canada", "Ukraine"))

    map1.keys.foreach(k => println(s"clé de mapl : ${k}"))

    map3.values.foreach(l=>{
      l.foreach(e=>println(s"valeur de mon map3: ${e}"))
    }
    )

    val monTableau: Array[String] = Array("juvenal", "boubdalla", "bruno", "vincent", "taylor")
    //val listeTableau = monTableau.toList()


  }


  def extractFirst(texte : String, longeur : Int) : Unit = {
    var resultat = texte.substring(longeur)
    println("votre texte extrait est : "+  resultat)
  }

   def extractFirstFunction(texte : String, longueur : Int) : String = {
    val resultat = texte.substring(longueur)
    return resultat
  }
}
