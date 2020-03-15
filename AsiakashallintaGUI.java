import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.format.*;
import javax.swing.text.MaskFormatter;


public class AsiakashallintaGUI extends MainUI  {
//
    private asiakasHallinta m_asiakas = new asiakasHallinta ();
    private Connection m_conn; // tietokantayhteys
    private JLabel lblAsiakasID;
    private JLabel lblEtunimi;
    private JLabel lblSukunimi;
    private JLabel lblLahiosoite;
    private JLabel lblPostitoimipaikka;
    private JLabel lblPostinro;
    private JLabel lblEmail;
	private JLabel lblPuhelinnumero;
	private JLabel lblOtsikko;
	



    private JTextField txtAsiakasID;
    private JTextField txtEtunimi;
    private JTextField txtSukunimi;
    private JTextField txtLahiosoite;
    private JTextField txtPostitoimipaikka;
	private JFormattedTextField txtPostinro;
    private JTextField txtEmail;
    private JTextField txtPuhelinnumero;




    private JButton btnLisaa;
    private JButton btnMuuta;
    private JButton btnHae;
    private JButton btnPoista;
    private JButton btnPaluu;

	public void modifyRightPanel(JPanel rightSidePanel)
	{


		lblAsiakasID = new JLabel("Asiakastunnus:");
        lblEtunimi = new JLabel("Etunimi:");
		lblSukunimi = new JLabel("Sukunimi:");
        lblLahiosoite = new JLabel("Lahiosoite:");
		lblPostitoimipaikka = new JLabel ("Postitoimipaikka:");
        lblPostinro = new JLabel("Postinro:");
        lblEmail = new JLabel("Email:");
		lblPuhelinnumero = new JLabel("Puhelinnumero:");
		lblOtsikko = new JLabel ("ASIAKASHALLINTA");

        txtAsiakasID = new JTextField (25);
        txtEtunimi = new JTextField (25);
        txtSukunimi = new JTextField (25);
        txtLahiosoite = new JTextField (25);
        txtPostitoimipaikka = new JTextField (25);
		txtPostinro = new JFormattedTextField();
        txtEmail = new JTextField (25);
        txtPuhelinnumero = new JTextField (25);
        
        btnHae = new JButton("Hae");
		btnMuuta = new JButton("Muuta");
		btnLisaa = new JButton("Lisaa");
		btnPoista = new JButton("Poista");
		btnPaluu = new JButton("Lopeta");



		
		Dimension minimumSize = new Dimension(100, 50);  //Provide minimum sizes for the split panes sides
		rightSidePanel.setMinimumSize(minimumSize); 
		SpringLayout layout = new SpringLayout();
		rightSidePanel.setLayout(layout);

		layout.putConstraint(SpringLayout.WEST, lblOtsikko, 01, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblOtsikko, 01, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblOtsikko);

		layout.putConstraint(SpringLayout.WEST, lblAsiakasID, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblAsiakasID, 20, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblAsiakasID);	

		layout.putConstraint(SpringLayout.WEST, txtAsiakasID, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtAsiakasID, 20, SpringLayout.NORTH, rightSidePanel);	
		rightSidePanel.add(txtAsiakasID);
	
		layout.putConstraint(SpringLayout.WEST, lblEtunimi, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblEtunimi, 55, SpringLayout.NORTH, rightSidePanel);	
		rightSidePanel.add(lblEtunimi);

		layout.putConstraint(SpringLayout.WEST, txtEtunimi, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtEtunimi, 55, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtEtunimi);

		layout.putConstraint(SpringLayout.WEST, lblSukunimi, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblSukunimi, 85, SpringLayout.NORTH, rightSidePanel);	
		rightSidePanel.add(lblSukunimi);

		layout.putConstraint(SpringLayout.WEST, txtSukunimi, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtSukunimi, 85, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtSukunimi);

		layout.putConstraint(SpringLayout.WEST, lblLahiosoite, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblLahiosoite, 115, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblLahiosoite);

		layout.putConstraint(SpringLayout.WEST, txtLahiosoite, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtLahiosoite, 115, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtLahiosoite);

		layout.putConstraint(SpringLayout.WEST, lblPostitoimipaikka, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblPostitoimipaikka, 145, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblPostitoimipaikka);

		layout.putConstraint(SpringLayout.WEST, txtPostitoimipaikka, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtPostitoimipaikka, 145, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtPostitoimipaikka);

		layout.putConstraint(SpringLayout.WEST, lblPostinro, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblPostinro, 175, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblPostinro);

		layout.putConstraint(SpringLayout.WEST, txtPostinro, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtPostinro, 175, SpringLayout.NORTH, rightSidePanel);
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
		
		layout.putConstraint(SpringLayout.WEST, lblEmail, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblEmail, 200, SpringLayout.NORTH, rightSidePanel);
        rightSidePanel.add(lblEmail);

        layout.putConstraint(SpringLayout.WEST, txtEmail, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtEmail, 200, SpringLayout.NORTH, rightSidePanel);
        rightSidePanel.add(txtEmail);
        
        layout.putConstraint(SpringLayout.WEST, lblPuhelinnumero, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblPuhelinnumero, 225, SpringLayout.NORTH, rightSidePanel);
        rightSidePanel.add(lblPuhelinnumero);
        
        layout.putConstraint(SpringLayout.WEST, txtPuhelinnumero, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtPuhelinnumero, 225, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtPuhelinnumero);

		layout.putConstraint(SpringLayout.WEST, btnHae, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnHae, 250, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnHae);

		layout.putConstraint(SpringLayout.WEST, btnMuuta, 90, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnMuuta, 250, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnMuuta);

		layout.putConstraint(SpringLayout.WEST, btnLisaa, 170, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnLisaa, 250, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnLisaa);

		layout.putConstraint(SpringLayout.WEST, btnPoista, 245, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnPoista, 250, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnPoista);

		layout.putConstraint(SpringLayout.WEST, btnPaluu, 325, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnPaluu, 250, SpringLayout.NORTH, rightSidePanel);	
		rightSidePanel.add(btnPaluu);
		
		
		btnHae.addActionListener(  
			new ActionListener () {
				public void actionPerformed(ActionEvent actEvent) {	
                                            hae_asiakas();
                                    	
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

	public  void hae_asiakas()  {
		m_asiakas = null;
		
		try {
			m_asiakas = m_asiakas.haeAsiakas(m_conn, Integer.parseInt(txtAsiakasID.getText()));
		} catch (SQLException se) {
		// SQL virheet
			JOptionPane.showMessageDialog(null, "Asiakasta ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
		// muut virheet
			JOptionPane.showMessageDialog(null, "Asiakasta ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		}
                
        if (Integer.parseInt(txtAsiakasID.getText()) == m_asiakas.getM_asiakas_id()) 
        {
			txtAsiakasID.setText(Integer.toString(m_asiakas.getM_asiakas_id()));
			txtEtunimi.setText(m_asiakas.getM_etunimi());
			txtSukunimi.setText(m_asiakas.getM_sukunimi());
			txtLahiosoite.setText(m_asiakas.getM_lahiosoite());
            txtPostitoimipaikka.setText(m_asiakas.getM_postitoimipaikka());
			txtPostinro.setText(Integer.toString(m_asiakas.getM_postinro()));
			txtEmail.setText(m_asiakas.getM_email());
			txtPuhelinnumero.setText(Integer.toString(m_asiakas.getM_puhelinnumero()));
		}
        else
        {
            txtAsiakasID.setText("");
			txtEtunimi.setText("");
			txtSukunimi.setText("");
			txtLahiosoite.setText("");
            txtPostitoimipaikka.setText("");
			txtPostinro.setText("");
			txtEmail.setText("");
			txtPuhelinnumero.setText("");

			JOptionPane.showMessageDialog(null, "Asiakasta ei loydy.", "Virhe", JOptionPane.ERROR_MESSAGE);
		}

        }
	



    /**
     * LisÃ¤tÃ¤Ã¤n tiedot syÃ¶tetyt tiedot tietokantaan
     */

	public  void lisaa_tiedot() {
		// lisÃ¤tÃ¤Ã¤n tietokantaan asiakas
		//System.out.println("Lisataan...");
		boolean asiakas_lisatty = true;
		m_asiakas = null;
		try 
		{
				m_asiakas = m_asiakas.haeAsiakas (m_conn, Integer.parseInt(txtAsiakasID.getText()));
		} 
		catch (SQLException se) 
		{
		// SQL virheet
			asiakas_lisatty = false;
			JOptionPane.showMessageDialog(null, "Tietokantavirhe.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) 
		{
		// muut virheet
			asiakas_lisatty = false;
			JOptionPane.showMessageDialog(null, "Tietokantavirhe.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		}
        if (Integer.parseInt(txtAsiakasID.getText()) == m_asiakas.getM_asiakas_id())
        {
		// suoritus jo olemassa, nÃ¤ytetÃ¤Ã¤n tiedot
			asiakas_lisatty = false;
			txtAsiakasID.setText(Integer.toString(m_asiakas.getM_asiakas_id()));
			txtEtunimi.setText(m_asiakas.getM_etunimi());
			txtSukunimi.setText(m_asiakas.getM_sukunimi());
			txtLahiosoite.setText(m_asiakas.getM_lahiosoite());
            txtPostitoimipaikka.setText(m_asiakas.getM_postitoimipaikka());
			txtPostinro.setText(Integer.toString(m_asiakas.getM_postinro()));
			txtEmail.setText(m_asiakas.getM_email());
			txtPuhelinnumero.setText(Integer.toString(m_asiakas.getM_puhelinnumero()));
			JOptionPane.showMessageDialog(null, "Asiakas on jo olemassa.", "Virhe", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			// asetetaan tiedot oliolle
			m_asiakas.setM_asiakas_id(Integer.parseInt(txtAsiakasID.getText()));
			m_asiakas.setM_etunimi(txtEtunimi.getText());
			m_asiakas.setM_sukunimi(txtSukunimi.getText());
			m_asiakas.setM_lahiosoite(txtLahiosoite.getText());
            m_asiakas.setM_postitoimipaikka(txtPostitoimipaikka.getText());
			m_asiakas.setM_postinro(Integer.parseInt(txtPostinro.getText()));
			m_asiakas.setM_email(txtEmail.getText());
			m_asiakas.setM_puhelinnumero(Integer.parseInt(txtPuhelinnumero.getText()));

			try 
			{
				// yritetÃ¤Ã¤n kirjoittaa kantaan
				m_asiakas.lisaaAsiakas (m_conn);
			} 
			catch (SQLException se) 
			{
			// SQL virheet
				asiakas_lisatty = false;
				JOptionPane.showMessageDialog(null, "Asiakkaan lisaaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
				//se.printStackTrace();
			} 
			catch (Exception e) 
			{
			// muut virheet
				asiakas_lisatty = false;
				JOptionPane.showMessageDialog(null, "Asiakkaan lisaaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);
			//	 e.printStackTrace();
			}
			finally 
			{
				if (asiakas_lisatty == true)
				JOptionPane.showMessageDialog(null, "Asiakkaan tiedot lisatty tietokantaan.");
			}
		
		}
		
	}



    /**
     * Muutetaan nÃ¤ytÃ¶llÃ¤ olevia tietoja ja viedÃ¤Ã¤n ne tietokantaan
     */

	public  void muuta_tiedot() {
		//System.out.println("Muutetaan...");
			boolean asiakas_muutettu = true;
		// asetetaan tiedot oliolle
		m_asiakas.setM_asiakas_id(Integer.parseInt(txtAsiakasID.getText()));
		m_asiakas.setM_etunimi(txtEtunimi.getText());
		m_asiakas.setM_sukunimi(txtSukunimi.getText());
		m_asiakas.setM_lahiosoite(txtLahiosoite.getText());
		m_asiakas.setM_postitoimipaikka(txtPostitoimipaikka.getText());
		m_asiakas.setM_postinro(Integer.parseInt(txtPostinro.getText()));
		m_asiakas.setM_email(txtEmail.getText());
		m_asiakas.setM_puhelinnumero(Integer.parseInt(txtPuhelinnumero.getText()));
			
			try 
			{
				// yritetÃ¤Ã¤n muuttaa (UPDATE) tiedot kantaan
				m_asiakas.muutaAsiakas (m_conn);
			} 
			catch (SQLException se) 
			{
			// SQL virheet
				asiakas_muutettu = false;
				JOptionPane.showMessageDialog(null, "Asiakkaan tietojen muuttaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
				 //se.printStackTrace();
			} 
			catch (Exception e) 
			{
			// muut virheet
			asiakas_muutettu = false;
				JOptionPane.showMessageDialog(null, "Asiakkaan tietojen muuttaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);
				//e.printStackTrace();
			} 
			finally 
			{
				if (asiakas_muutettu == true)
					JOptionPane.showMessageDialog(null, "Asiakkaan tiedot muutettu.");
			}
		
	}

    /**
     * Poistetaan tiedot tietokannasta 
     */
    public  void poista_tiedot() {
		// haetaan tietokannasta mokkia, jonka mokki_id = txtMokkiID ja mokki_id = txtMokkiID 
		m_asiakas = null;
		boolean asiakas_poistettu = false;
		
		try 
		{
			m_asiakas = m_asiakas.haeAsiakas (m_conn, Integer.parseInt(txtAsiakasID.getText()));
		} 
		catch (SQLException se) 
		{
		// SQL virheet
			JOptionPane.showMessageDialog(null, "Asiakasta ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		} 
		catch (Exception e) 
		{
		// muut virheet
			JOptionPane.showMessageDialog(null, "Asiakasta ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		}
	       if (m_asiakas.getM_asiakas_id()== 0) {
		// muut virheet
		txtAsiakasID.setText("");
		txtEtunimi.setText("");
		txtSukunimi.setText("");
		txtLahiosoite.setText("");
		txtPostitoimipaikka.setText("");
		txtPostinro.setText("");
		txtEmail.setText("");
		txtPuhelinnumero.setText("");
		JOptionPane.showMessageDialog(null, "Asiakasta ei loydy.", "Virhe", JOptionPane.ERROR_MESSAGE);
		return; // poistutaan
		}
		else
		{
			// naytetaan poistettavan suorituksen tiedot
			txtAsiakasID.setText(Integer.toString(m_asiakas.getM_asiakas_id()));
			txtEtunimi.setText(m_asiakas.getM_etunimi());
			txtSukunimi.setText(m_asiakas.getM_sukunimi());
			txtLahiosoite.setText(m_asiakas.getM_lahiosoite());
            txtPostitoimipaikka.setText(m_asiakas.getM_postitoimipaikka());
			txtPostinro.setText(Integer.toString(m_asiakas.getM_postinro()));
			txtEmail.setText(m_asiakas.getM_email());
			txtPuhelinnumero.setText(Integer.toString(m_asiakas.getM_puhelinnumero()));

		}
		try 
		{
			if (JOptionPane.showConfirmDialog(null, "Haluatko todella poistaa asiakkaan?")==0) {// vahvistus ikkunassa
				m_asiakas.poistaAsiakas (m_conn);
				asiakas_poistettu = true;
		}
			} 
			catch (SQLException se) 
			{
			// SQL virheet
				JOptionPane.showMessageDialog(null, "Asiakkaan tietojen poistaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
				// se.printStackTrace();
			} catch (Exception e) 
			{
			// muut virheet
				JOptionPane.showMessageDialog(null, "Asiakkaan tietojen poistaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);
				// e.printStackTrace();
			} finally 
			{
				if (asiakas_poistettu == true) { // ainoastaan, jos vahvistettiin ja poisto onnistui
					txtAsiakasID.setText("");
					txtEtunimi.setText("");
					txtSukunimi.setText("");
					txtLahiosoite.setText("");
					txtPostitoimipaikka.setText("");
					txtPostinro.setText("");
					txtEmail.setText("");
					txtPuhelinnumero.setText("");
                    JOptionPane.showMessageDialog(null, "Asiakkaan tiedot poistettu tietokannasta.");
                    m_asiakas = null;
				}         
			}
    }
}