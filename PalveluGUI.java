
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class PalveluGUI extends MainUI  {
//
    private Palvelu m_palvelu = new Palvelu ();
    private Connection m_conn; // tietokantayhteys
// kÃ¤yttÃ¶liittymÃ¤n otsikkokentÃ¤t
    private JLabel lblPalveluID;
    private JLabel lblToimipisteID;
    private JLabel lblNimi;
    private JLabel lblKuvaus;
	private JLabel lblHinta;
	private JLabel lblOtsikko;

//kÃ¤yttÃ¶liittymÃ¤n tekstikentÃ¤t
    private JTextField txtPalveluID;
    private JTextField txtToimipisteID;
    private JTextField txtNimi;
    private JTextField txtKuvaus;
    private JTextField txtHinta;


    
	
    private JButton btnLisaa;
    private JButton btnMuuta;
    private JButton btnHae;
    private JButton btnPoista;
    private JButton btnPaluu;


	public void modifyRightPanel(JPanel rightSidePanel)
	{
		lblPalveluID = new JLabel("Palvelun tunnus:");
        lblToimipisteID = new JLabel("Toimipisteen tunnus:");
		lblNimi = new JLabel("Nimi:");
        lblKuvaus = new JLabel("Kuvaus:");
		lblHinta = new JLabel("Hinta:");
		lblOtsikko = new JLabel ("PALVELUIDENHALLINTA");
        txtPalveluID = new JTextField (5);
        txtToimipisteID = new JTextField (5);
        txtNimi = new JTextField (25);
        txtKuvaus = new JTextField (25);
        txtHinta = new JTextField (5);
        
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

		layout.putConstraint(SpringLayout.WEST, lblPalveluID, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblPalveluID, 20, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblPalveluID);

		layout.putConstraint(SpringLayout.WEST, txtPalveluID, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtPalveluID, 20, SpringLayout.NORTH, rightSidePanel);	
		rightSidePanel.add(txtPalveluID);
	
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

		layout.putConstraint(SpringLayout.WEST, lblHinta, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblHinta, 145, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblHinta);

		layout.putConstraint(SpringLayout.WEST, txtHinta, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtHinta, 145, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtHinta);



		

		layout.putConstraint(SpringLayout.WEST, btnHae, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnHae, 175, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnHae);

		layout.putConstraint(SpringLayout.WEST, btnMuuta, 95, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnMuuta, 175, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnMuuta);

		layout.putConstraint(SpringLayout.WEST, btnLisaa, 175, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnLisaa, 175, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnLisaa);

		layout.putConstraint(SpringLayout.WEST, btnPoista, 250, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnPoista, 175, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnPoista);

		layout.putConstraint(SpringLayout.WEST, btnPaluu, 330, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnPaluu, 175, SpringLayout.NORTH, rightSidePanel);	
		rightSidePanel.add(btnPaluu);
		
		btnHae.addActionListener(  
			new ActionListener () {
				public void actionPerformed(ActionEvent actEvent) {	
                                            hae_palvelu();
                                    	
				}
			}
		);

		btnLisaa.addActionListener(
			new ActionListener () {
				public void actionPerformed(ActionEvent actEvent) {	
						lisaa_tiedot ();
					
				}
			}
		);

		btnMuuta.addActionListener(
			new ActionListener () {
				public void actionPerformed(ActionEvent actEvent) {	
						muuta_tiedot ();
					
				}
			}
		);

		btnPoista.addActionListener(
			new ActionListener () {
				public void actionPerformed(ActionEvent actEvent) {	
						poista_tiedot ();
					
				}
			}
		);

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
			JOptionPane.showMessageDialog(null, "Tapahtui virhe tietokantaa avattaessa.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Tapahtui JDBCvirhe tietokantaa avattaessa.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		}
	}
    public static void main(String args[]) {

	  
	
	}



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

	public  void hae_palvelu()  {

		m_palvelu = null;
		
		try {
			m_palvelu = m_palvelu.haePalvelu (m_conn, Integer.parseInt(txtPalveluID.getText()), Integer.parseInt(txtToimipisteID.getText()));
		} catch (SQLException se) {
		// SQL virheet
			JOptionPane.showMessageDialog(null, "Palvelua ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
		// muut virheet
			JOptionPane.showMessageDialog(null, "Palvelua ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		}
                
        if (Integer.parseInt(txtPalveluID.getText()) == m_palvelu.getM_palvelu_id() && Integer.parseInt(txtToimipisteID.getText()) == m_palvelu.getM_toimipiste_id())
        {
		// muut virheet
			// naytetaan tiedot
			txtPalveluID.setText(Integer.toString(m_palvelu.getM_palvelu_id()));
			txtToimipisteID.setText(Integer.toString(m_palvelu.getM_toimipiste_id()));
			txtNimi.setText(m_palvelu.getM_nimi());
			txtKuvaus.setText(m_palvelu.getM_kuvaus());
            txtHinta.setText(Double.toString(m_palvelu.getM_hinta()));
		}
                else
                {
                        txtPalveluID.setText("");
			txtToimipisteID.setText("");
			txtNimi.setText("");
			txtKuvaus.setText("");
                        txtHinta.setText("");

			JOptionPane.showMessageDialog(null, "Palvelua ei loydy.", "Virhe", JOptionPane.ERROR_MESSAGE);
		}

        }
	




	public  void lisaa_tiedot() {

		boolean palvelu_lisatty = true;
		m_palvelu = null;
		try {
				m_palvelu = m_palvelu.haePalvelu (m_conn, Integer.parseInt(txtPalveluID.getText()), Integer.parseInt(txtToimipisteID.getText()));
		} catch (SQLException se) {
		// SQL virheet
			palvelu_lisatty = false;
			JOptionPane.showMessageDialog(null, "Tietokantavirhe.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
		// muut virheet
			palvelu_lisatty = false;
			JOptionPane.showMessageDialog(null, "Tietokantavirhe.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		}
                  if (Integer.parseInt(txtPalveluID.getText()) == m_palvelu.getM_palvelu_id() && Integer.parseInt(txtToimipisteID.getText()) == m_palvelu.getM_toimipiste_id())
                {
		// suoritus jo olemassa, nÃ¤ytetÃ¤Ã¤n tiedot
			palvelu_lisatty = false;
			txtPalveluID.setText(Integer.toString(m_palvelu.getM_palvelu_id()));
			txtToimipisteID.setText(Integer.toString(m_palvelu.getM_toimipiste_id()));
			txtNimi.setText(m_palvelu.getM_nimi());
			txtKuvaus.setText(m_palvelu.getM_kuvaus());
                        txtHinta.setText(Double.toString(m_palvelu.getM_hinta()));
			JOptionPane.showMessageDialog(null, "Palvelu on jo olemassa.", "Virhe", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			// asetetaan tiedot oliolle
			m_palvelu.setM_palvelu_id(Integer.parseInt(txtPalveluID.getText()));
			m_palvelu.setM_toimipiste_id(Integer.parseInt(txtToimipisteID.getText()));
			m_palvelu.setM_nimi(txtNimi.getText());
			m_palvelu.setM_kuvaus(txtKuvaus.getText());
                        m_palvelu.setM_hinta(Double.parseDouble(txtHinta.getText()));

			try {
				// yritetÃ¤Ã¤n kirjoittaa kantaan
				m_palvelu.lisaaPalvelu (m_conn);
			} catch (SQLException se) {
			// SQL virheet
				palvelu_lisatty = false;
				JOptionPane.showMessageDialog(null, "Palvelun lisaaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
			//	 se.printStackTrace();
			} catch (Exception e) {
			// muut virheet
				palvelu_lisatty = false;
				JOptionPane.showMessageDialog(null, "Palvelun lisaaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);
			//	 e.printStackTrace();
			}finally {
				if (palvelu_lisatty == true)
					JOptionPane.showMessageDialog(null, "Palvelun tiedot lisatty tietokantaan.");
			}
		
		}
		
	}



	public  void muuta_tiedot() {
		//System.out.println("Muutetaan...");
			boolean palvelu_muutettu = true;
		// asetetaan tiedot oliolle
			m_palvelu.setM_palvelu_id(Integer.parseInt(txtPalveluID.getText()));
			m_palvelu.setM_toimipiste_id(Integer.parseInt(txtToimipisteID.getText()));
			m_palvelu.setM_nimi(txtNimi.getText());
			m_palvelu.setM_kuvaus(txtKuvaus.getText());
                        m_palvelu.setM_hinta(Double.parseDouble(txtHinta.getText()));
			
			try {
				// yritetÃ¤Ã¤n muuttaa (UPDATE) tiedot kantaan
				m_palvelu.muutaPalvelu (m_conn);
			} catch (SQLException se) {
			// SQL virheet
				palvelu_muutettu = false;
				JOptionPane.showMessageDialog(null, "Palvelun tietojen muuttaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
				 //se.printStackTrace();
			} catch (Exception e) {
			// muut virheet
				palvelu_muutettu = false;
				JOptionPane.showMessageDialog(null, "Palvelun tietojen muuttaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);
				// e.printStackTrace();
			} finally {
				if (palvelu_muutettu == true)
					JOptionPane.showMessageDialog(null, "Palvelun tiedot muutettu.");
			}
		
	}


    public  void poista_tiedot() {

		m_palvelu = null;
		boolean palvelu_poistettu = false;
		
		try {
			m_palvelu = m_palvelu.haePalvelu (m_conn, Integer.parseInt(txtPalveluID.getText()), Integer.parseInt(txtToimipisteID.getText()));
		} catch (SQLException se) {
		// SQL virheet
			JOptionPane.showMessageDialog(null, "Palvelua ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
		// muut virheet
			JOptionPane.showMessageDialog(null, "Palvelua ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		}
	       if (m_palvelu.getM_palvelu_id()== 0) {
		// muut virheet
                        txtPalveluID.setText("");
			txtToimipisteID.setText("");
			txtNimi.setText("");
			txtKuvaus.setText("");
                        txtHinta.setText("");
			JOptionPane.showMessageDialog(null, "Palvelua ei loydy.", "Virhe", JOptionPane.ERROR_MESSAGE);
			return; // poistutaan
		}
		else
		{
		
			txtPalveluID.setText(Integer.toString(m_palvelu.getM_palvelu_id()));
			txtToimipisteID.setText(Integer.toString(m_palvelu.getM_toimipiste_id()));
			txtNimi.setText(m_palvelu.getM_nimi());
			txtKuvaus.setText(m_palvelu.getM_kuvaus());
                        txtHinta.setText(Double.toString(m_palvelu.getM_hinta()));
		}
		try {
			if (JOptionPane.showConfirmDialog(null, "Haluatko todella poistaa palvelun?")==0) {// vahvistus ikkunassa
				m_palvelu.poistaPalvelu (m_conn);
				palvelu_poistettu = true;
			}
			} catch (SQLException se) {
			// SQL virheet
				JOptionPane.showMessageDialog(null, "Palvelun tietojen poistaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
				// se.printStackTrace();
			} catch (Exception e) {
			// muut virheet
				JOptionPane.showMessageDialog(null, "Palvelun tietojen poistaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);
				// e.printStackTrace();
			} finally {
				if (palvelu_poistettu == true) { // ainoastaan, jos vahvistettiin ja poisto onnistui
                                    txtPalveluID.setText("");
                                    txtToimipisteID.setText("");
                                    txtNimi.setText("");
                                    txtKuvaus.setText("");
                                    txtHinta.setText("");
                                            JOptionPane.showMessageDialog(null, "Palvelun tiedot poistettu tietokannasta.");
                                            m_palvelu = null;
				}         
	}
        }
}