package eu.stelliant.sinapps.module.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import eu.stelliant.model.DataTypes;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ApiDataTypesTest extends TestApiSetup {

  @Autowired
  ObjectMapper objectMapper;

  @Test
  public void date_() {

    OffsetDateTime offsetDateTime = OffsetDateTime
        .of(LocalDateTime.parse("2015-12-05T10:57:47"), ZoneOffset.of("+0200"));
    assertThat(offsetDateTime).toString().equals("2015-12-05T10:57:47+0200");
  }

  @Test
  public void date_colon() {

    OffsetDateTime offsetDateTime = OffsetDateTime
        .of(LocalDateTime.parse("2015-12-05T10:57:47"), ZoneOffset.of("+02:00"));
    assertThat(offsetDateTime).toString().equals("2015-12-05T10:57:47+02:00");
  }


  @Test
  public void date_should_throw_jackson_InvalidFormatException() {

    // Given
    String jsonDataTypes = "{"
        + "\"dateType\": \"2018-05-17T10:57:47+02:00\""
        + "}";

    // When
    Throwable exception = catchThrowable(() -> objectMapper
        .readValue(jsonDataTypes, DataTypes.class));

    // Then
    assertThat(exception).isInstanceOf(InvalidFormatException.class)
        .hasMessageContaining("Cannot deserialize value of type `java.time.OffsetDateTime`");
  }

  @Test
  public void date_should_be_parsed() throws IOException {

    // Given
    String jsonDataTypes = "{"
        + "\"dateType\": \"2018-05-17T10:57:47+0200\""
        + "}";

    // When
    DataTypes dataTypes = objectMapper.readValue(jsonDataTypes, DataTypes.class);

    // Then
    assertThat(dataTypes.getDateType().getOffset().toString()).isEqualTo("+02:00");
  }

  @Test
  public void array_should_throw_jackson_MismatchedInputException() {

    // Given
    String jsonDataTypes = "{\"arrayType\": {"
        + " \"/assureurId\": {"
        + "   \"type\": {"
        + "     \"array\": {"
        + "       \"type\": \"BSONObjectID\""
        + "     }"
        + "   },"
        + "   \"optional\": true"
        + " },"
        + " \"/prestataireId\": {"
        + "   \"type\": {"
        + "     \"array\": {"
        + "       \"type\": \"BSONObjectID\""
        + "     }"
        + "   },"
        + "   \"optional\": true"
        + " }"
        + "}";

    // When
    Throwable exception = catchThrowable(() -> objectMapper
        .readValue(jsonDataTypes, DataTypes.class));

    // Then
    assertThat(exception).isInstanceOf(MismatchedInputException.class)
        .hasMessageContaining("Cannot deserialize instance of `java.util.ArrayList`");
  }

  @Test
  public void array_should_be_parsed() throws IOException {

    // Given
    String jsonDataTypes = "{\"arrayType\": ["
        + " {"
        + "   \"/assureurId\": {"
        + "     \"type\": {"
        + "       \"array\": {"
        + "         \"type\": \"BSONObjectID\""
        + "       }"
        + "     },"
        + "     \"optional\": true"
        + "   }"
        + " }, {"
        + "   \"/prestataireId\": {"
        + "     \"type\": {"
        + "       \"array\": {"
        + "         \"type\": \"BSONObjectID\""
        + "       }"
        + "     },"
        + "     \"optional\": true"
        + "   }"
        + " }]"
        + "}";

    // When
    DataTypes dataTypes = objectMapper.readValue(jsonDataTypes, DataTypes.class);

    // Then
    assertThat(dataTypes.getArrayType().size()).isEqualTo(2);
  }

  @Test
  public void partenaire_should_be_parsed() throws IOException {

    // Given
    String jsonDataTypes = "{\"href\":\"/core/api/partenaire/partenaires/5afd440b20000017014fe8ef\",\"properties\":{\"numeroClient\":\"28953\",\"nom\":\"TEXA Point d'entree unique\",\"typePartenaire\":{\"name\":\"Expert\",\"label\":\"Expert\"},\"adresse\":{\"adresse1\":\"adresse 1\",\"adresse2\":\"adresse 2\",\"codePostal\":\"18000\",\"localite\":\"BOURGES\",\"codePays\":\"FR\"},\"agrement\":[{\"name\":\"Covea\",\"label\":\"Covea\"}],\"telephone\":\"0549494949\",\"agreePar\":[\"5afd3cfa110000640069ea88\",\"5afd3cfa110000640069ea83\",\"5afd3cfa1100008b0069ea86\",\"5afd3cfa1100008b0069ea78\",\"5afd3cfa1100008b0069ea7e\",\"5afd3cfa110000650069ea7b\",\"5afd3cfa110000650069ea80\"],\"id\":\"5afd440b20000017014fe8ef\",\"version\":0,\"dateDeCreation\":\"2018-06-15T17:36:03+0200\",\"dateDeDerniereModification\":\"2018-06-15T17:36:03+0200\"},\"links\":[{\"rel\":\"commands\",\"href\":\"/core/api/partenaire/partenaires/5afd440b20000017014fe8ef/commands?referer=commands\"},{\"rel\":\"events\",\"href\":\"/core/api/partenaire/partenaires/5afd440b20000017014fe8ef/events?referer=events\"},{\"rel\":\"linked-events\",\"href\":\"/core/api/partenaire/partenaires/5afd440b20000017014fe8ef/linkedEvents?referer=linked-events\"},{\"rel\":\"eventsNotCreatedByMe\",\"href\":\"/core/api/partenaire/partenaires/5afd440b20000017014fe8ef/othersEvents?referer=eventsNotCreatedByMe\"},{\"rel\":\"expertMissions\",\"href\":\"/core/api/covea-expert/missions?prestataireId=5afd440b20000017014fe8ef&referer=expertMissions\"},{\"rel\":\"abstractMissions\",\"href\":\"/core/api/common/missions?prestataireId=5afd440b20000017014fe8ef&referer=abstractMissions\"},{\"rel\":\"users\",\"href\":\"/core/api/partenaire/users?partenaireId=5afd440b20000017014fe8ef&referer=users\"},{\"rel\":\"profilPublic\",\"href\":\"/core/api/partenaire/partenaires/5afd440b20000017014fe8ef/public?referer=profilPublic\"},{\"rel\":\"partenairesAvecAgrement\",\"href\":\"/core/api/partenaire/partenaires/5afd440b20000017014fe8ef/partenairesAvecAgrement?referer=partenairesAvecAgrement\"},{\"rel\":\"current\",\"href\":\"/core/api/partenaire/partenaires/5afd440b20000017014fe8ef?referer=current\"},{\"rel\":\"last\",\"href\":\"/core/api/covea/partenaires/5afd440b20000017014fe8ef/versions/0?referer=last\"},{\"rel\":\"first\",\"href\":\"/core/api/covea/partenaires/5afd440b20000017014fe8ef/versions/0?referer=first\"}]}\n";

    // When
    com.darva.sinapps.api.client.transverse.model.RessourcePartenaire ressourcePartenaire = objectMapper
        .readValue(jsonDataTypes,
                   com.darva.sinapps.api.client.transverse.model.RessourcePartenaire.class);

    // Then
    assertThat(ressourcePartenaire.getProperties()).isNotNull();
  }

  @Test
  public void missions_should_be_parsed() throws IOException {

    // Given
    String jsonDataTypes = "{\"href\":\"/core/api/common/missions?prestataireId=5b2cc2f6100000c40284495d&nbPage=1&nbElements=16\",\"properties\":{\"totalNumberOfElements\":2,\"noPage\":1,\"nbElementPerPage\":16,\"nbElements\":2,\"firstPage\":\"/core/api/common/missions?prestataireId=5b2cc2f6100000c40284495d&nbPage=1&nbElements=16\",\"lastPage\":\"/core/api/common/missions?prestataireId=5b2cc2f6100000c40284495d&nbPage=1&nbElements=16\"},\"items\":[{\"href\":\"/core/api/covea-expert/missions/5b2cc7ae130000c8013602df\",\"properties\":{\"id\":\"5b2cc7ae130000c8013602df\",\"assureurId\":\"5b2cb5ad280000600054f674\",\"lieu\":\"NIORT, 79000\",\"departement\":\"79\",\"difficulteBloquante\":false,\"typeMission\":{\"name\":\"ExpertiseCovea\",\"label\":\"Expertise\"},\"pourcentageAvancement\":0,\"prestataireId\":\"5b2cc2f6100000c40284495d\",\"numero\":\"147258370\",\"dateDeMissionnement\":\"2018-05-15T14:40:48+0200\",\"difficulteNonBloquante\":false,\"etapeMission\":{\"name\":\"PrestataireMissionne\",\"label\":\"Prestataire missionné\"},\"cloture\":false,\"nature\":{\"name\":\"Incendie\",\"label\":\"Incendie\"},\"finDeMissionDemandee\":false,\"referenceSinistre\":\"r123455\",\"cause\":{\"name\":\"Foudre\",\"label\":\"Foudre\"},\"assure\":\"M Stéphane KER\",\"etatPrestation\":{\"name\":\"PrestationRecue\",\"label\":\"Prestation reçue\"},\"mandant\":\"covea-expert\",\"dateCreation\":\"2018-06-22T11:55:58+0200\"},\"links\":[]},{\"href\":\"/core/api/covea-expert/missions/5b2cc684130000a2013602cf\",\"properties\":{\"id\":\"5b2cc684130000a2013602cf\",\"assureurId\":\"5b2cb5ad280000600054f674\",\"lieu\":\"NIORT, 79000\",\"departement\":\"79\",\"difficulteBloquante\":false,\"typeMission\":{\"name\":\"ExpertiseCovea\",\"label\":\"Expertise\"},\"pourcentageAvancement\":0,\"prestataireId\":\"5b2cc2f6100000c40284495d\",\"numero\":\"147258370\",\"dateDeMissionnement\":\"2018-05-15T14:40:48+0200\",\"difficulteNonBloquante\":false,\"etapeMission\":{\"name\":\"PrestataireMissionne\",\"label\":\"Prestataire missionné\"},\"cloture\":false,\"nature\":{\"name\":\"Incendie\",\"label\":\"Incendie\"},\"finDeMissionDemandee\":false,\"referenceSinistre\":\"r123454\",\"cause\":{\"name\":\"Foudre\",\"label\":\"Foudre\"},\"assure\":\"M Stéphane KER\",\"etatPrestation\":{\"name\":\"PrestationRecue\",\"label\":\"Prestation reçue\"},\"mandant\":\"covea-expert\",\"dateCreation\":\"2018-06-22T11:51:00+0200\"},\"links\":[]}],\"links\":[{\"rel\":\"firstPage\",\"href\":\"/core/api/common/missions?prestataireId=5b2cc2f6100000c40284495d&nbPage=1&nbElements=16&referer=firstPage\"},{\"rel\":\"lastPage\",\"href\":\"/core/api/common/missions?prestataireId=5b2cc2f6100000c40284495d&nbPage=1&nbElements=16&referer=lastPage\"}],\"filters\":{\"/assureurId\":{\"type\":{\"array\":{\"type\":\"BSONObjectID\"}},\"optional\":true},\"/prestataireId\":{\"type\":{\"array\":{\"type\":\"BSONObjectID\"}},\"optional\":true},\"/typeMissionQuery\":{\"type\":{\"array\":{\"oneOf\":[{\"name\":\"RenDirecte\",\"label\":\"REN Directe\"},{\"name\":\"RenSuiteExpertise\",\"label\":\"REN suite à expertise\"},{\"name\":\"RechercheDeFuite\",\"label\":\"Recherche de fuite\"},{\"name\":\"UrgenceHabitation\",\"label\":\"Urgence Habitation\"},{\"name\":\"ExpertiseCovea\",\"label\":\"Expertise\"}]}},\"optional\":true},\"/etapeMission\":{\"type\":{\"array\":{\"oneOf\":[{\"name\":\"MissionRecue\",\"label\":\"Mission reçue\"},{\"name\":\"RendezVousChiffragePris\",\"label\":\"Rendez-vous chiffrage pris\"},{\"name\":\"ChiffrageValide\",\"label\":\"Chiffrage validé\"},{\"name\":\"TravauxPlanifies\",\"label\":\"Travaux planifiés\"},{\"name\":\"FactureTravauxDeposee\",\"label\":\"Facture de travaux déposée\"},{\"name\":\"FactureGestionDeposee\",\"label\":\"Facture de gestion déposée\"},{\"name\":\"ChiffrageEnAttenteDeValidation\",\"label\":\"Chiffrage en attente de validation\"},{\"name\":\"Annulee\",\"label\":\"Mission annulée\"},{\"name\":\"MissionArretee\",\"label\":\"Mission arrêtée\"},{\"name\":\"MissionTransmise\",\"label\":\"Mission transmise\"},{\"name\":\"MissionAcceptee\",\"label\":\"Mission acceptée\"},{\"name\":\"MissionAnnulee\",\"label\":\"Mission annulée\"},{\"name\":\"MissionRefusee\",\"label\":\"Mission refusée\"},{\"name\":\"TravauxChiffres\",\"label\":\"Travaux chiffrés\"},{\"name\":\"ChiffrageRefuse\",\"label\":\"Chiffrage refusé\"},{\"name\":\"MissionTraitee\",\"label\":\"Mission traitée\"},{\"name\":\"MissionCloseSansResolution\",\"label\":\"Mission close sans résolution\"},{\"name\":\"PrestataireMissionne\",\"label\":\"Prestataire missionné\"},{\"name\":\"ExpertiseInitiee\",\"label\":\"Expertise initiée\"},{\"name\":\"RapportDepose\",\"label\":\"Rapport déposé\"},{\"name\":\"PrestationRecue\",\"label\":\"Prestation reçue\"},{\"name\":\"PrestationQualifiee\",\"label\":\"Prestation qualifiée\"},{\"name\":\"RendezVousPris\",\"label\":\"Rendez-vous pris\"},{\"name\":\"CompteRenduDepose\",\"label\":\"Compte rendu déposé\"},{\"name\":\"ConclusionsDeposees\",\"label\":\"Conclusions déposées\"}]}},\"optional\":true},\"/departement\":{\"type\":{\"array\":{\"type\":\"string\"}},\"optional\":true},\"/cloture\":{\"type\":\"boolean\",\"optional\":true},\"/startDateQuery\":{\"type\":\"DateTime\",\"optional\":true},\"/endDateQuery\":{\"type\":\"DateTime\",\"optional\":true},\"/contentQuery\":{\"type\":\"string\",\"optional\":true}}}\n";

    // When
    com.darva.sinapps.api.client.expertise.model.InlineResponse2001 inlineResponse2001 = objectMapper
        .readValue(jsonDataTypes,
                   com.darva.sinapps.api.client.expertise.model.InlineResponse2001.class);

    // Then
    assertThat(inlineResponse2001).isNotNull();
  }

  @Test
  public void mission_should_be_parsed() throws IOException {

    // Given
    String jsonDataTypes = "{\"href\":\"/core/api/covea-expert/missions/5b2cc7ae130000c8013602df\",\"properties\":{\"dossier\":{\"assureurId\":\"5b2cb5ad280000600054f674\",\"contrat\":{\"numero\":\"12345F\",\"typeContrat\":\"HAB\",\"clausesParticulieres\":\"Clauses\",\"activitesSouscrites\":[\"Activités\"],\"informationsSpecifiques\":\"Infos\",\"franchise\":{\"value\":{\"montant\":110},\"name\":\"Fixe\",\"label\":\"Fixe\"},\"apporteur\":{\"nom\":\"string\",\"prenom\":\"string\",\"adresse\":{\"adresse1\":\"string\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"string\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}},\"professionnel\":false,\"dateSouscription\":\"2010-01-01\",\"dateModification\":\"2010-01-01\",\"indiceSouscription\":\"Indice\",\"dateValeur\":\"2010-01-01\",\"conventionsSouscrites\":\"Convention\",\"numeroAccord\":\"123LK\",\"intermediaire\":{\"referenceInterneSinistre\":\"string\",\"codeOrias\":\"string\",\"personne\":{\"nom\":\"string\",\"prenom\":\"string\",\"civilite\":{\"name\":\"M\",\"label\":\"M\"},\"adresse\":{\"adresse1\":\"string\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"string\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}}},\"garanties\":[{\"id\":\"5b2cc7ae130000c8013602dc\",\"typeGarantie\":{\"name\":\"Incendie\",\"label\":\"Incendie\"},\"typeGarantieComplementaire\":{\"name\":\"Tv\",\"label\":\"Tv\"},\"plafonds\":[{\"objetPlafonne\":{\"name\":\"PlafondDesObjetsDeValeur\",\"label\":\"Plafond des objets de valeur\"},\"valeur\":6700}]},{\"id\":\"5b2cc7ae130000c8013602dd\",\"typeGarantie\":{\"name\":\"Vol\",\"label\":\"Vol\"},\"typeGarantieComplementaire\":{\"name\":\"Tv\",\"label\":\"Tv\"},\"plafonds\":[{\"objetPlafonne\":{\"name\":\"PlafondDesObjetsDeValeur\",\"label\":\"Plafond des objets de valeur\"},\"valeur\":6700}]}],\"sinistralites\":[]},\"risques\":[{\"adresse\":{\"adresse1\":\"35 rue gare\",\"codePostal\":\"79000\",\"localite\":\"NIORT\",\"codePays\":\"33\",\"nomPays\":\"France\"},\"typeRisque\":{\"name\":\"MaisonParticuliere\",\"label\":\"Maison particulière\"},\"usageAssure\":{\"name\":\"ResidencePrincipale\",\"label\":\"Résidence principale\"},\"description\":\"desc risque\",\"mesures\":[{\"typeMesure\":{\"label\":\"Superficie\",\"name\":\"Superficie\",\"value\":{\"unite\":\"m²\",\"unites\":\"m²\"}},\"valeur\":110}],\"risqueConcerne\":true,\"risqueMoins2ans\":false,\"risquesAggraves\":[],\"niveauProtection\":\"Niv\",\"risqueAtypique\":true,\"anneeConstruction\":2010,\"indicateurs\":[{\"typeIndicateur\":{\"name\":\"Alarme\",\"label\":\"Alarme\"},\"valeur\":5}]}],\"sinistre\":{\"reference\":\"r123455\",\"caracteristiques\":{\"nature\":{\"name\":\"Incendie\",\"label\":\"Incendie\"},\"cause\":{\"name\":\"Foudre\",\"label\":\"Foudre\"},\"detail\":{\"name\":\"ChuteDirecte\",\"label\":\"Chute directe\"}},\"date\":\"2018-05-15\",\"heure\":\"10:00:00\",\"adresse\":{\"adresse1\":\"23 rue Croix\",\"codePostal\":\"79000\",\"localite\":\"NIORT\",\"codePays\":\"33\",\"nomPays\":\"France\"},\"conformiteSinistre\":{\"sinistreConforme\":true},\"dommagesConstates\":\"Dommages\",\"circonstances\":\"Circonstances\",\"nonSuppressionDeCause\":{\"motif\":{\"name\":\"EnCoursDeReparation\",\"label\":\"En cours de réparation\"},\"precision\":\"Réparation en cours\"},\"piecesEndommagees\":[],\"vitresEndommagees\":[],\"rappelMesuresConservatoires\":\"rappel\"},\"acteurs\":[{\"informationsAssure\":{\"profession\":\"Electricien\",\"particulariteClient\":{\"name\":\"VIP\",\"label\":\"VIP\"},\"precisionParticulariteClient\":\"Batîment\",\"situationComptable\":\"En cours\"},\"id\":\"5b2cc7ae130000c8013602de\",\"personne\":{\"nom\":\"KER\",\"prenom\":\"Stéphane\",\"civilite\":{\"name\":\"M\",\"label\":\"M\"},\"adresse\":{\"adresse1\":\"3 Rue des Ajoncs\",\"codePostal\":\"79000\",\"localite\":\"ECHIRE\",\"codePays\":\"33\",\"nomPays\":\"France\"},\"coordonnees\":{\"telPersonnel\":\"0105060402\",\"telProfessionnel\":\"0305060205\",\"telPortable\":\"0609212125\",\"email\":\"annie.barreau@libertysurf.fr\"}},\"references\":[{\"typeReference\":{\"name\":\"ReferenceSinistre\",\"label\":\"Référence sinistre\"},\"valeur\":\"toto\"}],\"archive\":false,\"representants\":[{\"qualite\":{\"name\":\"ConjointDeLAssure\",\"label\":\"Conjoint de l'assuré\"},\"personne\":{\"nom\":\"KER\",\"prenom\":\"Nath\",\"civilite\":{\"name\":\"Mme\",\"label\":\"Mme\"},\"adresse\":{\"adresse1\":\"25 rue moi\",\"codePostal\":\"79000\",\"localite\":\"AIFFRES\",\"codePays\":\"33\",\"nomPays\":\"France\"},\"coordonnees\":{\"telPortable\":\"0603363636\",\"email\":\"annie.barreau@libertysurf.fr\"}},\"informationsComplementaires\":\"Dispo entre 12 et 14h\"}],\"typeActeur\":{\"name\":\"Assure\",\"label\":\"Assuré\"},\"relationAuRisque\":{\"name\":\"Locataire\",\"label\":\"Locataire\"},\"responsableSinistre\":false,\"misEnCause\":false,\"lese\":false,\"_specificites\":[]}],\"franchiseApplicable\":{\"value\":{\"montant\":110},\"name\":\"Fixe\",\"label\":\"Fixe\"}},\"mission\":{\"prestataireId\":\"5b2cc2f6100000c40284495d\",\"assureurId\":\"5b2cb5ad280000600054f674\",\"dossierId\":\"5b2cc7ae130000c8013602db\",\"dateDeMissionnement\":\"2018-05-15T14:40:48.556+02:00[Europe/Paris]\",\"numero\":\"147258370\",\"commentaires\":[],\"statut\":{\"name\":\"Ouverte\",\"label\":\"Ouverte\"},\"etape\":{\"name\":\"PrestataireMissionne\",\"label\":\"Prestataire missionné\"},\"typeMission\":{\"name\":\"ExpertiseCovea\",\"label\":\"Expertise\"},\"TBD\":\"A Definir\"},\"id\":\"5b2cc7ae130000c8013602df\",\"version\":2,\"dateDeCreation\":\"2018-06-22T11:55:58+0200\",\"dateDeDerniereModification\":\"2018-06-22T11:55:58+0200\"},\"links\":[{\"rel\":\"commands\",\"href\":\"/core/api/covea-expert/missions/5b2cc7ae130000c8013602df/commands?referer=commands\"},{\"rel\":\"events\",\"href\":\"/core/api/covea-expert/missions/5b2cc7ae130000c8013602df/events?referer=events\"},{\"rel\":\"linked-events\",\"href\":\"/core/api/covea-expert/missions/5b2cc7ae130000c8013602df/linkedEvents?referer=linked-events\"},{\"rel\":\"eventsNotCreatedByMe\",\"href\":\"/core/api/covea-expert/missions/5b2cc7ae130000c8013602df/othersEvents?referer=eventsNotCreatedByMe\"},{\"rel\":\"piecesJointes\",\"href\":\"/core/api/pieceJointe/piecesJointes?missionId=5b2cc7ae130000c8013602df&archived=false&referer=piecesJointes\"},{\"rel\":\"synthese\",\"href\":\"/core/api/covea-expert/synthese?missionId=5b2cc7ae130000c8013602df&referer=synthese\"},{\"rel\":\"historique\",\"href\":\"/core/api/covea-expert/historique?missionId=5b2cc7ae130000c8013602df&referer=historique\"},{\"rel\":\"prestations\",\"href\":\"/core/api/covea-expert/prestation?missionId=5b2cc7ae130000c8013602df&referer=prestations\"},{\"rel\":\"difficultes\",\"href\":\"/core/api/covea-expert/difficulte?missionId=5b2cc7ae130000c8013602df&referer=difficultes\"},{\"rel\":\"chiffrages\",\"href\":\"/core/api/covea-expert/chiffrages?missionId=5b2cc7ae130000c8013602df&archived=false&referer=chiffrages\"},{\"rel\":\"recours\",\"href\":\"/core/api/covea-expert/recours?missionId=5b2cc7ae130000c8013602df&referer=recours\"}]}\n";

    // When
    com.darva.sinapps.api.client.expertise.model.RessourceMission ressourceMission = objectMapper
        .readValue(jsonDataTypes,
                   com.darva.sinapps.api.client.expertise.model.RessourceMission.class);

    // Then
    assertThat(ressourceMission).isNotNull();
  }
}
