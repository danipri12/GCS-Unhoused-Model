import java.util.ArrayList;
import java.util.Scanner;

/* POSSIBLE ADDITIONS TO MODEL
 * add stigmatizations, so if was homeless at some point then that limits the agent even now - DONE
 * more realistic stats for the availability of a home and assets (based on our research)
 * use disability and jobTraining in the model
 * add more variables to model
 */

/* UPDATES ADDED TO MODEL
 * user input, can change individual agents' assets or input for all
 */

public class Main {
    public static void main(String[] args) {
        int months = 12;
        ArrayList<Agent> agentList = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input variables for each agent or have same variables for all? (type each/all)");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("each")) {
            for (int agentnum = 1; agentnum <= 10; agentnum++) {
                System.out.println("What is Agent_" + agentnum + " starting asset amount?");
                int asset = scanner.nextInt();
                System.out.println("Are they disabled (true/false)?");
                boolean disability = scanner.nextBoolean();
                System.out.println("Do they have job training (true/false)?");
                boolean jobTraining = scanner.nextBoolean();
                System.out.println("Are they homeless (true/false)?");
                boolean homeless = scanner.nextBoolean();
                scanner.nextLine();
                agentList.add(new Agent("Agent_" + agentnum, asset, disability, jobTraining, homeless));
            }
        } else if (response.equalsIgnoreCase("all")) {
            // ask once, reuse for all agents
            System.out.println("What is the Agent's starting asset amount?");
            int asset = scanner.nextInt();
            System.out.println("Are they disabled (true/false)?");
            boolean disability = scanner.nextBoolean();
            System.out.println("Do they have job training (true/false)?");
            boolean jobTraining = scanner.nextBoolean();
            System.out.println("Are they homeless (true/false)?");
            boolean homeless = scanner.nextBoolean();
            scanner.nextLine();

            for (int agentnum = 1; agentnum <= 10; agentnum++) {
                agentList.add(new Agent("Agent_" + agentnum, asset, disability, jobTraining, homeless));
            }
        }

        int[] homesList = {1, 0, 1, 0, 1, 0, 1, 0, 1, 0}; // each house's availability (1 occupied, 0 vacant)
        Home availHomes = new Home(homesList);

        for (int m = 1; m <= months; m++) {
            System.out.println("Month: " + m);

            for (int i = 0; i < agentList.size(); i++) {
                Agent agentValues = agentList.get(i);
                agentValues.setAsset(agentValues.getAsset()); // set updated asset based on variables
            }

            // sort assets by descending order
            agentList.sort((a, b) -> Integer.compare(b.getAsset(), a.getAsset()));

            for (int n = 0; n < agentList.size(); n++) {
                int agentAsset = agentList.get(n).getAsset();
                if (agentAsset >= 20) {
                    for (int house = 0; house < availHomes.length(); house++) {
                        if (availHomes.getHouse(house) == 0 && agentList.get(n).getHomeless() == false) {
                            availHomes.setHouse(house, 1); // set that house to occupied
                        }
                    }
                } else if (agentAsset >= 10) {
                    for (int house = 0; house < availHomes.length() / 2; house++) { // only look through first half of houses bc those are low income
                        if (availHomes.getHouse(house) == 0 && agentList.get(n).getHomeless() == false) {
                            availHomes.setHouse(house, 1);
                        }
                    }
                } else {
                    agentList.get(n).setHomeless(true);
                }
            }
        }

        for (Agent ag : agentList) {
            System.out.println("Agent stats: " + ag.getAsset() + " (assets), " + ag.getHomeless() + " (homeless status), " + ag.getDisability() + " (disability status), " + ag.getJobTraining() + " (job training status)");
        }

        scanner.close();
    }
}
