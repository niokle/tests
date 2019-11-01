
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import org.usb4java.javax.*;
import org.usb4java.javax.Services;

import javax.usb.*;
import javax.usb.util.DefaultUsbIrp;
import javax.xml.ws.Service;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(final String[] args) throws UsbException, UnsupportedEncodingException {
        // Get the USB services and dump information about them
        final Services services = new Services();
        List<UsbDevice> usbDevices = services.getRootUsbHub().getAttachedUsbDevices();
        for (UsbDevice usbDevice : usbDevices) {
            if (usbDevice.getUsbDeviceDescriptor().idVendor() == 6790 && usbDevice.getUsbDeviceDescriptor().idProduct() == 29987) {
                System.out.println("-----------------------------------");
                System.out.println(usbDevice.getUsbDeviceDescriptor().idProduct());
                System.out.println(usbDevice.getUsbDeviceDescriptor().idVendor());
                System.out.println(usbDevice.getUsbDeviceDescriptor().bcdDevice());
                System.out.println(usbDevice.getUsbDeviceDescriptor().bcdUSB());
                System.out.println(usbDevice.getUsbDeviceDescriptor().bDeviceClass());
                System.out.println(usbDevice.getUsbDeviceDescriptor().bDeviceProtocol());
                System.out.println(usbDevice.getUsbDeviceDescriptor().bDeviceSubClass());
                System.out.println(usbDevice.getUsbDeviceDescriptor().bMaxPacketSize0());
                System.out.println(usbDevice.getUsbDeviceDescriptor().bNumConfigurations());
                System.out.println(usbDevice.getUsbDeviceDescriptor().iManufacturer());
                System.out.println(usbDevice.getUsbDeviceDescriptor().iProduct());
                System.out.println(usbDevice.getUsbDeviceDescriptor().iSerialNumber());
                System.out.println("-----------------------------------");
                System.out.println(usbDevice.getProductString());
                System.out.println(usbDevice.getManufacturerString());
                System.out.println(usbDevice.getActiveUsbConfiguration());
                System.out.println("-----------------------------------");
                System.out.println(usbDevice.getParentUsbPort());
                System.out.println(usbDevice.getParentUsbPort().getPortNumber());
                System.out.println(usbDevice.getParentUsbPort().getUsbDevice());
                System.out.println(usbDevice.getParentUsbPort().getUsbHub());
                System.out.println(usbDevice.getParentUsbPort().isUsbDeviceAttached());;
                System.out.println("-----------------------------------");
                System.out.println(usbDevice.getSerialNumberString());
                System.out.println("-----------------------------------");
                System.out.println(usbDevice.getSpeed());
                System.out.println("-----------------------------------");
                System.out.println(usbDevice.getUsbConfigurations());
                System.out.println(usbDevice.isConfigured());
                System.out.println(usbDevice.isUsbHub());
                System.out.println("-----------------------------------");
                System.out.println();

                UsbConfiguration usbConfiguration = usbDevice.getActiveUsbConfiguration();
                UsbInterface usbInterface = usbConfiguration.getUsbInterface((byte) 0);
                usbInterface.claim();
                List<UsbEndpoint> usbEndpoints = usbInterface.getUsbEndpoints();
                UsbPipe usbPipe;
                byte[] output = new byte[64];
                int resultCode;
                UsbIrp usbIrp;
                for (UsbEndpoint usbEndpoint : usbEndpoints) {
                    usbPipe = usbEndpoint.getUsbPipe();
                    usbPipe.open();
                    resultCode = usbPipe.syncSubmit(output);
                    //usbIrp = usbPipe.asyncSubmit(output);
                    System.out.println("########### " + usbPipe + " ##############");
                    for (byte b : output) {
                        System.out.print(b + " ");
                    }
                    System.out.println();
                    System.out.println("########## " + resultCode + " ###############");
                    //System.out.println("########## " + usbIrp.getLength() + " ###############");
                    usbPipe.close();
                }
                usbInterface.release();
            }
        }

        /* @@@@@@@ low level usb4java
        Context context = new Context();
        LibUsb.init(context);
        DeviceList devices = new DeviceList();
        DeviceDescriptor deviceDescriptor = new DeviceDescriptor();
        LibUsb.getDeviceList(context, devices);
        int result = 0;
        for (Device device : devices) {
            test(device);

        }

        LibUsb.exit(context);
        @@@@@@@ low level usb4java */

        /*
            result = LibUsb.getDeviceDescriptor(device, deviceDescriptor);
            System.out.println(LibUsb.getBusNumber(device));
            System.out.println(LibUsb.getDeviceAddress(device));
            System.out.println(LibUsb.getDeviceSpeed(device));
            System.out.println(LibUsb.getPortNumber(device));
            System.out.println(LibUsb.getParent(device));
            System.out.println(LibUsb.getDeviceAddress(device));
            System.out.println("-----------------------------------");
            System.out.println(deviceDescriptor.idProduct());
            System.out.println(deviceDescriptor.idVendor());
            System.out.println(deviceDescriptor.iManufacturer());
            System.out.println(deviceDescriptor.iSerialNumber());
            //System.out.println(deviceDescriptor.getBuffer().array().length);
            System.out.println();

             */

        /*
        System.out.println("USB Service Implementation: "
                + services.getImpDescription());
        System.out.println("Implementation version: "
                + services.getImpVersion());
        System.out.println("Service API version: " + services.getApiVersion());
        System.out.println();


        //show all devices
        for (Object object : services.getRootUsbHub().getAttachedUsbDevices()) {
            UsbDevice usbDevice = (UsbDevice) object;
            //System.out.println(usbDevice.getUsbDeviceDescriptor().toString());
            System.out.println("Vendor Id: " + usbDevice.getUsbDeviceDescriptor().idVendor());
            System.out.println("Product Id: " + usbDevice.getUsbDeviceDescriptor().idProduct());
            System.out.println();
        }
        System.out.println();
        */

        /*
        UsbTest usbTest = new UsbTest();
        short vendorId;
        short productId;
        //SDS011
        //vendorId = 6790;
        //productId = 29987;
        //mouse
        vendorId = 5426;
        productId = 52;

        UsbDevice usbDevice = usbTest.findDevice(services.getRootUsbHub(), vendorId, productId);
        //System.out.println(usbDevice);
        xxxxx(usbDevice);

         */

    }

    /* @@@@@@@ low level usb4java
    private static void test(Device device) {
        DeviceDescriptor deviceDescriptor = new DeviceDescriptor();
        int result = LibUsb.getDeviceDescriptor(device, deviceDescriptor);
        DeviceHandle deviceHandle = new DeviceHandle();
        LibUsb.

        if (deviceDescriptor.idVendor() == 6790 && deviceDescriptor.idProduct() == 29987) {
            System.out.println("##### start #####");
            Buffer buffer = deviceDescriptor.getBuffer();
            for (int l = 0; l <= buffer.capacity(); l++) {
                System.out.println(deviceDescriptor.getBuffer().get(l));
            }

            System.out.println("##### end #####");
        }
    }

     @@@@@@@ low level usb4java */

    private static void xxxxx(UsbDevice usbDevice) throws UsbException {
        if (usbDevice != null) {
            //
            UsbConfiguration configuration = usbDevice.getActiveUsbConfiguration();
            UsbInterface iface = configuration.getUsbInterface((byte) 0);
            iface.claim();
            //todo
            try
            {
                UsbEndpoint endpoint1 = (UsbEndpoint) iface.getUsbEndpoints().get(0);
                UsbPipe pipe1 = endpoint1.getUsbPipe();
                pipe1.open();
                UsbEndpoint endpoint2 = (UsbEndpoint) iface.getUsbEndpoints().get(1);
                UsbPipe pipe2 = endpoint2.getUsbPipe();
                pipe2.open();
                UsbEndpoint endpoint3 = (UsbEndpoint) iface.getUsbEndpoints().get(2);
                UsbPipe pipe3 = endpoint3.getUsbPipe();
                pipe3.open();
                try
                {
                    byte[] data1 = new byte[8];
                    int received1 = pipe1.syncSubmit(data1);
                    System.out.print(received1 + " bytes received @@@ ");
                    for (byte b : data1) {
                        System.out.print(b + " ");
                    }
                    System.out.println();

                    byte[] data2 = new byte[8];
                    UsbIrp usbIrp = pipe1.createUsbIrp();
                    data2 =  usbIrp.getData();
                    for (byte b : data2) {
                        System.out.print(b + " ");
                    }
                    System.out.println();

                    byte[] data3 = new byte[4];
                    int received3 = pipe3.syncSubmit(data3);
                    System.out.print(received3 + " bytes received @@@ ");
                    for (byte b : data3) {
                        System.out.print(b + " ");
                    }
                    System.out.println();

                    byte[] data4 = new byte[8];
                    UsbIrp received4 = pipe1.asyncSubmit(data4);
                    System.out.print(received4.getData() + " bytes received @@@ ");
                    for (byte b : data4) {
                        System.out.print(b + " ");
                    }
                    System.out.println();
                }
                finally
                {
                    pipe1.close();
                    pipe2.close();
                    pipe3.close();
                }
            }
            finally
            {
                iface.release();
            }
        }
        //xxxxx(usbDevice);
    }
}
