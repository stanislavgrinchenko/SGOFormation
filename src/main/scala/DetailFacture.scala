import SchemaFacture._
case class DetailFacture(
                        idFacture : SchemaFacture,
                        idProduct : String,
                        quantite : Int,
                        prixUnitaire : Double
                        )
