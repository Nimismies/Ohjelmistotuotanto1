import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Toimipiste {
  
    private int m_toimipiste_id;
    private String m_toimipisteNimi;
    private String m_lahiosoite;
    private String m_postitoimipaikka;
    private int m_postinro;
    private String m_email;
    private int m_puhelin;
    
public Toimipiste() {

    }
    public int getM_toimipiste_id() {
        return m_toimipiste_id;
    }
    public void setM_toimipiste_id(int m_toimipiste_id) {
        this.m_toimipiste_id = m_toimipiste_id;
    }
    public String getM_toimipisteNimi() {
        return m_toimipisteNimi;
    }
    public void setM_toimipisteNimi(String m_toimipisteNimi) {
        this.m_toimipisteNimi = m_toimipisteNimi;
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
    public int getM_postinro() {
        return m_postinro;
    }
    public void setM_postinro(int m_postinro) {
        this.m_postinro = m_postinro;
    }
    public String getM_email() {
        return m_email;
    }
    public void setM_email(String m_email) {
        this.m_email = m_email;
    }
    public int getM_puhelin() {
        return m_puhelin;
    }
    public void setM_puhelin(int m_puhelin) {
        this.m_puhelin = m_puhelin;
    }



    @Override
    public String toString(){
        return (m_toimipiste_id + " " + m_toimipisteNimi + " " + m_lahiosoite + " "+ m_postitoimipaikka + " " + m_postinro + " " + m_email + " " + m_puhelin );  //ToString lause
    }



    public static  Toimipiste haeToimipiste (Connection connection, int id1) throws SQLException, Exception { // tietokantayhteys valitetaan parametrina
		// haetaan tietokannasta toimipistettä, jonka mokki_id = id ja toimipiste_id = id2 
                
		String sql = "SELECT toimipiste_id, nimi, lahiosoite, postitoimipaikka, postinro, email, puhelinnro"
                            + " FROM toimipiste WHERE toimipiste_id = ? "; // ehdon arvo asetetaan jaljenpana
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			lause.setInt(1, id1); // asetetaan where ehtoon (?) arvo
			tulosjoukko = lause.executeQuery();
			if (tulosjoukko == null) {
				throw new Exception("Toimipistettä ei loydy");
                   
			}
		} catch (SQLException se) {
            // SQL virheet
                        throw se;
                } catch (Exception e) {
            // JDBC virheet
                        throw e;
		}
		// kasitellaan resultset - laitetaan tiedot ToimipisteOlio
		Toimipiste ToimipisteOlio = new Toimipiste();
		
		try {
			if (tulosjoukko.next () == true){
				//opiskelijaid, kurssiid, arvosana ja suorituspvm
                ToimipisteOlio.setM_toimipiste_id(tulosjoukko.getInt("toimipiste_id"));
				ToimipisteOlio.setM_toimipisteNimi(tulosjoukko.getString("nimi"));
				ToimipisteOlio.setM_lahiosoite(tulosjoukko.getString("lahiosoite"));
				ToimipisteOlio.setM_postitoimipaikka(tulosjoukko.getString("postitoimipaikka"));
                ToimipisteOlio.setM_postinro(tulosjoukko.getInt("postinro"));
                ToimipisteOlio.setM_email(tulosjoukko.getString("email"));
                ToimipisteOlio.setM_puhelin(tulosjoukko.getInt("puhelinnro"));

			}
			
		}catch (SQLException e) {
			throw e;
		}
		// palautetaan ToimipisteOlio
		
		return ToimipisteOlio;
	}



    public int lisaaToimipiste(Connection connection) throws SQLException, Exception { // tietokantayhteys valitetaan parametrina
        // haetaan tietokannasta Toimipistetta, jonka toimipiste_id = id
            
    String sql = "SELECT toimipiste_id"
                        + " FROM toimipiste WHERE toimipiste_id = ?"; // ehdon arvo asetetaan jaljenpana
    ResultSet tulosjoukko = null;
    PreparedStatement lause = null; 
    
    try {
        // luo PreparedStatement-olio sql-lauseelle
        lause = connection.prepareStatement(sql);
        lause.setInt(1, getM_toimipiste_id()); // asetetaan where ehtoon (?) arvo, olion Toimipiste ja toimipiste ID
                 // suorita sql-lause
        tulosjoukko = lause.executeQuery();	
        if (tulosjoukko.next () == true) { // Toimipiste loytyi
            throw new Exception("Toimipiste on jo olemassa");
        }
    } catch (SQLException se) {
        // SQL virheet
                throw se;
            } catch (Exception e) {
        // JDBC virheet
                throw e;
    }
    // parsitaan INSERT
    sql = "INSERT INTO toimipiste "
    + "(toimipiste_id, nimi, lahiosoite, postitoimipaikka, postinro, email, puhelinnro) "
    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
    // System.out.println("Lisataan " + sql);
    lause = null;
    try {
        // luo PreparedStatement-olio sql-lauseelle
        lause = connection.prepareStatement(sql);
        // laitetaan arvot INSERTtiin
        lause.setInt( 1, getM_toimipiste_id());
        lause.setString( 2, getM_toimipisteNimi());
        lause.setString( 3 , getM_lahiosoite()); 
        lause.setString( 4, getM_postitoimipaikka());
        lause.setInt( 5, getM_postinro()); 
        lause.setString( 6, getM_email()); 
        lause.setInt( 7, getM_puhelin()); 
        // suorita sql-lause
        int lkm = lause.executeUpdate();	
    //	System.out.println("lkm " + lkm);
        if (lkm == 0) {
            throw new Exception("Toimipisteen lisaaminen ei onnistu");
        }
    } catch (SQLException se) {
        // SQL virheet
        throw se;
    } catch (Exception e) {
        // JDBC ym. virheet
        throw e;
    }
    return 0;
}



public int muutaToimipiste (Connection connection) throws SQLException, Exception { // tietokantayhteys vÃ¤litetÃ¤Ã¤n parametrina
    // haetaan tietokannasta Mokkia, jonka mokki_id = id ja toimipiste_id = id2 
        
String sql = "SELECT toimipiste_id"
                    + " FROM toimipiste WHERE toimipiste_id = ?"; // ehdon arvo asetetaan jaljenpana
ResultSet tulosjoukko = null;
PreparedStatement lause = null;
try {
    // luo PreparedStatement-olio sql-lauseelle
    lause = connection.prepareStatement(sql);
    lause.setInt(1, getM_toimipiste_id()); // asetetaan where ehtoon (?) arvo
    // suorita sql-lause
    tulosjoukko = lause.executeQuery();	
    if (tulosjoukko.next () == false) { // toimipistetta ei loytynyt
        throw new Exception("Toimipistetta ei loydy tietokannasta");
    }
} catch (SQLException se) {
    // SQL virheet
            throw se;
        } catch (Exception e) {
    // JDBC virheet
            throw e;
}
// parsitaan Update, paivitetaan tiedot lukuunottamatta avainta
sql = "UPDATE toimipiste "
+ "SET nimi = ?, lahiosoite = ?, postitoimipaikka = ?, postinro = ?, email = ?, puhelinnro = ? "
+ " WHERE toimipiste_id = ?";

lause = null;
try {
    // luo PreparedStatement-olio sql-lauseelle
    lause = connection.prepareStatement(sql);
    
    // laitetaan olion attribuuttien arvot UPDATEen
    lause.setString(1, getM_toimipisteNimi()); 
    lause.setString(2, getM_lahiosoite()); 
    lause.setString(3, getM_postitoimipaikka());
    lause.setInt(4, getM_postinro());
    lause.setString(5, getM_email());
    lause.setInt(6, getM_puhelin());
    // where-ehdon arvo
    lause.setInt(7, getM_toimipiste_id());
    // suorita sql-lause
    int lkm = lause.executeUpdate();	
    if (lkm == 0) {
        throw new Exception("Toimipisteen tietojen muuttaminen ei onnistu");
    }
} catch (SQLException se) {
    // SQL virheet
        throw se;
} catch (Exception e) {
    // JDBC ym. virheet
        throw e;
}
return 0; // toiminto ok
}



public int poistaToimipiste (Connection connection) throws SQLException, Exception { // tietokantayhteys valitetaan parametrina
		
		// parsitaan DELETE
		String sql = "DELETE FROM toimipiste WHERE toimipiste_id = ?";
		PreparedStatement lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			// laitetaan arvot DELETEn WHERE-ehtoon
                        lause.setInt(1, getM_toimipiste_id());
			
			// suorita sql-lause
			int lkm = lause.executeUpdate();	
			if (lkm == 0) {
				throw new Exception("Toimipisteen tietojen poistaminen ei onnistu");
			}
			} catch (SQLException se) {
            // SQL virheet
                throw se;
             } catch (Exception e) {
            // JDBC virheet
                throw e;
		}
		return 0; // toiminto ok
	}

    
}