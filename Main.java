import java.util.ArrayList;

/* POSSIBLE ADDITIONS TO MODEL
 * add stigmatizations, so if was homeless at some point then that limits the agent even now
 * more realistic stats for the availability of a home and assets (based on our research)
 * use disability and jobTraining in the model
 * add more variables to model
 */

public class Main{
    public static void main(String[] args){
        int months = 12;
        ArrayList<Agent> agentList = new ArrayList<>();

        for(int agentnum=1; agentnum<=11; agentnum++){
            agentList.add(new Agent("Agent_" + agentnum, 0, false, false, false));
        }

        int[] homesList = {1, 0, 1, 0, 1, 0, 1, 0, 1, 0}; //each house's availability (1 occupied, 0 vacant)
        Home availHomes = new Home(homesList);

        for(int m=1; m<=months; m++){
            System.out.println("Month: " + m);

            for(int i=0; i<agentList.size(); i++){
                Agent agentValues = agentList.get(i);
                agentValues.setAsset(agentValues.getAsset()); //set updated asset based on variables

            }
            
            //sort assets by dec order
            agentList.sort((a,b) -> Integer.compare(b.getAsset(), a.getAsset()));

            for(int n=0; n<agentList.size(); n++){
                int agentAsset = agentList.get(n).getAsset();
                if(agentAsset >= 20){
                    for(int house=0; house<availHomes.length(); house++){
                        if(availHomes.getHouse(n) == 0){
                            availHomes.setHouse(n, 1); //set that house to occupied
                            agentList.get(n).setHomeless(false); //set agent to not homeless
                        }
                    }
                } else if (agentAsset >= 10){
                    for(int house=0; house<availHomes.length()/2; house++){ //only look through first half of houses bc those are low income
                        if(availHomes.getHouse(n) == 0){
                            availHomes.setHouse(n, 1);
                            agentList.get(n).setHomeless(false);
                        }
                    }
                } else {
                    agentList.get(n).setHomeless(true);
                }
            }
        }
        for(Agent ag : agentList){
            System.out.println("Agent stats: " + ag.getAsset() + " (assets)" + ", " + ag.getHomeless() + " (homeless status)");
        }
    }
}

