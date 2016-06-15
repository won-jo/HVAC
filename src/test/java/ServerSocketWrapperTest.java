import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.function.Function;

import static junit.framework.TestCase.*;

public class ServerSocketWrapperTest {
    private static final int PORT = 5000;
    private static final String HOST = "localhost";
    private ServerSocketWrapper wrapper;

    @Before
    public void setUp() {
        wrapper = new ServerSocketWrapper();
    }

    @After
    public void tearDown() throws IOException, InterruptedException {
    	wrapper.stop();
    	Thread.sleep(100);
    }

    @Test
    public void itStartsAndStopsAServer() throws IOException, InterruptedException {
        Function<String, String> dummyRouter = string -> "";
        startServerSocket(dummyRouter);

        try(Socket socketThatWorks = new Socket(HOST, PORT)) {
            assertTrue(socketThatWorks.isConnected());
        }

        wrapper.stop();
        try {
            new Socket(HOST, PORT);
            fail("Socket should not connect when there is no server running");
        } catch (ConnectException ignored) {

        }
    }

    @Test
    public void itAcceptsDataOverTheConnectionAndRoutesIt() throws IOException, InterruptedException {
        final String[] sentData = new String[1];
        Function<String, String> router = string -> {
            sentData[0] = string;
            return "";
        };
        startServerSocket(router);

        Socket socket = new Socket(HOST, PORT);
        sendDataToSocket(socket, "data");

        waitForDataToBePresent(sentData[0]);

        assertEquals("data", sentData[0]);
    }

    @Test
    public void itWritesBackWhatTheRouterReturns() throws IOException, InterruptedException {
        Function<String, String> router = string -> "returned data\n";
        startServerSocket(router);

        Socket socket = new Socket(HOST, PORT);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sendDataToSocket(socket, "irrelevant");

        socket.setSoTimeout(10);
        String readData = reader.readLine();

        assertEquals("returned data", readData);
    }
    
//    @Test
//    public void increaseHighTemperatureValue() throws UnknownHostException, IOException {
//    	HVAC hvac = new HVACMock();
//    	Function<String, String> router = command -> {
//    		CommandParser parser = new CommandParser(hvac);
//    		parser.parse(command);
//    		String value =  parser.getHighValue().toString();
//    		return value + "\n";
//    	};
//    	
//    	startServerSocket(router);
//    	
//    	Socket socket = new Socket(HOST, PORT);
//    	BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        sendDataToSocket(socket, "set_high 80");
//        
//        socket.setSoTimeout(50);
//        String readData = reader.readLine();
//        
//        assertEquals(80, Integer.parseInt(readData));
//    }
    
    
//    @Test
//    public void decreaseLowTemperatureValue() throws UnknownHostException, IOException {
//    	HVAC hvac = new HVACMock();
//    	Function<String, String> router = command -> {
//    		CommandParser parser = new CommandParser(hvac);
//    		parser.parse(command);
//    		String value =  parser.getLowValue().toString();
//    		return value + "\n";
//    	};
//    	
//    	startServerSocket(router);
//    	
//    	Socket socket = new Socket(HOST, PORT);
//    	BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        sendDataToSocket(socket, "set_low 60");
//        
//        socket.setSoTimeout(50);
//        String readData = reader.readLine();
//        
//        assertEquals(60, Integer.parseInt(readData));
//    }
 
    private void waitForDataToBePresent(String s) throws InterruptedException {
        int retries = 0;
        while (s == null && retries < 5) {
            retries++;
            Thread.sleep(100);
        }
    }

    private void sendDataToSocket(Socket socket, String data) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(data);
        out.flush();
    }

    private void startServerSocket(Function<String, String> router) throws InterruptedException {
        new Thread() {
            public void run() {
                try {
                    wrapper.start(router, PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        
        Thread.sleep(100);
    }
}
