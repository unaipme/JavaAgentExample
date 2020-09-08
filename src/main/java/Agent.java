import java.lang.instrument.Instrumentation;

public class Agent {

    // private static Instrumentation instrumentation;

    public static void agentmain(String agentArgs, Instrumentation inst) {
        // instrumentation = inst;
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            System.out.println(t.getName());
        }
    }

}
