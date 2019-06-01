import java.nio.*;
import java.util.*;

public class ByteBufferUtility {

    public static void initByteBuffer(ByteBuffer buffer) {
        int capacity = buffer.capacity();
        byte[] bytes = new byte[capacity];

        Random random = new Random(0);
        random.nextBytes(bytes);

        buffer.position(0);
        buffer.put(bytes);

        buffer.position(0);
    }

    public static void printByteBuffer(ByteBuffer buffer) {
        ByteBuffer dupBuffer = buffer.duplicate();

        dupBuffer.position(0);

        dupBuffer.limit(dupBuffer.capacity());

        for (int i = 0; i <= dupBuffer.capacity(); i++) {
            if (i == buffer.position()) {
                System.out.print("P");
                if (i == buffer.limit()) {
                    System.out.print("L");
                    if (i == buffer.capacity()) {
                        System.out.print("C");
                    } else {
                        System.out.print(" ");
                    }
                } else {
                    System.out.print(" ");
                }

                System.out.print("  ");
            } else if (i == buffer.limit()) {
                System.out.print("L");
                if (i == buffer.capacity()) {
                    System.out.print("C");
                } else {
                    System.out.print(" ");
                }
                System.out.print("  ");
            } else if (i == buffer.capacity()) {
                System.out.print("C");
            } else {
                System.out.print("    ");
            }
        }
        System.out.println();
        System.out.print(" [");
        for (int i = 0; i < dupBuffer.capacity() - 1; i++) {
            int x = (int) dupBuffer.get();
            if (x >= 0 && x < 0x10) {
                System.out.print("0");
            }
            System.out.print(Integer.toHexString(x & 0xff) + ", ");
        }

        int x = (int) dupBuffer.get();
        if (x >= 0 && x < 0x10) {
            System.out.println("0");
        }
        System.out.println(Integer.toHexString(x & 0xff) + "]");
    }
}
