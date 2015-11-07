package com.example.luca.androidapp;

import android.app.Application;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Global extends Application
{
    private String ip_spf_controller="192.168.43.175";//"10.16.3.70";//"10.0.2.2"; //"127.0.0.1";
    private int port_spf_controller=50000;
    private int port_result=56487;

    public int get_port_spf_controller()  {return port_spf_controller;}
    public void set_port_spf_controller(int p) {  this.port_spf_controller = p;}

    public String get_ip_spf_controller() {return ip_spf_controller;}
    public void set_ip_spf_controller(String ip) {  this.ip_spf_controller = ip;}

    public int get_port_result() {return port_result;}
    public void set_port_result(int p) {  this.port_result = p;}


    public String getIPold()
    {
        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipAddress = Formatter.formatIpAddress(ip);
        return ipAddress;
    }

    public static String getIP()
    {
       String ipv4;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    System.out.println("ip1--:" + inetAddress);
                    System.out.println("ip2--:" + inetAddress.getHostAddress());

                    // for getting IPV4 format
                    if (!inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipv4 = inetAddress.getHostAddress())) {
                        String ip = inetAddress.getHostAddress().toString();
                        System.out.println("ip---::" + ip);

                        // return inetAddress.getHostAddress().toString();
                        return ipv4;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("IP Address"+ ex.toString());
        }
        return "IP unknown";
    }

}
