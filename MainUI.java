

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainUI
{

    private JButton btnToimipisteidenHallinta;
    private JButton btnMajoitustenHallinta;  //!!HUOM!! TÄMÄ ON OIKEASTI VARAUSTEN HALLINTA VAIHDETAAN TIEDOSTONIMI JOSKUS JOS JOKU JAKSAA, SAATTAA RIKKOA KAIKEN
    private JButton btnAsiakasHallinta;
    private JButton btnLaskujenHallinta;
    private JButton btnMokkienHallinta;
    private JButton btnPalvelujenHallinta;
    private JButton btnRaportointi;
    private JButton btnRaportointi2;

    public JPanel rightSidePanel = new JPanel();
    JFrame mainFrame = new JFrame();

    public void createBaseGUI()
    {
        
        JPanel leftSidePanel = new JPanel(); 
         
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSidePanel, rightSidePanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);
        //sidePanel.setLocationRelativeTo(null);
        mainFrame.setContentPane(splitPane);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(1080,800); //for now use this instead of line below
        //mainFrame.setExtendedState(mainFrame.MAXIMIZED_BOTH); badly maximizes window 
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
        Dimension minimumSize = new Dimension(100, 50); //Provide minimum sizes for the split panes sides
        leftSidePanel.setMinimumSize(minimumSize);
		SpringLayout layout = new SpringLayout();
        leftSidePanel.setLayout(layout);
        btnToimipisteidenHallinta = new JButton("Toimipisteidenhallinta");
        btnMajoitustenHallinta = new JButton("Varaustenhallinta");  //!!HUOM!! TÄMÄ ON OIKEASTI VARAUSTEN HALLINTA VAIHDETAAN TIEDOSTONIMI JOSKUS JOS JOKU JAKSAA, SAATTAA RIKKOA KAIKEN
        btnAsiakasHallinta = new JButton("Asiakashallinta");
        btnLaskujenHallinta = new JButton("Laskujenhallinta");
        btnMokkienHallinta = new JButton("Mokkienhallinta");
        btnPalvelujenHallinta = new JButton("Palvelujenhallinta");
        btnRaportointi = new JButton("Mokkien raportointi");
        btnRaportointi2 = new JButton("Palveluiden raportointi");

		layout.putConstraint(SpringLayout.WEST, btnToimipisteidenHallinta, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnToimipisteidenHallinta, 20, SpringLayout.NORTH, rightSidePanel);
        leftSidePanel.add(btnToimipisteidenHallinta);
        btnToimipisteidenHallinta.setPreferredSize(new Dimension(170, 27));

        layout.putConstraint(SpringLayout.WEST, btnMajoitustenHallinta, 20, SpringLayout.WEST, rightSidePanel); //!!HUOM!! TÄMÄ ON OIKEASTI VARAUSTEN HALLINTA VAIHDETAAN TIEDOSTONIMI JOSKUS JOS JOKU JAKSAA, SAATTAA RIKKOA KAIKEN
		layout.putConstraint(SpringLayout.NORTH, btnMajoitustenHallinta, 50, SpringLayout.NORTH, rightSidePanel); //!!HUOM!! TÄMÄ ON OIKEASTI VARAUSTEN HALLINTA VAIHDETAAN TIEDOSTONIMI JOSKUS JOS JOKU JAKSAA, SAATTAA RIKKOA KAIKEN
        leftSidePanel.add(btnMajoitustenHallinta); //!!HUOM!! TÄMÄ ON OIKEASTI VARAUSTEN HALLINTA VAIHDETAAN TIEDOSTONIMI JOSKUS JOS JOKU JAKSAA, SAATTAA RIKKOA KAIKEN
        btnMajoitustenHallinta.setPreferredSize(new Dimension(170, 27));

        layout.putConstraint(SpringLayout.WEST, btnMokkienHallinta, 20, SpringLayout.WEST, rightSidePanel);
		layout.putConstraint(SpringLayout.NORTH, btnMokkienHallinta, 80, SpringLayout.NORTH, rightSidePanel);
        leftSidePanel.add(btnMokkienHallinta);
        btnMokkienHallinta.setPreferredSize(new Dimension(170, 27));

        layout.putConstraint(SpringLayout.WEST, btnPalvelujenHallinta, 20, SpringLayout.WEST, rightSidePanel);
        layout.putConstraint(SpringLayout.NORTH, btnPalvelujenHallinta, 110, SpringLayout.NORTH, rightSidePanel);
        leftSidePanel.add(btnPalvelujenHallinta);
        btnPalvelujenHallinta.setPreferredSize(new Dimension(170, 27));

        layout.putConstraint(SpringLayout.WEST, btnAsiakasHallinta, 20, SpringLayout.WEST, rightSidePanel);
        layout.putConstraint(SpringLayout.NORTH, btnAsiakasHallinta, 140, SpringLayout.NORTH, rightSidePanel);
        leftSidePanel.add(btnAsiakasHallinta);  //TO DO
        btnAsiakasHallinta.setPreferredSize(new Dimension(170, 27));
        
        layout.putConstraint(SpringLayout.WEST, btnLaskujenHallinta, 20, SpringLayout.WEST, rightSidePanel);
        layout.putConstraint(SpringLayout.NORTH, btnLaskujenHallinta, 170, SpringLayout.NORTH, rightSidePanel);
        leftSidePanel.add(btnLaskujenHallinta); //TO DO
        btnLaskujenHallinta.setPreferredSize(new Dimension(170, 27));

        layout.putConstraint(SpringLayout.WEST, btnRaportointi, 20, SpringLayout.WEST, rightSidePanel);
        layout.putConstraint(SpringLayout.NORTH, btnRaportointi, 200, SpringLayout.NORTH, rightSidePanel);
        leftSidePanel.add(btnRaportointi);
        btnRaportointi.setPreferredSize(new Dimension(170, 27));

        layout.putConstraint(SpringLayout.WEST, btnRaportointi2, 20, SpringLayout.WEST, rightSidePanel);
        layout.putConstraint(SpringLayout.NORTH, btnRaportointi2, 230, SpringLayout.NORTH, rightSidePanel);
        leftSidePanel.add(btnRaportointi2); //TO DO
        btnRaportointi2.setPreferredSize(new Dimension(170, 27));
        
        btnToimipisteidenHallinta.addActionListener(
        new ActionListener () 
        {
            public void actionPerformed(ActionEvent actEvent) 
            {	
                rightSidePanel.repaint();    //Käytetään 3 alla olevaa riviä jotta saadaan tyhjennettyä rightSidePanel edellisistä sisällöistään
                rightSidePanel.revalidate();
                rightSidePanel.removeAll();
                ToimipisteidenHallinta toimHallinta = new ToimipisteidenHallinta(); //uusi olio toimipisteidenhallintasta jotta voidaan kutsua modifyRightPanel() funktio
                toimHallinta.modifyRightPanel(rightSidePanel); //oikean puolen paneeli annetaan funktiolle parametrina jotta se muokkaa sen oikein
                    
            }
        });
        btnMajoitustenHallinta.addActionListener( //!!HUOM!! TÄMÄ ON OIKEASTI VARAUSTEN HALLINTA VAIHDETAAN TIEDOSTONIMI JOSKUS JOS JOKU JAKSAA, SAATTAA RIKKOA KAIKEN
        new ActionListener () 
        {
            public void actionPerformed(ActionEvent actEvent) 
            {	
                rightSidePanel.repaint();       //Käytetään 3 alla olevaa riviä jotta saadaan tyhjennettyä rightSidePanel edellisistä sisällöistään
                rightSidePanel.revalidate();
                rightSidePanel.removeAll();
                VaraustenHallintaGUI varHallinta = new VaraustenHallintaGUI(); //uusi olio toimipisteidenhallintasta jotta voidaan kutsua modifyRightPanel() funktio
                varHallinta.modifyRightPanel(rightSidePanel); //oikean puolen paneeli annetaan funktiolle parametrina jotta se muokkaa sen oikein
            }
        });
        btnMokkienHallinta.addActionListener(
        new ActionListener () 
        {
            public void actionPerformed(ActionEvent actEvent) 
            {	
                rightSidePanel.repaint();       //Käytetään 3 alla olevaa riviä jotta saadaan tyhjennettyä rightSidePanel edellisistä sisällöistään
                rightSidePanel.revalidate();
                rightSidePanel.removeAll();
                MokkiGUI mokkigui = new MokkiGUI(); //uusi olio toimipisteidenhallintasta jotta voidaan kutsua modifyRightPanel() funktio
                mokkigui.modifyRightPanel(rightSidePanel); //oikean puolen paneeli annetaan funktiolle parametrina jotta se muokkaa sen oikein
            }
        });
        btnPalvelujenHallinta.addActionListener(
        new ActionListener () 
        {
            public void actionPerformed(ActionEvent actEvent) 
            {	
                rightSidePanel.repaint();       //Käytetään 3 alla olevaa riviä jotta saadaan tyhjennettyä rightSidePanel edellisistä sisällöistään
                rightSidePanel.revalidate();
                rightSidePanel.removeAll();
                PalveluGUI palvelugui = new PalveluGUI(); //uusi olio toimipisteidenhallintasta jotta voidaan kutsua modifyRightPanel() funktio
                palvelugui.modifyRightPanel(rightSidePanel); //oikean puolen paneeli annetaan funktiolle parametrina jotta se muokkaa sen oikein
            }
        });
        btnAsiakasHallinta.addActionListener(
            new ActionListener () 
            {
                public void actionPerformed(ActionEvent actEvent) 
                {	
                    rightSidePanel.repaint();    //Käytetään 3 alla olevaa riviä jotta saadaan tyhjennettyä rightSidePanel edellisistä sisällöistään
                    rightSidePanel.revalidate();
                    rightSidePanel.removeAll();
                    AsiakashallintaGUI asHallinta = new AsiakashallintaGUI(); //uusi olio toimipisteidenhallintasta jotta voidaan kutsua modifyRightPanel() funktio
                    asHallinta.modifyRightPanel(rightSidePanel); //oikean puolen paneeli annetaan funktiolle parametrina jotta se muokkaa sen oikein
    
            }
        });
        btnRaportointi.addActionListener(
            new ActionListener () 
            {
                public void actionPerformed(ActionEvent actEvent) 
                {	
                    rightSidePanel.repaint();    //Käytetään 3 alla olevaa riviä jotta saadaan tyhjennettyä rightSidePanel edellisistä sisällöistään
                    rightSidePanel.revalidate();
                    rightSidePanel.removeAll();
                    hallintaGUI hallintaGUI = new hallintaGUI(); //uusi olio toimipisteidenhallintasta jotta voidaan kutsua modifyRightPanel() funktio
                    hallintaGUI.modifyRightPanel(rightSidePanel); //oikean puolen paneeli annetaan funktiolle parametrina jotta se muokkaa sen oikein
    
            }
        });
        btnRaportointi2.addActionListener(
            new ActionListener () 
            {
                public void actionPerformed(ActionEvent actEvent) 
                {	
                    rightSidePanel.repaint();    //Käytetään 3 alla olevaa riviä jotta saadaan tyhjennettyä rightSidePanel edellisistä sisällöistään
                    rightSidePanel.revalidate();
                    rightSidePanel.removeAll();
                    palveluRaporttiGUI palveluRaporttiGUI = new palveluRaporttiGUI(); //uusi olio toimipisteidenhallintasta jotta voidaan kutsua modifyRightPanel() funktio
                    palveluRaporttiGUI.modifyRightPanel(rightSidePanel); //oikean puolen paneeli annetaan funktiolle parametrina jotta se muokkaa sen oikein
    
            }
        });
                btnLaskujenHallinta.addActionListener(
        new ActionListener () 
        {
            public void actionPerformed(ActionEvent actEvent) 
            {	
                rightSidePanel.repaint();    //Käytetään 3 alla olevaa riviä jotta saadaan tyhjennettyä rightSidePanel edellisistä sisällöistään
                rightSidePanel.revalidate();
                rightSidePanel.removeAll();
                LaskuGUI toimHallinta = new LaskuGUI(); //uusi olio toimipisteidenhallintasta jotta voidaan kutsua modifyRightPanel() funktio
                toimHallinta.modifyRightPanel(rightSidePanel); //oikean puolen paneeli annetaan funktiolle parametrina jotta se muokkaa sen oikein
                    
            }
        });
    }
    public void reSize()
    {
        mainFrame.pack();
        System.out.println("kakkaa");
    }

    public static void main (String [] args)
    {
        MainUI GUI = new MainUI();
        GUI.createBaseGUI();
    }
}
