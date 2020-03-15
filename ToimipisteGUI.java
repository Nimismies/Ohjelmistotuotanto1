
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.format.*;
import javax.swing.text.MaskFormatter;


public class ToimipisteGUI extends MainUI  {
    //
        private Toimipiste m_toimipiste = new Toimipiste (); // Toimipiste, jota tässä pääsääntöisesti käsitellään
        private Connection m_conn; // tietokantayhteys
    // käyttöliittymän otsikkokentät
        private JLabel lblToimipisteID;
        private JLabel lblToimipisteNimi;
        private JLabel lblLahiosoite;
        private JLabel lblPostitoimipaikka;
        private JLabel lblPostinro;
        private JLabel lblEmail;
		private JLabel lblPuhelin;
		private JLabel lblOtsikko;
    
    //käyttöliittymän tekstikentät
        private JTextField txtToimipisteID;
        private JTextField txtToimipisteNimi;
        private JTextField txtLahiosoite;
        private JTextField txtPostitoimipaikka;
		private JFormattedTextField txtPostinro;
        private JTextField txtEmail;
        private JTextField txtPuhelin;
    
    // käyttöliittymän painikkeet
        private JButton btnLisaa;
        private JButton btnMuuta;
        private JButton btnHae;
        private JButton btnPoista;
    
    
        public void modifyRightPanel(JPanel rightSidePanel) {
    
            lblToimipisteID = new JLabel("ToimipisteID:");
            lblToimipisteNimi = new JLabel("Toimipisteen nimi:");
            lblLahiosoite = new JLabel("Lahiosoite:");
            lblPostitoimipaikka = new JLabel("Postitoimipaikka:");
            lblPostinro = new JLabel("Postinro:");
            lblEmail = new JLabel("Email:");
			lblPuhelin = new JLabel("Puhelin:");
			lblOtsikko = new JLabel ("TOIMIPISTEIDENHALLINTA");
    
            txtToimipisteID = new JTextField (11);
            txtToimipisteNimi = new JTextField (11);
            txtLahiosoite = new JTextField (11);
            txtPostitoimipaikka = new JTextField (11);
			txtPostinro = new JFormattedTextField();
            txtEmail = new JTextField (11);
            txtPuhelin = new JTextField (11);
    
    
            btnHae = new JButton("Hae");
            btnMuuta = new JButton("Muuta");
            btnLisaa = new JButton("Lisaa");
            btnPoista = new JButton("Poista");

		Dimension minimumSize = new Dimension(100, 50);  //Provide minimum sizes for the split panes sides
		rightSidePanel.setMinimumSize(minimumSize); 
		SpringLayout layout = new SpringLayout();
		rightSidePanel.setLayout(layout);

		layout.putConstraint(SpringLayout.WEST, lblOtsikko, 350, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblOtsikko, 00, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblOtsikko);

		layout.putConstraint(SpringLayout.WEST, lblToimipisteID, 5, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblToimipisteID, 5, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblToimipisteID);	

		layout.putConstraint(SpringLayout.WEST, txtToimipisteID, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtToimipisteID, 5, SpringLayout.NORTH, rightSidePanel);	
		rightSidePanel.add(txtToimipisteID);
	
		layout.putConstraint(SpringLayout.WEST, lblToimipisteNimi, 5, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblToimipisteNimi, 35, SpringLayout.NORTH, rightSidePanel);	
		rightSidePanel.add(lblToimipisteNimi);

		layout.putConstraint(SpringLayout.WEST, txtToimipisteNimi, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtToimipisteNimi, 35, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtToimipisteNimi);

		layout.putConstraint(SpringLayout.WEST, lblLahiosoite, 5, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblLahiosoite, 65, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblLahiosoite);

		layout.putConstraint(SpringLayout.WEST, txtLahiosoite, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtLahiosoite, 65, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtLahiosoite);

		layout.putConstraint(SpringLayout.WEST, lblPostitoimipaikka, 5, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblPostitoimipaikka, 95, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblPostitoimipaikka);

		layout.putConstraint(SpringLayout.WEST, txtPostitoimipaikka, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtPostitoimipaikka, 95, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtPostitoimipaikka);

		layout.putConstraint(SpringLayout.WEST, lblPostinro, 5, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblPostinro, 125, SpringLayout.NORTH, rightSidePanel);
        rightSidePanel.add(lblPostinro);

		layout.putConstraint(SpringLayout.WEST, txtPostinro, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtPostinro, 125, SpringLayout.NORTH, rightSidePanel);
		txtPostinro.setPreferredSize( new Dimension( 100, 20 ) );
        rightSidePanel.add(txtPostinro);
        try 
        {
            MaskFormatter maskFormatter = new MaskFormatter("#####"); //tekee päivämäärien tekstikentille valmiin formaatin josta käyttäjä ei voi poiketa
            maskFormatter.install(txtPostinro); 
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }

		layout.putConstraint(SpringLayout.WEST, lblEmail, 5, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblEmail, 155, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblEmail);
		
		layout.putConstraint(SpringLayout.WEST, txtEmail, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtEmail, 155, SpringLayout.NORTH, rightSidePanel);
        rightSidePanel.add(txtEmail);
        
		layout.putConstraint(SpringLayout.WEST, lblPuhelin, 5, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblPuhelin, 185, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblPuhelin);
		
		layout.putConstraint(SpringLayout.WEST, txtPuhelin, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtPuhelin, 185, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtPuhelin);

		layout.putConstraint(SpringLayout.WEST, btnHae, 5, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnHae, 215, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnHae);
	
		layout.putConstraint(SpringLayout.WEST, btnMuuta, 105, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnMuuta, 215, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnMuuta);
	
		layout.putConstraint(SpringLayout.WEST, btnLisaa, 205, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnLisaa, 215, SpringLayout.NORTH, rightSidePanel);
        rightSidePanel.add(btnLisaa);
        	
		layout.putConstraint(SpringLayout.WEST, btnPoista, 305, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnPoista, 215, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnPoista);



		// lisataan hakupainikkeelle tapahtumakuuntelija
		btnHae.addActionListener(   // toteutetaan  kayttamalla Javan ns. niemttomia sisaluokkia
			new ActionListener () {// parametrina luotavan "rajapintaluokan ilmentymÃ¤": new ActionListener()
				public void actionPerformed(ActionEvent actEvent) {	
						hae_toimipiste();
											
				}
			}
		);
	
		// lisataan lisayspainikkeelle tapahtumakuuntelija
		btnLisaa.addActionListener(   // toteutetaan  kayttamalla Javan ns. niemttomia sisaluokkia
			new ActionListener () {// parametrina luotavan "rajapintaluokan ilmentymÃ¤": new ActionListener()
				public void actionPerformed(ActionEvent actEvent) {	
						lisaa_tiedot ();
						
				}
			}
		);
	
		// lisataan muuta-painikkeelle tapahtumakuuntelija
		btnMuuta.addActionListener(   // toteutetaan  kayttamalla Javan ns. niemttomia sisaluokkia
			new ActionListener () {// parametrina luotavan "rajapintaluokan ilmentymÃ¤": new ActionListener()
				public void actionPerformed(ActionEvent actEvent) {	
						muuta_tiedot ();
						
				}
			}
		);
		// lisataan poista-painikkeelle tapahtumakuuntelija
		btnPoista.addActionListener(   // toteutetaan  kayttamalla Javan ns. niemttomia sisaluokkia
			new ActionListener () {// parametrina luotavan "rajapintaluokan ilmentymÃ¤": new ActionListener()
				public void actionPerformed(ActionEvent actEvent) {	
						poista_tiedot ();
						
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
	

		
		public static void main(String args[]) {
			}
			/**
			 *
			 * @throws SQLException jos SQL virheita
			 * @throws Exception jos tietokantayhteys virheita
			 * Avataan tietokanta yhteys
			 */
		
	public  void yhdista() throws SQLException, Exception {
		m_conn = null;
		String url = "jdbc:mariadb://localhost:3306/vp"; // palvelin = localhost, :portti annettu asennettaessa, tietokannan nimi
		try {
			// ota yhteys kantaan, kayttaja , salasana
			m_conn=DriverManager.getConnection(url,"root","123");
		}
		catch (SQLException e) { // tietokantaan ei saada yhteytta
			m_conn = null;
			throw e;
		}
		catch (Exception e ) { // JDBC ajuria ei lÃ¶ydy
			throw e;
		}
		
	}
	
	

		public  void hae_toimipiste()  {
		// haetaan tietokannasta toimipistetta
		m_toimipiste = null;
		
		try {
			m_toimipiste = m_toimipiste.haeToimipiste (m_conn, Integer.parseInt(txtToimipisteID.getText()));
		} catch (SQLException se) {
		// SQL virheet
			JOptionPane.showMessageDialog(null, "Toimipistetta ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
		// muut virheet
			JOptionPane.showMessageDialog(null, "Toimipistetta ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		}
                
        if (Integer.parseInt(txtToimipisteID.getText()) == m_toimipiste.getM_toimipiste_id())
        {
		// muut virheet
			// naytetaan tiedot
			txtToimipisteID.setText(Integer.toString(m_toimipiste.getM_toimipiste_id()));
			txtToimipisteNimi.setText(m_toimipiste.getM_toimipisteNimi());
			txtLahiosoite.setText(m_toimipiste.getM_lahiosoite());
			txtPostitoimipaikka.setText(m_toimipiste.getM_postitoimipaikka());
            txtPostinro.setText(Integer.toString(m_toimipiste.getM_postinro()));
			txtEmail.setText(m_toimipiste.getM_email());
			txtPuhelin.setText(Integer.toString(m_toimipiste.getM_puhelin()));
		}
        else
        {
			txtToimipisteID.setText("");
			txtToimipisteNimi.setText("");
			txtLahiosoite.setText("");
			txtPostitoimipaikka.setText("");
            txtPostinro.setText("");
			txtEmail.setText("");
			txtPuhelin.setText("");

			JOptionPane.showMessageDialog(null, "Toimipistetta ei loydy.", "Virhe", JOptionPane.ERROR_MESSAGE);
		}

        }
	

		/**
		 * lisataaan syotetyt tiedot tietokantaan
		 */
	
		public  void lisaa_tiedot() {
			// lisataan tietokantaan toimipiste
			//System.out.println("Lisataan...");
			boolean toimipiste_lisatty = true;
			m_toimipiste = null;
			try {
				m_toimipiste = m_toimipiste.haeToimipiste (m_conn, Integer.parseInt(txtToimipisteID.getText()));
			} catch (SQLException se) {
			// SQL virheet
			toimipiste_lisatty = false;
				JOptionPane.showMessageDialog(null, "Tietokantavirhe.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
			// muut virheet
			toimipiste_lisatty = false;
				JOptionPane.showMessageDialog(null, "Tietokantavirhe.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
			}
					  if (Integer.parseInt(txtToimipisteID.getText()) == m_toimipiste.getM_toimipiste_id())
					{
			// toimipiste jo olemassa, naytetaan tiedot
			toimipiste_lisatty = false;
			txtToimipisteID.setText(Integer.toString(m_toimipiste.getM_toimipiste_id()));
			txtToimipisteNimi.setText(m_toimipiste.getM_toimipisteNimi());
			txtLahiosoite.setText(m_toimipiste.getM_lahiosoite());
			txtPostitoimipaikka.setText(m_toimipiste.getM_postitoimipaikka());
            txtPostinro.setText(Integer.toString(m_toimipiste.getM_postinro()));
			txtEmail.setText(m_toimipiste.getM_email());
			txtPuhelin.setText(Integer.toString(m_toimipiste.getM_puhelin()));

			JOptionPane.showMessageDialog(null, "Toimipiste on jo olemassa.", "Virhe", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				// asetetaan tiedot oliolle
				m_toimipiste.setM_toimipiste_id(Integer.parseInt(txtToimipisteID.getText()));
				m_toimipiste.setM_toimipisteNimi(txtToimipisteNimi.getText());
				m_toimipiste.setM_lahiosoite(txtLahiosoite.getText());
				m_toimipiste.setM_postitoimipaikka(txtPostitoimipaikka.getText());
				m_toimipiste.setM_postinro(Integer.parseInt(txtPostinro.getText()));
				m_toimipiste.setM_email(txtEmail.getText());
				m_toimipiste.setM_puhelin(Integer.parseInt(txtPuhelin.getText()));
	
				try {
					// yritetaan kirjoittaa kantaan
					m_toimipiste.lisaaToimipiste (m_conn);
				} catch (SQLException se) {
				// SQL virheet
				toimipiste_lisatty = false;
					JOptionPane.showMessageDialog(null, "Toimipisteen lisaaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
				//	 se.printStackTrace();
				} catch (Exception e) {
				// muut virheet
				toimipiste_lisatty = false;
					JOptionPane.showMessageDialog(null, "Toimipisteen lisaaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);
				//	 e.printStackTrace();
				}finally {
					if (toimipiste_lisatty == true)
						JOptionPane.showMessageDialog(null, "Toimipisteen tiedot lisatty tietokantaan.");
				}
			
			}
			
		}
	
	
	
		/**
		 * Muutetaan naytolla olevia tietoja ja viedaan ne tietokantaan
		 */
	
		public  void muuta_tiedot() {
			//System.out.println("Muutetaan...");
				boolean toimipiste_muutettu = true;
			// asetetaan tiedot oliolle
			m_toimipiste.setM_toimipiste_id(Integer.parseInt(txtToimipisteID.getText()));
			m_toimipiste.setM_toimipisteNimi(txtToimipisteNimi.getText());
			m_toimipiste.setM_lahiosoite(txtLahiosoite.getText());
			m_toimipiste.setM_postitoimipaikka(txtPostitoimipaikka.getText());
			m_toimipiste.setM_postinro(Integer.parseInt(txtPostinro.getText()));
			m_toimipiste.setM_email(txtEmail.getText());
			m_toimipiste.setM_puhelin(Integer.parseInt(txtPuhelin.getText()));
				try {
					// yritetaan muuttaa (UPDATE) tiedot kantaan
					m_toimipiste.muutaToimipiste (m_conn);
				} catch (SQLException se) {
				// SQL virheet
					toimipiste_muutettu = false;
					JOptionPane.showMessageDialog(null, "Toimipisteen tietojen muuttaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
					 //se.printStackTrace();
				} catch (Exception e) {
				// muut virheet
				toimipiste_muutettu = false;
					JOptionPane.showMessageDialog(null, "Toimipisteen tietojen muuttaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);
					// e.printStackTrace();
				} finally {
					if (toimipiste_muutettu == true)
						JOptionPane.showMessageDialog(null, "Toimipisteen tiedot muutettu.");
				}
			
		}
	
		/**
		 * Poistetaan tiedot tietokannasta 
		 */
		public  void poista_tiedot() {
			// haetaan tietokannasta toimipistetta
			m_toimipiste = null;
			boolean toimipiste_poistettu = false;
			
			try {
				m_toimipiste = m_toimipiste.haeToimipiste (m_conn, Integer.parseInt(txtToimipisteID.getText()));
			} catch (SQLException se) {
			// SQL virheet
				JOptionPane.showMessageDialog(null, "Toimipistetta ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
			// muut virheet
				JOptionPane.showMessageDialog(null, "Toimipistetta ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
			}
			   if (m_toimipiste.getM_toimipiste_id()== 0) {
			// muut virheet
			txtToimipisteID.setText("");
			txtToimipisteNimi.setText("");
			txtLahiosoite.setText("");
			txtPostitoimipaikka.setText("");
			txtPostinro.setText("");
			txtEmail.setText("");
			txtPuhelin.setText("");
				
			JOptionPane.showMessageDialog(null, "Toimipistetta ei loydy.", "Virhe", JOptionPane.ERROR_MESSAGE);
				return; // poistutaan
			}
			else
			{
				// naytetaan poistettavan suorituksen tiedot
				txtToimipisteID.setText(Integer.toString(m_toimipiste.getM_toimipiste_id()));
				txtToimipisteNimi.setText(m_toimipiste.getM_toimipisteNimi());
				txtLahiosoite.setText(m_toimipiste.getM_lahiosoite());
				txtPostitoimipaikka.setText(m_toimipiste.getM_postitoimipaikka());
				txtPostinro.setText(Integer.toString(m_toimipiste.getM_postinro()));
				txtEmail.setText(m_toimipiste.getM_email());
				txtPuhelin.setText(Integer.toString(m_toimipiste.getM_puhelin()));
			}
			try {
				if (JOptionPane.showConfirmDialog(null, "Haluatko todella poistaa toimipisteen?")==0) {// vahvistus ikkunassa
					m_toimipiste.poistaToimipiste (m_conn);
					toimipiste_poistettu = true;
				}
				} catch (SQLException se) {
				// SQL virheet
					JOptionPane.showMessageDialog(null, "Toimipisteen tietojen poistaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
					// se.printStackTrace();
				} catch (Exception e) {
				// muut virheet
					JOptionPane.showMessageDialog(null, "Toimipisteen tietojen poistaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);
					// e.printStackTrace();
				} finally {
					if (toimipiste_poistettu == true) { // ainoastaan, jos vahvistettiin ja poisto onnistui
						txtToimipisteID.setText("");
						txtToimipisteNimi.setText("");
						txtLahiosoite.setText("");
						txtPostitoimipaikka.setText("");
						txtPostinro.setText("");
						txtEmail.setText("");
						txtPuhelin.setText("");
							
						JOptionPane.showMessageDialog(null, "Toimipisteen tiedot poistettu tietokannasta.");
						m_toimipiste = null;
					}         
		
				}
			

			}
	
}
    

        



    