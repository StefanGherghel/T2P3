//import libraria principala polyglot din graalvm
import org.graalvm.polyglot.*;

import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

//clasa principala - aplicatie JAVA
class Polyglot {

    private static int[] insert_dataset(){
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        Value result = polyglot.eval("python", "data=[]\ncitit=1\nfor i in range (10) :\n\tprint(\"Insert: \")\n\tcitit=int(input())\n\tif citit !=0:\n\t\tdata.append(citit)\ndata");
        int rez[] = new int[10];
        for (var i=0;i<10;i++)
            rez[i]=result.getArrayElement(i).asInt();
        polyglot.close();
        return rez;
        // inchidem contextul Polyglot
    }


    //functia MAIN
    public static void main(String[] args) throws java.io.IOException{

        Context polyglot = Context.create();
        int[] v = insert_dataset();
        FileWriter myWriter = new FileWriter("./dataset.txt");
        myWriter.write(Arrays.toString(v));
        myWriter.close();
        polyglot.close();
    }
}