import java.lang.instrument.Instrumentation;

public class Agent {

    // private static Instrumentation instrumentation;

    public static void agentmain(String agentArgs, Instrumentation inst) {
        // instrumentation = inst;
        Thread.currentThread().setName("THIS IS THE AGENT THREAD");
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            System.out.println(t.getName());
        }
    }

}
