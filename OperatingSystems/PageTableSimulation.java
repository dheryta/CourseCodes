import java.util.Scanner;

public class PageTableSimulator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Get logical address size and page size
        System.out.print("Enter total number of bits for logical address (m): ");
        int m = sc.nextInt();

        System.out.print("Enter number of bits for page offset (n): ");
        int n = sc.nextInt();

        int pageBits = m - n;
        int numPages = 1 << pageBits;  // 2^(m - n)
        int pageSize = 1 << n;         // 2^n

        int[] pageTable = new int[numPages];

        // Step 2: Input page table
        System.out.println("Enter frame number for each page (0 to " + (numPages - 1) + "):");
        for (int i = 0; i < numPages; i++) {
            System.out.print("Page " + i + " -> Frame: ");
            pageTable[i] = sc.nextInt();
        }

        // Step 3: Get logical address from user
        int maxAddress = (1 << m) - 1;
        System.out.print("Enter logical address (0 to " + maxAddress + "): ");
        int logicalAddress = sc.nextInt();

        // Step 4: Perform translation
        int pageNumber = logicalAddress >> n;
        int offset = logicalAddress & ((1 << n) - 1);
        int frameNumber = pageTable[pageNumber];
        int physicalAddress = (frameNumber << n) | offset;

        // Step 5: Display results
        System.out.println("\n--- Translation Result ---");
        System.out.println("Logical Address: " + logicalAddress);
        System.out.println("Page Number: " + pageNumber);
        System.out.println("Offset: " + offset);
        System.out.println("Frame Number (from page table): " + frameNumber);
        System.out.println("Physical Address: " + physicalAddress);

        sc.close();
    }
}
