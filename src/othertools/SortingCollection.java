package othertools;

import entity.Historical;
import javafx.collections.ObservableList;

public class SortingCollection {

    //Sử dụng heap sort để tối ưu thời gian O(nlogn) và bộ nhớ O(1)
    public static void sort(ObservableList<Historical> dataList) {
        int size = dataList.size();
        for(int i = size/2-1; i >= 0; i--)
            heapify(dataList, i, size);
        while(size>0) {
            swap(dataList, 0, --size);
            heapify(dataList, 0, size);
        }
    }

    private static void heapify(ObservableList<Historical> dataList, int root,int size)
    {
        int child = root * 2 + 1;
        while(child < size) {
            if(child+1 < size && StringHandler.compare(dataList.get(child).getName(), dataList.get(child+1).getName()) < 0)
                child++;
            if(StringHandler.compare(dataList.get(root).getName(), dataList.get(child).getName()) < 0) {
                swap(dataList, child, root);
                root = child;
                child = root*2+1;
            } else 
                break;
        }
    }

    private static void swap(ObservableList<Historical> list, int a, int b) {
        Historical tmp = list.get(a);
        list.set(a, list.get(b));
        list.set(b, tmp);
        tmp = null;
    }

}
