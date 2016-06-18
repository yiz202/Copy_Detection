import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.csvreader.CsvReader;

final class MyResult {
    private final float first;
    private final ArrayList<long[]> second;

    public MyResult(float first, ArrayList<long[]> second) {
        this.first = first;
        this.second = second;
    }

    public float getFirst() {
        return first;
    }

    public ArrayList<long[]> getSecond() {
        return second;
    }
}

public class Winnowing {
    //    private static double threshold = 0.8;
    public static int t = 6;
    public static int k = 3;
    private static int prime = 13;
//    private static long Q = longRandomPrime();
//    private static int k;
//    private static String text;

    private static long[] populateHashes(String text) {
        char[] str = text.toCharArray();
        int arrayLength = str.length - k + 1;
        long[] hashArray = new long[arrayLength];
        long hash = createHash(str);
        hashArray[0] = hash;
        for (int i = 1; i < arrayLength; i++) {
            hashArray[i] = recalculateHash(str, i, hashArray[i - 1]);

        }


        return hashArray;
    }
//
//    /** generate a random 31 bit prime **/
//    private static long longRandomPrime()
//    {
//        BigInteger prime = BigInteger.probablePrime(31, new Random());
//        return prime.longValue();
//    }

    private static long createHash(char[] str) {
        long[] charDecimal = chartoDecimalArr(str);
        long hash = 0;
//        for (int i = 0; i <end; i++) {
        for (int i = k-1; i>=0; i-- ){
            hash += charDecimal[k-i-1] * Math.pow(prime, i);
        }
//        System.out.println("random prime is!!!" + Q);
//        System.out.println(hash);
        return hash;
    }

    private static long recalculateHash(char[] str, int indexNow, long oldHash) {
        long[] charDecimal = chartoDecimalArr(str);
        long a = (long) (charDecimal[indexNow - 1] * Math.pow(prime, k - 1));
        long newHash = oldHash - a;
        return newHash*prime+charDecimal[indexNow + k - 1];
    }

    public static String preprocess_text(String x) {

//        String txt = x.replaceAll("\\p{P}", "").replaceAll("[^\\p{L}\\p{Nd}]+","").replaceAll("[0-9]","").replaceAll("[a-zA-Z]", "");
        String txt = x.toLowerCase().replaceAll("\\W", "").replaceAll("[0-9]","");
//        System.out.println("done processing is  "+ txt);
        return txt;

    }

    public static boolean ifmetMinimumLenEng(String x,int y) {
        String txt = Winnowing.preprocess_text(x);
//        String txt = x.replaceAll("\\p{P}", "").replaceAll("[^\\p{L}\\p{Nd}]+","").replaceAll("[0-9]","").replaceAll("[a-zA-Z]", "");
        if(txt.length() <= y){
            return false;
        }
        return true;

    }
    //test for the length of input string
    public static boolean ifmetMinimumLenChin(String x,int y) {

        String txt = x.replaceAll("\\p{P}", "").replaceAll("[^\\p{L}\\p{Nd}]+","").replaceAll("[0-9]","").replaceAll("[a-zA-Z]", "");
        if(txt.length() <= y){
            return false;
        }
        return true;

    }

    //helper
    private static long[] getMinAndIndex(long[] oneWindow){
        long tmp = oneWindow[0];
        int position = 0;
        for(int i = 1; i< oneWindow.length;i++) {
            if( tmp > oneWindow[i]) {
                tmp = oneWindow[i];
                position = i;
            }


        }
        long[] minAndIndex = new long[2];
        minAndIndex[0] = tmp;
        minAndIndex[1] = position;
        return minAndIndex;

    }
    //helper
    private static ArrayList<int[]> rightMaxAndIndexFromMap(ArrayList<int[]> value){
        int tmp = value.get(0)[0];
        ArrayList<int[]> valueElements = new ArrayList<>();

        valueElements.add(value.get(0));
        for(int i = 1; i < value.size(); i++){
            if(tmp < value.get(i)[0]) {
                tmp = value.get(i)[0];
                valueElements.clear();
                valueElements.add(value.get(i));

            }else if(tmp == value.get(i)[0]){
                valueElements.add(value.get(i));
            }

        }
        return valueElements;
    }

    public static ArrayList<long[]> getFingerprintArr(long[] hashArray) {
        int windowSize = t-k+1;
        int windowNum = 0;
        if (windowSize>hashArray.length) return null;
        windowNum = hashArray.length-t+k;
        long[][] windowArray = new long[windowNum][];
        for(int i = 0; i < windowNum; i++){
            windowArray[i] = new long[windowSize];
            for (int j = 0; j<windowSize; j++){
                windowArray[i][j] = hashArray[i+j];

            }
        }
        HashMap<Long, ArrayList<int[]>> map = new HashMap<>();
        for(int i = 0; i < windowNum; i++){

            long[] minIntPair = getMinAndIndex(windowArray[i]);
            if (map.containsKey(minIntPair[0])) {
                map.get(minIntPair[0]).add(new int[]{(int) minIntPair[1], i});


            }
            else {
                ArrayList<int[]> list = new ArrayList<>();
                list.add(new int[]{(int) minIntPair[1], i});

                map.put(minIntPair[0], list);
            }

        }



//        System.out.println(Arrays.deepToString(windowArray));
        //iterate through the map to construct final arraylist
        ArrayList<long[]> fpPositionPair = new ArrayList<>();



        for (Map.Entry<Long, ArrayList<int[]>> entry : map.entrySet()) {
            long key = entry.getKey();
            ArrayList<int[]> value = entry.getValue();
            ArrayList<int[]> qualifiedVal = rightMaxAndIndexFromMap(value);
            for(int[] pair: qualifiedVal){
                fpPositionPair.add(new long[]{key,(long)(pair[0]+pair[1]) });
            }

//            System.out.println("keyAfter, " + key + " valueAfter " + Arrays.deepToString(qualifiedVal.toArray()));


            // ...
        }
        Collections.sort(fpPositionPair, new Comparator <long[]>() {
            public int compare(long [] entry1, long [] entry2) {
                long hash1 = entry1[0];
                long hash2 = entry2[0];
                Long obj1 = new Long(hash1);
                Long obj2 = new Long(hash2);
                return obj1.compareTo(obj2);
            }
        });
//        System.out.println("finger print of this document is "+ Arrays.deepToString(fpPositionPair.toArray()));

        for (Map.Entry<Long, java.util.ArrayList<int[]>> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            java.util.ArrayList<int[]> value = entry.getValue();
//            System.out.println("key, " + key + " value " + Arrays.deepToString(value.toArray()));
        }
        return fpPositionPair;

    }
    //
//        public static ArrayList<long[]> ascendingFingerprint (ArrayList<long[]> fpPositionPair) {
//            ArrayList<long[]> ascendingFp = new ArrayList<>();
//            for(long[] pair:fpPositionPair){
//                ascendingFp.add(pair[0]
//            }
//
//
//        }
    public static ArrayList<long[]> winnowing(String txt) {
        long[] hasharray = populateHashes(preprocess_text(txt));
//            int j = 0;
//            for (long i:hasharray) {
//                System.out.println(j+" j"+ i);
//                j++;
//            }
        ArrayList<long[]> fingerPrintWithPosition = getFingerprintArr(hasharray);
        if (fingerPrintWithPosition == null) return null;




        return fingerPrintWithPosition;
    }

    public static MyResult similarity(ArrayList<long[]> txt1Hash, ArrayList<long[]> txt2Hash){
        int i = 0;
        int j = 0;
        ArrayList<long[]> matchingHash= new ArrayList<>();

        while (i!= txt1Hash.size() && j!= txt2Hash.size()){
            if (txt1Hash.get(i)[0] < txt2Hash.get(j)[0]) {
                i++;

            }
            else if(txt1Hash.get(i)[0] > txt2Hash.get(j)[0]){
                j++;
            }
            else{
                matchingHash.add(new long[]{txt1Hash.get(i)[0],txt1Hash.get(i)[1],txt2Hash.get(j)[1]});
                j++;
                i++;
            }

        }

        MyResult result = new MyResult(2*(float)matchingHash.size()/(txt1Hash.size()+txt2Hash.size()),matchingHash);
//            System.out.println("similarity is" + result.getFirst());
        return result;

    }
//        /**
//         * byte转换为16进制
//         */
//        public static void printChart(byte[] bytes){
//            for(int i = 0 ; i < bytes.length ; i++){
//                String hex = Integer.toHexString(bytes[i] & 0xFF);
//                if (hex.length() == 1) {
//                    hex = '0' + hex;
//                }
//                System.out.print(hex.toUpperCase() + " ");
//            }
//            System.out.println("");
//        }



    public static long[] chartoDecimalArr(char[] chars){
        long[] decimals = new long[chars.length];
        for(int i = 0 ; i < chars.length ; i++){
            String hexString = Integer.toHexString(chars[i]);
            long num = Long.parseLong(hexString, 16);
            decimals[i] = num;

        }
        return decimals;

    }
    //print out the matching substring
    public static ArrayList<String> matchingStrings(String text1, String text2, ArrayList<long[]> matchingFingerprint){
        text1 = preprocess_text(text1);
        text2 = preprocess_text(text2);
        String a = "";
//            System.out.println("text 1" + text1);
//            System.out.println("text 2" + text2);



        ArrayList<String> matchingString = new ArrayList<>();


        for(long [] smallArr : matchingFingerprint){
            int i = 0;
            int start1 = (int) smallArr[1];
            int end1 = (int) smallArr[1]+k;
            int start2 = (int) smallArr[2];
            int end2 = (int) smallArr[2]+k;
            String substring1 = "";
            String substring2 = "";


            while(i < t-k+2 && end1+i <= text1.length() && end2+i <= text2.length()){
                substring1 = text1.substring(start1,end1+i);
                substring2 = text2.substring(start2,end2+i);

                if(substring1.equals(substring2)) {
                    if(end1+i == text1.length() ||end2+i == text2.length() ) break;
                    else i++;
                }
                else break;

            }

            if(end1+i != text1.length() ||end2+i != text2.length()) a = substring1.substring(0,substring1.length()-1);
            else a = substring1.substring(0,substring1.length());
            matchingString.add(a);

        }
//            System.out.println("matching substring is "+ Arrays.deepToString(matchingString.toArray()));

        return matchingString;
    }

    //
//        public static ArrayList<String> readCSV(String filename){
//            ArrayList<String> contentElements = new ArrayList<>();
//            try {
//
//                CsvReader products = new CsvReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
//                products.readHeaders();
//                while (products.readRecord())
//                {
//
//                    String content = products.get("content");
//                    //if only consists non chinese character, insert blank
//                    if(content.matches("\\A\\p{ASCII}*\\z")||!ifmetMinimumLenChin(content,t-k)) {
//                        contentElements.add(" ");
//                    }
//                    else contentElements.add(content);
//
//                }
//
//
//
//                products.close();
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return contentElements;
//        }
    public static void main(String[] args) {
        String text1 ="Sanders have gone beyond dispassionate ideological disagreement and have exposed a deeper professional, political and personal hostility toward the senator and his campaign.";
        String text2 = "Their criticisms of Senator Sanders have gone beyond dispassionate ideological disagreement and have exposed a deeper professional, political and personal hostility toward the Senator and his Campaign.";
//            String text2 = text1;
//            ArrayList<String> textArr= readCSV("/Users/Yilun/Desktop/newspaper.csv",k,t);
//            String text1 = " I love you froggie im rabbiee and i want you";
//            String text2 = "froggie im rabbiee i like dazhonghua";
//            String text1 = "i love froggie";
//            String text2 = "i love froggie";

        ArrayList<long[]> fp1 = winnowing(text1);
        ArrayList<long[]> fp2 = winnowing(text2);
        float similar = similarity(fp1, fp2).getFirst();
        ArrayList<long[]> matchingHash = similarity(fp1, fp2).getSecond();
        System.out.print("aaaaa"+Arrays.deepToString(matchingHash.toArray()));


        ArrayList<String> strArr = matchingStrings(text1,text2,matchingHash);
        String content = "matching substring is "+ Arrays.deepToString(strArr.toArray());
        System.out.print(content);



//            try {
//
//
//                File file = new File("/Users/Yilun/Desktop/matchingStrings.txt");
//
//                // if file doesnt exists, then create it
//                if (!file.exists()) {
//                    file.createNewFile();
//                }
//
//                FileWriter fw = new FileWriter(file.getAbsoluteFile());
//                BufferedWriter bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//
//                System.out.println("Done writing to file");
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println("similarity is "+ similar);
//            System.out.println("matching substring is "+ Arrays.deepToString(strArr.toArray()));





//            for (int i = 0; i< textArr.size();i++) {
//                if(textArr.get(i)== " ") {
//                    continue;
//                }
//                ArrayList<long[]> fp1 = winnowing(textArr.get(17), t, k);
//                for (int j = 0; j < textArr.size(); j++) {
////                    System.out.println(textArr.get(j));
//                    if(textArr.get(j) == " ") {
//                        continue;
//                    }
//                    System.out.println("now processing at row " + "," +j );
//                    ArrayList<long[]> fp2 = winnowing(textArr.get(j), t, k);
//                    float similar = similarity(fp1, fp2);
//                    if (similar == 1.0) {
//                        continue;
//                    }
//                    if (similar >=0.02) {
//                        System.out.println("Detect possible plagirism, similarity of fp1 and fp2 is " + similar +"at row "+"17"+","+j);
//                    }
//                }
    }


}










