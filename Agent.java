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

    public void setAsset(int asset){
        int delta = (int)(Math.random() * 9) - 2;
        asset += delta;
        if(getAsset() < 0){
            asset = 0; //to prevent negative asset values
        }

        if(getDisability() && getAsset() >= 5){
            asset += -5;
        }
        if(getJobTraining() && getAsset() >= 0){
            asset += 5;
        }
        this.asset = asset;
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