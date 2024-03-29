package comp1110.lectures.C03;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HashDemo extends Application {

    interface HashFunction<T> {
        int hash(T key, int buckets);  //(key, buckets) -> {return key % buckets;}

        // (key, buckets) -> {
        //            int rtn = 0;
        //            for (int i = 0; i < key.length(); i++) {
        //                rtn += key.charAt(i);
        //            }
        //            return rtn % buckets;
        //        }
    }

    ArrayList<Integer> uids = new ArrayList<Integer>();
    ArrayList<String> given = new ArrayList<String>();
    ArrayList<String> family = new ArrayList<String>();
    ArrayList<String> words = new ArrayList<String>();

    /**
     * Read in the data we will use for our hashing examples.
     *
     * @throws FileNotFoundException
     */
    void readData() throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("resources/data/uids.txt"));
        while (in.hasNextInt()) {
            uids.add(in.nextInt());
        }
        in.close();
        in  = new Scanner(new FileInputStream("resources/data/names-given.txt"));
        while (in.hasNext()) {
            given.add(in.next());
        }
        in.close();
        in  = new Scanner(new FileInputStream("resources/data/names-family.txt"));
        while (in.hasNext()) {
            family.add(in.next());
        }
        in.close();
        in  = new Scanner(new FileInputStream("resources/words/dictionary.txt"));
        while (in.hasNext()) {
            words.add(in.next());
        }
        in.close();
    }

    /**
     * Test out an integer hash function.
     *
     * @param name A name for this test
     * @param buckets The number of buckets we wish to hash into
     * @param values The set of values we will be hashing
     * @param hf An integer hash function
     * @param color A color to draw the result in
     */
    void integerHash(String name, int buckets, ArrayList<Integer> values, HashFunction<Integer> hf, Color color) {
        int[] bkts = new int[buckets];
        for(Integer i : values) {
            bkts[hf.hash(i, buckets)]++; // the value of corresponding bucket add 1
        }
        new Histogram(name, bkts, color).show();  // Histogram(name, bkts,color).show()  .show is very important
    }

    /**
     * Test out an string hash function.
     *
     * @param name A name for this test
     * @param buckets The number of buckets we wish to hash into
     * @param values The set of values we will be hashing
     * @param hf A String hash function
     * @param color A color to draw the result in
     */
    void stringHash(String name, int buckets, ArrayList<String> values, HashFunction<String> hf, Color color) {
        int[] bkts = new int[buckets];
        for(String i : values) {
            bkts[hf.hash(i, buckets)]++;
        }
        new Histogram(name, bkts, color).show();
    }

    /**
     * Test an integer hash, using one more more input data sets
     *
     * @param color The color for the histogram
     * @param hash The hash function
     */
    private void integerHash(Color color, HashFunction<Integer> hash) {

        integerHash("UIDs", 20, uids, hash, color);
    }

    /**
     * Test a String hash, using three different input data sets
     *
     * @param color The color for each histogram
     * @param hash The hash function
     */
    private void stringHash(Color color, HashFunction<String> hash) {
        // 此功能的目的是将 void stringHash 功能批量用于不同的文件， 对于每个文件生成的图表，
        // 赋予不同的 diagram name, bucket number, color
        stringHash("Given Names", 20, given, hash, color);
        stringHash("Family Names", 20, family, hash, color);
        stringHash("Dictionary", 20, words, hash, color);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        readData();

        /* hashing integers */
		//integerHash(Color.GREEN, (key, buckets) -> 0);

        HashFunction<Integer> inthash =  (key, buckets) -> {return key % buckets;};
        // three method to use the imterface HashFunction
        // 1   integerHash(Color.LIMEGREEN, inthash );
        // 2   integerHash(Color.YELLOWGREEN, (key, buckets) -> {return (key/10) % buckets;});
        // and the third is the following
		integerHash("UID", 20, uids, inthash, Color.LIMEGREEN);




        /* hashing strings */
        //stringHash(Color.RED, (key, buckets) -> 0);
        //stringHash(Color.PINK, (key, buckets) -> { return key.charAt(0) % buckets;});

        stringHash(Color.PURPLE, (key, buckets) -> {
            int rtn = 0;
            for (int i = 0; i < key.length(); i++) {
                rtn += key.charAt(i);
            }
            return rtn % buckets;
        });

        //stringHash(Color.ORANGE, (key, buckets) -> { return Math.abs(key.hashCode()) % buckets;});

    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        launch(args);
        // append a lumbda expression to create a new HashFunction testHash
        HashFunction<Integer> testHash = (key, buckets) -> {return key % buckets;};
        //call the method hash to get the return
        int testInt = testHash.hash(20,4);

        System.out.println(testInt);
    }

}

