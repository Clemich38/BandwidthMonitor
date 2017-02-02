package com.oneact.bandwidthmonitor;



import java.util.Arrays;
import java.util.Random;

import jpcap.PacketReceiver;
import jpcap.packet.DatalinkPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

public class PacketCatcher implements PacketReceiver
{

    public static float downloadedDataSize = 0;
    public static float uploadedDataSize = 0;
    public static int downloadedPacketNb = 0;
    public static int uploadedPacketNb = 0;
    public static float tmpSize = 0;
    public static int tmpNb = 0;
    public static byte[] macAddress = {0, 0, 0, 0, 0, 0};
    
	//this method is called every time Jpcap captures a packet
	public void receivePacket(Packet packet)
	{
	    //just print out a captured packet
//	    downloadedDataSize += packet.len;
//	    System.out.println("dowloadedSize: " + downloadedDataSize);
	    
	    DatalinkPacket dp = packet.datalink;
	    EthernetPacket ept=(EthernetPacket)dp;
	    
	    byte[] destAddress = ept.dst_mac;
	    System.out.println("dest addr: " + destAddress);
	    
	    if(Arrays.equals(destAddress, macAddress) == true)
	    {
	    	downloadedDataSize += packet.len;
	    	downloadedPacketNb ++;
	    	System.out.println("downloadedSize: " + downloadedDataSize);
	    }
	    else
	    {
	    	uploadedDataSize += packet.len;
	    	uploadedPacketNb ++;
	    	System.out.println("uploadedSize: " + uploadedDataSize);
	    }
	}

    public static float getDownloadedDataSize()
    {
        tmpSize = downloadedDataSize;
        downloadedDataSize = 0;
        return tmpSize;
        
//        Random r = new Random();
//        int Result = r.nextInt(300);
//        return (float) Result;
    }

    public static float getUploadedDataSize()
    {
        tmpSize = uploadedDataSize;
        uploadedDataSize = 0;
        return tmpSize;
        
//        Random r = new Random();
//        int Result = r.nextInt(100);
//        return (float) Result;
    }

    public static int getDownloadedPacketNb()
    {
        tmpNb = downloadedPacketNb;
        downloadedPacketNb = 0;
        return tmpNb;
    }

    public static int getUploadedPacketNb()
    {
        tmpNb = uploadedPacketNb;
        uploadedPacketNb = 0;
        return tmpNb;
    }

    public static void setMacAddress(byte[] addr)
    {
    	System.arraycopy(addr, 0, macAddress, 0, 6);
    }

}