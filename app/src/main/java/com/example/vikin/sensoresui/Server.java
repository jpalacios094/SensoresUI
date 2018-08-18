package com.example.vikin.sensoresui;

import android.os.AsyncTask;
import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class Server extends Thread{
	MainActivity activity;
    int cont=0;
	//TempFragment fragment;
    //Socket socket;
    ServerSocket serverSocket;
	int envia = 0;
	String message = "";
	String recibe = "";
	static final int socketServerPORT = 8080;

	public Server(MainActivity activity) {
		this.activity = activity;
		//this.fragment = fragment;

        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();
      //  ServerAsyncTask serverAsyncTask = new ServerAsyncTask();
        //Start the AsyncTask execution
        //Accepted client socket object will pass as the parameter
       // (()) serverAsyncTask.execute();
          //Thread socketServerThread2 = new Thread(new SocketServerThread());
        //socketServerThread.start();
	}

	public int getPort() {
		return socketServerPORT;
	}
    /*class ServerAsyncTask extends AsyncTask<Void, Integer, String>{
        BufferedReader b;
        ServerSocket serverSocket;
        @Override
        protected String doInBackground(Void... voids) {
            try {
                serverSocket = new ServerSocket(socketServerPORT);
                while(true){
                    socket = serverSocket.accept();
                    activity.info.setText("conexion recibida"+socket);
                    b = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }*/
	/*public void onDestroy() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/

	public class SocketServerThread extends Thread {

		int count = 0;
        //Socket socket;
		BufferedReader b;
        ServerSocket serverSocket;
        /*public SocketServerThread(){
            try {
                serverSocket = new ServerSocket(socketServerPORT);
                while(true){
                    socket = serverSocket.accept();
                    activity.info.setText("conexion recibida"+socket);
                    b = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

		@Override
		public void run() {
			try {
                serverSocket = new ServerSocket(socketServerPORT);

                while (true) {
                    // LISTEN FOR INCOMING CLIENTS
                    Socket socket = serverSocket.accept();
					Thread recibeMensajes = new Thread(new ClienTMessages(socket,activity));//Crea un nuevo hilo que escuche los mensajes de los clientes
					recibeMensajes.start();
                }


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

	}


	/*private class SocketServerReplyThread extends Thread {

		private Socket hostThreadSocket;
		int cnt;

		SocketServerReplyThread(Socket socket, int c) {
			hostThreadSocket = socket;
			cnt = c;
		}

		@Override
		public void run() {
			OutputStream outputStream;
			String msgReply = "Mensaje Recibido";

			try {
				outputStream = hostThreadSocket.getOutputStream();
				PrintStream printStream = new PrintStream(outputStream);
				printStream.print(msgReply);
				//printStream.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message += "Something wrong! " + e.toString() + "\n";
			}

		}

	}*/

	public String getIpAddress() {
		String ip = "";
		try {
			Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (enumNetworkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = enumNetworkInterfaces
						.nextElement();
				Enumeration<InetAddress> enumInetAddress = networkInterface
						.getInetAddresses();
				while (enumInetAddress.hasMoreElements()) {
					InetAddress inetAddress = enumInetAddress
							.nextElement();

					if (inetAddress.isSiteLocalAddress()) {
						ip += "Server running at : "
								+ inetAddress.getHostAddress();
					}
				}
			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ip += "Something Wrong! " + e.toString() + "\n";
		}
		return ip;
	}
}

