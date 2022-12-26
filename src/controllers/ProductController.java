/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.Date;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import view.ViewPanelProduct;
import model.Model;
import entities.Category;
import entities.Product;

import utils.MethodUtil;

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
    private File fileSelect;
    private String pathFolderImage;
    private boolean removeImageStatus;

    public ProductController(ViewPanelProduct view, Model model) {
        this.model = model;
        this.view = view;
        this.listProduct = new ArrayList<>();
        this.listCategory = new ArrayList<>();
        this.editStatus = false;
        this.addStatus = false;
        this.removeImageStatus = false;
        this.indexTableSelected = -1;
        this.indexSort = 0;
        this.indexCategorySeleted = 0;

        File currentDir = new File("src/main/resources/images/");
        String path = currentDir.getAbsolutePath().replace("\\", "/") + "/";
        this.pathFolderImage = path;

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
        view.getBtnChooseImage().setEnabled(false);

        view.getTblProductView().addMouseListener(tableListener());
        view.getBtnToggleAdd().addActionListener(handleToggleAdd());
        view.getBtnAdd().addActionListener(handleAdd());
        view.getBtnToggleEdit().addActionListener(handleToggleEdit());
        view.getBtnEdit().addActionListener(handleEdit());
        view.getBtnDelete().addActionListener(handleDelete());
        view.getBtnSearch().addActionListener(handleSearch());
        view.getBtnChooseImage().addActionListener(handleSelectImage());
        view.getBtnRemoveImage().addActionListener(handleSetRemoveImage());

        view.getCheckSearch().addItemListener(handleCheckSearch());
        view.getCboSort().addItemListener(handleComboBoxSelect(1));
        view.getCboShowCategory().addItemListener(handleComboBoxSelect(2));

        loadDataCategory();
        renderComboxCategory();
        loadDataProduct();
        renderTable();

    }

    private ActionListener handleSetRemoveImage() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderImage(null);
                removeImageStatus = true;
            }
        };
    }

    private ActionListener handleSelectImage() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File file = MethodUtil.chooseFile(view);
                if (file != null) {
                    renderImage(file.toPath().toString());
                    fileSelect = file;
                } else {
                    renderImage(null);
                    fileSelect = null;
                }
            }
        };
    }

    private void renderImage(String path) {
        try {
            // BufferedImage myPicture = ImageIO.read(new
            // File("src/main/resources/images/dt.jpg"));
            BufferedImage myPicture = ImageIO.read(new File(path));
            Image scaled = myPicture.getScaledInstance(-1, 131, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaled);
            view.getLblProductImage().setIcon(icon);
        } catch (Exception e) {
            view.getLblProductImage().setIcon(null);
        }
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
                        JOptionPane.showMessageDialog(view, "Lỗi tim kiếm");
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
                    view.getCboSort().setSelectedIndex(0);
                    view.getCboShowCategory().setSelectedIndex(0);
                } else {
                    view.getTxtSearch().setText("");
                    loadDataProduct();
                    renderTable();
                }

                view.getBtnToggleAdd().setEnabled(ie.getStateChange() != 1);
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

                        int confirm = MethodUtil
                                .showMessageConfirm("Bạn có muốn xóa sản phẩm có mã " + productId + " không?");

                        if (confirm == 0) {

                            int check = model.getProductModel().deleteProduct(productId);

                            String message = "";
                            switch (check) {
                                case 1:
                                    message = "Xoá thành công";
                                    break;
                                case 2:
                                    message = "Số lượng trong kho lớn hơn 0, không thể xoá";
                                    break;
                                default:
                                    message = "Có lỗi, xoá không thành công";
                                    break;
                            }

                            JOptionPane.showMessageDialog(view, message);

                            if (check == 1) {
                                // xoa anh trong thu muc
                                try {
                                    String path = pathFolderImage + listProduct.get(indexTableSelected).getImage();
                                    MethodUtil.deleteFile(path);
                                } catch (Exception ex) {
                                }
                            }

                            loadDataProduct();
                            renderTable();
                            loadDataToField(-1);
                            fileSelect = null;
                            removeImageStatus = false;

                        }

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view, "Có lỗi xãy ra");
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
                        String extension = ""; // đuôi của hình ảnh
                        if (fileSelect != null) {
                            removeImageStatus = false;
                            int i = fileSelect.getName().lastIndexOf('.');
                            if (i > 0) {
                                extension = fileSelect.getName().toString().substring(i + 1);
                            }
                        }

                        // dùng thời gian để làm tên hình ảnh cho đẹp và để không trùng nhau
                        String newImageName = (fileSelect != null) ? (new Date().getTime() + "." + extension) : "";
                        String finalImageName = (newImageName.equals("") && !removeImageStatus)
                                ? listProduct.get(indexTableSelected).getImage()
                                : newImageName;
                        String destpath = pathFolderImage + newImageName;

                        Product prod = new Product(productId, cateId, productName, description, price, finalImageName);

                        try {

                            if (fileSelect != null) {
                                try {
                                    String path = pathFolderImage + listProduct.get(indexTableSelected).getImage();
                                    MethodUtil.deleteFile(path);
                                } catch (Exception ex) {
                                }
                                MethodUtil.copyFile(fileSelect.getPath(), destpath);
                            } else {
                                if (removeImageStatus) {
                                    try {
                                        String path = pathFolderImage + listProduct.get(indexTableSelected).getImage();
                                        MethodUtil.deleteFile(path);
                                    } catch (Exception ex) {
                                    }
                                }
                            }

                            boolean checkEdit = model.getProductModel().editProduct(productId, prod);

                            editStatus = false;
                            view.getBtnEdit().setEnabled(editStatus);
                            view.getBtnToggleEdit().setText("Sửa");

                            loadDataProduct();
                            renderTable();
                            view.getBtnToggleEdit().setEnabled(false);
                            view.getBtnDelete().setEnabled(false);
                            view.getBtnChooseImage().setEnabled(false);
                            view.getBtnDelete().setVisible(true);
                            view.getBtnRemoveImage().setVisible(false);

                            loadDataToField(-1);
                            fileSelect = null;
                            removeImageStatus = false;

                            JOptionPane.showMessageDialog(view, checkEdit ? "Sửa thành công" : "Sửa khôngthành công");

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(view, "Có lỗi xãy ra");
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
                        String extension = ""; // đuôi của hình ảnh
                        if (fileSelect != null) {
                            int i = fileSelect.getName().lastIndexOf('.');
                            if (i > 0) {
                                extension = fileSelect.getName().toString().substring(i + 1);
                            }
                        }

                        // dùng thời gian để làm tên hình ảnh cho đẹp và để không trùng nhau
                        String newImageName = (fileSelect != null) ? (new Date().getTime() + "." + extension) : "";
                        String destpath = pathFolderImage + newImageName;

                        Product prod = new Product(productId, cateId, productName, description, price, newImageName);

                        try {
                            if (fileSelect != null) {
                                MethodUtil.copyFile(fileSelect.getPath(), destpath);
                            }

                            boolean checkAdd = model.getProductModel().addProduct(prod);

                            addStatus = false;
                            view.getBtnAdd().setEnabled(addStatus);
                            view.getBtnToggleAdd().setText("Thêm");

                            loadDataProduct();
                            renderTable();
                            view.getBtnToggleEdit().setEnabled(false);
                            view.getBtnDelete().setEnabled(false);
                            view.getBtnChooseImage().setEnabled(false);
                            view.getBtnDelete().setVisible(true);
                            view.getBtnRemoveImage().setVisible(false);

                            loadDataToField(-1);

                            JOptionPane.showMessageDialog(view, checkAdd ? "Thêm thành công" : "Thêm không thành công");

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(view, "Có lỗi xãy ra");
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
                    removeImageStatus = false;
                }
                // view.getTxtProductId().setEditable(addStatus);
                view.getTxtProductName().setEditable(editStatus);
                view.getTxtProductPrice().setEditable(editStatus);
                view.getTxtProductDescription().setEditable(editStatus);

                view.getTxtCategoryId().setVisible(!editStatus);
                view.getCboProductCategory().setVisible(editStatus);

                view.getBtnEdit().setEnabled(editStatus);
                view.getBtnChooseImage().setEnabled(editStatus);
                view.getBtnDelete().setVisible(!editStatus);
                view.getBtnRemoveImage().setVisible(editStatus);

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
                    editStatus = false;
                    view.getBtnToggleAdd().setText("Huỷ thêm");
                    removeImageStatus = false;
                    renderImage(null);

                    loadDataToField(-1);

                }

                view.getTxtProductId().setEditable(addStatus);
                view.getTxtProductName().setEditable(addStatus);
                view.getTxtProductPrice().setEditable(addStatus);
                view.getTxtProductDescription().setEditable(addStatus);

                view.getTxtCategoryId().setVisible(!addStatus);
                view.getCboProductCategory().setVisible(addStatus);

                view.getBtnAdd().setEnabled(addStatus);
                view.getBtnToggleEdit().setEnabled(editStatus);
                view.getBtnDelete().setEnabled(!addStatus && indexTableSelected >= 0);
                view.getBtnChooseImage().setEnabled(addStatus);
                view.getBtnDelete().setVisible(!addStatus);
                view.getBtnRemoveImage().setVisible(addStatus);

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

            // hien thi anh
            renderImage((!prod.getImage().equals("") && prod.getImage() != null)
                    ? "src/main/resources/images/" + prod.getImage()
                    : null);
        }

        view.getTxtProductId().setText(check ? prod.getProductId() : "");
        view.getTxtProductName().setText(check ? prod.getProductName() : "");
        view.getTxtProductPrice().setText(check ? String.format("%.0f", prod.getPrice()) + "" : "");
        view.getTxtProductDescription().setText(check ? prod.getDescription() : "");
        view.getTxtCategoryId().setText(indexCate > 0 ? cateName : "");
        view.getCboProductCategory().setSelectedIndex(indexCate);

    }

    private MouseAdapter tableListener() {
        return new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                int index = view.getTblProductView().getSelectedRow();
                indexTableSelected = index;

                loadDataToField(index);

                view.getBtnToggleEdit().setEnabled(index >= 0);
                view.getBtnDelete().setEnabled(index >= 0);

                addStatus = false;
                view.getBtnToggleAdd().setText("Thêm mới");
                view.getBtnAdd().setEnabled(false);
                view.getBtnChooseImage().setEnabled(false);
                view.getBtnDelete().setVisible(true);
                view.getBtnRemoveImage().setVisible(false);

            }
        };
    }

    private void loadDataCategory() {
        try {
            listCategory = model.getCategoryModel().getCategories();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu");
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
            JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu");
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
                    prod.getDescription(), String.format("%.0f", prod.getPrice()) };
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

}
