

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * kurssi.java
 * @author ville
 * 
 */
public class Mokki {
  
        private int m_mokki_id;
	private int m_toimipiste_id;
	private String m_nimi;
        private String m_kuvaus;
        private String m_varustelutaso;
        private double m_hinta;
        
    /**
     *
     */
    public Mokki() {
        
        
    }

    public int getM_mokki_id() {
        return m_mokki_id;
    }

    public void setM_mokki_id(int m_mokki_id) {
        this.m_mokki_id = m_mokki_id;
    }

    public int getM_toimipiste_id() {
        return m_toimipiste_id;
    }

    public void setM_toimipiste_id(int m_toimipiste_id) {
        this.m_toimipiste_id = m_toimipiste_id;
    }

    public String getM_nimi() {
        return m_nimi;
    }

    public void setM_nimi(String m_nimi) {
        this.m_nimi = m_nimi;
    }

    public String getM_kuvaus() {
        return m_kuvaus;
    }

    public void setM_kuvaus(String m_kuvaus) {
        this.m_kuvaus = m_kuvaus;
    }

    public String getM_varustelutaso() {
        return m_varustelutaso;
    }

    public void setM_varustelutaso(String m_varustelutaso) {
        this.m_varustelutaso = m_varustelutaso;
    }

    public double getM_hinta() {
        return m_hinta;
    }

    public void setM_hinta(double m_hinta) {
        this.m_hinta = m_hinta;
    }
    
    

        @Override
    public String toString(){
        return (m_mokki_id + " " + m_toimipiste_id + " " + m_nimi + " "+ m_kuvaus + " " + m_varustelutaso + " " + m_hinta );  //ToString lause
    }
    
    /**
     * 
     * @param connection tietokantayhteys
     * @param id kurssi_id
     * @return
         * @throws SQLException jos SQL virheitÃƒÂ¤
         * @throws Exception jos tietokantayhteys virheitÃƒÂ¤
         * 
     */
 public static  Mokki haeMokki (Connection connection, int id1, int id2) throws SQLException, Exception { // tietokantayhteys vÃ¤litetÃ¤Ã¤n parametrina
		// haetaan tietokannasta Mokkia, jonka mokki_id = id ja toimipiste_id = id2 
                
		String sql = "SELECT mokki_id, toimipiste_id, nimi, kuvaus, varustelutaso, hinta"
                            + " FROM mokki WHERE mokki_id = ? AND toimipiste_id = ?"; // ehdon arvo asetetaan jÃ¤ljempÃ¤nÃ¤
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			lause.setInt(1, id1); // asetetaan where ehtoon (?) arvo
                        lause.setInt(2, id2);        // suorita sql-lause
			tulosjoukko = lause.executeQuery();
			if (tulosjoukko == null) {
				throw new Exception("mokkia ei loydy");
                   
			}
		} catch (SQLException se) {
            // SQL virheet
                        throw se;
                } catch (Exception e) {
            // JDBC virheet
                        throw e;
		}
		// kÃ¤sitellÃ¤Ã¤n resultset - laitetaan tiedot Opintosuoritusoliolle
		Mokki MokkiOlio = new Mokki ();
		
		try {
			if (tulosjoukko.next () == true){
				//opiskelijaid, kurssiid, arvosana ja suorituspvm
                MokkiOlio.setM_mokki_id(tulosjoukko.getInt("mokki_id"));
				MokkiOlio.setM_toimipiste_id (tulosjoukko.getInt("toimipiste_id"));
				MokkiOlio.setM_nimi (tulosjoukko.getString("nimi"));
				MokkiOlio.setM_kuvaus(tulosjoukko.getString("kuvaus"));
                MokkiOlio.setM_varustelutaso(tulosjoukko.getString("varustelutaso"));
                MokkiOlio.setM_hinta(tulosjoukko.getDouble("hinta"));

			}
			
		}catch (SQLException e) {
			throw e;
		}
		// palautetaan Opintosuoritusolio
		
		return MokkiOlio;
	}


    /**
     *
     * @param connection tietokantayhteys
     * @return
     * @throws SQLException jos SQL virheitÃƒÂ¤
     * @throws Exception jos tietokantayhteys virheitÃƒÂ¤
     * LisÃ¤tÃ¤Ã¤n mokin tiedot tietokantaan mokki ID.n ja toimipiste ID.n perusteella
     */

    
 public int lisaaMokki(Connection connection) throws SQLException, Exception { // tietokantayhteys vÃ¤litetÃ¤Ã¤n parametrina
			// haetaan tietokannasta Mokkia, jonka mokki_id = id ja toimipiste_id = id2 
                
		String sql = "SELECT mokki_id, toimipiste_id"
                            + " FROM mokki WHERE mokki_id = ? AND toimipiste_id = ?"; // ehdon arvo asetetaan jÃ¤ljempÃ¤nÃ¤
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null; 
		
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			lause.setInt(1, getM_mokki_id()); // asetetaan where ehtoon (?) arvo, olion Mokki ja toimipiste ID
                        lause.setInt(2, getM_toimipiste_id());         // suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			if (tulosjoukko.next () == true) { // Mokki loytyi
				throw new Exception("Mokki on jo olemassa");
			}
		} catch (SQLException se) {
            // SQL virheet
                    throw se;
                } catch (Exception e) {
            // JDBC virheet
                    throw e;
		}
		// parsitaan INSERT
		sql = "INSERT INTO mokki "
		+ "(mokki_id, toimipiste_id, nimi, kuvaus, varustelutaso, hinta) "
		+ " VALUES (?, ?, ?, ?, ?, ?)";
		// System.out.println("Lisataan " + sql);
		lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			// laitetaan arvot INSERTtiin
                        lause.setInt( 1, getM_mokki_id());
			lause.setInt( 2, getM_toimipiste_id());
			lause.setString( 3 , getM_nimi()); 
			lause.setString( 4, getM_kuvaus());
                        lause.setString( 5, getM_varustelutaso()); 
                        lause.setDouble( 6, getM_hinta()); 
			// suorita sql-lause
			int lkm = lause.executeUpdate();	
		//	System.out.println("lkm " + lkm);
			if (lkm == 0) {
				throw new Exception("Mokin lisaaminen ei onnistu");
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

    /**
     *
     * @param connection tietokantayhteys
     * @return
     * @throws SQLException jos SQL virheitÃƒÂ¤
     * @throws Exception jos tietokantayhteys virheitÃƒÂ¤
     * Muutetaan suorituksia Mokki ID.n ja Toimipiste ID.n perusteella
     */

    public int muutaMokki (Connection connection) throws SQLException, Exception { // tietokantayhteys vÃ¤litetÃ¤Ã¤n parametrina
			// haetaan tietokannasta Mokkia, jonka mokki_id = id ja toimipiste_id = id2 
                
		String sql = "SELECT mokki_id, toimipiste_id"
                            + " FROM mokki WHERE mokki_id = ? AND toimipiste_id = ?"; // ehdon arvo asetetaan jÃ¤ljempÃ¤nÃ¤
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
                        lause.setInt(1, getM_mokki_id());
			lause.setInt(2, getM_toimipiste_id()); // asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			if (tulosjoukko.next () == false) { // Suoritusta ei lÃ¶ytynyt
				throw new Exception("Mokkia ei loydy tietokannasta");
			}
		} catch (SQLException se) {
            // SQL virheet
                    throw se;
                } catch (Exception e) {
            // JDBC virheet
                    throw e;
		}
		// parsitaan Update, pÃ¤iviteÃ¤Ã¤n tiedot lukuunottamatta avainta
		sql = "UPDATE mokki "
		+ "SET nimi = ?, kuvaus = ?, hinta = ?, varustelutaso = ? "
		+ " WHERE mokki_id = ? AND toimipiste_id = ?";
		
		lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			
			// laitetaan olion attribuuttien arvot UPDATEen
			lause.setString(1, getM_nimi()); 
			lause.setString(2, getM_varustelutaso()); 
                        lause.setDouble(3, getM_hinta());
                        lause.setString(4, getM_kuvaus());
			// where-ehdon arvo
                        lause.setInt(5, getM_mokki_id());
                        lause.setInt(6, getM_toimipiste_id());
			// suorita sql-lause
			int lkm = lause.executeUpdate();	
			if (lkm == 0) {
				throw new Exception("Mokin tietojen muuttaminen ei onnistu");
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

    /**
     *
     * @param connection tietokantayhteys
     * @return
     * @throws SQLException jos SQL virheitÃƒÂ¤
     * @throws Exception jos tietokantayhteys virheitÃƒÂ¤
     * Poistetaan suorituksia Mokki ID.n ja toimipiste ID.n perusteella
     */

	public int poistaMokki (Connection connection) throws SQLException, Exception { // tietokantayhteys vÃ¤litetÃ¤Ã¤n parametrina
		
		// parsitaan DELETE
		String sql = "DELETE FROM mokki WHERE mokki_id = ? AND toimipiste_id = ?";
		PreparedStatement lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			// laitetaan arvot DELETEn WHERE-ehtoon
                        lause.setInt(1, getM_mokki_id());
                        lause.setInt(2, getM_toimipiste_id());
			
			// suorita sql-lause
			int lkm = lause.executeUpdate();	
			if (lkm == 0) {
				throw new Exception("Mokin tietojen poistaminen ei onnistu");
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
