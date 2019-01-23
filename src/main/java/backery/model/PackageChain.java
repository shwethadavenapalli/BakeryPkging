package backery.model;

import java.util.ArrayList;
import java.util.List;

public class PackageChain {

    private List<Integer> numbersList;

    public PackageChain(PackageChain packageChain){
        numbersList = new ArrayList<>(packageChain.numbersList);
    }
    public PackageChain(List<Integer> numbersList){
        this.numbersList = numbersList;
    }

    public void add(Integer number){
        numbersList.add(number);
    }

    public Integer getLastNumber(){
        return numbersList.get(numbersList.size()-1);
    }

    public List<Integer> getNumbersList() {
        return numbersList;
    }
}
