/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Long
 */
public class Model {
    private CategoryModel categoryModel;
    private ProductModel productModel;

    public Model() {
        this.categoryModel = new CategoryModel();
        this.productModel = new ProductModel();
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

}
