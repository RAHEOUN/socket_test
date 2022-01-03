
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import  java.io.PrintWriter;
import java.net.Socket;
/**
 *  다 대 다 연결을 지원하는 클라이언트
 *  수신발신을 담담하는 스레드 작성*/
/**
 * 메세지의 수신을 담당하는 스레드
 */
class ReceiverThread extends Thread {
    Socket socket;

    public ReceiverThread(Socket socket) { this.socket = socket;}

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String str = reader.readLine();
                if (str == null) {
                    break;
                }
                System.out.println(str);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


/**
 * 메시지의 발신을 담당하는 스레드
 */
class SenderThread extends Thread {
    Socket socket;
    String name;

    public SenderThread(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            //제일 먼저 서버로 대화명을 송신합니다.
            writer.println(name);
            writer.flush();

            while (true) {
                String str = reader.readLine();
                if (str.equals("bye")) {
                    break;
                }
                writer.println(str);
                writer.flush();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                //TODO Autoo-generated catch block
                e.printStackTrace();
            }
        }
    }
}

public class Client_n_n {
    public static void main(String[] args) {

        try {
            // 아이피와 포트입력
            Socket socket = new Socket("192.168.219.125", 2222);
            //닉네임을 적어준다
            Thread thread1 = new SenderThread(socket, "허운뀨!~~😎");
            Thread thread2 = new ReceiverThread(socket);

            thread1.start();
            thread2.start();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
