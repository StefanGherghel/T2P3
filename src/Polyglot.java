//import libraria principala polyglot din graalvm
import org.graalvm.polyglot.*;

import java.util.Arrays;


//clasa principala - aplicatie JAVA
class Polyglot {
    //metoda privata pentru conversie low-case -> up-case folosind functia toupper() din R
    private static String RToUpper(String token){
        //construim un context care ne permite sa folosim elemente din R
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        //folosim o variabila generica care va captura rezultatul excutiei funcitiei R, toupper(String)
        //pentru aexecuta instructiunea I din limbajul X, folosim functia graalvm polyglot.eval("X", "I");
        Value result = polyglot.eval("R", "toupper(\""+token+"\");");
        //utilizam metoda asString() din variabila incarcata cu output-ul executiei pentru a mapa valoarea generica la un String
        String resultString = result.asString();
        // inchidem contextul Polyglot
        polyglot.close();

        return resultString;
    }

    //metoda privata pentru evaluarea unei sume de control simple a literelor unui text ASCII, folosind PYTHON
    private static int SumCRC(String token){
        //construim un context care ne permite sa folosim elemente din PYTHON
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        //folosim o variabila generica care va captura rezultatul excutiei functiei PYTHON, sum()
        //avem voie sa inlocuim anumite elemente din scriptul pe care il construim spre evaluare, aici token provine din JAVA, dar va fi interpretat de PYTHON
        Value result = polyglot.eval("python", "sum((ord(ch) for ch in '" + token + "'))**2");
        //utilizam metoda asInt() din variabila incarcata cu output-ul executiei, pentru a mapa valoarea generica la un Int
        int resultInt = result.asInt();
        // inchidem contextul Polyglot
        polyglot.close();

        return resultInt;
    }

    //functia MAIN
    public static void main(String[] args) {
        //construim un context pentru evaluare elemente JS
        Context polyglot = Context.create();
        //construim un array de string-uri, folosind cuvinte din pagina web:  https://chrisseaton.com/truffleruby/tenthings/
        Value array = polyglot.eval("js", "[\"ana\",\"are\",\"ana\",\"si\",\"ana\",\"si\",];");
        //pentru fiecare cuvant, convertim la upcase folosind R si calculam suma de control folosind PYTHON
        String[] element= new String[50];
        String[] upper = new String[50];
        int[] crc = new int[50];
        for(var i=0;i<50;i++)
            crc[i]=0;
        for (int i = 0; i < array.getArraySize();i++){
            element[i] = array.getArrayElement(i).asString();
            upper[i] = RToUpper(element[i]);
            crc[i] = SumCRC(upper[i]);
            System.out.println(upper[i] + " -> " + crc[i]);
        }
        System.out.println("Elemente cu aceeasi suma de control: ");
        int checked_crc[] = new int[50];
        for(var i=0;i<50;i++)
            checked_crc[i]=0;
        int i=0;
        while(crc[i]!=0)
        {
            Boolean ok = Boolean.FALSE;
            int j=0;
            while(checked_crc[j]!=0)
            {
                if(checked_crc[j]==crc[i])
                    ok = Boolean.TRUE;
                j++;
            }
            checked_crc[j] = crc[i];
            if(ok==Boolean.FALSE)
            {
                System.out.print("\n"+upper[i]+" ");
                for(var k=i+1;k<50;k++)
                {
                    if(crc[k]==crc[i])
                        System.out.print(upper[k]+" ");
                }
            }
            i++;
        }
        // inchidem contextul Polyglot
        polyglot.close();
    }
}