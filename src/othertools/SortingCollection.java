package othertools;

import datahandle.Importer;
import entity.Historical;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SortingCollection {
    public static void sort(ObservableList<Historical> dataList){
        //Devid list
        int size = dataList.size();
        if(size <=1)
            return;
        ObservableList<Historical> left = FXCollections.observableArrayList(dataList.subList(0, size/2));
        ObservableList<Historical> right = FXCollections.observableArrayList(dataList.subList(size/2, size));
        sort(left);
        sort(right);
        int l = 0, r = 0;
        dataList.clear();
        while(l < left.size() && r < right.size()) {
            if(StringHandler.compare(left.get(l).getName(), right.get(r).getName()) < 0) {
                dataList.add(left.get(l++));
            } else {
                dataList.add(right.get(r++));
            }
        }
        while(l < left.size())
            dataList.add(left.get(l++));
        while(r < right.size())
            dataList.add(right.get(r++));
        left.clear();
        left = null;
        right.clear();
        right = null;
    }

    public static void heapSort(ObservableList<Historical> dataList) {
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
            if(dataList.get(root).getName().compareTo(dataList.get(child).getName()) < 0) {
                swap(dataList, child, root);
                root = child;
                child = root*2+1;
            }else
                break;
        }
    }

    private static void swap(ObservableList<Historical> list, int a, int b) {
        Historical tmp = list.get(a);
        list.set(a, list.get(b));
        list.set(b, tmp);
        tmp = null;
    }

    public static void main(String[] args) {
        ObservableList<Historical> dataList = FXCollections.observableArrayList((new Importer()).getDynasties());
        heapSort(dataList);
        for(Historical a : dataList) {
            System.out.println(a.getName());
        }
    }
}
