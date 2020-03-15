import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.format.*;
import javax.swing.text.MaskFormatter;

import javax.swing.*;

import java.sql.*;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LaskuGUI extends MainUI {
	//
	private lasku m_lasku = new lasku();
	private Connection m_conn;
	private String tiedot;

	private JLabel lbllaskuid;
	private JLabel lblvarausid;
	private JLabel lblasiakasid;
	private JLabel lblnimi;
	private JLabel lbllahiosoite;
	private JLabel lblpostitoimipaikka;
	private JLabel lblpostinro;
	private JLabel lblsumma;
	private JLabel lblOtsikko;

	private JTextField txtlaskuid;
	private JTextField txtvarausid;
	private JTextField txtasiakasid;
	private JTextField txtnimi;
	private JTextField txtlahiosoite;
	private JTextField txtpostitoimipaikka;
	private JFormattedTextField txtpostinro;
	private JTextField txtsumma;

	private JButton btnLisaa;
	private JButton btnMuuta;
	private JButton btnHae;
	private JButton btnPoista;
	private JButton btnLaheta;
	private JButton btnTulosta;
	private final static int POINTS_PER_INCH = 72;
	/**
	 * Luo tulostukseen tarvittavan muotoilun ja lisaa sinne tekstit
	 */
	public class Document implements Printable, ActionListener {

		public int print(Graphics g, PageFormat pageFormat, int page) throws PrinterException {
			// hakee tiedot muuttujalle tiedot
			try {
				tiedot = lasku.tulosta(m_conn, Integer.parseInt(txtasiakasid.getText()),
						Integer.parseInt(txtvarausid.getText()), Integer.parseInt(txtlaskuid.getText()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

      //--- Create the Graphics2D object
      Graphics2D g2d = (Graphics2D) g;

      //--- Translate the origin to 0,0 for the top left corner
      g2d.translate(pageFormat.getImageableX(), pageFormat
          .getImageableY());

      //--- Set the drawing color to black
      g2d.setPaint(Color.black);

      //--- Draw a border arround the page using a 12 point border
      g2d.setStroke(new BasicStroke(12));
      Rectangle2D.Double border = new Rectangle2D.Double(0, 0, pageFormat
          .getImageableWidth(), pageFormat.getImageableHeight());

      g2d.draw(border);

      //--- Print page 1
      if (page == 1) {
        //--- Print the text one inch from the top and laft margins
        g2d.drawString(tiedot,
            POINTS_PER_INCH, POINTS_PER_INCH);
        return (PAGE_EXISTS);
	  }
return (NO_SUCH_PAGE);
}
/**
 * metodi jonka avulla tulostus tapahtuu
 */
public void actionPerformed(ActionEvent e) {
// luodaan uusi printerjob olio
 PrinterJob job = PrinterJob.getPrinterJob();
 // luodaan uusi book olio
 Book kirja = new Book();
 // luodaan sivun asettelu olio
 PageFormat dokumentti = new PageFormat();
 // asetetaan dokumenttiin asetteluja
 dokumentti.setOrientation(PageFormat.LANDSCAPE);
 // lisataan kirjaan tiedot 
 kirja.append(new Document(), dokumentti);
 // asetetaan kirja tulostukseen
 job.setPageable(kirja);
// ehto jossa tulostus kaynnistyy
 if (job.printDialog()) {
	 try {
		 // kaynnistaa tulostuksen
		  job.print();
	 } catch (PrinterException ex) {
	  // The job did not successfully complete
	 }
 }
}
}
/**
 * Sahkopostin lahetys metodi
 */
public void laheta(){
	// haetaan laskulle tiedot
				try {
					tiedot = lasku.laheta(m_conn, Integer.parseInt(txtasiakasid.getText()), Integer.parseInt(txtvarausid.getText()), Integer.parseInt(txtlaskuid.getText()) );
			} catch (Exception ex) {
				
			}
// luodaan desktop muuttuja
Desktop desktop;
// ehto jossa katsotaan onko desktop tuettu
if (Desktop.isDesktopSupported() 
&& (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
			URI mailto = null;
try {
// syotetaan mailto muuttujaan tiedot
	mailto = new URI(tiedot);
} catch (URISyntaxException ex) {
	
}
try {
// kokeillaan lahettaa viestia
	desktop.mail(mailto);
} catch (IOException ex) {
	
}
} else {
throw new RuntimeException("Tyopoyta ei tue tata toimintoa");
}
}
	public void modifyRightPanel(JPanel rightSidePanel)
	{
	lbllaskuid = new JLabel("LaskuID:");
        lblvarausid = new JLabel("VarausID:");
	lblasiakasid = new JLabel("AsiakasID:");
        lblnimi = new JLabel("Nimi:");
        lbllahiosoite = new JLabel("Osoite:");
        lblpostitoimipaikka = new JLabel ("Postitoimipaikka:");
        lblpostinro = new JLabel ("Postinumero:");
		lblsumma = new JLabel ("Summa:");
		
		lblOtsikko = new JLabel ("LASKUJENHALLINTA");
        txtlaskuid = new JTextField (25);
        txtvarausid = new JTextField (25);
        txtasiakasid = new JTextField (25);
        txtnimi = new JTextField (25);
        txtlahiosoite = new JTextField (25);
        txtpostitoimipaikka = new JTextField (25);
		txtpostinro = new JFormattedTextField();
        txtsumma = new JTextField (25);       
        
        btnHae = new JButton("Hae");
		btnMuuta = new JButton("Muuta");
		btnLisaa = new JButton("Lisaa");
		btnPoista = new JButton("Poista");
        btnLaheta = new JButton("Laheta");
        btnTulosta = new JButton("Tulosta");

		Dimension minimumSize = new Dimension(100, 50);  
		rightSidePanel.setMinimumSize(minimumSize); 
		SpringLayout layout = new SpringLayout();
		rightSidePanel.setLayout(layout);

		layout.putConstraint(SpringLayout.WEST, lblOtsikko, 01, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblOtsikko, 01, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblOtsikko);

		layout.putConstraint(SpringLayout.WEST, lbllaskuid, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lbllaskuid, 20, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lbllaskuid);

		layout.putConstraint(SpringLayout.WEST, txtlaskuid, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtlaskuid, 20, SpringLayout.NORTH, rightSidePanel);	
		rightSidePanel.add(txtlaskuid);
	
		layout.putConstraint(SpringLayout.WEST, lblvarausid, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblvarausid, 55, SpringLayout.NORTH, rightSidePanel);	
		rightSidePanel.add(lblvarausid);

		layout.putConstraint(SpringLayout.WEST, txtvarausid, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtvarausid, 55, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtvarausid);

		layout.putConstraint(SpringLayout.WEST, lblasiakasid, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblasiakasid, 85, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblasiakasid);

		layout.putConstraint(SpringLayout.WEST, txtasiakasid, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtasiakasid, 85, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtasiakasid);

		layout.putConstraint(SpringLayout.WEST, lblnimi, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblnimi, 115, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblnimi);

		layout.putConstraint(SpringLayout.WEST, txtnimi, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtnimi, 115, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtnimi);

		layout.putConstraint(SpringLayout.WEST, lbllahiosoite, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lbllahiosoite, 145, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lbllahiosoite);

		layout.putConstraint(SpringLayout.WEST, txtlahiosoite, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtlahiosoite, 145, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtlahiosoite);
                
        layout.putConstraint(SpringLayout.WEST, lblpostitoimipaikka, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblpostitoimipaikka, 170, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblpostitoimipaikka);
                
        layout.putConstraint(SpringLayout.WEST, txtpostitoimipaikka, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtpostitoimipaikka, 170, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtpostitoimipaikka);

        layout.putConstraint(SpringLayout.WEST, lblpostinro, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblpostinro, 205, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblpostinro);
                
        layout.putConstraint(SpringLayout.WEST, txtpostinro, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtpostinro, 205, SpringLayout.NORTH, rightSidePanel);
		txtpostinro.setPreferredSize( new Dimension( 100, 20 ) );
        rightSidePanel.add(txtpostinro);

        try 
        {
            MaskFormatter maskFormatter = new MaskFormatter("#####"); //tekee päivämäärien tekstikentille valmiin formaatin josta käyttäjä ei voi poiketa
            maskFormatter.install(txtpostinro); 
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }

                
        layout.putConstraint(SpringLayout.WEST, lblsumma, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblsumma, 235, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblsumma);
            
        layout.putConstraint(SpringLayout.WEST, txtsumma, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtsumma, 235, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(txtsumma);

		

                
                
                
                
                
		layout.putConstraint(SpringLayout.WEST, btnHae, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnHae, 265, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnHae);

		layout.putConstraint(SpringLayout.WEST, btnMuuta, 95, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnMuuta, 265, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnMuuta);

		layout.putConstraint(SpringLayout.WEST, btnLisaa, 175, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnLisaa, 265, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnLisaa);

		layout.putConstraint(SpringLayout.WEST, btnPoista, 250, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnPoista, 265, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnPoista);
                
        layout.putConstraint(SpringLayout.WEST, btnLaheta, 325, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnLaheta, 265, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnLaheta);
                
        layout.putConstraint(SpringLayout.WEST, btnTulosta, 400, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnTulosta, 265, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnTulosta);

		
		btnHae.addActionListener(  
			new ActionListener () {
				public void actionPerformed(ActionEvent actEvent) {	
                        hae_lasku();
                                    	
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

		btnLaheta.addActionListener(
			new ActionListener () {
				public void actionPerformed(ActionEvent actEvent) {	
						laheta();
					
				}
			}
		);
		btnTulosta.addActionListener( new Document());


		try 
		{
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
		String url = "jdbc:mariadb://localhost:3306/vp"; 
		try 
		{
			m_conn=DriverManager.getConnection(url,"root","123");
		}
		catch (SQLException e) { 
			m_conn = null;
			throw e;
		}
		catch (Exception e ) {
			throw e;
		}
		
	}

	public  void sulje_kanta() throws SQLException, Exception {
		
		try {
			m_conn.close ();
		}
		catch (SQLException e) { 
			throw e;
		}
		catch (Exception e ) { 
			throw e;
		}
        }

        public  void hae_lasku()  {

		m_lasku = null;
		
		try {
			m_lasku = m_lasku.haeLasku (m_conn, Integer.parseInt(txtlaskuid.getText()));
		} catch (SQLException se) {

			JOptionPane.showMessageDialog(null, "Toimipistetta ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Toimipistetta ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		}
                
                if (Integer.parseInt(txtlaskuid.getText()) == m_lasku.getM_lasku_id())
                {

			txtlaskuid.setText(Integer.toString(m_lasku.getM_lasku_id()));
			txtvarausid.setText(Integer.toString(m_lasku.getM_varaus_id()));
			txtasiakasid.setText(Integer.toString(m_lasku.getM_asiakas_id()));
			txtnimi.setText(m_lasku.getM_nimi());
            txtlahiosoite.setText(m_lasku.getM_lahiosoite());
			txtpostitoimipaikka.setText(m_lasku.getM_postitoimipaikka());
            txtpostinro.setText(m_lasku.getM_postinro());
            txtsumma.setText(Double.toString(m_lasku.getM_summa()));
		}
    	else
    	{
    		txtlaskuid.setText("");
			txtvarausid.setText("");
			txtasiakasid.setText("");
			txtnimi.setText("");
        	txtlahiosoite.setText("");
			txtpostitoimipaikka.setText("");
			txtpostinro.setText("");
        	txtsumma.setText("");

			JOptionPane.showMessageDialog(null, "Toimipistetta ei loydy.", "Virhe", JOptionPane.ERROR_MESSAGE);
		}

        }
	



 
		public  void lisaa_tiedot() {

			boolean lasku_lisatty = true;
			m_lasku = null;
			try {
				m_lasku = m_lasku.haeLasku(m_conn, Integer.parseInt(txtlaskuid.getText()));
			} catch (SQLException se) {

			lasku_lisatty = false;
				JOptionPane.showMessageDialog(null, "Tietokantavirhe.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {

			lasku_lisatty = false;
				JOptionPane.showMessageDialog(null, "Tietokantavirhe.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
			}
			if (Integer.parseInt(txtlaskuid.getText()) == m_lasku.getM_lasku_id())
			{
                                    
                                    
                                    
            	txtlaskuid.setText(Integer.toString(m_lasku.getM_lasku_id()));
				txtvarausid.setText(Integer.toString(m_lasku.getM_varaus_id()));
				txtasiakasid.setText(Integer.toString(m_lasku.getM_asiakas_id()));
				txtnimi.setText(m_lasku.getM_nimi());
            	txtlahiosoite.setText(m_lasku.getM_lahiosoite());
				txtpostitoimipaikka.setText(m_lasku.getM_postitoimipaikka());
            	txtpostinro.setText(m_lasku.getM_postinro());
            	txtsumma.setText(Double.toString(m_lasku.getM_summa()));

			JOptionPane.showMessageDialog(null, "Lasku on jo olemassa.", "Virhe", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				
				m_lasku.setM_lasku_id(Integer.parseInt(txtlaskuid.getText()));
				m_lasku.setM_varaus_id(Integer.parseInt(txtvarausid.getText()));
				m_lasku.setM_asiakas_id(Integer.parseInt(txtasiakasid.getText()));

				try {

					m_lasku.lisaaLasku (m_conn);
				} catch (SQLException se) {

				lasku_lisatty = false;
					JOptionPane.showMessageDialog(null, "Laskun lisaaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

				} catch (Exception e) {
				lasku_lisatty = false;
					JOptionPane.showMessageDialog(null, "Toimipisteen lisaaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);
				}finally {
					if (lasku_lisatty == true)
						JOptionPane.showMessageDialog(null, "Toimipisteen tiedot lisatty tietokantaan.");
				}
			
			}
			
		}

		public  void muuta_tiedot() {

				boolean lasku_muutettu = true;

				m_lasku.setM_lasku_id(Integer.parseInt(txtlaskuid.getText()));
				m_lasku.setM_varaus_id(Integer.parseInt(txtvarausid.getText()));
				m_lasku.setM_asiakas_id(Integer.parseInt(txtasiakasid.getText()));
				m_lasku.setM_nimi(txtnimi.getText());
				m_lasku.setM_lahiosoite(txtlahiosoite.getText());
				m_lasku.setM_postitoimipaikka(txtpostitoimipaikka.getText());
				m_lasku.setM_postinro(txtpostinro.getText());
                m_lasku.setM_summa(Double.parseDouble(txtsumma.getText()));
				try {

					m_lasku.muutaLasku (m_conn);
				} catch (SQLException se) {
					lasku_muutettu = false;
					JOptionPane.showMessageDialog(null, "Laskun tietojen muuttaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
				lasku_muutettu = false;
					JOptionPane.showMessageDialog(null, "Laskun tietojen muuttaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);
				} finally {
					if (lasku_muutettu == true)
						JOptionPane.showMessageDialog(null, "Laskun tiedot muutettu.");
				}
			
		}
	

		public  void poista_tiedot() {

			m_lasku = null;
			boolean toimipiste_poistettu = false;
			
			try {
				m_lasku = m_lasku.haeLasku(m_conn, Integer.parseInt(txtlaskuid.getText()));
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(null, "Laskua ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Laskua ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
			}
			   if (m_lasku.getM_lasku_id()== 0) {
                             txtlaskuid.setText("");
                             txtvarausid.setText("");
                             txtasiakasid.setText("");
                             txtnimi.setText("");
                             txtlahiosoite.setText("");
                             txtpostitoimipaikka.setText("");
                             txtpostinro.setText("");
                             txtsumma.setText("");
				
			JOptionPane.showMessageDialog(null, "Laskua ei loydy.", "Virhe", JOptionPane.ERROR_MESSAGE);
				return; 
			}
			else
			{
	                txtlaskuid.setText(Integer.toString(m_lasku.getM_lasku_id()));
			txtvarausid.setText(Integer.toString(m_lasku.getM_varaus_id()));
			txtasiakasid.setText(Integer.toString(m_lasku.getM_asiakas_id()));
			txtnimi.setText(m_lasku.getM_nimi());
                        txtlahiosoite.setText(m_lasku.getM_lahiosoite());
			txtpostitoimipaikka.setText(m_lasku.getM_postitoimipaikka());
                        txtpostinro.setText(m_lasku.getM_postinro());
                    txtsumma.setText(Double.toString(m_lasku.getM_summa()));
			}
			try {
				if (JOptionPane.showConfirmDialog(null, "Haluatko todella poistaa laskun?")==0) {// vahvistus ikkunassa
					m_lasku.poistaLasku (m_conn);
					toimipiste_poistettu = true;
				}
				} catch (SQLException se) {
				// SQL virheet
					JOptionPane.showMessageDialog(null, "Laskun tietojen poistaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
					// se.printStackTrace();
				} catch (Exception e) {
				// muut virheet
					JOptionPane.showMessageDialog(null, "Laskun tietojen poistaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);
					// e.printStackTrace();
				} finally {
					if (toimipiste_poistettu == true) { // ainoastaan, jos vahvistettiin ja poisto onnistui
                                              txtlaskuid.setText("");
                                              txtvarausid.setText("");
                                              txtasiakasid.setText("");
                                              txtnimi.setText("");
                                              txtlahiosoite.setText("");
                                              txtpostitoimipaikka.setText("");
                                              txtpostinro.setText("");
                                              txtsumma.setText("");
							
						JOptionPane.showMessageDialog(null, "Laskun tiedot poistettu tietokannasta.");
						m_lasku = null;
					}         
		
				}
			

			}

	
}