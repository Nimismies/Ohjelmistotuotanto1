import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * OpintosuoritusGUI.java
 * Mokki.javan kÃ¤yttÃ¶liittymÃ¤
 * @author ville
 */

public class MokkiGUI extends MainUI  {
//
    private Mokki m_mokki = new Mokki ();
    private Connection m_conn; // tietokantayhteys
// kÃ¤yttÃ¶liittymÃ¤n otsikkokentÃ¤t
    private JLabel lblMokkiID;
    private JLabel lblToimipisteID;
    private JLabel lblNimi;
    private JLabel lblKuvaus;
    private JLabel lblVarustelutaso;
	private JLabel lblHinta;
	private JLabel lblOtsikko;
//kÃ¤yttÃ¶liittymÃ¤n tekstikentÃ¤t
    private JTextField txtMokkiID;
    private JTextField txtToimipisteID;
    private JTextField txtNimi;
    private JTextField txtKuvaus;
    private JTextField txtVarustelutaso;
    private JTextField txtHinta;


    
// kÃ¤yttÃ¶liittymÃ¤n painikkeet	
    private JButton btnLisaa;
    private JButton btnMuuta;
    private JButton btnHae;
    private JButton btnPoista;
    private JButton btnPaluu;

    /**
     * Opintosuoritus construktori
     */
	public void modifyRightPanel(JPanel rightSidePanel)
	{


		lblMokkiID = new JLabel("Mokintunnus:");
        lblToimipisteID = new JLabel("Toimipisteentunnus:");
		lblNimi = new JLabel("Nimi:");
        lblKuvaus = new JLabel("Kuvaus:");
		lblVarustelutaso = new JLabel ("Varustelutaso:");
		lblHinta = new JLabel("Hinta:");
		lblOtsikko = new JLabel ("MOKKIENHALLINTA");
        txtMokkiID = new JTextField (25);
        txtToimipisteID = new JTextField (25);
        txtNimi = new JTextField (25);
        txtKuvaus = new JTextField (25);
        txtVarustelutaso = new JTextField (25);
        txtHinta = new JTextField (25);
        
        btnHae = new JButton("Hae");
		btnMuuta = new JButton("Muuta");
		btnLisaa = new JButton("Lisaa");
		btnPoista = new JButton("Poista");
		btnPaluu = new JButton("Lopeta");



		//
		Dimension minimumSize = new Dimension(100, 50);  //Provide minimum sizes for the split panes sides
		rightSidePanel.setMinimumSize(minimumSize); 
		SpringLayout layout = new SpringLayout();
		rightSidePanel.setLayout(layout);

		layout.putConstraint(SpringLayout.WEST, lblOtsikko, 01, SpringLayout.WEST, rightSidePanel);
layout.putConstraint(SpringLayout.NORTH, lblOtsikko, 01, SpringLayout.NORTH, rightSidePanel);
rightSidePanel.add(lblOtsikko);

		layout.putConstraint(SpringLayout.WEST, lblMokkiID, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblMokkiID, 20, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblMokkiID);	

		layout.putConstraint(SpringLayout.WEST, txtMokkiID, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtMokkiID, 20, SpringLayout.NORTH, rightSidePanel);	
		rightSidePanel.add(txtMokkiID);
	
		layout.putConstraint(SpringLayout.WEST, lblToimipisteID, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblToimipisteID, 55, SpringLayout.NORTH, rightSidePanel);	
		rightSidePanel.add(lblToimipisteID);

		layout.putConstraint(SpringLayout.WEST, txtToimipisteID, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtToimipisteID, 55, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtToimipisteID);

		layout.putConstraint(SpringLayout.WEST, lblNimi, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblNimi, 85, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblNimi);

		layout.putConstraint(SpringLayout.WEST, txtNimi, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtNimi, 85, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtNimi);

		layout.putConstraint(SpringLayout.WEST, lblKuvaus, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblKuvaus, 115, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblKuvaus);

		layout.putConstraint(SpringLayout.WEST, txtKuvaus, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtKuvaus, 115, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtKuvaus);

		layout.putConstraint(SpringLayout.WEST, lblVarustelutaso, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblVarustelutaso, 145, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblVarustelutaso);

		layout.putConstraint(SpringLayout.WEST, txtVarustelutaso, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtVarustelutaso, 145, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtVarustelutaso);

		layout.putConstraint(SpringLayout.WEST, lblHinta, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblHinta, 175, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblHinta);
		
		layout.putConstraint(SpringLayout.WEST, txtHinta, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtHinta, 175, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtHinta);

		layout.putConstraint(SpringLayout.WEST, btnHae, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnHae, 205, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnHae);

		layout.putConstraint(SpringLayout.WEST, btnMuuta, 90, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnMuuta, 205, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnMuuta);

		layout.putConstraint(SpringLayout.WEST, btnLisaa, 170, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnLisaa, 205, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnLisaa);

		layout.putConstraint(SpringLayout.WEST, btnPoista, 245, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnPoista, 205, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnPoista);

		layout.putConstraint(SpringLayout.WEST, btnPaluu, 325, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnPaluu, 205, SpringLayout.NORTH, rightSidePanel);	
		rightSidePanel.add(btnPaluu);
		
		
		// lisÃ¤tÃ¤Ã¤n hakupainikkeelle tapahtumakuuntelija
		btnHae.addActionListener(   // toteutetaan  kÃ¤yttÃ¤mÃ¤llÃ¤ Javan ns. nimettÃ¶miÃ¤ sisÃ¤luokkia
			new ActionListener () {// parametrina luotavan "rajapintaluokan ilmentymÃ¤": new ActionListener()
				public void actionPerformed(ActionEvent actEvent) {	
                                            hae_mokki();
                                    	
				}
			}
		);

		// lisÃ¤tÃ¤Ã¤n lisayspainikkeelle tapahtumakuuntelija
		btnLisaa.addActionListener(   // toteutetaan  kÃ¤yttÃ¤mÃ¤llÃ¤ Javan ns. nimettÃ¶miÃ¤ sisÃ¤luokkia
			new ActionListener () {// parametrina luotavan "rajapintaluokan ilmentymÃ¤": new ActionListener()
				public void actionPerformed(ActionEvent actEvent) {	
						lisaa_tiedot ();
					
				}
			}
		);

		// lisÃ¤tÃ¤Ã¤n muuta-painikkeelle tapahtumakuuntelija
		btnMuuta.addActionListener(   // toteutetaan  kÃ¤yttÃ¤mÃ¤llÃ¤ Javan ns. nimettÃ¶miÃ¤ sisÃ¤luokkia
			new ActionListener () {// parametrina luotavan "rajapintaluokan ilmentymÃ¤": new ActionListener()
				public void actionPerformed(ActionEvent actEvent) {	
						muuta_tiedot ();
					
				}
			}
		);
		// lisÃ¤tÃ¤Ã¤n muuta-painikkeelle tapahtumakuuntelija
		btnPoista.addActionListener(   // toteutetaan  kÃ¤yttÃ¤mÃ¤llÃ¤ Javan ns. nimettÃ¶miÃ¤ sisÃ¤luokkia
			new ActionListener () {// parametrina luotavan "rajapintaluokan ilmentymÃ¤": new ActionListener()
				public void actionPerformed(ActionEvent actEvent) {	
						poista_tiedot ();
					
				}
			}
		);

		// lisÃ¤tÃ¤Ã¤n lopetuspainikkeelle tapahtumakuuntelija
		btnPaluu.addActionListener(   
			new ActionListener () {
				public void actionPerformed(ActionEvent actEvent) {
					try {
						sulje_kanta ();
					} catch (SQLException se) {
					// SQL virheet
						JOptionPane.showMessageDialog(null, "Tapahtui tietokantavirhe tietokantaa suljettaessa.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
					} catch (Exception e) {
					// muut virheet
						JOptionPane.showMessageDialog(null, "Tapahtui virhe tietokantaa suljettaessa.", "Virhe", JOptionPane.ERROR_MESSAGE);
					} finally {
						System.exit(0);
					}
						
        		}
      		}
		);


				try {
			yhdista ();
		 } catch (SQLException se) {
            // SQL virheet
			JOptionPane.showMessageDialog(null, "Tapahtui virhe tietokantaa avattaessa.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // JDBC virheet
            JOptionPane.showMessageDialog(null, "Tapahtui JDBCvirhe tietokantaa avattaessa.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		}
	}
	
    /**
     *
     * @param args
     */
    public static void main(String args[]) {
    //MokkiGUI frmMokki = new modi(rightSidePanel);  // luodaan lomakeluokan olio
	  
	
	}


    /**
     *
     * @throws SQLException jos SQL virheitÃƒÂ¤
     * @throws Exception jos tietokantayhteys virheitÃƒÂ¤
     * Avataan tietokanta yhteys
     */

	public  void yhdista() throws SQLException, Exception {
		m_conn = null;
		String url = "jdbc:mariadb://localhost:3306/vp"; // palvelin = localhost, :portti annettu asennettaessa, tietokannan nimi
		try {
			// ota yhteys kantaan, kayttaja , salasana
			m_conn=DriverManager.getConnection(url,"root","123");
		}
		catch (SQLException e) { // tietokantaan ei saada yhteyttÃ¤
			m_conn = null;
			throw e;
		}
		catch (Exception e ) { // JDBC ajuria ei lÃ¶ydy
			throw e;
		}
		
	}

    /**
     *
     * @throws SQLException jos SQL virheitÃƒÂ¤
     * @throws Exception jos tietokantayhteys virheitÃƒÂ¤
     * Suljetaan tietokantayhteys
     */

	public  void sulje_kanta() throws SQLException, Exception {
		// suljetaan		
		try {
			// sulje yhteys kantaan
			m_conn.close ();
		}
		catch (SQLException e) { // tietokantavirhe
			throw e;
		}
		catch (Exception e ) { // muu virhe tapahtui
			throw e;
		}
		
	}
	/*
	Haetaan tietokannasta suorituksen tiedot nÃ¤ytÃ¶llÃ¤ olevan opiskelijaid:n ja kurssiid:n perusteella ja nÃ¤ytetÃ¤Ã¤n tiedot lomakkeella
	*/

    /**
     * Haetaan opintosuoritusta mokki ID.n ja toimipiste ID.n perusteella
     */

	public  void hae_mokki()  {
		// haetaan tietokannasta suoritusta, jonka opiskelija_id = txtOpiskelijaID ja kurssiID = txtkurssiID
		m_mokki = null;
		
		try 
		{
			m_mokki = m_mokki.haeMokki (m_conn, Integer.parseInt(txtMokkiID.getText()), Integer.parseInt(txtToimipisteID.getText()));
		} 
		catch (SQLException se) 
		{
		// SQL virheet
			JOptionPane.showMessageDialog(null, "Mokkia ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		} 
		catch (Exception e) 
		{
		// muut virheet
			JOptionPane.showMessageDialog(null, "Mokkia ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		}
                
        if (Integer.parseInt(txtMokkiID.getText()) == m_mokki.getM_mokki_id() && Integer.parseInt(txtToimipisteID.getText()) == m_mokki.getM_toimipiste_id())
        {
			txtMokkiID.setText(Integer.toString(m_mokki.getM_mokki_id()));
			txtToimipisteID.setText(Integer.toString(m_mokki.getM_toimipiste_id()));
			txtNimi.setText(m_mokki.getM_nimi());
			txtKuvaus.setText(m_mokki.getM_kuvaus());
            txtVarustelutaso.setText(m_mokki.getM_varustelutaso());
            txtHinta.setText(Double.toString(m_mokki.getM_hinta()));
		}
        else
        {
            txtMokkiID.setText("");
			txtToimipisteID.setText("");
			txtNimi.setText("");
			txtKuvaus.setText("");
            txtVarustelutaso.setText("");
            txtHinta.setText("");

			JOptionPane.showMessageDialog(null, "Mokkia ei loydy.", "Virhe", JOptionPane.ERROR_MESSAGE);
		}

        }
	



    /**
     * LisÃ¤tÃ¤Ã¤n tiedot syÃ¶tetyt tiedot tietokantaan
     */

	public  void lisaa_tiedot() {
		// lisÃ¤tÃ¤Ã¤n tietokantaan suoritus
		//System.out.println("Lisataan...");
		boolean mokki_lisatty = true;
		m_mokki = null;
		try {
				m_mokki = m_mokki.haeMokki (m_conn, Integer.parseInt(txtMokkiID.getText()), Integer.parseInt(txtToimipisteID.getText()));
		} catch (SQLException se) {
		// SQL virheet
			mokki_lisatty = false;
			JOptionPane.showMessageDialog(null, "Tietokantavirhe.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
		// muut virheet
			mokki_lisatty = false;
			JOptionPane.showMessageDialog(null, "Tietokantavirhe.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		}
                  if (Integer.parseInt(txtMokkiID.getText()) == m_mokki.getM_mokki_id() && Integer.parseInt(txtToimipisteID.getText()) == m_mokki.getM_toimipiste_id())
                {
		// suoritus jo olemassa, nÃ¤ytetÃ¤Ã¤n tiedot
			mokki_lisatty = false;
			txtMokkiID.setText(Integer.toString(m_mokki.getM_mokki_id()));
			txtToimipisteID.setText(Integer.toString(m_mokki.getM_toimipiste_id()));
			txtNimi.setText(m_mokki.getM_nimi());
			txtKuvaus.setText(m_mokki.getM_kuvaus());
                        txtVarustelutaso.setText(m_mokki.getM_varustelutaso());
                        txtHinta.setText(Double.toString(m_mokki.getM_hinta()));
			JOptionPane.showMessageDialog(null, "Mokki on jo olemassa.", "Virhe", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			// asetetaan tiedot oliolle
			m_mokki.setM_mokki_id(Integer.parseInt(txtMokkiID.getText()));
			m_mokki.setM_toimipiste_id(Integer.parseInt(txtToimipisteID.getText()));
			m_mokki.setM_nimi(txtNimi.getText());
			m_mokki.setM_kuvaus(txtKuvaus.getText());
                        m_mokki.setM_varustelutaso(txtVarustelutaso.getText());
                        m_mokki.setM_hinta(Double.parseDouble(txtHinta.getText()));

			try {
				// yritetÃ¤Ã¤n kirjoittaa kantaan
				m_mokki.lisaaMokki (m_conn);
			} catch (SQLException se) {
			// SQL virheet
				mokki_lisatty = false;
				JOptionPane.showMessageDialog(null, "Mokin lisaaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
			//	 se.printStackTrace();
			} catch (Exception e) {
			// muut virheet
				mokki_lisatty = false;
				JOptionPane.showMessageDialog(null, "Mokin lisaaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);
			//	 e.printStackTrace();
			}finally {
				if (mokki_lisatty == true)
					JOptionPane.showMessageDialog(null, "Mokin tiedot lisatty tietokantaan.");
			}
		
		}
		
	}



    /**
     * Muutetaan nÃ¤ytÃ¶llÃ¤ olevia tietoja ja viedÃ¤Ã¤n ne tietokantaan
     */

	public  void muuta_tiedot() {
		//System.out.println("Muutetaan...");
			boolean mokki_muutettu = true;
		// asetetaan tiedot oliolle
			m_mokki.setM_mokki_id(Integer.parseInt(txtMokkiID.getText()));
			m_mokki.setM_toimipiste_id(Integer.parseInt(txtToimipisteID.getText()));
			m_mokki.setM_nimi(txtNimi.getText());
			m_mokki.setM_kuvaus(txtKuvaus.getText());
                        m_mokki.setM_varustelutaso(txtVarustelutaso.getText());
                        m_mokki.setM_hinta(Double.parseDouble(txtHinta.getText()));
			
			try {
				// yritetÃ¤Ã¤n muuttaa (UPDATE) tiedot kantaan
				m_mokki.muutaMokki (m_conn);
			} catch (SQLException se) {
			// SQL virheet
				mokki_muutettu = false;
				JOptionPane.showMessageDialog(null, "Mokin tietojen muuttaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
				 //se.printStackTrace();
			} catch (Exception e) {
			// muut virheet
				mokki_muutettu = false;
				JOptionPane.showMessageDialog(null, "Mokin tietojen muuttaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);
				// e.printStackTrace();
			} finally {
				if (mokki_muutettu == true)
					JOptionPane.showMessageDialog(null, "Mokin tiedot muutettu.");
			}
		
	}

    /**
     * Poistetaan tiedot tietokannasta 
     */
    public  void poista_tiedot() {
		// haetaan tietokannasta mokkia, jonka mokki_id = txtMokkiID ja mokki_id = txtMokkiID 
		m_mokki = null;
		boolean mokki_poistettu = false;
		
		try {
			m_mokki = m_mokki.haeMokki (m_conn, Integer.parseInt(txtMokkiID.getText()), Integer.parseInt(txtToimipisteID.getText()));
		} catch (SQLException se) {
		// SQL virheet
			JOptionPane.showMessageDialog(null, "Mokkia ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
		// muut virheet
			JOptionPane.showMessageDialog(null, "Mokkia ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		}
	       if (m_mokki.getM_mokki_id()== 0) {
		// muut virheet
                        txtMokkiID.setText("");
			txtToimipisteID.setText("");
			txtNimi.setText("");
			txtKuvaus.setText("");
                        txtVarustelutaso.setText("");
                        txtHinta.setText("");
			JOptionPane.showMessageDialog(null, "Mokkia ei loydy.", "Virhe", JOptionPane.ERROR_MESSAGE);
			return; // poistutaan
		}
		else
		{
			// naytetaan poistettavan suorituksen tiedot
			txtMokkiID.setText(Integer.toString(m_mokki.getM_mokki_id()));
			txtToimipisteID.setText(Integer.toString(m_mokki.getM_toimipiste_id()));
			txtNimi.setText(m_mokki.getM_nimi());
			txtKuvaus.setText(m_mokki.getM_kuvaus());
                        txtVarustelutaso.setText(m_mokki.getM_varustelutaso());
                        txtHinta.setText(Double.toString(m_mokki.getM_hinta()));
		}
		try {
			if (JOptionPane.showConfirmDialog(null, "Haluatko todella poistaa Mokin?")==0) {// vahvistus ikkunassa
				m_mokki.poistaMokki (m_conn);
				mokki_poistettu = true;
			}
			} catch (SQLException se) {
			// SQL virheet
				JOptionPane.showMessageDialog(null, "Mokin tietojen poistaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
				// se.printStackTrace();
			} catch (Exception e) {
			// muut virheet
				JOptionPane.showMessageDialog(null, "Mokin tietojen poistaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);
				// e.printStackTrace();
			} finally {
				if (mokki_poistettu == true) { // ainoastaan, jos vahvistettiin ja poisto onnistui
                                    txtMokkiID.setText("");
                                    txtToimipisteID.setText("");
                                    txtNimi.setText("");
                                    txtKuvaus.setText("");
                                    txtVarustelutaso.setText("");
                                    txtHinta.setText("");
                                            JOptionPane.showMessageDialog(null, "Opintosuorituksen tiedot poistettu tietokannasta.");
                                            m_mokki = null;
				}         
	}
        }
}