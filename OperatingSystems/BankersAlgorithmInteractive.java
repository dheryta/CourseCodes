import java.util.*;

public class BankersAlgorithmInteractive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Input system configuration
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        System.out.print("Enter number of resources: ");
        int m = sc.nextInt();

        int[][] allocation = new int[n][m];
        int[][] max = new int[n][m];
        int[][] need = new int[n][m];
        int[] available = new int[m];

        // Input Allocation Matrix
        System.out.println("Enter Allocation Matrix:");
        for (int i = 0; i < n; i++) {
            System.out.print("P" + i + ": ");
            for (int j = 0; j < m; j++) {
                allocation[i][j] = sc.nextInt();
            }
        }

        // Input Max Matrix
        System.out.println("Enter Max Matrix:");
        for (int i = 0; i < n; i++) {
            System.out.print("P" + i + ": ");
            for (int j = 0; j < m; j++) {
                max[i][j] = sc.nextInt();
            }
        }

        // Input Available Vector
        System.out.println("Enter Available Resources:");
        for (int j = 0; j < m; j++) {
            available[j] = sc.nextInt();
        }

        System.out.println("Need Matrix:");
        // Calculate Need Matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
                System.out.print(need[i][j] + " ");
            }
            System.out.println();
        }

        // Step 2: Request handling
        System.out.print("Enter process number making request (0 to " + (n - 1) + "): ");
        int reqProcess = sc.nextInt();
        int[] request = new int[m];
        System.out.println("Enter request vector:");
        for (int j = 0; j < m; j++) {
            request[j] = sc.nextInt();
        }

        // Step 3: Validate request
        boolean valid = true;
        for (int j = 0; j < m; j++) {
            if (request[j] > need[reqProcess][j]) {
                valid = false;
                System.out.println("Request > Need " + request[j] +">" + need[reqProcess][j]);
                break;
            }else {
            	System.out.println("Request <= Need " + request[j] +"<=" + need[reqProcess][j] + " Condition 1 Met");
            }
            if (request[j] > available[j]) {
                valid = false;
                System.out.println("Request > Available " + request[j] +">" + available[j]);
                break;
            }else {
            	 System.out.println("Request <= Available " + request[j] +"<=" + available[j] + " Condition 2 Met");
            }
        }

        if (!valid) {
            System.out.println("Request cannot be granted: exceeds need or not enough resources.");
            return;
        }

        System.out.println("We will pretend to allocate by updating system state....");
        // Step 4: Pretend to allocate temporarily
        for (int j = 0; j < m; j++) {
            available[j] -= request[j];
            allocation[reqProcess][j] += request[j];
            need[reqProcess][j] -= request[j];
        }

        System.out.println("****Temporarily Updated System State****");
        System.out.println("Available = Available - Request:");
        for (int j = 0; j < m; j++) {
            System.out.print(available[j] + " "); 
        }
        System.out.println();
        
        System.out.println("Allocation = Allocation + Request:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
         
                System.out.print(allocation[i][j] + " ");
            }
            System.out.println();
        }
        
        System.out.println("Need = Need - Request:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
         
                System.out.print(need[i][j] + " ");
            }
            System.out.println();
        }
        
        // Step 5: Check if system is still in safe state
        boolean[] finish = new boolean[n];
        int[] work = Arrays.copyOf(available, m);
        List<Integer> safeSequence = new ArrayList<>();
        
        System.out.print("Work (it is a working copy of Available): ");
        for(int k=0;k<work.length;k++)
        System.out.print(work[k] + " ");
        
        System.out.println("\nFinding a process whose need is less than work and is not yet finished....");
        while (safeSequence.size() < n) {
            boolean allocated = false;
            for (int i = 0; i < n; i++) {
                if (!finish[i]) {
                	System.out.println("Checking Process P" + i);
                    boolean canAllocate = true;
                    for (int j = 0; j < m; j++) {
                        if (need[i][j] > work[j]) {
                            canAllocate = false;
                            System.out.println("Need > Work " + need[i][j] + ">" +  work[j] + " Not checking for other resources");
                            break;
                        }else {
                        	System.out.println("Need <= Work " + need[i][j] + "<=" +  work[j]);
                        }
                    }

                    if (canAllocate) {
                    	System.out.println("Process P" + i + " can be allocated resources");
                        for (int j = 0; j < m; j++) {
                            work[j] += allocation[i][j];
                        }
                        System.out.print("Updated Work = Work + Allocation: ");
                        for(int k=0;k<work.length;k++)
                        System.out.print(work[k] + " ");
                        System.out.println();
                        
                        safeSequence.add(i);
                        finish[i] = true;
                        allocated = true;
                    }
                }
            }
            if (!allocated) 
            	{
            	System.out.println("None of processes could be allocated resources...");
            	break;
            	}
        }

        // Step 6: Output result
        if (safeSequence.size() == n) {
            System.out.println("Request CAN be granted.");
            System.out.print("Safe Sequence: ");
            for (int i = 0; i < safeSequence.size(); i++) {
                System.out.print("P" + safeSequence.get(i));
                if (i < safeSequence.size() - 1) System.out.print(" → ");
            }
        } else {
            System.out.println("Request CANNOT be granted. System would enter unsafe state.");

            // Rollback the request
            for (int j = 0; j < m; j++) {
                available[j] += request[j];
                allocation[reqProcess][j] -= request[j];
                need[reqProcess][j] += request[j];
            }
        }

        sc.close();
    }
}
