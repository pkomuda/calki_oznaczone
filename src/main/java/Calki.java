import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Calki extends JFrame
{
    private JPanel panel1;
    private JButton zatwierdzPrzycisk;
    private JFormattedTextField dokladnoscPole;
    private JLabel dokladnoscPodpis;
    private JLabel przedzialPodpis;
    private JLabel poczatekPodpis;
    private JLabel koniecPodpis;
    private JFormattedTextField koniecPole;
    private JFormattedTextField poczatekPole;
    private JLabel stopienPodpis;
    private JFormattedTextField stopienPole;

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Całka oznaczona z wielomianu");
        frame.setContentPane(new Calki().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(frame);
        frame.setVisible(true);
    }

    private Calki()
    {
        keyBinding(zatwierdzPrzycisk,panel1);

        zatwierdzPrzycisk.setText("Zatwierdź");
        dokladnoscPodpis.setText("Ilość miejsc po przecinku");
        przedzialPodpis.setText("Przedział całkowania");
        poczatekPodpis.setText("Początek");
        stopienPodpis.setText("Stopień wielomianu");

        zatwierdzPrzycisk.addActionListener
        (e ->
            {
                if (dokladnoscPole.getValue()!=null && poczatekPole.getValue()!=null && koniecPole.getValue()!=null)
                {
                    poczatekPole.setText(poczatekPole.getText().replace(",","."));
                    koniecPole.setText(koniecPole.getText().replace(",","."));
                    JFrame popup = new JFrame("Wprowadź współczynniki");
                    popup.setContentPane(new Popup(this).getPanel1());
                    popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    popup.pack();
                    popup.setLocationRelativeTo(popup);
                    popup.setVisible(true);
                }
                else
                    JOptionPane.showMessageDialog(null,"Wprowadź wszystkie parametry","Ostrzeżenie",JOptionPane.WARNING_MESSAGE);
            }
        );
    }

    private void createUIComponents()
    {
        NumberFormatter calkowite = new NumberFormatter(NumberFormat.getInstance());
        calkowite.setValueClass(Integer.class);
        calkowite.setMinimum(0);
        dokladnoscPole = new JFormattedTextField(calkowite);
        stopienPole = new JFormattedTextField(calkowite);

        NumberFormat zmiennoprzecinkowe = DecimalFormat.getInstance();
        poczatekPole = new JFormattedTextField(zmiennoprzecinkowe);
        koniecPole = new JFormattedTextField(zmiennoprzecinkowe);
    }

    int getIloscMiejscPoPrzecinku()
    {
        if (dokladnoscPole.getValue() != null)
            return (int)dokladnoscPole.getValue();
        else
            return 2;
    }

    double getDokladnosc()
    {
        if (dokladnoscPole.getValue() != null)
            return Math.pow(10,-(int)dokladnoscPole.getValue());
        else
            return 0.01;
    }

    double[] getPrzedzial()
    {
        String[] tempString = new String[2];
        tempString[0] = poczatekPole.getText();
        tempString[1] = koniecPole.getText();

        double[] tempDouble = new double[2];
        tempDouble[0] = Double.parseDouble(tempString[0]);
        tempDouble[1] = Double.parseDouble(tempString[1]);

        double[] przedzial = new double[2];
        if (tempDouble[0] <= tempDouble[1])
        {
            przedzial[0] = tempDouble[0];
            przedzial[1] = tempDouble[1];
        }
        else
        {
            przedzial[0] = tempDouble[1];
            przedzial[1] = tempDouble[0];
        }
        return przedzial;
    }

    int getStopien()
    {
        return (int)stopienPole.getValue();
    }

    void setPoczatek(String text)
    {
        poczatekPole.setText(text);
    }

    void setKoniec(String text)
    {
        koniecPole.setText(text);
    }

    void setStopien(String text)
    {
        stopienPole.setText(text);
    }

    static void keyBinding(JButton przycisk, JPanel panel)
    {
        Action enter = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                przycisk.doClick();
            }
        };
        InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "enter");
        actionMap.put("enter", enter);
    }
}