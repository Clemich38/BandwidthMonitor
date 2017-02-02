package com.oneact.bandwidthmonitor;

import java.io.IOException;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

public class PacketLookupThread extends Thread
{
	private JpcapCaptor captor = null;
	private int interfaceIndex = 0;
	
    @Override
    public void run()
    {
		//Obtain the list of network interfaces
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		int index = interfaceIndex;
		System.out.println("Openning device: "+devices[index].name + "(" + devices[index].description+")");
		
		try {
			captor = JpcapCaptor.openDevice(devices[index], 65535, false, 20);
		}
		catch (IOException e) {
			e.printStackTrace();
		}


		if(captor != null)
		{
			PacketCatcher.setMacAddress(devices[index].mac_address);
			System.out.println("Session started");
			captor.setPacketReadTimeout(1000);
			captor.loopPacket(-1, new PacketCatcher());
			System.out.println("Session stopped");
		}
    }

    public void setIndex(int index)
    {
    	interfaceIndex = index;
    }

    public void stopsession()
    {
        captor.breakLoop();
        captor.close();
    }
}
