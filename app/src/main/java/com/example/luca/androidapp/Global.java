package com.example.luca.androidapp;

import android.app.Application;
import android.text.format.Formatter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Global extends Application
{
    private String ip_spf_controller="192.168.43.175";//"10.16.3.70";//"10.0.2.2"; //"127.0.0.1";
    private int port_spf_controller=50000;
    private int port_result=56487;
    private int waiting_time_for_response=7;

    public int get_port_spf_controller()  {return port_spf_controller;}
    public void set_port_spf_controller(int p) {  this.port_spf_controller = p;}

    public String get_ip_spf_controller() {return ip_spf_controller;}
    public void set_ip_spf_controller(String ip) {  this.ip_spf_controller = ip;}

    public int get_port_result() {return port_result;}
    public void set_port_result(int p) {  this.port_result = p;}

    public int get_waiting_time_for_response() {return waiting_time_for_response;}
    public void set_waiting_time_for_response(int p) {  this.waiting_time_for_response = p;}

    public static String getIP()
    {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    //System.out.println("$$$$$ "+Formatter.formatIpAddress(inetAddress.hashCode()));
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            System.out.println(ex.toString());
        }
        return "ip sconosciuto";
    }

}
