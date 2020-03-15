import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.format.*;
import java.text.DateFormat;   

public class PalveluVaraus extends MainUI  {

    private int varausTun;
    private int palveluTun;
    private int asiakasTun;
    private int toimipisteTun;
    private int maxID;
    private int daysBetween;

    private Date Days;
    private Date alkuPvm;
    private Date loppuPvm;
    private Date varattuPvm;

    public Connection connection;

    public PalveluVaraus() 
    {
     
    }
    public int getVarausTun() {
        return varausTun;
    }

    public void setVarausTun(int varausTun) 
    {
        this.varausTun = varausTun;
    }

    public int getPalveluTun() 
    {
        return palveluTun;
    }

    public void setPalveluTun(int palveluTun) 
    {
        this.palveluTun = palveluTun;
    }

    public int getAsiakasTun() 
    {
        return asiakasTun;
    }

    public void setAsiakasTun(int asiakasTun) {
        this.asiakasTun = asiakasTun;
    }

    public int getToimipisteTun() {
        return toimipisteTun;
    }

    public void setToimipisteTun(int toimipisteTun) 
    {
        this.toimipisteTun = toimipisteTun;
    }
    public void setVarattuPvm(Date varattuPvm)
    {

    }
    public Date getVarattuPvm()
    {
        return varattuPvm;
    }
    public Date getAlkuPvm() {
        return alkuPvm;
    }

    public void setAlkuPvm(Date alkuPvm) {
        this.alkuPvm = alkuPvm;
    }

    public Date getLoppuPvm() {
        return loppuPvm;
    }

    public void setLoppuPvm(Date loppuPvm) {
        this.loppuPvm = loppuPvm;
    }

    public String toString()
    {
        return (varausTun + " " + palveluTun + " " + asiakasTun + " "+ toimipisteTun + " " + alkuPvm + " " + loppuPvm );  //ToString lause
    }
    public void LisaaVaraus(PalveluVaraus PalveluVaraus, Connection connection) throws SQLException, Exception 
    {
        String dateDiffSql = "SELECT DATEDIFF(?, ?) AS DateDiff; ";
        String sql = "INSERT INTO varaus (varaus_id, asiakas_id, toimipiste_id, varattu_pvm, varattu_alkupvm, varattu_loppupvm) "
                        + "VALUES (?, ?, ?, ?, ?, ?);";
        String sql2 = "INSERT INTO varauksen_palvelut (varaus_id, palvelu_id, lkm) "
                        + "VALUES (?, ?, ?);";

        ResultSet dateDiffJoukko = null;
        ResultSet tulosjoukko = null;
        ResultSet tulosjoukko2 = null;

        PreparedStatement dateDiffLause = connection.prepareStatement(dateDiffSql);
        PreparedStatement lause = connection.prepareStatement(sql);
        PreparedStatement lause2 = connection.prepareStatement(sql2);

        try 
        {
            lause.setInt(1, PalveluVaraus.getVarausTun());
            lause.setInt(2, PalveluVaraus.getAsiakasTun());
            lause.setInt(3, PalveluVaraus.getToimipisteTun());

            LocalDate localDate = LocalDate.now();
            Date date1 = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()); //ottaa paikallisen ajan ja muuttaa sen sql ajaksi ja laittaa sen mokkivaraus oliolle
            PalveluVaraus.setVarattuPvm(date1);
            java.util.Date utilDateLocal = date1;
            java.sql.Date sqlDateLocal = new java.sql.Date(utilDateLocal.getTime());
            lause.setDate(4, sqlDateLocal);

            java.util.Date utilDateAlku = PalveluVaraus.getAlkuPvm();  //muuttaa java.util.date formaatin java.sql.date formaatiksi
            java.sql.Date sqlDateAlku = new java.sql.Date(utilDateAlku.getTime());  //muuttaa java.util.date formaatin java.sql.date formaatiksi
            lause.setDate(5, sqlDateAlku);

            java.util.Date utilDateLoppu = PalveluVaraus.getLoppuPvm();
            java.sql.Date sqlDateLoppu = new java.sql.Date(utilDateLoppu.getTime());
            lause.setDate(6, sqlDateLoppu);

            //tästä alaspäin lasketaan varauksen_mokit tauluun "lkm" kohta (varauksen pituus päivinä) 
            tulosjoukko = lause.executeQuery();
            dateDiffLause.setDate(1, sqlDateLoppu);
            dateDiffLause.setDate(2, sqlDateAlku);
            dateDiffJoukko = dateDiffLause.executeQuery();
            if (dateDiffJoukko.next() == true)
            {
                daysBetween = dateDiffJoukko.getInt("DateDiff");
                System.out.println(daysBetween);
            }

            //tästä alaspäin asetetaan varauksen_mokit tauluun tiedot
            lause2.setInt(1, PalveluVaraus.getVarausTun());
            lause2.setInt(2, PalveluVaraus.getPalveluTun());
            lause2.setInt(3, daysBetween);
            tulosjoukko2 = lause2.executeQuery();

            JOptionPane.showMessageDialog(null, "palvelun tiedot lisatty tietokantaan.");
		} 
        catch (SQLException se) 
        {
            JOptionPane.showMessageDialog(null, "Tapahtui tietokantavirhe varausta lisattaessa.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
            se.printStackTrace();
        } 
        catch (Exception e)      
        {
            JOptionPane.showMessageDialog(null, "Tapahtui virhe varausta lisattaessa.", "virhe", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    public void MuokkaaVaraus(Connection connection, PalveluVaraus PalveluVaraus)  throws SQLException, Exception 
    {
        String sql = "UPDATE varaus "
                        + "SET asiakas_id = ?, toimipiste_id = ?, varattu_alkupvm = ?, varattu_loppupvm = ? "
                        + " WHERE varaus_id = ?; ";
        String sql2 = "UPDATE varauksen_palvelut "
                        + "SET palvelu_id = ? "
                        + " WHERE varaus_id = ?; ";
        ResultSet tulosjoukko = null;
        ResultSet tulosjoukko2 = null;
        PreparedStatement lause = connection.prepareStatement(sql);
        PreparedStatement lause2 = connection.prepareStatement(sql2);
        try 
        {    
            lause.setInt(1, PalveluVaraus.getAsiakasTun());
            lause.setInt(2, PalveluVaraus.getToimipisteTun());
                
            java.util.Date utilDateAlku = PalveluVaraus.getAlkuPvm();  //muuttaa java.util.date formaatin java.sql.date formaatiksi
            java.sql.Date sqlDateAlku = new java.sql.Date(utilDateAlku.getTime());  //muuttaa java.util.date formaatin java.sql.date formaatiksi
            lause.setDate(3, sqlDateAlku);
                
            java.util.Date utilDateLoppu = PalveluVaraus.getLoppuPvm();
            java.sql.Date sqlDateLoppu = new java.sql.Date(utilDateLoppu.getTime());
            lause.setDate(4, sqlDateLoppu);
                
            lause.setInt(5, PalveluVaraus.getVarausTun());
                
            lause2.setInt(1, PalveluVaraus.getPalveluTun());
            lause2.setInt(2, PalveluVaraus.getVarausTun());
            tulosjoukko = lause.executeQuery();
            tulosjoukko2 = lause2.executeQuery();
            JOptionPane.showMessageDialog(null, "Varaus muokattu onnistuneesti");
         } 
         catch (SQLException se) 
         {
            JOptionPane.showMessageDialog(null, "Tapahtui tietokantavirhe varausta muokattaessa.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
             se.printStackTrace();
         } 
         catch (Exception e)      
         {
            JOptionPane.showMessageDialog(null, "Tapahtui virhe varausta muokattaessa.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
             e.printStackTrace();
         }

    }
    public static PalveluVaraus haeVaraus(Connection connection, int id1) throws SQLException, Exception 
    {
        PalveluVaraus PalveluVaraus = new PalveluVaraus ();
        
        String sql = "SELECT varaus.varaus_id, varaus.asiakas_id, varaus.toimipiste_id, varaus.varattu_pvm, varaus.varattu_alkupvm, varaus.varattu_loppupvm, varauksen_palvelut.palvelu_id "
                        + "FROM varaus "
                        + "INNER JOIN varauksen_palvelut ON varaus.varaus_id = varauksen_palvelut.varaus_id "
                        + "WHERE varaus.varaus_id = ? ";
		ResultSet tulosjoukko = null;
        PreparedStatement lause = connection.prepareStatement(sql);
        
        try 
        {
            lause.setInt(1, id1);
            tulosjoukko = lause.executeQuery();

            if (tulosjoukko == null) 
            {
				throw new Exception("palvelua ei loydy");
            }

            if (tulosjoukko.next () == true)
            {
                PalveluVaraus.setVarausTun(tulosjoukko.getInt("varaus_id"));
                PalveluVaraus.setAsiakasTun (tulosjoukko.getInt("asiakas_id"));
                PalveluVaraus.setToimipisteTun (tulosjoukko.getInt("toimipiste_id"));
                PalveluVaraus.setPalveluTun(tulosjoukko.getInt("palvelu_id"));
    
                SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                Date parsed = format.parse(tulosjoukko.getString("varattu_alkupvm"));
                java.sql.Date PvmAlku = new java.sql.Date(parsed.getTime());
                PalveluVaraus.setAlkuPvm(PvmAlku);
    
                SimpleDateFormat formatLoppu = new SimpleDateFormat("yyyy-mm-dd");
                Date parsedLoppu = formatLoppu.parse(tulosjoukko.getString("varattu_loppupvm"));
                java.sql.Date PvmLoppu = new java.sql.Date(parsedLoppu.getTime());
                PalveluVaraus.setLoppuPvm(PvmLoppu);
                
                System.out.println(PalveluVaraus.toString());
    
                //laittaa tekstikenttien sisällön niille tiedoille mitä haetaan
                VaraustenHallintaGUI GUI = new VaraustenHallintaGUI();
                GUI.txtVarauksenTunnus.setText(String.valueOf(PalveluVaraus.getVarausTun()));
                GUI.txtPalvelunTunnus.setText(Integer.toString(PalveluVaraus.getPalveluTun()));
                GUI.txtAsiakasTunnus.setText(Integer.toString(PalveluVaraus.getAsiakasTun()));
                GUI.txtToimipisteTunnus.setText(Integer.toString(PalveluVaraus.getToimipisteTun()));

                String format2 = "yyyy-mm-dd";      //päivämäärien dataformaattia muutellaan
                DateFormat dFormat = new SimpleDateFormat(format2);
                String alkuPvmString = dFormat.format(PalveluVaraus.getAlkuPvm());
                String loppuPvmFormat = dFormat.format(PalveluVaraus.getLoppuPvm());
                GUI.txtVarattu_Alkupvm.setText(alkuPvmString);
                GUI.txtVarattu_Loppupvm.setText(loppuPvmFormat);
            }
		} 
        catch (SQLException se) 
        {
            JOptionPane.showMessageDialog(null, "Tapahtui tietokantavirhe varausta hakiessa.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
            se.printStackTrace();
        } 
        catch (Exception e)      
        {
            JOptionPane.showMessageDialog(null, "Tapahtui virhe varausta hakiessa.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
		
		return PalveluVaraus;

        }
    public void PoistaVaraus(Connection connection, PalveluVaraus PalveluVaraus)  throws SQLException, Exception 
    {
        
        String sql = "DELETE FROM varaus WHERE varaus_id = ? ";
        String sql2 = "DELETE FROM varaus WHERE varaus_id = ? ";

        ResultSet tulosjoukko = null;
        ResultSet tulosjoukko2 = null;
        PreparedStatement lause = connection.prepareStatement(sql);
        PreparedStatement lause2 = connection.prepareStatement(sql2);
        try 
        {
            lause.setInt(1, PalveluVaraus.getVarausTun());
            lause2.setInt(1, PalveluVaraus.getVarausTun());

            tulosjoukko = lause.executeQuery();
            tulosjoukko2 = lause2.executeQuery();

            System.out.println("poistettu " + PalveluVaraus.toString());

            VaraustenHallintaGUI GUI = new VaraustenHallintaGUI();
            GUI.txtVarauksenTunnus.setText(null);
            GUI.txtPalvelunTunnus.setText(null);
            GUI.txtAsiakasTunnus.setText(null);
            GUI.txtToimipisteTunnus.setText(null);
            GUI.txtVarattu_Alkupvm.setText(null);
            GUI.txtVarattu_Loppupvm.setText(null);
            JOptionPane.showMessageDialog(null, "Varaus poistettu onnistuneesti");
		} 
        catch (SQLException se) 
        {
            JOptionPane.showMessageDialog(null, "Tapahtui tietokantavirhe varausta poistettaessa.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
            se.printStackTrace();
        } 
        catch (Exception e)      
        {
            JOptionPane.showMessageDialog(null, "Tapahtui virhe varausta poistettaessa.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
