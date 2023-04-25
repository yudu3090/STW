import java.io.*;
import java.util.*;

public class UkkonenSuffixTree {

    private static int activeEdgeIndex, offset;

    /**
     * gauname vaiko indeksa pagal simboli chars[lastCharIndex - offset] paskutinis - ilgis su kuriuo dirbame
     * @return vaiko indeksas pagal indeksa
     */
    private static int getChildEdgeIndex() {
        if (!edges[activeEdgeIndex].children.containsKey(chars[lastCharIndex - offset])) {//jeigu briauna neturi sakos
            return 0;//graziname sakni
        }
        return edges[activeEdgeIndex].children.get(chars[lastCharIndex - offset]);//graziname ta pacia saka
    }

    /**
     * leidziames per medi tol, kol briaunos ilgis ne bus didesnis nei ilgis su kuriuos dirbame
     */
    private static void comeDown() {
        while (offset > edges[getChildEdgeIndex()].len) {
            activeEdgeIndex = getChildEdgeIndex();
            offset -= edges[activeEdgeIndex].len;
        }
    }

    /**
     * @param begin briaunos pradzios indeksas
     * @param len   briaunos ilgis
     * @return paskutionio ÄÆterpto indeksas (arba jau iterptÅ³ skaiÄ¨ius)
     */
    private static int putEdge(int begin, int len) {
        edges[lastEdgeIndex] = new Edge(begin, len);
        return lastEdgeIndex++;
    }

    /**
     * pagrindinÄ— metodo logika Ä¨ia
     * naujos eilutÄ—s apdirbimas
     * @param 
     */
    private static void putChar(char c) {
        chars[lastCharIndex++] = c; //iterpeme simboli i masyva ir padidiname counteri
        int lastLinked = 0; //sukuriame suffix'u linkus, tam kad zymeti panasias briaunas vienos simbolio ribose
        offset++; //eilutes ilgis didinasi nes atsiranda dar vienas naujas simbolis
        while (offset > 0) { //kol visi simboliai nepraejo
            comeDown(); //nusileidziame iki briaunos, kur yra suffix'as
            int processedEdgeIndex = getChildEdgeIndex();
            char lastCharProcessedEdge = chars[edges[processedEdgeIndex].begin + offset - 1]; //paskutinis simbolis sioje braiunoje
            if (processedEdgeIndex == 0) { //jei nera vaiku, tai tokio simbolio dar nebuvo ir dedama i sakni
                edges[activeEdgeIndex].children.put(chars[lastCharIndex - offset], putEdge(lastCharIndex - offset, Integer.MAX_VALUE)); //dedame lapa i sakni
                edges[lastLinked].link = activeEdgeIndex; //dedame linka i iterpimo vieta
                lastLinked = 0; // paskurinis sulinkintas - jis
            } else if (lastCharProcessedEdge == c) { //jei briauos pabaiga ir simbolis sutapo
                edges[lastLinked].link = activeEdgeIndex; //tiesiog dedame suffix'o linka
                return;
            } else { //briaunos visuris
                int split = putEdge(edges[processedEdgeIndex].begin, offset - 1); //pridedame nauja briauna
                edges[split].children.put(c, putEdge(lastCharIndex - 1, Integer.MAX_VALUE)); //is ten atsiranda lapas su simboliu
                edges[split].children.put(lastCharProcessedEdge, processedEdgeIndex); //ir briauno tampa naujos briaunos vaiku
                edges[processedEdgeIndex].begin += offset - 1; //Š½briaunos pradzia judame ten kur iterpeme begin -> offset - 1
                edges[processedEdgeIndex].len -= offset - 1; //ilgis sumazejo
                edges[activeEdgeIndex].children.put(chars[lastCharIndex - offset], split); //issisakojima idedame i aktyvia saka
                edges[lastLinked].link = split; //dedame linka
                lastLinked = split; // paskurinis sulinkintas - jis
            }
            if (activeEdgeIndex == 0) {//jei iterpimas buvo is saknies
                offset--;//maziname ilgi su kuriuo dirbame
            } else {
                activeEdgeIndex = edges[activeEdgeIndex].link;//kitaip, pereiname per suffix'o linka i panasia briauna
            }
        }
    }

    /**
     * counteriai ir masyvai simboliÅ³ ir briaunÅ³
     */
    private static int lastCharIndex, lastEdgeIndex;
    private static char[] chars;
    private static Edge[] edges;

    public static void main(String[] args) {
        System.out.println("Iveskite eilute:");
        String string = new Scanner(System.in).nextLine() + "$";//perskaitem eilutÄ™ ir pridÄ—jome pabaigos simbolÄÆ
        int stringLength = string.length();
        edges = new Edge[2 * stringLength + 2];//paimta iÅ� algoritmo paaiskinimo
        lastEdgeIndex = 0;
        chars = new char[stringLength];//vieta ÄÆvestos eilutÄ—s simboliams
        lastCharIndex = 0;
        putEdge(0, Integer.MAX_VALUE);//dedam Å�aknÄÆ ÄÆ pabaigÄ…
        activeEdgeIndex = 0;//pirma aktyvi braiuna - Å�aknis
        for (int i = 0; i < stringLength; i++) {
            putChar(string.charAt(i));
        }
        print();
    }

    private static class Edge {
        /**
         * pradiniam simbolio indeksui saugoti, ilgiui 
         */
        private int begin, len, link;
        private Map<Character, Integer> children = new TreeMap<>();

        private Edge(int begin, int len) {
            this.begin = begin;
            this.len = len;
        }

        /**
         * graÅ¾ina simboliÅ³ kiekÄÆ (pabaigÄ… eilutÄ—s)
         * @return briaunos ilgis
         */
        public int getLen() {
            return Math.min(lastCharIndex + 1, len);
        }
    }

    /**
     * spausdiname medÄÆ ÄÆ redaktoriÅ³
     */
    private static void print() {
        PrintWriter out = new PrintWriter(System.out);
        out.println("digraph {");
        //Å�akÅ³ kryptis
        out.println("rankdir = LR;");
        out.println("edge [arrowsize=0.4,fontsize=10]");
        out.println("node0 [label=\"\",style=filled,fillcolor=lightgrey,shape=circle,width=.1,height=.1];");
        //rekursija nuo 0 briaunos, nuo Å�aknÅ³
        printTree("leaves", 0, out);
        printTree("nodes", 0, out);
        printTree("edges", 0, out);
        printTree("links", 0, out);
        out.println("}");
        out.close();
    }

    /**
     * @param index briaunos indeksas
     * @param out Ä¨ia kur raÅ�ysime
	 * SI DALYS IS IRANKIO DOKUMENTACIJOS, GALI KARTOTIS ARBA BUTI PANASI
     */
    
    private static void printTree(String n, int index, PrintWriter out) {
        if (n=="leaves") {
        	if (edges[index].children.size() == 0) {
        		out.printf("node%d[shape=point]%n", index);//indeksas ÄÆterpiamas vietoje %d ir t.t.
        	} else {
        		for (int childIndex : edges[index].children.values()) {
        			printTree("leaves", childIndex, out);
        		}
        	}
        } else if (n=="nodes") {
        	 if (index != 0 && edges[index].children.size() > 0) {
                 out.printf("node%d[label=\"\",style=filled,fillcolor=lightgrey,shape=circle,width=.07,height=.07]%n", index);
             }
             for (int childIndex : edges[index].children.values()) {
            	 printTree("nodes",childIndex, out);
             }
         } else if (n=="edges") {
        	 for (int childIndex : edges[index].children.values()) {
                 out.printf("node%d -> node%d[label=\"%s\",weight=3]%n", index, childIndex, new String(Arrays.copyOfRange(chars, edges[childIndex].begin, edges[childIndex].begin + Math.min(lastCharIndex + 1, edges[childIndex].len))).trim());
                 printTree("edges",childIndex, out);
             }
         } else  if (n=="links") {
        	 if (index != 0 && edges[index].link != 0) {
        		 out.printf("node%d -> node%d [weight=1,style=dotted]%n", index, edges[index].link);
        	 }
        	 for (int childIndex : edges[index].children.values()) {
        		 printTree("links",childIndex, out);
        	 }               
         }
    }
}
 