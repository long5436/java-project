/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import entities.Category;
import entities.Product;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import model.Model;
import view.ViewPanelProduct;

/**
 *
 * @author Long
 */
public class ProductController {
    private Model model;
    private ViewPanelProduct view;
    private ArrayList<Product> listProduct;
    private ArrayList<Category> listCategory;
    private boolean editStatus;
    private boolean addStatus;
    private int indexTableSelected;
    private int indexSort;
    private int indexCategorySeleted;

    public ProductController(ViewPanelProduct view, Model model) {
        this.model = model;
        this.view = view;
        this.listProduct = new ArrayList<>();
        this.listCategory = new ArrayList<>();
        this.editStatus = false;
        this.addStatus = false;
        this.indexTableSelected = -1;
        this.indexSort = 0;
        this.indexCategorySeleted = 0;

        init();
    }

    private void init() {
        view.getTxtSearch().setEditable(false);
        view.getTxtProductId().setEditable(false);
        view.getTxtProductName().setEditable(false);
        view.getTxtProductPrice().setEditable(false);
        view.getTxtProductDescription().setEditable(false);

        // combobox va text field thay the cho combobox
        view.getTxtCategoryId().setEditable(false);
        view.getTxtCategoryId().setVisible(true);
        view.getCboProductCategory().setVisible(false);

        // button
        view.getBtnSearch().setEnabled(false);
        view.getBtnAdd().setEnabled(false);
        view.getBtnDelete().setEnabled(false);
        view.getBtnEdit().setEnabled(false);
        view.getBtnToggleEdit().setEnabled(false);

        loadDataCategory();
        renderComboxCategory();
        loadDataProduct();
        renderTable();

        view.getTblProductView().addMouseListener(tableListener());
        view.getBtnToggleAdd().addActionListener(handleToggleAdd());
        view.getBtnAdd().addActionListener(handleAdd());
        view.getBtnToggleEdit().addActionListener(handleToggleEdit());
        view.getBtnEdit().addActionListener(handleEdit());
        view.getBtnDelete().addActionListener(handleDelete());
        view.getBtnSearch().addActionListener(handleSearch());

        view.getCheckSearch().addItemListener(handleCheckSearch());

        view.getCboSort().addItemListener(handleComboBoxSelect(1));
        view.getCboShowCategory().addItemListener(handleComboBoxSelect(2));

    }

    private ActionListener handleSearch() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyWord = view.getTxtSearch().getText();
                if (!keyWord.equals("")) {
                    try {
                        listProduct = model.getProductModel().searchProduct(keyWord);
                        renderTable();
                    } catch (Exception ex) {
                        // TODO: handle exception
                    }
                }
            }
        };
    }

    private ItemListener handleCheckSearch() {
        return new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == 1) {
                    indexCategorySeleted = 0;
                    indexSort = 0;
                } else {
                    view.getTxtSearch().setText("");
                    loadDataProduct();
                    renderTable();
                }

                view.getBtnSearch().setEnabled(ie.getStateChange() == 1);
                view.getTxtSearch().setEditable(ie.getStateChange() == 1);
            }
        };
    }

    private ItemListener handleComboBoxSelect(int key) {
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent arg0) {

                switch (key) {
                    case 1:
                        indexSort = view.getCboSort().getSelectedIndex();
                        break;
                    case 2:
                        indexCategorySeleted = view.getCboShowCategory().getSelectedIndex();
                        loadDataProduct();
                        break;

                    default:
                        break;
                }

                renderTable();
            }
        };
    }

    private ActionListener handleDelete() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (indexTableSelected >= 0) {
                    String productId = listProduct.get(indexTableSelected).getProductId();
                    try {

                        int confirm = showMessageConfirm("Bạn có muốn xóa sản phẩm có mã " + productId + " không?");

                        if (confirm == 0) {

                            model.getProductModel().deleteProduct(productId);

                            loadDataProduct();
                            renderTable();

                            loadDataToField(-1);
                        }

                    } catch (Exception ex) {
                        System.out.println("Có lỗi xãy ra");
                    }
                }

            }
        };
    }

    private ActionListener handleEdit() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productId = view.getTxtProductId().getText();
                String productName = view.getTxtProductName().getText();
                String description = view.getTxtProductDescription().getText();
                String priceText = view.getTxtProductPrice().getText();
                int cateSelectedId = view.getCboProductCategory().getSelectedIndex();

                if (!productId.equals("") &&
                        !productName.equals("") &&
                        !description.equals("") &&
                        !priceText.equals("") &&
                        cateSelectedId > 0) {

                    boolean check = checkNumber(priceText);
                    if (check) {

                        String cateId = listCategory.get(cateSelectedId - 1).getCategoryId();
                        Double price = Double.parseDouble(priceText);
                        Product prod = new Product(productId, cateId, productName, description, price);

                        try {
                            model.getProductModel().editProduct(productId, prod);

                            editStatus = false;
                            view.getBtnEdit().setEnabled(editStatus);
                            view.getBtnToggleEdit().setText("Sửa");

                            loadDataProduct();
                            renderTable();
                            view.getBtnToggleEdit().setEnabled(false);
                            view.getBtnDelete().setEnabled(false);

                            loadDataToField(-1);

                        } catch (Exception ex) {
                            // 5 TODO: handle exception
                        }
                    }
                }
            }
        };
    }

    private ActionListener handleAdd() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productId = view.getTxtProductId().getText();
                String productName = view.getTxtProductName().getText();
                String description = view.getTxtProductDescription().getText();
                String priceText = view.getTxtProductPrice().getText();
                int cateSelectedId = view.getCboProductCategory().getSelectedIndex();

                if (!productId.equals("") &&
                        !productName.equals("") &&
                        !description.equals("") &&
                        !priceText.equals("") &&
                        cateSelectedId > 0) {

                    boolean check = checkNumber(priceText);
                    if (check) {

                        String cateId = listCategory.get(cateSelectedId - 1).getCategoryId();
                        Double price = Double.parseDouble(priceText);
                        Product prod = new Product(productId, cateId, productName, description, price);

                        try {
                            model.getProductModel().addProduct(prod);

                            addStatus = false;
                            view.getBtnAdd().setEnabled(addStatus);
                            view.getBtnToggleAdd().setText("Thêm");

                            loadDataProduct();
                            renderTable();
                            view.getBtnToggleEdit().setEnabled(false);
                            view.getBtnDelete().setEnabled(false);

                            loadDataToField(-1);

                        } catch (Exception ex) {
                            // 5 TODO: handle exception
                        }
                    }
                }
            }
        };
    }

    private ActionListener handleToggleEdit() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editStatus) {
                    editStatus = false;
                    view.getBtnToggleEdit().setText("Sủa");
                    loadDataToField(indexTableSelected);
                } else {
                    editStatus = true;
                    view.getBtnToggleEdit().setText("Huỷ sửa");
                }
                // view.getTxtProductId().setEditable(addStatus);
                view.getTxtProductName().setEditable(editStatus);
                view.getTxtProductPrice().setEditable(editStatus);
                view.getTxtProductDescription().setEditable(editStatus);

                view.getTxtCategoryId().setVisible(!editStatus);
                view.getCboProductCategory().setVisible(editStatus);

                view.getBtnEdit().setEnabled(editStatus);
            }
        };
    }

    private ActionListener handleToggleAdd() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addStatus) {
                    addStatus = false;
                    view.getBtnToggleAdd().setText("Thêm mới");

                    loadDataToField(indexTableSelected);
                } else {
                    addStatus = true;
                    view.getBtnToggleAdd().setText("Huỷ thêm");

                    loadDataToField(-1);
                }

                view.getTxtProductId().setEditable(addStatus);
                view.getTxtProductName().setEditable(addStatus);
                view.getTxtProductPrice().setEditable(addStatus);
                view.getTxtProductDescription().setEditable(addStatus);

                view.getTxtCategoryId().setVisible(!addStatus);
                view.getCboProductCategory().setVisible(addStatus);

                view.getBtnAdd().setEnabled(addStatus);
            }
        };
    }

    private void loadDataToField(int index) {
        boolean check = index >= 0;
        Product prod = check ? listProduct.get(index) : null;
        int indexCate = 0;
        String cateName = "";

        // combobox catagory
        if (check) {
            for (Category cate : listCategory) {
                if (cate.getCategoryId().equals(prod.getCategoryId())) {
                    cateName = cate.getCategoryName();
                    break;
                }
                indexCate++;
            }

            indexCate = (indexCate + 1 > listCategory.size()) ? 0 : indexCate + 1;
        }

        view.getTxtProductId().setText(check ? prod.getProductId() : "");
        view.getTxtProductName().setText(check ? prod.getProductName() : "");
        view.getTxtProductPrice().setText(check ? prod.getPrice() + "" : "");
        view.getTxtProductDescription().setText(check ? prod.getDescription() : "");
        view.getTxtCategoryId().setText(indexCate > 0 ? cateName : "");
        view.getCboProductCategory().setSelectedIndex(0);
    }

    public MouseAdapter tableListener() {
        return new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                int index = view.getTblProductView().getSelectedRow();
                indexTableSelected = index;

                loadDataToField(index);

                view.getBtnToggleEdit().setEnabled(index >= 0);
                view.getBtnDelete().setEnabled(index >= 0);
            }
        };
    }

    private void loadDataCategory() {
        try {
            listCategory = model.getCategoryModel().getCategories();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void loadDataProduct() {
        try {
            switch (indexCategorySeleted) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    String cateId = listCategory.get(indexCategorySeleted - 1).getCategoryId();
                    listProduct = model.getProductModel().getAllProductByTypeId(cateId);
                    System.out.println(indexCategorySeleted);
                    System.out.println(cateId);
                    break;
                case 0:
                default:
                    listProduct = model.getProductModel().getAllProduct();
                    break;
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void renderComboxCategory() {
        for (Category cate : listCategory) {
            view.getCboProductCategory().addItem(cate.getCategoryName());
            view.getCboShowCategory().addItem(cate.getCategoryName());
        }
    }

    private void renderTable() {
        DefaultTableModel table = (DefaultTableModel) view.getTblProductView().getModel();
        table.setRowCount(0);

        // constructor: String productId, String categoryId, String productName, String
        // description, double price
        sortProduct();

        for (Product prod : listProduct) {
            Object[] ob = new Object[] { prod.getProductId(), prod.getCategoryId(), prod.getProductName(),
                    prod.getDescription(), prod.getPrice() };
            table.addRow(ob);
        }
    }

    private void sortProduct() {
        switch (indexSort) {
            case 1:
                listProduct = model.getProductModel().sortByName();
                break;
            case 2:
                listProduct = model.getProductModel().sortByPrice();
                break;
            case 3:
                listProduct = model.getProductModel().sortByNameThenByPrice();
                break;
            case 4:
                listProduct = model.getProductModel().sortByPriceThenByName();
                break;
            case 0:
            default:
                listProduct = model.getProductModel().getlistProduct();
                break;
        }
    }

    private boolean checkNumber(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            JPanel panel = new JPanel();
            panel.add(new JLabel("Trường giá sản phẩm phải là số"));
            JOptionPane.showMessageDialog(null, panel);

            return false;
        }
    }

    private int showMessageConfirm(String message) {
        Object[] options = { "Có", "Không" };
        int confirm = JOptionPane.showOptionDialog(
                null,
                message,
                "Xác nhận",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        return confirm;

    }

}
