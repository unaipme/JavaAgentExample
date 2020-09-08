import com.sun.tools.attach.VirtualMachine;

public class AttachMain {

    public static void main(String [] args) throws Exception {
        System.out.println("Running AttachMain with arguments: " + args[0] + " " + args[1]);
        VirtualMachine vm = VirtualMachine.attach(args[0]);
        try {
            vm.loadAgent(args[1]);
        } finally {
            vm.detach();
        }
    }

}
