object ObjectOriented {
  def main(args : Array[String]) : Unit = {
    val facture1 = SchemaFacture("BK34", "20/10/2021", "Juvenal", 3500)
    println(facture1.factureId)

    val commande1 = DetailFacture(facture1, "KI897C", 1, 800)
    val commande2 = DetailFacture(SchemaFacture("BK34", "20/10/2021", "SGO", 2500), "KI897C", 2, 2200)

    val commande3 = Commande(facture1, commande1)
    val revenueFacture1 = commande3.revenueCompte(commande3.produitCommande.quantite, commande3.produitCommande.prixUnitaire)

    println(s"total f = ${revenueFacture1}")
  }
}
