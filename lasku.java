import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class lasku {
  
    private int m_lasku_id;
    private int m_varaus_id;
    private int m_asiakas_id;
    private String m_nimi;
    private String m_lahiosoite;
    private String m_postitoimipaikka;
    private String m_postinro;
    private double m_summa;
    
public lasku() {
    
}
        
    public int getM_lasku_id() {
        return m_lasku_id;
    }

    public void setM_lasku_id(int m_lasku_id) {
        this.m_lasku_id = m_lasku_id;
    }

    public int getM_varaus_id() {
        return m_varaus_id;
    }

    public void setM_varaus_id(int m_varaus_id) {
        this.m_varaus_id = m_varaus_id;
    }

    public int getM_asiakas_id() {
        return m_asiakas_id;
    }

    public void setM_asiakas_id(int m_asiakas_id) {
        this.m_asiakas_id = m_asiakas_id;
    }

    public String getM_nimi() {
        return m_nimi;
    }

    public void setM_nimi(String m_nimi) {
        this.m_nimi = m_nimi;
    }

    public String getM_lahiosoite() {
        return m_lahiosoite;
    }

    public void setM_lahiosoite(String m_lahiosoite) {
        this.m_lahiosoite = m_lahiosoite;
    }

    public String getM_postitoimipaikka() {
        return m_postitoimipaikka;
    }

    public void setM_postitoimipaikka(String m_postitoimipaikka) {
        this.m_postitoimipaikka = m_postitoimipaikka;
    }

    public String getM_postinro() {
        return m_postinro;
    }

    public void setM_postinro(String m_postinro) {
        this.m_postinro = m_postinro;
    }

    public double getM_summa() {
        return m_summa;
    }

    public void setM_summa(Double m_summa) {
        this.m_summa = m_summa;
    }
 @Override
    public String toString(){
        return (m_lasku_id + " " + m_varaus_id + " " + m_asiakas_id + " "+ m_nimi + " " + m_lahiosoite + " " + m_postitoimipaikka + " " + m_postinro + " " + m_summa);  //ToString lause
    }


    public static  lasku haeLasku (Connection connection, int id1) throws SQLException, Exception { 
	
                
		String sql = "SELECT lasku_id, varaus_id, asiakas_id, nimi, lahiosoite, postitoimipaikka, postinro, summa"
                            + " FROM lasku WHERE lasku_id = ? "; 
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		try {
			
			lause = connection.prepareStatement(sql);
			lause.setInt(1, id1); 
			tulosjoukko = lause.executeQuery();
			if (tulosjoukko == null) {
				throw new Exception("laskua ei loydy");
                   
			}
		} catch (SQLException se) {
           
                        throw se;
                } catch (Exception e) {
         
                        throw e;
		}
		
		lasku laskuOlio = new lasku();
		
		try {
			if (tulosjoukko.next () == true){
				
                laskuOlio.setM_lasku_id(tulosjoukko.getInt("lasku_id"));
		laskuOlio.setM_varaus_id(tulosjoukko.getInt("varaus_id"));
		laskuOlio.setM_asiakas_id(tulosjoukko.getInt("asiakas_id"));
		laskuOlio.setM_nimi(tulosjoukko.getString("nimi"));
                laskuOlio.setM_lahiosoite(tulosjoukko.getString("lahiosoite"));
                laskuOlio.setM_postitoimipaikka(tulosjoukko.getString("postitoimipaikka"));
                laskuOlio.setM_postinro(tulosjoukko.getString("postinro"));
                laskuOlio.setM_summa(tulosjoukko.getDouble("summa"));

			}
			
		}catch (SQLException e) {
			throw e;
		}
		
		
		return laskuOlio;
	}


    /**
     * Lisaa laskuun automaattisesti tiedot
     * @param connection tietokantayhteys
     * @return "varmistus"
     * @throws SQLException virheilmoitus
     * @throws Exception virheilmoitus
     */
    public int lisaaLasku(Connection connection) throws SQLException, Exception { 
      
    // haetaan laskuID        
    String sql = "SELECT lasku_id"
                        + " FROM lasku WHERE lasku_id = ?";
    // haetaan asiakkaan tiedot asiakasID perusteella
    String sql2 = "SELECT CONCAT(etunimi, ' ', sukunimi) as nimi, lahiosoite, postitoimipaikka, postinro FROM asiakas WHERE asiakas_id = ? ";
    // haetaan mokkien yhteishinta asiakasID ja varausID perusteella
    String sql3 = "SELECT SUM(mokki.hinta * varauksen_mokit.lkm) AS hinta FROM varaus, mokki, varauksen_mokit WHERE varauksen_mokit.varaus_id = varaus.varaus_id AND mokki.mokki_id = varauksen_mokit.mokki_id AND varaus.asiakas_id = ? AND varaus.varaus_id = ?";
    // haetaan palveluidenn yhteishinta asiakasID ja varausID perusteella
    String sql4 = "SELECT SUM(palvelu.hinta * varauksen_palvelut.lkm) AS hinta FROM varaus, palvelu, varauksen_palvelut WHERE varauksen_palvelut.varaus_id = palvelu.palvelu_id AND palvelu.palvelu_id = varauksen_palvelut.palvelu_id AND varaus.asiakas_id = ? AND varaus.varaus_id = ?";
    // luodaan resultset muuttujat
    ResultSet tulosjoukko = null;
    ResultSet tulosjoukko2 = null;
    ResultSet tulosjoukko3 = null;
    ResultSet tulosjoukko4 = null;
    // luodaan preparestatement muuttujat
    PreparedStatement lause = null;
    PreparedStatement lause2 = null;
    PreparedStatement lause3 = null;
    PreparedStatement lause4 = null;
    
    try {
        // syotetaan sql lauseet paikoilleen
        lause = connection.prepareStatement(sql);
        lause2 = connection.prepareStatement(sql2);
        lause3 = connection.prepareStatement(sql3);
        lause4 = connection.prepareStatement(sql4);
        // syotetaan tarvittavat hakuehdot
        lause.setInt(1, getM_lasku_id());
        lause2.setInt(1, getM_asiakas_id()); 
        lause3.setInt(1, getM_asiakas_id()); 
        lause3.setInt(2, getM_varaus_id()); 
        lause4.setInt(1, getM_asiakas_id());
        lause4.setInt(2, getM_varaus_id());  
        // suoritetaan sql lauseet        
        tulosjoukko = lause.executeQuery();
        tulosjoukko2 = lause2.executeQuery();
        tulosjoukko3 = lause3.executeQuery();
        tulosjoukko4 = lause4.executeQuery();
        // mennaan tuloksissa eteenpain
        tulosjoukko2.next();
        tulosjoukko3.next();
        tulosjoukko4.next();
        // haetaan tuloksista tietoja
        m_nimi = tulosjoukko2.getString("nimi");
        m_lahiosoite = tulosjoukko2.getString("lahiosoite");
        m_postitoimipaikka = tulosjoukko2.getString("postitoimipaikka");
        m_postinro = tulosjoukko2.getString("postinro");
        m_summa = tulosjoukko3.getDouble("hinta") + tulosjoukko4.getDouble("hinta");	
        // jos lasku on olemassa, tulee virhe
        if (tulosjoukko.next () == true) { 
            throw new Exception("Lasku on jo olemassa");
        }
    } catch (SQLException se) {
     
                throw se;
            } catch (Exception e) {
      
                throw e;
    }
    // kasataan sql lause jolla tiedot syotetaan
    sql = "INSERT INTO lasku "
    + "(lasku_id, varaus_id, asiakas_id, nimi, lahiosoite, postitoimipaikka, postinro, summa) "
    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
 
    lause = null;
    try {

        lause = connection.prepareStatement(sql);

        lause.setInt   ( 1, getM_lasku_id());
        lause.setInt( 2, getM_varaus_id());
        lause.setInt( 3 , getM_asiakas_id()); 
        lause.setString( 4, getM_nimi());
        lause.setString( 5, getM_lahiosoite()); 
        lause.setString( 6, getM_lahiosoite()); 
        lause.setString( 7, getM_postinro()); 
        lause.setDouble   ( 8, getM_summa());
        // tarkastetaan toimiiko laskun lisays
        int lkm = lause.executeUpdate();	
        if (lkm == 0) {
            throw new Exception("Laskun lisaaminen ei onnistu");
        }
    } catch (SQLException se) {
        throw se;
    } catch (Exception e) {
        throw e;
    }
    return 0;
}



public int muutaLasku (Connection connection) throws SQLException, Exception { 
  
        
String sql = "SELECT lasku_id"
                    + " FROM lasku WHERE lasku_id = ?"; 
ResultSet tulosjoukko = null;
PreparedStatement lause = null;
try {
    lause = connection.prepareStatement(sql);
    lause.setInt(1, getM_lasku_id()); 

    tulosjoukko = lause.executeQuery();	
    if (tulosjoukko.next () == false) { // toimipistetta ei loytynyt
        throw new Exception("Laskua ei loydy tietokannasta");
    }
} catch (SQLException se) {

            throw se;
        } catch (Exception e) {

            throw e;
}

sql = "UPDATE lasku "
+ "SET nimi = ?, lahiosoite = ?, postitoimipaikka = ?, postinro = ?, summa = ? " //varaus_id = ?, asikas_id = ?, 
+ " WHERE lasku_id = ?";

lause = null;
try {
    lause = connection.prepareStatement(sql);
    
        //lause.setInt( 1, getM_varaus_id());
        //lause.setInt( 2 , getM_asiakas_id()); 
        lause.setString( 1, getM_nimi());
        lause.setString( 2, getM_lahiosoite()); 
        lause.setString( 3, getM_postitoimipaikka()); 
        lause.setString( 4, getM_postinro()); 
        lause.setDouble   ( 5, getM_summa());

    lause.setInt(6, getM_lasku_id());
    int lkm = lause.executeUpdate();	
    if (lkm == 0) {
        throw new Exception("Laskun tietojen muuttaminen ei onnistu");
    }
} catch (SQLException se) {
        throw se;
} catch (Exception e) {
        throw e;
}
return 0;
}



public int poistaLasku (Connection connection) throws SQLException, Exception { 
		

		String sql = "DELETE FROM lasku WHERE lasku_id = ?";
		PreparedStatement lause = null;
		try {
			lause = connection.prepareStatement(sql);
                        lause.setInt(1, getM_lasku_id());
			
			int lkm = lause.executeUpdate();	
			if (lkm == 0) {
				throw new Exception("Laskun tietojen poistaminen ei onnistu");
			}
			} catch (SQLException se) {
                throw se;
             } catch (Exception e) {
                throw e;
		}
		return 0; 
    }
    /**
     * Sahkoposti viestin kasaamis metodi
     * @param connection tietokantayhteys
     * @param id asiakasID
     * @param id2 varausID
     * @param id3 laskuID
     * @throws SQLExpection virheilmoitus
     * @throws Expection virheilmitus
     */
    public static String laheta (Connection connection, int id, int id2, int id3) throws SQLException, Exception { // tietokantayhteys välitetään parametrina
                // haetaan laskun tiedot laskuID perusteella
		        String sql = "SELECT concat(lahiosoite, '%0A', postitoimipaikka, '%0A', postinro, '%0A') AS lasku, summa"
                            + " FROM lasku WHERE lasku_id = ? ";
                // haetaan palveluiden summa ja samalla haetaan aloitus/lopetus paivamaarat asiakasID ja varausID perusteella
                String sql2 = "SELECT concat(palvelu.nimi, '%20', palvelu.hinta, '%20', varauksen_palvelut.lkm) AS nimi_hinta_paiva, concat(DATE(varaus.varattu_alkupvm),'-',DATE(varaus.varattu_loppupvm)) AS aika FROM varaus, palvelu, varauksen_palvelut WHERE varauksen_palvelut.varaus_id = varaus.varaus_id AND palvelu.palvelu_id = varauksen_palvelut.palvelu_id AND varaus.asiakas_id = ? AND varaus.varaus_id = ?";
                // haetaan mokkien summa asiakasID ja varausID perusteella
                String sql3 = "SELECT concat(mokki.nimi, '%20', mokki.hinta, '%20', varauksen_mokit.lkm) AS nimi_hinta_paiva FROM varaus, mokki, varauksen_mokit WHERE varauksen_mokit.varaus_id = varaus.varaus_id AND mokki.mokki_id = varauksen_mokit.mokki_id AND varaus.asiakas_id = ? AND varaus.varaus_id = ?";
                // haetaan asiakkaan nimi ja sahkoposti asiakasID perusteella
                String sql4 = "SELECT etunimi, sukunimi, email FROM asiakas WHERE asiakas_id = ?";
                // haetaan toimipisteen tiedot toimipisteID perusteella
                String sql5 = "SELECT concat(nimi,'%0A',lahiosoite,'%0A',postitoimipaikka,'%0A',postinro,'%0A',email,'%0A',puhelinnro) AS toimipiste FROM toimipiste WHERE toimipiste_id = (SELECT toimipiste_id FROM varaus WHERE asiakas_id = ? AND varaus_id = ?);";
                // luodaan resultset muuttujat
                ResultSet tulosjoukko = null;
                ResultSet tulosjoukko2 = null;
                ResultSet tulosjoukko3 = null;
                ResultSet tulosjoukko4 = null;
                ResultSet tulosjoukko5 = null;
                // luodaan preparedstatement muuttujat
                PreparedStatement lause = null;
                PreparedStatement lause2 = null;
                PreparedStatement lause3 = null;
                PreparedStatement lause4 = null;
                PreparedStatement lause5 = null;
            
        try {
                // syotetaan sql lauseet paikoilleen
            lause = connection.prepareStatement(sql);
            lause2 = connection.prepareStatement(sql2);
            lause3 = connection.prepareStatement(sql3);
            lause4 = connection.prepareStatement(sql4);
            lause5 = connection.prepareStatement(sql5);
            // asetetaan ehdot paikoilleen
            lause.setInt( 1, id3); 
            lause2.setInt( 1, id);
            lause2.setInt(2, id2);
            lause3.setInt( 1, id);
            lause3.setInt(2, id2);
            lause4.setInt( 1, id);
            lause5.setInt(1, id);
            lause5.setInt(2, id2);
            // suorita sql-lauseet
            tulosjoukko = lause.executeQuery();	
            tulosjoukko2 = lause2.executeQuery();
            tulosjoukko3 = lause3.executeQuery();
            tulosjoukko4 = lause4.executeQuery();
            tulosjoukko5 = lause5.executeQuery();
            // tarkastetaan loytyyko tiedot
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
                // tulos muuttuja johon laitetaan tiedot
                String tulos = "";
            
                try {
            while (tulosjoukko.next () == true){
                        // siirrytaan tuloksissa eteenpain
                        tulosjoukko4.next();
                        tulosjoukko2.next();
                        tulosjoukko5.next();
                        // syotetaan tulokseen tiedot jotka haetaan GUIssa mailto muuttujaan
                        tulos += "mailto:" + (tulosjoukko4.getString("email"));
                        tulos += "?subject=Village%20People%20Lasku%20ajalta%20" + tulosjoukko2.getString("aika");
                        tulos += "&body=Laskun%20lahettaja:%0A" + tulosjoukko5.getString("toimipiste") + "%0A%0A";
                        tulos += "Laskun%20saaja:%0A";
                        tulos += (tulosjoukko4.getString("etunimi") + "%0A");
                        tulos += (tulosjoukko4.getString("sukunimi") + "%0A");
                        tulos += (tulosjoukko.getString("lasku") + "%0A");
                        tulos += "Palvelujen%20hinnat:" + "%0A%0A";
                        tulosjoukko2.previous();
                        while(tulosjoukko2.next() == true){
                            tulos += (tulosjoukko2.getString("nimi_hinta_paiva") + "%0A");
                        }
                        tulos += "%0A" + "Mokkien%20hinnat:" + "%0A%0A";
                        while(tulosjoukko3.next() == true){
                            tulos += (tulosjoukko3.getString("nimi_hinta_paiva") + "%20" + "%0A");
                        }
                        tulos += "%0A" + "Laskun%20summa%20yhteensa" + "%20" + (tulosjoukko.getString("summa"));
            }
                }catch (SQLException e) {
            throw e;
                }
        // palautetaan tulos
                return tulos;
        }
        /**
         * Tulostukseen kaytetty metodi
         * @param connection tietokantayhteys
         * @param id asiakasID
         * @param id2 varausID
         * @param id3 laskuID
         * @return palauttaa haetut tiedot
         * @throws SQLException virheilmoitus
         * @throws Exception virheilmoitus
         */
        public static String tulosta (Connection connection, int id, int id2, int id3) throws SQLException, Exception { // tietokantayhteys välitetään parametrina
                    // haetaan laskun tiedot laskuID perusteella
                    String sql = "SELECT lahiosoite, postitoimipaikka, postinro, summa"
                                + " FROM lasku WHERE lasku_id = ? ";
                    // haetaan palveluiden tietoja ja samalla varauksen alku/loppu paivamaara asiakasID ja varausID perusteella
                    String sql2 = "SELECT palvelu.nimi, palvelu.hinta, varauksen_palvelut.lkm, concat(DATE(varaus.varattu_alkupvm),'-',DATE(varaus.varattu_loppupvm)) AS aika FROM varaus, palvelu, varauksen_palvelut WHERE varauksen_palvelut.varaus_id = varaus.varaus_id AND palvelu.palvelu_id = varauksen_palvelut.palvelu_id AND varaus.asiakas_id = ? AND varaus.varaus_id = ?";
                    // haetaan mokkien tietoja asiakasID ja varausID perusteella
                    String sql3 = "SELECT mokki.nimi, mokki.hinta, varauksen_mokit.lkm FROM varaus, mokki, varauksen_mokit WHERE varauksen_mokit.varaus_id = varaus.varaus_id AND mokki.mokki_id = varauksen_mokit.mokki_id AND varaus.asiakas_id = ? AND varaus.varaus_id = ?";
                    // haetaan asiakkaan tietoja asiakasID perusteella
                    String sql4 = "SELECT etunimi, sukunimi, email FROM asiakas WHERE asiakas_id = ?";
                    // haetaan toimipisteen tietoja toimipisteID perusteella
                    String sql5 = "SELECT nimi, lahiosoite, postitoimipaikka, postinro, email, puhelinnro FROM toimipiste WHERE toimipiste_id = (SELECT toimipiste_id FROM varaus WHERE asiakas_id = ? AND varaus_id = ?);";
                    // luodaan resultset muuttujat
                    ResultSet tulosjoukko = null;
                    ResultSet tulosjoukko2 = null;
                    ResultSet tulosjoukko3 = null;
                    ResultSet tulosjoukko4 = null;
                    ResultSet tulosjoukko5 = null;
                    // luodaan preparedstatement muuttujat
                    PreparedStatement lause = null;
                    PreparedStatement lause2 = null;
                    PreparedStatement lause3 = null;
                    PreparedStatement lause4 = null;
                    PreparedStatement lause5 = null;
                
            try {
                // laitetaan sql lauseet paikoilleen
                lause = connection.prepareStatement(sql);
                lause2 = connection.prepareStatement(sql2);
                lause3 = connection.prepareStatement(sql3);
                lause4 = connection.prepareStatement(sql4);
                lause5 = connection.prepareStatement(sql5);
                // asetetaan arvot ehtoihin
                lause.setInt( 1, id3);
                lause2.setInt( 1, id);
                lause2.setInt(2, id2);
                lause3.setInt( 1, id);
                lause3.setInt(2, id2);
                lause4.setInt( 1, id);
                lause5.setInt(1, id);
                lause5.setInt(2, id2);
                // suorita sql-lauseet
                tulosjoukko = lause.executeQuery();	
                tulosjoukko2 = lause2.executeQuery();
                tulosjoukko3 = lause3.executeQuery();
                tulosjoukko4 = lause4.executeQuery();
                tulosjoukko5 = lause5.executeQuery();
                // tarkastetaan loytyyko
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
                    // tulos muuttuja johon tiedot syotetaan
                    String tulos = "";
                
                    try {
                while (tulosjoukko.next () == true){
                            // siirrytaan tuloksissa eteenpain
                            tulosjoukko4.next();
                            tulosjoukko2.next();
                            tulosjoukko5.next();
                            // viedaan tulos muuttujaan tiedot
                            tulos += "Village People Lasku ajalta "  + tulosjoukko2.getString("aika") + System.lineSeparator();
                            tulos += "Laskun lahettaja: " + System.lineSeparator() + tulosjoukko5.getString("nimi") + System.lineSeparator() + tulosjoukko5.getString("lahiosoite") + System.lineSeparator() + tulosjoukko5.getString("postitoimipaikka") + System.lineSeparator() + tulosjoukko5.getString("postinro") + System.lineSeparator() + tulosjoukko5.getString("email") + System.lineSeparator() + tulosjoukko5.getString("puhelinnro") + System.lineSeparator();
                            tulos += "Laskun saaja:" + System.lineSeparator() + tulosjoukko4.getString("etunimi") + System.lineSeparator() + tulosjoukko4.getString("sukunimi") + System.lineSeparator() + tulosjoukko.getString("lahiosoite") + System.lineSeparator() + tulosjoukko.getString("postitoimipaikka") + System.lineSeparator() +  tulosjoukko.getString("postinro") + System.lineSeparator() + tulosjoukko.getString("summa") + System.lineSeparator();
                            tulos += "Palvelujen hinnat:" + System.lineSeparator() + System.lineSeparator();
                            // siirrytaan tulosjoukossa taaksepain
                            tulosjoukko2.previous();
                            // loopataan tietoja irti
                            while(tulosjoukko2.next() == true){
                                tulos += tulosjoukko2.getString("nimi") + " " + tulosjoukko2.getString("hinta") + " " + tulosjoukko2.getString("lkm") + System.lineSeparator() + System.lineSeparator();
                            }
                            tulos += "Mokkien hinnat:" + System.lineSeparator();
                            // loopataan tietoja irti
                            while(tulosjoukko3.next() == true){
                                tulos += tulosjoukko3.getString("nimi") + " " + tulosjoukko3.getString("hinta") + " " + tulosjoukko3.getString("lkm") + System.lineSeparator() + System.lineSeparator();

                            }
                            tulos += "Laskun summa yhteensa " + (tulosjoukko.getString("summa"));
                }
                    }catch (SQLException e) {
                throw e;
                    }
            // palautetaan tiedot
                    return tulos;
            }
}

    

