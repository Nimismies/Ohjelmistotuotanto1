
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.*;
import java.util.regex.*;
import java.time.*;
import java.text.DateFormat;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.format.*;
import javax.swing.text.MaskFormatter;


public class VaraustenHallintaGUI extends MainUI
{
    
    JButton btnMokinVaraus;
    JButton btnUusiMokkiVaraus;
    JButton btnMuokkaaMokkivarausta;
    JButton btnPoistaMokkiVaraus;
    JButton btnPalveluVaraus;
    JButton btnUusiPalveluVaraus;
    JButton btnMuokkaaPalveluVarausta;
    JButton btnPoistaPalveluVaraus;
    JButton btnBack;                        //Onko tämä turha kun ei ole käytössä missään?
    JButton btnMokinVaraukset;
    JButton btnPalveluVaraukset;
    JButton btnHaeMokinVaraukset;
    JButton btnHaePalveluVaraukset;

    public static JTextField txtVarauksenTunnus;
    public static JTextField txtMokinTunnus;
    public static JTextField txtPalvelunTunnus;
    public static JTextField txtLkm;
    public static JTextField txtAsiakasTunnus;
    public static JTextField txtToimipisteTunnus;
    public static JFormattedTextField txtVarattu_Alkupvm;
    public static JFormattedTextField txtVarattu_Loppupvm;

    JLabel lblVarauksenTunnus;
    JLabel lblMokinTunnus;
    JLabel lblPalvelunTunnus;
    JLabel lblLkm;
    JLabel lblAsiakasTunnus;
    JLabel lblToimipistetunnus;
    JLabel lblVarattu_Alkupvm;
    JLabel lblVarattu_Loppupvm;
    private JLabel lblOtsikko;
    private JLabel lblOtsikko2;

    private Connection m_conn; 
    private Date Days;


    public void modifyRightPanel(JPanel rightSidePanel) //oikean puolen paneeli annetaan funktiolle parametrina jotta se muokkaa sen oikein
    {                                                   
        btnPalveluVaraukset = new JButton("Palveluiden varaukset");
        btnMokinVaraukset = new JButton("Mokkien varaukset");
        btnUusiMokkiVaraus = new JButton("Tee uusi mokkivaraus"); 
        btnMuokkaaMokkivarausta = new JButton("Muokkaa nykyista mokkivarausta");
        btnPoistaMokkiVaraus = new JButton("Poista mokkivaraus");
        btnUusiPalveluVaraus = new JButton("Tee uusi palvelun varaus");
        btnMuokkaaPalveluVarausta = new JButton("Muokkaa nykyista palvelun varausta");
        btnPoistaPalveluVaraus = new JButton("Poista palvelun varaus");
        btnHaeMokinVaraukset = new JButton ("Hae mokkien varaukset");
        btnHaePalveluVaraukset = new JButton ("Hae palveluiden varaukset");
        btnHaeMokinVaraukset = new JButton ("Hae mokkivaraukset");
        btnHaePalveluVaraukset = new JButton ("Hae palveluvaraukset");


        txtVarauksenTunnus = new JTextField(5);
        txtMokinTunnus = new JTextField(5);
        txtPalvelunTunnus = new JTextField(5);
        txtLkm = new JTextField(3);
        txtAsiakasTunnus = new JTextField(5);
        txtToimipisteTunnus = new JTextField(5);
        txtVarattu_Alkupvm = new JFormattedTextField();
        txtVarattu_Loppupvm = new JFormattedTextField();
    
        lblVarauksenTunnus = new JLabel("Varauksen tunnus:");
        lblMokinTunnus = new JLabel("Mokin tunnus:");
        lblPalvelunTunnus = new JLabel("Palvelun tunnus:");
        lblLkm = new JLabel("Varausten lukumaara:");
        lblAsiakasTunnus = new JLabel("Asiakastunnus:");
        lblToimipistetunnus = new JLabel("Toimipistetunnus:");
        lblVarattu_Alkupvm = new JLabel("Varauksen alkupvm:");
        lblVarattu_Loppupvm = new JLabel("Varauksen loppupvm:");
        lblOtsikko = new JLabel ("MOKKIEN VARAUKSET");
        lblOtsikko2 = new JLabel ("PALVELUIDEN VARAUKSET");



        Dimension minimumSize = new Dimension(100, 50);  //Provide minimum sizes for the split panes sides
        rightSidePanel.setMinimumSize(minimumSize); 
		SpringLayout layout = new SpringLayout();
        rightSidePanel.setLayout(layout);

        
        layout.putConstraint(SpringLayout.WEST, btnPalveluVaraukset, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnPalveluVaraukset, 20, SpringLayout.NORTH, rightSidePanel);
        rightSidePanel.add(btnPalveluVaraukset);

        layout.putConstraint(SpringLayout.WEST, btnMokinVaraukset, 20, SpringLayout.WEST, rightSidePanel);
	    layout.putConstraint(SpringLayout.NORTH, btnMokinVaraukset, 50, SpringLayout.NORTH, rightSidePanel);
        rightSidePanel.add(btnMokinVaraukset);

        btnMokinVaraukset.addActionListener(
        new ActionListener () 
        {
            public void actionPerformed(ActionEvent actEvent) 
            {	
                rightSidePanel.repaint();    //Käytetään 3 alla olevaa riviä jotta saadaan tyhjennettyä rightSidePanel edellisistä sisällöistään
                rightSidePanel.revalidate();
                rightSidePanel.removeAll();

                layout.putConstraint(SpringLayout.WEST, lblOtsikko, 350, SpringLayout.WEST, rightSidePanel);
                layout.putConstraint(SpringLayout.NORTH, lblOtsikko, 01, SpringLayout.NORTH, rightSidePanel);
                rightSidePanel.add(lblOtsikko);

                layout.putConstraint(SpringLayout.WEST, lblVarauksenTunnus, 5, SpringLayout.WEST, rightSidePanel);
		        layout.putConstraint(SpringLayout.NORTH, lblVarauksenTunnus, 5, SpringLayout.NORTH, rightSidePanel);
                rightSidePanel.add(lblVarauksenTunnus);	

                layout.putConstraint(SpringLayout.WEST, txtVarauksenTunnus, 150, SpringLayout.WEST, rightSidePanel);
		        layout.putConstraint(SpringLayout.NORTH, txtVarauksenTunnus, 5, SpringLayout.NORTH, rightSidePanel);	
                rightSidePanel.add(txtVarauksenTunnus);

                layout.putConstraint(SpringLayout.WEST, lblMokinTunnus, 5, SpringLayout.WEST, rightSidePanel);
		        layout.putConstraint(SpringLayout.NORTH, lblMokinTunnus, 35, SpringLayout.NORTH, rightSidePanel);	
		        rightSidePanel.add(lblMokinTunnus);

		        layout.putConstraint(SpringLayout.WEST, txtMokinTunnus, 150, SpringLayout.WEST, rightSidePanel);
		        layout.putConstraint(SpringLayout.NORTH, txtMokinTunnus, 35, SpringLayout.NORTH, rightSidePanel);
		        rightSidePanel.add(txtMokinTunnus);

		        layout.putConstraint(SpringLayout.WEST, lblLkm, 5, SpringLayout.WEST, rightSidePanel);
		        layout.putConstraint(SpringLayout.NORTH, lblLkm, 95, SpringLayout.NORTH, rightSidePanel);
		        rightSidePanel.add(lblLkm);

		        layout.putConstraint(SpringLayout.WEST, txtLkm, 150, SpringLayout.WEST, rightSidePanel);
		        layout.putConstraint(SpringLayout.NORTH, txtLkm, 95, SpringLayout.NORTH, rightSidePanel);
		        rightSidePanel.add(txtLkm);

		        layout.putConstraint(SpringLayout.WEST, lblAsiakasTunnus, 5, SpringLayout.WEST, rightSidePanel);
		        layout.putConstraint(SpringLayout.NORTH, lblAsiakasTunnus, 125, SpringLayout.NORTH, rightSidePanel);
		        rightSidePanel.add(lblAsiakasTunnus);

		        layout.putConstraint(SpringLayout.WEST, txtAsiakasTunnus, 150, SpringLayout.WEST, rightSidePanel);
		        layout.putConstraint(SpringLayout.NORTH, txtAsiakasTunnus, 125, SpringLayout.NORTH, rightSidePanel);
		        rightSidePanel.add(txtAsiakasTunnus);

		        layout.putConstraint(SpringLayout.WEST, lblToimipistetunnus, 5, SpringLayout.WEST, rightSidePanel);
		        layout.putConstraint(SpringLayout.NORTH, lblToimipistetunnus, 155, SpringLayout.NORTH, rightSidePanel);
		        rightSidePanel.add(lblToimipistetunnus);

		        layout.putConstraint(SpringLayout.WEST, txtToimipisteTunnus, 150, SpringLayout.WEST, rightSidePanel);
		        layout.putConstraint(SpringLayout.NORTH, txtToimipisteTunnus, 155, SpringLayout.NORTH, rightSidePanel);
                rightSidePanel.add(txtToimipisteTunnus);

		        layout.putConstraint(SpringLayout.WEST, lblVarattu_Alkupvm, 5, SpringLayout.WEST, rightSidePanel);
		        layout.putConstraint(SpringLayout.NORTH, lblVarattu_Alkupvm, 185, SpringLayout.NORTH, rightSidePanel);
		        rightSidePanel.add(lblVarattu_Alkupvm);

		        layout.putConstraint(SpringLayout.WEST, txtVarattu_Alkupvm, 150, SpringLayout.WEST, rightSidePanel);
                layout.putConstraint(SpringLayout.NORTH, txtVarattu_Alkupvm, 185, SpringLayout.NORTH, rightSidePanel);
                txtVarattu_Alkupvm.setPreferredSize( new Dimension( 100, 20 ) );
                rightSidePanel.add(txtVarattu_Alkupvm);
                try 
                {
                    MaskFormatter maskFormatter = new MaskFormatter("####-##-##"); //tekee päivämäärien tekstikentille valmiin formaatin josta käyttäjä ei voi poiketa
                    maskFormatter.install(txtVarattu_Alkupvm); 
                }
                catch (Exception e) 
                {
                    e.printStackTrace();
                }  

                layout.putConstraint(SpringLayout.WEST, lblVarattu_Loppupvm, 5, SpringLayout.WEST, rightSidePanel);
		        layout.putConstraint(SpringLayout.NORTH, lblVarattu_Loppupvm, 215, SpringLayout.NORTH, rightSidePanel);
		        rightSidePanel.add(lblVarattu_Loppupvm);

		        layout.putConstraint(SpringLayout.WEST, txtVarattu_Loppupvm, 150, SpringLayout.WEST, rightSidePanel);
                layout.putConstraint(SpringLayout.NORTH, txtVarattu_Loppupvm, 215, SpringLayout.NORTH, rightSidePanel);
                txtVarattu_Loppupvm.setPreferredSize( new Dimension( 100, 20 ) );
                rightSidePanel.add(txtVarattu_Loppupvm);
                try 
                {
                    MaskFormatter maskFormatter = new MaskFormatter("####-##-##"); //tekee päivämäärien tekstikentille valmiin formaatin josta käyttäjä ei voi poiketa
                    maskFormatter.install(txtVarattu_Loppupvm); 
                }
                catch (Exception e) 
                {
                    e.printStackTrace();
                }  

                layout.putConstraint(SpringLayout.WEST, btnHaeMokinVaraukset , 5, SpringLayout.WEST, rightSidePanel);
		        layout.putConstraint(SpringLayout.NORTH, btnHaeMokinVaraukset , 260, SpringLayout.NORTH, rightSidePanel);
		        rightSidePanel.add(btnHaeMokinVaraukset );

                layout.putConstraint(SpringLayout.WEST, btnUusiMokkiVaraus, 160, SpringLayout.WEST, rightSidePanel);
		        layout.putConstraint(SpringLayout.NORTH, btnUusiMokkiVaraus, 260, SpringLayout.NORTH, rightSidePanel);
		        rightSidePanel.add(btnUusiMokkiVaraus);

		        layout.putConstraint(SpringLayout.WEST, btnPoistaMokkiVaraus, 325, SpringLayout.WEST, rightSidePanel);
		        layout.putConstraint(SpringLayout.NORTH, btnPoistaMokkiVaraus, 260, SpringLayout.NORTH, rightSidePanel);
		        rightSidePanel.add(btnPoistaMokkiVaraus);

		        layout.putConstraint(SpringLayout.WEST, btnMuokkaaMokkivarausta, 475, SpringLayout.WEST, rightSidePanel);
		        layout.putConstraint(SpringLayout.NORTH, btnMuokkaaMokkivarausta, 260, SpringLayout.NORTH, rightSidePanel);
                rightSidePanel.add(btnMuokkaaMokkivarausta);
        
            }
            
        });
        btnHaeMokinVaraukset.addActionListener(
        new ActionListener () {
            public void actionPerformed(ActionEvent actEvent) 
            {
                HaeVaraus();
            }
            });

        btnUusiMokkiVaraus.addActionListener(
        new ActionListener () {
            public void actionPerformed(ActionEvent actEvent) 
                {
                    LisaaVaraus();
                }
            });
        btnPoistaMokkiVaraus.addActionListener(
        new ActionListener () {
            public void actionPerformed(ActionEvent actEvent) 
            {
                PoistaVaraus();
            }
        });
        btnMuokkaaMokkivarausta.addActionListener(
        new ActionListener () {
            public void actionPerformed(ActionEvent actEvent) 
            {
                MuokkaaVaraus();
            }
        });


        btnPalveluVaraukset.addActionListener(
            new ActionListener () 
            {
                public void actionPerformed(ActionEvent actEvent) 
                {	
                    rightSidePanel.repaint();    //Käytetään 3 alla olevaa riviä jotta saadaan tyhjennettyä rightSidePanel edellisistä sisällöistään
                    rightSidePanel.revalidate();
                    rightSidePanel.removeAll();
                    
                    layout.putConstraint(SpringLayout.WEST, lblOtsikko2, 350, SpringLayout.WEST, rightSidePanel);
                    layout.putConstraint(SpringLayout.NORTH, lblOtsikko2, 00, SpringLayout.NORTH, rightSidePanel);
                    rightSidePanel.add(lblOtsikko2);

                    layout.putConstraint(SpringLayout.WEST, lblVarauksenTunnus, 5, SpringLayout.WEST, rightSidePanel);
		            layout.putConstraint(SpringLayout.NORTH, lblVarauksenTunnus, 5, SpringLayout.NORTH, rightSidePanel);
                    rightSidePanel.add(lblVarauksenTunnus);	

                    layout.putConstraint(SpringLayout.WEST, txtVarauksenTunnus, 150, SpringLayout.WEST, rightSidePanel);
		            layout.putConstraint(SpringLayout.NORTH, txtVarauksenTunnus, 5, SpringLayout.NORTH, rightSidePanel);	
                    rightSidePanel.add(txtVarauksenTunnus);

                    layout.putConstraint(SpringLayout.WEST, lblPalvelunTunnus, 5, SpringLayout.WEST, rightSidePanel);
		            layout.putConstraint(SpringLayout.NORTH, lblPalvelunTunnus, 35, SpringLayout.NORTH, rightSidePanel);	
		            rightSidePanel.add(lblPalvelunTunnus);

		            layout.putConstraint(SpringLayout.WEST, txtPalvelunTunnus, 150, SpringLayout.WEST, rightSidePanel);
		            layout.putConstraint(SpringLayout.NORTH, txtPalvelunTunnus, 35, SpringLayout.NORTH, rightSidePanel);
		            rightSidePanel.add(txtPalvelunTunnus);

		            layout.putConstraint(SpringLayout.WEST, lblLkm, 5, SpringLayout.WEST, rightSidePanel);
		            layout.putConstraint(SpringLayout.NORTH, lblLkm, 95, SpringLayout.NORTH, rightSidePanel);
		            rightSidePanel.add(lblLkm);

		            layout.putConstraint(SpringLayout.WEST, txtLkm, 150, SpringLayout.WEST, rightSidePanel);
		            layout.putConstraint(SpringLayout.NORTH, txtLkm, 95, SpringLayout.NORTH, rightSidePanel);
		            rightSidePanel.add(txtLkm);

		            layout.putConstraint(SpringLayout.WEST, lblAsiakasTunnus, 5, SpringLayout.WEST, rightSidePanel);
		            layout.putConstraint(SpringLayout.NORTH, lblAsiakasTunnus, 125, SpringLayout.NORTH, rightSidePanel);
		            rightSidePanel.add(lblAsiakasTunnus);

		            layout.putConstraint(SpringLayout.WEST, txtAsiakasTunnus, 150, SpringLayout.WEST, rightSidePanel);
		            layout.putConstraint(SpringLayout.NORTH, txtAsiakasTunnus, 125, SpringLayout.NORTH, rightSidePanel);
		            rightSidePanel.add(txtAsiakasTunnus);

		            layout.putConstraint(SpringLayout.WEST, lblToimipistetunnus, 5, SpringLayout.WEST, rightSidePanel);
		            layout.putConstraint(SpringLayout.NORTH, lblToimipistetunnus, 155, SpringLayout.NORTH, rightSidePanel);
		            rightSidePanel.add(lblToimipistetunnus);

		            layout.putConstraint(SpringLayout.WEST, txtToimipisteTunnus, 150, SpringLayout.WEST, rightSidePanel);
		            layout.putConstraint(SpringLayout.NORTH, txtToimipisteTunnus, 155, SpringLayout.NORTH, rightSidePanel);
                    rightSidePanel.add(txtToimipisteTunnus);

		            layout.putConstraint(SpringLayout.WEST, lblVarattu_Alkupvm, 5, SpringLayout.WEST, rightSidePanel);
		            layout.putConstraint(SpringLayout.NORTH, lblVarattu_Alkupvm, 185, SpringLayout.NORTH, rightSidePanel);
                    rightSidePanel.add(lblVarattu_Alkupvm);
                    
		            layout.putConstraint(SpringLayout.WEST, txtVarattu_Alkupvm, 150, SpringLayout.WEST, rightSidePanel);
                    layout.putConstraint(SpringLayout.NORTH, txtVarattu_Alkupvm, 185, SpringLayout.NORTH, rightSidePanel);
                    txtVarattu_Alkupvm.setPreferredSize( new Dimension( 100, 20 ) );
                    rightSidePanel.add(txtVarattu_Alkupvm);
                    try 
                    {
                        MaskFormatter maskFormatter = new MaskFormatter("####-##-##"); //tekee päivämäärien tekstikentille valmiin formaatin josta käyttäjä ei voi poiketa
                        maskFormatter.install(txtVarattu_Alkupvm); 
                    }
                    catch (Exception e) 
                    {
                        e.printStackTrace();
                    }  

                    layout.putConstraint(SpringLayout.WEST, lblVarattu_Loppupvm, 5, SpringLayout.WEST, rightSidePanel);
                    layout.putConstraint(SpringLayout.NORTH, lblVarattu_Loppupvm, 215, SpringLayout.NORTH, rightSidePanel);
                    rightSidePanel.add(lblVarattu_Loppupvm);
                    
                   
		            layout.putConstraint(SpringLayout.WEST, txtVarattu_Loppupvm, 150, SpringLayout.WEST, rightSidePanel);
                    layout.putConstraint(SpringLayout.NORTH, txtVarattu_Loppupvm, 215, SpringLayout.NORTH, rightSidePanel);
                    txtVarattu_Loppupvm.setPreferredSize( new Dimension( 100, 20 ) );   //jos käyttää JFormattedTextfieldiä niin ei jostain syystä voi laittaa tekstiboxin kokoa samallalailla kuin JtextFieldissä joten tämä laittaa tekstiboxin koon
                    rightSidePanel.add(txtVarattu_Loppupvm);  
                    try 
                    {
                        MaskFormatter maskFormatter = new MaskFormatter("####-##-##"); //tekee päivämäärien tekstikentille valmiin formaatin josta käyttäjä ei voi poiketa
                        maskFormatter.install(txtVarattu_Loppupvm);
                    }
                    catch (Exception e) 
                    {
                        e.printStackTrace();
                    }                    

                    layout.putConstraint(SpringLayout.WEST, btnHaePalveluVaraukset  , 5, SpringLayout.WEST, rightSidePanel);
		            layout.putConstraint(SpringLayout.NORTH, btnHaePalveluVaraukset  , 260, SpringLayout.NORTH, rightSidePanel);
                    rightSidePanel.add(btnHaePalveluVaraukset  );
                    
                    layout.putConstraint(SpringLayout.WEST, btnUusiPalveluVaraus, 160, SpringLayout.WEST, rightSidePanel);
		            layout.putConstraint(SpringLayout.NORTH, btnUusiPalveluVaraus, 260, SpringLayout.NORTH, rightSidePanel);
		            rightSidePanel.add(btnUusiPalveluVaraus);
	
		            layout.putConstraint(SpringLayout.WEST, btnPoistaPalveluVaraus, 325, SpringLayout.WEST, rightSidePanel);
		            layout.putConstraint(SpringLayout.NORTH, btnPoistaPalveluVaraus, 260, SpringLayout.NORTH, rightSidePanel);
		            rightSidePanel.add(btnPoistaPalveluVaraus);
	
		            layout.putConstraint(SpringLayout.WEST, btnMuokkaaPalveluVarausta, 475, SpringLayout.WEST, rightSidePanel);
		            layout.putConstraint(SpringLayout.NORTH, btnMuokkaaPalveluVarausta, 260, SpringLayout.NORTH, rightSidePanel);
                    rightSidePanel.add(btnMuokkaaPalveluVarausta);

                    //TO DO
                   
                }
                
            });
        btnHaePalveluVaraukset.addActionListener(
        new ActionListener () {
            public void actionPerformed(ActionEvent actEvent) 
            {
                HaePalveluVaraus();
            }
            });

        btnUusiPalveluVaraus.addActionListener(
        new ActionListener () {
            public void actionPerformed(ActionEvent actEvent) 
                {
                    LisaaPalveluVaraus();
                }
            });
        btnPoistaPalveluVaraus.addActionListener(
        new ActionListener () {
            public void actionPerformed(ActionEvent actEvent) 
            {
                PoistaPalveluVaraus();
            }
        });
        btnMuokkaaPalveluVarausta.addActionListener(
        new ActionListener () {
            public void actionPerformed(ActionEvent actEvent) 
            {
                MuokkaaPalveluVaraus();
            }
        });


    }
    public void LisaaPalveluVaraus()
    {
        try 
        {
            
            connect();            
            java.sql.Date sqlPvmAlku = java.sql.Date.valueOf(txtVarattu_Alkupvm.getText());   
            java.sql.Date sqlPvmLoppu = java.sql.Date.valueOf(txtVarattu_Loppupvm.getText()); 
            PalveluVaraus PalveluVaraus = new PalveluVaraus();
            PalveluVaraus.setVarausTun(Integer.parseInt(txtVarauksenTunnus.getText()));
            PalveluVaraus.setPalveluTun(Integer.parseInt(txtPalvelunTunnus.getText()));
            PalveluVaraus.setAsiakasTun(Integer.parseInt(txtAsiakasTunnus.getText()));
            PalveluVaraus.setToimipisteTun(Integer.parseInt(txtToimipisteTunnus.getText()));
            PalveluVaraus.setAlkuPvm(sqlPvmAlku);
            PalveluVaraus.setLoppuPvm(sqlPvmLoppu);
            PalveluVaraus.LisaaVaraus(PalveluVaraus, m_conn);

        } 
        catch (SQLException se) 
        {
            se.printStackTrace();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }   
    }
    public void MuokkaaPalveluVaraus()
    {
        try 
        {
            connect();
            
            java.sql.Date sqlPvmAlku = java.sql.Date.valueOf(txtVarattu_Alkupvm.getText());   
            java.sql.Date sqlPvmLoppu = java.sql.Date.valueOf(txtVarattu_Loppupvm.getText()); 
            PalveluVaraus PalveluVaraus = new PalveluVaraus();
            PalveluVaraus.setVarausTun(Integer.parseInt(txtVarauksenTunnus.getText()));
            PalveluVaraus.setPalveluTun(Integer.parseInt(txtPalvelunTunnus.getText()));
            PalveluVaraus.setAsiakasTun(Integer.parseInt(txtAsiakasTunnus.getText()));
            PalveluVaraus.setToimipisteTun(Integer.parseInt(txtToimipisteTunnus.getText()));
            PalveluVaraus.setAlkuPvm(sqlPvmAlku);
            PalveluVaraus.setLoppuPvm(sqlPvmLoppu);
            PalveluVaraus.MuokkaaVaraus(m_conn, PalveluVaraus);
        } 
        catch (SQLException se) 
        {
            se.printStackTrace();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }   
    }
    public  void HaePalveluVaraus()  {
		
        try 
        {
            connect();
            PalveluVaraus PalveluVaraus = new PalveluVaraus();
			PalveluVaraus.haeVaraus (m_conn, Integer.parseInt(txtVarauksenTunnus.getText()));
        } 
        catch (SQLException se) 
        {
            se.printStackTrace();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
		}

    }
    public void PoistaPalveluVaraus()
    {
        try
        {
            connect();

            PalveluVaraus PalveluVaraus = new PalveluVaraus();
            PalveluVaraus.setVarausTun(Integer.parseInt(txtVarauksenTunnus.getText()));
            PalveluVaraus.PoistaVaraus(m_conn, PalveluVaraus);
        }
        catch (SQLException se) 
        {
            se.printStackTrace();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
		}
    }
    public void LisaaVaraus()
    {
        try 
        {
            connect();
            java.sql.Date sqlPvmAlku = java.sql.Date.valueOf(txtVarattu_Alkupvm.getText());   
            java.sql.Date sqlPvmLoppu = java.sql.Date.valueOf(txtVarattu_Loppupvm.getText()); 
            MokkiVaraus MokkiVaraus = new MokkiVaraus();
            MokkiVaraus.setVarausTun(Integer.parseInt(txtVarauksenTunnus.getText()));
            MokkiVaraus.setMokkiTun(Integer.parseInt(txtMokinTunnus.getText()));
            MokkiVaraus.setAsiakasTun(Integer.parseInt(txtAsiakasTunnus.getText()));
            MokkiVaraus.setToimipisteTun(Integer.parseInt(txtToimipisteTunnus.getText()));
            MokkiVaraus.setAlkuPvm(sqlPvmAlku);
            MokkiVaraus.setLoppuPvm(sqlPvmLoppu);
            MokkiVaraus.LisaaVaraus(MokkiVaraus, m_conn);

        } 
        catch (SQLException se) 
        {
            se.printStackTrace();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }   
    }
    public void MuokkaaVaraus()
    {
        try 
        {
            connect();
            
            java.sql.Date sqlPvmAlku = java.sql.Date.valueOf(txtVarattu_Alkupvm.getText());   
            java.sql.Date sqlPvmLoppu = java.sql.Date.valueOf(txtVarattu_Loppupvm.getText()); 
            MokkiVaraus MokkiVaraus = new MokkiVaraus();
            MokkiVaraus.setVarausTun(Integer.parseInt(txtVarauksenTunnus.getText()));
            MokkiVaraus.setMokkiTun(Integer.parseInt(txtMokinTunnus.getText()));
            MokkiVaraus.setAsiakasTun(Integer.parseInt(txtAsiakasTunnus.getText()));
            MokkiVaraus.setToimipisteTun(Integer.parseInt(txtToimipisteTunnus.getText()));
            MokkiVaraus.setAlkuPvm(sqlPvmAlku);
            MokkiVaraus.setLoppuPvm(sqlPvmLoppu);
            MokkiVaraus.MuokkaaVaraus(m_conn, MokkiVaraus);
        } 
        catch (SQLException se) 
        {
            se.printStackTrace();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }   
    }

    public  void HaeVaraus()  {
		
        try 
        {
            connect();
            MokkiVaraus MokkiVaraus = new MokkiVaraus();
			MokkiVaraus.haeVaraus (m_conn, Integer.parseInt(txtVarauksenTunnus.getText()));
        } 
        catch (SQLException se) 
        {
            se.printStackTrace();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
		}

    }
    public void PoistaVaraus()
    {
        try
        {
            connect();

            MokkiVaraus MokkiVaraus = new MokkiVaraus();
            MokkiVaraus.setVarausTun(Integer.parseInt(txtVarauksenTunnus.getText()));
            MokkiVaraus.PoistaVaraus(m_conn, MokkiVaraus);
        }
        catch (SQLException se) 
        {
            se.printStackTrace();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
		}
    }

    public void connect() throws SQLException, Exception
    {
        m_conn = null;
        try
        {
            m_conn = DriverManager.getConnection ("jdbc:mysql://localhost:3306/vp", "root", "123");
        }
        catch (SQLException e)
        {
            m_conn = null;
            throw e;
        }
        catch (Exception es)
        {
            throw es;
        }
    }


    public static void main (String [] args)
    {
        
    }
}
