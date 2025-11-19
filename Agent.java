public class Agent{
    int asset;
    boolean disability;
    boolean jobTraining;
    boolean homeless;
    private String name;
    boolean everHomeless;

    public Agent(String name, int asset, boolean disability, 
                    boolean jobTraining, boolean homeless, boolean everHomeless){
        this.name = name;
        this.asset = asset;
        this.disability = disability;
        this.jobTraining = jobTraining;
        this.homeless = homeless;
        this.everHomeless = everHomeless;
    }

    public String getAgentName(){
        return name;
    }

    public int getAsset(){
        return asset;
    }

    public void updateMonthlyAsset(){
        int newAsset = this.asset;

        int delta = (int)(Math.random() * 13) - 6; // random number
        newAsset += delta;

        if(disability && newAsset >= 5){
            newAsset -= 5;
        }
        if(jobTraining && newAsset >= 0){
            newAsset += 5;
        }

        if(newAsset < 0) newAsset = 0;
        if(newAsset > 100) newAsset = 100;

        this.asset = newAsset;  // updates current asset
        }

    public boolean getDisability(){
        return disability;
    }

    public boolean getJobTraining(){
        return jobTraining;
    }

    public boolean getHomeless(){
        return homeless;
    }

    public void setHomeless(boolean status){
        homeless = status;
    }

    public boolean getEverHomeless(){
        return everHomeless;
    }
}