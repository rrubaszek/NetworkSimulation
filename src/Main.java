
public class Main {

    public static void main(String[] args) {

       TestNetworkGraph test = new TestNetworkGraph();

       int successes = 0;
       int delta = 0;
       for(int i = 0; i < 10000; i++) {
           //Provide values in kilobytes
           successes += test.test(100, 120, delta);
            if(i % 100 == 0) {
                delta++;
            }
       }

       System.out.println(successes);
    }
}