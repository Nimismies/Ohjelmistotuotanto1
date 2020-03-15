
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Antto
 */
/**
 * Palveluraportti luokka
 */
public class palveluRaportti {
    // tarvittavat muuttujat
    private String m_aloitus;
    private String m_lopetus;
    private String m_paiva;
    private String m_toimipisteID;

    /**
     * Konstruktori
     */
public palveluRaportti(){
        
    }
    /**
     * Aloitus getteri
     * @return palauttaa string m_aloitus
     */
    public String getAloitus() {
	return m_aloitus;
    }
    /**
     * Lopetus getteri
     * @return palauttaa string m_lopetus
     */
    public String getLopetus() {
	return m_lopetus;
    }
    /**
     * Paiva getteri 
     * @return palauttaa string m_paiva
     */
    public String getPaiva() {
	return m_paiva;
    }
    /**
     * ToimipisteID getteri 
     * @return palauttaa string m_toimipisteID
     */
    public String getToimipisteID(){
    return m_toimipisteID;
    }
    /**
     * Aloitus setteri
     * @param at uusi m_aloitus arvo
     */
    public void setAloitus (String at){
        m_aloitus = at;
    }
    /**
     * Lopetus setteri
     * @param lo uusi m_lopetus arvo
     */
    public void setLopetus (String lo){
        m_lopetus = lo;
    }
    /**
     * Paiva setteri
     * @param pa uusi m_paiva arvo
     */
    public void setPaiva (String pa){
        m_paiva = pa;
    }
    /**
     * ToimipisteID setteri
     * @param to uusi m_toimipisteID arvo
     */
    public void setToimipisteID (String to){
        m_toimipisteID = to;
    }
    /**
     * toString metodi muuttujille
     */
    @Override
    public String toString(){
        return (m_aloitus + " " + m_lopetus + " " +  m_paiva + " " + m_toimipisteID);
    }
    /**
     * Hakee raportin valitulta ajalta
     * @param connection tietokantayhteys
     * @param id toimipisteID arvo
     * @param aPaiva aloituspaiva arvo
     * @param lPaiva lopetuspaiva arvo
     * @throws SQLException virheilmoitus
     * @throws Exception virheilmoitus
     */
  	public static void raportti (Connection connection, int id, String aPaiva, String lPaiva) throws SQLException, Exception { // tietokantayhteys välitetään parametrina
            // hakee toimipisteen nimen
            String sql = "SELECT nimi" 
                        + " FROM toimipiste WHERE toimipiste_id = ?";
            // hakee asiakkaiden nimen valitulta ajalta ja tietyn toimipisteID:n mukaan, lajittelu asiakas_id perusteella          
            String sql2 = "SELECT concat(etunimi, ' ', sukunimi) AS nimi FROM asiakas WHERE asiakas_id IN (SELECT asiakas_id FROM varaus WHERE varattu_alkupvm BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) AND varaus_id IN(SELECT varaus_id FROM varauksen_palvelut WHERE toimipiste_id = ?)) GROUP BY asiakas_id;";
            // hakee paivamaarat valitulta ajalta tietyltä ajalta ja tietyn toimipisteID:n mukaan, lajittelu asiakas_id perusteella  
            String sql3 = "SELECT DATE(varattu_alkupvm) AS varattu_alkupvm, DATE(varattu_loppupvm) AS varattu_loppupvm FROM varaus WHERE varaus_id IN (SELECT varaus_id FROM varauksen_palvelut WHERE varaus_id IN(SELECT varaus_id FROM varaus WHERE varattu_alkupvm BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) AND toimipiste_id = ?)) GROUP BY asiakas_id;" ;
            // hakee palveluiden nimen, hinnan ja lukumaaran valitulta ajalta ja tietyn toimipisteID:n mukaan, lajittelu asiakas_id:n ja palvelu_id:n perusteella
            String sql4 = "SELECT concat(palvelu.nimi, ' hinta ', palvelu.hinta, ' lukumaara ', varauksen_palvelut.lkm) AS nimi_hinta_paiva, varaus.asiakas_id FROM varaus, palvelu, varauksen_palvelut WHERE varauksen_palvelut.varaus_id = varaus.varaus_id AND palvelu.palvelu_id = varauksen_palvelut.palvelu_id AND varaus.asiakas_id IN (SELECT varaus.asiakas_id FROM varaus WHERE varaus.varattu_alkupvm BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) AND varaus.toimipiste_id = ?) GROUP BY varaus.asiakas_id, varauksen_palvelut.palvelu_id;";
            // hakee palveluiden kokonaissumman valitulta ajalta ja tietyn toimipisteID:n mukaan, lajittelu asiakas_id perusteella
            String sql5 = "SELECT SUM(palvelu.hinta * varauksen_palvelut.lkm) AS hinta FROM varaus, palvelu, varauksen_palvelut WHERE varauksen_palvelut.varaus_id = varaus.varaus_id AND palvelu.palvelu_id = varauksen_palvelut.palvelu_id AND varaus.asiakas_id IN (SELECT varaus.asiakas_id FROM varaus WHERE varaus.varattu_alkupvm BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) AND varaus.toimipiste_id = ?) GROUP BY varaus.asiakas_id;";
            // luodaan resultSet muuttujat
            ResultSet tulosjoukko = null;
            ResultSet tulosjoukko2 = null;
            ResultSet tulosjoukko3 = null;
            ResultSet tulosjoukko4 = null;
            ResultSet tulosjoukko5 = null;
            // luodaan preparedStatement oliot
            PreparedStatement lause = null;
            PreparedStatement lause2 = null;
            PreparedStatement lause3 = null;
            PreparedStatement lause4 = null;
            PreparedStatement lause5 = null;
		
            try {
            // luodaan PreparedStatement-oliot sql-lauseelle
		        lause = connection.prepareStatement(sql);
                lause2 = connection.prepareStatement(sql2);
                lause3 = connection.prepareStatement(sql3);
                lause4 = connection.prepareStatement(sql4);
                lause5 = connection.prepareStatement(sql5);
		
                lause.setInt( 1, id); // asetetaan ehtoihin arvot
                lause2.setString(1, aPaiva);
                lause2.setString(2, lPaiva);
                lause2.setInt(3, id);
                lause3.setString(1, aPaiva);
                lause3.setString(2, lPaiva);
                lause3.setInt(3, id);
                lause4.setString(1, aPaiva);
                lause4.setString(2, lPaiva);
                lause4.setInt(3, id);
                lause5.setString(1, aPaiva);
                lause5.setString(2, lPaiva);
                lause5.setInt(3, id);
                // suoritetaan sql-lauseet
		        tulosjoukko = lause.executeQuery();
                tulosjoukko2 = lause2.executeQuery();
                tulosjoukko3 = lause3.executeQuery();
                tulosjoukko4 = lause4.executeQuery();
                tulosjoukko5 = lause5.executeQuery();
                // jos ei löydy, heitetaan poikkeus
		if (tulosjoukko == null) {
                    throw new Exception("laskua ei loydy");
		}
            } catch (SQLException se) {
            // SQL virheet
                throw se;
            } catch (Exception e) {
            // JDBC virheet
                throw e;
            }
            // käsitellään resultsetit
            // luodaan tarvittavat muuttujat
            String tulos = "";
            String vertaus = "";
            String vertaus2 = "";
            String paiva = "";
            tulosjoukko.next();
            tulos = tulosjoukko.getString("nimi") + "_" + aPaiva + "-" + lPaiva;
            tulosjoukko.previous();
            // luodaan muistio tiedosto
            File raportti = new File("palvelut_" + tulos  + ".txt");
            raportti.createNewFile();
            // luodaan "kirjoittaja" syotettaville tiedoille
            BufferedWriter output = new BufferedWriter(new FileWriter(raportti));
            // siirrytaan tulosjoukossa eteenpain
            tulosjoukko.next();
            // haetaan tulos muuttujaan nimi tulosjoukko muuttujasta
            tulos = tulosjoukko.getString("nimi");
            tulosjoukko3.next();
            paiva = tulosjoukko3.getString("varattu_alkupvm");
            paiva += " - ";
            paiva += tulosjoukko3.getString("varattu_loppupvm");
            // peruutetaan tulosjoukossa
            tulosjoukko3.previous();
            // kirjoitetaan aiemmin luotuun muistioon
            output.write(tulos + " " + "raportti ajalta " + paiva);
            // kirjoitetaan muistioon pari tyhjaa rivia
            output.write(System.lineSeparator() + System.lineSeparator());
            
            try {
            // looppi jossa kirjoitetaan haettuja tietoja    
		while (tulosjoukko2.next () == true){
            // ensin tulos-muuttujaan lisataan kyseinen tieto johon lisataan tulosjoukko2 saatu nimi
                    tulos = "Asiakas: ";
                    tulos += tulosjoukko2.getString("nimi");
                    // kirjoitetaan tulos ja lisataan yksi tabulaattori painallus
                    output.write(tulos + "\t");
                    tulosjoukko3.next();
                    tulos = tulosjoukko3.getString("varattu_alkupvm");
                    output.write(tulos + "\t");
                    tulosjoukko4.next();
                    // haetaan vertaus muuttujiin tietoa
                    vertaus = tulosjoukko4.getString("asiakas_id");
                    vertaus2 = tulosjoukko4.getString("asiakas_id");
                    output.write(System.lineSeparator() + System.lineSeparator());
                    // kun vertailut ovat samat tehdaan tata looppia niin kauan kuin asiakasID ei vaihdu
                    while(vertaus.equals(vertaus2)){
                        tulos = tulosjoukko4.getString("nimi_hinta_paiva");
                        output.write(tulos + System.lineSeparator());
                        // tassa haetaan vertailu kohde seuraavasta tulosjoukko4 tiedosta
                        if(tulosjoukko4.next() == true){
                        vertaus2 = tulosjoukko4.getString("asiakas_id");
                        }else
                        vertaus2 = "Ei ole";
                    }
                    tulosjoukko4.previous();
                    tulosjoukko5.next();
                    tulos = "Yhteensa " + tulosjoukko5.getString("hinta");
                    output.write(System.lineSeparator());
                    output.write(tulos + " euroa");
                    output.write(System.lineSeparator() + System.lineSeparator());
		}
            }catch (SQLException e) {
		throw e;
            }
            // sulkee kirjoittimen
            output.close();
            output = null;
    }
    /**
     * Hakee tietyn paivan tiedot
     * @param connection tietokantayhteys
     * @param id toimipisteID arvo
     * @param aPaiva aloituspaiva arvo
     * @throws SQLException virheilmoitus
     * @throws Exception virheilmoitus
     */
    public static void raportti2 (Connection connection, int id, String aPaiva) throws SQLException, Exception { // tietokantayhteys välitetään parametrina
                // hakee toimipisteen nimen
                String sql = "SELECT nimi" 
                            + " FROM toimipiste WHERE toimipiste_id = ?";
                // hakee asiakkaiden nimen valitulta ajalta ja tietyn toimipisteID:n mukaan, lajittelu asiakas_id perusteella
                String sql2 = "SELECT concat(etunimi, ' ', sukunimi) AS nimi FROM asiakas WHERE asiakas_id IN (SELECT asiakas_id FROM varaus WHERE DATE(varaus.varattu_alkupvm) = ? AND varaus_id IN(SELECT varaus_id FROM varauksen_palvelut WHERE toimipiste_id = ?)) GROUP BY asiakas_id;";
                // hakee paivamaaran valitulta ajalta tietyltä ajalta ja tietyn toimipisteID:n mukaan, lajittelu asiakas_id perusteella  
                String sql3 = "SELECT DATE(varattu_alkupvm) AS varattu_alkupvm FROM varaus WHERE varaus_id IN (SELECT varaus_id FROM varauksen_palvelut WHERE varaus_id IN(SELECT varaus_id FROM varaus WHERE DATE(varaus.varattu_alkupvm) = ? AND toimipiste_id = ?)) GROUP BY asiakas_id;" ;
                // hakee palveluiden nimen, hinnan ja lukumaaran valitulta ajalta ja tietyn toimipisteID:n mukaan, lajittelu asiakas_id:n ja palvelu_id:n perusteella
                String sql4 = "SELECT concat(palvelu.nimi, ' hinta ', palvelu.hinta, ' lukumaara ', varauksen_palvelut.lkm) AS nimi_hinta_paiva, varaus.asiakas_id FROM varaus, palvelu, varauksen_palvelut WHERE varauksen_palvelut.varaus_id = varaus.varaus_id AND palvelu.palvelu_id = varauksen_palvelut.palvelu_id AND varaus.asiakas_id IN (SELECT varaus.asiakas_id FROM varaus WHERE DATE(varaus.varattu_alkupvm) = ?  AND varaus.toimipiste_id = ?) GROUP BY varaus.asiakas_id, varauksen_palvelut.palvelu_id;";
                // hakee palveluiden kokonaissumman valitulta ajalta ja tietyn toimipisteID:n mukaan, lajittelu asiakas_id perusteella
                String sql5 = "SELECT SUM(palvelu.hinta * varauksen_palvelut.lkm) AS hinta FROM varaus, palvelu, varauksen_palvelut WHERE varauksen_palvelut.varaus_id = varaus.varaus_id AND palvelu.palvelu_id = varauksen_palvelut.palvelu_id AND varaus.asiakas_id IN (SELECT varaus.asiakas_id FROM varaus WHERE DATE(varaus.varattu_alkupvm) = ?  AND varaus.toimipiste_id = ?) GROUP BY varaus.asiakas_id;";
                // luodaan resultSet muuttujat
                ResultSet tulosjoukko = null;
                ResultSet tulosjoukko2 = null;
                ResultSet tulosjoukko3 = null;
                ResultSet tulosjoukko4 = null;
                ResultSet tulosjoukko5 = null;
                // luodaan preparedStatement oliot
                PreparedStatement lause = null;
                PreparedStatement lause2 = null;
                PreparedStatement lause3 = null;
                PreparedStatement lause4 = null;
                PreparedStatement lause5 = null;
            
                try {
                // luo PreparedStatement-oliot sql-lauseelle
                    lause = connection.prepareStatement(sql);
                    lause2 = connection.prepareStatement(sql2);
                    lause3 = connection.prepareStatement(sql3);
                    lause4 = connection.prepareStatement(sql4);
                    lause5 = connection.prepareStatement(sql5);
            
                    lause.setInt( 1, id); // asetetaan ehtoihin arvot
                    lause2.setString(1, aPaiva);
                    lause2.setInt(2, id);
                    lause3.setString(1, aPaiva);
                    lause3.setInt(2, id);
                    lause4.setString(1, aPaiva);
                    lause4.setInt(2, id);
                    lause5.setString(1, aPaiva);
                    lause5.setInt(2, id);
                    // suorita sql-lauseet
                    tulosjoukko = lause.executeQuery();
                    tulosjoukko2 = lause2.executeQuery();
                    tulosjoukko3 = lause3.executeQuery();
                    tulosjoukko4 = lause4.executeQuery();
                    tulosjoukko5 = lause5.executeQuery();
                    // jos ei löydy, heitetaan poikkeus
            if (tulosjoukko == null) {
                        throw new Exception("laskua ei loydy");
            }
                } catch (SQLException se) {
                // SQL virheet
                    throw se;
                } catch (Exception e) {
                // JDBC virheet
                    throw e;
                }
                // käsitellään resultsetit
                // luodaan tarvittavat muuttujat
                String tulos = "";
                String vertaus = "";
                String vertaus2 = "";
                String paiva = "";
                // luodaan muistio tiedosto
                tulosjoukko.next();
                tulos = tulosjoukko.getString("nimi") + "_" + aPaiva;
                tulosjoukko.previous();
                // luodaan muistio tiedosto
                File raportti = new File("palvelut_" + tulos  + ".txt");
                raportti.createNewFile();
                // luodaan "kirjoittaja" syotettaville tiedoille
                BufferedWriter output = new BufferedWriter(new FileWriter(raportti));
                // siirrytaan tulosjoukossa eteenpain
                tulosjoukko.next();
                // haetaan tulos muuttujaan nimi tulosjoukko muuttujasta
                tulos = tulosjoukko.getString("nimi");
                tulosjoukko3.next();
                paiva = tulosjoukko3.getString("varattu_alkupvm");
                // peruutetaan tulosjoukossa
                tulosjoukko3.previous();
                output.write(tulos + " " + "raportti paivalta " + paiva);
                // kirjoitetaan muistioon pari tyhjaa rivia
                output.write(System.lineSeparator() + System.lineSeparator());
                
                try {
                // looppi jossa kirjoitetaan haettuja tietoja
            while (tulosjoukko2.next () == true){
                        // ensin tulos-muuttujaan lisataan kyseinen tieto johon lisataan tulosjoukko2 saatu nimi
                        tulos = "Asiakas: ";
                        tulos += tulosjoukko2.getString("nimi");
                        // kirjoitetaan tulos ja lisataan yksi tabulaattori painallus
                        output.write(tulos + "\t");
                        tulosjoukko3.next();
                        tulos = tulosjoukko3.getString("varattu_alkupvm");
                        output.write(tulos + "\t");
                        tulosjoukko4.next();
                        // haetaan vertaus muuttujiin tietoa
                        vertaus = tulosjoukko4.getString("asiakas_id");
                        vertaus2 = tulosjoukko4.getString("asiakas_id");
                        output.write(System.lineSeparator() + System.lineSeparator());
                        // kun vertaukset ovat samat tehdaan tata looppia niin kauan kuin asiakasID ei vaihdu
                        while(vertaus.equals(vertaus2)){
                            tulos = tulosjoukko4.getString("nimi_hinta_paiva");
                            output.write(tulos + System.lineSeparator());
                            // tassa haetaan vertailu kohde seuraavasta tulosjoukko4 tiedosta
                            if(tulosjoukko4.next() == true){
                            vertaus2 = tulosjoukko4.getString("asiakas_id");
                            }else
                            vertaus2 = "Ei ole";
                        }
                        tulosjoukko4.previous();
                        tulosjoukko5.next();
                        tulos = "Yhteensa " + tulosjoukko5.getString("hinta");
                        output.write(System.lineSeparator());
                        output.write(tulos + " euroa");
                        output.write(System.lineSeparator() + System.lineSeparator());
            }
                }catch (SQLException e) {
            throw e;
                }
                // sulkee kirjoittimen
                output.close();
                output = null;
        }
}
