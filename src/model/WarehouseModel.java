package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import entities.Warehouse;
import dao.WarehouseDAO;

public class WarehouseModel {
    private ArrayList<Warehouse> arr;

    public WarehouseModel() {
    }

    public ArrayList<Warehouse> getArr() {
        return arr;
    }

    public ArrayList<Warehouse> getWarehouse() throws Exception {
        arr = WarehouseDAO.getWarehouse();
        return arr;
    }

    public ArrayList<Warehouse> searchWarehouse(String keyword) throws Exception {
        return WarehouseDAO.searchWarehouse(keyword);
    }

    public boolean editWarehouse(String productId, Warehouse w) throws Exception {
        return WarehouseDAO.editWarehouse(productId, w);
    }

    private int compareName(String ten1, String ten2) {

        String a = ten1.toLowerCase();
        String b = ten2.toLowerCase();

        return a.compareTo(b);
    }

    public ArrayList<Warehouse> sortByName() {
        @SuppressWarnings("unchecked")
        ArrayList<Warehouse> newArr = (ArrayList<Warehouse>) arr.clone();

        Collections.sort(newArr, new Comparator<Warehouse>() {
            @Override
            public int compare(Warehouse sp1, Warehouse sp2) {

                return compareName(sp1.getProductName(), sp2.getProductName());
            }
        });

        return newArr;
    }

    public ArrayList<Warehouse> sortByQuantity() {
        @SuppressWarnings("unchecked")
        ArrayList<Warehouse> newArr = (ArrayList<Warehouse>) arr.clone();

        Collections.sort(newArr, new Comparator<Warehouse>() {
            @Override
            public int compare(Warehouse sp1, Warehouse sp2) {

                return sp1.getQuantity() - sp2.getQuantity();

            }
        });

        return newArr;
    }

    public ArrayList<Warehouse> sortByQuantityThenByName() {
        @SuppressWarnings("unchecked")
        ArrayList<Warehouse> newArr = (ArrayList<Warehouse>) arr.clone();

        Collections.sort(newArr, new Comparator<Warehouse>() {
            @Override
            public int compare(Warehouse sp1, Warehouse sp2) {
                int result = sp1.getQuantity() - sp2.getQuantity();

                if (result != 0) {
                    return result;
                } else {
                    return compareName(sp1.getProductName(), sp2.getProductName());
                }
            }
        });
        return newArr;
    }

    public ArrayList<Warehouse> sortByNameThenByQuantity() {
        @SuppressWarnings("unchecked")
        ArrayList<Warehouse> newArr = (ArrayList<Warehouse>) arr.clone();

        Collections.sort(newArr, new Comparator<Warehouse>() {
            @Override
            public int compare(Warehouse sp1, Warehouse sp2) {

                int result = compareName(sp1.getProductName(), sp2.getProductName());

                if (result != 0) {
                    return result;
                } else {
                    return sp1.getQuantity() - sp2.getQuantity();
                }
            }
        });

        return newArr;
    }
}
