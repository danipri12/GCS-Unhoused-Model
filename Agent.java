public class Agent{
    int asset;
    boolean disability;
    boolean jobTraining;
    boolean homeless;
    private String name;

    public Agent(String name, int asset, boolean disability, 
                    boolean jobTraining, boolean homeless){
        this.name = name;
        this.asset = asset;
        this.disability = Math.random()<=0.1;
        this.jobTraining = Math.random()<=0.51;
        this.homeless = Math.random()<=0.23;
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
        homeless = status; //mayybe change homeless to num (float?), not boolean
    }
}