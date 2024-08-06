
/**
 * Write a description of Part3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class Part3 {
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
        //String dna = "TAATAATAAATGTTTTAGTTTATGTTTTTTAAATAAUUUTGATAG";
        //brca1line.fa
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString();
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
    double cgRatio(String dna) {
        int count = 0;
        for (int i = 0; i < dna.length(); i++ ) {
            if (dna.charAt(i) == 'C' || dna.charAt(i) == 'G' ) {
                count++;
            }
        }
        return (float) count / dna.length();
    }
    
    void processGenes(StorageResource sr) {
        int countTotal = 0;
        int count = 0;
        int countCgRatio = 0;
        int LongestGenelen = 0;
        int count4CTG = 0;
        for (String s : sr.data() ) {
            countTotal++;
            int temp = s.length();
            if (temp > LongestGenelen) {
                LongestGenelen = temp;
            }
            if (s.length() > 60 ) {
                count++;
                System.out.println(s + " is longer than 9 characters");
            }
            if (cgRatio(s) > 0.35) {
                countCgRatio++;
                System.out.println(s + "'s C-G-ratio is higher than 0.35");
                
            }
            count4CTG += countCTG(s);
        }
        System.out.println("the total number of the strings is " + countTotal);
        System.out.println("the number of Strings in sr whose C-G-ratio is higher than 0.35: " + countCgRatio);
        System.out.println("the number of Strings in sr that are longer than 9 characters: " + count);
        System.out.println("the length of the longest gene in sr is: " + LongestGenelen);
        System.out.println(count4CTG);
    }
    
    int countCTG(String dna) {
        int currIndex = 0;
        int count = 0;
        int startIndex = 0;
        while (true) {
            startIndex = dna.indexOf("CTG", currIndex);
            if (startIndex == -1) {
                break;
            }
            count++;
            currIndex = startIndex + 3;
        }
        return count;
    }
    
    void testCountCTG() {
        String dna = "CTGCTGCTG";
        System.out.println(countCTG(dna));
    }
    
    void testProcessGenes() {
        //brca1line.fa GRch38dnapart.fa
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString();
        StorageResource allGenes = getAllGenes(dna);
        System.out.println("--------");
        System.out.println(dna);
        System.out.println("--------");
        processGenes(allGenes);
        System.out.println("--------");
        
        //String test1 = "ATGATGAAATTTTGATGA";
        //String test2 = "ATGTGA";
        //String test3 = "ATGCCATAG";
        //String test4 = "ATGAAATAG";
        //StorageResource genes = new StorageResource();
        //genes.add(test1);
        //genes.add(test2);
        //genes.add(test3);
        //genes.add(test4);
        //processGenes(genes);
    }

}
