/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import entities.Product;
import dao.ProductDAO;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author nguye
 */
public class ProductModel {

    private ArrayList<Product> listProduct;

    public ProductModel() {
        this.listProduct = new ArrayList<>();
    }

    public ArrayList<Product> getlistProduct() {
        return listProduct;
    }

    public boolean addProduct(Product product) throws Exception {
        return ProductDAO.addProduct(product);
    }

    public int deleteProduct(String productId) throws Exception {
        return ProductDAO.deleteProduct(productId);
    }

    public boolean editProduct(String productId, Product product) throws Exception {
        return ProductDAO.editProduct(productId, product);
    }

    public ArrayList<Product> getAllProduct() throws Exception {
        listProduct = ProductDAO.getAllProduct();
        return listProduct;
    }

    public ArrayList<Product> getAllProductByTypeId(String categoryId) throws Exception {
        listProduct = ProductDAO.getAllProductByTypeId(categoryId);
        return listProduct;
    }

    public ArrayList<Product> searchProduct(String keyword) throws Exception {
        listProduct = ProductDAO.searchProduct(keyword);
        return listProduct;
    }

    private int comparePrice(double price1, double price2) {
        if (price1 - price2 > 0) {
            return 1;
        } else if (price1 - price2 < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    private int compareName(String ten1, String ten2) {

        String a = ten1.toLowerCase();
        String b = ten2.toLowerCase();

        return a.compareTo(b);
    }

    public ArrayList<Product> sortByName() {
        @SuppressWarnings("unchecked")
        ArrayList<Product> newArr = (ArrayList<Product>) listProduct.clone();

        Collections.sort(newArr, new Comparator<Product>() {
            @Override
            public int compare(Product sp1, Product sp2) {

                return compareName(sp1.getProductName(), sp2.getProductName());
            }
        });

        return newArr;
    }

    public ArrayList<Product> sortByPrice() {
        @SuppressWarnings("unchecked")
        ArrayList<Product> newArr = (ArrayList<Product>) listProduct.clone();

        Collections.sort(newArr, new Comparator<Product>() {
            @Override
            public int compare(Product sp1, Product sp2) {

                return comparePrice(sp1.getPrice(), sp2.getPrice());

            }
        });

        return newArr;
    }

    public ArrayList<Product> sortByPriceThenByName() {
        @SuppressWarnings("unchecked")
        ArrayList<Product> newArr = (ArrayList<Product>) listProduct.clone();

        Collections.sort(newArr, new Comparator<Product>() {
            @Override
            public int compare(Product sp1, Product sp2) {
                int result = comparePrice(sp1.getPrice(), sp2.getPrice());

                if (result != 0) {
                    return result;
                } else {
                    return compareName(sp1.getProductName(), sp2.getProductName());
                }
            }
        });
        return newArr;
    }

    public ArrayList<Product> sortByNameThenByPrice() {
        @SuppressWarnings("unchecked")
        ArrayList<Product> newArr = (ArrayList<Product>) listProduct.clone();

        Collections.sort(newArr, new Comparator<Product>() {
            @Override
            public int compare(Product sp1, Product sp2) {

                int result = compareName(sp1.getProductName(), sp2.getProductName());

                if (result != 0) {
                    return result;
                } else {
                    return comparePrice(sp1.getPrice(), sp2.getPrice());
                }
            }
        });

        return newArr;
    }

}
