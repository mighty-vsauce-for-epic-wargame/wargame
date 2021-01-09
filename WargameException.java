package wargame;

import javax.swing.JOptionPane;

public class WargameException extends Exception
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
    
    public WargameException(String message)
    {
        this.message = message;
    }
    
    @Override
    public String getMessage()
    {
        return message;
    }
    
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
