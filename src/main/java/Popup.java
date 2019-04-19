import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Popup extends JFrame
{
    private Calki parent;
    private JPanel panel1;
    private JButton obliczPrzycisk;
    private JFormattedTextField[] pola;
    private JLabel[] podpisy;

    Popup(Calki parent)
    {
        this.parent = parent;
        Calki.keyBinding(obliczPrzycisk,panel1);

        pola = new JFormattedTextField[parent.getStopien()+1];
        podpisy = new JLabel[pola.length-1];

        NumberFormat zmiennoprzecinkowe = DecimalFormat.getInstance();

        for (int i=0; i<pola.length; i++)
        {
            pola[i] = new JFormattedTextField(zmiennoprzecinkowe);
            pola[i].setColumns(3);
            panel1.add(pola[i]);
            if (i != pola.length-1)
            {
                if (i != pola.length-2)
                {
                    String htmlLabel = "<html><span>x</span><a><sup>" + (pola.length - i - 1) + "</sup></a><span> +</span></html>";
                    podpisy[i] = new JLabel(htmlLabel);
                    panel1.add(podpisy[i]);
                }
                else
                {
                    podpisy[i] = new JLabel("x +");
                    panel1.add(podpisy[i]);
                }
            }
        }
        obliczPrzycisk.setText("Oblicz całkę");
        panel1.add(obliczPrzycisk);
        obliczPrzycisk.addActionListener
        (e ->
            {
                boolean czyNull = false;
                for (JFormattedTextField i : pola)
                {
                    if (i.getValue() == null)
                        czyNull = true;
                }
                if (!czyNull)
                {
                    Frame.getFrames()[getFrames().length - 2].dispose();
                    Frame.getFrames()[getFrames().length - 1].dispose();
                    JOptionPane.showMessageDialog(null, htmlMessage(), "Wynik", JOptionPane.INFORMATION_MESSAGE);
                    parent.setPoczatek("");
                    parent.setKoniec("");
                    parent.setStopien("");
                }
                else
                    JOptionPane.showMessageDialog(null,"Wprowadź wszystkie parametry","Ostrzeżenie",JOptionPane.WARNING_MESSAGE);
            }
        );
    }

    JPanel getPanel1()
    {
        return panel1;
    }

    private double[] getWsp()
    {
        double[] wsp = new double[pola.length];
        for (int i=0; i<pola.length; i++)
            wsp[i] = Double.parseDouble(pola[i].getText().replace(",","."));
        return wsp;
    }

    private String htmlMessage()
    {
        String pattern = "0.0";
        for (int i=1; i<parent.getIloscMiejscPoPrzecinku(); i++)
            pattern = pattern.concat("0");
        DecimalFormat d = new DecimalFormat(pattern);
        double[] przedzial = parent.getPrzedzial();
        String message = "<html><span>Całka oznaczona z wielomianu y =</span>";
        for (int i=0; i<podpisy.length; i++)
        {
            if (getWsp()[i] > 0 && i == 0)
            {
                if (Math.floor(getWsp()[i]) == getWsp()[i])
                {
                    if (getWsp()[i] != 1)
                        message = message.concat("<span> " + (int) getWsp()[i] + "x</span><a><sup>" + (podpisy.length - i) + "</sup></a></span>");
                    else
                        message = message.concat("<span> x</span><a><sup>" + (podpisy.length - i) + "</sup></a></span>");
                }
                else
                    message = message.concat("<span> " + getWsp()[i] + "x</span><a><sup>" + (podpisy.length - i) + "</sup></a></span>");
            }
                // współczynnik przy x^1
                else if (i == podpisy.length - 1)
                {
                    if (Math.floor(getWsp()[i]) == getWsp()[i])
                    {
                        if (getWsp()[i] > 0)
                        {
                            if (getWsp()[i] != 1)
                                message = message.concat("<span> +" + (int) getWsp()[i] + "x</span>");
                            else
                                message = message.concat("<span> +x</span>");
                        } else if (getWsp()[i] < 0)
                        {
                            if (getWsp()[i] != -1)
                                message = message.concat("<span> " + (int) getWsp()[i] + "x</span>");
                            else
                                message = message.concat("<span> -x</span>");
                        }
                    } else
                    {
                        if (getWsp()[i] > 0)
                            message = message.concat("<span> +" + getWsp()[i] + "x</span>");
                        else
                            message = message.concat("<span> " + getWsp()[i] + "x</span>");
                    }
                }
                // dodatni współczynnik przy wszystkich x oprócz najwyższego stopnia
                else if (getWsp()[i] > 0 && i != 0)
                {
                    if (Math.floor(getWsp()[i]) == getWsp()[i])
                    {
                        if (getWsp()[i] != 1)
                            message = message.concat("<span> +" + (int) getWsp()[i] + "x</span><a><sup>" + (podpisy.length - i) + "</sup></a></span>");
                        else
                            message = message.concat("<span> +x</span><a><sup>" + (podpisy.length - i) + "</sup></a></span>");
                    } else
                        message = message.concat("<span> +" + getWsp()[i] + "x</span><a><sup>" + (podpisy.length - i) + "</sup></a></span>");
                }
                // ujemny współczynnik
                else if (getWsp()[i] < 0)
                {
                    if (Math.floor(getWsp()[i]) == getWsp()[i])
                    {
                        if (getWsp()[i] != -1)
                            message = message.concat("<span> " + (int) getWsp()[i] + "x</span><a><sup>" + (podpisy.length - i) + "</sup></a></span>");
                        else
                            message = message.concat("<span> -x</span><a><sup>" + (podpisy.length - i) + "</sup></a></span>");
                    } else
                        message = message.concat("<span> " + getWsp()[i] + "x</span><a><sup>" + (podpisy.length - i) + "</sup></a></span>");
                }
            }
        // wyraz wolny
        if (getWsp()[podpisy.length]>0)
        {
            if (Math.floor(getWsp()[podpisy.length])==getWsp()[podpisy.length])
                message = message.concat("<span> +"+(int)getWsp()[podpisy.length]);
            else
                message = message.concat("<span> +"+getWsp()[podpisy.length]);
        }
        else if (getWsp()[podpisy.length]<0)
        {
            if (Math.floor(getWsp()[podpisy.length])==getWsp()[podpisy.length])
                message = message.concat("<span> "+(int)getWsp()[podpisy.length]);
            else
                message = message.concat("<span> "+getWsp()[podpisy.length]);
        }
        message = message.concat(" na przedziale (");
        if (Math.floor(przedzial[0])==przedzial[0])
            message = message.concat((int)przedzial[0]+"; ");
        else
            message = message.concat(przedzial[0]+"; ");
        if (Math.floor(przedzial[1])==przedzial[1])
            message = message.concat((int)przedzial[1]+") wynosi ");
        else
            message = message.concat(przedzial[1]+") wynosi ");
        message = message.concat(d.format(NewtonCotes.simpson(przedzial[0],przedzial[1],parent.getDokladnosc(),parent.getStopien(),getWsp()))+"</span></html>");
        return message;
    }
}