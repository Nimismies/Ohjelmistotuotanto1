

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Antto
 */
/**
 * Konstruktori joka luo GUI:n
 */
public class hallintaGUI extends MainUI{
    
    private Connection m_conn; // tietokantayhteys
    // Tarvittavat labelit
    private JLabel lblAloitus;
    private JLabel lblLopetus;
    private JLabel lblPaiva;
    private JLabel lblToimipisteID;
    private JLabel lblOtsikko;
    // tarvittavat textfieldit
    private JTextField txtAloitus;
    private JTextField txtLopetus;
    private JTextField txtPaiva;
    private JTextField txtToimipisteID;
    // tarvittavat buttonit
    private JButton btnRaportoi;
    private JButton btnRaportoi2;
    /**
     * Muotoilee GUIn tarvittavaan muotoon
     * @param rightSidePanel paneeli jota kaytetaan GUIn muodostamisessa oikeaan paikkaan
     */
    public void modifyRightPanel(JPanel rightSidePanel){
        // luodaan labelit
        lblAloitus = new JLabel("Aloitus pvm");
        lblLopetus = new JLabel("lopetus pvm");
        lblPaiva = new JLabel ("Tietty pvm");
        lblToimipisteID = new JLabel("ToimipisteID");
        lblOtsikko = new JLabel ("MOKKIEN RAPORTOINTI");
        // luodaan textfieldit maaritellyilla pituuksilla
        txtAloitus = new JTextField (20);
        txtLopetus = new JTextField (20);
        txtPaiva = new JTextField (20);
        txtToimipisteID = new JTextField (10);
        // luodaan napit
        btnRaportoi = new JButton("Luo ajanjakson raportti");
        btnRaportoi2 = new JButton("Luo paiva raportti");
        // oikean ja vasemman paneelin minimikoko
        Dimension minimumSize = new Dimension(100, 50);
        // asetetaan minimikoko oikeaan paneeliin
        rightSidePanel.setMinimumSize(minimumSize);
        // luodaan muuttuja asettelua varten
        SpringLayout layout = new SpringLayout();
        // asetetaan asettelija oikealle paneelille
        rightSidePanel.setLayout(layout);
        // maaritellaan muuttujille oikeat paikat
        layout.putConstraint(SpringLayout.WEST, lblOtsikko, 01, SpringLayout.WEST, rightSidePanel);
layout.putConstraint(SpringLayout.NORTH, lblOtsikko, 01, SpringLayout.NORTH, rightSidePanel);
rightSidePanel.add(lblOtsikko);

        layout.putConstraint(SpringLayout.WEST, lblAloitus, 20, SpringLayout.WEST, rightSidePanel);
        layout.putConstraint(SpringLayout.NORTH, lblAloitus, 20, SpringLayout.NORTH, rightSidePanel);
        // lisataan muuttuja oikeaan paneeliin
        rightSidePanel.add(lblAloitus);

        layout.putConstraint(SpringLayout.WEST, txtAloitus, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtAloitus, 20, SpringLayout.NORTH, rightSidePanel);	
		rightSidePanel.add(txtAloitus);
	
		layout.putConstraint(SpringLayout.WEST, lblLopetus, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblLopetus, 55, SpringLayout.NORTH, rightSidePanel);	
        rightSidePanel.add(lblLopetus);
        
        layout.putConstraint(SpringLayout.WEST, txtLopetus, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtLopetus, 55, SpringLayout.NORTH, rightSidePanel);
        rightSidePanel.add(txtLopetus);
        
        layout.putConstraint(SpringLayout.WEST, lblPaiva, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblPaiva, 85, SpringLayout.NORTH, rightSidePanel);	
		rightSidePanel.add(lblPaiva);

		layout.putConstraint(SpringLayout.WEST, txtPaiva, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtPaiva, 85, SpringLayout.NORTH, rightSidePanel);
        rightSidePanel.add(txtPaiva);
        
        layout.putConstraint(SpringLayout.WEST, lblToimipisteID, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, lblToimipisteID, 115, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(lblToimipisteID);

		layout.putConstraint(SpringLayout.WEST, txtToimipisteID, 150, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, txtToimipisteID, 115, SpringLayout.NORTH, rightSidePanel);
        rightSidePanel.add(txtToimipisteID);
        
        layout.putConstraint(SpringLayout.WEST, btnRaportoi, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnRaportoi, 250, SpringLayout.NORTH, rightSidePanel);
        rightSidePanel.add(btnRaportoi);
        
        layout.putConstraint(SpringLayout.WEST, btnRaportoi2, 200, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnRaportoi2, 250, SpringLayout.NORTH, rightSidePanel);
		rightSidePanel.add(btnRaportoi2);
        
        btnRaportoi.addActionListener(   // toteutetaan  käyttämällä Javan ns. nimettömiä sisäluokkia
            new ActionListener () {// parametrina luotavan "rajapintaluokan ilmentymä": new ActionListener()
		public void actionPerformed(ActionEvent actEvent) {	
                    try {
                        hallinta.raportti(m_conn, Integer.parseInt(txtToimipisteID.getText()), txtAloitus.getText(), txtLopetus.getText());
                    } catch (Exception ex) {
                        Logger.getLogger(hallintaGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
            }
	);
    btnRaportoi2.addActionListener(   // toteutetaan  käyttämällä Javan ns. nimettömiä sisäluokkia
    new ActionListener () {// parametrina luotavan "rajapintaluokan ilmentymä": new ActionListener()
public void actionPerformed(ActionEvent actEvent) {	
            try {
                hallinta.raportti2(m_conn, Integer.parseInt(txtToimipisteID.getText()), txtPaiva.getText());
            } catch (Exception ex) {
                Logger.getLogger(hallintaGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
}
    }
);
        // yhdistetaan tietokantaan
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
     * Tietokannan yhdistamiseen kaytetty metodi
     * @throws SQLException virheilmoitus
     * @throws Exception virheilmoitus
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
	}           

