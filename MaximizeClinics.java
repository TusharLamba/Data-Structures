import java.util.*;
class City implements Comparable {
    final int pop;
    int clinicCnt;
    int load;
    public City(int p) {
        this.pop = p;
        this.clinicCnt = 1;
        this.load = p;
    }
    public int compareTo(Object o) {
        City c = (City)o;
        if(load < c.load) return -1;
        if(load > c.load) return 1;
        return 0; 
    }
    public void recalc() {
       this.clinicCnt++;
       this.load = this.pop / this.clinicCnt;
       if(this.pop % this.clinicCnt != 0)
          this.load++;
    }
    
}
class PriorityQueue4 {
    City[] ca;
    int size;
    public PriorityQueue4(int capacity) {
        ca = new City[capacity + 1];
        size = 0;
    }
    private void swap(int i, int j) {
        City t = ca[i];
        ca[i] = ca[j];
        ca[j] = t;
    }
    public void insert(int x) {
        ca[++size] = new City(x);
        swim(size);
    }
    private void swim(int i) {
        if(i <= 1)
            return;
        if(ca[i/2].compareTo(ca[i]) == -1) {
            swap(i/2, i);
            swim(i/2);
        } 
    }
    public void sink(int i) {
        int max = i, left = 2*i, right = 2*i + 1;
        if(left <= size && ca[left].compareTo(ca[max]) == 1) 
            max = left;
        if(right <= size && ca[right].compareTo(ca[max]) == 1) 
            max = right;
        if(max != i) {
            swap(max, i);
            sink(max);
        }   
    }
    public City findMax() {
        return ca[1];
    }
    public void setMax(City c) {
        ca[1] = c;
    }
}
public class MaximizeClinics {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        PriorityQueue4 p = new PriorityQueue4(n);
        for(int i = 1; i <= n; i++) {
            p.insert(scn.nextInt());
        }
        int clinicLeft = scn.nextInt();
       /* for(int i = 1; i <= n; i++) {
            System.out.println("City " + i + ": ");
            System.out.println("Population: " + p.ca[i].pop + "\nClinics: " + p.ca[i].clinicCnt + 
            "\nLoad: " + p.ca[i].load + "\n");
        }      */  
        clinicLeft -= p.size;
        while(clinicLeft > 0) {
            City c = p.findMax();
            c.recalc();
            p.setMax(c);
            p.sink(1);
            clinicLeft--;
        }
        System.out.println("Maximum load/Vaccinations: " + p.findMax().load);
        
        /*for(int i = 1; i <= n; i++) {
            System.out.println("City " + i + ": ");
            System.out.println("Population: " + p.ca[i].pop + "\nClinics: " + p.ca[i].clinicCnt + 
            "\nLoad: " + p.ca[i].load + "\n");
        }   */
    }
}