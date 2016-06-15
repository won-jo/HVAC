
import java.io.IOException;
import java.util.function.Function;

public class HvacServer {
	
	private static ServerSocketWrapper wrapper;
	
	private static final int PORT = 5000;
	
	private static Function<String, String> router;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		int low = 65;
		int high = 75;
		
		if(args.length > 0) {
			low = Integer.parseInt(args[1]);
			high = Integer.parseInt(args[3]);
		}
		
		wrapper = new ServerSocketWrapper();
		
		HVACRouter r = new HVACRouter();
		
		router = r.getHvacRouter();
		
        startServerSocket(router);
        
        router.apply("set_high " + high);
        router.apply("set_low " + low);
	}
	
	private static void startServerSocket(Function<String, String> router) {
        new Thread() {
            public void run() {
                try {
                    wrapper.start(router, PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
