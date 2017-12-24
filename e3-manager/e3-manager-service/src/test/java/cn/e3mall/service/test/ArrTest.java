package cn.e3mall.service.test;

public class ArrTest {  
	  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
  
        ArrayChange ac=new ArrayChange(20);  
        ac.insert(15);  
        ac.insert(23);  
        ac.insert(2);  
        ac.insert(5);  
        ac.insert(56);  
        ac.insert(90);  
        ac.insert(23);  
        ac.insert(53);  
        ac.insert(21);  
        ac.insert(100);  
        ac.insert(3);  
        ac.display();  
        ac.change();  
        ac.display();  
    }  
  
}  
class ArrayChange{  
    int items;  
    int[] array;  
    ArrayChange(int max){  
       array=new int[max];  
       items=0;  
    }  
    public void insert(int k){  
        array[items++]=k;  
    }  
    public void change(){  
        int max=0;int min=items-1;  
        for(int i=0;i<items;i++){  
            if(array[i]>array[max])  
                max=i;  
            if(array[i]<array[min])  
                min=i;  
        }  
          
        if(min==0){  
            int t=array[items-1];  
            array[items-1]=array[min];  
            array[0]=array[max];  
            array[max]=t;  
            return;  
        }  
        if(max!=0){  
            array[max]^=array[0];  
            array[0]^=array[max];  
            array[max]^=array[0];  
        }  
          
        if(min!=items-1){  
            array[min]^=array[items-1];  
            array[items-1]^=array[min];  
            array[min]^=array[items-1];  
        }  
       
    }  
    public void display(){  
        for(int i=0;i<items;i++){  
            System.out.print(array[i]+" ");  
        }  
        System.out.println();  
    }  
}  
