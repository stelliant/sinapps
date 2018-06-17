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
        .hasMessageContaining("Cannot deserialize value of type `java.time.ZonedDateTime`");
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
    com.darva.sinapps.api.client.transverse.model.InlineResponse2001 inlineResponse2001 = objectMapper
        .readValue(jsonDataTypes,
                   com.darva.sinapps.api.client.transverse.model.InlineResponse2001.class);

    // Then
    assertThat(inlineResponse2001.getProperties().getId()).isNotNull();
  }

  @Test
  public void missions_should_be_parsed() throws IOException {

    // Given
    String jsonDataTypes = "{\"href\":\"/core/api/common/missions?prestataireId=5afd440b20000017014fe8ef&nbPage=1&nbElements=16\",\"properties\":{\"totalNumberOfElements\":3,\"noPage\":1,\"nbElementPerPage\":16,\"nbElements\":3,\"firstPage\":\"/core/api/common/missions?prestataireId=5afd440b20000017014fe8ef&nbPage=1&nbElements=16\",\"lastPage\":\"/core/api/common/missions?prestataireId=5afd440b20000017014fe8ef&nbPage=1&nbElements=16\"},\"items\":[{\"href\":\"/core/api/covea-expert/missions/5afd78c4130000ac014c5577\",\"properties\":{\"id\":\"5afd78c4130000ac014c5577\",\"assureurId\":\"5afd3cfa1100008b0069ea78\",\"lieu\":\"Charleston, 13000\",\"departement\":\"13\",\"difficulteBloquante\":false,\"typeMission\":{\"name\":\"ExpertiseCovea\",\"label\":\"Expertise\"},\"pourcentageAvancement\":50,\"prestataireId\":\"5afd440b20000017014fe8ef\",\"numero\":\"13\",\"dateDeMissionnement\":\"2018-03-16T13:40:48+0100\",\"difficulteNonBloquante\":false,\"etapeMission\":{\"name\":\"PrestataireMissionne\",\"label\":\"Prestataire missionné\"},\"cloture\":false,\"nature\":{\"name\":\"BrisDeGlace\",\"label\":\"Bris de glace\"},\"finDeMissionDemandee\":false,\"referenceSinistre\":\"3535353\",\"cause\":{\"name\":\"Accidentel\",\"label\":\"Accidentel\"},\"assure\":\"M Lucky Luke\",\"etatPrestation\":{\"name\":\"RendezVousPris\",\"label\":\"Rendez-vous pris\"},\"mandant\":\"covea-expert\",\"dateCreation\":\"2018-05-17T14:42:45+0200\"},\"links\":[]},{\"href\":\"/core/api/covea-expert/missions/5afd6818130000e3004c551b\",\"properties\":{\"id\":\"5afd6818130000e3004c551b\",\"assureurId\":\"5afd3cfa1100008b0069ea78\",\"lieu\":\"Washington, 13000\",\"departement\":\"13\",\"difficulteBloquante\":false,\"typeMission\":{\"name\":\"ExpertiseCovea\",\"label\":\"Expertise\"},\"pourcentageAvancement\":25,\"prestataireId\":\"5afd440b20000017014fe8ef\",\"numero\":\"4\",\"dateDeMissionnement\":\"2018-03-16T13:40:48+0100\",\"difficulteNonBloquante\":false,\"etapeMission\":{\"name\":\"PrestataireMissionne\",\"label\":\"Prestataire missionné\"},\"cloture\":false,\"nature\":{\"name\":\"Vol\",\"label\":\"Vol\"},\"finDeMissionDemandee\":false,\"referenceSinistre\":\"2\",\"cause\":{\"name\":\"AvecEffraction\",\"label\":\"Avec effraction\"},\"assure\":\"Mme Michèle Obama\",\"etatPrestation\":{\"name\":\"PrestationQualifiee\",\"label\":\"Prestation qualifiée\"},\"mandant\":\"covea-expert\",\"dateCreation\":\"2018-05-17T13:31:36+0200\"},\"links\":[]},{\"href\":\"/core/api/covea-expert/missions/5afd74801300001f014c5537\",\"properties\":{\"id\":\"5afd74801300001f014c5537\",\"assureurId\":\"5afd3cfa1100008b0069ea78\",\"lieu\":\"Aubagne, 13000\",\"departement\":\"13\",\"difficulteBloquante\":false,\"typeMission\":{\"name\":\"ExpertiseCovea\",\"label\":\"Expertise\"},\"pourcentageAvancement\":25,\"prestataireId\":\"5afd440b20000017014fe8ef\",\"numero\":\"9\",\"dateDeMissionnement\":\"2018-03-16T13:40:48+0100\",\"difficulteNonBloquante\":false,\"etapeMission\":{\"name\":\"PrestataireMissionne\",\"label\":\"Prestataire missionné\"},\"cloture\":false,\"nature\":{\"name\":\"Vol\",\"label\":\"Vol\"},\"finDeMissionDemandee\":false,\"referenceSinistre\":\"3535353\",\"cause\":{\"name\":\"SansEffraction\",\"label\":\"Sans effraction\"},\"assure\":\"M James Dean\",\"etatPrestation\":{\"name\":\"PrestationQualifiee\",\"label\":\"Prestation qualifiée\"},\"mandant\":\"covea-expert\",\"dateCreation\":\"2018-05-17T14:24:32+0200\"},\"links\":[]}],\"links\":[{\"rel\":\"firstPage\",\"href\":\"/core/api/common/missions?prestataireId=5afd440b20000017014fe8ef&nbPage=1&nbElements=16&referer=firstPage\"},{\"rel\":\"lastPage\",\"href\":\"/core/api/common/missions?prestataireId=5afd440b20000017014fe8ef&nbPage=1&nbElements=16&referer=lastPage\"}],\"filters\":{\"/assureurId\":{\"type\":{\"array\":{\"type\":\"BSONObjectID\"}},\"optional\":true},\"/prestataireId\":{\"type\":{\"array\":{\"type\":\"BSONObjectID\"}},\"optional\":true},\"/typeMissionQuery\":{\"type\":{\"array\":{\"oneOf\":[{\"name\":\"RenDirecte\",\"label\":\"REN Directe\"},{\"name\":\"RenSuiteExpertise\",\"label\":\"REN suite à expertise\"},{\"name\":\"UrgenceHabitation\",\"label\":\"Urgence Habitation\"},{\"name\":\"ExpertiseCovea\",\"label\":\"Expertise\"}]}},\"optional\":true},\"/etapeMission\":{\"type\":{\"array\":{\"oneOf\":[{\"name\":\"MissionRecue\",\"label\":\"Mission reçue\"},{\"name\":\"RendezVousChiffragePris\",\"label\":\"Rendez-vous chiffrage pris\"},{\"name\":\"ChiffrageValide\",\"label\":\"Chiffrage validé\"},{\"name\":\"TravauxPlanifies\",\"label\":\"Travaux planifiés\"},{\"name\":\"FactureTravauxDeposee\",\"label\":\"Facture de travaux déposée\"},{\"name\":\"FactureGestionDeposee\",\"label\":\"Facture de gestion déposée\"},{\"name\":\"ChiffrageEnAttenteDeValidation\",\"label\":\"Chiffrage en attente de validation\"},{\"name\":\"Annulee\",\"label\":\"Mission annulée\"},{\"name\":\"MissionArretee\",\"label\":\"Mission arrêtée\"},{\"name\":\"MissionTransmise\",\"label\":\"Mission transmise\"},{\"name\":\"MissionAcceptee\",\"label\":\"Mission acceptée\"},{\"name\":\"MissionAnnulee\",\"label\":\"Mission annulée\"},{\"name\":\"MissionRefusee\",\"label\":\"Mission refusée\"},{\"name\":\"RendezVousPris\",\"label\":\"Rendez-vous pris\"},{\"name\":\"TravauxChiffres\",\"label\":\"Travaux chiffrés\"},{\"name\":\"ChiffrageRefuse\",\"label\":\"Chiffrage refusé\"},{\"name\":\"MissionTraitee\",\"label\":\"Mission traitée\"},{\"name\":\"MissionCloseSansResolution\",\"label\":\"Mission close sans résolution\"},{\"name\":\"PrestataireMissionne\",\"label\":\"Prestataire missionné\"},{\"name\":\"ExpertiseInitiee\",\"label\":\"Expertise initiée\"},{\"name\":\"RapportDepose\",\"label\":\"Rapport déposé\"}]}},\"optional\":true},\"/etatPrestation\":{\"type\":{\"array\":{\"oneOf\":[{\"name\":\"PrestationRecue\",\"label\":\"Prestation reçue\"},{\"name\":\"PrestationQualifiee\",\"label\":\"Prestation qualifiée\"},{\"name\":\"RendezVousPris\",\"label\":\"Rendez-vous pris\"},{\"name\":\"CompteRenduDepose\",\"label\":\"Compte rendu déposé\"},{\"name\":\"ConclusionsDeposees\",\"label\":\"Conclusions déposées\"}]}},\"optional\":true},\"/departement\":{\"type\":{\"array\":{\"type\":\"string\"}},\"optional\":true},\"/cloture\":{\"type\":\"boolean\",\"optional\":true},\"/startDateQuery\":{\"type\":\"DateTime\",\"optional\":true},\"/endDateQuery\":{\"type\":\"DateTime\",\"optional\":true},\"/contentQuery\":{\"type\":\"string\",\"optional\":true}}}\n";

    // When
    com.darva.sinapps.api.client.transverse.model.InlineResponse2002 inlineResponse2002 = objectMapper
        .readValue(jsonDataTypes,
                   com.darva.sinapps.api.client.transverse.model.InlineResponse2002.class);

    // Then
    assertThat(inlineResponse2002).isNotNull();
  }

  @Test
  public void mission_should_throw_jackson_InvalidFormatException() throws IOException {

    // Given
    String jsonDataTypes = "{\"href\":\"/core/api/covea-expert/missions/5afd74801300001f014c5537\",\"properties\":{\"dossier\":{\"assureurId\":\"5afd3cfa1100008b0069ea78\",\"contrat\":{\"numero\":\"string\",\"typeContrat\":\"string\",\"clausesParticulieres\":\"string\",\"activitesSouscrites\":[\"string\"],\"informationsSpecifiques\":\"string\",\"franchise\":{\"value\":{\"montant\":110},\"name\":\"Fixe\",\"label\":\"Fixe\"},\"apporteur\":{\"nom\":\"string\",\"prenom\":\"string\",\"adresse\":{\"adresse1\":\"string\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"string\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}},\"professionnel\":false,\"dateSouscription\":\"2010-01-01\",\"dateModification\":\"2010-01-01\",\"indiceSouscription\":\"string\",\"dateValeur\":\"2010-01-01\",\"conventionsSouscrites\":\"string\",\"numeroAccord\":\"string\",\"intermediaire\":{\"referenceInterneSinistre\":\"string\",\"codeOrias\":\"string\",\"personne\":{\"nom\":\"string\",\"prenom\":\"string\",\"civilite\":{\"name\":\"M\",\"label\":\"M\"},\"adresse\":{\"adresse1\":\"string\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"string\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}}},\"garanties\":[{\"id\":\"5afd74801300001f014c5534\",\"typeGarantie\":{\"name\":\"Vol\",\"label\":\"Vol\"},\"typeGarantieComplementaire\":{\"name\":\"VolHorsDomicileHorsLocal\",\"label\":\"Vol Hors Domicile/Hors Local\"},\"plafonds\":[{\"objetPlafonne\":{\"name\":\"PlafondDesObjetsDeValeur\",\"label\":\"Plafond des objets de valeur\"},\"valeur\":6700}],\"franchise\":{\"value\":{\"taux\":20,\"plancher\":200,\"plafond\":1000},\"name\":\"Proportionnelle\",\"label\":\"Proportionnelle\"}},{\"id\":\"5afd74801300001f014c5535\",\"typeGarantie\":{\"name\":\"Gel\",\"label\":\"Gel\"},\"plafonds\":[{\"objetPlafonne\":{\"name\":\"PlafondVeranda\",\"label\":\"Plafond véranda\"},\"valeur\":12000}],\"franchise\":{\"value\":{\"montant\":450},\"name\":\"Fixe\",\"label\":\"Fixe\"}}],\"sinistralites\":[]},\"risques\":[{\"adresse\":{\"adresse1\":\"rue de l'american way of life\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"13400\",\"localite\":\"Los Angeles\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"typeRisque\":{\"name\":\"MaisonParticuliere\",\"label\":\"Maison particulière\"},\"usageAssure\":{\"name\":\"ResidencePrincipale\",\"label\":\"Résidence principale\"},\"description\":\"James Dean est enterré au Park Cemetery à Fairmount dans l'Indiana. \",\"mesures\":[{\"typeMesure\":{\"label\":\"Superficie\",\"name\":\"Superficie\",\"value\":{\"unite\":\"m²\",\"unites\":\"m²\"}},\"valeur\":300}],\"risqueConcerne\":true,\"risqueMoins2ans\":false,\"risquesAggraves\":[],\"niveauProtection\":\"string\",\"risqueAtypique\":true,\"anneeConstruction\":1856,\"indicateurs\":[{\"typeIndicateur\":{\"name\":\"Alarme\",\"label\":\"Alarme\"},\"valeur\":1}]}],\"sinistre\":{\"reference\":\"3535353\",\"caracteristiques\":{\"nature\":{\"name\":\"Vol\",\"label\":\"Vol\"},\"cause\":{\"name\":\"SansEffraction\",\"label\":\"Sans effraction\"}},\"date\":\"2015-02-11\",\"heure\":\"17:00:00\",\"adresse\":{\"adresse1\":\"rue d'à l'est de l'eden\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"13000\",\"localite\":\"Aubagne\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"conformiteSinistre\":{\"sinistreConforme\":true},\"dommagesConstates\":\"Elia Kazan : « J’ai choisi Jimmy parce qu’il était Cal. Il n’y avait aucun doute, personne ne pourrait le jouer mieux que lui »8.\",\"circonstances\":\" Passionné de compétition automobile, James Dean a plusieurs victoires à son actif. Un de ses loisirs favoris est de traverser les rues de Los Angeles à très grande vitesse, semant les voitures de police..\",\"nonSuppressionDeCause\":{\"motif\":{\"name\":\"EnCoursDeReparation\",\"label\":\"En cours de réparation\"},\"precision\":\"James Dean venait de terminer le tournage de Géant, durant lequel, ironiquement, une clause de son contrat lui interdisait les courses automobiles et les conduites dangereuses. Il avait tourné peu de temps avant un clip pour la prévention routière, incitant les gens à rouler prudemment..\"},\"piecesEndommagees\":[],\"vitresEndommagees\":[],\"rappelMesuresConservatoires\":\"string\"},\"acteurs\":[{\"informationsAssure\":{\"profession\":\"Acteur\",\"particulariteClient\":{\"name\":\"VIP\",\"label\":\"VIP\"},\"precisionParticulariteClient\":\"Batîment\",\"situationComptable\":\"En cours\"},\"id\":\"5afd74801300001f014c5536\",\"personne\":{\"nom\":\"Dean\",\"prenom\":\"James\",\"civilite\":{\"name\":\"M\",\"label\":\"M\"},\"adresse\":{\"adresse1\":\"route 466\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"Californie\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}},\"references\":[{\"typeReference\":{\"name\":\"ReferenceSinistre\",\"label\":\"Référence sinistre\"},\"valeur\":\"toto\"}],\"archive\":false,\"representants\":[{\"qualite\":{\"name\":\"ConjointDeLAssure\",\"label\":\"Conjoint de l'assuré\"},\"personne\":{\"nom\":\"Taylor\",\"prenom\":\"Liz\",\"civilite\":{\"name\":\"Mme\",\"label\":\"Mme\"},\"adresse\":{\"adresse1\":\"string\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"Los Angeles\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}},\"informationsComplementaires\":\"Dispo entre 12 et 14h\"}],\"typeActeur\":{\"name\":\"Assure\",\"label\":\"Assuré\"},\"relationAuRisque\":{\"name\":\"Locataire\",\"label\":\"Locataire\"},\"responsableSinistre\":false,\"misEnCause\":false,\"_specificites\":[]}],\"franchiseApplicable\":{\"value\":{\"montant\":300},\"name\":\"Fixe\",\"label\":\"Fixe\"}},\"mission\":{\"prestataireId\":\"5afd440b20000017014fe8ef\",\"assureurId\":\"5afd3cfa1100008b0069ea78\",\"dossierId\":\"5afd74801300001f014c5533\",\"dateDeMissionnement\":\"2018-03-16T13:40:48.556+01:00[Europe/Paris]\",\"numero\":\"9\",\"commentaires\":[\"James Dean est un acteur américain, né le 8 février 1931 à Marion (Indiana) et mort le 30 septembre 1955 à Cholame (Californie).\"],\"statut\":{\"name\":\"Ouverte\",\"label\":\"Ouverte\"},\"etape\":{\"name\":\"PrestataireMissionne\",\"label\":\"Prestataire missionné\"},\"typeMission\":{\"name\":\"ExpertiseCovea\",\"label\":\"Expertise\"},\"TBD\":\"A Definir\"},\"id\":\"5afd74801300001f014c5537\",\"version\":2,\"dateDeCreation\":\"2018-05-17T14:24:32+0200\",\"dateDeDerniereModification\":\"2018-05-17T14:24:32+0200\"},\"links\":[{\"rel\":\"commands\",\"href\":\"/core/api/covea-expert/missions/5afd74801300001f014c5537/commands?referer=commands\"},{\"rel\":\"events\",\"href\":\"/core/api/covea-expert/missions/5afd74801300001f014c5537/events?referer=events\"},{\"rel\":\"linked-events\",\"href\":\"/core/api/covea-expert/missions/5afd74801300001f014c5537/linkedEvents?referer=linked-events\"},{\"rel\":\"eventsNotCreatedByMe\",\"href\":\"/core/api/covea-expert/missions/5afd74801300001f014c5537/othersEvents?referer=eventsNotCreatedByMe\"},{\"rel\":\"piecesJointes\",\"href\":\"/core/api/pieceJointe/piecesJointes?missionId=5afd74801300001f014c5537&archived=false&referer=piecesJointes\"},{\"rel\":\"synthese\",\"href\":\"/core/api/covea-expert/synthese?missionId=5afd74801300001f014c5537&referer=synthese\"},{\"rel\":\"historique\",\"href\":\"/core/api/covea-expert/historique?missionId=5afd74801300001f014c5537&referer=historique\"},{\"rel\":\"prestations\",\"href\":\"/core/api/covea-expert/prestation?missionId=5afd74801300001f014c5537&referer=prestations\"},{\"rel\":\"difficultes\",\"href\":\"/core/api/covea-expert/difficulte?missionId=5afd74801300001f014c5537&referer=difficultes\"},{\"rel\":\"chiffrages\",\"href\":\"/core/api/covea-expert/chiffrages?missionId=5afd74801300001f014c5537&archived=false&referer=chiffrages\"}]}\n";

    // When
    Throwable exception = catchThrowable(() -> objectMapper
        .readValue(jsonDataTypes,
                   com.darva.sinapps.api.client.expertise.model.InlineResponse2001.class));

    // Then
    assertThat(exception).isInstanceOf(InvalidFormatException.class)
        .hasMessageContaining("Cannot deserialize value of type `java.time.ZonedDateTime`");
  }
}
