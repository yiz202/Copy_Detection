
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.opencsv.*;

import javax.swing.*;
import java.io.*;

//import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
//import org.apache.commons.lang3.builder.ToStringBuilder;
// if a sentence has a dot then counted as a sentence, if it's length is less than t-k

/**
 * Created by Yilun on 5/30/16.
 */
final class DocPair_Score_Chin {

    private final String filename1;
    private final String filename2;
//    private final int cluster;
    private final double score;


    public DocPair_Score_Chin(String filename1, String filename2, double score) {
        this.filename1 = filename1;
        this.filename2 = filename2;
//        this.cluster = cluster;
        this.score = score;
    }

    public String getFilename1() {
        return filename1;
    }

    public String getFilename2() {
        return filename2;
    }
//    public int getCluster() {
//        return cluster;
//    }


    public double getScore() {
        return score;
    }


    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("filename1: ").append(this.getFilename1()).append(System.getProperty("line.separator"));
        strBuilder.append("filename2: ").append(this.getFilename2()).append(System.getProperty("line.separator"));
//        strBuilder.append("cluster: ").append(this.getCluster()).append(System.getProperty("line.separator"));
        strBuilder.append("score: ").append(this.getScore()).append(System.getProperty("line.separator"));
        return strBuilder.toString();

    }
}




final class Pair_Chin {
    private final int first;
    private final int second;

    public Pair_Chin(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return ("start" + this.getFirst() + " end" + this.getSecond());

    }
}


final class ResultPair_Chin{
    private final float similarity;
    private final ArrayList<String> matchingStr;
    private final ArrayList<String> matchingStrInText1;
    private final ArrayList<String> matchingStrInText2;
    private final String text1;
    private final String filename1;
    private final int pIndex1;
    private final int sIndex1;
    private final ArrayList<Pair_Chin> Pair_Chins1;
    private final String text2;
    private final String filename2;
    private final int pIndex2;
    private final int sIndex2;
    private final ArrayList<Pair_Chin> Pair_Chins2;


    public ResultPair_Chin(float similarity, ArrayList<String> matchingStr, ArrayList<String> matchingStrInText1, ArrayList<String> matchingStrInText2, String text1, String filename1, int pIndex1, int sIndex1, ArrayList<Pair_Chin> Pair_Chins1,
                           String text2, String filename2, int pIndex2, int sIndex2, ArrayList<Pair_Chin> Pair_Chins2) {
        this.similarity = similarity;
        this.matchingStr = matchingStr;
        this.matchingStrInText1 = matchingStrInText1;
        this.matchingStrInText2 = matchingStrInText2;
        this.text1 = text1;
        this.filename1 = filename1;
        this.text2 = text2;
        this.pIndex1 = pIndex1;
        this.sIndex1 = sIndex1;
        this.Pair_Chins1 = Pair_Chins1;
        this.filename2 = filename2;
        this.pIndex2 = pIndex2;
        this.sIndex2 = sIndex2;
        this.Pair_Chins2 = Pair_Chins2;
    }
    public float getSimilarity() {return similarity; }
    public ArrayList<String> getMatchingStr() {return matchingStr;}
    public ArrayList<String> getMatchingStrInText1() {return matchingStrInText1;}
    public ArrayList<String> getMatchingStrInText2() {return matchingStrInText2;}
    public String getText1() {return text1;}
    public String getFilename1() {return filename1;}
    public int getpIndex1() {return pIndex1;}
    public int getsIndex1() { return sIndex1;}
    public ArrayList<Pair_Chin> getPair_Chins1() {return Pair_Chins1;}
    public String getText2() {return text2;}
    public String getFilename2() {return filename2;}
    public int getpIndex2() {return pIndex2;}
    public int getsIndex2() {return sIndex2;}
    public ArrayList<Pair_Chin> getPair_Chins2() {return Pair_Chins2;}

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Similarity: ").append(this.getSimilarity()).append(System.getProperty( "line.separator" ));
        strBuilder.append("matchingStr : ").append(this.matchingStr.toString()).append(System.getProperty( "line.separator" ));
        strBuilder.append("matchingStrInText1 : ").append(this.matchingStrInText1.toString()).append(System.getProperty( "line.separator" ));
        strBuilder.append("matchingStrInText2 : ").append(this.matchingStrInText2.toString()).append(System.getProperty( "line.separator" ));
        strBuilder.append("Filename1: ").append(this.getFilename1()).append(System.getProperty( "line.separator" ));
        strBuilder.append("pIndex1 :").append(this.getpIndex1()).append(System.getProperty( "line.separator" ));
        strBuilder.append("sIndex1 : ").append(this.getsIndex1()).append(System.getProperty( "line.separator" ));
        strBuilder.append("Sentence1: ").append(this.getText1()).append(System.getProperty( "line.separator" ));
        strBuilder.append("start&end1 : ").append(this.getPair_Chins1().toString()).append(System.getProperty( "line.separator" ));
        strBuilder.append("Filename2: ").append(this.getFilename2()).append(System.getProperty( "line.separator" ));
        strBuilder.append("pIndex2 :").append(this.getpIndex2()).append(System.getProperty( "line.separator" ));
        strBuilder.append("sIndex2 : ").append(this.getsIndex2()).append(System.getProperty( "line.separator" ));
        strBuilder.append("Sentence2: ").append(this.getText2()).append(System.getProperty( "line.separator" ));
        strBuilder.append("start&end2 : ").append(this.getPair_Chins2().toString()).append(System.getProperty( "line.separator" ));
        return strBuilder.toString();
//        return ("Similarity: "+this.getSimilarity()+
//                "Sentence1: "+this.getText1()+
//                "pIndex1 :"+this.getpIndex1()+
//                "sIndex1 : "+ this.getsIndex1() +
//                "start&end1 : "+ this.getPair_Chins1().toString()+
//                "Sentence2: "+this.getText2()+
//                "pIndex2 :"+this.getpIndex2()+
//                "sIndex2 : "+ this.getsIndex2() +
//                "start&end2 : "+ this.getPair_Chins2().toString());

    }


}
public class Table_Chin {
    private static double threshold = 0.5;
    private static int t = Winnowing_Chin.t;
    private static int k = Winnowing_Chin.k;


    public static int docLength(String filename){
        BufferedReader br = null;
        int i = 0;

        try {

            String paragraph;
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    new FileInputStream("myfile.txt"), "UTF-16")

            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filename), "UTF-8"));

            while ((paragraph = br.readLine()) != null) {
                if (paragraph.equals("")) continue;
//                Pattern re = Pattern.compile("[^!?\\s][^!?]*(?:[!?](?!['\"]?\\s|$)[^!?]*)*[!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
                Pattern re2 = Pattern.compile("[^\\u3002\\u003f\\u0021\\u2026\\uff1f\\uff01]+[\\u3002\\u003f\\u0021\\u2026\\uff1f\\uff01]");
                Matcher reMatcher2 = re2.matcher(paragraph);
                while (reMatcher2.find()) {
                    i++;
                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return i;

    }

    public static ArrayList<ArrayList<String>> groupText(String filename) {
        BufferedReader br = null;
        ArrayList<ArrayList<String>> sentenceArr = new ArrayList<>();
        int i = 0;

        try {

            String paragraph;
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    new FileInputStream("myfile.txt"), "UTF-16")

            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filename), "UTF-8"));

            while ((paragraph = br.readLine()) != null) {
                if (paragraph.equals("")) continue;
                sentenceArr.add(new ArrayList<String>());
//                Pattern re = Pattern.compile("[^!?\\s][^!?]*(?:[!?](?!['\"]?\\s|$)[^!?]*)*[!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
                Pattern re2 = Pattern.compile("[^\\u3002\\u003f\\u0021\\u2026\\uff1f\\uff01]+[\\u3002\\u003f\\u0021\\u2026\\uff1f\\uff01]");
                Matcher reMatcher2 = re2.matcher(paragraph);
                while (reMatcher2.find()) {
                    //adding each sentence to paragraph
                    sentenceArr.get(i).add(reMatcher2.group());
                }
                i++;

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return sentenceArr;

    }

    // 根据UnicodeBlock方法判断中文标点符号
    private static boolean isChinesePunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                || ub == Character.UnicodeBlock.VERTICAL_FORMS) {
            return true;
        } else {
            return false;
        }
    }

    public static Pair_Chin matching(String text, String substr) {
        text = text.toLowerCase();
        int i = 0;
        int j = 0;
        int start = 0;
        int wsindex = 0;


        while (i < text.length() && j < substr.length()) {
            int c = text.codePointAt(i);
            char s = text.charAt(i);
            char d = substr.charAt(j);
            if (Character.UnicodeScript.of(text.codePointAt(i)) != Character.UnicodeScript.HAN) {
                i++;
                continue;
            }
            if (text.charAt(i) != substr.charAt(j)) i++;
            else {
                start = i;
                i++;
                j++;
                while (j < substr.length()) {

                    while (isChinesePunctuation(text.charAt(i)) || Character.isWhitespace(text.charAt(i))) {
                        wsindex = i - 1;
                        i++;
                    }
                    while (Character.UnicodeScript.of(text.codePointAt(i)) != Character.UnicodeScript.HAN) i++;
                    char a = text.charAt(i);
                    char b = substr.charAt(j);
                    if (text.charAt(i) != substr.charAt(j)) {
                        start = 0;
                        j = 0;
                        break;
                    } else {
                        if (j == substr.length() - 1) {
                            wsindex = i;
                            j++;
                            break;
                        } else {
                            i++;
                            j++;
                        }
                    }


                }

            }
        }
        if (i < text.length()) {
            if (isChinesePunctuation(text.charAt(i))|| Character.isWhitespace(text.charAt(i))) {
                wsindex = i - 1;
            } else if (i == text.length() - 1) wsindex = text.length() - 2;
        }

//    System.out.println(text.substring(start,wsindex+1));
        return new Pair_Chin(start, wsindex);


    }

        //return the arraylist that contains qualified
    public static ArrayList<ResultPair_Chin> qualified(String filname1, ArrayList<ArrayList<String>> sentenceArr1, String filename2, ArrayList<ArrayList<String>> sentenceArr2){
        ArrayList<ResultPair_Chin> resultArr = new ArrayList<>();
        int score = 0;
        for(int ip1 = 0; ip1 < sentenceArr1.size();ip1++) {
            for (int is1 = 0; is1 < sentenceArr1.get(ip1).size(); is1++) {
                // sentence in document 1
                String s1 = sentenceArr1.get(ip1).get(is1);
                if (!Winnowing_Chin.ifmetMinimumLenChin(s1,t-k)) continue;
                try {
                    for (int ip2 = 0; ip2 < sentenceArr2.size(); ip2++) {
                        for (int is2 = 0; is2 < sentenceArr2.get(ip2).size(); is2++) {
                            // sentence in document 2
                            String s2 = sentenceArr2.get(ip2).get(is2);
                            if (!Winnowing_Chin.ifmetMinimumLenChin(s2, t - k)) continue;


                            //Winnowing_Chin to compare two sentences
//                        System.out.println("pIndex1 = "+ip1 + "sIndex1 = "+ is1);
//                        System.out.println("pIndex2 = "+ip2 + "sIndex2 = "+ is2);

                            ArrayList<long[]> fp1 = Winnowing_Chin.Winnowing_Chin(s1);
                            if (fp1 == null) continue;
                            ArrayList<long[]> fp2 = Winnowing_Chin.Winnowing_Chin(s2);
                            if (fp2 == null) continue;
                            float similar = Winnowing_Chin.similarity(fp1, fp2).getFirst();
                            ArrayList<long[]> matchingHash = Winnowing_Chin.similarity(fp1, fp2).getSecond();

                            ArrayList<String> strArr = Winnowing_Chin.matchingStrings(s1, s2, matchingHash);

                            if (similar >= threshold) {
                                score += similar;
                                //record the sentences and see where is matching
                                ArrayList<Pair_Chin> Pair_Chin1 = new ArrayList<>();
                                ArrayList<Pair_Chin> Pair_Chin2 = new ArrayList<>();

                                for (String matchingsubstring : strArr) {
                                    Pair_Chin1.add(matching(s1, matchingsubstring));
                                    Pair_Chin2.add(matching(s2, matchingsubstring));
                                }
//                            System.out.println("Pair_Chin1 "+ReflectionToStringBuilder.toString(Pair_Chin1));
//                            System.out.println("Pair_Chin2 "+ ReflectionToStringBuilder.toString(Pair_Chin2));
                                ArrayList<String> subText1 = new ArrayList<>();
                                ArrayList<String> subText2 = new ArrayList<>();
                                //find the substring in original text
                                for (Pair_Chin Pair_ChinSub : Pair_Chin1)
                                    subText1.add(s1.substring(Pair_ChinSub.getFirst(), Pair_ChinSub.getSecond() + 1));
                                for (Pair_Chin Pair_ChinSub : Pair_Chin2)
                                    subText2.add(s2.substring(Pair_ChinSub.getFirst(), Pair_ChinSub.getSecond() + 1));


                                ResultPair_Chin result = new ResultPair_Chin(similar, strArr, subText1, subText2, s1, filname1, ip1, is1, Pair_Chin1,
                                        s2, filename2, ip2, is2, Pair_Chin2);
//                            System.out.println(result.toString());
                                resultArr.add(result);
                            }
                        }
                    }
                }
                catch (Exception e) {
                    return null;
                }

            }
        }
        return resultArr;
    }
//    public static ResultPair_Chin qualified(String s1, String s2) {
//        int ip1 = 0;
//        int ip2 = 0;
//        int is1 = 0;
//        int is2 = 0;
//
//        ResultPair_Chin result;
//        //Winnowing_Chin to compare two sentences
////                        System.out.println("pIndex1 = "+ip1 + "sIndex1 = "+ is1);
////                        System.out.println("pIndex2 = "+ip2 + "sIndex2 = "+ is2);
//
//        ArrayList<long[]> fp1 = Winnowing_Chin.Winnowing_Chin(s1);
//        ArrayList<long[]> fp2 = Winnowing_Chin.Winnowing_Chin(s2);
//        float similar = Winnowing_Chin.similarity(fp1, fp2).getFirst();
//        ArrayList<long[]> matchingHash = Winnowing_Chin.similarity(fp1, fp2).getSecond();
//
//        ArrayList<String> strArr = Winnowing_Chin.matchingStrings(s1, s2, matchingHash);
//
//
//        //record the sentences and see where is matching
//        ArrayList<Pair_Chin> Pair_Chin1 = new ArrayList<>();
//        ArrayList<Pair_Chin> Pair_Chin2 = new ArrayList<>();
//
//        for (String matchingsubstring : strArr) {
//            Pair_Chin1.add(matching(s1, matchingsubstring));
//            Pair_Chin2.add(matching(s2, matchingsubstring));
//        }
////                            System.out.println("Pair_Chin1 "+ReflectionToStringBuilder.toString(Pair_Chin1));
////                            System.out.println("Pair_Chin2 "+ ReflectionToStringBuilder.toString(Pair_Chin2));
//        ArrayList<String> subText1 = new ArrayList<>();
//        ArrayList<String> subText2 = new ArrayList<>();
//        //find the substring in original text
//        for (Pair_Chin Pair_ChinSub : Pair_Chin1)
//            subText1.add(s1.substring(Pair_ChinSub.getFirst(), Pair_ChinSub.getSecond() + 1));
//        for (Pair_Chin Pair_ChinSub : Pair_Chin2)
//            subText2.add(s2.substring(Pair_ChinSub.getFirst(), Pair_ChinSub.getSecond() + 1));
//
//
//        result = new ResultPair_Chin(similar, strArr, subText1, subText2, s1, "1", ip1, is1, Pair_Chin1,
//                s2, "2", ip2, is2, Pair_Chin2);
//
//        return result;
//    }
    public static void extractToFiles(String csvFilename, String dirName) throws IOException {
        File dir = new File(dirName);  // directory to save files
        if (!dir.exists()) dir.mkdirs();
        CSVReader reader = new CSVReader(new FileReader(csvFilename));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            File file = new File(dir, String.valueOf(nextLine[0])+".txt");
                    // first column for id
                    PrintWriter pw = new PrintWriter(new FileWriter(file));
            pw.println(nextLine[10]);   // 10th column for article content
            pw.close();
        }
    }

    public static void extractToFilesForCluster(String csvFilename, String dirName) throws IOException {
        File dir = new File(dirName);  // directory to save files
        if (!dir.exists()) dir.mkdirs();
        CSVReader reader = new CSVReader(new FileReader(csvFilename));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            File file = new File(dir, String.valueOf(nextLine[0])+"cluster"+String.valueOf(nextLine[1])+".txt");
            // first column for id
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            pw.println(nextLine[10]);   // 10th column for article content
            pw.close();
        }
    }


    public static void main(String[] args) throws IOException {

        String dirName = "/Users/Yilun/Desktop/detection";
        String csvFilename = "/Users/Yilun/Desktop/SDSCproject/MergedPropganda2012.csv";
        extractToFilesForCluster(csvFilename,dirName);
        final File folder = new File(dirName);
        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !name.equals(".DS_Store");
            }
        });

        try(FileWriter fw = new FileWriter("/Users/Yilun/Desktop/outfilename", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
        for (final File fileEntry1 : files) {
            for (final File fileEntry2 : files) {
                int numObject = 0;
                int len = 0;
                double score = 0.0;
                try (FileWriter fw2 = new FileWriter("/Users/Yilun/Desktop/sentencewise", true);
                     BufferedWriter bw2 = new BufferedWriter(fw2);
                     PrintWriter out2 = new PrintWriter(bw2))
                {
                    out2.println("Now processing file pair: " + fileEntry1.toString() + " and " + fileEntry2.toString());
                    System.out.println("Now processing file pair: " + fileEntry1.toString() + " and " + fileEntry2.toString());
                    if (fileEntry1.toString().compareTo(fileEntry2.toString()) == 0) continue;
                    if (fileEntry1.toString().compareTo(fileEntry2.toString()) < 0) {
                        ArrayList<ArrayList<String>> sentenceArr1 = groupText(fileEntry1.toString());
                        ArrayList<ArrayList<String>> sentenceArr2 = groupText(fileEntry2.toString());
                        ArrayList<ResultPair_Chin> resulting = qualified(fileEntry1.getName(), sentenceArr1, fileEntry2.getName(), sentenceArr2);
                        //checking

                            for (ResultPair_Chin finalPair_Chin : resulting) {
                                out2.println(finalPair_Chin.toString());
    //                            System.out.println("An object is");
    //                            System.out.println(finalPair_Chin.toString());
                                numObject += 1;
                            }
                            if (docLength(fileEntry1.toString()) < docLength(fileEntry2.toString()))
                                len = docLength(fileEntry1.toString());
                            else len = docLength(fileEntry2.toString());
                            score = numObject * 1.0 / len;
                            DocPair_Score_Chin resultscore = new DocPair_Score_Chin(fileEntry1.getName(), fileEntry2.getName(), score);
                            //write to file
                            out.println(resultscore.toString());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println(e.getCause().getMessage());
                }
            }
        }
            out.close();
        } catch (Exception e) {
        System.out.println(e.getMessage());
            System.out.println(e.getCause().getMessage());
    }


    }
}
//    public static void main(String[] args) {
//        String s1 = "克林顿获得的初选代表票数已达到成为美国民主党总统候选人提名的标准。";
//        String s2 = "她将成为美国大选历史上首位获得主要政党提名的女性。";
//        ResultPair_Chin resulting = qualified(s1, s2);
//        //checking
//        System.out.println("An object is");
//        System.out.println(resulting.toString());
//
//    }










