package wargame;

import javax.swing.JOptionPane;

/**
  * Cette classe gère les exceptions du jeu.
  *
  * @author Alexandre Vernet */
public class WargameException extends Exception
{
    private static final long serialVersionUID = 1L;
    private final String message;
    
    /**
      * Renvoie une WargameException agrémentée d'un message,
      * pour l'afficher plus tard dans une messagebox.
      * @param message Le message a afficher dans la messagebox.
      */
    public WargameException(String message)
    {
        this.message = message;
    }
    
    /**
      * Renvoie le message de l'exception.
      * @return String Le message de l'exception.
      */
    @Override
    public String getMessage()
    {
        return message;
    }
    
    /**
      * Affiche une messagebox d'erreur fatale, puis quitte
      * l'application en appuyant sur le bouton.
      * @param message Le message à afficher dans la messagebox.
      */
    public static void montrerMessageBoxFatal(String message)
    {
        Object[] options = { "Quitter le jeu" };
        int retour;
        
        retour = JOptionPane.showOptionDialog(
            null,
            message,
            "Erreur fatale",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.ERROR_MESSAGE,
            null,
            options,
            options[0]);
        
        if (retour == JOptionPane.OK_OPTION)
            System.exit(IConfig.FATAL_ERROR);
    }
    
    /**
      * Affiche une messagebox d'erreur non fatale et ne quitte pas
      * l'application.
      * @param message Le message à afficher dans la messagebox.
      */
    public static void montrerMessageBoxNonFatal(String message)
    {
        Object[] options = { "OK" };
        
        JOptionPane.showOptionDialog(
            null,
            message,
            "Information",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]);
    }
}
