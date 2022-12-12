import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;


public class SJF {
    public static void sortProcessesBasedOnBurstTime(List<Process> P, int n, int counter , int contextSwitching ) {//,int counter //check arri
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if ((P.get(j).getBurstTime()+contextSwitching < P.get(i).getBurstTime()&&P.get(j).getArrivalTime()<= counter)||
                        (P.get(j).getArrivalTime())<=counter&&P.get(i).getArrivalTime()>counter) {
                    Process temp = P.get(i);
                    P.set(i, P.get(j));
                    P.set(j, temp);
                }
            }
        }
    }
    //    public Static void SFj
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List <Process> processes = new ArrayList<Process>();
        System.out.println("   --welcome-- to Shortest Jop First CPU Scheduling code--   ");
        System.out.println("enter the number of processes and context switching");
        int numberOfProcesses = input.nextInt();
        int contextSwitchingTime=input.nextInt();
        Process contextSwitching=new Process();
        contextSwitching.setName("contextSwitching");
        contextSwitching.setBurstTime(contextSwitchingTime);
        contextSwitching.setArrivalTime(0);
        System.out.println("enter for each process its name , arrival time and burst time");
        for (int i = 0; i < numberOfProcesses; i++) {
            Process s = new Process();
            s.setName(input.next());
            s.setArrivalTime(input.nextInt());
            s.setBurstTime(input.nextInt());
            processes.add(s);
        }
        int totalNumberOfBurstTime=0;
        for (Process p : processes){
            totalNumberOfBurstTime+=p.getBurstTime();
        }
        Stack<Process> P = new Stack<Process>();
        Stack<Integer> T = new Stack<Integer>();
        Stack<Integer> C = new Stack<Integer>();
        Process noProcessInTheMemory=new Process();
        noProcessInTheMemory.setName("no process in the memory");
        noProcessInTheMemory.setWaitingTime(0);
        P.add(null);
        T.add(null);
        C.add(null);
        int counter=0;
        boolean status;
        while (totalNumberOfBurstTime!=0){
            sortProcessesBasedOnBurstTime(processes,numberOfProcesses,counter,contextSwitchingTime);
            status=false;
            for (int i = 0; i < numberOfProcesses; i++) {
                if(processes.get(i).getBurstTime()>0&&processes.get(i).getArrivalTime()<=counter)
                {
                    status=true;
                    if (P.lastElement() == processes.get(i)) {
                        T.set(T.size()-1, (T.lastElement() + 1));
                    } else {
                        if (P.lastElement() != null && (P.lastElement() != noProcessInTheMemory || T.lastElement() < contextSwitchingTime)
                                && (P.lastElement() != noProcessInTheMemory || T.get(T.size() - 2) != null)) {
                            P.add(contextSwitching);
                            T.add(contextSwitchingTime);
                            counter += contextSwitchingTime;
                        }
                        P.add(processes.get(i));
                        T.add(1);
                    }
                    processes.get(i).setBurstTime(processes.get(i).getBurstTime()-1);
                    if(processes.get(i).getBurstTime()==0){
                        processes.get(i).setTurnAroundTime((counter+1)-processes.get(i).getArrivalTime());
                        processes.get(i).setWaitingTime(processes.get(i).getTurnAroundTime()-processes.get(i).getSaveBurstTime());
                    }
                    break;
                }
            }
            if (!status) {
                if (P.lastElement() != noProcessInTheMemory) {
                    P.add(noProcessInTheMemory);
                    T.add(1);
                } else {
                    T.set(T.size() - 1, T.get(T.size() - 1) + 1);
                }
            }
            totalNumberOfBurstTime=0;
            for (Process p : processes){
                totalNumberOfBurstTime+=p.getBurstTime();
            }
            counter++;
        }
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("Processes execution order |");
        for (int i = 1; i < P.size(); i++) {
            System.out.println(P.get(i).getName()+" "+T.get(i));
        }
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("table of processes |Name ,WaitingTime ,TurnaroundTime ,BurstTime");
        double avgWaitingTime=0.0;
        double avgTurnAroundTime=0.0;
        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.println(processes.get(i).getName()+" "+processes.get(i).getWaitingTime()+" "+processes.get(i).getTurnAroundTime()+" "
                    +processes.get(i).getSaveBurstTime());
            avgWaitingTime+=processes.get(i).getWaitingTime();
            avgTurnAroundTime+=processes.get(i).getTurnAroundTime();
        }
        avgWaitingTime/=numberOfProcesses;
        avgTurnAroundTime/=numberOfProcesses;
        System.out.print("the avrage waiting time for processes = ");
        System.out.println(avgWaitingTime);
        System.out.print("the avrage turnaround time for processes = ");
        System.out.println(avgTurnAroundTime);
    }
}

//5 1
//p1 0 4
//p2 1 8
//p3 3 2
//p4 10 6
//p5 12 5


//3 5
//p1 5 10
//p2 15 17
//p3 20 3

