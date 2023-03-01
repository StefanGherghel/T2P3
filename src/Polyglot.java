//import libraria principala polyglot din graalvm
import org.graalvm.polyglot.*;

//clasa principala - aplicatie JAVA
class Polyglot {
    private static int[] int_py(){
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        Value result = polyglot.eval("python", "import random\nv=[]\nfor i in range(20):\n\tv.append(random.randint(0,100))\nv");

        int rez[] = new int[20];
        for (Integer i=0;i<20;i++)
            rez[i]=result.getArrayElement(i).asInt();
        // inchidem contextul Polyglot
        polyglot.close();
        return rez;
    }

    private static void print_js(int[] v){
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        polyglot.getBindings("js").putMember("v",v);
        Value result = polyglot.eval("js", "console.log(v)");
        polyglot.close();
    }
    private static int[] process_R(int[] v){
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        polyglot.getBindings("R").putMember("v",v);
        Value result = polyglot.eval("R", "nv <- as.vector(v)\nrez <- sort(nv)\nrez<-rez[-c(1,2,3,4,17,18,19,20)]");
        int rez[] = new int[12];
        for (Integer i=0;i<12;i++)
            rez[i]=result.getArrayElement(i).asInt();
        // inchidem contextul Polyglot
        Value medie = polyglot.eval("R", "m = mean(rez)\nprint(m)");
        polyglot.close();
        return rez;
    }

    //functia MAIN
    public static void main(String[] args) {
        //construim un context pentru evaluare elemente JS
        Context polyglot = Context.create();

        int res[] = int_py();
        //for (Integer i=0;i<20;i++)
        //    System.out.print(res[i]+" ");
        System.out.print("Lista realizata in py: ");
        print_js(res);
        System.out.print("Media si noua lista procesate in R: ");
        res = process_R(res);
        print_js(res);
        polyglot.close();
    }
}

