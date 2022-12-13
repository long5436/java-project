/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import entities.Category;
import dao.CategoryDAO;

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

    public boolean xoaCategory(String maCategory) throws Exception {
        return CategoryDAO.deleteCategory(maCategory);
    }

    public boolean suaCategory(String maCategory, Category Category) throws Exception {
        return CategoryDAO.editCategory(maCategory, Category);
    }

    public ArrayList<Category> layDanhSachCategory() throws Exception {
        ArrayList<Category> ds = CategoryDAO.getCategories();

        this.productCategoryList = ds;

        return this.productCategoryList;
    }
}
