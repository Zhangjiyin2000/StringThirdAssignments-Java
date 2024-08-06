
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class Part1 {
    int findStopCodon(String dna, int startIndex, String stopCodon) {
        int stopIndex = dna.indexOf(stopCodon, startIndex + 3);
        while (stopIndex != -1) {
            int diff = stopIndex - startIndex;
            if (diff % 3 == 0 ) {
                return stopIndex;
            } else {
                stopIndex = dna.indexOf(stopCodon, stopIndex + 1);
            }
        }
        return dna.length();
    }
    
    void testFindStopCodon(){
        //            01234567890123456789012345
        String dna = "xxxyyyzzzTAAxxxyyyzzzTAAxx";
        int dex = findStopCodon(dna, 0, "TAA");
        if (dex != 9) System.out.println("error on 9"); 
        dex = findStopCodon(dna, 9, "TAA");
        if (dex != 21) System.out.println("error on 21");
        dex = findStopCodon(dna, 1, "TAA");
        if (dex != 26) System.out.println("error on 26");
        dex = findStopCodon(dna, 0, "TAG");
        if (dex != 26) System.out.println("error on 26 TAG");
        System.out.println("tests finished");
    }
    
    String findGene(String dna, int where) {
        int startIndex = dna.indexOf("ATG", where);
        if (startIndex == -1 ) return "";
        int taaIndex = findStopCodon(dna, startIndex, "TAA");
        int tagIndex = findStopCodon(dna, startIndex, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex, "TGA");
        int temp = Math.min(taaIndex, tagIndex);
        int minIndex = Math.min(temp, tgaIndex);
        if (minIndex == dna.length()) return "";
        return dna.substring(startIndex, minIndex + 3);
    }
    
    void testFindGene() {
        String dna1 = "TAATAATAA"; // no ATG
        System.out.println(dna1);
        System.out.println(findGene(dna1, 0));
        String dna2 = "ATGTTTTAGTTT";
        System.out.println(dna2);
        System.out.println(findGene(dna2, 0));
        String dna3 = "ATGTTTTTTAAATAAUUUTGATAG";
        System.out.println(dna3);
        System.out.println(findGene(dna3, 0));
        String dna4 = "ATGTTAATAGTGA";
        System.out.println(dna4);
        System.out.println(findGene(dna4, 0));
    }
    
    void printAllGenes(String dna) {
        int startIndex = 0;
        while(true) {
            String currGene = findGene(dna, startIndex);
            if (currGene.isEmpty() ) {
                break;
            }
            System.out.println(currGene);
            startIndex = dna.indexOf(currGene, startIndex) + currGene.length();
        }
        
    }
    
    StorageResource getAllGenes(String dna) {
        StorageResource genes = new StorageResource();
        int startIndex = 0;
        while(true) {
            String currGene = findGene(dna, startIndex);
            if (currGene.isEmpty() ) {
                break;
            }
            genes.add(currGene);
            startIndex = dna.indexOf(currGene, startIndex) + currGene.length();
        }
        return genes;
    }
    
    void testGetAllGenes() {
        System.out.println("----------");
        String dna = "TAATAATAAATGTTTTAGTTTATGTTTTTTAAATAAUUUTGATAG";
        StorageResource genes = getAllGenes(dna);
        for (String gene : genes.data() ) {
            System.out.println(gene);
        }
    }
    
    void testPrintAllGenes() {
        System.out.println("----------");
        String dna = "TAATAATAAATGTTTTAGTTTATGTTTTTTAAATAAUUUTGATAG";
        printAllGenes(dna);
    }


}
