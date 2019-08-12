package jc.dev.docker.manager;

import com.jcraft.jsch.*;
import jc.dev.docker.config.SingleFileExecutionConfig;

import java.io.InputStream;
import java.io.OutputStream;

public class SSH {
    SingleFileExecutionConfig config;
    public SSH(SingleFileExecutionConfig config) {
        this.config = config;

        // init
        JSch.setConfig("StrictHostKeyChecking", "no");
    }

    public Session getSession() throws JSchException {
        JSch jsch = new JSch();
        jsch.addIdentity(config.getPrivateKey());
        Session session = jsch.getSession(config.getUser(), config.getHost(), 22);
        session.connect(5000);
        System.out.println(session.isConnected());
        return session;
    }

    public String execCommand(String command) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Session session = this.getSession();
            Channel channel = session.openChannel("exec");

            ((ChannelExec)channel).setCommand(command);

            InputStream in = channel.getInputStream();
            OutputStream out = channel.getOutputStream();
            ((ChannelExec)channel).setErrStream(System.err);

            channel.connect(5000);
            System.out.println(session.isConnected());

            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    stringBuilder.append(new String(tmp, 0, i));
                }
                if(channel.isClosed()){
                    System.out.println("exit-status: "+channel.getExitStatus());
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }

            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
