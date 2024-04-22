
public class Main {

    public static void main(String[] args) {

       TestNetworkGraph test = new TestNetworkGraph();

       int successes = 0;
       for(int i = 0; i < 100; i++)
           //Provide values in kilobytes
           successes += test.test(149, 150, 101 - i);

       System.out.println(successes);
    }
}