

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * kurssi.java
 * @author ville
 * nÃ¤yttÃ¤Ã¤, lisÃ¤Ã¤, poistaa, muuttaa kurssin tietoja
 */
public class asiakasHallinta {
  
        private int m_asiakas_id;
        private String m_etunimi;
        private String m_sukunimi;
        private String m_lahiosoite;
        private String m_postitoimipaikka;
        private int m_postinro;
        private String m_email;
	    private int m_puhelinnumero;
        
    /**
     *
     */
    public asiakasHallinta() {
        
        
    }

    public int getM_asiakas_id() {
        return m_asiakas_id;
    }

    public void setM_asiakas_id(int m_asiakas_id) {
        this.m_asiakas_id = m_asiakas_id;
    }

    public String getM_etunimi() {
        return m_etunimi;
    }

    public void setM_etunimi(String m_etunimi) {
        this.m_etunimi = m_etunimi;
    }

    public String getM_sukunimi() {
        return m_sukunimi;
    }

    public void setM_sukunimi(String m_sukunimi) {
        this.m_sukunimi = m_sukunimi;
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
    public int getM_puhelinnumero() { 
        return m_puhelinnumero;
    }

    public void setM_puhelinnumero(int m_puhelinnumero) {
        this.m_puhelinnumero = m_puhelinnumero;
    }
   
        @Override
    public String toString(){
        return (m_asiakas_id + " " + m_etunimi + " " + m_sukunimi + " "+  m_lahiosoite + " " + m_postitoimipaikka + " " + m_postinro + " " + m_email + " " + m_puhelinnumero);  //ToString lause
    }
    

     
 public static  asiakasHallinta haeAsiakas (Connection connection, int id1) throws SQLException, Exception { // tietokantayhteys vÃ¤litetÃ¤Ã¤n parametrina

                
		String sql = "SELECT asiakas_id, etunimi, sukunimi, lahiosoite, postitoimipaikka, postinro, email, puhelinnro"
                            + " FROM asiakas WHERE asiakas_id = ?";  // ehdon arvo asetetaan jÃ¤ljempÃ¤nÃ¤  = ? AND toimipiste_id = ?";
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			lause.setInt(1, id1);
			tulosjoukko = lause.executeQuery();
			if (tulosjoukko == null) {
				throw new Exception("Asiakasta ei loydy");
                   
			}
		} catch (SQLException se) {
            // SQL virheet
                        throw se;
                } catch (Exception e) {
            // JDBC virheet
                        throw e;
		}
		// kÃ¤sitellÃ¤Ã¤n resultset - laitetaan tiedot Opintosuoritusoliolle
		asiakasHallinta asiakasHallintaOlio = new asiakasHallinta ();
		
		try {
			if (tulosjoukko.next () == true){
			
                asiakasHallintaOlio.setM_asiakas_id(tulosjoukko.getInt("asiakas_id"));
		asiakasHallintaOlio.setM_etunimi (tulosjoukko.getString("etunimi"));
		asiakasHallintaOlio.setM_sukunimi (tulosjoukko.getString("sukunimi"));
		asiakasHallintaOlio.setM_lahiosoite(tulosjoukko.getString("lahiosoite"));
                asiakasHallintaOlio.setM_postitoimipaikka(tulosjoukko.getString("postitoimipaikka"));
                asiakasHallintaOlio.setM_postinro(tulosjoukko.getInt("postinro"));
                asiakasHallintaOlio.setM_email(tulosjoukko.getString("email"));
                asiakasHallintaOlio.setM_puhelinnumero(tulosjoukko.getInt("puhelinnro"));

			}
			
		}catch (SQLException e) {
			throw e;
		}
		// palautetaan Opintosuoritusolio
		
		return asiakasHallintaOlio;
	}



 public int lisaaAsiakas(Connection connection) throws SQLException, Exception { // tietokantayhteys vÃ¤litetÃ¤Ã¤n parametrina

                
		String sql = "SELECT asiakas_id" 
                            + " FROM asiakas WHERE asiakas_id = ?"; 
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null; 
		
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			lause.setInt(1, getM_asiakas_id()); // asetetaan where ehtoon (?) arvo, olion Mokki ja toimipiste ID
			tulosjoukko = lause.executeQuery();	
			if (tulosjoukko.next () == true) { // Mokki loytyi
				throw new Exception("Asiakas on jo olemassa");
			}
		} catch (SQLException se) {
            // SQL virheet
                    throw se;
                } catch (Exception e) {
            // JDBC virheet
                    throw e;
		}
		// parsitaan INSERT
		sql = "INSERT INTO asiakas "
		+ "(asiakas_id, etunimi, sukunimi, lahiosoite, postitoimipaikka, postinro, email, puhelinnro) "
		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
            lause = connection.prepareStatement(sql);
			// laitetaan arvot INSERTtiin
            lause.setInt( 1, getM_asiakas_id());
            lause.setString( 2, getM_etunimi());
            lause.setString( 3 , getM_sukunimi()); 
            lause.setString( 4, getM_lahiosoite());
            lause.setString( 5, getM_postitoimipaikka()); 
            lause.setInt( 6, getM_postinro()); 
            lause.setString( 7, getM_email()); 
            lause.setInt( 8, getM_puhelinnumero()); 
			int asiakas_id = lause.executeUpdate();	
			if (asiakas_id == 0) {
				throw new Exception("Asiakkaan lisaaminen ei onnistu");
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

  
    public int muutaAsiakas (Connection connection) throws SQLException, Exception { // tietokantayhteys vÃ¤litetÃ¤Ã¤n parametrina
			
                
		String sql = "SELECT asiakas_id"
                            + " FROM asiakas WHERE asiakas_id = ?"; 
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
                        lause.setInt(1, getM_asiakas_id());
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			if (tulosjoukko.next () == false) { // Suoritusta ei lÃ¶ytynyt
				throw new Exception("Asiakasta ei loydy tietokannasta");
			}
		} catch (SQLException se) {
            // SQL virheet
                    throw se;
                } catch (Exception e) {
            // JDBC virheet
                    throw e;
		}
		// parsitaan Update, pÃ¤iviteÃ¤Ã¤n tiedot lukuunottamatta avainta
		sql = "UPDATE asiakas "
		+ "SET etunimi = ?, sukunimi = ?, lahiosoite = ?, postitoimipaikka = ?, postinro = ?, email = ?, puhelinnro = ?"
		+ " WHERE asiakas_id = ?"; //AND toimipiste_id = ?";
		
		lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			
			// laitetaan olion attribuuttien arvot UPDATEen
            lause.setString( 1, getM_etunimi());
            lause.setString( 2 , getM_sukunimi()); 
            lause.setString( 3, getM_lahiosoite());
            lause.setString( 4, getM_postitoimipaikka()); 
            lause.setInt( 5, getM_postinro()); 
            lause.setString( 6, getM_email()); 
            lause.setInt( 7, getM_puhelinnumero()); 
            lause.setInt( 8, getM_asiakas_id()); 
			// suorita sql-lause
			int asiakas_id = lause.executeUpdate();	
			if (asiakas_id == 0) {
				throw new Exception("Asiakkaan tietojen muuttaminen ei onnistu");
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


	public int poistaAsiakas (Connection connection) throws SQLException, Exception { // tietokantayhteys vÃ¤litetÃ¤Ã¤n parametrina
		
		// parsitaan DELETE
		String sql = "DELETE FROM Asiakas WHERE asiakas_id = ?"; 
		PreparedStatement lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			// laitetaan arvot DELETEn WHERE-ehtoon
                        lause.setInt(1, getM_asiakas_id());
                        //lause.setInt(2, getM_toimipiste_id());
			
			// suorita sql-lause
			int asiakas_id = lause.executeUpdate();	
			if (asiakas_id == 0) {
				throw new Exception("Asiakkaan tietojen poistaminen ei onnistu");
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
