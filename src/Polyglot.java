//import libraria principala polyglot din graalvm
import org.graalvm.polyglot.*;

//clasa principala - aplicatie JAVA
class Polyglot {
    //metoda privata pentru conversie low-case -> up-case folosind functia toupper() din R
    //metoda privata pentru evaluarea unei sume de control simple a literelor unui text ASCII, folosind PYTHON
    private static int[] int_py(){
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        Value result = polyglot.eval("python", "import random\nv=[]\nfor i in range(10):\n\tv.append(random.randint(0,100))\nv");

        int rez[] = new int[10];
        for (Integer i=0;i<10;i++)
            rez[i]=result.getArrayElement(i).asInt();
        // inchidem contextul Polyglot
        polyglot.close();

        return rez;
    }

    //functia MAIN
    public static void main(String[] args) {
        //construim un context pentru evaluare elemente JS
        Context polyglot = Context.create();

        int res[] = int_py();
        for (Integer i=0;i<10;i++)
            System.out.print(res[i]+" ");
        polyglot.close();
    }
}

