

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.sql.*;

public class ToimipisteidenHallinta extends MainUI
{
    JButton btnUusiToimipiste;
    JButton btnMuokkaaToimipistetta;
    JButton btnPoistaToimipiste; 

    public void modifyRightPanel(JPanel rightSidePanel) //oikean puolen paneeli annetaan funktiolle parametrina jotta se muokkaa sen oikein
    {                                                   

        Dimension minimumSize = new Dimension(100, 50);  //Provide minimum sizes for the split panes sides
        rightSidePanel.setMinimumSize(minimumSize); 
        rightSidePanel.setLayout((new BoxLayout(rightSidePanel, BoxLayout.PAGE_AXIS)));
        rightSidePanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        ToimipisteGUI toimipisteGUI = new ToimipisteGUI();
        toimipisteGUI.modifyRightPanel(rightSidePanel);

        btnUusiToimipiste.addActionListener(
        new ActionListener () 
        {
            public void actionPerformed(ActionEvent actEvent) 
            {	
                rightSidePanel.repaint();    //Käytetään 3 alla olevaa riviä jotta saadaan tyhjennettyä rightSidePanel edellisistä sisällöistään
                rightSidePanel.revalidate();
                rightSidePanel.removeAll();
            }
        });
    }


    


    public static void main (String [] args)
    {

    }
}