import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Palvelu {
  
        private int m_palvelu_id;
	private int m_toimipiste_id;
	private String m_nimi;
        private String m_kuvaus;
        private double m_hinta;
        
    public Palvelu() {
        
        
    }

    public int getM_palvelu_id() {
        return m_palvelu_id;
    }

    public void setM_palvelu_id(int m_palvelu_id) {
        this.m_palvelu_id = m_palvelu_id;
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

    public double getM_hinta() {
        return m_hinta;
    }

    public void setM_hinta(double m_hinta) {
        this.m_hinta = m_hinta;
    }
    
    

        @Override
    public String toString(){
        return (m_palvelu_id + " " + m_toimipiste_id + " " + m_nimi + " "+ m_kuvaus + " " + m_hinta );  //ToString lause
    }
    
 
 public static  Palvelu haePalvelu (Connection connection, int id1, int id2) throws SQLException, Exception { // tietokantayhteys vÃ¤litetÃ¤Ã¤n parametrina
	
                
		String sql = "SELECT palvelu_id, toimipiste_id, nimi, kuvaus, hinta"
                            + " FROM palvelu WHERE palvelu_id = ? AND toimipiste_id = ?"; // ehdon arvo asetetaan jÃ¤ljempÃ¤nÃ¤
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			lause.setInt(1, id1); // asetetaan where ehtoon (?) arvo
                        lause.setInt(2, id2);        // suorita sql-lause
			tulosjoukko = lause.executeQuery();
			if (tulosjoukko == null) {
				throw new Exception("Palvelua ei loydy");
                   
			}
		} catch (SQLException se) {
            // SQL virheet
                        throw se;
                } catch (Exception e) {
            // JDBC virheet
                        throw e;
		}
		Palvelu PalveluOlio = new Palvelu ();
		
		try {
			if (tulosjoukko.next () == true){
                                PalveluOlio.setM_palvelu_id(tulosjoukko.getInt("palvelu_id"));
				PalveluOlio.setM_toimipiste_id (tulosjoukko.getInt("toimipiste_id"));
				PalveluOlio.setM_nimi (tulosjoukko.getString("nimi"));
				PalveluOlio.setM_kuvaus(tulosjoukko.getString("kuvaus"));
                                PalveluOlio.setM_hinta(tulosjoukko.getDouble("hinta"));

			}
			
		}catch (SQLException e) {
			throw e;
		}
		
		return PalveluOlio;
	}


   
 public int lisaaPalvelu(Connection connection) throws SQLException, Exception { // tietokantayhteys vÃ¤litetÃ¤Ã¤n parametrina
		
                
		String sql = "SELECT palvelu_id, toimipiste_id"
                            + " FROM palvelu WHERE palvelu_id = ? AND toimipiste_id = ?"; // ehdon arvo asetetaan jÃ¤ljempÃ¤nÃ¤
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null; 
		
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			lause.setInt(1, getM_palvelu_id()); // asetetaan where ehtoon (?) arvo, olion Mokki ja toimipiste ID
                        lause.setInt(2, getM_toimipiste_id());         // suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			if (tulosjoukko.next () == true) { // Mokki loytyi
				throw new Exception("Palvelu on jo olemassa");
			}
		} catch (SQLException se) {
            // SQL virheet
                    throw se;
                } catch (Exception e) {
            // JDBC virheet
                    throw e;
		}
		// parsitaan INSERT
		sql = "INSERT INTO palvelu "
		+ "(palvelu_id, toimipiste_id, nimi, kuvaus, hinta) "
		+ " VALUES (?, ?, ?, ?, ?)";
		// System.out.println("Lisataan " + sql);
		lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			// laitetaan arvot INSERTtiin
                        lause.setInt( 1, getM_palvelu_id());
			lause.setInt( 2, getM_toimipiste_id());
			lause.setString( 3 , getM_nimi()); 
			lause.setString( 4, getM_kuvaus());
                        lause.setDouble( 5, getM_hinta()); 
			// suorita sql-lause
			int lkm = lause.executeUpdate();	
		//	System.out.println("lkm " + lkm);
			if (lkm == 0) {
				throw new Exception("Palvelun lisaaminen ei onnistu");
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


    public int muutaPalvelu (Connection connection) throws SQLException, Exception { 
	
		String sql = "SELECT palvelu_id, toimipiste_id"
                            + " FROM palvelu WHERE palvelu_id = ? AND toimipiste_id = ?"; 
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		try 
		{
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
            lause.setInt(1, getM_palvelu_id());
			lause.setInt(2, getM_toimipiste_id()); // asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			if (tulosjoukko.next () == false) { // Suoritusta ei lÃ¶ytynyt
				throw new Exception("Palvelua ei loydy tietokannasta");
			}
		} catch (SQLException se) {
            // SQL virheet
                    throw se;
                } catch (Exception e) {
            // JDBC virheet
                    throw e;
		}
		// parsitaan Update, pÃ¤iviteÃ¤Ã¤n tiedot lukuunottamatta avainta
		sql = "UPDATE palvelu "
		+ "SET nimi = ?, kuvaus = ?, hinta = ? "
		+ " WHERE palvelu_id = ? AND toimipiste_id = ?";
		
		lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			
			// laitetaan olion attribuuttien arvot UPDATEen
			lause.setString(1, getM_nimi()); 
            lause.setString(2, getM_kuvaus());
            lause.setDouble(3, getM_hinta());
			// where-ehdon arvo
            lause.setInt(4, getM_palvelu_id());
            lause.setInt(5, getM_toimipiste_id());
			// suorita sql-lause
			int lkm = lause.executeUpdate();	
			if (lkm == 0) {
				throw new Exception("Palvelun tietojen muuttaminen ei onnistu");
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


	public int poistaPalvelu (Connection connection) throws SQLException, Exception { // tietokantayhteys vÃ¤litetÃ¤Ã¤n parametrina
		
		// parsitaan DELETE
		String sql = "DELETE FROM palvelu WHERE palvelu_id = ? AND toimipiste_id = ?";
		PreparedStatement lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			// laitetaan arvot DELETEn WHERE-ehtoon
                        lause.setInt(1, getM_palvelu_id());
                        lause.setInt(2, getM_toimipiste_id());
			
			// suorita sql-lause
			int lkm = lause.executeUpdate();	
			if (lkm == 0) {
				throw new Exception("Palvelun tietojen poistaminen ei onnistu");
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