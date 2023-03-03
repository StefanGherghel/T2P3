//import libraria principala polyglot din graalvm
import org.graalvm.polyglot.*;

import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

//clasa principala - aplicatie JAVA
class Polyglot {

    private static int[] insert_numbers_PY(){
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        Value result = polyglot.eval("python", "data=[]\ndata.append(int(input(\"Nr de aruncari: \")))\ndata.append(int(input(\"Nr max de castiguri: \")))\ndata");
        int rez[] = new int[2];
        for (var i=0;i<2;i++)
            rez[i]=result.getArrayElement(i).asInt();
        polyglot.close();
        return rez;
        // inchidem contextul Polyglot
    }
    private static void bin_distribution_R(int[]v){
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        polyglot.getBindings("R").putMember("v",v);
        Value result = polyglot.eval("R", "x<-seq(0,v[1],by=1)\ny <- dbinom(x, v[1], prob=0.5)\npng(file = \"dbinom.png\")\nplot(x,y)\ndev.off()\nprint(\"Probabilitatea sa castige de mai putin de nr introsus de ori: \")\nprint(sum(dbinom(0:v[2],v[1],0.5)))\n");
        //int rez[] = new int[10];
        //for (var i=0;i<10;i++)
        //    rez[i]=result.getArrayElement(i).asInt();
        polyglot.close();
        //return rez;
        // inchidem contextul Polyglot
    }


    //functia MAIN
    public static void main(String[] args) throws java.io.IOException{

        Context polyglot = Context.create();

        int[]Numere = insert_numbers_PY();
        bin_distribution_R(Numere);


        polyglot.close();
    }
}