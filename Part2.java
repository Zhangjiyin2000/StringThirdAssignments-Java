
/**
 * Write a description of Part2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2 {
    double cgRatio(String dna) {
        int count = 0;
        for (int i = 0; i < dna.length(); i++ ) {
            if (dna.charAt(i) == 'C' || dna.charAt(i) == 'G' ) {
                count++;
            }
        }
        return (float) count / dna.length();
    }
    
    void testCgRatio() {
        String dna = "ATGCCATAG";
        System.out.println(cgRatio(dna));
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
            if ((startIndex - currIndex) % 3 == 0) {
                count++;
            } else {
                break;
            }
            
            currIndex = startIndex + 3;
        }
        return count;
    }
    
    void testCountCTG() {
        String dna = "CTGCTGCTG";
        System.out.println(countCTG(dna));
    }

}
