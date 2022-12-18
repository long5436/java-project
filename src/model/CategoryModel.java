/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import entities.Category;
import dao.CategoryDAO;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Long
 */
public class CategoryModel {

    private ArrayList<Category> productCategoryList;

    public ArrayList<Category> getProductCategoryList() {
        return productCategoryList;
    }

    public boolean addCategory(Category category) throws Exception {
        return CategoryDAO.addCategory(category);
    }

    public boolean deleteCategory(String maCategory) throws Exception {
        return CategoryDAO.deleteCategory(maCategory);
    }

    public boolean editCategory(String maCategory, Category Category) throws Exception {
        return CategoryDAO.editCategory(maCategory, Category);
    }

    public ArrayList<Category> getCategories() throws Exception {
        ArrayList<Category> ds = CategoryDAO.getCategories();

        this.productCategoryList = ds;

        return this.productCategoryList;
    }

    private int compareName(String ten1, String ten2) {

        String a = ten1.toLowerCase();
        String b = ten2.toLowerCase();

        return a.compareTo(b);
    }

    public ArrayList<Category> sortByAZ() {
        @SuppressWarnings("unchecked")
        ArrayList<Category> newArr = (ArrayList<Category>) productCategoryList.clone();

        Collections.sort(newArr, new Comparator<Category>() {
            @Override
            public int compare(Category ct1, Category ct2) {

                return compareName(ct1.getCategoryName(), ct2.getCategoryName());
            }
        });

        return newArr;
    }

    public ArrayList<Category> sortByZA() {
        @SuppressWarnings("unchecked")
        ArrayList<Category> newArr = (ArrayList<Category>) productCategoryList.clone();

        Collections.sort(newArr, new Comparator<Category>() {
            @Override
            public int compare(Category ct1, Category ct2) {

                return compareName(ct2.getCategoryName(), ct1.getCategoryName());
            }
        });

        return newArr;
    }

    public ArrayList<Category> searchCategory(String keyword) throws Exception {
        productCategoryList = CategoryDAO.searchCategory(keyword);
        return productCategoryList;
    }

}
