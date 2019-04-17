package cn.huangkanglin.udp.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @ClassName Server
 * @Description udp服务类
 * @Author hkl
 * @Date 2019/4/17 10:20
 **/
public class Server {

    public static void main(String[] args) throws Exception {
        String ip = "";
        int prot = 0;

        String str = "";
        String message = "";
        byte[] bytes = new byte[1024];

        DatagramSocket ds = new DatagramSocket(8888);
        DatagramPacket dp = new DatagramPacket(bytes, 0, bytes.length);

        /**包含IP地址及主机名*/
        SocketAddress socketAddress = null;
        /**在InetAdress的基础上加入端口*/
        InetSocketAddress inetSocketAddress = null;

        while (true) {
            System.out.println("wait...");
            ds.receive(dp);
            System.out.println("success");

            message = new String(dp.getData(), 0, dp.getLength());
            System.out.println("message=" + message);

            // 获取网络地址
            inetSocketAddress = (InetSocketAddress) dp.getSocketAddress();
            prot = inetSocketAddress.getPort();
            ip = inetSocketAddress.getAddress().getHostAddress();

            if (message.startsWith("getIp")) {
                str = prot + "," + ip;
                byte tmpBytes[] = str.getBytes();
                dp.setData(tmpBytes, 0, tmpBytes.length);
                ds.send(dp);
                System.out.println(prot + "," + ip);
            }

            if (!message.equals("getIp")) {
                str = "udp:ip=" + ip + "prot=" + prot + "massage=" + message;
                byte[] reply = str.getBytes();
                dp.setData(reply,0,reply.length);
                ds.send(dp);
                System.out.println("send massage="+str);
            }
            dp.setData(bytes,0,bytes.length);
        }

    }

}
