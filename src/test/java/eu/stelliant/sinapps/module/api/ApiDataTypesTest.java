package eu.stelliant.sinapps.module.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.darva.sinapps.api.client.expertise.model.InlineResponse2001;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import eu.stelliant.model.DataTypes;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class ApiDataTypesTest {

  private static final ObjectMapper mapper = new ObjectMapper();

  @BeforeClass
  public static void init() {
    mapper.findAndRegisterModules();
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
  }

  @Test
  public void date_should_throw_jackson_invalidFormatException() {

    // Given
    String jsonDataTypes = "{"
        + "\"dateType\": \"2018-05-17T10:57:47+0200\""
        + "}";

    // When
    Throwable exception = catchThrowable(() -> mapper.readValue(jsonDataTypes, DataTypes.class));

    // Then
    assertThat(exception).isInstanceOf(InvalidFormatException.class)
        .hasMessageContaining("could not be parsed at index 19");
  }

  @Test
  public void date_should_be_parsed() throws IOException {

    // Given
    String jsonDataTypes = "{"
        + "\"dateType\": \"2018-05-17T10:57:47+02:00\""
        + "}";

    // When
    DataTypes dataTypes = mapper.readValue(jsonDataTypes, DataTypes.class);

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
    Throwable exception = catchThrowable(() -> mapper.readValue(jsonDataTypes, DataTypes.class));

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
    DataTypes dataTypes = mapper.readValue(jsonDataTypes, DataTypes.class);

    // Then
    assertThat(dataTypes.getArrayType().size()).isEqualTo(2);
  }

  @Test
  public void id_should_not_be_null() throws IOException {

    // Given
    String jsonDataTypes = "{\"properties\": {"
        + "   \"id\": \"5afd74801300001f014c5537\""
        + " }"
        + "}";

    // When
    DataTypes dataTypes = mapper.readValue(jsonDataTypes, DataTypes.class);

    // Then
    assertThat(dataTypes.getProperties().getId()).isNotNull();
  }

  @Test
  public void id_should_() throws IOException {

    // Given
    String jsonDataTypes = "{\"href\":\"/core/api/covea-expert/missions/5afd78c4130000ac014c5577\",\"properties\":{\"dossier\":{\"assureurId\":\"5afd3cfa1100008b0069ea78\",\"contrat\":{\"numero\":\"string\",\"typeContrat\":\"string\",\"clausesParticulieres\":\"string\",\"activitesSouscrites\":[\"string\"],\"informationsSpecifiques\":\"string\",\"franchise\":{\"value\":{\"montant\":110},\"name\":\"Fixe\",\"label\":\"Fixe\"},\"apporteur\":{\"nom\":\"string\",\"prenom\":\"string\",\"adresse\":{\"adresse1\":\"string\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"string\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}},\"professionnel\":false,\"dateSouscription\":\"2010-01-01\",\"dateModification\":\"2010-01-01\",\"indiceSouscription\":\"string\",\"dateValeur\":\"2010-01-01\",\"conventionsSouscrites\":\"string\",\"numeroAccord\":\"string\",\"intermediaire\":{\"referenceInterneSinistre\":\"string\",\"codeOrias\":\"string\",\"personne\":{\"nom\":\"string\",\"prenom\":\"string\",\"civilite\":{\"name\":\"M\",\"label\":\"M\"},\"adresse\":{\"adresse1\":\"string\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"string\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}}},\"garanties\":[{\"id\":\"5afd78c4130000ac014c5574\",\"typeGarantie\":{\"name\":\"BrisDeGlace\",\"label\":\"Bris De Glace\"},\"typeGarantieComplementaire\":{\"name\":\"VolHorsDomicileHorsLocal\",\"label\":\"Vol Hors Domicile/Hors Local\"},\"plafonds\":[{\"objetPlafonne\":{\"name\":\"PlafondDesObjetsDeValeur\",\"label\":\"Plafond des objets de valeur\"},\"valeur\":3500}],\"franchise\":{\"value\":{\"taux\":20,\"plancher\":200,\"plafond\":1000},\"name\":\"Proportionnelle\",\"label\":\"Proportionnelle\"}},{\"id\":\"5afd78c4130000ac014c5575\",\"typeGarantie\":{\"name\":\"CatastropheNaturelle\",\"label\":\"Catastrophe Naturelle\"},\"plafonds\":[{\"objetPlafonne\":{\"name\":\"PlafondMobilier\",\"label\":\"Plafond Mobilier\"},\"valeur\":12000}],\"franchise\":{\"value\":{\"montant\":450},\"name\":\"Fixe\",\"label\":\"Fixe\"}}],\"sinistralites\":[]},\"risques\":[{\"adresse\":{\"adresse1\":\"rue de l'american way of life\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"13400\",\"localite\":\"San Francisco\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"typeRisque\":{\"name\":\"MaisonParticuliere\",\"label\":\"Maison particulière\"},\"usageAssure\":{\"name\":\"ResidencePrincipale\",\"label\":\"Résidence principale\"},\"description\":\"L'arrivée des colons à l'Ouest, du train et du télégraphe sont traités de même manière que les grands évènements que sont la ruée vers l'or ou l'exploitation du pétrole..\",\"mesures\":[{\"typeMesure\":{\"label\":\"Superficie\",\"name\":\"Superficie\",\"value\":{\"unite\":\"m²\",\"unites\":\"m²\"}},\"valeur\":10}],\"risqueConcerne\":true,\"risqueMoins2ans\":false,\"risquesAggraves\":[],\"niveauProtection\":\"string\",\"risqueAtypique\":true,\"anneeConstruction\":1861,\"indicateurs\":[{\"typeIndicateur\":{\"name\":\"Alarme\",\"label\":\"Alarme\"},\"valeur\":1}]}],\"sinistre\":{\"reference\":\"3535353\",\"caracteristiques\":{\"nature\":{\"name\":\"BrisDeGlace\",\"label\":\"Bris de glace\"},\"cause\":{\"name\":\"Accidentel\",\"label\":\"Accidentel\"}},\"date\":\"2015-02-11\",\"heure\":\"17:00:00\",\"adresse\":{\"adresse1\":\"rue du croque-mort\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"13000\",\"localite\":\"Charleston\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"conformiteSinistre\":{\"sinistreConforme\":true},\"dommagesConstates\":\"Lucky Luke est une série de bandes dessinées caricaturale, de ce fait, tout l'environnement est exagéré aussi bien au niveau des attitudes que du physique des personnages comme l'utilisation de gros nez.\",\"circonstances\":\" La série se déroule sur une période de 40 ans, de 1861 (juste avant la guerre de Sécession qui débute en avril) jusqu'à la fin du siècle. La majorité des histoires se déroulent vers les années 1880\",\"nonSuppressionDeCause\":{\"motif\":{\"name\":\"EnCoursDeReparation\",\"label\":\"En cours de réparation\"},\"precision\":\"Les frères Dalton sont les premiers personnages historiques à apparaître dans l'histoire Hors-la-loi (1951).\"},\"piecesEndommagees\":[],\"vitresEndommagees\":[],\"rappelMesuresConservatoires\":\"string\"},\"acteurs\":[{\"informationsAssure\":{\"profession\":\"Justicier\",\"particulariteClient\":{\"name\":\"VIP\",\"label\":\"VIP\"},\"precisionParticulariteClient\":\"He's a poor lonesome cowboy\",\"situationComptable\":\"En cours\"},\"id\":\"5afd78c4130000ac014c5576\",\"personne\":{\"nom\":\"Luke\",\"prenom\":\"Lucky\",\"civilite\":{\"name\":\"M\",\"label\":\"M\"},\"adresse\":{\"adresse1\":\"rue du Farwest\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"79000\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}},\"references\":[{\"typeReference\":{\"name\":\"ReferenceSinistre\",\"label\":\"Référence sinistre\"},\"valeur\":\"toto\"}],\"archive\":false,\"representants\":[{\"qualite\":{\"name\":\"ConjointDeLAssure\",\"label\":\"Conjoint de l'assuré\"},\"personne\":{\"nom\":\"Jumper\",\"prenom\":\"Jolly\",\"civilite\":{\"name\":\"M\",\"label\":\"M\"},\"adresse\":{\"adresse1\":\"string\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"string\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}},\"informationsComplementaires\":\"Dispo entre 12 et 14h\"}],\"typeActeur\":{\"name\":\"Assure\",\"label\":\"Assuré\"},\"relationAuRisque\":{\"name\":\"Locataire\",\"label\":\"Locataire\"},\"responsableSinistre\":false,\"misEnCause\":false,\"_specificites\":[]}],\"franchiseApplicable\":{\"value\":{\"montant\":700},\"name\":\"Fixe\",\"label\":\"Fixe\"}},"
        + "\"mission\":{\"prestataireId\":\"5afd440b20000017014fe8ef\",\"assureurId\":\"5afd3cfa1100008b0069ea78\",\"dossierId\":\"5afd78c4130000ac014c5573\",\"dateDeMissionnement\":\"2018-03-16T13:40:48.556+01:00[Europe/Paris]\",\"numero\":\"13\",\"commentaires\":[\"Lucky Luke est une série de bande dessinée franco-belge de western humoristique créée par le dessinateur belge Morris dans l'Almanach 1947, un hors-série du journal Spirou publié en 1946. Morris est aidé, à partir de la neuvième histoire, par plusieurs scénaristes, dont le plus fameux est René Goscinny\"],"
        + "\"statut\":{\"name\":\"EnCours\",\"label\":\"En cours\"},"
        + "\"etape\":{\"name\":\"PrestataireMissionne\",\"label\":\"Prestataire missionné\"},"
        + "\"typeMission\":{\"name\":\"ExpertiseCovea\",\"label\":\"Expertise\"},"
        + "\"TBD\":\"A Definir\"},"
        + "\"id\":\"5afd78c4130000ac014c5577\",\"version\":3,\"dateDeCreation\":\"2018-05-17T14:42:45+0200\",\"dateDeDerniereModification\":\"2018-05-17T14:42:45+0200\"},\"links\":[{\"rel\":\"commands\",\"href\":\"/core/api/covea-expert/missions/5afd78c4130000ac014c5577/commands?referer=commands\"},{\"rel\":\"events\",\"href\":\"/core/api/covea-expert/missions/5afd78c4130000ac014c5577/events?referer=events\"},{\"rel\":\"linked-events\",\"href\":\"/core/api/covea-expert/missions/5afd78c4130000ac014c5577/linkedEvents?referer=linked-events\"},{\"rel\":\"eventsNotCreatedByMe\",\"href\":\"/core/api/covea-expert/missions/5afd78c4130000ac014c5577/othersEvents?referer=eventsNotCreatedByMe\"},{\"rel\":\"piecesJointes\",\"href\":\"/core/api/pieceJointe/piecesJointes?missionId=5afd78c4130000ac014c5577&archived=false&referer=piecesJointes\"},{\"rel\":\"synthese\",\"href\":\"/core/api/covea-expert/synthese?missionId=5afd78c4130000ac014c5577&referer=synthese\"},{\"rel\":\"historique\",\"href\":\"/core/api/covea-expert/historique?missionId=5afd78c4130000ac014c5577&referer=historique\"},{\"rel\":\"prestations\",\"href\":\"/core/api/covea-expert/prestation?missionId=5afd78c4130000ac014c5577&referer=prestations\"},{\"rel\":\"difficultes\",\"href\":\"/core/api/covea-expert/difficulte?missionId=5afd78c4130000ac014c5577&referer=difficultes\"},{\"rel\":\"chiffrages\",\"href\":\"/core/api/covea-expert/chiffrages?missionId=5afd78c4130000ac014c5577&archived=false&referer=chiffrages\"}]}";

    String jsonDataTypes2 = "{\"href\":\"/core/api/covea-expert/missions/5afd6818130000e3004c551b\",\"properties\":{\"dossier\":{\"assureurId\":\"5afd3cfa1100008b0069ea78\",\"contrat\":{\"numero\":\"string\",\"typeContrat\":\"string\",\"clausesParticulieres\":\"clauses présidentielles\",\"activitesSouscrites\":[\"Présidence des Etats-Unis\"],\"informationsSpecifiques\":\"la clé de la maison blanche est sous le paillasson\",\"franchise\":{\"value\":{\"montant\":250},\"name\":\"Fixe\",\"label\":\"Fixe\"},\"apporteur\":{\"nom\":\"string\",\"prenom\":\"string\",\"adresse\":{\"adresse1\":\"string\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"string\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}},\"professionnel\":false,\"dateSouscription\":\"2010-01-01\",\"dateModification\":\"2010-01-01\",\"indiceSouscription\":\"mandant de 4 ans\",\"dateValeur\":\"2010-01-01\",\"conventionsSouscrites\":\"toutes\",\"numeroAccord\":\"string\",\"intermediaire\":{\"referenceInterneSinistre\":\"string\",\"codeOrias\":\"string\",\"personne\":{\"nom\":\"Kennedy\",\"prenom\":\"John\",\"civilite\":{\"name\":\"M\",\"label\":\"M\"},\"adresse\":{\"adresse1\":\"string\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"string\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}}},\"garanties\":[{\"id\":\"5afd6818130000e3004c5519\",\"typeGarantie\":{\"name\":\"Vol\",\"label\":\"Vol\"},\"typeGarantieComplementaire\":{\"name\":\"VolHorsDomicileHorsLocal\",\"label\":\"Vol Hors Domicile/Hors Local\"},\"plafonds\":[{\"objetPlafonne\":{\"name\":\"PlafondDesObjetsDeValeur\",\"label\":\"Plafond des objets de valeur\"},\"valeur\":16500}],\"franchise\":{\"value\":{\"taux\":20,\"plancher\":450,\"plafond\":10000},\"name\":\"Proportionnelle\",\"label\":\"Proportionnelle\"}}],\"sinistralites\":[]},\"risques\":[{\"adresse\":{\"adresse1\":\"avenue de l'american way of life\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"13400\",\"localite\":\"Aubagne\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"typeRisque\":{\"name\":\"MaisonParticuliere\",\"label\":\"Maison particulière\"},\"usageAssure\":{\"name\":\"ResidencePrincipale\",\"label\":\"Résidence principale\"},\"description\":\"Abraham Lincoln naît dans le Comté de Hardin, sur la « Frontière » le 12 février 1809 dans la cabane en rondins de ses parents, un couple de fermiers sans fortune1. Il est prénommé Abraham, sans deuxième prénom, en souvenir de son grand-père paternel. Le mythe a quelque peu exagéré la pauvreté de ses parents à sa naissance. Abraham fréquente l'école de Cumberland Road avec sa sœur.\",\"mesures\":[{\"typeMesure\":{\"label\":\"Superficie\",\"name\":\"Superficie\",\"value\":{\"unite\":\"m²\",\"unites\":\"m²\"}},\"valeur\":1700}],\"risqueConcerne\":true,\"risqueMoins2ans\":false,\"risquesAggraves\":[],\"niveauProtection\":\"optimal\",\"risqueAtypique\":true,\"anneeConstruction\":1856,\"indicateurs\":[{\"typeIndicateur\":{\"name\":\"Alarme\",\"label\":\"Alarme\"},\"valeur\":1}]}],\"sinistre\":{\"reference\":\"2\",\"caracteristiques\":{\"nature\":{\"name\":\"Vol\",\"label\":\"Vol\"},\"cause\":{\"name\":\"AvecEffraction\",\"label\":\"Avec effraction\"}},\"date\":\"2015-02-11\",\"heure\":\"17:00:00\",\"adresse\":{\"adresse1\":\"rue dela guerre de sécession\",\"adresse2\":\"Bâtiment du grand Canyon\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"13000\",\"localite\":\"Washington\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"conformiteSinistre\":{\"sinistreConforme\":true},\"dommagesConstates\":\"Abraham Lincoln, né le 12 février 1809 dans le comté de Hardin au Kentucky et mort assassiné le 15 avril 1865 à Washington, D.C., est un homme d'État américain. Il est le seizième président des États-Unis. Il est élu à deux reprises président des États-Unis, en novembre 1860 et en novembre 1864.\",\"circonstances\":\" Assassiné cinq jours plus tard, à la suite d'un complot organisé par des confédérés, il ne termine pas son second mandat.\",\"nonSuppressionDeCause\":{\"motif\":{\"name\":\"EnCoursDeReparation\",\"label\":\"En cours de réparation\"},\"precision\":\"Son père, Thomas Lincoln, descend d'une longue lignée de Lincoln, dont le premier avait émigré d'Angleterre dans le Massachusetts en 1637. De là, les générations ont voyagé en Pennsylvanie, en Virginie puis dans le Kentucky. Le père de Thomas, nommé aussi Abraham, a été tué par des Indiens en 17861. Simple charpentier illettré au départ, Thomas est devenu un des fermiers les plus riches du comté2.\"},\"piecesEndommagees\":[],\"vitresEndommagees\":[],\"rappelMesuresConservatoires\":\"string\"},\"acteurs\":[{\"informationsAssure\":{\"profession\":\"Première dame\",\"particulariteClient\":{\"name\":\"VIP\",\"label\":\"VIP\"},\"precisionParticulariteClient\":\"la clé de la maison blanche est sous le paillasson\",\"situationComptable\":\"En cours\"},\"id\":\"5afd6818130000e3004c551a\",\"personne\":{\"nom\":\"Obama\",\"prenom\":\"Michèle\",\"civilite\":{\"name\":\"Mme\",\"label\":\"Mme\"},\"adresse\":{\"adresse1\":\"rue de la Maison blanche\",\"adresse2\":\"pas loin du pentagone\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"Washington\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"0606060606\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}},\"references\":[{\"typeReference\":{\"name\":\"ReferenceSinistre\",\"label\":\"Référence sinistre\"},\"valeur\":\"toto\"}],\"archive\":false,\"representants\":[{\"qualite\":{\"name\":\"ConjointDeLAssure\",\"label\":\"Conjoint de l'assuré\"},\"personne\":{\"nom\":\"Obama\",\"prenom\":\"Barack\",\"civilite\":{\"name\":\"M\",\"label\":\"M\"},\"adresse\":{\"adresse1\":\"rue du bureau oval\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"Washington\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}},\"informationsComplementaires\":\"Dispo entre 12 et 14h\"}],\"typeActeur\":{\"name\":\"Assure\",\"label\":\"Assuré\"},\"relationAuRisque\":{\"name\":\"Locataire\",\"label\":\"Locataire\"},\"responsableSinistre\":false,\"misEnCause\":false,\"_specificites\":[]}],\"franchiseApplicable\":{\"value\":{\"montant\":200},\"name\":\"Fixe\",\"label\":\"Fixe\"}},"
        + "\"mission\":{\"prestataireId\":\"5afd440b20000017014fe8ef\",\"assureurId\":\"5afd3cfa1100008b0069ea78\",\"dossierId\":\"5afd6818130000e3004c5518\",\"dateDeMissionnement\":\"2018-03-16T13:40:48.556+01:00[Europe/Paris]\",\"numero\":\"4\",\"commentaires\":[\"Lincoln naît dans une famille modeste. Après une enfance et adolescence sans relief, il apprend le droit seul grâce à ses talents d’autodidacte et devient avocat itinérant. Entraîné peu à peu sur le terrain de la politique, il dirige un temps le parti Whig et est élu à la Chambre des représentants de l’Illinois dans les années 1830, puis à celle des États-Unis pour un mandat dans les années 1840.\"],"
        + "\"statut\":{\"name\":\"EnCours\",\"label\":\"En cours\"},"
        + "\"etape\":{\"name\":\"PrestataireMissionne\",\"label\":\"Prestataire missionné\"},"
        + "\"typeMission\":{\"name\":\"ExpertiseCovea\",\"label\":\"Expertise\"},"
        + "\"TBD\":\"A Definir\"},"
        + "\"id\":\"5afd6818130000e3004c551b\",\"version\":5,\"dateDeCreation\":\"2018-05-17T13:31:36+0200\",\"dateDeDerniereModification\":\"2018-05-17T13:31:36+0200\"},\"links\":[{\"rel\":\"commands\",\"href\":\"/core/api/covea-expert/missions/5afd6818130000e3004c551b/commands?referer=commands\"},{\"rel\":\"events\",\"href\":\"/core/api/covea-expert/missions/5afd6818130000e3004c551b/events?referer=events\"},{\"rel\":\"linked-events\",\"href\":\"/core/api/covea-expert/missions/5afd6818130000e3004c551b/linkedEvents?referer=linked-events\"},{\"rel\":\"eventsNotCreatedByMe\",\"href\":\"/core/api/covea-expert/missions/5afd6818130000e3004c551b/othersEvents?referer=eventsNotCreatedByMe\"},{\"rel\":\"piecesJointes\",\"href\":\"/core/api/pieceJointe/piecesJointes?missionId=5afd6818130000e3004c551b&archived=false&referer=piecesJointes\"},{\"rel\":\"synthese\",\"href\":\"/core/api/covea-expert/synthese?missionId=5afd6818130000e3004c551b&referer=synthese\"},{\"rel\":\"historique\",\"href\":\"/core/api/covea-expert/historique?missionId=5afd6818130000e3004c551b&referer=historique\"},{\"rel\":\"prestations\",\"href\":\"/core/api/covea-expert/prestation?missionId=5afd6818130000e3004c551b&referer=prestations\"},{\"rel\":\"difficultes\",\"href\":\"/core/api/covea-expert/difficulte?missionId=5afd6818130000e3004c551b&referer=difficultes\"},{\"rel\":\"chiffrages\",\"href\":\"/core/api/covea-expert/chiffrages?missionId=5afd6818130000e3004c551b&archived=false&referer=chiffrages\"}]}";

    String jsonDataTypes3 = "{\"href\":\"/core/api/covea-expert/missions/5afd74801300001f014c5537\",\"properties\":{\"dossier\":{\"assureurId\":\"5afd3cfa1100008b0069ea78\",\"contrat\":{\"numero\":\"string\",\"typeContrat\":\"string\",\"clausesParticulieres\":\"string\",\"activitesSouscrites\":[\"string\"],\"informationsSpecifiques\":\"string\",\"franchise\":{\"value\":{\"montant\":110},\"name\":\"Fixe\",\"label\":\"Fixe\"},\"apporteur\":{\"nom\":\"string\",\"prenom\":\"string\",\"adresse\":{\"adresse1\":\"string\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"string\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}},\"professionnel\":false,\"dateSouscription\":\"2010-01-01\",\"dateModification\":\"2010-01-01\",\"indiceSouscription\":\"string\",\"dateValeur\":\"2010-01-01\",\"conventionsSouscrites\":\"string\",\"numeroAccord\":\"string\",\"intermediaire\":{\"referenceInterneSinistre\":\"string\",\"codeOrias\":\"string\",\"personne\":{\"nom\":\"string\",\"prenom\":\"string\",\"civilite\":{\"name\":\"M\",\"label\":\"M\"},\"adresse\":{\"adresse1\":\"string\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"string\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}}},\"garanties\":[{\"id\":\"5afd74801300001f014c5534\",\"typeGarantie\":{\"name\":\"Vol\",\"label\":\"Vol\"},\"typeGarantieComplementaire\":{\"name\":\"VolHorsDomicileHorsLocal\",\"label\":\"Vol Hors Domicile/Hors Local\"},\"plafonds\":[{\"objetPlafonne\":{\"name\":\"PlafondDesObjetsDeValeur\",\"label\":\"Plafond des objets de valeur\"},\"valeur\":6700}],\"franchise\":{\"value\":{\"taux\":20,\"plancher\":200,\"plafond\":1000},\"name\":\"Proportionnelle\",\"label\":\"Proportionnelle\"}},{\"id\":\"5afd74801300001f014c5535\",\"typeGarantie\":{\"name\":\"Gel\",\"label\":\"Gel\"},\"plafonds\":[{\"objetPlafonne\":{\"name\":\"PlafondVeranda\",\"label\":\"Plafond véranda\"},\"valeur\":12000}],\"franchise\":{\"value\":{\"montant\":450},\"name\":\"Fixe\",\"label\":\"Fixe\"}}],\"sinistralites\":[]},\"risques\":[{\"adresse\":{\"adresse1\":\"rue de l'american way of life\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"13400\",\"localite\":\"Los Angeles\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"typeRisque\":{\"name\":\"MaisonParticuliere\",\"label\":\"Maison particulière\"},\"usageAssure\":{\"name\":\"ResidencePrincipale\",\"label\":\"Résidence principale\"},\"description\":\"James Dean est enterré au Park Cemetery à Fairmount dans l'Indiana. \",\"mesures\":[{\"typeMesure\":{\"label\":\"Superficie\",\"name\":\"Superficie\",\"value\":{\"unite\":\"m²\",\"unites\":\"m²\"}},\"valeur\":300}],\"risqueConcerne\":true,\"risqueMoins2ans\":false,\"risquesAggraves\":[],\"niveauProtection\":\"string\",\"risqueAtypique\":true,\"anneeConstruction\":1856,\"indicateurs\":[{\"typeIndicateur\":{\"name\":\"Alarme\",\"label\":\"Alarme\"},\"valeur\":1}]}],\"sinistre\":{\"reference\":\"3535353\",\"caracteristiques\":{\"nature\":{\"name\":\"Vol\",\"label\":\"Vol\"},\"cause\":{\"name\":\"SansEffraction\",\"label\":\"Sans effraction\"}},\"date\":\"2015-02-11\",\"heure\":\"17:00:00\",\"adresse\":{\"adresse1\":\"rue d'à l'est de l'eden\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"13000\",\"localite\":\"Aubagne\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"conformiteSinistre\":{\"sinistreConforme\":true},\"dommagesConstates\":\"Elia Kazan : « J’ai choisi Jimmy parce qu’il était Cal. Il n’y avait aucun doute, personne ne pourrait le jouer mieux que lui »8.\",\"circonstances\":\" Passionné de compétition automobile, James Dean a plusieurs victoires à son actif. Un de ses loisirs favoris est de traverser les rues de Los Angeles à très grande vitesse, semant les voitures de police..\",\"nonSuppressionDeCause\":{\"motif\":{\"name\":\"EnCoursDeReparation\",\"label\":\"En cours de réparation\"},\"precision\":\"James Dean venait de terminer le tournage de Géant, durant lequel, ironiquement, une clause de son contrat lui interdisait les courses automobiles et les conduites dangereuses. Il avait tourné peu de temps avant un clip pour la prévention routière, incitant les gens à rouler prudemment..\"},\"piecesEndommagees\":[],\"vitresEndommagees\":[],\"rappelMesuresConservatoires\":\"string\"},\"acteurs\":[{\"informationsAssure\":{\"profession\":\"Acteur\",\"particulariteClient\":{\"name\":\"VIP\",\"label\":\"VIP\"},\"precisionParticulariteClient\":\"Batîment\",\"situationComptable\":\"En cours\"},\"id\":\"5afd74801300001f014c5536\",\"personne\":{\"nom\":\"Dean\",\"prenom\":\"James\",\"civilite\":{\"name\":\"M\",\"label\":\"M\"},\"adresse\":{\"adresse1\":\"route 466\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"Californie\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}},\"references\":[{\"typeReference\":{\"name\":\"ReferenceSinistre\",\"label\":\"Référence sinistre\"},\"valeur\":\"toto\"}],\"archive\":false,\"representants\":[{\"qualite\":{\"name\":\"ConjointDeLAssure\",\"label\":\"Conjoint de l'assuré\"},\"personne\":{\"nom\":\"Taylor\",\"prenom\":\"Liz\",\"civilite\":{\"name\":\"Mme\",\"label\":\"Mme\"},\"adresse\":{\"adresse1\":\"string\",\"adresse2\":\"string\",\"adresse3\":\"string\",\"adresse4\":\"string\",\"codePostal\":\"79000\",\"localite\":\"Los Angeles\",\"codePays\":\"33\",\"nomPays\":\"string\"},\"coordonnees\":{\"telPersonnel\":\"string\",\"telProfessionnel\":\"string\",\"telPortable\":\"string\",\"email\":\"annie.barreau@libertysurf.fr\",\"fax\":\"string\"}},\"informationsComplementaires\":\"Dispo entre 12 et 14h\"}],\"typeActeur\":{\"name\":\"Assure\",\"label\":\"Assuré\"},\"relationAuRisque\":{\"name\":\"Locataire\",\"label\":\"Locataire\"},\"responsableSinistre\":false,\"misEnCause\":false,\"_specificites\":[]}],\"franchiseApplicable\":{\"value\":{\"montant\":300},\"name\":\"Fixe\",\"label\":\"Fixe\"}},"
        + "\"mission\":{\"prestataireId\":\"5afd440b20000017014fe8ef\",\"assureurId\":\"5afd3cfa1100008b0069ea78\",\"dossierId\":\"5afd74801300001f014c5533\",\"dateDeMissionnement\":\"2018-03-16T13:40:48.556+01:00[Europe/Paris]\",\"numero\":\"9\",\"commentaires\":[\"James Dean est un acteur américain, né le 8 février 1931 à Marion (Indiana) et mort le 30 septembre 1955 à Cholame (Californie).\"],"
        + "\"statut\":{\"name\":\"Ouverte\",\"label\":\"Ouverte\"},"
        + "\"etape\":{\"name\":\"PrestataireMissionne\",\"label\":\"Prestataire missionné\"},"
        + "\"typeMission\":{\"name\":\"ExpertiseCovea\",\"label\":\"Expertise\"},"
        + "\"TBD\":\"A Definir\"},"
        + "\"id\":\"5afd74801300001f014c5537\",\"version\":2,\"dateDeCreation\":\"2018-05-17T14:24:32+0200\",\"dateDeDerniereModification\":\"2018-05-17T14:24:32+0200\"},\"links\":[{\"rel\":\"commands\",\"href\":\"/core/api/covea-expert/missions/5afd74801300001f014c5537/commands?referer=commands\"},{\"rel\":\"events\",\"href\":\"/core/api/covea-expert/missions/5afd74801300001f014c5537/events?referer=events\"},{\"rel\":\"linked-events\",\"href\":\"/core/api/covea-expert/missions/5afd74801300001f014c5537/linkedEvents?referer=linked-events\"},{\"rel\":\"eventsNotCreatedByMe\",\"href\":\"/core/api/covea-expert/missions/5afd74801300001f014c5537/othersEvents?referer=eventsNotCreatedByMe\"},{\"rel\":\"piecesJointes\",\"href\":\"/core/api/pieceJointe/piecesJointes?missionId=5afd74801300001f014c5537&archived=false&referer=piecesJointes\"},{\"rel\":\"synthese\",\"href\":\"/core/api/covea-expert/synthese?missionId=5afd74801300001f014c5537&referer=synthese\"},{\"rel\":\"historique\",\"href\":\"/core/api/covea-expert/historique?missionId=5afd74801300001f014c5537&referer=historique\"},{\"rel\":\"prestations\",\"href\":\"/core/api/covea-expert/prestation?missionId=5afd74801300001f014c5537&referer=prestations\"},{\"rel\":\"difficultes\",\"href\":\"/core/api/covea-expert/difficulte?missionId=5afd74801300001f014c5537&referer=difficultes\"},{\"rel\":\"chiffrages\",\"href\":\"/core/api/covea-expert/chiffrages?missionId=5afd74801300001f014c5537&archived=false&referer=chiffrages\"}]}";

    // When
    InlineResponse2001 dataTypes = mapper.readValue(jsonDataTypes, InlineResponse2001.class);

    // Then
    assertThat(dataTypes.getProperties().getId()).isNotNull();
  }
}
