import com.fazecast.jSerialComm.SerialPort;

public class Serial {
    public static void main(String[] args) {
        long[] data;
        Serial serial = new Serial();
        for (int i = 0 ; i < 100 ; i++) {
            data = serial.getLevel();
            System.out.println("PM 2.5 yg/m3: " + data[0] + ", PM 10 yg/m3: " + data[1]);
        }
    }

    private long[] getLevel() throws NullPointerException {
        byte[] data = filterData();
        long[] result = new long[2];
        result[0] = ((data[3] * 256) + data[2]) / 10;
        result[1] = ((data[5] * 256) + data[4]) / 10;
        return result;
    }

    private byte[] filterData() {
        byte[] data = getData();
        byte resultByte0 = -86;
        byte resultByte1 = -64;
        int resultByte8 = data[2] + data[3] + data[4] + data[5] + data[6] + data[7];
        byte resultByte9 = -85;
        if (data[0] == resultByte0 && data[1] == resultByte1 && data[8] == resultByte8 && data[9] == resultByte9) {
            return data;
        }
        return null;
    }

    private byte[] getData() {
        byte[] data = new byte[10];
        for (SerialPort serialPort : SerialPort.getCommPorts()) {
            if (serialPort.getSystemPortName().equals("tty.usbserial-1410")) {
                serialPort.openPort();
                serialPort.readBytes(data, 10);
                serialPort.closePort();
            }
        }
        printData(data);
        return data;
    }

    private void printData(byte[] data) {
        for (byte b : data) {
            System.out.print(b + " ");
        }
        System.out.println();
    }
}
