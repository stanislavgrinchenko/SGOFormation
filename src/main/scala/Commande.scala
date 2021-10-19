case class Commande(
                   facture : SchemaFacture,
                   produitCommande : DetailFacture
                   )
{
  def revenueCompte(quantite : Int, prixUnitaire: Double) : Double = {
    return quantite * prixUnitaire
  }

  def taxesCompute(tauxtva : Double, totalFacture : Double) : Double = {
    return tauxtva*totalFacture
  }
}
