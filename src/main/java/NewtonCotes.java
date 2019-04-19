class NewtonCotes
{
    private static double horner(double x, int stopien, double[] wsp)
    {
        double wynik=wsp[0];
        for (int i=1; i<=stopien; i++)
            wynik = wynik*x+wsp[i];
        return wynik;
    }

    static double simpson(double a, double b, double dokladnosc, int stopien, double[] wsp)
    {
        double h, suma1, suma2, poprzedni_wynik, ostatni_wynik = 0;
        int m = 10;
        do
        {
            h = (b-a)/m;
            poprzedni_wynik = ostatni_wynik;
            suma1 = 0;
            suma2 = 0;
            for(int i=1; i<=(m/2.0)-1; i++)
            {
                suma1 += horner(a+(2*i*h),stopien,wsp);
                suma2 += horner(a+((2*i+1)*h),stopien,wsp);
            }
            ostatni_wynik = h*(horner(a,stopien,wsp)+horner(b,stopien,wsp)+2*suma1+4*suma2)/3.0;
            m = m*2;
        }
        while (Math.abs(ostatni_wynik-poprzedni_wynik)>dokladnosc);
        return ostatni_wynik;
    }
}